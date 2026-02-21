import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditMedicine extends JFrame implements ActionListener {

    int medicineId;

    JTextField nameField, daysField;
    JComboBox<String> typeBox;

    JTextField morningDose, noonDose, nightDose;

    JComboBox<Integer> morningHour, morningMinute;
    JComboBox<String> morningAMPM;

    JComboBox<Integer> noonHour, noonMinute;
    JComboBox<String> noonAMPM;

    JComboBox<Integer> nightHour, nightMinute;
    JComboBox<String> nightAMPM;

    JComboBox<String> morningFood, noonFood, nightFood;

    JButton updateBtn;

    Integer[] hours = {1,2,3,4,5,6,7,8,9,10,11,12};
    Integer[] minutes = new Integer[60];

    String foodList[] = {"After Food","Before Food"};

    public EditMedicine(int id){

        this.medicineId = id;

        for(int i=0;i<60;i++)
            minutes[i] = i;

        setTitle("Edit Medicine");
        setSize(650,700);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0,2,10,10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Medicine Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Medicine Type:"));
        typeBox = new JComboBox<>(new String[]{"Tablet","Syrup","Injection"});
        add(typeBox);

        // ===== MORNING =====
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

        updateBtn = new JButton("Update");
        updateBtn.addActionListener(this);
        add(new JLabel());
        add(updateBtn);

        loadData();

        setVisible(true);
    }

    private String formatTime(int hour, int minute, String ampm){
        return String.format("%02d:%02d %s", hour, minute, ampm);
    }

    private void parseAndSetTime(String time, JComboBox<Integer> hourBox,
                                 JComboBox<Integer> minuteBox,
                                 JComboBox<String> ampmBox){

        if(time == null) return;

        try{
            String[] parts = time.split(" ");
            String[] hm = parts[0].split(":");

            int hour = Integer.parseInt(hm[0]);
            int minute = Integer.parseInt(hm[1]);
            String ampm = parts[1];

            hourBox.setSelectedItem(hour);
            minuteBox.setSelectedItem(minute);
            ampmBox.setSelectedItem(ampm);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadData(){
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement pst =
                    con.prepareStatement("SELECT * FROM medicines WHERE id=?");
            pst.setInt(1,medicineId);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                nameField.setText(rs.getString("name"));
                typeBox.setSelectedItem(rs.getString("type"));

                morningDose.setText(rs.getString("morningDose"));
                parseAndSetTime(rs.getString("morningTime"),
                        morningHour,morningMinute,morningAMPM);
                morningFood.setSelectedItem(rs.getString("morningFood"));

                noonDose.setText(rs.getString("noonDose"));
                parseAndSetTime(rs.getString("noonTime"),
                        noonHour,noonMinute,noonAMPM);
                noonFood.setSelectedItem(rs.getString("noonFood"));

                nightDose.setText(rs.getString("nightDose"));
                parseAndSetTime(rs.getString("nightTime"),
                        nightHour,nightMinute,nightAMPM);
                nightFood.setSelectedItem(rs.getString("nightFood"));

                daysField.setText(String.valueOf(rs.getInt("duration")));
            }

            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e){

        try{
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE medicines SET "
                    + "name=?, type=?, "
                    + "morningDose=?, morningTime=?, morningFood=?, "
                    + "noonDose=?, noonTime=?, noonFood=?, "
                    + "nightDose=?, nightTime=?, nightFood=?, "
                    + "duration=? WHERE id=?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1,nameField.getText().trim());
            pst.setString(2,(String)typeBox.getSelectedItem());

            pst.setString(3,morningDose.getText());
            pst.setString(4,formatTime(
                    (Integer)morningHour.getSelectedItem(),
                    (Integer)morningMinute.getSelectedItem(),
                    (String)morningAMPM.getSelectedItem()
            ));
            pst.setString(5,(String)morningFood.getSelectedItem());

            pst.setString(6,noonDose.getText());
            pst.setString(7,formatTime(
                    (Integer)noonHour.getSelectedItem(),
                    (Integer)noonMinute.getSelectedItem(),
                    (String)noonAMPM.getSelectedItem()
            ));
            pst.setString(8,(String)noonFood.getSelectedItem());

            pst.setString(9,nightDose.getText());
            pst.setString(10,formatTime(
                    (Integer)nightHour.getSelectedItem(),
                    (Integer)nightMinute.getSelectedItem(),
                    (String)nightAMPM.getSelectedItem()
            ));
            pst.setString(11,(String)nightFood.getSelectedItem());

            pst.setInt(12,Integer.parseInt(daysField.getText()));
            pst.setInt(13,medicineId);

            pst.executeUpdate();

            con.close();

            JOptionPane.showMessageDialog(this,"Updated Successfully!");
            dispose();
            new MedicineList();

        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,ex.getMessage());
        }
    }
}
