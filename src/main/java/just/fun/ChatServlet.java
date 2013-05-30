package just.fun;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class ChatServlet extends CommonChatServlet {
    private static final long serialVersionUID = -2197719945729108834L;

    private MessageService messageService = new MessageService();

    private SerializationService serializationService = new SerializationService();

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
                messageService.append(user, messageRaw);
            }
            if (action.equals("receive") || action.equals("send")) {
                List<MessageResponce> responces = new ArrayList<MessageResponce>();
                for (Message message : messageService.resive(lastMessageId)) {
                    MessageResponce messageResponce = new MessageResponce(
                            message.getId(), message.getUser(),
                            message.getMessage(), message.getUser().equals(
                                    user.getLogin()));
                    responces.add(messageResponce);
                }
                String responce = serializationService.serialize(responces);
                resp.getWriter().println(responce);
            }
        }
    }

    private String readBody(HttpServletRequest req) throws IOException {
        Reader reader = null;
        StringWriter writer = new StringWriter();
        try {
            reader = req.getReader();
            IOUtils.copy(reader, writer);
            return writer.toString();
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(writer);
        }
    }
}