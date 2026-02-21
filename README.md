# Medicine Reminder System

## 1. Project Title

Medicine Reminder System – Java Desktop Application

---

## 2. Team Members

- Ananthu K Santhosh (24UBC108)  
- Abhishek P Suresh (24UBC102)

---

## 3. Problem Statement

Many individuals forget to take medicines at the correct time, which may lead to health complications and improper treatment. There is a need for a simple and reliable desktop-based system that allows users to schedule medicines and receive timely reminders.

---

## 4. Objective

The objective of this project is to develop a Java-based desktop application that:

- Enables users to manage daily medicine schedules.
- Provides reminder notifications at scheduled times.
- Stores medicine details securely in a database.
- Allows modification and deletion of existing records.
- Demonstrates GUI development, database connectivity, and multithreading concepts.

---

## 5. Features

- User login authentication
- Add new medicines
- Edit existing medicine records
- Delete medicine records
- View all medicines in table format
- Custom reminder time selection (Hour, Minute, AM/PM)
- Automatic popup reminder notification
- Duration-based reminder control
- MySQL database integration

---

## 6. Technologies Used

- Java (Swing for GUI development)
- MySQL Database
- JDBC (Java Database Connectivity)
- Multithreading (for reminder functionality)
- Git and GitHub for version control

---

## 7. System Modules

### 7.1 Login Module
Provides authentication before accessing the system.

### 7.2 Main Dashboard
Allows navigation to:
- Add Medicine
- View Medicines
- Exit Application

### 7.3 Add Medicine Module
Allows users to enter:
- Medicine name
- Type (Tablet, Syrup, Injection)
- Dosage details
- Reminder time
- Food preference
- Duration in days

### 7.4 Edit Medicine Module
Allows updating previously saved medicine details.

### 7.5 Medicine List Module
Displays all stored medicines in a structured table format.

### 7.6 Reminder Module
The system continuously checks the current system time and displays a popup notification when the scheduled time matches the current time. The reminder automatically stops after the specified duration period.

---

## 8. Database Details

Database Name: `medicine_db`  
Table Name: `medicines`

Main Fields:
- id
- name
- type
- morningDose
- morningTime
- morningFood
- noonDose
- noonTime
- noonFood
- nightDose
- nightTime
- nightFood
- duration
- startDate
- created_at

---

## 9. Steps to Run the Program

1. Install Java JDK.
2. Install MySQL Server.
3. Create the database using the provided SQL script.
4. Update database credentials in `DBConnection.java`.
5. Compile and run `Main.java`.

### Login Credentials

Username: admin  
Password: 1234  

---

## 10. Screenshots

Screenshots of the application are available in the `Screenshot` folder.

- Login Page
- Main Dashboard
- Add Medicine Page
- Medicine List Page
- Edit Medicine
- Delete Medicine
- Reminder Popup

---

## 11. Sample Input

Medicine Name: Paracetamol  
Type: Tablet  
Time: 07:30 AM  
Food Preference: After Food  
Duration: 5 Days  

---

## 12. Sample Output

- Medicine details stored successfully in the database.
- Record displayed in the medicine list.
- Popup reminder displayed at the scheduled time.

---

## 13. Demonstration Video

Working demonstration video:

https://www.youtube.com/watch?v=kqnLvwrRMIY

---

## 14. Conclusion

The Medicine Reminder System provides a simple and effective solution for managing daily medicine schedules. The project demonstrates Java GUI development, database integration using JDBC, and real-time reminder implementation using multithreading.
