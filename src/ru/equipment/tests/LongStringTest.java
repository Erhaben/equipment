package ru.equipment.tests;

import ru.equipment.device.printer.SHTRIH_M_PTK;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import ru.equipment.entity.Check;

import static org.junit.Assert.assertEquals;

public class LongStringTest
{
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
        String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam luctus volutpat quam, nec condimentum nisi interdum id. Phasellus commodo sapien vitae leo accumsan facilisis. Nam sollicitudin est ac augue aliquam vitae hendrerit enim faucibus. Maecenas in nisl sit amet lacus mattis convallis ac vitae nunc. Nullam a nunc sed dui pulvinar hendrerit ac a urna. Duis sed arcu eu neque viverra imperdiet. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Duis nec lectus magna, vel condimentum risus. Etiam ipsum lectus, egestas ut volutpat ut, faucibus in tortor. In hac habitasse platea dictumst. Nulla in velit dui. Duis vitae erat eu mi posuere pharetra ac a odio. Mauris vitae erat mattis tellus vulputate tempus.\n";

        HashMap status = this.device.getStatus();
        error_code = Integer.parseInt(status.get("error_code").toString());
        System.out.println(status);
        assertEquals(error_code, 0);

        this.device.printLongString(2, 39, lorem);
        this.device.broach(2, 10);
        System.out.println(this.device.cut(1));
    }
}
