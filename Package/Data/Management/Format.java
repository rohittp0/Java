package Data.Management;

/**
 *
 * @author Your Name Rohit.T.P
 */

public class Format
{

    /**
     *
     * Returns the passed String with all of the words
     * in it starting with capital letter. (Heading Style)
     * 
     * @param text The text to format.
     * @return String The formated text.
     */
    public static String allWords(String text)
    {
        if(text==null)return null;
        
        char[] texts=text.toLowerCase()
                .trim().toCharArray();
        text=(texts[0]+"").toUpperCase();
        for(int i=1;i<texts.length;i++)
        {
            if(texts[i]==' ')
            {
                text=text+texts[i]+((texts[i+1]+"").toUpperCase());
                i++;
            }
            else text=text+texts[i];
        }
        return text;
    }
    
    /**
     *
     * Returns the passed String with all of the words after a {'.' / '!' / '?'}
     * in it starting with capital letter. (Sentence Style)
     * 
     * @param text The text to format.
     * @return String The formated text.
     */
    public static String firstWords(String text)
    {
        if(text==null)return null;
        
        char[] texts=text.toLowerCase()
                .trim().toCharArray();
        text=(texts[0]+"").toUpperCase();
        for(int i=1;i<texts.length;i++)
        {
            if(texts[i]=='.' || texts[i]=='?' || texts[i]=='!')
            {
                text=text+texts[i]+((texts[i+1]+"").toUpperCase());
                i++;
            }
            else text=text+texts[i];
        }
        return text;
    }
    
    /**
     * Creates a String using the characters of the passed array.
     * <br> Returns null if passed array is empty.
     * @param chars
     * @return The created String.
     */
    public static String makeString(char[] chars)
    {
        if(chars.length<1)return null;
        
        String text="";
        for(char cha : chars)
        {
            text +=cha;
        }
        return text.trim();
    }
    
    /**
     * Removes the character at the specified index.
     * <br> Returns null if passed string is null.
     * <br> If passed index if greater than String.length or less than 0 then
     * returns the passed string.
     * 
     * @param string
     * @param index
     * @return The new String.
     */
    public static String RemoveCharAt(String string , int index)
    {
        if(string == null || index>string.length() || index < 0) return string;
        if(string.length() == index) return string.substring(0, index);
        else if(index == 0)return string.substring(1);
        else return string.substring(0, index) + string.substring(index + 1);
    }
    
}
