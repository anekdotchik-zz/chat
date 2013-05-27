package just.fun;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CommonChatServlet extends HttpServlet {
	private static final String LOG4J_XML = "WEB-INF/classes/log4j.xml";
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
							.getRealPath(LOG4J_XML));
					isInit = new Object();
				}
			}
		}
		logger = Logger.getLogger(this.getClass());
	}

	protected void error(Object message, Throwable t) {
		if (logger != null) {
			logger.error(message, t);
		}
	}

	protected void info(Object message) {
		if (logger != null) {
			logger.info(message);
		}
	}

	protected void warn(Object message) {
		if (logger != null) {
			logger.warn(message);
		}
	}

	protected void warn(Object message, Throwable e) {
		logger.warn(message, e);
	}

	protected static String getParam(HttpServletRequest req, String name) {
		String param = req.getParameter(name);
		if (param == null) {
			param = "";
		}
		return param;
	}
}
