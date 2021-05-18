/**
 * Simple File Encryption With PassWord Program
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
/**
 * Main App
 *
 * @author Mohammed Maasher
 * @since 2006
 */
class App extends JFrame implements ActionListener {

    private JLabel jlSrc=new JLabel("Source File:       ");
    private JLabel jlDes=new JLabel("Destination File: ");
    private JLabel jlPw=new JLabel("Password: ");

    private JButton jbBrowse1=new JButton("Browse");
    private JButton jbBrowse2=new JButton("Browse");
    private JButton jbEnc=new JButton("Encrypt");
    private JButton jbDec=new JButton("Decrypt");
    private JButton jbAbout=new JButton("About..");

    private JTextField jtSrc=new JTextField();
    private JTextField jtDes=new JTextField();
    private JTextField jtPw=new JTextField();

    /**
     * Action Events Handlers Method
     */
    public void actionPerformed (ActionEvent e){

        // Event of Browse1
        if (e.getSource()==jbBrowse1)
        {
            JFileChooser jfSrc=new JFileChooser();
            int intRet=jfSrc.showOpenDialog(App.this);
            if (intRet==jfSrc.APPROVE_OPTION){
                jtSrc.setText(jfSrc.getSelectedFile().getAbsolutePath());
            }
        }

        // Event of Browse2
        else if (e.getSource()==jbBrowse2)
        {
            JFileChooser jfSrc=new JFileChooser();
            int intRet=jfSrc.showSaveDialog(App.this);
            if (intRet==jfSrc.APPROVE_OPTION){
                jtDes.setText(jfSrc.getSelectedFile().getAbsolutePath());
            }
        }

        // Event of About
        else if(e.getSource()==jbAbout){
            JOptionPane joMsg=new JOptionPane();
            joMsg.showMessageDialog(joMsg,
                    "Simple File Encryption Program");
        }

        // Event of Encrypt
        else if(e.getSource()==jbEnc){

            String strSrc=jtSrc.getText();
            String strDes=jtDes.getText();
            String strPw=jtPw.getText();

            if (strSrc.equals("") | strDes.equals("") | strPw.equals("")){
                JOptionPane joMsg=new JOptionPane();
                joMsg.showMessageDialog(joMsg,"Error,You Must Fill All Fields!");
                return;
            }

            try{
                Encryptor.FileEnc(strSrc,strDes,strPw);
                JOptionPane joMsg=new JOptionPane();
                joMsg.showMessageDialog(joMsg,"OK , Encryption Done!");
            }
            catch(IOException ex){
                System.out.println(""+e);
            }
        }

        // Event of Decrypt
        else if(e.getSource()==jbDec){

            String strSrc=jtSrc.getText();
            String strDes=jtDes.getText();
            String strPw=jtPw.getText();

            if (strSrc.equals("") | strDes.equals("") | strPw.equals("")){
                JOptionPane joMsg=new JOptionPane();
                joMsg.showMessageDialog(joMsg,"Error,You Must Fill All Fields!");
                return;
            }

            try{
                int intRet=Encryptor.FileDec(strSrc,strDes,strPw);
                JOptionPane joMsg=new JOptionPane();
                if (intRet != -1)
                    joMsg.showMessageDialog(joMsg,"OK , Decryption Done!");
                else
                    joMsg.showMessageDialog(joMsg,"Error , Invalid Password");
            }
            catch(IOException ex){
                System.out.println(""+e);
            }
        }
    }

    public App(){
        //Init the main frame
        super("Simple File Encryption >>");
        setSize(450,180);

        JPanel jpContent=(JPanel) getContentPane();

        jpContent.setLayout(new FlowLayout());

        //Inti The Components
        jtSrc.setColumns(20);
        jtDes.setColumns(20);
        jtPw.setColumns(10);

        jbEnc.setBackground(new Color(255,255,223));
        jbDec.setBackground(new Color(255,130,130));
        jbAbout.setBackground(new Color(235,235,235));

        jbBrowse1.addActionListener(this);
        jbBrowse2.addActionListener(this);
        jbEnc.addActionListener(this);
        jbDec.addActionListener(this);
        jbAbout.addActionListener(this);

        //Add The Components to main Panel
        jpContent.add(jlSrc);
        jpContent.add(jtSrc);
        jpContent.add(jbBrowse1);

        jpContent.add(jlDes);
        jpContent.add(jtDes);
        jpContent.add(jbBrowse2);

        jpContent.add(jlPw);
        jpContent.add(jtPw);
        jpContent.add(jbEnc);
        jpContent.add(jbDec);

        JPanel jpAbout=new JPanel();
        jpContent.add(jpAbout);
        jpAbout.add(jbAbout);

    }

    /**
     * main method
     */
    public static void main(String a[])  throws IOException {

        new App().setVisible(true);
    }
}