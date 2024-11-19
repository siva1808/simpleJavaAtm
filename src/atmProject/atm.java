package atmProject;

import java.sql.*;
import java.util.Scanner;

public class atm {
	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "root");
			Statement stmt = con.createStatement();

			Scanner sc = new Scanner(System.in);
			System.out.println("Welcome to the ATM!");
			while (true) {
				System.out.print("Enter your pin number : ");
				int pin = sc.nextInt();
				ResultSet rs = stmt.executeQuery("SELECT * from list WHERE acc_no=" + pin);

				String name = null;
				int count = 0;
				int balance = 0;
				while (rs.next()) {
					name = rs.getString(2);
					balance = rs.getInt(3);
					count++;
				}
				int choice;
				int depositAmount = 0;
				int withdrawAmount = 0;

				if (count > 0) {
					System.out.println("Hello " + name);

					while (true) {
						System.out.println(
								"\nChoose an option:\n1. Check Balance\t2. Deposit\n\n3. Withdraw\t4. print the Receipt\n\n5. Exit");
						System.out.println();
						System.out.println("Enter your choice : ");
						choice = sc.nextInt();
						System.out.println("");

						switch (choice) {
						case 1:
							System.out.println("Your current balance is : " + balance + ".00");
							break;
						case 2:
							System.out.print("Enter the amount to deposit : ");
							depositAmount = sc.nextInt();
							balance += depositAmount;
							int bal = stmt
									.executeUpdate("UPDATE list SET balance = " + balance + " WHERE acc_no = " + pin);
							System.out.println("Deposit successful! Your new balance is : " + balance + ".00");
							break;
						case 3:
							System.out.print("Enter the amount to withdraw : ");
							withdrawAmount = sc.nextInt();
							if (withdrawAmount <= balance) {
								balance -= withdrawAmount;
								int sub = stmt.executeUpdate(
										"UPDATE list SET balance = " + balance + " WHERE acc_no = " + pin);
								System.out.println("Withdrawal successful! Your new balance is : " + balance + ".00");
							} else {
								System.out.println("Insufficient balance! Try Miminum of your balance.");
							}
							break;
						case 4:
							System.out.println("Thanks for Coming.");
							System.out.println("Your current balance is : " + balance + ".00");
							System.out.println("Amount to Deposit : " + depositAmount + ".00");
							System.out.println("The Amount to Withdraw : " + withdrawAmount + ".00");
							break;
						case 5:
							System.out.println("Exiting...");
							System.exit(0);
							break;
						default:
							System.out.println("Invalid choice! Try again.");
							break;
						}

					}
				}

				else {
					System.out.println("\nInvalid Pin Number! Try Again.");
				}

			}

		}

		catch (Exception e) {
			System.out.println(e);
		}
	}
}
