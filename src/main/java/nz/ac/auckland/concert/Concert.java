package nz.ac.auckland.concert;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDateTime;

/**
 * Class to represent a Concert. A Concert is characterised by an unique ID, 
 * title, date and time, and a featuring Performer.
 * 
 * Concert implements Comparable with a natural ordering based on its title.
 * Hence, in a List, Concert instances can be sorted into alphabetical order
 * based on their title value.
 *
 */
public class Concert implements Comparable<Concert> {
	private Long _id;
	private String _title;
	private LocalDateTime _date;
	private Performer _performer;
	
	public Concert(Long id, String title, LocalDateTime date, Performer performer) {
		_id = id;
		_title = title;
		_date = date;
		_performer = performer;
	}
	
	public Concert(String title, LocalDateTime date, Performer performer) {
		this(null, title, date, performer);
	}
	
	public Concert() {}
	
	public Long getId() {
		return _id;
	}
	
	public void setId(Long id) {
		_id = id;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public LocalDateTime getDate() {
		return _date;
	}
	
	public void setDate(LocalDateTime date) {
		_date = date;
	}

	public Performer getPerformer() {
		return _performer;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Concert, id: ");
		buffer.append(_id);
		buffer.append(", title: ");
		buffer.append(_title);
		buffer.append(", date: ");
		buffer.append(_date.toString());
		buffer.append(", featuring: ");
		buffer.append(_performer.getName());
		
		return buffer.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		// Implement value-equality based on a Concert's title alone. ID isn't 
		// included in the equality check because two Concert objects could
		// represent the same real-world Concert, where one is stored in the
		// database (and therefore has an ID - a primary key) and the other 
		// doesn't (it exists only in memory).
		if (!(obj instanceof Concert))
            return false;
        if (obj == this)
            return true;

        Concert rhs = (Concert) obj;
        return new EqualsBuilder().
            append(_title, rhs._title).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		// Hash-code value is derived from the value of the title field. It's 
		// good practice for the hash code to be generated based on a value 
		// that doesn't change. 
		return new HashCodeBuilder(17, 31). 
	            append(_title).hashCode();
	}

	@Override
	public int compareTo(Concert concert) {
		return _title.compareTo(concert.getTitle());
	}
}
