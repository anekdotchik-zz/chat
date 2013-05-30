package just.fun;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageService {
    private List<Message> messages = new CopyOnWriteArrayList<Message>();

    private long messageId = 0L;

    public void append(User user, String messageRaw) {
        Message message = new Message(++messageId, user.getLogin(), messageRaw);
        messages.add(message);
    }

    public List<Message> resive(long lastMessageId) {
        List<Message> responces = new ArrayList<Message>();
        for (Message message : messages) {
            if (message.getId() > lastMessageId) {
                responces.add(message);
            }
        }
        return responces;
    }

}
