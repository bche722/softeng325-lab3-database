package nz.ac.auckland.concert;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Class to represent a Performer (an artist or band that plays at Concerts). A
 * Performer object has an ID (a database primary key value), a name, the name 
 * of an image file, and a genre.
 *
 */
public class Performer {
	
	private Long _id;
	private String _name;
	private String _s3ImageUri;
	private Genre _genre;
	
	public Performer(Long id, String name, String s3ImageUri, Genre genre) {
		_id = id;
		_name = name;
		_s3ImageUri = s3ImageUri;
		_genre = genre;
	}
	
	public Performer(String name, String s3ImageUri, Genre genre) {
		this(null, name, s3ImageUri, genre);
	}
	
	public Long getId() {
		return _id;
	}
	
	public void setId(Long id) {
		_id = id;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getS3ImageUri() {
		return _s3ImageUri;
	}

	public void setS3ImageUri(String s3ImageUri) {
		_s3ImageUri = s3ImageUri;
	}

	public Genre getGenre() {
		return _genre;
	}

	public void setGenre(Genre genre) {
		_genre = genre;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Performer, id: ");
		buffer.append(_id);
		buffer.append(", name: ");
		buffer.append(_name);
		buffer.append(", s3 image: ");
		buffer.append(_s3ImageUri);
		buffer.append(", genre: ");
		buffer.append(_genre.toString());
		
		return buffer.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Performer))
            return false;
        if (obj == this)
            return true;

        Performer rhs = (Performer) obj;
        return new EqualsBuilder().
            append(_name, rhs._name).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
	            append(_name).hashCode();
	}
}
