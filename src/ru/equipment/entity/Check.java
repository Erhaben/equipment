package ru.equipment.entity;
import ru.equipment.tools.Convert;

import java.util.ArrayList;

public class Check
{
    public class Product
    {
        public String title;
        public int count;
        public double price;
        public int[] taxes = new int[4];

        public Product(String title, int count, double price, int[]t)
        {
            this.title = title;
            this.count = count;
            this.price = price;
            this.taxes = t;
        }

        public char[] getSaleParams() throws Exception
        {
            char[] params = new char[55];

            // Массивы параметров
            char[] count_array = Convert.toCharArray(new Double(this.count * 1000).intValue());
            char[] price_array = Convert.toCharArray(new Double(this.price * 100).intValue());
            char[] title_array = Convert.toCP1251(this.title);
            char[] tax_array = Convert.toCharArray(this.taxes);

            for(int count_iter = 0; count_iter < count_array.length; count_iter++)
                params[count_iter] = count_array[count_iter];

            for(int price_iter = 0; price_iter < price_array.length; price_iter++)
                params[price_iter + count_array.length] = price_array[price_iter];

            params[10] = (char)0;

            for(int tax_iter = 0; tax_iter < tax_array.length; tax_iter++)
                params[10 + tax_iter] = tax_array[tax_iter];

            for(int title_iter = 0; title_iter < title_array.length; title_iter++)
                params[15 + title_iter] = title_array[title_iter];

            return params;
        }
    }

    /* Налоги */
    public int[] taxes = new int[]{0,0,0,0};

    /* Типы чеков */
    private int type = 0;

    /* Массив товаров */
    private ArrayList<Product> products = new ArrayList<Product>();

    /* Массив с типами оплат */
    private ArrayList<Double> payed = new ArrayList<Double>();

    /* Скидка / надбавка */
    private double discount = 0.0;

    public boolean setType(int type)
    {
        boolean result = true;

        if (type >= 0 && type <= 3)
            this.type = type;
        else
            result = false;

        return result;
    }

    public void setDiscount(double discount)
    {
        this.discount = discount;
    }

    public boolean addProduct(String title, int count, double price)
    {
        if (title.isEmpty())
            return false;

        if (count <= 0)
            return false;

        if (price <= 0)
            return false;

        if (taxes.length > 4)
            return false;

        Product product = new Product(title, count, price, this.taxes);
        this.products.add(product);

        return true;
    }

    public void clean()
    {
        this.products = new ArrayList<Product>();
    }

    public boolean removeProduct(int index)
    {
        boolean result = true;

        if (this.products.size() >= index)
            this.products.remove(index);
        else
            result = false;

        return result;
    }

    public Product getProduct(int index)
    {
        return this.products.get(index);
    }

    public boolean addPayment(int index, double amount)
    {
        boolean result = true;

        this.payed.add(index, amount);

        return result;
    }

    public boolean removePayment(int index)
    {
        boolean result = true;

        if (this.payed.size() >= index)
            this.payed.remove(index);
        else
            this.payed.remove(index);

        return result;
    }

    public void cleanPayments()
    {
        this.payed = new ArrayList<Double>();
    }

    public int getType()
    {
        return this.type;
    }

    public int getCommand() throws Exception
    {
        int command = 0;
        switch (this.getType())
        {
            case 0:
                command = 0x80;
                break;
            case 1:
                command = 0x81;
                break;
            case 2:
                command = 0x82;
                break;
            case 3:
                command = 0x83;
                break;
            default:
                throw new Exception("Неизвестная команда!");
        }

        return command;
    }

    public int getProductsCount()
    {
        return this.products.size();
    }

    public boolean validate() throws Exception
    {
        boolean result = true;

        if (this.products.size() == 0)
            throw new Exception("Не указаны товары");

        if (this.payed.size() == 0)
            throw new Exception("Не указаны платежи");

        for(int i = 0; i < this.taxes.length; i++)
            if(this.taxes[i] < 0 || this.taxes[i] > 4)
                throw new Exception("Неверные параметры налогов");

        double total_to_pay = 0.0;
        for(Product p : this.products )
            total_to_pay += (p.count * p.price);

        double total_payed = 0.0;
        for(Double payment : this.payed)
            total_payed += payment;

        if (Math.round(total_payed) < Math.round(total_to_pay))
            throw new Exception("Недостаточная оплата ( нужно " + Double.toString(total_to_pay) + ", заплачено "  + Double.toString(total_payed) + " )");

        return result;
    }

    public char[] getCloseParams()
    {
        char[] close_params = new char[66];

        Double[] payed = new Double[]{0.0, 0.0, 0.0, 0.0};
        System.out.println(this.payed.size());
        for (int i = 0; i < payed.length; i++)
            payed[i] = this.payed.get(i) * 100;

        char[] cash_array = Convert.toCharArray(payed[0].intValue());
        char[] f_array = Convert.toCharArray(payed[1].intValue());
        char[] s_array = Convert.toCharArray(payed[2].intValue());
        char[] t_array = Convert.toCharArray(payed[3].intValue());

        char sign = (char)0;
        char digit = (char)0;

        if ( this.discount < 0)
            sign = (char)0;

        digit = (char)(new Double(this.discount).intValue());

        char[] sale_array = new char[]{sign, digit};

        int[] total_taxes = this.taxes;

        for(int i = 0; i < cash_array.length; i++)
            close_params[i] = cash_array[i];

        for(int i = 0; i < f_array.length; i++)
            close_params[i + 5] = f_array[i];

        for(int i = 0; i < s_array.length; i++)
            close_params[i + 10] = s_array[i];

        for(int i = 0; i < t_array.length; i++)
            close_params[i + 15] = t_array[i];

        for(int i = 0; i < sale_array.length; i++)
            close_params[i + 20] = sale_array[i];

        for(int i = 0; i < total_taxes.length; i++)
            close_params[i + 22] = (char)total_taxes[i];

        return close_params;
    }

    public void setTaxes(int[] t)
    {
        this.taxes = t;
    }

    public void fromJSON(String json)
    {

    }

    public void reload()
    {
        this.products = new ArrayList<Product>();
        this.discount = 0.0;
        this.payed = new ArrayList<Double>();
        this.taxes = new int[]{0,0,0,0};
        this.type = 0;
    }

    public Check()
    {
        for(int i = 0; i <= 4; i++)
            this.payed.add(0.00);
    }
}
