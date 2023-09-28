package org.logitrack.services;

import org.logitrack.dto.request.AppUserLoginRequest;
import org.logitrack.dto.request.AppUserRegistrationRequest;
import org.logitrack.dto.request.DeliveryManCreatingRequest;
import org.logitrack.dto.response.ApiResponse;
import org.logitrack.dto.response.LoginResponse;
import org.springframework.stereotype.Service;

public interface CustomerService {
    ApiResponse registerUser(AppUserRegistrationRequest registrationDTO);
    ApiResponse registerDeliveryMan(DeliveryManCreatingRequest request);
    LoginResponse login(AppUserLoginRequest loginDTO);
    ApiResponse regenerateVerificationTokenAndSendEmail(String email);
}
