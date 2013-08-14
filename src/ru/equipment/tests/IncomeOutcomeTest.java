package ru.equipment.tests;

import ru.equipment.device.printer.SHTRIH_M_PTK;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import ru.equipment.entity.Check;

import static org.junit.Assert.assertEquals;

public class IncomeOutcomeTest {
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
        HashMap answer = new HashMap();
        HashMap status = this.device.getStatus();
        error_code = Integer.parseInt(status.get("error_code").toString());
        System.out.println(status);
        assertEquals(error_code, 0);

        // Проверка cashInput ( на 100 рублей )
        answer = this.device.cashInput(10000);
        System.out.println(answer);
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        this.device.cut(1);

        // Проверка cashOutput ( на 100 рублей )
        answer = this.device.cashOutput(10000);
        System.out.println(answer);
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        this.device.cut(1);
    }
}
