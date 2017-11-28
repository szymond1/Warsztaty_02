package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import Model.Exercise;
import Model.Solution;
import Model.User;

public class AssignExerciseApp {

	public static void main(String[] args) {

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root",
					"coderslab");
			Scanner scan = new Scanner(System.in);

			String decision = "";

			do {
				System.out.println("\n");
				System.out.println("Wybierz jedna z opcji: ");
				System.out.println("add - przypisywnie zadań do użytkowników \n"
						+ "view - przeglądanie rozwiązań danego użytkownika \n" + "quit - zakonczenie programu \n");
				decision = scan.next();

				while (!(decision.equals("add") || decision.equals("view") || decision.equals("quit"))) {
					System.out.println("Błędnie podana instrukcja, sprobuj jeszcze raz");
					decision = scan.next();
				}

				switch (decision) {
				case "add":

					System.out.println("Chcesz przypisać zadanie do użytkownika. \n");
					System.out.println("Lista wszystkich użytkowników: \n");
					List<User> list = User.loadAllUser(conn);
					for (User u : list) {
						System.out.println(u.getId() + " | " + u.getUsername() + " | " + u.getEmail());
					}
					System.out.println("\n Podaj id użytkownika");
					long userId = scan.nextLong();

					System.out.println("Lista wszystkich zadań: \n");
					List<Exercise> list1 = Exercise.loadAllExcercise(conn);
					for (Exercise e : list1) {
						System.out.println(e.getId() + " | " + e.getTitle() + " | " + e.getDescription());
					}

					System.out.println("\n Podaj id zadania");
					int exerciseId = scan.nextInt();

					try {
						Solution solution = new Solution();
						solution.setExcercise_id(exerciseId);
						solution.setUsers_id(userId);
						solution.save(conn);
						System.out.println("Dodano rozwiązanie do bazy danych");
					} catch (Exception e) {
						System.out.println("Nie znaleziono rekordu o podanym id!");
					}
					break;

				case "view":
					System.out.println("Chcesz zobaczyc rozwiązania danego użytkownika");
					System.out.println("Lista wszystkich użytkowników: \n");
					List<User> list2 = User.loadAllUser(conn);
					for (User u : list2) {
						System.out.println(u.getId() + " | " + u.getUsername() + " | " + u.getEmail());
					}
					System.out.println("Podaj id użytkownika: \n");
					int id=0;
					while (scan.hasNext()) {
						try {
							id = scan.nextInt();
							break;
						} catch (InputMismatchException e) {
							scan.nextLine();
							System.out.println("Podaj poprawną liczbę ");
						}
					}
					Exercise.loadAllByUserId(conn, id);
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
