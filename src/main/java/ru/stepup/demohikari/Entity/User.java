package ru.stepup.demohikari.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String username;

    public User(String username) {
        this.username = username;
    }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
