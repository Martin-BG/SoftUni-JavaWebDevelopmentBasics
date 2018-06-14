package fdmc.util;

import fdmc.data.models.User;
import fdmc.data.models.UserRole;
import fdmc.data.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;

public final class LoggedUser {

    private LoggedUser() {
    }

    public static User get(final HttpServletRequest req) {
        return ((UserRepository) req.getServletContext().getAttribute("users"))
                .getByUsername((String) req.getSession().getAttribute("username"));
    }

    public static boolean isPresent(final HttpServletRequest req) {
        return get(req) != null;
    }

    public static boolean isAdmin(final HttpServletRequest req) {
        final User user = get(req);

        return user != null && user.getRole() == UserRole.ADMIN;
    }
}
