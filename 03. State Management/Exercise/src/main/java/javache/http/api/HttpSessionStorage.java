package javache.http.api;

import java.util.Map;

public interface HttpSessionStorage {

    void addSession(HttpSession session);

    HttpSession getById(String sessionId);

    void refreshSessions();

    Map<String, HttpSession> getAllSessions();
}
