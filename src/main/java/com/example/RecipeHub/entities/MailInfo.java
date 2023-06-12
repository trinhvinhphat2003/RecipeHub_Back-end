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

	public MailInfo(String receiver, String subject, String htmlTemplateName, Map<String, Object> properties) {
		this.receiver = receiver;
		this.subject = subject;
		this.htmlTemplateName = htmlTemplateName;
		this.properties = properties;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getHtmlTemplateName() {
		return htmlTemplateName;
	}
	public void setHtmlTemplateName(String htmlTemplateName) {
		this.htmlTemplateName = htmlTemplateName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Map<String, Object> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

}
