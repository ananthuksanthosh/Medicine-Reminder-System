import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {

    JButton addBtn, listBtn, exitBtn;

    public Main(){

        setTitle("Medicine Reminder System");
        setSize(400,250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3,1,10,10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addBtn = new JButton("Add Medicine");
        listBtn = new JButton("View Medicines");
        exitBtn = new JButton("Exit");

        addBtn.addActionListener(this);
        listBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        add(addBtn);
        add(listBtn);
        add(exitBtn);

        new ReminderThread(); // keep reminder working

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource()==addBtn)
            new AddMedicine();

        if(e.getSource()==listBtn)
            new MedicineList();

        if(e.getSource()==exitBtn)
            System.exit(0);
    }

    public static void main(String[] args){
        new Login();  // Start from login
    }
}