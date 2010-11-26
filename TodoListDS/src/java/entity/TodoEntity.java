package entity;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.io.Serializable;
import java.util.Date;

public class TodoEntity implements Serializable {

	private Key key;
	private String text;
	private Date date;

	public TodoEntity() {
	}

	public TodoEntity(Key key, String text, Date date) {
		this.key = key;
		this.text = text;
		this.date = date;
	}

	public TodoEntity(Entity entity) {
		this.key = entity.getKey();
		this.text = (String)entity.getProperty("text");
		this.date = (Date)entity.getProperty("date");
	}

	public Key getKey() {
		return key;
	}

	public String getKeyAsString() {
		return KeyFactory.keyToString(key);
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Entity getEntity() {
		Entity entity = new Entity("todoEntity");
		entity.setProperty("text", text);
		entity.setProperty("date", date);

		return entity;
	}

	public Entity mergeWithoutKey(Entity entity) {
		entity.setProperty("text", text);
		entity.setProperty("date", date);

		return entity;
	}
}
