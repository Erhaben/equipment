package ru.equipment.device.printer;

import ru.equipment.entity.Check;
import ru.equipment.hardware.printer.shtrih_connector;
import ru.equipment.tools.Status;
import ru.equipment.tools.Encoding;
import ru.equipment.tools.Convert;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public class SHTRIH_M_PTK {

    private shtrih_connector device;

    public SHTRIH_M_PTK(String device) throws Exception
    {
        this.device = new shtrih_connector(device);
    }

    public HashMap beep() throws Exception
    {
        ArrayList<Integer> answer = this.device.send(0x13, 2, 1, new char[]{});
        if (answer.size() == 0 || answer == null)
            throw new Exception("Пустой ответ");

        HashMap<String, Integer> result = new HashMap<String, Integer>();
        result.put("result", answer.get(0));

        if (answer.get(0) == Status.OK)
        {
            result.put("error_code", answer.get(4));
            result.put("operator", answer.get(5));
        }

        return result;
    }

    public HashMap cut(int type) throws Exception
    {
        Thread.sleep(400);
        ArrayList<Integer> answer = this.device.send(0x25, 2, 3, new char[]{(char)type});
        if (answer.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap<String, Integer> result = new HashMap<String, Integer>();
        result.put("result", answer.get(0));

        if (answer.get(0) == Status.OK)
        {
            result.put("error_code", answer.get(4));
            result.put("operator", answer.get(5));
            //result.put("document_number", answer.get(6));
        }

        Thread.sleep(500);

        return result;
    }

    /**
     * @param type - это тип печати ( цифра один или два )
     * @param text - что нужно напечатать, максимум 40 символов
     * @return ArrayList
     * @throws Exception
     */
    public HashMap printLine(int type, String text) throws Exception
    {
        byte[] in_cp1251 = text.getBytes(Encoding.CP1251);
        char[] params = new char[1 + in_cp1251.length];
        params[0] = (char)type;
        for (int i = 0; i < text.length(); i++)
            params[i + 1] = (char)in_cp1251[i];

        ArrayList<Integer> answer = this.device.send(0x17, 1, 1, params);
        if (answer.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap<String, Integer> result = new HashMap<String, Integer>();
        result.put("result", answer.get(0));

        if (answer.get(0) == Status.OK)
        {
            result.put("error_code", answer.get(4));
            result.put("operator", answer.get(5));
        }

        return result;
    }

    public HashMap openSession() throws Exception
    {
        ArrayList<Integer> answer = this.device.send(0xE0, 100, 10, new char[]{});
        if (answer.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap<String, Integer> result = new HashMap<String, Integer>();
        result.put("result", answer.get(0));

        if (answer.get(0) == Status.OK)
        {
            result.put("error_code", answer.get(4));
            result.put("operator", answer.get(5));
        }

        return result;
    }

    public HashMap closeSession() throws Exception
    {
        ArrayList<Integer> answer = this.device.send(65, 100, 10, new char[]{});
        if (answer.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap<String, Integer> result = new HashMap<String, Integer>();
        result.put("result", answer.get(0));

        if (answer.get(0) == Status.OK)
        {
            if(answer.size() > 1)
            {
                result.put("error_code", answer.get(4));
                result.put("operator", answer.get(5));
            }
        }
        Thread.sleep(100);

        return result;
    }

    public HashMap printReport() throws Exception
    {
        ArrayList<Integer> answer = this.device.send(0x40, 10, 10, new char[]{});
        if (answer.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap<String, Integer> result = new HashMap<String, Integer>();
        result.put("result", answer.get(0));

        if (answer.get(0) == Status.OK)
        {
            result.put("error_code", answer.get(4));
            result.put("operator", answer.get(5));
        }
        Thread.sleep(400);

        return result;
    }

    public HashMap printBarcode(String barcode) throws Exception
    {
        ArrayList<Integer> answer = this.device.send(0xc2, 5, 3, barcode.toCharArray());
        if (answer.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap<String, Integer> result = new HashMap<String, Integer>();
        result.put("result", answer.get(0));

        if (answer.get(0) == Status.OK)
        {
            result.put("error_code", answer.get(4));
            result.put("operator", answer.get(5));
        }

        return result;
    }

    public ArrayList<Integer> printGraph(int begin, int end)  throws Exception
    {
        return this.device.send(0xC1, 5, 3, new char[]{(char)begin, (char)end});
    }

    public ArrayList<Integer> loadGraph(int line, int[] data) throws Exception
    {
        char[] params = new char[1 + data.length];
        params[0] = (char)line;
        for(int i = 0; i < data.length; i++)
            params[1 + i] = (char)data[i];

        return this.device.send(0xC0, 5, 3, params);
    }

    public HashMap cashInput(int amount) throws Exception
    {
        char[] params = Convert.toCharArray(amount);

        ArrayList<Integer> answer = this.device.send(0x50, 70, 3, params);
        if (answer.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap<String, Integer> result = new HashMap<String, Integer>();

        result.put("result", answer.get(0));

        if (answer.get(0) == Status.OK)
        {
            if (answer.size() > 1)
            {
                result.put("error_code", answer.get(4));
                result.put("operator", answer.get(5));

                /*int f_p = answer.get(6);
                int n_p =  answer.get(7);
                byte[] number = {(byte)f_p, (byte)n_p, 0x0, 0x0};
                ByteBuffer wrapped = ByteBuffer.wrap(number);

                result.put("document_number", wrapped.getInt());
                System.out.println(answer);
                System.out.println(result); */
            }
        }

        return result;
    }

    public HashMap cashOutput(int amount) throws Exception
    {
        char[] params = Convert.toCharArray(amount);

        ArrayList<Integer> answer = this.device.send(0x51, 70, 3, params);
        if (answer.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap<String, Integer> result = new HashMap<String, Integer>();

        result.put("result", answer.get(0));

        if (answer.get(0) == Status.OK)
        {
            if (answer.size() > 1)
            {
                result.put("error_code", answer.get(4));
                result.put("operator", answer.get(5));

                /*byte[] number = {0x0, 0x0, answer.get(6).byteValue(), answer.get(7).byteValue()};
                ByteBuffer wrapped = ByteBuffer.wrap(number);

                result.put("document_number", wrapped.getInt());           */
            }
        }

        return result;
    }

    public HashMap broach(int type, int count) throws Exception
    {
        char[] params = new char[]{(char)type, (char)count};

        ArrayList<Integer> answer = this.device.send(0x29, 3, 3, params);
        if (answer.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap<String, Integer> result = new HashMap<String, Integer>();

        if (answer.get(0) == Status.OK)
        {
            if (answer.size() > 1)
            {
                result.put("error_code", answer.get(4));
                result.put("operator", answer.get(5));
            }
        }

        return  result;
    }

    public HashMap printCliche() throws Exception
    {
        ArrayList<Integer> answer = this.device.send(0x52, 3, 3, new char[]{});
        if (answer.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap<String, Integer> result = new HashMap<String, Integer>();

        if (answer.get(0) == Status.OK)
        {
            if (answer.size() > 1)
            {
                result.put("error_code", answer.get(4));
                result.put("operator", answer.get(5));
            }
        }

        return  result;
    }

    public HashMap<String, Integer> printCheck(Check check) throws Exception
    {
        HashMap<String, Integer> result = new HashMap<String, Integer>();

        if ( ! check.validate())
            throw new Exception("Ошибка заполнения чека");

        int command = check.getCommand();

        ArrayList<Integer> answer = this.device.send(0x8D, 50, 4, new char[]{(char)check.getType()});

        if (answer.get(0) != Status.OK)
            throw new Exception("Ошибка открытия чека");

        int products_count = check.getProductsCount();

        for(int i = 0; i < products_count; i++)
        {
            String title = check.getProduct(i).title;

            if (title.length() > 39)
            {
                this.printLongString(2, 39, title);
                check.getProduct(i).title = "";
            }
            answer = device.send(command, 30, 5, check.getProduct(i).getSaleParams());

            if (answer.get(0) != Status.OK)
                throw new Exception("Ошибка продажи товара");
        }

        //Thread.sleep(400);
        answer = device.send(0x85, 50, 8, check.getCloseParams());

        if (answer.get(0) != Status.OK)
            throw new Exception("Ошибка закрытия чека");

        result.put("result", answer.get(0));

        if (answer.get(0) == Status.OK)
        {
            if (answer.size() > 1)
            {
                result.put("error_code", answer.get(4));
                result.put("operator", answer.get(5));
            }
        }

        return result;
    }

    public void printLongString(int type, int max, String text) throws Exception
    {
        HashMap map = new HashMap();

        String line = "";
        int row_id = 0;

        String[] words = text.split(" ");

        for(int i = 0; i < words.length; i++)
        {
            line = "";

            if (map.containsKey(row_id))
            {
                line = (String)map.get(row_id);
            }

            int length_of_word = words[i].length();
            int length_of_line = line.length();

            if ( (length_of_word + length_of_line) <= max)
            {
                line += " " + words[i];
                map.put(row_id, line);
            }
            else
            {
                row_id += 1;

                line = words[i];

                map.put(row_id, line);
            }
        }

        for (int i = 0; i < map.size(); i++)
        {
            this.printLine(type, (String)map.get(i));
        }
    }

    public HashMap cancelCheck() throws Exception
    {
        Thread.sleep(200);
        ArrayList<Integer> answer = this.device.send(0x88, 3, 3, new char[]{});
        if (answer.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap result = new HashMap();
        result.put("result", answer.get(0));

        if (answer.get(0) == Status.OK)
        {
            result.put("error_code", answer.get(4));
            result.put("operator", answer.get(5));
        }

        return result;
    }

    public HashMap continuePrint() throws Exception
    {
        Thread.sleep(200);
        ArrayList<Integer> answer = this.device.send(0xB0, 3, 3, new char[]{});
        if (answer.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap result = new HashMap();
        result.put("result", answer.get(0));

        if (answer.get(0) == Status.OK)
        {
            result.put("error_code", answer.get(4));
            result.put("operator", answer.get(5));
        }

        return result;
    }

    public HashMap getStatus() throws Exception
    {
        Thread.sleep(200);
        ArrayList<Integer> status = this.device.send(0x11, 50, 3, new char[]{});
        if (status.size() == 0)
            throw new Exception("Пустой ответ");

        HashMap answer = new HashMap();

        // Ответ от принтера
        answer.put("result", status.get(0));

        if ( status.get(0) == Status.OK)
        {
            // Байт начала сообщения
            answer.put("STX", status.get(1));

            // Длина сообщения
            answer.put("length", status.get(2));

            // Команда
            answer.put("command", status.get(3));

            // Код ошибки
            answer.put("error_code", status.get(4).toString());

            // Номер оператора
            answer.put("operator", status.get(5).toString());

            // Версия ПО фискального регистратора
            answer.put("version_soft_fr", (char)Integer.parseInt(status.get(6).toString()) + "." + (char)Integer.parseInt(status.get(7).toString()));

            // Сборка ПО фискального регистратора
            answer.put("assembly_soft_fr", status.get(8).toString() + status.get(9).toString());

            // Дата ПО фискального регистратора ( ??? )
            answer.put("date_fr", status.get(10).toString() + "-" + status.get(11).toString() +  "-" +status.get(12).toString());

            // Номер в зале
            answer.put("number_in_hall", status.get(13).toString());

            // Текущий номер документа ( сквозной )
            answer.put("current_doc_num", (status.get(14) + status.get(15)));

            // Флаги фискального регистратора
            answer.put("flags_fr", status.get(16).toString() + status.get(17).toString());

            // Режим работы
            answer.put("mode", status.get(18).toString());

            // Подрежим ( может отсутствовать )
            answer.put("submode", status.get(19).toString());

            // Порт фискального регистратора
            answer.put("port_fr", status.get(20).toString());

            // Версия ПО ФП
            answer.put("version_soft_fp",  (char)Integer.parseInt(status.get(21).toString()) + "."  + (char)Integer.parseInt(status.get(22).toString()));

            // Сборка ПО ФП
            answer.put("assembly_soft_fp", status.get(23).toString() + status.get(24).toString());

            // Дата ПО ФП
            answer.put("date_fp", status.get(25).toString() + "-" + status.get(26).toString() +  "-" +status.get(27).toString());

            // Дата принтера
            answer.put("date", status.get(28).toString() + "-" + status.get(29).toString() +  "-" +status.get(30).toString());

            // Время принтера
            answer.put("time", status.get(31).toString() + "-" + status.get(32).toString() +  "-" +status.get(33).toString());

            // Флаги ФП
            answer.put("flags_fp", status.get(34));

            // Заводской номер
            answer.put("manufacturer_num", status.get(35).toString() + status.get(36).toString() + status.get(37).toString() + status.get(38).toString());

            // Номер последней закрытой смены
            answer.put("num_of_last_closed_session", status.get(39).toString() + status.get(40).toString());

            // Количество свободных записей в ФП
            answer.put("free_records_in_fp", status.get(41).toString() + status.get(42).toString());

            // Количество перерегистраций
            answer.put("count_of_fiscal", status.get(43).toString());

            // Количество оставшихся перерегистраций
            answer.put("count_of_fiscal_balance", status.get(44).toString());

            // ИНН
            answer.put("inn", status.get(45).toString() + status.get(46).toString() + status.get(47).toString()+ status.get(48).toString() + status.get(49).toString()+ status.get(50).toString());
        }

        return answer;
    }

    public String sayHelloToTest()
    {
        return "Hello";
    }
}
