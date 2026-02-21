import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginBtn, exitBtn;

    // Demo credentials
    private final String USERNAME = "user";
    private final String PASSWORD = "123";

    public Login(){

        setTitle("Medicine Reminder System - Login");
        setSize(350,200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,2,10,10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginBtn = new JButton("Login");
        exitBtn = new JButton("Exit");

        loginBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        add(loginBtn);
        add(exitBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource() == loginBtn){

            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();

            if(user.equals(USERNAME) && pass.equals(PASSWORD)){
                dispose();       // Close login
                new Main();      // Open main window
            }else{
                JOptionPane.showMessageDialog(this,
                        "Invalid Username or Password");
            }
        }

        if(e.getSource() == exitBtn){
            System.exit(0);
        }
    }
}