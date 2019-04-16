package logical;

import java.awt.Toolkit;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author Rohit TP
 */
public class Errors 
{
    private List<Exception> errorList=new ArrayList<>();
    private List<String> cause = new ArrayList<>();
    private List<Long> time = new ArrayList<>();
    
    private long Start=0;
    private JTextArea outLabel;
    
    public Errors()
    {
        Start=System.currentTimeMillis();
    }
    
    public void setOut(JTextArea outLabel)
    {
        this.outLabel=outLabel;
        outLabel.setEditable(false);
    }
    
    public void addError(Exception er)
    {
        errorList.add(er);
        findCause(er);
        Toolkit.getDefaultToolkit().beep();
    }
    
    public Exception[] getError()
    {
        return (Exception[]) errorList.toArray();
    }
    
    public String[] getCause()
    {
        return (String[]) cause.toArray();
    }

    private void findCause(Exception er)
    {
        try
        {
            cause.add(er.getCause().toString());
        }
        catch(Exception e)
        {
            cause.add(er.toString());
        }
        time.add((System.currentTimeMillis()-Start));
        
        if(outLabel==null)return;        
        
        String error= cause.isEmpty() ? er.toString() : cause.get(cause.size()-1);
        
        if(outLabel.getText().equals(""))outLabel.setText(outLabel.getText()+"\n-> "+error+
                " -> Time : "+time.get(time.size()-1)+" MilliSeconds");
        else outLabel.setText("-> "+error+" -> Time : "+time.get(time.size()-1)+
                " MilliSeconds");
    }
}
