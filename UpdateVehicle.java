import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateVehicle extends JFrame {
    private JTextField chassisNumberField, nameField, parentageField, mobileField, addressField, aadharNumberField,
            insuranceNameField, financeDetailsField;

    public UpdateVehicle() {
        setTitle("Update Vehicle Record");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Top Panel for fetching by Chassis Number ---
        JPanel fetchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fetchPanel.add(new JLabel("Enter Chassis Number to Update:"));
        chassisNumberField = new JTextField(15);
        fetchPanel.add(chassisNumberField);
        JButton fetchButton = new JButton("Fetch Details");
        fetchPanel.add(fetchButton);
        mainPanel.add(fetchPanel, BorderLayout.NORTH);

        // --- Form Panel for editable details ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        int gridY = 0;

        nameField = addFormField(formPanel, "Name:", gbc, gridY++);
        parentageField = addFormField(formPanel, "Parentage:", gbc, gridY++);
        mobileField = addFormField(formPanel, "Mobile Number:", gbc, gridY++); // Now updatable
        addressField = addFormField(formPanel, "Address:", gbc, gridY++);
        aadharNumberField = addFormField(formPanel, "Aadhar Number:", gbc, gridY++);
        insuranceNameField = addFormField(formPanel, "Insurance Name:", gbc, gridY++);
        financeDetailsField = addFormField(formPanel, "Finance Details:", gbc, gridY++);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // --- Bottom Panel for buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton updateButton = new JButton("Update Record");
        JButton backButton = new JButton("Back");
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        fetchButton.addActionListener(e -> fetchRecord());
        updateButton.addActionListener(e -> updateRecord());
        backButton.addActionListener(e -> dispose());
    }

    private JTextField addFormField(JPanel panel, String label, GridBagConstraints gbc, int gridY) {
        gbc.gridx = 0;
        gbc.gridy = gridY;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        JTextField textField = new JTextField(25);
        panel.add(textField, gbc);
        return textField;
    }

    private void fetchRecord() {
        String chassisNumber = chassisNumberField.getText().trim();
        if (chassisNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Chassis Number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM vehicle_registration WHERE chassis_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, chassisNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                parentageField.setText(rs.getString("parentage"));
                mobileField.setText(rs.getString("mobile"));
                addressField.setText(rs.getString("address"));
                aadharNumberField.setText(rs.getString("aadharnumber"));
                insuranceNameField.setText(rs.getString("insurancename"));
                financeDetailsField.setText(rs.getString("financedetails"));
            } else {
                JOptionPane.showMessageDialog(this, "Record not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateRecord() {
        String chassisNumber = chassisNumberField.getText().trim();
        if (chassisNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fetch a vehicle by its chassis number first.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = getConnection()) {
            String sql = "UPDATE vehicle_registration SET name=?, parentage=?, mobile=?, address=?, aadhar_number=?, insurance_name=?, finance_details=? WHERE chassis_number=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, nameField.getText());
            stmt.setString(2, parentageField.getText());
            stmt.setString(3, mobileField.getText());
            stmt.setString(4, addressField.getText());
            stmt.setString(5, aadharNumberField.getText());
            stmt.setString(6, insuranceNameField.getText());
            stmt.setString(7, financeDetailsField.getText());
            stmt.setString(8, chassisNumber); // WHERE clause

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Record updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Record not found or no changes made.", "Update Status", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating record: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/VRMS_db";
        String username = "root";
        String password = "Aleem007"; // Replace with your password
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdateVehicle().setVisible(true));
    }
}
