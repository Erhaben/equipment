package ru.equipment.hardware.printer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import ru.equipment.tools.Math;
import ru.equipment.tools.Convert;
import ru.equipment.tools.Status;
import ru.equipment.tools.Sign;

public class shtrih_connector
{
    /* Прочее */
    private int MAX_TRIES = 20;

    /* Интервалы */
    private int MAX_INTERVAL = 50;

    /* Пароли */
    private int[] ADMIN_PASS = {0x1e,0x0,0x0,0x0}; // По умолчанию 30
    private int[] OPERATOR_PASS = {0x1,0x0,0x0,0x0}; // По умолчанию 1

    /* Ввод - вывод */
    private File device = null;
    private FileInputStream input = null;
    private FileOutputStream output = null;

    public shtrih_connector(String device_name) throws Exception
    {
        this.device = new File("/dev/" + device_name);

        if ( ! this.device.exists())
            throw  new Exception("Device not found!");
    }

    /* Проверка устройства при создании */
    public boolean checkState() throws Exception
    {
        boolean result = false;

        this.input = new FileInputStream(this.device.getAbsolutePath());
        this.output = new FileOutputStream(this.device.getAbsolutePath());

        this.output.flush();

        Thread.sleep(this.MAX_INTERVAL * 3);

        System.out.println(input.available());

        this.output.write((byte)Sign.REQUEST);
        this.output.flush();

        Thread.sleep(this.MAX_INTERVAL * 8);
        System.out.println(input.available());
        Integer c = 0;
        input.markSupported();
        ArrayList<Integer> list = new ArrayList<Integer>();
        if ( input.available() > 0)
        {
            while ((c = input.read()) != -1)
                list.add(c);

            if (list.size() > 0)
            {
                if(list.get(0) == Status.ERROR)
                {
                    byte[] command = new byte[2];
                    command[0] = (byte)Status.OK;
                    command[1] = (byte)Sign.REQUEST;
                    this.output.write(command);
                    this.output.flush();
                    result = true;
                }
            }
        }

        this.input.close();
        this.output.close();

        return  result;
    }

    /* Очистить соединение */
    private int sendClearCommand() throws Exception
    {
        int c;
        ArrayList<Integer> answer = new ArrayList();

        this.output.flush();
        Thread.sleep(this.MAX_INTERVAL * 10);

        this.output.write((char)Sign.REQUEST);

        this.output.flush();
        Thread.sleep(this.MAX_INTERVAL * 3);
        //this.input.reset();
        System.out.println();
        while ((c = input.read()) != -1)
            answer.add(c);


        if (answer.get(0) == Status.ERROR)
            return 1;

        if (answer.get(0) == Status.OK)
        {
            if (answer.get(1) != Sign.MESSAGE_BEGIN)
                throw new Exception("Второй байт ответа не является STX!");

            this.output.write((char)Status.OK);
            Thread.sleep(this.MAX_INTERVAL * 2);
            return 2;
        }
        else
            throw new Exception("Первый байт ответа не является ACK млм NAK!");
    }

    private int clearAnswer() throws Exception
    {
        int n = 0;

        while(n < this.MAX_TRIES && this.sendClearCommand() != 1)
        {
            if ( n > this.MAX_TRIES)
                throw new Exception("Достигнут максимальный лимит попыток");

            n += 1;
        }

        return 0;
    }

    /* Разбор ответа */
    private ArrayList<Integer> parseAnswer(int multiplier) throws Exception
    {
        Thread.sleep(this.MAX_INTERVAL * multiplier);
        int c;
        ArrayList<Integer> answer = new ArrayList<Integer>();
        while ((c = input.read()) != -1)
            answer.add(c);

        if (answer.get(0) == Status.OK)
        {
            if (answer.size() > 1)
            {
                if (answer.get(1) != Sign.MESSAGE_BEGIN)
                {
                    this.output.write((char)Status.ERROR);
                    this.output.flush();
                    throw new Exception("Второй байт ответа не является STX!");
                }


                int length = answer.get(2);

                String data = "";

                for (int i = 5; i < answer.size() - 2; i++)
                    data += (char)(int)answer.get(i);

                /*if ((length - 3) != data.length())
                {
                    System.out.println(length);
                    System.out.println(data.length());
                    this.output.write((char)Status.ERROR);
                    this.output.flush();
                    throw new Exception("Длина сообщения не совпадает!");
                }*/
            }

            this.output.write((char)Status.OK);
            this.output.flush();
            //Thread.sleep(this.MAX_INTERVAL * 2);
        }
        else if (answer.get(0) == Status.ERROR)
        {
            System.out.println("Получен NAK");
        }
        else
            throw new Exception("Первый байт ответа не является ACK млм NAK!");

        return answer;
    }

    private void open() throws Exception
    {
        this.input = new FileInputStream(this.device.getAbsolutePath());
        this.output = new FileOutputStream(this.device.getAbsolutePath());

        this.clearAnswer();
        this.output.flush();
        Thread.sleep(this.MAX_INTERVAL * 2);
    }

    private void close() throws Exception
    {
        this.input.close();
        this.output.close();
    }

    public ArrayList send(int command, int multiplier, char[] params_raw) throws Exception
    {
        this.open();
        /* Создание строки параметров */
        String params = Math.ByteToString(this.ADMIN_PASS);

        if (params_raw.length > 0)
            for(int b = 0; b < params_raw.length; b++)
                params += params_raw[b];

        /* Вычисление длины команды и массива байт */
        int data_length = 1 + params.length();
        int command_length = 4 + params.length();

        /* Строка необходима для подсчета CRC */
        String content = Convert.toBinary(data_length) + (Convert.toBinary(command) + params);

        /* Формирование команды */
        byte[] command_bytes = new byte[command_length];
        /* Байт начала команды */
        command_bytes[0] = (byte)Sign.MESSAGE_BEGIN;
        /* Байт длины сообщения */
        command_bytes[1] = (byte)Convert.toBinary(data_length);
        /* Байт указания команды */
        command_bytes[2] = (byte)Convert.toBinary(command);
        /* Параметры команды */
        for(int i = 0; i < params.length(); i++)
            command_bytes[i + 3] = (byte)params.charAt(i);
        /* Указываем CRC */
        command_bytes[command_length - 1] = (byte)Math.LRC(content);

        this.output.write(command_bytes);

        ArrayList answer = this.parseAnswer(multiplier);

        this.close();

        return answer;
    }
}
