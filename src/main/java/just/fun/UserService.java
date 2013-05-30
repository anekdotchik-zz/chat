package just.fun;

public class UserService {

    public User lookupUser(String login) {
        return new User(login);
    }

    public boolean isValid(User user, String password) {
        if (user != null && !user.getLogin().isEmpty()) {
            return true;
        }
        return false;
    }
}
