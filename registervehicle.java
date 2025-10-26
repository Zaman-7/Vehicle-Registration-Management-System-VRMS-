import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException; // ADDED import
import com.toedter.calendar.JDateChooser;

public class registervehicle extends JFrame {
    private JTextField nameTextField, mobileTextField, addressTextField, parentageTextField, aadharNumberTextField,
            vehicleNameTextField, vehicleMakeTextField, vehicleModelTextField, insuranceNameTextField,
            financeDetailsTextField, engineNumberTextField, chassisNumberTextField, vehicleCapacityTextField,
            engineCapacityTextField;
    private JDateChooser dateChooser;

    private Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/VRMS_db";
        String username = "root";
        String password = "Aleem007"; // Replace with your password
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public registervehicle() {
        setTitle("Register Your Vehicle");
        setSize(550, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        int gridY = 0;

        // Form Fields
        nameTextField = addFormField(formPanel, "Name:", gbc, gridY++);
        mobileTextField = addFormField(formPanel, "Mobile:", gbc, gridY++);
        addressTextField = addFormField(formPanel, "Address:", gbc, gridY++);
        parentageTextField = addFormField(formPanel, "Parentage:", gbc, gridY++);
        aadharNumberTextField = addFormField(formPanel, "Aadhar Number:", gbc, gridY++);
        vehicleNameTextField = addFormField(formPanel, "Vehicle Name:", gbc, gridY++);
        vehicleMakeTextField = addFormField(formPanel, "Vehicle Make:", gbc, gridY++);
        vehicleModelTextField = addFormField(formPanel, "Vehicle Model:", gbc, gridY++);
        insuranceNameTextField = addFormField(formPanel, "Insurance Name:", gbc, gridY++);
        financeDetailsTextField = addFormField(formPanel, "Finance Details:", gbc, gridY++);
        engineNumberTextField = addFormField(formPanel, "Engine Number:", gbc, gridY++);
        chassisNumberTextField = addFormField(formPanel, "Chassis Number:", gbc, gridY++);
        vehicleCapacityTextField = addFormField(formPanel, "Vehicle Capacity:", gbc, gridY++);
        engineCapacityTextField = addFormField(formPanel, "Engine Capacity:", gbc, gridY++);

        // Date Chooser
        gbc.gridx = 0;
        gbc.gridy = gridY;
        formPanel.add(new JLabel("Registration Date:"), gbc);
        gbc.gridx = 1;
        dateChooser = new JDateChooser();
        dateChooser.setFont(new Font("Arial", Font.PLAIN, 16));
        formPanel.add(dateChooser, gbc);
        gridY++;

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back"); // ADDED
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton); // ADDED

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        registerButton.addActionListener(e -> registerVehicle());

        // ADDED: Action listener for the Back button
        backButton.addActionListener(e -> dispose());
    }

    private JTextField addFormField(JPanel panel, String label, GridBagConstraints gbc, int gridY) {
        gbc.gridx = 0;
        gbc.gridy = gridY;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(textField, gbc);
        return textField;
    }

    private void registerVehicle() {
        // MODIFIED: Added specific catch for duplicate entries
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO vehicle_registration (name, mobile, address, parentage, aadhar_number, vehicle_name, vehicle_make, vehicle_model, insurance_name, finance_details, engine_number, chassis_number, vehicle_capacity, engine_capacity, registration_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, nameTextField.getText());
            statement.setString(2, mobileTextField.getText());
            statement.setString(3, addressTextField.getText());
            statement.setString(4, parentageTextField.getText());
            statement.setString(5, aadharNumberTextField.getText());
            statement.setString(6, vehicleNameTextField.getText());
            statement.setString(7, vehicleMakeTextField.getText());
            statement.setString(8, vehicleModelTextField.getText());
            statement.setString(9, insuranceNameTextField.getText());
            statement.setString(10, financeDetailsTextField.getText());
            statement.setString(11, engineNumberTextField.getText());
            statement.setString(12, chassisNumberTextField.getText());
            statement.setString(13, vehicleCapacityTextField.getText());
            statement.setString(14, engineCapacityTextField.getText());

            if (dateChooser.getDate() != null) {
                statement.setDate(15, new java.sql.Date(dateChooser.getDate().getTime()));
            } else {
                statement.setNull(15, java.sql.Types.DATE);
            }

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(this, "A vehicle with this Chassis Number already exists.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new registervehicle().setVisible(true));
    }
}
