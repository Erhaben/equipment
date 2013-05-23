package ru.equipment.tools;

import java.util.ArrayList;

public class Convert
{
    public static char toBinary(int digit) {
        String s = Integer.toBinaryString(digit);
        return (char)Integer.parseInt(s, 2);
    }

    public static char[] toCharArray(int integer)
    {
        char[] params = new char[5];
        byte[] bytes = java.nio.ByteBuffer.allocate(4).putInt(integer).array();

        for(int i = 0; i < bytes.length; i++)
            params[i] = (char)bytes[(bytes.length - 1) - i];
        params[4] = (char)0;

        return params;
    }

    public static char[] toCharArray(int[] a)
    {
        char[]res = new char[a.length];

        for(int i = 0; i < a.length; i++)
            res[i] = (char)a[i];

        return res;
    }

    public static char[] toCharArray(ArrayList<Integer> list)
    {
        char[] result = new char[list.size()];

        for(int i = 0; i < list.size(); i++)
            result[i] = (char)list.get(i).intValue();

        return result;
    }

    public static char[] merge(char[] first, char[] second)
    {
        char[] merged = new char[first.length + second.length];

        for(int i = 0; i < first.length; i++)
            merged[i] = first[i];

        for(int b = 0; b < second.length; b++)
            merged[b + first.length - 1] = second[b];

        return merged;
    }

    public static char[] toCP1251(String s) throws Exception
    {
        byte[] in_cp1251 = s.getBytes(Encoding.CP1251);
        char[] params = new char[in_cp1251.length];

        for (int i = 0; i < s.length(); i++)
            params[i] = (char)in_cp1251[i];

        return params;
    }

    public static char[] getSubarray(int from, int to, char[] source)
    {
        char[] result = new char[to - from];

        int index = 0;
        for(int i = from; i <= to; i++)
        {
            result[index] = source[i];
            index += 1;
        }

        return result;
    }
}
