package ru.equipment.tests;

import ru.equipment.device.printer.SHTRIH_M_PTK;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import ru.equipment.entity.Check;

import static org.junit.Assert.assertEquals;

public class SessionTest {

    SHTRIH_M_PTK device;

    @Before
    public void setUp() throws Exception
    {
        this.device = new SHTRIH_M_PTK("ttyUSB0");
    }

    @Test
    public void testRun() throws Exception
    {
        int error_code;
        HashMap status = this.device.getStatus();
        error_code = Integer.parseInt(status.get("error_code").toString());
        System.out.println(status);
        assertEquals(error_code, 0);

        HashMap answer = new HashMap();
        answer = this.device.closeSession();
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        this.device.cut(1);

        answer = this.device.openSession();
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        answer = this.device.getStatus();
        System.out.println(answer);
        int mode = Integer.parseInt(answer.get("mode").toString());
        assertEquals(mode, 2);

        answer = this.device.cut(1);
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

    }

}
