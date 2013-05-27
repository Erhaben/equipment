package ru.equipment.init;

import java.applet.Applet;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;
import netscape.javascript.*; // add plugin.jar to classpath during compilation
import ru.equipment.controller.PosController;
import ru.equipment.controller.PrinterController;

public class applet extends Applet
{
    private JSObject window;
    private PrinterController printer;
    private PosController pos;

    @Override
    public void init()
    {
        this.printer = new PrinterController(this);
        this.pos = new PosController(this);
        this.window = JSObject.getWindow(this);
        this.log("[Applet] Запущен");
    }

    @Override
    public void start()
    {
        try
        {
            this.window.eval("plugin.printer.status()");
        }
        catch (Exception ex)
        {
            this.warn("[Applet] Не могу запустить JS метод получения статуса устройства. Метод содержит ошибки или отсутствует.");
        }
    }

    public String printerCut()
    {
        this.log("[Applet] Отрезка ленты");
        String answer;

        try
        {
            HashMap a = this.printer.cut();
            if(a == null)
                throw new Exception();

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }

        return answer;
    }

    public String printerBeep()
    {
        this.log("[Applet] Сигнал");
        String answer;

        try
        {
            HashMap a = this.printer.beep();
            if(a == null)
                throw new Exception();

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }

        return answer;
    }

    public String printerBroach(int lines)
    {
        this.log("[Applet] Прокрутка ленты (" + Integer.toString(lines) + " линий)");
        String answer;

        try
        {
            HashMap a = this.printer.broach(lines);
            if(a == null)
                throw new Exception();

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }

        return answer;
    }

    public String printerIncome(double amount)
    {
        this.log("[Applet] Внесение денег (" + Double.toString(amount) + " руб.)");
        String answer;

        try
        {
            HashMap a = this.printer.income(amount);
            if(a == null)
                throw new Exception();

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }
        return answer;
    }

    public String printerOutcome(double amount)
    {
        this.log("[Applet] Выдача денег (" + Double.toString(amount) + " руб.)");
        String answer;

        try
        {
            HashMap a = this.printer.outcome(amount);
            if(a == null)
                throw new Exception();

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }
        return answer;
    }

    public String printerOpenSession()
    {
        this.log("[Applet] Открытие смены");
        String answer;

        try
        {
            HashMap a = this.printer.openSession();
            if(a == null)
                throw new Exception();

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }
        return answer;
    }

    public String printerCloseSession()
    {
        this.log("[Applet] Отчет с гашением");
        String answer;

        try
        {
            HashMap a = this.printer.closeSession();
            if(a == null)
                throw new Exception();

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }
        return answer;
    }

    public String printerGetStatus()
    {
        this.log("[Applet] Получение состояния принтера");
        String answer;
        try
        {
            HashMap a = this.printer.getStatus();
            if(a == null)
                throw new Exception();

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }

        return answer;
    }

    public int printerInitCheck()
    {
        this.log("[Applet] Инициализация чека");
        int answer = 1;
        try
        {
            this.printer.initCheck();
            this.log("[Applet] Выполнено");
        }
        catch (Exception ex)
        {
            answer = -1;
            this.warn("[Applet] Ошибка");
        }
        return answer;
    }

    public int printerSetCheckType(int type)
    {
        this.log("[Applet] Указание типа чека ( " + Integer.toString(type) + " )");
        int answer = 1;
        try
        {
            this.printer.setCheckType(type);
            this.log("[Applet] Выполнено");
        }
        catch (Exception ex)
        {
            answer = -1;
            this.warn("[Applet] Ошибка");
        }
        return answer;
    }

    public int printerAddProductToCheck(String title, int count, double price)
    {
        this.log("[Applet] Добавление товара к чеку");
        this.log("[Applet] " + title);
        this.log("[Applet] " + Integer.toString(count) + " шт.");
        this.log("[Applet] " + Double.toString(price) + " руб.");
        int answer = 1;
        try
        {
            this.printer.addProductToCheck(title, count, price);
            this.log("[Applet] Выполнено");
        }
        catch (Exception ex)
        {
            answer = -1;
            this.warn("[Applet] Ошибка");
        }
        return answer;
    }

    public int printerAddPaymentToCheck(int index, double amount)
    {
        this.log("[Applet] Добавление платежа к чеку ( " + Double.toString(amount) + " руб. )");
        int answer = 1;
        try
        {
            this.printer.addPaymentToCheck(index, amount);
            this.log("[Applet] Выполнено");
        }
        catch (Exception ex)
        {
            answer = -1;
            this.warn("[Applet] Ошибка");
        }
        return answer;
    }

    public String printerCancelCheck()
    {
        this.log("[Applet] Отмена чека");
        String answer;

        try
        {
            HashMap a = this.printer.cancelCheck();
            if(a == null)
                throw new Exception();

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }
        return answer;
    }

    public String printerPrintCheck()
    {
        this.log("[Applet] Печать чека");
        String answer;

        try
        {
            HashMap a = this.printer.printCheck();
            if(a == null)
                throw new Exception();

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }
        return answer;
    }

    public String printerContinuePrint()
    {
        this.log("[Applet] Продолжение прерваной печати");
        String answer;

        try
        {
            HashMap a = this.printer.continuePrint();
            if(a == null)
                throw new Exception();

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }
        return answer;
    }

    public String posIncome(Double amount)
    {
        this.log("[Applet] Внесение денег по пластиковой карте");
        String answer;

        try
        {
            HashMap<String, ArrayList> a = this.pos.income(amount);

            ArrayList<String> check = a.get("check");

            String status = this.printerGetStatus();
            if ( ! status.equalsIgnoreCase("-1"))
            {
                for(int i = 2; i < check.size(); i++)
                    this.printer.printString(2, 39, check.get(i));
            }

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }

        return answer;
    }

    public String posOutcome(Double amount)
    {
        this.log("[Applet] Возврат денег на пластиковой карте");
        String answer;

        try
        {
            HashMap<String, ArrayList> a = this.pos.outcome(amount);

            ArrayList<String> check = a.get("check");

            String status = this.printerGetStatus();
            if ( ! status.equalsIgnoreCase("-1"))
            {
                for(int i = 2; i < check.size(); i++)
                    this.printer.printString(2, 39, check.get(i));
            }

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }

        return answer;
    }

    public String posRevise()
    {
        this.log("[Applet] Сверка итогов");
        String answer = "";

        try
        {
            HashMap<String, ArrayList> a = this.pos.revise();

            ArrayList<String> check = a.get("check");

            /*String status = this.printerGetStatus();
            if ( ! status.equalsIgnoreCase("-1"))
            {
                for(int i = 2; i < check.size(); i++)
                    this.printer.printString(2, 39, check.get(i));
            }*/

            JSONObject json = new JSONObject(a);
            answer = json.toString();
        }
        catch (Exception ex)
        {
            answer = "-1";
        }

        return answer;
    }

    public void log(String message)
    {
        String code = "console.log('" + message + "')";
        System.out.println(code);
        this.window.eval(code);
    }

    public void warn(String message)
    {
        String code = "console.warn('" + message + "')";
        System.out.println(code);
        this.window.eval(code);
    }

    public void error(String message)
    {
        String code = "console.error('" + message + "')";
        System.out.println(code);
        this.window.eval(code);
    }

    public void info(String message)
    {
        String code = "console.info('" + message + "')";
        System.out.println(code);
        this.window.eval(code);
    }

    public String hello()
    {
        return "hello";
    }
}
