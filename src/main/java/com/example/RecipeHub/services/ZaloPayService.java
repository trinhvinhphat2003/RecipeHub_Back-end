package com.example.RecipeHub.services;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ZaloPayService {
    void createPayment() throws IOException, URISyntaxException;
}
