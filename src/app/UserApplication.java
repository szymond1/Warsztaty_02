package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Model.Exercise;
import Model.Solution;
import Model.User;

public class UserApplication {

	public static void main(String[] args) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root",
					"coderslab");

			Scanner scan = new Scanner(System.in);
			System.out.println("Witaj w programie użytkownika, wpisz swoje id");
			int userId = scan.nextInt();
			String decision;

			do {
				System.out.println("\n");
				System.out.println("Wybierz jedna z opcji: ");
				System.out.println("add - dodawanie rozwiązania \n"
						+ "view - przeglądanie swoich rozwiązań \n" + "quit - zakonczenie programu \n");
				decision = scan.next();

				while (!(decision.equals("add") || decision.equals("view") || decision.equals("quit"))) {
					System.out.println("Błędnie podana instrukcja, sprobuj jeszcze raz");
					decision = scan.next();
				}

				switch (decision) {
				case "add":

					// lista zadan ktorych uzytkownik jeszcze nie wykonal
					User.loadNotDoneById(conn, userId);
					
					//edycja
					System.out.println("Podaj id zadania, do którego chcesz dodać rozwiązanie");
					int exerciseId = scan.nextInt();
					System.out.println("Podaj rozwiązanie zadania");
					String description = scan.next();
					Solution solution = new Solution(new Date(), new Date(), description);
					solution.setExcercise_id(exerciseId);
					solution.setUsers_id(userId);

					// sprawdzenie czy dodano juz rozwiazanie tego zadania
					boolean check = Solution.checkIfExists(conn, exerciseId, userId);
					if (check == false) {
						solution.save(conn);
						System.out.println("Dodano rozwiązanie do bazy danych!");
					} else {
					System.out.println("Już dodano rozwiązanie do tego zadania!");
					}
					break;

				case "view":

					try {

						Exercise.loadAllByUserId(conn, userId);
					} catch (Exception f) {
						System.out.println("Nie znaleziono rekordu o podanym id!");
					}

				case "quit":
					break;
				}
			} while (!decision.equals("quit"));{
				System.out.println("Koniec programu");
			}
			scan.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
				

