package just.fun;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class ChatServlet extends CommonChatServlet {
	private static final long serialVersionUID = -2197719945729108834L;

	private List<Message> messages = new CopyOnWriteArrayList<Message>();

	private long messageId = 0L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = getParam(req, "action");
		long lastMessageId = Long.parseLong(getParam(req, "id"));
		Object userObj = req.getSession().getAttribute("user");
		if (userObj != null && userObj instanceof User) {
			User user = (User) userObj;
			if (action.equals("send")) {
				String messageRaw = readBody(req);
				Message message = new Message(++messageId, user.getLogin(),
						messageRaw);
				messages.add(message);
			}
			if (action.equals("receive") || action.equals("send")) {
				List<MessageResponce> responces = new ArrayList<MessageResponce>();
				for (Message message : messages) {
					if (message.getId() > lastMessageId) {
						MessageResponce messageResponce = new MessageResponce(
								message.getId(), message.getUser(),
								message.getMessage(), message.getUser().equals(
										user.getLogin()));
						responces.add(messageResponce);
					}
				}
				String responce = serialize(responces);
				resp.getWriter().println(responce);
			}
		}
	}

	private String readBody(HttpServletRequest req) throws IOException {
		Reader reader = null;
		StringWriter writer = null;
		try {
			reader = req.getReader();
			writer = new StringWriter();
			char[] buffer = new char[512];
			int length;
			while ((length = reader.read(buffer)) == 512) {
				writer.write(buffer);
			}
			writer.write(buffer, 0, length);
			return writer.toString();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					warn("Error close reader", e);
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					warn("Error close writer", e);
				}
			}
		}
	}

	private String serialize(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, obj);
			return writer.toString();
		} catch (JsonGenerationException e) {
			error("Error json generation", e);
		} catch (JsonMappingException e) {
			error("Error json mappings", e);
		} catch (IOException e) {
			error("Error working with stream", e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				warn("Error close writer", e);
			}
		}
		return "";
	}

}
