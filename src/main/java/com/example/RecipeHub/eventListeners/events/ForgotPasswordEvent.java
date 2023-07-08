package com.example.RecipeHub.eventListeners.events;

import com.example.RecipeHub.client.dtos.ForgottenPasswordDto;
import org.springframework.context.ApplicationEvent;

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
