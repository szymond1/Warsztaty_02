package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import Model.Exercise;


public class ExerciseManagment {

	public static void main(String[] args) {

		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root",
					"coderslab");
			Scanner scan = new Scanner(System.in);

			String decision = "";

			do {
				List<Exercise> list = Exercise.loadAllExcercise(conn);
				System.out.println("Lista wszystkich zadań: ");
				for (Exercise e : list) {
					System.out.println(e.getId() + " *** " + e.getTitle() + " *** " + e.getDescription());
				}
				System.out.println("\n");
				System.out.println("Wybierz jedna z opcji: ");
				System.out.println("add - dodanie zadania \n" + "edit - edycja zadania \n"
						+ "delete - usuniecie zadania \n" + "quit - zakonczenie programu \n");
				decision = scan.next();

				while (!(decision.equals("add") || decision.equals("edit") || decision.equals("delete")
						|| decision.equals("quit"))) {
					System.out.println("Błędnie podana instrukcja, sprobuj jeszcze raz");
					decision = scan.next();
				}

				switch (decision) {
				case "add":
					System.out.println("Chcesz dodac zadanie \n Podaj tytuł zadania: ");
					scan.nextLine();
					String title = scan.nextLine();
					System.out.println("Podaj opis zadania");
					String desc = scan.nextLine();
					Exercise exercise = new Exercise(title, desc);
					exercise.save(conn);
					System.out.println("Dodano zadanie");
					break;

				case "edit":
					System.out.println("Chcesz edytować zadanie \n Podaj id zadania: ");
					int id = scan.nextInt();
					Exercise ex1 = Exercise.loadExerciseById(conn, id);
					System.out.println("Podaj tytuł zadania: ");
					scan.nextLine();
					ex1.setTitle(scan.nextLine());
					System.out.println("Podaj opis zadania");
					ex1.setDescription(scan.nextLine());
					try {
						ex1.save(conn);
						System.out.println("Edytowano zadanie");
					} catch (MySQLIntegrityConstraintViolationException e) {
						System.out.println("Zadanie nie istnieje");
					}
					break;

				case "delete":
					System.out.println("Chcesz usunąć zadanie \n Podaj id zadania: ");
					id = scan.nextInt();
					Exercise ex2 = Exercise.loadExerciseById(conn, id);
					try {
						ex2.deleteExercise(conn);
						System.out.println("Usunięto zadanie");
					} catch (NullPointerException e) {
						System.out.println("Zadanie nie istnieje");
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