package com.skilldistillery.filmquery.app;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	private DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		System.out.println("Welcome to the Skill Distillery FilmQueryApp !");
		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		System.out.println("Please choose from the following options?");
		System.out.println("\t1. Lookup a film by its ID.");
		System.out.println("\t2. Lookup a film by a keyword searching the title/description.");
		System.out.println("\t3. Exit the application.");
		System.out.print("Enter here: ");

		int choice = 0;
		try {
			choice = input.nextInt();
		} catch (InputMismatchException e) {
		} finally {
			input.nextLine();
		}

		switch (choice) {
		case 1:
			searchFilmByID(input);
			break;
		case 2:
			searchByKeyword(input);
			break;
		case 3:
			System.out.println();
			System.out.println("Thank you, and have a good day!");
			System.exit(0);
			break;
		default:
			System.err.println("Sorry, input did not match 1, 2, or 3. Please try again.");
			startUserInterface(input);
			break;
		}
	}

	private void searchFilmByID(Scanner input) {
		System.out.print("Please enter a Film ID: ");
		int filmID = 0;

		try {
			filmID = input.nextInt();
		} catch (InputMismatchException e) {
			input.nextLine();
			System.err.println("Sorry, your input was not a number. Please try again.");
			System.out.println();
			startUserInterface(input);
		} finally {
			input.nextLine();
		}

		Film film = db.findFilmById(filmID);
		List<Actor> actors = db.findActorsByFilmId(filmID);
		StringBuilder sb = new StringBuilder();

		System.out.println();

		if (film == null) {
			System.out.println("Sorry, the Film ID was not found.");
		} else {
			System.out.println(film.toStringFilmByID());
			for (Actor actor : actors) {
				sb.append(actor.getFirstName()).append(" ").append(actor.getLastName()).append(", ");
			}
			if (sb.length() > 1) {
				System.out.println("Actors: " + sb.delete(sb.length() - 2, sb.length() - 1));
				sb.delete(0, sb.length() - 1);
			} else {
				System.out.println("Actors: none listed in database");
			}
			System.out.println();
			seeAllDetailsMenu(input, filmID);
		}

		System.out.println();
		startUserInterface(input);
	}

	private void searchByKeyword(Scanner input) {
		System.out.print("Please enter a keyword to search the film database: ");
		String keyword = input.nextLine();
		System.out.println();

		List<Film> films = db.findFilmByKeyword(keyword);

		List<Actor> actors = null;
		StringBuilder sb = new StringBuilder();

		if (films.size() == 0) {
			System.out.println("Sorry, the keyword you input was not found.");
		} else {
			for (Film film : films) {
				System.out.println(film.toStringFilmByID());
				actors = db.findActorsByFilmId(film.getFilmId());
				for (Actor actor : actors) {
					sb.append(actor.getFirstName()).append(" ").append(actor.getLastName()).append(", ");
				}
				if (sb.length() > 1) {
					System.out.println("Actors:" + sb.delete(sb.length() - 2, sb.length() - 1));
					sb.delete(0, sb.length() - 1);
				} else {
					System.out.println("Actors: none listed in database");
				}

				System.out.println();
			}
		}

		System.out.println();
		startUserInterface(input);
	}

	private void seeAllDetailsMenu(Scanner input, int filmID) {
		System.out.println("What would you like to do?...");
		System.out.println("\t1. Return to the main menu.");
		System.out.println("\t2. View all film details.");
		System.out.print("Please enter your choice (1 or 2): ");

		int choice = 0;

		try {
			choice = input.nextInt();
		} catch (InputMismatchException e) {
			input.nextLine();

			System.err.println("Sorry, your input was not a number.");
			System.out.println();
			seeAllDetailsMenu(input, filmID);
		} finally {
			input.nextLine();
		}

		switch (choice) {
		case 1:
			break;
		case 2:
			seeAllDetails(filmID);
			System.out.println();
			startUserInterface(input);
			break;
		default:
			System.out.println();
			System.err.println("Sorry, your input was not 1 or 2. Please try again.");
			System.out.println();
			seeAllDetailsMenu(input, filmID);
			break;
		}
	}

	private void seeAllDetails(int filmID) {
		Film film = db.findFilmById(filmID);
		List<Actor> actors = db.findActorsByFilmId(filmID);
		StringBuilder sb = new StringBuilder();

		System.out.println();

		System.out.println(film.toString());
		for (Actor actor : actors) {
			sb.append(actor.getFirstName()).append(" ").append(actor.getLastName()).append(", ");
		}

		System.out.println("Actors: " + sb.delete(sb.length() - 2, sb.length() - 1));
	}
}