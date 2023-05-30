package com.example.RecipeHub.eventListeners;

import org.springframework.context.ApplicationEvent;

public class RegistrationCompletionEvent extends ApplicationEvent {
    public RegistrationCompletionEvent(Object source){
        super(source);
    }
}
