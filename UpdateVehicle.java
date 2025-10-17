import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateVehicle extends JFrame {
    private JTextField nameField, parentageField, mobileField, addressField, aadharNumberField, insuranceNameField, financeDetailsField;

    public UpdateVehicle() {
        setTitle("Update Vehicle Record");
        setSize(520, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(22);
        parentageField = new JTextField(22);
        mobileField = new JTextField(22);
        addressField = new JTextField(22);
        aadharNumberField = new JTextField(22);
        insuranceNameField = new JTextField(22);
        financeDetailsField = new JTextField(22);

        Font labelFont = new Font("Aerial", Font.PLAIN, 16);

        String[] labels = {
                "New Name:", "Parentage:", "Mobile Number:", "New Address:",
                "New Aadhar Number:", "New Insurance Name:", "New Finance Details:"
        };
        JTextField[] fields = {
                nameField, parentageField, mobileField, addressField,
                aadharNumberField, insuranceNameField, financeDetailsField
        };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            formPanel.add(label(labels[i], labelFont), gbc);
            gbc.gridx = 1;
            formPanel.add(fields[i], gbc);
        }

        JButton updateButton = new JButton("Update Record");
        updateButton.setBackground(new Color(72, 133, 237));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("SansSerif", Font.BOLD, 16));

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = labels.length;
        buttonConstraints.gridwidth = 2;
        buttonConstraints.insets = new Insets(16, 0, 0, 0);
        buttonConstraints.anchor = GridBagConstraints.CENTER;

        formPanel.add(updateButton, buttonConstraints);

        formPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        // Center the form panel in main panel
        mainPanel.add(formPanel, new GridBagConstraints());

        add(mainPanel);
        setLocationRelativeTo(null);

        updateButton.addActionListener(e -> updateRecord());
    }

    private JLabel label(String text, Font font) {
        JLabel l = new JLabel(text);
        l.setFont(font);
        return l;
    }

    private void updateRecord() {
        String mobile = mobileField.getText().trim();
        String name = nameField.getText().trim();
        String parentage = parentageField.getText().trim();
        String address = addressField.getText().trim();
        String aadhar = aadharNumberField.getText().trim();
        String insurance = insuranceNameField.getText().trim();
        String finance = financeDetailsField.getText().trim();

        if (mobile.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mobile number is required for update lookup!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/VRMS_db", "root", "Aleem007")) {
            String sql = "UPDATE vehicle_registration SET name=?, parentage=?, address=?, aadhar_number=?, insurance_name=?, finance_details=? WHERE mobile=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, parentage);
            stmt.setString(3, address);
            stmt.setString(4, aadhar);
            stmt.setString(5, insurance);
            stmt.setString(6, finance);
            stmt.setString(7, mobile);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Record updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No matching record found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating record.\n" + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdateVehicle().setVisible(true));
    }
}
