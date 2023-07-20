package com.example.RecipeHub.services;

import com.example.RecipeHub.client.dtos.response.ZaloPayResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ZaloPayService {
    ZaloPayResponse createPayment() throws Exception;
    void checkCallback(String jsonStr);
}
