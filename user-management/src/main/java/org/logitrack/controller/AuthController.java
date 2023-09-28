package org.logitrack.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.logitrack.dto.request.AdminLoginRequest;
import org.logitrack.dto.request.AppUserLoginRequest;
import org.logitrack.dto.request.AppUserRegistrationRequest;
import org.logitrack.dto.request.DeliveryManCreatingRequest;
import org.logitrack.dto.response.ApiResponse;
import org.logitrack.dto.response.LoginResponse;
import org.logitrack.emails.EmailService;
import org.logitrack.entities.VerificationToken;
import org.logitrack.services.CustomerService;
import org.logitrack.services.CustomerServiceImpl.AdminServiceImpl;
import org.logitrack.validation.PasswordValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/auth")
public class AuthController {
    private final CustomerService userService;
    private final PasswordValidator passwordValidator;
    private final EmailService emailService;
    private final AdminServiceImpl adminService;

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid AppUserLoginRequest request) {
        log.info("request to login user");
        LoginResponse response = userService.login(request);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid AppUserRegistrationRequest request) {
        log.info("controller register: register user :: [{}] ::", request.getEmail());
        passwordValidator.isValid(request);
        ApiResponse response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create-delivery-man")
    public ResponseEntity<?> createDeliveryMan(@RequestBody DeliveryManCreatingRequest request){
        ApiResponse response = userService.registerDeliveryMan(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestBody VerificationToken tokenDTO) {
        String confirmationToken = tokenDTO.getConfirmationToken();
        String response = emailService.confirmEmail(confirmationToken);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/regenerate-token")
    public ResponseEntity<ApiResponse> regenerateVerificationTokenAndSendEmail(@RequestParam("email") String email) {
        ApiResponse response = userService.regenerateVerificationTokenAndSendEmail(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/loginAdmin")
    public ResponseEntity<ApiResponse> adminLogin(@RequestBody AdminLoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (adminService.adminLogin(email, password)) {
            ApiResponse successResponse = new ApiResponse("200", "Admin login successfully", HttpStatus.OK);
            return ResponseEntity.ok(successResponse);
        } else {
            ApiResponse errorResponse = new ApiResponse("Admin login failed", "error", HttpStatus.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

}
