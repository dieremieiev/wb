package net.hrobotics.wb.bot.telegram.model;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({
//        "id",
//        "username",
        "first_name",
        "last_name"})
public class User {
    private Long id;
    private String username;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

