package nz.ac.auckland.concert;

import java.util.List;

/**
 * Interface for a DAO for Concerts.
 * 
 * Implementations are free to use any persistence technology, e.g. 
 * serialization, relational database etc.
 *
 */
public interface ConcertDAO {

	/**
	 * Persists a Concert object. 
	 * 
	 * If the Concert is already in the database it is updated, otherwise it is 
	 * inserted. This operation saves the complete state of a Concert instance,
	 * including its Performer.
	 * 
	 * @param concert the Concert object to persist.
	 * 
	 * @throws DAOException if there's an error with storing the Concert.
	 * 
	 */
	public void save(Concert concert) throws DAOException;
	
	/**
	 * Retrieves a Concert by its unique ID. 	
	 * 
	 * @param id the unique ID of the Concert.
	 * 
	 * @return the Concert object, or null if there's no such Concert with the
	 * specified ID.
	 * 
	 * @throws DAOException if there's an error with retrieving the Concert.
	 * 
	 */
	public Concert getById(long id) throws DAOException;
	
	/**
	 * Retrieves all Concerts. The Concerts are ordered alphabetically, based
	 * on Concert's title field.
	 * 
	 * @return a List of Concerts. The List is empty if there are no Concerts 
	 * in the datastore.
	 * 
	 * @throws DAOException if there's an error with retrieving the Concerts.
	 * 
	 */
	public List<Concert> getAll() throws DAOException;
	
	/**
	 * Deletes a Concert.
	 * 
	 * @param concert the Concert to delete.
	 * 
	 * @throws DAOException if there's an error with deleting the Concert.
	 * 
	 */
	public void deleteConcert(Concert concert) throws DAOException;
	
	/**
	 * Closes the connection to the datastore.
	 * 
	 * @throws DAOException if there's an error with closing the connection.
	 * 
	 */
	public void close() throws DAOException;
}
