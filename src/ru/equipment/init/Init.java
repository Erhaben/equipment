package ru.equipment.init;
import ru.equipment.device.printer.SHTRIH_M_PTK;
import ru.equipment.entity.Check;

import java.util.HashMap;

public class Init
{
    public static void main(String arg[])
    {
        try
        {
            SHTRIH_M_PTK printer = new SHTRIH_M_PTK("ttyUSB0");
            //System.exit(0);

            /*String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam luctus volutpat quam, nec condimentum nisi interdum id. Phasellus commodo sapien vitae leo accumsan facilisis. Nam sollicitudin est ac augue aliquam vitae hendrerit enim faucibus. Maecenas in nisl sit amet lacus mattis convallis ac vitae nunc. Nullam a nunc sed dui pulvinar hendrerit ac a urna. Duis sed arcu eu neque viverra imperdiet. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Duis nec lectus magna, vel condimentum risus. Etiam ipsum lectus, egestas ut volutpat ut, faucibus in tortor. In hac habitasse platea dictumst. Nulla in velit dui. Duis vitae erat eu mi posuere pharetra ac a odio. Mauris vitae erat mattis tellus vulputate tempus.\n" +
                    "\n" +
                    "Aenean suscipit commodo felis, id gravida mi consectetur vel. In nunc quam, interdum ut pellentesque nec, tempor sed metus. Phasellus nunc orci, sollicitudin a faucibus a, sagittis quis dui. Nunc eu eros at metus fermentum malesuada quis vehicula odio. Nullam eget laoreet tellus. Fusce cursus ullamcorper ligula sed vestibulum. Vivamus congue volutpat leo non imperdiet. Phasellus sapien mi, vulputate nec mattis in, molestie a ligula. Duis in urna libero. Nunc ipsum ligula, venenatis vel ullamcorper eget, commodo at est. Suspendisse potenti. Mauris non dui tellus. Praesent accumsan tortor quam, et vehicula risus. Ut metus nisi, pulvinar quis suscipit nec, faucibus eget nulla.\n" +
                    "\n" +
                    "Nulla vel eros ultrices enim faucibus porttitor nec sollicitudin lectus. Integer lorem arcu, condimentum non eleifend non, mollis et diam. Aliquam blandit congue justo. Donec sed aliquet augue. Praesent ac enim eget leo faucibus placerat. Donec quis sem risus. Aliquam vestibulum aliquet lorem id cursus. Suspendisse ac augue nunc, non congue tortor.\n" +
                    "\n" +
                    "Donec ut purus nunc. Suspendisse feugiat, justo a aliquet convallis, dolor ligula luctus odio, eget iaculis nulla mi eget nulla. Phasellus et massa augue, at cursus nulla. Nam eu lacus sapien, ac faucibus nibh. Curabitur laoreet pretium dui, ut tempus mauris consectetur at. Proin ante eros, ornare et pellentesque quis, tincidunt id erat. Cras non justo non nunc scelerisque elementum quis at nulla. Phasellus quam leo, sagittis et auctor sit amet, auctor id neque. Nulla sed nisi et nunc viverra luctus. Aenean bibendum vehicula magna eu varius.\n" +
                    "\n" +
                    "Maecenas venenatis adipiscing tortor, id porta elit pulvinar eu. Proin et nibh luctus odio tincidunt pulvinar sit amet et arcu. Vestibulum magna neque, adipiscing et ullamcorper nec, luctus quis tellus. Mauris sem quam, scelerisque imperdiet scelerisque nec, facilisis nec turpis. Nulla vitae quam neque, quis sodales nibh. Maecenas adipiscing mauris cursus leo auctor a blandit enim vestibulum. Suspendisse eget rhoncus felis. Etiam volutpat erat ac nulla eleifend sodales. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Maecenas est lacus, tempor et volutpat vitae, interdum id quam. Nam risus quam, posuere at semper id, suscipit eget elit. Nulla facilisi. Curabitur tincidunt dapibus varius. Proin at est vel eros mollis fermentum. Vivamus tristique suscipit justo, aliquam varius magna condimentum quis.";

            Check c = new Check();
            c.setType(0);
            c.setTaxes(new int[]{0,0,0,0});
            c.setDiscount(0.0);

            c.addProduct("Первый товар", 1, 22.3);

            c.addPayment(3, 50.0);


            HashMap answer = printer.printCheck(c);*/
            //HashMap answer = printer.cashOutput(100000);
            //printer.cut(1);
            //printer.beep();
            //printer.beep();
            //printer.broach(2, 10);
            //printer.beep();      */
            System.out.println(printer.continuePrint().get("error_code"));
            System.out.println(printer.cut(0).get("error_code"));
            HashMap answer = printer.cancelCheck();
            System.out.println(answer.get("error_code"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
