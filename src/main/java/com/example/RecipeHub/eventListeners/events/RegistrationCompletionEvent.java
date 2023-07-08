package com.example.RecipeHub.eventListeners.events;

import org.springframework.context.ApplicationEvent;

import com.example.RecipeHub.client.dtos.RegisterRequest;

public class RegistrationCompletionEvent extends ApplicationEvent {
    private RegisterRequest request;
    private String applicationPath;
    public RegistrationCompletionEvent(RegisterRequest registerRequest, String applicationPath){
        super(registerRequest);
        this.request = registerRequest;
        this.applicationPath = applicationPath;
    }
	public RegisterRequest getRequest() {
		return request;
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