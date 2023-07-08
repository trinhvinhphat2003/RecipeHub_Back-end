package com.example.RecipeHub.eventListeners.listeners;


import com.example.RecipeHub.eventListeners.events.RegistrationCompletionEvent;
import com.example.RecipeHub.services.RegisterService;
import com.example.RecipeHub.services.SpecificEmailService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationCompletionEvent> {
    private final RegisterService registerService;
    private final SpecificEmailService emailService;

    public RegistrationListener(RegisterService registerService, SpecificEmailService emailService) {
        super();
        this.registerService = registerService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompletionEvent event) {
        try {
			completeRegistration(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void completeRegistration(RegistrationCompletionEvent event) throws Exception {
        // create token
        String token = registerService.createVerificationToken(event.getRequest().getEmail());
        // send email
        emailService.sendRegisterVerificationEmail(event.getRequest(), event.getApplicationPath(), token);
    }
}