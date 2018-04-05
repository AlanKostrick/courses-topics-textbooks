package courses;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Course {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String description;

	/**
	 * Textbook owns the relationship because it has the foreign key to Course.
	 */
	@OneToMany(mappedBy = "course")
	private Collection<Textbook> textbooks;

	/**
	 * We are thinking of Course as the owner, so it does not get a "mappedBy"
	 * attribute. This is arbitrary in the case of ManyToMany relationships.
	 */
	@ManyToMany
	private Collection<Topic> topics;

	public Collection<Textbook> getTextbooks() {
		return textbooks;
	}

	public Collection<Topic> getTopics() {
		return topics;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@SuppressWarnings("unused")
	private Course() {
	}

	public Course(String name, String description, Topic... topics) {
		this.name = name;
		this.description = description;
		this.topics = new HashSet<>(asList(topics));
	}

	// needed when running jpa tests so that we can flush out the proper objects by
	// id
	@Override
	public int hashCode() {
		return ((Long) id).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		return id == ((Course) obj).id;
	}

}
