package com.example.RecipeHub.eventListeners;


import com.example.RecipeHub.services.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationListener {
    private final RegisterService registerService;

    @EventListener
    public void completeRegistration(RegistrationCompletionEvent registrationCompletionEvent){
        // create token, send email
//        registerService.createVerificationToken();

    }
}
