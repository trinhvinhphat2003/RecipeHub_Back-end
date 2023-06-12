package com.example.RecipeHub.eventListeners;


import com.example.RecipeHub.services.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationCompletionEvent> {
    private final RegisterService registerService;

    public RegistrationListener(RegisterService registerService) {
        super();
        this.registerService = registerService;
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
        registerService.sendVerificationEmail(event.getRequest(), event.getApplicationPath(), token);
    }
}