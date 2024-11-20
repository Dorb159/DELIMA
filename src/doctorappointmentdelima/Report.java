package doctorappointmentdelima;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Report {
    
    public void viewReports() {
        Scanner sc = new Scanner(System.in);
        String response = "yes";
        int action = -1;

        Report rep = new Report();

        do {
            System.out.println("");      
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("       ----- WELCOME TO REPORTS -----       ");   
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||");
            System.out.println("");
            System.out.println("========================");
            System.out.println("|1. VIEW DOCTOR REPORT |");
            System.out.println("|2. VIEW PATIENT REPORT|");
            System.out.println("|3. VIEW APPOINTMENTS  |");
            System.out.println("|4. EXIT REPORTS       |");
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

            if (action < 1 || action > 4) {
                System.out.println("Invalid action! Enter a valid input!.");
                continue; 
            }

            switch (action) {
                case 1:
                    rep.viewDoctorReport();
                    break;
                case 2:
                    rep.viewPatientReport();
                    break;
                case 3:
                    rep.viewAppointmentReport();
                    break;
                case 4:
                    System.out.println("Exiting Reports...");
                    return; 
                default:
                    System.out.println("Invalid action. Try again."); 
            }

            System.out.println("Do you want to continue viewing reports? (yes/no)");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank you for using Reports!");
    }

    private void viewDoctorReport() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        
        String qry = "SELECT d_id, d_fname, d_lname, d_email, d_status FROM tbl_doctor";
        String[] headers = {"Doctor ID", "First Name", "Last Name", "Email", "Status"};
        String[] columns = {"d_id", "d_fname", "d_lname", "d_email", "d_status"};
        System.out.println("=== DOCTOR REPORT ===");
        conf.viewRecords(qry, headers, columns);
        
        
        System.out.println("Enter Doctor ID to view appointments:");
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Doctor ID: ");
            sc.next();
        }
        int doctorId = sc.nextInt();
        viewAppointmentsForDoctor(doctorId);
    }

    private void viewPatientReport() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        
        String qry = "SELECT p_id, p_fname, p_lname, p_email, p_status FROM tbl_patient";
        String[] headers = {"Patient ID", "First Name", "Last Name", "Email", "Status"};
        String[] columns = {"p_id", "p_fname", "p_lname", "p_email", "p_status"};
        System.out.println("=== PATIENT REPORT ===");
        conf.viewRecords(qry, headers, columns);
        
        
        System.out.println("Enter Patient ID to view assigned doctor:");
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Patient ID: ");
            sc.next();
        }
        int patientId = sc.nextInt();
        viewDoctorForPatient(patientId);
    }

    private void viewAppointmentReport() {
        config conf = new config();
        
        
        String qry = "SELECT a_id, d_fname, p_fname, a_date, a_time, a_status FROM tbl_appointment "
                   + "LEFT JOIN tbl_doctor ON tbl_doctor.d_id = tbl_appointment.d_id "
                   + "LEFT JOIN tbl_patient ON tbl_patient.p_id = tbl_appointment.p_id";
        String[] headers = {"Appointment ID", "Doctor Name", "Patient Name", "Date", "Time", "Status"};
        String[] columns = {"a_id", "d_fname", "p_fname", "a_date", "a_time", "a_status"};
        System.out.println("=== APPOINTMENT REPORT ===");
        conf.viewRecords(qry, headers, columns);
    }

    private void viewAppointmentsForDoctor(int doctorId) {
        config conf = new config();
        
        
        String qry = "SELECT a_id, p_fname, a_date, a_time, a_status FROM tbl_appointment "
                   + "LEFT JOIN tbl_patient ON tbl_patient.p_id = tbl_appointment.p_id "
                   + "WHERE tbl_appointment.d_id = ?";
        String[] headers = {"Appointment ID", "Patient Name", "Date", "Time", "Status"};
        String[] columns = {"a_id", "p_fname", "a_date", "a_time", "a_status"};
        System.out.println("=== APPOINTMENTS FOR DOCTOR ===");
        conf.viewRecordsWithParam(qry, headers, columns, doctorId);
    }

    private void viewDoctorForPatient(int patientId) {
        config conf = new config();
        
        
        String qry = "SELECT d_fname, d_lname FROM tbl_doctor "
                   + "JOIN tbl_appointment ON tbl_doctor.d_id = tbl_appointment.d_id "
                   + "WHERE tbl_appointment.p_id = ?";
        String[] headers = {"Doctor First Name", "Doctor Last Name"};
        String[] columns = {"d_fname", "d_lname"};
        System.out.println("=== DOCTOR ASSIGNED TO PATIENT ===");
        conf.viewRecordsWithParam(qry, headers, columns, patientId);
    }
}
