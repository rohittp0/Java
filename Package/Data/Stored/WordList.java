package Data.Stored;

import Data.Management.IO;
import Data.Management.Sort;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Rohit TP
 */
public class WordList
{

    /**
     * Load words from
     * https://raw.githubusercontent.com/dwyl/english-words/master/words.txt .
     * These words may contain numbers and special characters.
     */
    public static final String ALL_WORDS = "https://raw.githubusercontent.com/dwyl/english-words/master/words.txt";

    /**
     * Load words from
     * https://raw.githubusercontent.com/dwyl/english-words/master/words_alpha.txt
     * . The loaded words are strictly alphabets only.
     */
    public static final String ALPHA_WORDS = "https://raw.githubusercontent.com/dwyl/english-words/master/words_alpha.txt";
    
    /**
     * Collection of all English alphabets.
     * <br> <u>Note:</u> Lower case only. Please use toUppercase if needed.
     */
    public static final char[] LETTERS = 
    {
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r',
        's','t','u','v','w','x','y','z'        
    };
    
    /**
     * Collection of all Symbols.
     * <br>
     * <u>Symbols are</u> :
     * <br>
     * . , , , < , > , ? , / , '  , " , ; , : , [ , ] , { , } , | , \ , + , = ,
     *   - , _ , ( , ) , * , & , ^ , % , $ , # , @ , ! , ~ , ` 
     */
    public static final char[] SYMBOLS = 
    {
        '.',',','<','>','?','/','\'','"',';',':','[',']','{','}','|','\\','+','=',
        '-','_','(',')','*','&','^','%','$','#','@','!','~','`'        
    };
    
    private final List<String> Words;

    private Thread[] Threads = new Thread[Runtime.getRuntime().availableProcessors()];

    private int[] parts = new int[Threads.length + 2];
    private int repeat = 0;

    private boolean Repeatable = true;
    private boolean match = false;
    private boolean found=false;

    /**
     *
     * Creates a new WordList with words from the file passed.
     *
     * words in the file should be separated with '\n' , '\r' or equivalent
     * operator.
     *
     * @param path The path of the file(web address) with words.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public WordList(String path) throws FileNotFoundException, IOException, MalformedURLException, InterruptedException
    {
        Words = Collections.synchronizedList(IO.Read(path));
        divide();
    }

    /**
     *
     * Creates a new WordList with elements from the passed ArrayList.
     *
     * @param words The ArrayList containing the words.
     */
    public WordList(ArrayList<String> words)
    {
        Words = Collections.synchronizedList(words);
        divide();
    }

    /**
     * Creates an empty WordList.
     */
    public WordList()
    {
        Words = Collections.synchronizedList(new ArrayList<>());
        divide();
    }

    /**
     *
     * Creates a new WordList with elements from the passed Array.
     *
     * @param words The Array containing the words.
     */
    public WordList(String[] words)
    {
        Words = Collections.synchronizedList(new ArrayList<>());
        Words.addAll(Arrays.asList(words));
        divide();
    }

    /**
     * Removes all words from the WordList.
     */
    public void Clear()
    {
        Words.removeAll(Words);
        divide();
    }

    /**
     *
     * Appends the specified word to the end of this WordList (optional
     * operation).
     *
     * @throws UnsupportedOperationException - if the add operation is not
     * supported by this list
     * @throws ClassCastException - if the class of the specified element
     * prevents it from being added to this list
     * @throws NullPointerException - if the specified element is null and this
     * list does not permit null elements
     * @throws IllegalArgumentException - if some property of this element
     * prevents it from being added to this list
     *
     * @param word The word to add.
     * @return
     */
    public boolean addWord(String word) throws UnsupportedOperationException, ClassCastException, NullPointerException, IllegalArgumentException
    {
        if (!Repeatable && Words.indexOf(word) != -1)
        {
            return false;
        }
        boolean stat = Words.add(word);
        divide();
        return stat;
    }

    /**
     *
     * Counts the number of times each element is repeated in the WordList.
     *
     * Note : This is not the number of distinct words repeated it is the sum of
     * all repeats.
     *
     * @return Number of repeats
     */
    public int countRepeat() throws InterruptedException
    {
        repeat = 0;
        for (int j = 0; j < Threads.length; j++)
        {
            final int i = j;
            Threads[i] = new Thread()
            {
                @Override
                public void run()
                {
                    for (int j = parts[i]; j < parts[i + 1]; j++)
                    {
                        repeat += countRepeat(Words.get(j));
                    }
                }
            };
            Threads[i].start();
        }
        for (Thread t : Threads)
        {
            t.join();
        }

        return repeat;
    }

    /**
     *
     * Counts the number of times the passed word is repeated in the WordList.
     *
     * @param word The word to check for repeat.
     * @return Number of repeats or zero if only one instance is found.
     */
    public int countRepeat(String word)
    {
        return Collections.frequency(Words, word) - 1;
    }

    public List<String> getList()
    {
        return Words;
    }

    /**
     * Returns a random word from the WordList.
     *
     * @return Random word.
     */
    public String getRandom()
    {
        return Words.get(new Random().nextInt(Words.size()));
    }

    /**
     * Returns a random word from the WordList that is in the range -1 to limit.
     *
     * @param limit The maximum index of the word
     * @return
     * @throws IllegalArgumentException
     */
    public String getRandom(int limit) throws IllegalArgumentException
    {
        if (limit > Words.size())
        {
            throw new IllegalArgumentException("Passed limit (" + limit + ") > size of WordList(" + Words.size() + ").");
        }
        if (limit < 0)
        {
            throw new IllegalArgumentException("Passed limit (" + limit + ") < mimimum value (0).");
        }
        return Words.get(new Random().nextInt(limit));
    }

    /**
     * Returns weather there can be multiple instances of same word in this
     * WordList.
     *
     * @return the Repeatable
     */
    public boolean isRepeatable()
    {
        return Repeatable;
    }

    /**
     * Sets weather there can be multiple instances of same word in this
     * WordList.
     *
     * @param Repeatable the Repeatable to set
     */
    public void setRepeatable(boolean Repeatable) throws InterruptedException
    {
        this.Repeatable = Repeatable;
        if (!Repeatable)
        {
            this.removeRepeat();
        }
    }

    /**
     *
     * Checks for repeats in the WordList.
     *
     * @return true if found else false.
     */
    @SuppressWarnings("UnnecessaryReturnStatement")
    public boolean isRepeated()
    {
        found=false;
        Words.forEach((String word) ->
        {
            if (Collections.frequency(Words, word) != 1)
            {
                found=true;
            }
        });

        return found;
    }

    /**
     *
     * Checks weather the passed word is repeated in the WordList.
     *
     * @param word to check for repeat.
     * @return true if found else false.
     */
    public boolean isRepeated(String word)
    {
        return Collections.frequency(Words, word) != 1;
    }

    /**
     *
     * Checks weather the passed word is in this WordList.
     *
     * The case and leading and trailing white spaces are ignored by calling
     * String.toLowerCase() and String.trim().
     *
     * @param word The word to check for.
     * @return true if word belongs to this WordList else false.
     * @see isWordExact(String word)
     */
    public boolean isWord(String word) throws InterruptedException
    {
        match = false;
        for (repeat = 0; repeat < Threads.length; repeat++)
        {
            final int i = repeat;
            Threads[i] = new Thread()
            {
                @Override
                public void run()
                {
                    for (int j = parts[i]; j < parts[i + 1]; j++)
                    {
                        String Word = Words.get(j).toLowerCase().trim();
                        if (Word == null ? word.toLowerCase().trim() == null : Word.equals(word.toLowerCase().trim()))
                        {
                            match = true;
                        }
                    }
                }
            };
            Threads[i].start();
        }
        for (Thread t : Threads)
        {
            t.join();
        }

        return match;
    }

    /**
     *
     * Checks weather the passed word is in this WordList.
     *
     * This method is case sensitive.
     *
     * @param word The word to check for.
     * @return true if word belongs to this WordList else false.
     * @see isWord(String word)
     */
    public boolean isWordExact(String word)
    {
        return Words.indexOf(word) != -1;
    }

    /**
     * Removes the word at the Index passed from this WordList.
     *
     * @param index The Index of the word.
     */
    public void remove(int index)
    {
        Words.remove(index);
        divide();
    }

    /**
     * Removes the passed word from the WordList.
     *
     * @param word The word to remove.
     */
    public void remove(String word)
    {
        Words.remove(word);
        divide();
    }

    /**
     *
     * Removes all elements that are repeated from the WordList leaving only one
     * instance.
     *
     * @return weather repeats were found or not.
     */
    public boolean removeRepeat() throws InterruptedException
    {
        int size = Words.size();

        for (repeat = 0; repeat < Threads.length; repeat++)
        {
            final int i = repeat;
            Threads[i] = new Thread()
            {
                @Override
                public void run()
                {
                    for (int j = parts[i]; j < parts[i + 1]; j++)
                    {
                        if (Collections.frequency(Words, Words.get(j)) > 1)
                        {
                            Words.remove(Words.get(j));
                        }
                    }
                }
            };
            Threads[i].start();
        }
        for (Thread t : Threads)
        {
            t.join();
        }

        boolean stat = Words.size() != size;

        if (stat)
        {
            divide();
        }

        return stat;
    }

    /**
     *
     * Searches this WordList for the passed word and returns an ArrayList
     * containing the results sorted according to the similarity with the passed
     * word.
     * <br><br>
     * <u>NOTE:</u> The sorting is done with
     * Package.Data.Management.Sort.distanceSort
     *
     * @param word The word to search for.
     * @return Results of the search as an ArrayList.
     * @throws InterruptedException
     * @throws IllegalArgumentException If passed word in null.
     */
    public ArrayList<String> search(String word) throws IllegalArgumentException, InterruptedException
    {
        if (word == null || "".equals(word))
        {
            throw new IllegalArgumentException("Search queary can't be null.");
        }
        ArrayList<String> result = new ArrayList<>();
        for (repeat = 0; repeat < Threads.length; repeat++)
        {
            final int i = repeat;
            Threads[i] = new Thread()
            {
                @Override
                public void run()
                {
                    for (int j = parts[i]; j < parts[i + 1]; j++)
                    {
                        String Word = Words.get(j).trim().toLowerCase();
                        if (Word != null && Word.contains(word.toLowerCase().trim()))
                        {

                            result.add(Word);
                        }
                    }
                }
            };
            Threads[i].start();
        }
        for (Thread t : Threads)
        {
            t.join();
        }

        return Sort.distanceSort(word, result);
    }

    /**
     * Sorts the WordList in alphabetic order.
     */
    public void sortWords()
    {
        Collections.sort(Words);
    }

    /**
     * Sorts the WordList according to the the passed comparator.
     *
     * @param comparator The Comparator<String>.
     */
    public void sortWords(Comparator<String> comparator)
    {
        Collections.sort(Words, comparator);
    }

    /**
     *
     * Returns the size of the WordList.
     *
     * @return Size of the WordList.
     */
    public int getSize()
    {
        return Words.size();
    }

    private void divide()
    {
        Threads = new Thread[Runtime.getRuntime().availableProcessors()];
        parts = new int[Threads.length + 1];

        parts[0] = 0;
        for (int i = 1; i < Threads.length + 1; i++)
        {
            parts[i] = Words.size() / (Threads.length - i + 1);
        }
    }
}
