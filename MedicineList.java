import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MedicineList extends JFrame implements ActionListener {

    JTable table;
    DefaultTableModel model;
    JButton editBtn, deleteBtn, backBtn;

    public MedicineList(){

        setTitle("Medicine List");
        setSize(1000,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String cols[]={
                "ID","Name","Type",
                "Morning Dose","Morning Time",
                "Noon Dose","Noon Time",
                "Night Dose","Night Time",
                "Duration"
        };

        model = new DefaultTableModel(cols,0);
        table = new JTable(model);

        loadData();

        add(new JScrollPane(table), BorderLayout.CENTER);

        editBtn = new JButton("Edit");
        deleteBtn = new JButton("Delete");
        backBtn = new JButton("Back");

        editBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        backBtn.addActionListener(this);

        JPanel bottom = new JPanel();
        bottom.add(editBtn);
        bottom.add(deleteBtn);
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadData(){
        try{
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM medicines");

            model.setRowCount(0);

            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("morningDose"),
                        rs.getString("morningTime"),
                        rs.getString("noonDose"),
                        rs.getString("noonTime"),
                        rs.getString("nightDose"),
                        rs.getString("nightTime"),
                        rs.getInt("duration")
                });
            }

            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e){

        int row = table.getSelectedRow();

        if(e.getSource()==editBtn){
            if(row==-1){
                JOptionPane.showMessageDialog(this,"Select a row!");
                return;
            }

            int id = (int)model.getValueAt(row,0);
            dispose();
            new EditMedicine(id);
        }

        if(e.getSource()==deleteBtn){
            if(row==-1){
                JOptionPane.showMessageDialog(this,"Select a row!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if(confirm != JOptionPane.YES_OPTION)
                return;

            int id = (int)model.getValueAt(row,0);

            try{
                Connection con = DBConnection.getConnection();
                PreparedStatement pst =
                        con.prepareStatement("DELETE FROM medicines WHERE id=?");
                pst.setInt(1,id);
                pst.executeUpdate();
                con.close();
                loadData();

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        if(e.getSource()==backBtn){
            dispose();
            new Main();
        }
    }
}
