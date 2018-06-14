package fdmc.servlets.cats;

import fdmc.data.models.Cat;
import fdmc.data.repositories.CatRepository;
import fdmc.util.LoggedUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cats/profile")
public final class CatProfileServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        final Cat cat = ((CatRepository) this.getServletContext().getAttribute("cats"))
                .getByName(req.getParameter("catName"));

        if (cat == null || !LoggedUser.isPresent(req)) {
            resp.sendRedirect("/");
            return;
        }

        cat.increaseViews();

        req.getRequestDispatcher("/WEB-INF/jsp/cats/profile.jsp").forward(req, resp);
    }
}
