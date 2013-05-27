package ru.equipment.controller;

import ru.equipment.device.terminal.VERIFONE_VX_810;
import ru.equipment.init.applet;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;

public class PosController
{
    private HashMap<String, ArrayList> transfer = null;
    private applet window;
    private Double transfer_amount = 0.0;

    public PosController(applet w)
    {
        this.window = w;
    }

    public HashMap<String, ArrayList> income(Double amount)
    {
        this.transfer_amount = amount;
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try {
                    VERIFONE_VX_810 pos = new VERIFONE_VX_810();
                    transfer = pos.income(transfer_amount);
                    transfer_amount = 0.0;
                    pos = null;
                } catch (Exception ex) {
                    window.warn("[pos]" + ex.toString());
                }

                return null; // nothing to return
            }
        });
        return this.transfer;
    }

    public HashMap<String, ArrayList> outcome(Double amount)
    {
        this.transfer_amount = amount;
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try {
                    VERIFONE_VX_810 pos = new VERIFONE_VX_810();
                    transfer = pos.outcome(transfer_amount);
                    transfer_amount = 0.0;
                    pos = null;
                } catch (Exception ex) {
                    window.warn("[pos]" + ex.toString());
                }

                return null; // nothing to return
            }
        });
        return this.transfer;
    }

    public HashMap<String, ArrayList> revise()
    {
        this.transfer = null;
        java.security.AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
                try {
                    VERIFONE_VX_810 pos = new VERIFONE_VX_810();
                    transfer = pos.revise();
                    pos = null;
                } catch (Exception ex) {
                    window.warn("[pos]" + ex.toString());
                }

                return null; // nothing to return
            }
        });
        return this.transfer;
    }
}
