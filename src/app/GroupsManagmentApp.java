package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import Model.Group;
import Model.User;

public class GroupsManagmentApp {

	public static void main(String[] args) {

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root",
					"coderslab");
			Scanner scan = new Scanner(System.in);

			String decision = "";

			do {
				List<Group> list = Group.loadAllGroup(conn);
				System.out.println("Lista wszystkich grup: ");
				for (Group g : list) {
					System.out.println(g.getId() + " *** " + g.getName());
				}
				System.out.println("\n");
				System.out.println("Wybierz jedna z opcji: ");
				System.out.println("add - dodanie grupy \n" + "edit - edycja grupy \n"
						+ "delete - usuniecie grupy \n" + "quit - zakonczenie programu \n");
				decision = scan.next();

				while (!(decision.equals("add") || decision.equals("edit") || decision.equals("delete")
						|| decision.equals("quit"))) {
					System.out.println("Błędnie podana instrukcja, sprobuj jeszcze raz");
					decision = scan.next();
				}

				switch (decision) {
				case "add":
					System.out.println("Chcesz dodac grupe \n Podaj nazwę grupy: ");
					scan.nextLine();
					String name = scan.nextLine();
					Group group = new Group(name);
					group.save(conn);
					System.out.println("Dodano grupe");
					break;

				case "edit":
					System.out.println("Chcesz edytować grupe \n Podaj id grupy: ");
					int id = scan.nextInt();
					Group group1 = Group.loadGroupById(conn, id);
					System.out.println("Podaj nazwę grupy");
					try {
						scan.nextLine();
						group1.setName(scan.nextLine());
						group1.save(conn);
						System.out.println("Edytowano grupe");
					} catch (NullPointerException e) {
						System.out.println("Grupa nie istnieje");
					}
					break;

				case "delete":
					System.out.println("Chcesz usunąć grupe \n Podaj id grupy: ");
					id = scan.nextInt();
					Group group2 = Group.loadGroupById(conn, id);
					try {
						group2.deleteGroup(conn);
						System.out.println("Usunięto grupe");
					} catch (NullPointerException e) {
						System.out.println("Grupa nie istnieje");
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
