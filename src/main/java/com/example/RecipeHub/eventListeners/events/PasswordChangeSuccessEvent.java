package com.example.RecipeHub.eventListeners.events;

import com.example.RecipeHub.entities.User;
import org.springframework.context.ApplicationEvent;

public class PasswordChangeSuccessEvent extends ApplicationEvent {
    private User user;

    public PasswordChangeSuccessEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
