import ru.equipment.device.printer.SHTRIH_M_PTK;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import ru.equipment.entity.Check;

import static org.junit.Assert.assertEquals;

public class ShtrihMPtkTest
{
    SHTRIH_M_PTK device;

    @Before
    public void setUp() throws Exception
    {
        this.device = new SHTRIH_M_PTK("ttyUSB0");
    }

    @Test
    public void testSayHello() throws Exception
    {
        String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam luctus volutpat quam, nec condimentum nisi interdum id. Phasellus commodo sapien vitae leo accumsan facilisis. Nam sollicitudin est ac augue aliquam vitae hendrerit enim faucibus. Maecenas in nisl sit amet lacus mattis convallis ac vitae nunc. Nullam a nunc sed dui pulvinar hendrerit ac a urna. Duis sed arcu eu neque viverra imperdiet. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Duis nec lectus magna, vel condimentum risus. Etiam ipsum lectus, egestas ut volutpat ut, faucibus in tortor. In hac habitasse platea dictumst. Nulla in velit dui. Duis vitae erat eu mi posuere pharetra ac a odio. Mauris vitae erat mattis tellus vulputate tempus.\n";

        int error_code;
        HashMap answer = new HashMap();

        // Проверка получения статуса
        answer = this.device.getStatus();
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        // Проверка открытия смены
        answer = this.device.openSession();
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        // Проверка печати чека

        Check c = new Check();
        c.setType(0);
        c.setTaxes(new int[]{0,0,0,0});
        c.setDiscount(0.0);

        c.addProduct("Первый товар", 1, 22.3);
        c.addProduct("Второй товар", 2, 122.3);
        c.addProduct("Третий товар", 3, 222.3);
        c.addProduct("Третий товар Третий товар Третий товар Третий товар Третий товар", 10, 322.3);
        c.addProduct("Пятый товар", 40, 2002.3);

        c.addPayment(0, 64248.91);
        c.addPayment(3, 20000.00);
        //c.addPayment(1, 5000.20);
        //c.addPayment(2, 4247.22);
        //c.addPayment(3, 5000.17);

        answer = this.device.printCheck(c);
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        this.device.cut(1);

        // Проверка cashOutput ( на 1000 рублей )
        answer = this.device.cashOutput(100000);
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        this.device.cut(1);

        // Проверка cashInput ( на 1000 рублей )
        answer = this.device.cashInput(100000);
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        this.device.cut(1);

        // Проверка beep
        answer= this.device.beep();
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        // Проверка закрытия смены
        answer = this.device.closeSession();
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        this.device.cut(1);

        // Проверка метода cut
        answer = this.device.cut(1);
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        // Печать длинной строки
        this.device.printLongString(2, 39, lorem);

        this.device.broach(2, 10);

        this.device.printLongString(2, 39, lorem);

        this.device.broach(2, 10);
        this.device.cut(1);

        // Проверка получения статуса
        answer = this.device.getStatus();
        error_code = Integer.parseInt(answer.get("error_code").toString());
        assertEquals(error_code, 0);

        assertEquals("Hello", this.device.sayHelloToTest());
    }
}
