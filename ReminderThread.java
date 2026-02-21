import javax.swing.*;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReminderThread extends Thread {

    private String lastTriggeredTime = "";

    public ReminderThread() {
        start();
    }

    @Override
    public void run() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("hh:mm a");

        while (true) {
            try {

                Connection con = DBConnection.getConnection();
                if (con == null) {
                    Thread.sleep(30000);
                    continue;
                }

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM medicines");

                String currentTime =
                        LocalTime.now().format(formatter);

                while (rs.next()) {

                    checkAndPopup(rs, "morningTime", "Morning", currentTime);
                    checkAndPopup(rs, "noonTime", "Noon", currentTime);
                    checkAndPopup(rs, "nightTime", "Night", currentTime);
                }

                rs.close();
                st.close();
                con.close();

                Thread.sleep(30000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkAndPopup(ResultSet rs,
                               String column,
                               String label,
                               String currentTime) {

        try {

            String dbTime = rs.getString(column);

            if (dbTime == null || dbTime.trim().isEmpty())
                return;

            dbTime = dbTime.trim();

            // Prevent duplicate popup in same minute
            if (currentTime.equalsIgnoreCase(dbTime)
                    && !currentTime.equals(lastTriggeredTime)) {

                lastTriggeredTime = currentTime;

                JOptionPane.showMessageDialog(null,
                        "📌 " + label + " Medicine Reminder\n\n"
                                + "Name : " + rs.getString("name") + "\n"
                                + "Dose : " + rs.getString(label.toLowerCase() + "Dose") + "\n"
                                + "Food : " + rs.getString(label.toLowerCase() + "Food"),
                        "Medicine Reminder",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
