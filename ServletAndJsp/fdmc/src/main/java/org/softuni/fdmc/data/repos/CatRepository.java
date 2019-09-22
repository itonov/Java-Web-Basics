package org.softuni.fdmc.data.repos;

import org.softuni.fdmc.data.models.Cat;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CatRepository {

    private Set<Cat> cats;

    public CatRepository() {
        this.cats = new HashSet<>();
    }

    public Cat getByName(String catName) {
        return this.cats
                .stream()
                .filter(x -> x.getName().equals(catName))
                .findFirst()
                .orElse(null);
    }

    public Set<Cat> getAllCats() {
        return Collections.unmodifiableSet(this.cats);
    }

    public void addCat(Cat cat) {
        this.cats.add(cat);
    }

    public Cat getCatByName(String name) {
        return this.cats.stream().filter(c -> c.getName().equals(name)).findFirst().get();
    }
}
