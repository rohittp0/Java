/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Swing;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Your Name Rohit.T.P
 */
public class Login_SignUp
{

    /**
     * Creates new form Login_SignUp
     *
     * @throws java.lang.Exception
     */
    public boolean signUp = false;
    public boolean Done = false;

    private JFrame frame;
    private JFrame work = null;
    private JFrame signUpFrame = null;

    private String user = "User";
    private String password = "Password";

    private final String Look = "javax.swing.plaf.nimbus.NimbusLookAndFeel";

    public final void setLook(String look)
    {
        try
        {
            UIManager.setLookAndFeel(look);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e)
        {
            try
            {
                UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e2)
            {
            }
        }
    }

    public void setFrame(JFrame frame)
    {
        work = frame;
    }

    public void setSignUP(JFrame frame)
    {
        this.signUpFrame = frame;
    }

    public void setData(String userName, String passwordText)
    {
        user = userName;
        password = passwordText;
    }

    public void setDp(Image dp)
    {
        if (dp == null)
        {
            return;
        }
        ImageIcon imageIcon1 = new ImageIcon(dp.getScaledInstance(Dp.getWidth(), Dp.getHeight(), Image.SCALE_SMOOTH));
        Dp.setIcon(imageIcon1);
    }

    public Login_SignUp(Image logo, Image dp, boolean signUp)
    {
        this.signUp = signUp;
        Login_SignUp login_SignUp = new Login_SignUp();
        ImageIcon imageIcon = new ImageIcon(logo.getScaledInstance(Logo.getWidth(), Logo.getHeight(), Image.SCALE_SMOOTH));
        Logo.setIcon(imageIcon);
        ImageIcon imageIcon1 = new ImageIcon(dp.getScaledInstance(Dp.getWidth(), Dp.getHeight(), Image.SCALE_SMOOTH));
        Dp.setIcon(imageIcon1);
    }

    public Login_SignUp()
    {
        setLook(Look);
        frame = new JFrame();
        frame.setSize(590, 400);
        frame.getRootPane().setDefaultButton(Login);
        initComponents();
        Action();
        frame.setVisible(true);
        ImageIcon file = new ImageIcon("D:/Java/Package/Swing/Images/Tech.jpg");
        Image img = file.getImage();
        ImageIcon imageIcon = new ImageIcon(img.getScaledInstance(Logo.getWidth(), Logo.getHeight(), Image.SCALE_SMOOTH));
        Logo.setIcon(imageIcon);
        file = new ImageIcon("D:/Java/Package/Swing/Images/download.jpg");
        Image img1 = file.getImage();
        ImageIcon imageIcon1 = new ImageIcon(img1.getScaledInstance(Dp.getWidth(), Dp.getHeight(), Image.SCALE_SMOOTH));
        Dp.setIcon(imageIcon1);
    }

    private void Action()
    {
        Name.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void changedUpdate(DocumentEvent e)
            {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                changed();
            }

            @Override
            public void insertUpdate(DocumentEvent e)
            {
                changed();
            }

            public void changed()
            {

                if (Name.getText().equals(""))
                {
                    Login.setEnabled(false);
                } else
                {
                    Login.setEnabled(true);
                }
                if (Name.getText().equals(user))
                {
                    Login.setBackground(new java.awt.Color(60, 150, 90));
                    Login.setForeground(new java.awt.Color(0, 149, 6));
                } else
                {
                    Login.setBackground(javax.swing.UIManager.getDefaults().getColor("nb.errorForeground"));
                    Login.setForeground(java.awt.Color.red);
                }
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents()
    {

        LogoPanel = new javax.swing.JPanel();
        Logo = new javax.swing.JLabel();
        WorkPanel = new javax.swing.JPanel();
        Name = new javax.swing.JTextField();
        NameLabel = new javax.swing.JLabel();
        Password = new javax.swing.JPasswordField();
        PasswordLabel = new javax.swing.JLabel();
        Login = new javax.swing.JButton();
        SignUp = new javax.swing.JLabel();
        Dp = new JLabel();

        SignUp.setVisible(signUp);

        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setBackground(new java.awt.Color(255, 255, 255));
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);

        LogoPanel.setBackground(new java.awt.Color(240, 180, 15));

        Logo.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout LogoPanelLayout = new javax.swing.GroupLayout(LogoPanel);
        LogoPanel.setLayout(LogoPanelLayout);
        LogoPanelLayout.setHorizontalGroup(
                LogoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 250, Short.MAX_VALUE)
        );
        LogoPanelLayout.setVerticalGroup(
                LogoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Logo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        LogoPanel.setBounds(0, 0, 250, 400);
        frame.getContentPane().add(LogoPanel);

        WorkPanel.setBackground(new java.awt.Color(0, 0, 0));

        Name.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        NameLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        NameLabel.setForeground(new java.awt.Color(255, 255, 255));
        NameLabel.setText("Name");

        PasswordLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        PasswordLabel.setForeground(new java.awt.Color(255, 255, 255));
        PasswordLabel.setText("Password");

        Login.setBackground(javax.swing.UIManager.getDefaults().getColor("nb.errorForeground"));
        Login.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Login.setForeground(java.awt.Color.red);
        Login.setText("Login");
        Login.addActionListener((java.awt.event.ActionEvent evt) ->
        {
            LoginActionPerformed(evt);
        });

        SignUp.setText("Click Here To Sign Up");
        SignUp.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                SignUpMousePressed(evt);
            }
        });

        javax.swing.GroupLayout WorkPanelLayout = new javax.swing.GroupLayout(WorkPanel);
        WorkPanel.setLayout(WorkPanelLayout);
        WorkPanelLayout.setHorizontalGroup(
                WorkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(WorkPanelLayout.createSequentialGroup()
                                .addGroup(WorkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(WorkPanelLayout.createSequentialGroup()
                                                .addGroup(WorkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(WorkPanelLayout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addComponent(NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(17, 17, 17)
                                                                .addComponent(SignUp))
                                                        .addGroup(WorkPanelLayout.createSequentialGroup()
                                                                .addGap(77, 77, 77)
                                                                .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 86, Short.MAX_VALUE))
                                        .addGroup(WorkPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(PasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(WorkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(Password)
                                                        .addComponent(Name))))
                                .addContainerGap())
                        .addGroup(WorkPanelLayout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(Dp, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        WorkPanelLayout.setVerticalGroup(
                WorkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(WorkPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(Dp, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(WorkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(WorkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(Password, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(PasswordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                                .addGap(31, 31, 31)
                                .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                                .addComponent(SignUp)
                                .addGap(37, 37, 37))
        );
        WorkPanel.setBounds(250, 0, 340, 400);
        frame.getContentPane().add(WorkPanel);
        frame.setLocationRelativeTo(null);
    }// </editor-fold>                        

    private void SignUpMousePressed(java.awt.event.MouseEvent evt)
    {
        if (signUp)
        {
            if (this.signUpFrame != null)
            {
                frame.dispose();
                signUpFrame.setVisible(true);
            } else
            {
                JOptionPane.showMessageDialog(null, "This Function is not yet supported.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void LoginActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (Name.getText().equals(user) && Password.getText().equals(password))
        {
            Done = true;
            if (work != null)
            {
                work.setVisible(Done);
            }
            frame.dispose();
        } else
        {
            frame.setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, "Wrong Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
            frame.setAlwaysOnTop(true);
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel Dp;
    private javax.swing.JButton Login;
    private javax.swing.JLabel Logo;
    private javax.swing.JPanel LogoPanel;
    private javax.swing.JTextField Name;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JPasswordField Password;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JLabel SignUp;
    private javax.swing.JPanel WorkPanel;
    // End of variables declaration                   
}
