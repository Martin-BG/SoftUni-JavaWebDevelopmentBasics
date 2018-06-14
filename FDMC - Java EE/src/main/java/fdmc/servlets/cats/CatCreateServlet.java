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

@WebServlet("/cats/create")
public final class CatCreateServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        if (!LoggedUser.isAdmin(req)) {
            resp.sendRedirect("/");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/jsp/cats/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        if (!LoggedUser.isAdmin(req)) {
            resp.sendRedirect("/");
            return;
        }

        if (!this.isRequestValid(req)) {
            resp.sendRedirect("/cats/create");
            return;
        }

        final Cat cat = this.createCat(req);

        if (cat != null) {
            resp.sendRedirect("/cats/profile?catName=" + cat.getName());
        } else {
            resp.sendRedirect("/");
        }
    }

    private Cat createCat(final HttpServletRequest req) {
        final Cat cat = new Cat(
                req.getParameter("name"),
                req.getParameter("breed"),
                req.getParameter("color"),
                Integer.parseInt(req.getParameter("legs")),
                LoggedUser.get(req));

        return ((CatRepository) this.getServletContext().getAttribute("cats")).addCat(cat)
                ? cat : null;
    }

    private boolean isRequestValid(final HttpServletRequest req) {
        return req.getParameter("name") != null && !req.getParameter("name").isEmpty()
                && req.getParameter("breed") != null && !req.getParameter("breed").isEmpty()
                && req.getParameter("color") != null && !req.getParameter("color").isEmpty()
                && req.getParameter("legs") != null && req.getParameter("legs").matches("\\d+");
    }
}
