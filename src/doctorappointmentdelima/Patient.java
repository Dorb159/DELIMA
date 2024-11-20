package doctorappointmentdelima;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Patient {
    public void patientTransaction() {
        Scanner sc = new Scanner(System.in);
        String response = "yes";
        int action = -1;
        
        Patient patient = new Patient();

        do {
            System.out.println("");     
            System.out.println("||||||||||||||||||||||||||||||||||||||||");
            System.out.println("     ----- WELCOME TO PATIENT -----     ");   
            System.out.println("||||||||||||||||||||||||||||||||||||||||");
            System.out.println("");
            System.out.println("====================");
            System.out.println("|1.  ADD PATIENT   |");
            System.out.println("|2.  VIEW PATIENT  |");
            System.out.println("|3.  UPDATE PATIENT|");
            System.out.println("|4.  DELETE PATIENT|");
            System.out.println("|5.  EXIT PATIENT  |");
            System.out.println("====================");
            System.out.println("");
            
            System.out.println("|============|");
            System.out.println("|Enter Action|");
            System.out.println("|============|");
            System.out.print(": ");

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
                    patient.addPatients();
                    break;
                case 2:       
                    patient.viewPatients();
                    break;
                case 3:
                    patient.viewPatients();
                    patient.updatePatients();
                    patient.viewPatients();
                    break;
                case 4:
                    patient.viewPatients();
                    patient.deletePatients();
                    patient.viewPatients();
                    break;
                case 5:
                    System.out.println("Exiting Patient Transactions...");
                    return;
            }

            
            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank You, See you soon!");
    }

    public void addPatients() { 
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("First Name: ");
        String fname = sc.nextLine();
        System.out.print("Last Name: ");
        String lname = sc.next();
        System.out.print("Email: ");
        String email = sc.next();
        System.out.print("Status: ");
        String stat = sc.next();

        String sql = "INSERT INTO tbl_patient (p_fname, p_lname, p_email, p_status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, stat);
    }

    public void viewPatients() {
        config conf = new config();
        String query = "SELECT * FROM tbl_patient";
        String[] headers = {"Patient ID", "First Name", "Last Name", "Email", "Status"};
        String[] columns = {"p_id", "p_fname", "p_lname", "p_email", "p_status"};

        conf.viewRecords(query, headers, columns);
    }

    private void updatePatients() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
        int id;
        while (true) {
            System.out.print("Enter the Patient ID to update: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Patient ID: ");
            sc.next();
        }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT p_id FROM tbl_patient WHERE p_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric ID.");
                sc.nextLine(); 
            }
        }

        System.out.print("New Patient First Name: ");
        String nfname = sc.next();
        System.out.print("New Last Name: ");
        String nlname = sc.next();
        System.out.print("New Email: ");
        String nemail = sc.next();
        System.out.print("New Status: ");
        String nstat = sc.next();

        String qry = "UPDATE tbl_patient SET p_fname = ?, p_lname = ?, p_email = ?, p_status = ? WHERE p_id = ?";
        conf.updateRecord(qry, nfname, nlname, nemail, nstat, id);
    }

    private void deletePatients() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int id;
        while (true) {
            System.out.print("Enter the Patient ID to delete: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Patient ID: ");
            sc.next();
        }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT p_id FROM tbl_patient WHERE p_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric ID.");
                sc.nextLine(); 
            }
        }

        String qry = "DELETE FROM tbl_patient WHERE p_id = ?";
        conf.deleteRecord(qry, id);
    }
}
