package com.skilldistillery.filmquery.database;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.skilldistillery.filmquery.entities.Film;

class DatabaseAccessTests {
	private DatabaseAccessor db;

	@BeforeEach
	void setUp() throws Exception {
		db = new DatabaseAccessorObject();
	}

	@AfterEach
	void tearDown() throws Exception {
		db = null;
	}

	@Test
	@DisplayName("Test null: findFilmById with invalid id returns null")
	void test1() {
		Film f = db.findFilmById(-1);
		assertNull(f);
	}

	@Test
	@DisplayName("Test not null: findFilmById with valid id returns data")
	void test2() {
		Film f = db.findFilmById(1);
		assertNotNull(f);
	}

}
