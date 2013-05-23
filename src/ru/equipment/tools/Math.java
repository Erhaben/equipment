package ru.equipment.tools;

public class Math
{
    public static byte[] StringToByte()
    {
        return new byte[]{};
    }

    public static String ByteToString(int[] bytes)
    {
        String answer = "";
        for (int b : bytes)
            answer += (char)b;
        return  answer;
    }

    public static String ByteToString(byte[] bytes)
    {
        String answer = "";
        for (int b : bytes)
            answer += (char)b;
        return  answer;
    }

    public static String ByteToString(char [] bytes)
    {
        String answer = "";
        for (char b : bytes)
            answer += b;
        return  answer;
    }

    public static char LRC(String buff)
    {
        int result = 0;

        char[] arr = buff.toCharArray();

        for(char c : arr)
            result = result ^ (int)c;

        return (char)result;
    }
}
