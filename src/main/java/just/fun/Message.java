package just.fun;

public class Message {
	private long id;
	private String user;
	private String message;

	public Message(long id, String user, String message) {
		this.id = id;
		this.user = user;
		this.message = message;
	}

	public long getId() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public String getMessage() {
		return message;
	}
}
