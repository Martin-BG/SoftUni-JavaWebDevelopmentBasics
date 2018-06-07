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
public class UserRegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/users/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String username = req.getParameter("username");
        final String password = req.getParameter("password");
        final String confirmPassword = req.getParameter("confirmPassword");
        final UserRole userRole = req.getParameter("isAdmin") == null ? UserRole.USER : UserRole.ADMIN;


        if (password == null || username == null || !password.equals(confirmPassword)) {
            resp.sendRedirect("/users/register");
            return;
        }

        User user = new User(username, password, userRole);

        ((UserRepository) this.getServletContext().getAttribute("users")).addUser(user);

        resp.sendRedirect("/");
    }
}
