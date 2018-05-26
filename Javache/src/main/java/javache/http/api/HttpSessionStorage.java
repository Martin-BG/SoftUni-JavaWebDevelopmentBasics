package javache.http.api;

public interface HttpSessionStorage {

    void addSession(HttpSession session);

    HttpSession getById(String sessionId);

    void refreshSessions();
}
