package ru.equipment.tests;

import ru.equipment.device.printer.SHTRIH_M_PTK;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import ru.equipment.entity.Check;

import static org.junit.Assert.assertEquals;

public class printCancelCheckTest {

    SHTRIH_M_PTK device;

    @Before
    public void setUp() throws Exception
    {
        this.device = new SHTRIH_M_PTK("ttyUSB0");
    }

    @Test
    public void testPrintCheck() throws Exception
    {
        int error_code;
        HashMap answer = new HashMap();

        // Проверка получения статуса
        answer = this.device.getStatus();
        error_code = Integer.parseInt(answer.get("error_code").toString());
        System.out.println(answer);
        assertEquals(error_code, 0);

        Check c = new Check();
        c.setType(2);
        c.setTaxes(new int[]{0,0,0,0});
        c.setDiscount(0.0);

        String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam luctus volutpat quam, nec condimentum nisi interdum id. Phasellus commodo sapien vitae leo accumsan facilisis. Nam sollicitudin est ac augue aliquam vitae hendrerit enim faucibus. Maecenas in nisl sit amet lacus mattis convallis ac vitae nunc. Nullam a nunc sed dui pulvinar hendrerit ac a urna. Duis sed arcu eu neque viverra imperdiet. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Duis nec lectus magna, vel condimentum risus. Etiam ipsum lectus, egestas ut volutpat ut, faucibus in tortor. In hac habitasse platea dictumst. Nulla in velit dui. Duis vitae erat eu mi posuere pharetra ac a odio. Mauris vitae erat mattis tellus vulputate tempus.\n";
        // Итого 1 * 1 + 2 * 1 + 3 * 1 + 10 * 1.1 = 1 + 2 + 3 + 11 = 17
        c.addProduct("Первый товар", 1, 1);
        c.addProduct("Второй товар", 2, 1);
        c.addProduct("Третий товар", 3, 1);
        c.addProduct(lorem, 10, 1.1);

        c.addPayment(0, 16);
        c.addPayment(3, 1);

        answer = this.device.printCheck(c);
        error_code = Integer.parseInt(answer.get("error_code").toString());
        System.out.println(answer);
        assertEquals(error_code, 0);

        this.device.cut(1);

    }

}
