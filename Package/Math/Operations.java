package Math;

import java.math.BigInteger;
import java.util.ArrayList;
import logical.promise.*;

/**
 *
 * @author Rohit TP
 */
public class Operations
{

    public static void main(String[] args)
    {
        Promise pr = new Promise(() ->
        {
            return "First Promise.";
        });
        pr.then((ret) ->
        {
            System.out.println("First then");
           return new Promise(() ->
            {
                int k = 10/0;
                return getFactorial(BigInteger.valueOf(10000));
            });
        }).then((ret) ->
        {
            System.out.println(ret);
            return new Promise(() ->
            {
                int k = 10/0;
                return getFactorial(BigInteger.valueOf(200));
            });
        }).then((ret) ->
        {
            System.out.println(ret);
            return Promise.DONE;
        });
        pr.onError((error) ->
        {
            System.err.println(error);
        });
    }

    public static int[] getFactors(int num)
    {
        return null;

    }

    /**
     * Calculates the factorial of the passed number.
     *
     * @param Number the number to find factorial of.
     *
     * @return Factorial
     *
     * @throws NumberFormatException If number is less than zero.
     */
    public static BigInteger getFactorial(BigInteger Number)
    {
        if (Number.compareTo(BigInteger.ZERO) < 0)
            throw new NumberFormatException("Number must be greater than or equal to zero.");
        return (Number.compareTo(BigInteger.ONE) == 1)
                ? (getFactorial(Number.subtract(BigInteger.ONE)).multiply(Number))
                : (BigInteger.ONE);
    }

    /**
     * Creates an ArrayList containing first n prime numbers.
     *
     * @param n The number upto which primes has to be generated
     *
     * @return The ArrayList of primes
     *
     * @throws NumberFormatException if n is less than 1.
     */
    public static ArrayList listPrimes(int n)
    {
        if (n < 1)
            throw new NumberFormatException("'n' must be greater than or equal to 1");
        ArrayList numbers = new ArrayList();
        for (int i = 0; i < n; i++)
            numbers = nextPrime(numbers);
        return numbers;
    }

    /**
     * Adds the next prime number to the ArrayList of primes passed.
     * <p>
     * If passed list is empty new ArrayList is created with 2 as first element.
     *
     * @param primes the ArrayLis of primes
     *
     * @return ArrayList of primes
     */
    public static ArrayList nextPrime(ArrayList primes)
    {
        if (primes.isEmpty())
        {
            primes = new ArrayList();
            primes.add(2);
        } else
            for (int i = (int) primes.get(primes.size() - 1);; i++)
                if (isPrime(primes, i))
                {
                    primes.add(i);
                    break;
                }
        return primes;
    }

    /**
     * Checks weather a give number is prime or not.
     * This function works based on the list of prime numbers passed.
     *
     * @param primes
     *
     * @param Number the number to check
     *
     * @return Prime or Not
     *
     * @see isPrime(int Number)
     * @throws NumberFormatException if number is less than 2
     */
    public static boolean isPrime(ArrayList primes, int Number)
    {
        if (Number < 2)
            throw new NumberFormatException("Number must be >= 2");

        boolean state = true;
        for (int i = 0; i < primes.size(); i++)
            if (Number % (int) primes.get(i) == 0)
                state = false;
        return state;
    }

    /**
     * Checks weather a give number is prime or not.
     * This function checks all numbers below the passed number so it is not
     * efficient.
     *
     * @param Number the number to check
     *
     * @return Prime or Not
     *
     * @see isPrime(ArrayList primes , int no)
     * @throws NumberFormatException if number is less than 2
     */
    public static boolean isPrime(int Number)
    {
        if (Number < 2)
            throw new NumberFormatException("Number must be >= 2");

        boolean state = true;
        if (Number % 2 == 0)
            return false;

        for (int i = 3; i < Number / 2; i += 2)
            if (Number % i == 0)
                state = false;
        return state;
    }

    /**
     *
     * This function takes the integer value passed and maps it to the passed
     * range.
     *
     * @param value
     * @param vmin  minimum range of value
     * @param vmax  maximum range of value
     * @param rmin  minimum range of returned value
     * @param rmax  maximum range of returned value
     *
     * @return Mapped Value
     */
    public static int map(int value, int vmin, int vmax, int rmin, int rmax)
    {
        double cvalue = value;
        double cvmin = vmin;
        double cvmax = vmax;
        double crmin = rmin;
        double crmax = rmax;

        double fraction = (crmax - crmin) / (cvmax - cvmin);
        return Math.round(Math.round(fraction * cvalue + rmin + 0.000));
    }

    /**
     *
     * This function takes the Double value passed and maps it to the passed
     * range.
     *
     * @param value
     * @param vmin  minimum range of value
     * @param vmax  maximum range of value
     * @param rmin  minimum range of returned value
     * @param rmax  maximum range of returned value
     *
     * @return Mapped Value
     */
    public static Double map(double value, double vmin, double vmax, double rmin, double rmax)
    {
        double fraction = (rmax - rmin) / (vmax - vmin);
        return fraction * value + rmin;
    }

    /**
     *
     * Returns the percentage value of passed number with the max value.
     * If the passed max value is zero then 0 will be returned.
     *
     * @param no
     * @param max
     *
     * @return percentage
     */
    public float Percentage(double no, double max)
    {
        if (no == 0.00)
            return 0;
        return Math.round(max / no * 100.00);
    }
}
