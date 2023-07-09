package com.example.RecipeHub.eventListeners.events;

import org.springframework.context.ApplicationEvent;

import com.example.RecipeHub.dtos.ForgottenPasswordDto;

public class ForgotPasswordEvent extends ApplicationEvent {
    private ForgottenPasswordDto userPasswordChangeDto;
    public ForgotPasswordEvent(ForgottenPasswordDto userPasswordChangeDto) {
        super(userPasswordChangeDto);
        this.userPasswordChangeDto = userPasswordChangeDto;
    }

    public ForgottenPasswordDto getUserPasswordChangeDto() {
        return userPasswordChangeDto;
    }

    public void setUserPasswordChangeDto(ForgottenPasswordDto userPasswordChangeDto) {
        this.userPasswordChangeDto = userPasswordChangeDto;
    }
}
