package nz.ac.auckland.concert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConcertDAOTest {
	
	private static final String DB_INIT_SCRIPT_DIRECTORY = "src/test/resources";
	private static final String DB_INIT_SCRIPT = "db-init.sql";
	
	private JDBCConcertDAO _dao;
	
	@Before
	public void initialiseDatabase() throws DAOException {
		File file = new File(DB_INIT_SCRIPT_DIRECTORY + "/" + DB_INIT_SCRIPT);
		_dao = new JDBCConcertDAO(file);
	}
	
	@After
	public void closeDatabase() throws DAOException {
		_dao.close();
	}
	
	@Test
	public void queryAllConcerts() {
		try {
			List<Concert> concerts = _dao.getAll();
			
			// Check that the List contains all 22 Concerts stored in the database.
			assertEquals(22, concerts.size());
			
			// Check that the first Concert is correct.
			Concert concert = concerts.get(0);
			assertEquals("24K Magic World Tour", concert.getTitle());
			assertEquals(new LocalDateTime(2017, 9, 2, 19, 30), concert.getDate());
			Performer performer = concert.getPerformer();
			assertEquals("Bruno Mars", performer.getName());
			assertEquals("BrunoMars.jpg", performer.getS3ImageUri());
			assertEquals(Genre.RhythmAndBlues, performer.getGenre());
			
			// Check that where a Performer appears in more than 1 Concert, 
			// that the Performer is represented by the same Performer object.
			//
			// Katy Perry appears in two Concerts: "One Love Manchester" and 
			// "Witness: The Tour". The list of Concerts retrieved by the call
			// to ConcertDAO#getAll() should return two Concert instances that
			// share a common Performer object for Katy Perry.
			//
			// Find the two Concert instances in the List, extract their 
			// Performer objects and check that there's only a single instance 
			// that is aliased. The natural ordering for Concerts is based on 
			// the title of a Concert, and the ConcertDAO#getAll() call is 
			// expected to return a List of Concerts that is ordered 
			// alphabetically based on title. Using Collections' binarySearch()
			// method to find the two Concerts of interest.
			Concert oneLoveManchester = new Concert();
			oneLoveManchester.setTitle("One Love Manchester");
			Concert witnessTheTour = new Concert();
			witnessTheTour.setTitle("Witness: The Tour");
			int oneLoveManchesterIndex = Collections.binarySearch(concerts, oneLoveManchester);
			int witnessTheTourIndex = Collections.binarySearch(concerts, witnessTheTour);
			
			Performer katy1 = concerts.get(oneLoveManchesterIndex).getPerformer();
			Performer katy2 = concerts.get(witnessTheTourIndex).getPerformer();
			assertSame(katy1, katy2);
		} catch(DAOException e) {
			fail();
		}
	}
	
	@Test
	public void queryConcert() {
		try {
			// Query two Concerts.
			Concert concert1 = _dao.getById(1);
			Concert concert2 = _dao.getById(1);
		
			// The DAO is expected to return 2 Concert objects with the same 
			// value.
			assertEquals(concert1, concert2);
			
			// The DAO is expected to return 2 distinct Concert objects.
			assertNotSame(concert1, concert2);
		} catch(DAOException e) {
			fail();
		}
	}
	
	@Test
	public void addConcert() {
		try {
			// Retrieve Ed Sheran's concert "Divide Tour", and the extract the
			// Performer object for Ed.
			Concert divideTour = _dao.getById(2);
			Performer ed = divideTour.getPerformer();
			
			// Create a new Concert featuring Ed.
			LocalDateTime date = new LocalDateTime(2017, 12, 1, 16, 00);
			System.out.println(date.toString());
			Concert garbage = new Concert("My Music is Dreadfull", date, ed);
			
			// Save the new Concert.
			_dao.save(garbage);
			
			// Query all Concerts and pick out the new Concert.
			List<Concert> concerts = _dao.getAll();
			int index = Collections.binarySearch(concerts, garbage);
			Concert concert = concerts.get(index);
			
			// Check that the new Concert's ID has been assigned.
			assertNotNull(concert.getTitle());
			
			// Check that the result of the query for the new Concert equals
			// the newly created Concert.
			assertEquals(garbage, concert);
		} catch(DAOException e) {
			fail();
		}
	}
	
	@Test
	public void deleteConcert() {
		try {
			// Query Concert with the ID 18 (this represents "Evolve!").
			Concert evolve = _dao.getById(18);
			
			// Delete the Concert.
			_dao.deleteConcert(evolve);
			
			// Requery the Concert to check it's been deleted.
			evolve = _dao.getById(18);
			assertNull(evolve);
		} catch(DAOException e) {
			fail();
		}
	}
	
	@Test
	public void updateConcertAndPerformer() {
		try {
			// Query Concert with the ID 11 (this represents "Dangerous Woman").
			Concert dangerousWoman = _dao.getById(11);
						
			// Update the Concert's date, postponing it by one week.
			LocalDateTime newDate = new LocalDateTime(2017, 8, 17, 18, 30);
			dangerousWoman.setDate(newDate);
			
			// Also change the Concert Performer's image file.
			Performer performer = dangerousWoman.getPerformer();
			String newImage = "new_image.jpg";
			performer.setS3ImageUri(newImage);
			
			// Save the updated Concert (and Performer).
			_dao.save(dangerousWoman);
						
			// Requery the Concert.
			Concert concert = _dao.getById(11);
						
			// Check that the Concert's date has been updated.
			assertEquals(newDate, concert.getDate());
			
			// Check that the Performer's been updated too.
			performer = concert.getPerformer();
			assertEquals(newImage, performer.getS3ImageUri());
		} catch(DAOException e) {
			fail();
		}
	}
}
