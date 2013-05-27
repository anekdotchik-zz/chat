package just.fun;

public class MessageResponce extends Message {
    private boolean isMy;

    public MessageResponce(long id, String user, String message, boolean isMy) {
        super(id, user, message);
        this.isMy = isMy;
    }

    public boolean isMy() {
        return isMy;
    }
}
