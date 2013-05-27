package just.fun;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CommonChatServlet extends HttpServlet {
	private static final long serialVersionUID = 6471841875208233224L;
	private static volatile Object isInit;
	private Logger logger;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Object isInitLocal = isInit;
		if (isInitLocal == null) {
			synchronized (this) {
				isInitLocal = isInit;
				if (isInitLocal == null) {
					PropertyConfigurator.configure(config.getServletContext()
							.getRealPath("WEB-INF/classes/log4j.xml"));
					isInit = new Object();
				}
			}
		}
		logger = Logger.getLogger(this.getClass());
	}

	public void error(Object message, Throwable t) {
		if (logger != null) {
			logger.error(message, t);
		}
	}

	public void info(Object message) {
		if (logger != null) {
			logger.info(message);
		}
	}

	public void warn(Object message) {
		if (logger != null) {
			logger.warn(message);
		}
	}

	public void warn(Object message, Throwable e) {
		logger.warn(message, e);
	}

	protected String getParam(HttpServletRequest req, String name) {
		String param = req.getParameter(name);
		if (param == null) {
			param = "";
		}
		return param;
	}
}
