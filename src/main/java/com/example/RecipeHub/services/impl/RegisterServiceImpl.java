package com.example.RecipeHub.services.impl;

import com.example.RecipeHub.entities.MailInfo;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.entities.VerificationToken;
import com.example.RecipeHub.mappers.UserMapper;
import com.example.RecipeHub.dtos.RegisterRequest;
import com.example.RecipeHub.dtos.RegisterResponse;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.repositories.VerificationTokenRepository;
import com.example.RecipeHub.services.RegisterService;
import com.example.RecipeHub.services.EmailService;
import com.example.RecipeHub.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private static final long EXPIRATION_TIME = 86400000L;
    private static final String VERIFICATION_EMAIL_HTML_TEMPLATE = "verification-email";
    private static final String TOKEN_INVALID = "Token invalid";
    private static final String TOKEN_EXPIRED = "Token expired";
    private static final String TOKEN_VALID = "Success";

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {

        // check if email has been register
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new RuntimeException("Email " + registerRequest.getEmail() + " has been registered.");
        }

        // save account to database
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User user = userMapper.registerRequestToUser(registerRequest);
        user = userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
//        return RegisterResponse.builder()
//                .jwtToken(jwtToken)
//                .build();
        return new RegisterResponse(jwtToken);
    }

    @Override
    public String verifyUser(String token) throws Exception {
        // check token co dung voi user do khong
        VerificationToken verificationToken = verificationTokenRepository.findVerificationTokenByToken(token)
                .orElseThrow(() -> new RuntimeException(TOKEN_INVALID));
        if(verificationToken.getExpirationDate().before(new Date(System.currentTimeMillis()))){
            throw new RuntimeException(TOKEN_EXPIRED);
        }
        User user = verificationToken.getUser();
        user.setEnable(true);
        userRepository.save(user);
        // chua return ne
        return TOKEN_VALID;
    }

    @Override
    public String createVerificationToken(String userEmail){
        String token = UUID.randomUUID().toString();
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        User user = userRepository.findByEmail(userEmail).orElseThrow();
//        VerificationToken verificationToken = VerificationToken.builder()
//                .token(token)
//                .expirationDate(expirationDate)
//                .user(user)
//                .build();
        VerificationToken verificationToken = new VerificationToken(null, token, expirationDate, user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Override
    public void sendVerificationEmail(RegisterRequest registerRequest, String applicationPath, String token) throws Exception {
        final String subject = "Verify your email address";
        String verificationPath = applicationPath + "/api/v1/auth/verify-user?token=" + token;
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", registerRequest.getFullName());
        properties.put("verificationUrl", verificationPath);
        MailInfo mailInfo = MailInfo.builder()
                .receiver(registerRequest.getEmail())
                .subject(subject)
                .htmlTemplateName(VERIFICATION_EMAIL_HTML_TEMPLATE)
                .properties(properties)
                .build();
        emailService.sendEmailUsingHTMLTemplate(mailInfo);
    }
}