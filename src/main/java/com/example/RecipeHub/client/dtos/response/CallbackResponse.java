package com.example.RecipeHub.client.dtos.response;

public class CallbackResponse {
    public static final int SUCCESS = 1;
    public static final int INVALID_CALLBACK = -1;
    public static final int CALLBACK_AGAIN = 0;
    public static final int DUPLICATED_APPID_OR_ZLTRANSID = 2;
    private int returncode;
    private String returnmessage;

    public CallbackResponse() {
    }

    public CallbackResponse(int returncode, String returnmessage) {
        this.returncode = returncode;
        this.returnmessage = returnmessage;
    }

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getReturnmessage() {
        return returnmessage;
    }

    public void setReturnmessage(String returnmessage) {
        this.returnmessage = returnmessage;
    }
}
