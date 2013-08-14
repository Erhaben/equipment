package ru.equipment.init;
import ru.equipment.device.printer.SHTRIH_M_PTK;
import ru.equipment.device.terminal.VERIFONE_VX_810;
import ru.equipment.entity.Check;

import java.util.HashMap;

// sudo stty -F /dev/ttyUSB0 115200
public class Init
{
    public static void main(String arg[])
    {
        try
        {
            SHTRIH_M_PTK printer = new SHTRIH_M_PTK("ttyUSB0");
            //printer.beep();
            System.out.println(printer.getStatus());
            //printer.openSession();
            //System.out.println(printer.cancelCheck());

            //System.out.println(printer.openSession());

            //System.out.println(printer.closeSession());

            //System.out.println(printer.openSession());

            //printer.cut(1);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
