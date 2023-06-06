package com.example.RecipeHub.eventListeners;

import com.example.RecipeHub.dtos.RegisterRequest;
import lombok.*;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompletionEvent extends ApplicationEvent {
    private RegisterRequest request;
    private String applicationPath;
    public RegistrationCompletionEvent(RegisterRequest registerRequest, String applicationPath){
        super(registerRequest);
        this.request = registerRequest;
        this.applicationPath = applicationPath;
    }
}
