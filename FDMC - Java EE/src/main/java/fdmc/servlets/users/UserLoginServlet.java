package fdmc.servlets.users;

import fdmc.data.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/login")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/users/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final String username = req.getParameter("username");
        final String password = req.getParameter("password");

        final boolean isValid = ((UserRepository)
                this.getServletContext().getAttribute("users"))
                .isValidCredentials(username, password);

        if (!isValid) {
            resp.sendRedirect("/users/login");
            return;
        }

        req.getSession().setAttribute("username", username);

        resp.sendRedirect("/");
    }
}
