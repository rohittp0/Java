package Data;

import Data.Management.IO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Rohit.T.P
 */
public class Elements
{
    public Elements(int j) throws FileNotFoundException, IOException
    {
        IO.createFile("D:\\images\\ ");
    }

    public Elements() throws IOException, InterruptedException
    {
    }

    public String[] read(int limit, File f) throws FileNotFoundException, IOException
    {
        String[] build = new String[limit];
        try (BufferedReader br = new BufferedReader(new FileReader(f)))
        {
            for (int count = 0; count < limit; count++)
            {
                build[count] = br.readLine().trim();
            }
        }
        return build;
    }

    public final void Write()
    {
        File file = new File("D:\\wordlist.txt");
        String[] current;
        try
        {
            file.createNewFile();
            try (PrintWriter writer = new PrintWriter(new FileWriter(file)))
            {
                file = new File("D:\\Java\\Package\\Language\\Words.txt");
                current = read(370099, file);
                for (int i = 0; i < current.length; i++)
                {
                    //writer.print("{");
                    writer.print('"' + current[i] + '"' + ",");
                    //if(!current[i].contains("."))current[i]=current[i]+".000";
                    //writer.println(current[i]+"},");
                }
            }
        } catch (IOException ex)
        {
            Logger.getLogger(Elements.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
