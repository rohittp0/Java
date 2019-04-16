package Data.Management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Rohit TP
 */
public class IO
{
    
    /**
     * Filename Filter to filter files ending with extension .txt.
     */
    public static FilenameFilter txt_Filter=(File dir, String filename) -> filename.endsWith(".txt");
    
    /**
     * Filename Filter to filter files ending with extension .book.
     */
    public static FilenameFilter book_Filter=(File dir, String filename) -> filename.endsWith(".book");
    
    /**
     * Creates the passed file and if needed the parent directories of the file.
     * <br> If the file already exists it is first deleted.
     *
     * @param file The file to be created.
     * @throws IOException If an I/O error occurred.
     */
    public static void createFile(File file) throws IOException
    {
        if (file.exists())
        {
            deleteFile(file.getAbsolutePath());
        }
        if(file.isFile())
        {
            if(file.getParentFile().exists())file.getParentFile().mkdirs();
            file.createNewFile();
        }
        else file.mkdirs();
    }

    /**
     * Deletes the passed File/Folder.
     *
     * @param file The File/Folder to be deleted.
     */
    public static void deleteFile(String file) throws IOException
    {
        Files.walk(Paths.get(file))
                .map(Path::toFile)
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach(File::delete);
    }

    /**
     *
     * Converts the passed File/Folder to zip file.
     *
     * @param file File/Folder to compress.
     * @param zipfile Output File.
     *
     * @throws IOException
     */
    public static void zip(File inputfile, File outputFile) throws IOException
    {
        // out put file
        try (FileInputStream in = new FileInputStream(inputfile); // out put file
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputFile)))
        {
            
            // name the file inside the zip  file
            out.putNextEntry(new ZipEntry(inputfile.getName()));
            
            // buffer size
            byte[] b = new byte[1024];
            int count;
            
            while ((count = in.read(b)) > 0) {
                out.write(b, 0, count);
            }
        }
    }

    /**
     * Creates the file with passed path and if needed the parent directories of
     * the file.
     *
     * @param path The path of the file to be created.
     * @throws IOException If an I/O error occurred.
     */
    public static File createFile(String path) throws IOException
    {
        createFile(new File(path));
        return new File(path);
    }

    /**
     * Writes each element of the passed ArrayList to the file at the path
     * specified.
     *
     * @param file The file to write to
     * @param Words The ArrayList to write.
     * @throws IOException If an I/O error occurred.
     */
    public static void Write(File file, ArrayList<Object> Words) throws IOException
    {
        try (PrintWriter print = new PrintWriter(new BufferedWriter(new FileWriter(file))))
        {
            Words.forEach((Object obj) ->
            {
                print.print(obj);
            });
        }
    }

    /**
     * Writes each element of the passed ArrayList to the file at the path
     * specified and writes the passed line break after each element.
     *
     * @param file The file to write to
     * @param Words The ArrayList to write.
     * @throws IOException If an I/O error occurred.
     */
    public static void Write(File file, ArrayList<Object> Words, String lineBreak) throws IOException
    {
        try (PrintWriter print = new PrintWriter(new BufferedWriter(new FileWriter(file))))
        {
            Words.forEach((Object obj) ->
            {
                print.print(obj);
                print.print(lineBreak);
            });
        }
    }

    /**
     * Writes each element of the passed ArrayList to the file at the path
     * specified.
     *
     * @param path The string containing the path of the file
     * @param Words The ArrayList to write.
     * @throws IOException If an I/O error occurred.
     */
    public static void Write(String path, ArrayList<Object> Words) throws IOException
    {
        Write(new File(path), Words);
    }

    /**
     * Writes each element of the passed ArrayList to the file at the path
     * specified and writes the passed line break after each element.
     *
     * @param path The string containing the path of the file
     * @param Words The ArrayList to write.
     * @throws IOException If an I/O error occurred.
     */
    public static void Write(String path, ArrayList<Object> Words, String lineBreak) throws IOException
    {
        Write(new File(path), Words, lineBreak);
    }

    /**
     * This function reads the file at the passed path line by line to a new
     * ArrayList.
     *
     * @param path The string containing the path of the file.
     * @return The ArrayList<String> containing the read data.
     * @throws MalformedURLException If the path is invalid.
     * @throws IOException If the file can't be read.
     */
    public static List<String> Read(String path) throws MalformedURLException, IOException, InterruptedException
    {
        return path.startsWith("https:") ? Read(new URL(path))
                : Read(new File(path));
    }

    /**
     * This function reads the file passed line by line to a new ArrayList.
     *
     * @param file The file to read from.
     * @return The ArrayList<String> containing the read data.
     * @throws MalformedURLException If the path is invalid.
     * @throws IOException If the file can't be read.
     */
    public static ArrayList<String> Read(File file) throws IOException
    {
        ArrayList<String> Words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String word = br.readLine();
            while (word != null)
            {
                Words.add(word);
                word = br.readLine();
            }
        }
        Words.trimToSize();
        return Words;
    }

    /**
     * This function reads the file at the passed URL line by line to a new
     * ArrayList.
     *
     * @param url The URL of the file.
     * @return The ArrayList<String> containing the read data.
     * @throws MalformedURLException If the path is invalid.
     * @throws IOException If the file can't be read.
     */
    public static List<String> Read(URL url) throws IOException, InterruptedException
    {
        Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors()];
        List<String> Words = Collections.synchronizedList(new ArrayList<>());
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream())))
        {
            for (int i = 0; i < threads.length; i++)
            {
                threads[i] = new Thread()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            for (String word = br.readLine(); word != null; word = br.readLine())
                            {
                                Words.add(word);
                            }
                        } catch (IOException ex)
                        {
                            Words.add(ex.getMessage());
                        }
                    }
                };
                threads[i].start();
            }
            for (Thread thread : threads)
            {
                thread.join();
            }
        }
        Runtime.getRuntime().gc();

        return Words;
    }
}
