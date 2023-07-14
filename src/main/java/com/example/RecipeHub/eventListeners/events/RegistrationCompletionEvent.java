package com.example.RecipeHub.eventListeners.events;

import org.springframework.context.ApplicationEvent;

import com.example.RecipeHub.dtos.RegisterRequest;

public class RegistrationCompletionEvent extends ApplicationEvent {
    private RegisterRequest request;
    private String applicationPath;
    private String token;
    public RegistrationCompletionEvent(RegisterRequest registerRequest, String applicationPath, String token){
        super(registerRequest);
        this.request = registerRequest;
        this.applicationPath = applicationPath;
        this.token = token;
    }
	public RegisterRequest getRequest() {
		return request;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setRequest(RegisterRequest request) {
		this.request = request;
	}
	public String getApplicationPath() {
		return applicationPath;
	}
	public void setApplicationPath(String applicationPath) {
		this.applicationPath = applicationPath;
	}
    
    
}