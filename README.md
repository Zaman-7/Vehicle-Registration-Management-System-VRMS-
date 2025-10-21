# Vehicle-Registration-Management-System-VRMS

## Overview

The **Vehicle Registration Management System (VRMS)** is a Java-based desktop application designed to streamline and manage the process of vehicle registration, record updates, details retrieval, and management tasks for an institutional or governmental office. The system uses a graphical user interface (GUI) built with Java Swing and connects to a MySQL database to store and manipulate vehicle information.

## Features

- **Vehicle Registration:** Allows users to register new vehicles with all relevant details.
- **View Vehicle Details:** Retrieve and display information for vehicles already in the system.
- **Update Records:** Update existing vehicle registration data as needed.
- **Delete Records:** Remove incorrect or obsolete vehicle entries.
- **User-Friendly GUI:** Built with Java Swing for ease of use and visual clarity.

## Technologies Used

- **Java** (Swing for GUI application)
- **MySQL** (for backend data storage)
- JDBC (Java Database Connectivity)
- Java Libraries: `javax.swing`, `java.awt`, `java.sql`
- External Library: JDateChooser for date selection

## Project Structure

- `Main.java` — Launches the main application window, sets up navigation among features.
- `registervehicle.java` — Handles new vehicle registration, including form validation and database entry.
- `ShowDetails.java` — Retrieves and shows details of registered vehicles.
- `UpdateVehicle.java` — Provides functionality for modifying existing records.
- `DeleteVehicle.java` — Allows removal of selected vehicle records.
- `database.java` — Contains database connection code and example queries.
- `bg.png` — Background image resource used in the GUI.

## Getting Started

### Prerequisites

- Java JDK 8 or higher
- MySQL Server with a database named `VRMS_db`
- External JAR: `jcalendar` for the JDateChooser component

### Database Setup

1. Create a database named `VRMS_db` in MySQL.
2. Create a table named `vehicle_registration` with appropriate fields, e.g.:
   ```sql
   CREATE TABLE vehicle_registration (
     id INT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(50),
     mobile VARCHAR(20),
     address VARCHAR(100),
     parentage VARCHAR(50),
     aadhar_number VARCHAR(20),
     vehicle_name VARCHAR(50),
     vehicle_make VARCHAR(50),
     vehicle_model VARCHAR(50),
     insurance_name VARCHAR(50),
     finance_details VARCHAR(100),
     engine_number VARCHAR(50),
     chassis_number VARCHAR(50),
     vehicle_capacity VARCHAR(20),
     engine_capacity VARCHAR(20),
     registration_date DATE
   );
   ```
3. Update the credentials in code (username: `root`, password: `Aleem007` by default — change as per your environment).

### Running the Application

1. Clone or download the project repository.
2. Ensure all Java files and resources (including `bg.png`) are in the correct structure.
3. Add the `jcalendar` JAR to your project build path.
4. Compile all Java files:
   ```bash
   javac *.java
   ```
5. Run the main application:
   ```bash
   java Main
   ```

## Usage

- Use the main menu for navigation: Register, Check, Update, or Delete vehicle details.
- After each operation, confirmation dialogs will show the result.
- All actions require a working database connection.

## Contributing

Pull requests are welcome. For substantial changes, please open an issue to discuss your proposed changes.

## License

This project is for educational purposes and has no explicit license. Please add one if you redistribute or modify.
