package ru.equipment.controller;

import ru.equipment.device.printer.SHTRIH_M_PTK;
import ru.equipment.init.applet;
import ru.equipment.entity.Check;
import java.security.PrivilegedAction;
import java.util.HashMap;

public class PrinterController
{
    private HashMap transfer = null;
    private Check check = null;

    private applet window;
    private String device_name = "ttyUSB0";

    public PrinterController(applet w)
    {
        this.window = w;
    }

    public void setDeviceName(String name)
    {
        this.device_name = name;
    }

    public HashMap beep()
    {
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try {
                    SHTRIH_M_PTK printer = new SHTRIH_M_PTK(device_name);
                    transfer = printer.beep();
                    printer = null;
                } catch (Exception ex) {
                    window.warn("[Printer]" + ex.toString());
                }

                return null; // nothing to return
            }
        });
        return this.transfer;
    }

    public HashMap cut()
    {
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try
                {
                    SHTRIH_M_PTK printer = new SHTRIH_M_PTK(device_name);
                    transfer = printer.cut(1);
                    printer = null;
                }
                catch (Exception ex)
                {
                    window.warn("[Printer]" + ex.toString());
                }

                return null; // nothing to return
            }
        });
        return this.transfer;
    }

    public HashMap income(final double amount)
    {
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try
                {
                    SHTRIH_M_PTK printer = new SHTRIH_M_PTK(device_name);

                    int amount_raw = new Double(amount * 100).intValue();
                    transfer = printer.cashInput(amount_raw);
                    printer = null;
                }
                catch (Exception ex)
                {
                    window.warn("[Printer]" + ex.toString());
                }

                return null; // nothing to return
            }
        });
        return this.transfer;
    }

    public HashMap outcome(final double amount)
    {
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try
                {
                    SHTRIH_M_PTK printer = new SHTRIH_M_PTK(device_name);

                    int amount_raw = new Double(amount * 100).intValue();
                    transfer = printer.cashOutput(amount_raw);
                    printer = null;
                }
                catch (Exception ex)
                {
                    window.warn("[Printer]" + ex.toString());
                }

                return null; // nothing to return
            }
        });
        return this.transfer;
    }

    public HashMap broach(final int lines)
    {
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try
                {
                    SHTRIH_M_PTK printer = new SHTRIH_M_PTK(device_name);
                    transfer = printer.broach(2, lines);
                    printer = null;
                }
                catch (Exception ex)
                {
                    window.warn("[Printer]" + ex.toString());
                }

                return null; // nothing to return
            }
        });
        return this.transfer;
    }

    public HashMap openSession()
    {
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try
                {
                    SHTRIH_M_PTK printer = new SHTRIH_M_PTK(device_name);
                    transfer = printer.openSession();
                    printer = null;
                }
                catch (Exception ex)
                {
                    window.warn("[Printer]" + ex.toString());
                }

                return null; // nothing to return
            }
        });
        return this.transfer;
    }

    public HashMap closeSession()
    {
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try
                {
                    SHTRIH_M_PTK printer = new SHTRIH_M_PTK(device_name);
                    transfer = printer.closeSession();
                    printer = null;
                }
                catch (Exception ex)
                {
                    window.warn("[Printer]" + ex.toString());
                }

                return null; // nothing to return
            }
        });
        return this.transfer;
    }

    public void initCheck()
    {
        this.check = new Check();
    }

    public void setCheckType(int type)
    {
        if (this.check == null)
            this.check = new Check();

        this.check.setType(type);
    }

    public void addProductToCheck(String title, int count, double price)
    {
        if (this.check == null)
            this.check = new Check();

        this.check.addProduct(title, count, price);
    }

    public void addPaymentToCheck(int index, double amount)
    {
        if (this.check == null)
            this.check = new Check();

        this.check.addPayment(index, amount);
    }

    public HashMap printCheck()
    {
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try
                {
                    SHTRIH_M_PTK printer = new SHTRIH_M_PTK(device_name);
                    transfer = printer.printCheck(check);
                    printer = null;
                }
                catch (Exception ex)
                {
                    window.warn("[Printer]" + ex.toString());
                }

                return null; // nothing to return
            }
        });

        this.initCheck();
        return this.transfer;
    }

    public HashMap cancelCheck()
    {
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try
                {
                    SHTRIH_M_PTK printer = new SHTRIH_M_PTK(device_name);
                    transfer = printer.cancelCheck();
                    printer = null;
                }
                catch (Exception ex)
                {
                    window.warn("[Printer]" + ex.toString());
                }

                return null; // nothing to return
            }
        });

        this.initCheck();
        return this.transfer;
    }

    public HashMap getStatus()
    {
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try
                {
                    SHTRIH_M_PTK printer = new SHTRIH_M_PTK(device_name);
                    transfer = printer.getStatus();
                    printer = null;
                }
                catch (Exception ex)
                {
                    window.warn("[Printer]" + ex.toString());
                }

                return null;
            }
        });

        return this.transfer;
    }

    public HashMap continuePrint()
    {
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try
                {
                    SHTRIH_M_PTK printer = new SHTRIH_M_PTK(device_name);
                    transfer = printer.continuePrint();
                    printer = null;
                }
                catch (Exception ex)
                {
                    window.warn("[Printer]" + ex.toString());
                }

                return null;
            }
        });

        return this.transfer;
    }

    public HashMap printString(final int type, final int limit, final String line)
    {
        this.transfer = null;

        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try
                {
                    SHTRIH_M_PTK printer = new SHTRIH_M_PTK(device_name);

                    if (line.length() >= limit)
                    {
                         printer.printLongString(type, limit, line);
                    }
                    else
                    {
                         printer.printLine(type, line);
                    }

                    printer = null;
                }
                catch (Exception ex)
                {
                    window.warn("[Printer]" + ex.toString());
                }

                return null;
            }
        });

        return this.transfer;
    }
}
