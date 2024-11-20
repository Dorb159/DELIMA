package doctorappointmentdelima;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Doctor {
    public void doctorTransaction() {
        Scanner sc = new Scanner(System.in);
        String response = "yes";
        int action = -1;
        
        Doctor doctor = new Doctor();
        
        do {
            System.out.println("");    
            System.out.println("|||||||||||||||||||||||||||||||||||||||");
            System.out.println("     ----- WELCOME TO DOCTOR -----     ");
            System.out.println("|||||||||||||||||||||||||||||||||||||||");
            System.out.println("");
            System.out.println("==================");
            System.out.println("|1. ADD DOCTOR   |");
            System.out.println("|2. VIEW DOCTOR  |");
            System.out.println("|3. UPDATE DOCTOR|");
            System.out.println("|4. DELETE DOCTOR|");
            System.out.println("|5. EXIT DOCTOR  |");
            System.out.println("==================");
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
                    doctor.addDoctors();
                    break;
                case 2:
                    doctor.viewDoctors();
                    break;
                case 3:
                    doctor.viewDoctors();
                    doctor.updateDoctors();
                    doctor.viewDoctors();
                    break;
                case 4:
                    doctor.viewDoctors();
                    doctor.deleteDoctors();
                    doctor.viewDoctors();
                    break;
                case 5:
                    System.out.println("Exiting Doctor Transactions...");
                    return;
            }

            
            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank You, See you soon!");
    }

    public void addDoctors() {
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

        String sql = "INSERT INTO tbl_doctor (d_fname, d_lname, d_email, d_status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, stat);
    }

    public void viewDoctors() {
        config conf = new config();
        String query = "SELECT * FROM tbl_doctor";
        String[] headers = {"Doctor ID", "First Name", "Last Name", "Email", "Status"};
        String[] columns = {"d_id", "d_fname", "d_lname", "d_email", "d_status"};

        conf.viewRecords(query, headers, columns);
    }

    private void updateDoctors() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int id;
        while (true) {
            System.out.print("Enter the ID to update: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Doctor ID: ");
            sc.next();
        }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT d_id FROM tbl_doctor WHERE d_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric ID.");
                sc.nextLine(); 
            }
        }

        System.out.print("New Doctor First Name: ");
        String nfname = sc.next();
        System.out.print("New Last Name: ");
        String nlname = sc.next();
        System.out.print("New Email: ");
        String nemail = sc.next();
        System.out.print("New Status: ");
        String nstat = sc.next();

        String qry = "UPDATE tbl_doctor SET d_fname = ?, d_lname = ?, d_email = ?, d_status = ? WHERE d_id = ?";
        conf.updateRecord(qry, nfname, nlname, nemail, nstat, id);
    }

    private void deleteDoctors() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int id;
        while (true) {
            System.out.print("Enter the ID to delete: ");
            while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid Doctor ID: ");
            sc.next();
        }
            try {
                id = sc.nextInt();
                if (conf.getSingleValue("SELECT d_id FROM tbl_doctor WHERE d_id = ?", id) != 0) {
                    break; 
                }
                System.out.println("Selected ID doesn't exist! Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric ID.");
                sc.nextLine();
            }
        }

        String qry = "DELETE FROM tbl_doctor WHERE d_id = ?";
        conf.deleteRecord(qry, id);
    }
}
