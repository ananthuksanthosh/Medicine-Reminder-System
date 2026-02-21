import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddMedicine extends JFrame implements ActionListener {

    JTextField nameField, daysField;
    JComboBox<String> typeBox;

    JCheckBox morningCheck, noonCheck, nightCheck;

    JTextField morningDose, noonDose, nightDose;

    JComboBox<Integer> morningHour, morningMinute;
    JComboBox<String> morningAMPM;

    JComboBox<Integer> noonHour, noonMinute;
    JComboBox<String> noonAMPM;

    JComboBox<Integer> nightHour, nightMinute;
    JComboBox<String> nightAMPM;

    JComboBox<String> morningFood, noonFood, nightFood;

    JButton saveBtn, backBtn;   // ✅ Added backBtn

    Integer[] hours = {1,2,3,4,5,6,7,8,9,10,11,12};
    Integer[] minutes = new Integer[60];

    String foodList[] = {"After Food","Before Food"};

    public AddMedicine(){

        for(int i=0;i<60;i++)
            minutes[i] = i;

        setTitle("Add Medicine");
        setSize(650,700);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0,2,10,10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Medicine Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Medicine Type:"));
        typeBox = new JComboBox<>(new String[]{"Tablet","Syrup","Injection","Oilment"});
        add(typeBox);

        // ===== MORNING =====
        morningCheck = new JCheckBox("Morning");
        add(morningCheck); add(new JLabel());

        add(new JLabel("Morning Dose:"));
        morningDose = new JTextField();
        add(morningDose);

        add(new JLabel("Morning Time:"));
        JPanel mPanel = new JPanel();
        morningHour = new JComboBox<>(hours);
        morningMinute = new JComboBox<>(minutes);
        morningAMPM = new JComboBox<>(new String[]{"AM","PM"});
        mPanel.add(morningHour);
        mPanel.add(new JLabel(":"));
        mPanel.add(morningMinute);
        mPanel.add(morningAMPM);
        add(mPanel);

        add(new JLabel("Morning Food:"));
        morningFood = new JComboBox<>(foodList);
        add(morningFood);

        // ===== NOON =====
        noonCheck = new JCheckBox("Noon");
        add(noonCheck); add(new JLabel());

        add(new JLabel("Noon Dose:"));
        noonDose = new JTextField();
        add(noonDose);

        add(new JLabel("Noon Time:"));
        JPanel nPanel = new JPanel();
        noonHour = new JComboBox<>(hours);
        noonMinute = new JComboBox<>(minutes);
        noonAMPM = new JComboBox<>(new String[]{"AM","PM"});
        nPanel.add(noonHour);
        nPanel.add(new JLabel(":"));
        nPanel.add(noonMinute);
        nPanel.add(noonAMPM);
        add(nPanel);

        add(new JLabel("Noon Food:"));
        noonFood = new JComboBox<>(foodList);
        add(noonFood);

        // ===== NIGHT =====
        nightCheck = new JCheckBox("Night");
        add(nightCheck); add(new JLabel());

        add(new JLabel("Night Dose:"));
        nightDose = new JTextField();
        add(nightDose);

        add(new JLabel("Night Time:"));
        JPanel niPanel = new JPanel();
        nightHour = new JComboBox<>(hours);
        nightMinute = new JComboBox<>(minutes);
        nightAMPM = new JComboBox<>(new String[]{"AM","PM"});
        niPanel.add(nightHour);
        niPanel.add(new JLabel(":"));
        niPanel.add(nightMinute);
        niPanel.add(nightAMPM);
        add(niPanel);

        add(new JLabel("Night Food:"));
        nightFood = new JComboBox<>(foodList);
        add(nightFood);

        add(new JLabel("Duration (Days):"));
        daysField = new JTextField();
        add(daysField);

        // ✅ Save and Back buttons
        saveBtn = new JButton("Save");
        backBtn = new JButton("Back");

        saveBtn.addActionListener(this);
        backBtn.addActionListener(this);

        add(backBtn);   // Left
        add(saveBtn);   // Right

        enableMorning(false);
        enableNoon(false);
        enableNight(false);

        morningCheck.addActionListener(e -> enableMorning(morningCheck.isSelected()));
        noonCheck.addActionListener(e -> enableNoon(noonCheck.isSelected()));
        nightCheck.addActionListener(e -> enableNight(nightCheck.isSelected()));

        setVisible(true);
    }

    private void enableMorning(boolean flag){
        morningDose.setEnabled(flag);
        morningHour.setEnabled(flag);
        morningMinute.setEnabled(flag);
        morningAMPM.setEnabled(flag);
        morningFood.setEnabled(flag);
    }

    private void enableNoon(boolean flag){
        noonDose.setEnabled(flag);
        noonHour.setEnabled(flag);
        noonMinute.setEnabled(flag);
        noonAMPM.setEnabled(flag);
        noonFood.setEnabled(flag);
    }

    private void enableNight(boolean flag){
        nightDose.setEnabled(flag);
        nightHour.setEnabled(flag);
        nightMinute.setEnabled(flag);
        nightAMPM.setEnabled(flag);
        nightFood.setEnabled(flag);
    }

    private String formatTime(int hour, int minute, String ampm){
        return String.format("%02d:%02d %s", hour, minute, ampm);
    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource()==backBtn){
            dispose();
            return;
        }

        if(e.getSource()==saveBtn){

            try{

                if(nameField.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this,"Enter Medicine Name");
                    return;
                }

                if(!morningCheck.isSelected() &&
                   !noonCheck.isSelected() &&
                   !nightCheck.isSelected()){
                    JOptionPane.showMessageDialog(this,
                            "Select at least one time");
                    return;
                }

                if(daysField.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this,"Enter Duration");
                    return;
                }

                int duration = Integer.parseInt(daysField.getText().trim());

                Connection con = DBConnection.getConnection();

                String sql = "INSERT INTO medicines "
                        + "(name,type,morningDose,morningTime,morningFood,"
                        + "noonDose,noonTime,noonFood,"
                        + "nightDose,nightTime,nightFood,duration,startDate) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,CURDATE())";

                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1,nameField.getText().trim());
                pst.setString(2,(String)typeBox.getSelectedItem());

                pst.setString(3,morningCheck.isSelected()?morningDose.getText():null);
                pst.setString(4,morningCheck.isSelected()?formatTime(
                        (Integer)morningHour.getSelectedItem(),
                        (Integer)morningMinute.getSelectedItem(),
                        (String)morningAMPM.getSelectedItem()
                ):null);
                pst.setString(5,morningCheck.isSelected()?(String)morningFood.getSelectedItem():null);

                pst.setString(6,noonCheck.isSelected()?noonDose.getText():null);
                pst.setString(7,noonCheck.isSelected()?formatTime(
                        (Integer)noonHour.getSelectedItem(),
                        (Integer)noonMinute.getSelectedItem(),
                        (String)noonAMPM.getSelectedItem()
                ):null);
                pst.setString(8,noonCheck.isSelected()?(String)noonFood.getSelectedItem():null);

                pst.setString(9,nightCheck.isSelected()?nightDose.getText():null);
                pst.setString(10,nightCheck.isSelected()?formatTime(
                        (Integer)nightHour.getSelectedItem(),
                        (Integer)nightMinute.getSelectedItem(),
                        (String)nightAMPM.getSelectedItem()
                ):null);
                pst.setString(11,nightCheck.isSelected()?(String)nightFood.getSelectedItem():null);

                pst.setInt(12,duration);

                pst.executeUpdate();

                pst.close();
                con.close();

                JOptionPane.showMessageDialog(this,"Saved Successfully!");
                dispose();

            }catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,ex.getMessage());
            }
        }
    }
}