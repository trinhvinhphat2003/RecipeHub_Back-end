package com.example.RecipeHub.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.RecipeHub.dtos.RegisterRequest;
import com.example.RecipeHub.dtos.RegisterResponse;
import com.example.RecipeHub.dtos.UserEntityJsonBinding;
import com.example.RecipeHub.entities.MailInfo;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.entities.VerificationToken;
import com.example.RecipeHub.enums.LoginType;
import com.example.RecipeHub.enums.RegisterStatusResponse;
import com.example.RecipeHub.errorHandlers.BadRequestExeption;
import com.example.RecipeHub.mappers.UserMapper;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.repositories.VerificationTokenRepository;
import com.example.RecipeHub.services.EmailService;
import com.example.RecipeHub.services.JwtService;
import com.example.RecipeHub.services.RegisterService;
import com.example.RecipeHub.utils.SystemUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
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

    
    
    public RegisterServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, UserRepository userRepository,
			VerificationTokenRepository verificationTokenRepository, JwtService jwtService, EmailService emailService) {
		super();
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.verificationTokenRepository = verificationTokenRepository;
		this.jwtService = jwtService;
		this.emailService = emailService;
	}

	@Override
    public String register(RegisterRequest registerRequest, HttpServletRequest httpServletRequest) throws Exception {

        // check if email has been register
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
        	throw new BadRequestExeption(RegisterStatusResponse.EMAIL_DUPLICATED.name());
        }

        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User user = userMapper.registerRequestToUser(registerRequest);
        user.setProfileImage(SystemUtil.getApplicationPath(httpServletRequest) + "/api/v1/global/image/avatar/default.jpg");
        user.setLoginType(LoginType.BASIC);
        UserEntityJsonBinding userBinding = new UserEntityJsonBinding(user.getEmail(), user.getPassword(), user.getFullName(), user.getProfileImage(), user.getBirthday(), user.getRole(), user.getGender(), user.getLoginType(), user.isEnable(), user.isBlocked());
        String userJson = new ObjectMapper().writeValueAsString(userBinding);
        System.out.println(userJson);
        //user = userRepository.save(user);

        //generate token
        String jwtToken = jwtService.generateTokenForRegisterVerification(userJson);
        VerificationToken verificationToken = new VerificationToken(null, jwtToken);
        verificationTokenRepository.save(verificationToken);
//        return RegisterResponse.builder()
//                .jwtToken(jwtToken)
//                .build();
        return jwtToken;
    }

	@Transactional
    @Override
    public String verifyUser(String token) throws Exception {
        // check token co dung voi user do khong
        VerificationToken verificationToken = verificationTokenRepository.findVerificationTokenByToken(token)
                .orElseThrow(() -> new RuntimeException(TOKEN_INVALID));
        if(jwtService.isTokenExpired(verificationToken.getToken())){
            throw new RuntimeException(TOKEN_EXPIRED);
        }
        UserEntityJsonBinding userBinding = new ObjectMapper().readValue(jwtService.extractSubject(token), UserEntityJsonBinding.class);
        User user = new User(userBinding.getEmail(), userBinding.getPassword(), userBinding.getRole(), userBinding.getFullName(), userBinding.getGender(), userBinding.isEnable(), userBinding.getBirthday(), userBinding.getProfileImage(), userBinding.getLoginType(), userBinding.isBlocked());
        user.setEnable(true);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        // chua return ne
        return TOKEN_VALID;
    }

    @Override
    public String createVerificationToken(String userEmail){
        String token = UUID.randomUUID().toString();
        User user = userRepository.findByEmail(userEmail).orElseThrow();
//        VerificationToken verificationToken = VerificationToken.builder()
//                .token(token)
//                .expirationDate(expirationDate)
//                .user(user)
//                .build();
        VerificationToken verificationToken = new VerificationToken(null, token);
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
        MailInfo mailInfo = new MailInfo(registerRequest.getEmail(), subject, VERIFICATION_EMAIL_HTML_TEMPLATE, properties);
        emailService.sendEmailUsingHTMLTemplate(mailInfo);
    }
}