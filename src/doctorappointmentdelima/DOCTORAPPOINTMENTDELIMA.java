package doctorappointmentdelima;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DOCTORAPPOINTMENTDELIMA {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);    
        boolean exit = true;

        do {
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||"); 
            System.out.println("     ----- DOCTOR APPOINTMENT RECORDS -----     ");
            System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||"); 
            System.out.println("");
            System.out.println("==================");
            System.out.println("|1. DOCTOR       |");
            System.out.println("|2. PATIENT      |");
            System.out.println("|3. APPOINTMENT  |");
            System.out.println("|4. VIEW REPORTS |");
            System.out.println("|5. EXIT         |");
            System.out.println("==================");
            System.out.println("");
            System.out.println("|============|");
            System.out.println("|Enter Action|");
            System.out.println("|============|");
            System.out.println(":");

            int act = -1; 

            try {
                act = sc.nextInt(); 
            } catch (InputMismatchException e) {
               
                System.out.println("Invalid input, Please enter a valid input.");
                sc.nextLine(); 
                continue; 
            }

            if (act < 1 || act > 5) {
                System.out.println("Invalid action, Please enter a valid input.");
                continue; 
            }

            
            switch (act) {
                case 1:
                    Doctor doctor = new Doctor();
                    doctor.doctorTransaction();
                    break;

                case 2:
                    Patient patient = new Patient();
                    patient.patientTransaction();
                    break;

                case 3:
                    Appointment ap = new Appointment();
                    ap.apTransaction();
                    break;

                case 4:
                    Report rep = new Report();
                    rep.viewReports();
                    break;

                case 5:
                    System.out.print("Do you want to exit? (yes/no): ");
                    String resp = sc.next();
                    if (resp.equalsIgnoreCase("yes")) {
                        exit = false;
                    }
                    break;
            }
        } while (exit);

        System.out.println("Thank you, see you soon!");
    }
}
