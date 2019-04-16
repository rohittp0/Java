package logical;

import Data.Management.Format;
import Data.Stored.WordList;
import java.text.DecimalFormat;

/**
 *
 * @author Rohit TP
 */
public class Converter
{
    private static final String[] numNames =
    {
        "",
        " one",
        " two",
        " three",
        " four",
        " five",
        " six",
        " seven",
        " eight",
        " nine",
        " ten",
        " eleven",
        " twelve",
        " thirteen",
        " fourteen",
        " fifteen",
        " sixteen",
        " seventeen",
        " eighteen",
        " nineteen"
    };

    private static final String[] tensNames =
    {
        "",
        " ten",
        " twenty",
        " thirty",
        " forty",
        " fifty",
        " sixty",
        " seventy",
        " eighty",
        " ninety"
    };


    /**
     * Takes a double value and returns the string that is the English
     * translation.
     *
     * @param number
     * @return
     */
    public static String numToText(double number)
    {
        String pre = number < 0 ? "Minus " : "";
        number = number < 0 ? -number : number;
        String no = number + "";

        char[] c = new char[no.indexOf(".")];
        no.getChars(0, no.indexOf("."), c, 0);
        long whole = Long.parseLong(Format.makeString(c));

        c = new char[no.length() - no.indexOf(".")];
        no.getChars(no.indexOf(".") + 1, no.length(), c, 0);
        long deci = Long.parseLong(Format.makeString(c));

        return pre + numToText(whole) + " point" + deciText(deci);
    }

    /**
     *
     * Takes a long value and returns the string that is the English
     * translation.
     *
     * @param number
     * @return The string representation of the passed number.
     */
    public static String numToText(long number)
    {
        // -999 999 999 999 to 999 999 999 999
        String pre = "";
        if (number == 0)
        {
            return "zero";
        }

        if (number < 0)
        {
            pre = "Minus ";
        }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions)
        {
            case 0:
                tradBillions = "";
                break;
            case 1:
                tradBillions = convertLessThanOneThousand(billions)
                        + " billion ";
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions)
                        + " billion ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions)
        {
            case 0:
                tradMillions = "";
                break;
            case 1:
                tradMillions = convertLessThanOneThousand(millions)
                        + " million ";
                break;
            default:
                tradMillions = convertLessThanOneThousand(millions)
                        + " million ";
        }
        result = result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands)
        {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "one thousand ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                        + " thousand ";
        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        // remove extra spaces!
        return pre + result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }
    /**
     * Returns the float value in the passed String.
     *
     * @param number
     * @return 0 if String contains non numeric characters other than '.'
     */
    public static float safeFloat(String number)
    {
        return safeDouble(number).floatValue();
    }
    /**
     * Returns the integer value in the passed String.
     *
     * @param number
     * @return 0 if String contains non numeric characters other than '.'
     */
    public static int safeInt(String number)
    {
        return safeDouble(number).intValue();
    }

    private static String convertLessThanOneThousand(int number)
    {
        String soFar;

        if (number % 100 < 20)
        {
            soFar = numNames[number % 100];
            number /= 100;
        } else
        {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0)
        {
            return soFar;
        }
        return numNames[number] + " hundred" + soFar;
    }

    private static String deciText(long deci)
    {
        StringBuilder str = new StringBuilder();
        String no = deci + "";
        for (int i = 0; i < no.length(); i++)
        {
            int n = Integer.parseInt(no.charAt(i) + "");
            if (n == 0)
            {
                str.append("0").append(" ");
            } else
            {
                str.append(numNames[n]).append(" ");
            }
        }
        return str.toString();
    }
    
    /**
     * Returns the Double value in the passed String.
     * 
     * @param number
     * @return 0 if String contains non numeric characters other than '.'
     */
    private static Double safeDouble(String number)
    {
        double ret = 0;
        
        if(number==null)return ret;
        
        for (char c : WordList.LETTERS)
        {
            if (number.contains(c + ""))
            {
                return ret;
            }
        }
        for (char c : WordList.SYMBOLS)
        {
            if (number.contains(c + "") && c != '.')
            {
                return ret;
            }
        }
        try
        {
            ret = Float.valueOf(number);
        } catch (NumberFormatException er)
        {
            return ret;
        }

        return ret;
    }
}
