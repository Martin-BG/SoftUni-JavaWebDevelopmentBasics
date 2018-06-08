package fdmc.servlets.cats;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cats/all")
public final class CatsAllServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/jsp/cats/all.jsp").forward(req, resp);
    }
}
