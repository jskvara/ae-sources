package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TodoEntity implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String text;

	@Temporal(TemporalType.DATE)
	private Date created;

	public TodoEntity() {
	}

	public TodoEntity(Long id, String text, Date date) {
		this.id = id;
		this.text = text;
		this.created = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return created;
	}

	public void setDate(Date date) {
		this.created = date;
	}
}
