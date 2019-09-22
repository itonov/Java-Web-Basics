package org.softuni.summermvc.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BindingResult {
    private Set<String> errors;

    public BindingResult() {
        this.errors = new HashSet<>();
    }

    public Set<String> getErrors() {
        return Collections.unmodifiableSet(this.errors);
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public void clearErrors() {
        this.errors.clear();
    }

    public boolean hasErrors() {
        return this.getErrors().size() > 0;
    }
}
