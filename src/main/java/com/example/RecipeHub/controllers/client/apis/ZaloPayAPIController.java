package com.example.RecipeHub.controllers.client.apis;

import com.example.RecipeHub.client.dtos.response.ZaloPayResponse;
import com.example.RecipeHub.configs.ZaloPayConfiguration;
import com.example.RecipeHub.services.ZaloPayService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.DatatypeConverter;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/zalo-pay")
public class ZaloPayAPIController {
    private final ZaloPayService zaloPayService;

    public ZaloPayAPIController(ZaloPayService zaloPayService) throws Exception {
        this.zaloPayService = zaloPayService;
    }

    @GetMapping(path = "/create-order")
    public ResponseEntity<ZaloPayResponse> createPayment(HttpServletResponse httpServletResponse) throws Exception {
        ZaloPayResponse response = zaloPayService.createPayment();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/callback")
    public String callback(@RequestBody String jsonStr) {
        zaloPayService.checkCallback(jsonStr);
        return "";
    }
}
