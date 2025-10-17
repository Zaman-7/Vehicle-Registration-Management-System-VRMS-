import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteVehicle extends JFrame {
    private JTextField mobileField;

    public DeleteVehicle() {
        setTitle("Delete Vehicle Record");
        setSize(300, 120);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 2));

        add(new JLabel("Mobile Number:"));
        mobileField = new JTextField();
        add(mobileField);

        JButton deleteButton = new JButton("Delete");
        add(deleteButton);

        deleteButton.addActionListener(e -> deleteRecord());
    }

    private void deleteRecord() {
        String mobile = mobileField.getText();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/VRMS_db", "root", "Aleem007")) {
            String sql = "DELETE FROM vehicle_registration WHERE mobile = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, mobile);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Record deleted!");
            } else {
                JOptionPane.showMessageDialog(this, "Record not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
