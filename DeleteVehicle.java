import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DeleteVehicle extends JFrame {
    private JTextField chassisNumberField; // MODIFIED

    public DeleteVehicle() {
        setTitle("Delete Vehicle Record");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // MODIFIED: UI updated for Chassis Number
        panel.add(new JLabel("Chassis Number:"));
        chassisNumberField = new JTextField();
        panel.add(chassisNumberField);

        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back"); // ADDED
        panel.add(deleteButton);
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);

        deleteButton.addActionListener(e -> deleteRecord());
        backButton.addActionListener(e -> dispose()); // ADDED
    }

    private void deleteRecord() {
        // MODIFIED: Deletion logic now uses chassisnumber
        String chassisNumber = chassisNumberField.getText().trim();
        if (chassisNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Chassis Number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM vehicle_registration WHERE chassis_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, chassisNumber);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Record deleted successfully!");
                chassisNumberField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Record not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/VRMS_db";
        String username = "root";
        String password = "Aleem007"; // Replace with your password
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeleteVehicle().setVisible(true));
    }
}
