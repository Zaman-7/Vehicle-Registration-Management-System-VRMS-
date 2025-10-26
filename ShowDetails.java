import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class ShowDetails extends JFrame {
    private JTextField chassis_NumberTextField; // MODIFIED
    private JTable dataTable;

    public ShowDetails() {
        setTitle("Vehicle Details");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // MODIFIED: Search panel updated for Chassis Number
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.add(new JLabel("Chassis Number:"));
        chassis_NumberTextField = new JTextField(15);
        formPanel.add(chassis_NumberTextField);

        JButton searchButton = new JButton("Search");
        formPanel.add(searchButton);

        JButton backButton = new JButton("Back"); // ADDED
        formPanel.add(backButton);

        dataTable = new JTable();
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        add(mainPanel);

        searchButton.addActionListener(e -> searchDatabase());
        backButton.addActionListener(e -> dispose()); // ADDED
    }

    private void searchDatabase() {
        // MODIFIED: Search logic now uses chassisnumber
        String chassisNumber = chassis_NumberTextField.getText().trim();
        if (chassisNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Chassis Number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM vehicle_registration WHERE chassis_number = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, chassisNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Build table model from ResultSet
            dataTable.setModel(buildTableModel(resultSet));

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    private Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/VRMS_db";
        String username = "root";
        String password = "Aleem007"; // Replace with your password
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShowDetails().setVisible(true));
    }
}
