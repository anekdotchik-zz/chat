package just.fun;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends CommonChatServlet {
    private static final String LOGIN_LOG_TEMPLATE = "Action='%s', Login='%s', Pass='%s'";
    private static final long serialVersionUID = -6744792746683700035L;
    private static final String LOGIN = "login";

    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String login = getParam(request, "user");
        User user = userService.lookupUser(login);
        String pass = getParam(request, "pass");
        String action = getParam(request, LOGIN);
        info(String.format(LOGIN_LOG_TEMPLATE, action, user, pass));
        if (LOGIN.equals(action) && userService.isValid(user, pass)) {
            request.getSession().setAttribute("user", user);
            redirect(response, "chat.jsp");
        } else {
            invalidate(session);
            redirect(response, "login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        info("Logout: " + session);
        invalidate(session);
    }

    private void redirect(HttpServletResponse response, String location)
            throws ServletException, IOException {
        response.sendRedirect(location);
    }

    private void invalidate(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }
}
