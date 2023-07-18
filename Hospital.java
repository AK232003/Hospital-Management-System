import java.sql.*;
import java.util.Scanner;

public class Hospital {

   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost/Hospital?useSSL=false";
   static final String USER = "user10";
   static final String PASS = "adrij@SHA8019";

   public static void main(String[] args) {

      Connection conn = null;
      Statement stmt = null;
      try {
         Class.forName(JDBC_DRIVER);
         conn = DriverManager.getConnection(DB_URL, USER, PASS);
         stmt = conn.createStatement();
         Scanner scan = new Scanner(System.in); // Create a Scanner object

         clearScreen();
         System.out.println("\nWELCOME TO PUSHPANJALI HOSPITAL!\n");
         main_menu(stmt, scan);

         scan.close();
         stmt.close();
         conn.close();
      } catch (SQLException se) { 
          se.printStackTrace();
      } catch (Exception e) { 
          e.printStackTrace();
      } finally {
         try {
            if (stmt != null)
               stmt.close();
         } catch (SQLException se2) {
         }
         try {
            if (conn != null)
               conn.close();
         } catch (SQLException se) {
             se.printStackTrace();
         }
      }
   }

   static void main_menu(Statement stmt, Scanner scan) {
      System.out.println("Login as a- ");
      System.out.println("1. Patient/Customer");
      System.out.println("2. Hospital Staff");
      System.out.println("3. Super Hospital Admin");
      System.out.println("0. Exit");

      System.out.print("\n\nENTER YOUR CHOICE : ");
      int choice = Integer.parseInt(scan.nextLine());
      clearScreen();

      switch (choice) {
         case 0:
            System.out.println("\nWish you Good Byy and Good Health from Pushpanjali Hospital!!\n\n");
            System.exit(0);
         case 1:
            patient_menu(stmt, scan);
            break;
         case 2:
            check_HospitalStaff(stmt, scan);
            break;
         case 3:
            check_super_admin(stmt, scan);
            break;
         default:
            clearScreen();
            System.out.println("Your entered choice is invalid. Please enter a valid choice.\n");
            break;
      }
      main_menu(stmt, scan);
   }

   static void patient_menu(Statement stmt, Scanner scan) {
      System.out.println("Please select appropriate option-");
      System.out.println("1. List of available medicines");
      System.out.println("0. Exit");

      System.out.print("\n\nENTER YOUR CHOICE : ");
      int choice = Integer.parseInt(scan.nextLine());
      clearScreen();

      switch (choice) {
         case 0:
            return;
         case 1:
            list_of_medicines(stmt, scan, true);
            break;
         default:
            clearScreen();
            System.out.println("Please Enter a Valid Choice!!\n");
            break;
      }
      patient_menu(stmt, scan);
   }

   static boolean authentication(Statement stmt, Scanner scan, boolean isSuperAdmin) {
      System.out.print("Enter your ID: ");
      String id = scan.nextLine();
      System.out.print("Enter your password: ");
      String password = scan.nextLine();

      clearScreen();
      boolean authenticated = false;

      if (isSuperAdmin) {
         String sql = "SELECT * from super_admin";
         ResultSet rs = executeSqlStmt(stmt, sql);

         try {
            while (rs.next()) {
               String possible_id = rs.getString("super_admin_id");
               String possible_password = rs.getString("super_admin_password");

               if (possible_id.equals(id) && password.equals(possible_password)) {
                  authenticated = true;
                  break;
               }
            }
         } catch (SQLException se) {
         }
      } else {
         String sql = "SELECT * from HospitalStaff";
         ResultSet rs = executeSqlStmt(stmt, sql);

         try {
            while (rs.next()) {
               String possible_id = rs.getString("staff_id");
               String possible_password = rs.getString("staff_password");

               if (possible_id.equals(id) && password.equals(possible_password)) {
                  authenticated = true;
                  break;
               }
            }
         } catch (SQLException se) {
         }
      }
      return authenticated;
   }

   static void check_HospitalStaff(Statement stmt, Scanner scan) {
      if (authentication(stmt, scan, false)) {
         HospitalStaff_menu(stmt, scan);
      } else {
         System.out.print("Entered details were incorrect. Do you want to try again (Y/N)? ");
         String input = scan.nextLine();
         if (input.equals("Y"))
            check_HospitalStaff(stmt, scan);
         else
            return;
      }
   }

   static void check_super_admin(Statement stmt, Scanner scan) {
      if (authentication(stmt, scan, true)) {
         super_admin_menu(stmt, scan);
      } else {
         System.out.print("Entered details were incorrect. Do you want to try again (Y/N)? ");
         String input = scan.nextLine();
         if (input.equals("Y"))
            check_super_admin(stmt, scan);
         else
            return;
      }
   }

   static void HospitalStaff_menu(Statement stmt, Scanner scan) {
      System.out.println("Please select appropriate option-");
      System.out.println("1. List of all medicines");
      System.out.println("2. List of medicines presently in hospital");
      System.out.println("3. Sell a medicine");
      System.out.println("4. Return a medicine");
      System.out.println("5. Add a medicine");
      System.out.println("6. Delete a medicine");
      System.out.println("0. Exit");
      System.out.print("\n\nENTER YOUR CHOICE : ");
      int choice = Integer.parseInt(scan.nextLine());
      clearScreen();

      switch (choice) {
         case 0:
            return;
         case 1:
            list_of_medicines(stmt, scan, false);
            break;
         case 2:
            list_of_medicines(stmt, scan, true);
            break;
         case 3:
            sell_medicine(stmt, scan);
            break;
         case 4:
            return_medicine(stmt, scan);
            break;
         case 5:
            add_medicine(stmt, scan);
            break;
         case 6:
            delete_medicine(stmt, scan);
            break;
         default:
            clearScreen();
            System.out.println("Please Enter a Valid Choice!!\n");
            break;
      }
      HospitalStaff_menu(stmt, scan);
   }

   static void super_admin_menu(Statement stmt, Scanner scan) {
      System.out.println("Please select appropriate option-");
      System.out.println("1. List of patients");
      System.out.println("2. List of hospital staff members");
      System.out.println("3. Add a patient");
      System.out.println("4. Remove a patient");
      System.out.println("5. Add a hospital staff");
      System.out.println("6. Delete a hospital staff");
      System.out.println("0. Exit");

      System.out.print("\n\nENTER YOUR CHOICE : ");
      int choice = Integer.parseInt(scan.nextLine());
      clearScreen();

      switch (choice) {
         case 0:
            return;
         case 1:
            list_of_patients(stmt, scan);
            break;
         case 2:
            list_of_HospitalStaffs(stmt, scan);
            break;
         case 3:
            add_patient(stmt, scan);
            break;
         case 4:
            delete_patient(stmt, scan);
            break;
         case 5:
            add_HospitalStaff(stmt, scan);
            break;
         case 6:
            delete_HospitalStaff(stmt, scan);
            break;
         default:
            clearScreen();
            System.out.println("Please Enter a Valid Choice!!\n");
            break;
      }
      super_admin_menu(stmt, scan);
   }

   static boolean list_of_medicines(Statement stmt, Scanner scan, boolean checkAvailable) {
      String sql = "select * from medicine";
      ResultSet rs = executeSqlStmt(stmt, sql);
      boolean noMedicines = true;

      try {
         System.out.println("List of available medicines:\n");
         while (rs.next()) {
            String id = rs.getString("medicine_id");
            String name = rs.getString("medicine_name");
            String disease = rs.getString("medicine_disease");
            Integer year = rs.getInt("medicine_year");
            String buyer = rs.getString("buyer");

            if (checkAvailable) {
               if (buyer == null) {
                  System.out.println("Medicine ID : " + id);
                  System.out.println("Medicine Name: " + name);
                  System.out.println("Medicine Disease : " + disease);
                  System.out.println("Medicine Expiry Year : " + year);
                  System.out.println("");
                  noMedicines = false;
               }
            } else {
                  System.out.println("Medicine ID : " + id);
                  System.out.println("Medicine Name: " + name);
                  System.out.println("Medicine Disease : " + disease);
                  System.out.println("Medicine Expiry Year : " + year);
                  System.out.println("Medicine Buyer : " + buyer);
                  System.out.println("");
                  noMedicines = false;
            }
         }

         if (noMedicines)
            System.out.println("Sorry, no medicines are available!\n");

         rs.close();
      } catch (SQLException e) {
          //e.printStackTrace();
      }
      return noMedicines;
   }

   static void sell_medicine(Statement stmt, Scanner scan) {
      try {
         boolean noMedicines = list_of_medicines(stmt, scan, true);
         if (!noMedicines) {
            System.out.print("\nEnter medicine id : ");
            String medicine_id = scan.nextLine();

            System.out.print("Enter patient id : ");
            String patient_id = scan.nextLine();

            clearScreen();

            String sql = String.format("UPDATE medicine SET buyer = '%s' WHERE medicine_id = '%s'", patient_id, medicine_id);
            int result = updateSqlStmt(stmt, sql);

            if (result != 0)
               System.out.println("BUYER HAS BEEN UPDATED SUCCESFULLY!!\n");
            else
               System.out.println("Something went wrong!\n");
         }
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void return_medicine(Statement stmt, Scanner scan) {
      try {
         System.out.print("\nEnter medicine id : ");
         String medicine_id = scan.nextLine();

         clearScreen();

         String sql = String.format("UPDATE medicine SET buyer = NULL WHERE medicine_id = '%s'", medicine_id);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Medicine has been returned!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void add_medicine(Statement stmt, Scanner scan) {
      try {
         System.out.print("\nEnter medicine id : ");
         String medicine_id = scan.nextLine();
         System.out.print("\nEnter medicine name : ");
         String medicine_name = scan.nextLine();
         System.out.print("\nEnter medicine disease : ");
         String medicine_disease = scan.nextLine();
         System.out.print("\nEnter medicine expiry year : ");
         Integer medicine_year = Integer.parseInt(scan.nextLine());

         clearScreen();

         String sql = String.format("INSERT INTO medicine VALUES('%s', '%s', '%s', '%d', NULL);", medicine_id, medicine_name, medicine_disease, medicine_year);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Medicine has been added successfully!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void delete_medicine(Statement stmt, Scanner scan) {
      try {
         System.out.print("\nEnter medicine ID : ");
         String medicine_id = scan.nextLine();

         clearScreen();

         String sql = String.format("DELETE FROM medicine where medicine_id = '%s'", medicine_id);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Medicine has been deleted successfully!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void list_of_patients(Statement stmt, Scanner scan) {
      String sql = "select * from patient";
      ResultSet rs = executeSqlStmt(stmt, sql);

      try {
         System.out.println("List of patients:\n");
         while (rs.next()) {
            String patient_id = rs.getString("patient_id");
            String patient_name = rs.getString("patient_name");

            System.out.println("Patient Id : " + patient_id);
            System.out.println("Patient Name : " + patient_name);
            System.out.println("\n");
         }

         rs.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }
   }

   static void list_of_HospitalStaffs(Statement stmt, Scanner scan) {
      String sql = "select * from HospitalStaff";
      ResultSet rs = executeSqlStmt(stmt, sql);

      try {
         System.out.println("List of Hospital Staff members:\n");
         while (rs.next()) {
            String staff_id = rs.getString("staff_id");
            String staff_name = rs.getString("staff_name");

            System.out.println("Staff Id : " + staff_id);
            System.out.println("Staff Name: " + staff_name);
            System.out.println("\n");
         }

         rs.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }
   }

   static void add_patient(Statement stmt, Scanner scan) {
      try {
         System.out.print("Enter patient id : ");
         String patient_id = scan.nextLine();
         System.out.print("Enter patient name : ");
         String patient_name = scan.nextLine();

         clearScreen();

         String sql = String.format("INSERT INTO patient VALUES('%s', '%s', NULL)", patient_id, patient_name);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Patient has been added successfully!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void add_HospitalStaff(Statement stmt, Scanner scan) {
      try {
         System.out.print("Enter Staff id : ");
         String staff_id = scan.nextLine();
         System.out.print("Enter Staff name : ");
         String staff_name = scan.nextLine();
         System.out.print("Enter Staff password : ");
         String staff_password = scan.nextLine();

         clearScreen();

         String sql = String.format("INSERT INTO HospitalStaff VALUES('%s', '%s', '%s')", staff_id, staff_name, staff_password);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Staff has been added successfully!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void delete_patient(Statement stmt, Scanner scan) {
      try {
         System.out.print("Enter patient id : ");
         String patient_id = scan.nextLine();

         clearScreen();

         String sql = String.format("DELETE FROM patient where patient_id = '%s'", patient_id);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Patient has been deleted successfully!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void delete_HospitalStaff(Statement stmt, Scanner scan) {
      try {
         System.out.print("Enter Staff id : ");
         String id = scan.nextLine();

         clearScreen();

         String sql = String.format("DELETE FROM HospitalStaff where staff_id = '%s'", id);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Staff has been deleted successfully!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static ResultSet executeSqlStmt(Statement stmt, String sql) {
      try {
         ResultSet rs = stmt.executeQuery(sql);
         return rs;
      } catch (SQLException se) {
          se.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
   }

   static int updateSqlStmt(Statement stmt, String sql) {
      try {
         int rs = stmt.executeUpdate(sql);
         return rs;
      } catch (SQLException se) {
          //se.printStackTrace();
      } catch (Exception e) {
          //e.printStackTrace();
      }
      return 0;
   }

   static void clearScreen() {
      System.out.println("\033[H\033[J");
      System.out.flush();
   }
}
