package com.example.RecipeHub.controllers.client.apis;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.dtos.ForgottenPasswordDto;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.eventListeners.events.ForgotPasswordEvent;
import com.example.RecipeHub.eventListeners.events.ForgotPasswordVerifiedMailEvent;
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
    public ResponseEntity<String> changePassword(@PathVariable("newPassword") String newPassword, @AuthenticationPrincipal User user){
        // change password
        accountService.changePassword(newPassword, user);
        //inform password change
        eventPublisher.publishEvent(new PasswordChangeSuccessEvent(user));
        return ResponseEntity.ok("you have changed your password successfully");
    }

    @PutMapping(path = "global/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgottenPasswordDto forgottenPasswordDto) throws Exception {
        // generate token
    	ForgotPasswordVerifiedMailEvent event = accountService.generateVerifyTokenEmailEvent(forgottenPasswordDto);
        // send verify email
        eventPublisher.publishEvent(event);
        return ResponseEntity.ok("a verified mail have been sent to your email");
    }  
    
    @GetMapping("global/forgot-password/verified/{token}")
    public ResponseEntity<String> verifiedForgotPassword(@PathVariable("token") String token) throws Exception {
        // verify token
    	ForgotPasswordEvent event = accountService.verifyForgotPasswordToken(token);
        // send email notify new password
        eventPublisher.publishEvent(event);
        return ResponseEntity.ok("a new password mail have been sent to your email");
    } 
}
