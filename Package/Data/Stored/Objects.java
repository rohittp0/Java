package Data.Stored;

import Data.Stored.Element;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import science.util.Object;

/**
 *
 * @author Rohit TP
 */
public class Objects
{

    /**
     * Array of All elements of periodic table as objects;
     */
    public final Object[] elements;

    /**
     *
     */
    public Objects()
    {
        Element ele = new Element();
        elements = new Object[ele.elements.length];
        for (int i = 0; i < ele.elements.length; i++)
        {
            elements[i] = new Object(ele, i);
        }
    }

    /**
     *
     * Saves The passed objects to a file.
     *
     * @param obj
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void addObject(Object obj) throws IOException, ClassNotFoundException
    {
        if (obj == null)
        {
            return;
        }
        File file = new File("D:\\Objects\\");
        if (!file.exists())
        {
            file.mkdirs();
        }
        file = new File("D:\\Objects\\" + obj.getName() + ".txt");
        file.createNewFile();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)))
        {
            out.writeObject(obj);
        }
    }

    /**
     *
     * @return ObjectArray
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object[] getObjects() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        Object[] obj=null;
        File file = new File("D:\\Objects\\");
        if (!file.exists())
        {
            throw new FileNotFoundException("Root directory not found");
        }
        File[] list = file.listFiles();
        if (list.length <= 0)
        {
            throw new FileNotFoundException("No objects found in root directory");
        }
        obj=new Object[list.length];
        for (int i=0;i<list.length;i++)
        {
            try (ObjectInputStream ob = new ObjectInputStream(new FileInputStream(list[i])))
            {
                obj[i] = (Object) ob.readObject();
            }
        }
        return obj;
    }
}
