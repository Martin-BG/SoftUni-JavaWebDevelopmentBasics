package javache.http.impl;

import javache.http.api.HttpSession;
import javache.http.api.HttpSessionStorage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class HttpSessionStorageImpl implements HttpSessionStorage {

    private final Map<String, HttpSession> sessions;

    public HttpSessionStorageImpl() {
        this.sessions = new HashMap<>();
    }

    @Override
    public void addSession(final HttpSession session) {
        this.sessions.putIfAbsent(session.getId(), session);
    }

    @Override
    public HttpSession getById(final String sessionId) {
        return this.sessions.get(sessionId);
    }

    @Override
    public void refreshSessions() {
        this.sessions.entrySet()
                .removeIf(session -> !session.getValue().isValid());
    }

    @Override
    public Map<String, HttpSession> getAllSessions() {
        return Collections.unmodifiableMap(this.sessions);
    }
}
