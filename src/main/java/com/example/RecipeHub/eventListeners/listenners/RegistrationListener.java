package com.example.RecipeHub.eventListeners.listenners;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.RecipeHub.eventListeners.events.RegistrationCompletionEvent;
import com.example.RecipeHub.services.RegisterService;

@Component
public class RegistrationListener{
    private final RegisterService registerService;

    public RegistrationListener(RegisterService registerService) {
		super();
		this.registerService = registerService;
	}

    @Async
    @EventListener
    public void onApplicationEvent(RegistrationCompletionEvent event) {
        try {
			completeRegistration(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void completeRegistration(RegistrationCompletionEvent event) throws Exception {
        // send email
        registerService.sendVerificationEmail(event.getRequest(), event.getApplicationPath(), event.getToken());
    }
}