package Swing;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class SelectionManager extends PlainDocument
{
    private final JComboBox comboBox;
    private final ComboBoxModel model;
    private final JTextComponent editor;
    
    boolean editable=true;
    // flag to indicate if setSelectedItem has been called
    // subsequent calls to remove/insertString should be ignored
    boolean selecting = false;

    public SelectionManager(final JComboBox comboBox)
    {
        this.comboBox = comboBox;
        model = comboBox.getModel();
        editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
        editor.setDocument(this);
        comboBox.addActionListener((ActionEvent e) ->
        {
            if (!selecting)
            {
                highlightCompletedText(0);
            }
        });
        editor.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (comboBox.isDisplayable())
                {
                    comboBox.setPopupVisible(true);
                }
            }
        });
        // Handle initially selected object
        Object selected = comboBox.getSelectedItem();
        if (selected != null)
        {
            setText(selected.toString());
        }
        highlightCompletedText(0);
    }

    /**
     *
     * @param offs
     * @param len
     * @throws BadLocationException
     */
    @Override
    public void remove(int offs, int len) throws BadLocationException
    {
        // return immediately when selecting an item
        if (selecting)
        {
            return;
        }
        super.remove(offs, len);
    }

    /**
     *
     * @param offs
     * @param str
     * @param a
     * @throws BadLocationException
     */
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
    {
        // return immediately when selecting an item
        if (selecting)
        {
            return;
        }
        // insert the string into the document
        super.insertString(offs, str, a);
        // lookup and select a matching item
        Object item = lookupItem(getText(0, getLength()));
        if (item != null)
        {
            setSelectedItem(item);
        } else if(!editable)
        {
            // keep old item selected if there is no match
            item = comboBox.getSelectedItem();
            //imitate no insert (later on offs will be incremented by str.length(): selection won't move forward)
            offs = offs - str.length();
            // provide feedback to the user that his input has been received but can not be accepted
            comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
        } else
        {
        }
        if (item != null)setText(item.toString());
        // select the completed part
        highlightCompletedText(offs + str.length());
    }

    private void setText(String text)
    {
        try
        {
            // remove all text and insert the completed string
            super.remove(0, getLength());
            super.insertString(0, text, null);
        } catch (BadLocationException e)
        {
            throw new RuntimeException(e.toString());
        }
    }

    private void highlightCompletedText(int start)
    {
        editor.setCaretPosition(getLength());
        editor.moveCaretPosition(start);
    }

    private void setSelectedItem(Object item)
    {
        selecting = true;
        model.setSelectedItem(item);
        selecting = false;
    }

    private Object lookupItem(String pattern)
    {
        Object selectedItem = model.getSelectedItem();
        // only search for a different item if the currently selected does not match
        if (selectedItem != null && startsWithIgnoreCase(selectedItem.toString(), pattern))
        {
            return selectedItem;
        } else
        {
            // iterate over all items
            for (int i = 0, n = model.getSize(); i < n; i++)
            {
                Object currentItem = model.getElementAt(i);
                // current item starts with the pattern?
                if (startsWithIgnoreCase(currentItem.toString(), pattern))
                {
                    return currentItem;
                }
            }
        }
        // no item starts with the pattern => return null
        return null;
    }

    // checks if str1 starts with str2 - ignores case
    private boolean startsWithIgnoreCase(String str1, String str2)
    {
        return str1.toUpperCase().startsWith(str2.toUpperCase());
    }
    
    /**
     * sets weather JComboBox can get new item as input. 
     * @param edit
     * 
     */
    public void setEditable(boolean edit)
    {
        this.editable=edit;
    }
    
    /**
     *
     * @return editable
     */
    public boolean isEditable()
    {
        return editable;
    }
}
