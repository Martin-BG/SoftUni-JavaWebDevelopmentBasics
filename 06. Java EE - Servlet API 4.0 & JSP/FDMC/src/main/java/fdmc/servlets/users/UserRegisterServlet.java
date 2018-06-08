package fdmc.servlets.users;

import fdmc.data.models.User;
import fdmc.data.models.UserRole;
import fdmc.data.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/register")
public final class UserRegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/users/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!this.isRequestValid(req)) {
            resp.sendRedirect("/users/register");
            return;
        }

        final String username = req.getParameter("username");
        final String password = req.getParameter("password");
        final UserRole userRole = req.getParameter("isAdmin") == null ? UserRole.USER : UserRole.ADMIN;

        final User user = new User(username, password, userRole);

        final boolean isRegistered = ((UserRepository) this.getServletContext().getAttribute("users")).addUser(user);

        if (isRegistered) {
            resp.sendRedirect("/users/login");
        } else {
            resp.sendRedirect("/users/register");
        }
    }

    private boolean isRequestValid(final HttpServletRequest req) {
        return req.getParameter("username") != null && !req.getParameter("username").isEmpty()
                && req.getParameter("password") != null && !req.getParameter("password").isEmpty()
                && req.getParameter("confirmPassword") != null && !req.getParameter("confirmPassword").isEmpty()
                && req.getParameter("password").equals(req.getParameter("confirmPassword"));
    }
}
