package doctorappointmentdelima;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Appointment {

    public void apTransaction() {
        Scanner sc = new Scanner(System.in);
        String response = "yes";
        int action = -1;
        
        Appointment ap = new Appointment();
        
        do {
            System.out.println("");
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("     ----- WELCOME TO APPOINTMENT -----     ");
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("");
            System.out.println("========================");
            System.out.println("|1.  ADD APPOINTMENT   |");
            System.out.println("|2.  VIEW APPOINTMENT  |");
            System.out.println("|3.  UPDATE APPOINTMENT|");
            System.out.println("|4.  DELETE APPOINTMENT|");
            System.out.println("|5.  EXIT APPOINTMENT  |");
            System.out.println("========================");
            System.out.println("");

            System.out.println("|============|");
            System.out.println("|Enter Action|");
            System.out.println("|============|");
            System.out.println(":");

          try {
                action = sc.nextInt(); 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter a valid input!.");
                sc.nextLine();
                continue; 
            }

            if (action < 1 || action > 5) {
                System.out.println("Invalid action! Enter a valid input!.");
                continue; 
            }
           

            switch (action) {
                case 1:
                    ap.addAppointments();
                    break;
                case 2:
                    ap.viewAppointments();
                    break;
                case 3:
                    ap.viewAppointments();
                    ap.updateAppointments();
                    ap.viewAppointments();
                    break;
                case 4:
                    ap.viewAppointments();
                    ap.deleteAppointments();
                    ap.viewAppointments();
                    break;
                case 5:
                    System.out.println("Exiting Appointments...");
                    return; 
            }

            System.out.println("Do you want to continue? (yes/no)");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank You, See you soon!");
    }

    public void addAppointments() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        Doctor doctor = new Doctor();
        doctor.viewDoctors();

        int did;
        while (true) {
            System.out.print("Enter the ID of the Doctor: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Doctor ID: ");
            sc.next();
        }
            try {
                did = sc.nextInt();
                if (conf.getSingleValue("SELECT d_id FROM tbl_doctor WHERE d_id = ?", did) != 0) {
                    break; 
                }
                System.out.println("Doctor does not exist, select again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric ID.");
                sc.nextLine(); 
            }
        }

        Patient patient = new Patient();
        patient.viewPatients();

        int pid;
        while (true) {
            System.out.print("Enter the ID of the Patient: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Patient ID: ");
            sc.next();
        }
            try {
                pid = sc.nextInt();
                if (conf.getSingleValue("SELECT p_id FROM tbl_patient WHERE p_id = ?", pid) != 0) {
                    break; 
                }
                System.out.println("Patient does not exist, select again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric ID.");
                sc.nextLine(); 
            }
        }

        System.out.print("Appointment Date (YYYY-MM-DD): ");
        String date = sc.next();

        System.out.print("Appointment Time (HH:MM): ");
        String time = sc.next();

        System.out.print("Appointment Status: ");
        String status = sc.next();

        String qry = "INSERT INTO tbl_appointment (d_id, p_id, a_date, a_time, a_status) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(qry, did, pid, date, time, status);
    }

    public void viewAppointments() {
        String qry = "SELECT a_id, d_fname, p_fname, a_date, a_time, a_status FROM tbl_appointment "
                + "LEFT JOIN tbl_doctor ON tbl_doctor.d_id = tbl_appointment.d_id "
                + "LEFT JOIN tbl_patient ON tbl_patient.p_id = tbl_appointment.p_id";

        String[] hrds = {"Appointment ID", "Doctor Name", "Patient Name", "Appointment Date", "Appointment Time", "Appointment Status"};
        String[] clms = {"a_id", "d_fname", "p_fname", "a_date", "a_time", "a_status"};

        config conf = new config();
        conf.viewRecords(qry, hrds, clms);
    }

    private void updateAppointments() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        while (true) {
            System.out.print("Enter the ID to update: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Appointment ID: ");
            sc.next();
        }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT a_id FROM tbl_appointment WHERE a_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid ID.");
                sc.nextLine();             }
        }

        System.out.print("Enter New Appointment Status: ");
        sc.nextLine(); 
        String status = sc.nextLine();

        String qry = "UPDATE tbl_appointment SET a_status = ? WHERE a_id = ?";
        conf.updateRecord(qry, status, id);
    }

    private void deleteAppointments() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        while (true) {
            System.out.print("Enter the ID to delete: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Appoinment ID: ");
            sc.next();
        }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT a_id FROM tbl_appointment WHERE a_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid ID.");
                sc.nextLine(); 
            }
        }

        String qry = "DELETE FROM tbl_appointment WHERE a_id = ?";
        conf.deleteRecord(qry, id);
    }
}
