package javache.http.impl;

import javache.http.api.HttpSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class HttpSessionImpl implements HttpSession {

    private final String id;
    private final Map<String, Object> attributes;
    private boolean isValid;

    public HttpSessionImpl() {
        this.id = UUID.randomUUID().toString();
        this.attributes = new HashMap<>();
        this.isValid = true;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean isValid() {
        return this.isValid;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    @Override
    public void addAttribute(final String name, final Object attribute) {
        this.attributes.put(name, attribute); // TODO (note) - Existing attributes are replaced!
    }

    @Override
    public void invalidate() {  // TODO - This method could be dropped if session is removed directly from HttpSessionStorage - investigate for side effects for doing so
        this.isValid = false;
    }
}
