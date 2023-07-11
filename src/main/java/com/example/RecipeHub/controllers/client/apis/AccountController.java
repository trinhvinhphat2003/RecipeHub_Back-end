package com.example.RecipeHub.controllers.client.apis;

import com.example.RecipeHub.client.dtos.ForgottenPasswordDto;
import com.example.RecipeHub.client.dtos.request.RegisterRequest;
import com.example.RecipeHub.client.dtos.response.RegisterResponse;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.eventListeners.events.ForgotPasswordEvent;
import com.example.RecipeHub.eventListeners.events.PasswordChangeSuccessEvent;
import com.example.RecipeHub.eventListeners.events.RegistrationCompletionEvent;
import com.example.RecipeHub.services.AccountService;
import com.example.RecipeHub.services.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final RegisterService registerService;
    private final AccountService accountService;
    private final ApplicationEventPublisher eventPublisher;

    public AccountController(RegisterService registerService, AccountService accountService, ApplicationEventPublisher eventPublisher) {
        this.registerService = registerService;
        this.accountService = accountService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) throws Exception {
        // register account
        RegisterResponse registerResponse = registerService.register(registerRequest, request);
        // send verify email
        eventPublisher.publishEvent(new RegistrationCompletionEvent(registerRequest, getApplicationPath(request)));
        return ResponseEntity.ok(registerResponse);
    }

    @GetMapping(path = "/verify-user")
    public ResponseEntity<String> verifyUser(@RequestParam(name = "token") String verificationToken) throws Exception{
        return ResponseEntity.ok(registerService.verifyUser(verificationToken));
    }

    @PutMapping(path = "/change-password")
    public ResponseEntity<?> changePassword(@RequestParam("newPassword") String newPassword, @AuthenticationPrincipal User user){
        // change password
        accountService.changePassword(newPassword, user);
        //inform password change
        eventPublisher.publishEvent(new PasswordChangeSuccessEvent(user));
        return null;
    }

    @PutMapping(path = "/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgottenPasswordDto forgottenPasswordDto) throws Exception {
        // generate password & save to user account
        accountService.generatePassword(forgottenPasswordDto);
        // send email notify new password
        eventPublisher.publishEvent(new ForgotPasswordEvent(forgottenPasswordDto));
        return null;
    }

    private String getApplicationPath(HttpServletRequest request){
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
