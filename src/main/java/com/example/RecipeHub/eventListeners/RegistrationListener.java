package com.example.RecipeHub.eventListeners;


import com.example.RecipeHub.services.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<RegistrationCompletionEvent> {
    private final RegisterService registerService;

    @SneakyThrows
    @Override
    public void onApplicationEvent(RegistrationCompletionEvent event) {
        completeRegistration(event);
    }

    private void completeRegistration(RegistrationCompletionEvent event) throws Exception {
        // create token
        String token = registerService.createVerificationToken(event.getRequest().getEmail());
        // send email
        registerService.sendVerificationEmail(event.getRequest(), event.getApplicationPath(), token);
    }
}
