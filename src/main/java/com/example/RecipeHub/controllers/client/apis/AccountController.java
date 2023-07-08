package com.example.RecipeHub.controllers.client.apis;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.client.dtos.ForgottenPasswordDto;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.eventListeners.events.ForgotPasswordEvent;
import com.example.RecipeHub.eventListeners.events.PasswordChangeSuccessEvent;
import com.example.RecipeHub.services.AccountService;
import com.example.RecipeHub.services.RegisterService;

@RestController
@RequestMapping("/api/v1/")
public class AccountController {
	private final RegisterService registerService;
    private final AccountService accountService;
    private final ApplicationEventPublisher eventPublisher;

    public AccountController(RegisterService registerService, AccountService accountService, ApplicationEventPublisher eventPublisher) {
        this.registerService = registerService;
        this.accountService = accountService;
        this.eventPublisher = eventPublisher;
    }
    
    @PutMapping(path = "user/change-password/{newPassword}")
    public ResponseEntity<?> changePassword(@PathVariable("newPassword") String newPassword, @AuthenticationPrincipal User user){
        // change password
        accountService.changePassword(newPassword, user);
        //inform password change
        eventPublisher.publishEvent(new PasswordChangeSuccessEvent(user));
        return null;
    }

    @PutMapping(path = "global/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgottenPasswordDto forgottenPasswordDto) throws Exception {
        // generate password & save to user account
    	forgottenPasswordDto = accountService.generatePassword(forgottenPasswordDto);
        // send email notify new password
        eventPublisher.publishEvent(new ForgotPasswordEvent(forgottenPasswordDto));
        return null;
    }   
}
