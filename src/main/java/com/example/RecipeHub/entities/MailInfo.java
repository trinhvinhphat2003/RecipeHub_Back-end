package com.example.RecipeHub.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class MailInfo {
    private String receiver;
    private String subject;
    private String htmlTemplateName;
    private String text;
    private Map<String, Object> properties;
}
