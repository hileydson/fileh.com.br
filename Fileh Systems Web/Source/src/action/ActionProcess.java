package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionProcess {
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
