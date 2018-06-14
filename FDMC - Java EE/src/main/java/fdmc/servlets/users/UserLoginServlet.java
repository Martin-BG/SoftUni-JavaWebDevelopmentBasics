package fdmc.servlets.users;

import fdmc.data.models.User;
import fdmc.data.repositories.UserRepository;
import fdmc.util.LoggedUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/login")
public final class UserLoginServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        if (LoggedUser.isPresent(req)) {
            resp.sendRedirect("/");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/jsp/users/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        if (LoggedUser.isPresent(req)) {
            resp.sendRedirect("/");
            return;
        }

        final User user = this.login(req);

        if (user == null) {
            resp.sendRedirect("/users/login");
        } else {
            resp.sendRedirect("/");
        }
    }

    private User login(final HttpServletRequest req) {
        final String username = req.getParameter("username");
        final String password = req.getParameter("password");

        final User user = ((UserRepository) this.getServletContext().getAttribute("users"))
                .getByUsername(username);

        if (user != null && user.isPasswordValid(password)) {
            req.getSession().setAttribute("username", user.getUsername());
            return user;
        }

        return null;
    }
}
