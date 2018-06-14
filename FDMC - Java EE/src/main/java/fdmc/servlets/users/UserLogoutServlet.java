package fdmc.servlets.users;

import fdmc.util.LoggedUser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/logout")
public final class UserLogoutServlet extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        if (LoggedUser.isPresent(req)) {
            req.getSession().invalidate();
        }

        resp.sendRedirect("/");
    }
}
