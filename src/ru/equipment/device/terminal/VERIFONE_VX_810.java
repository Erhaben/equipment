package ru.equipment.device.terminal;

import ru.equipment.hardware.terminal.verifonevx810_connector;

import java.util.ArrayList;
import java.util.HashMap;

public class VERIFONE_VX_810
{
    verifonevx810_connector device;

    public VERIFONE_VX_810() throws Exception
    {
        this.device = new verifonevx810_connector();
    }

    public HashMap<String, ArrayList> income(Double amount)
    {
        return this.device.income(amount);
    }

    public HashMap<String, ArrayList> outcome(Double amount)
    {
        return this.device.outcome(amount);
    }

    public HashMap<String, ArrayList> revise()
    {
        return this.device.revise();
    }
}
