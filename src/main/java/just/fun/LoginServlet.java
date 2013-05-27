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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user = getParam(request, "user");
		String pass = getParam(request, "pass");
		String action = getParam(request, LOGIN);
		info(String.format(LOGIN_LOG_TEMPLATE, action, user, pass));
		if (LOGIN.equals(action) && validate(user, pass)) {
			request.getSession().setAttribute("user", new User(user, pass));
			redirect(response, "chat.jsp");
		} else {
			invalidate(session);
			redirect(response, "login.jsp");
		}
	}

	private void redirect(HttpServletResponse response, String location)
			throws ServletException, IOException {
		response.sendRedirect(location);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		invalidate(request.getSession(false));
	}

	private boolean validate(String user, String pass) {
		if (!user.isEmpty() && !pass.isEmpty()) {
			return true;
		}
		return false;
	}

	private void invalidate(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
	}
}
