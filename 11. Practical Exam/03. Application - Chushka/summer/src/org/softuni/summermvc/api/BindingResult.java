package org.softuni.summermvc.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BindingResult {    //NOTE - implemented BindingResult (errors) class

    private final Set<String> errors;

    public BindingResult() {
        this.errors = new HashSet<>();
    }

    public Set<String> getErrors() {
        return Collections.unmodifiableSet(this.errors);
    }

    public void addError(final String error) {
        this.errors.add(error);
    }

    public void clearErros() {
        this.errors.clear();
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }
}
