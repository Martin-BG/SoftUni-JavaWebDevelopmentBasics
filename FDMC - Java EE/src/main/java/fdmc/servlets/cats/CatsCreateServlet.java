package fdmc.servlets.cats;

import fdmc.data.models.Cat;
import fdmc.data.models.User;
import fdmc.data.repositories.CatRepository;
import fdmc.data.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cats/create")
public class CatsCreateServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
        final User currentUser = ((UserRepository) this.getServletContext().getAttribute("users"))
                .getByUsername(req.getSession().getAttribute("username").toString());

        if (currentUser == null || !currentUser.isAdmin()) {
            resp.sendRedirect("/users/login");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/jsp/cats/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final User creator = ((UserRepository) this.getServletContext().getAttribute("users"))
                .getByUsername(req.getSession().getAttribute("username").toString());

        if (creator == null) {
            resp.sendRedirect("/users/login");
            return;
        }

        final Cat cat = new Cat(
                req.getParameter("name"),
                req.getParameter("breed"),
                req.getParameter("color"),
                Integer.parseInt(req.getParameter("legs")),
                creator);

        final boolean isAdded = ((CatRepository) this.getServletContext().getAttribute("cats")).addCat(cat);

        if (isAdded) {
            resp.sendRedirect("/cats/profile?catName=" + cat.getName());
        } else {
            resp.sendRedirect("/cars/create");
        }
    }
}
