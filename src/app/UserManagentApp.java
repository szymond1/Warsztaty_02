package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import Model.User;

public class UserManagentApp {

	public static void main(String[] args) {

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root",
					"coderslab");
			Scanner scan = new Scanner(System.in);

			String decision = "";

			do {
				List<User> list = User.loadAllUser(conn);
				System.out.println("Lista wszystkich użytkowników: ");
				for (User u : list) {
					System.out.println(u.getId() + " *** " + u.getUsername() + " *** " + u.getEmail() + " *** "
							+ u.getUserGroupId());
				}
				System.out.println("\n");
				System.out.println("Wybierz jedna z opcji: ");
				System.out.println("add - dodanie użytkownika \n" + "edit - edycja uzytkownika \n"
						+ "delete - usuniecie uzytkownika \n" + "quit - zakonczenie programu \n");
				decision = scan.next();

				while (!(decision.equals("add") || decision.equals("edit") || decision.equals("delete")
						|| decision.equals("quit"))) {
					System.out.println("Błędnie podana instrukcja, sprobuj jeszcze raz");
					decision = scan.next();
				}

				switch (decision) {
				case "add":
					System.out.println("Chcesz dodac uzytkownika \n Podaj nazwę użytkownika: ");
					String username = scan.next();
					System.out.println("Podaj email użytkownika");
					String email = scan.next();
					System.out.println("Podaj hasło użytkownika");
					String password = scan.next();
					System.out.println("Podaj id_grupy użytkownika");
					int groupId = scan.nextInt();
					User user = new User(username, email, password);
					user.setUserGroupId(groupId);
					try {
						user.save(conn);
						System.out.println("Dodano użytkownika");
					} catch (MySQLIntegrityConstraintViolationException e) {
						System.out.println("Grupa nie istnieje");
					}

					break;

				case "edit":
					System.out.println("Chcesz edytować uzytkownika \n Podaj id użytkownika: ");
					int id = scan.nextInt();
					User user1 = User.loadUserById(conn, id);
					System.out.println("Podaj nazwę użytkownika");
					user1.setUsername(scan.next());
					System.out.println("Podaj email użytkownika");
					user1.setEmail(scan.next());
					System.out.println("Podaj hasło użytkownika");
					user1.setPassword(scan.next());
					System.out.println("Podaj id_grupy użytkownika");
					user1.setUserGroupId(scan.nextInt());
					try {
						user1.save(conn);
						System.out.println("Edytowano użytkownika");
					} catch (MySQLIntegrityConstraintViolationException e) {
						System.out.println("Grupa nie istnieje");
					}
					break;

				case "delete":
					System.out.println("Chcesz usunąć uzytkownika \n Podaj id użytkownika: ");
					id = scan.nextInt();
					User user2 = User.loadUserById(conn, id);
					try {
						user2.deleteUser(conn);
						System.out.println("Usunięto użytkownika");
					} catch (MySQLIntegrityConstraintViolationException e) {
						System.out.println("Użytkownik ma przypisane rozwiazania, najpierw usuń zadania");
					} catch (NullPointerException e) {
						System.out.println("Użytkownik nie istnieje");
					}
					break;
				case "quit":
					break;
				}
			} while (!decision.equals("quit"));
			{
				System.out.println("Koniec programu");
			}
			scan.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
