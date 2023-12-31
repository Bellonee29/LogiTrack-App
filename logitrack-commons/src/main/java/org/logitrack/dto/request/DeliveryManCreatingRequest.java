package org.logitrack.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.logitrack.enums.Gender;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryManCreatingRequest {
    @NotBlank(message = "FullName cannot be empty")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "FirstName can only have letters and spaces")
    private String fullName;
    @jakarta.validation.constraints.Email
    @NotBlank(message = "email cannot be empty")
    private String Email;
    @NotBlank(message = " phoneNumber cannot be empty")
    @Size(message = "phoneNumber must be atLeast 11",max = 14)
    private String phoneNumber;
    @NotBlank(message = "city cannot be empty")
    private String city;
    @NotBlank(message = "gender cannot be empty")
    private Gender gender;
    @NotBlank(message = "address cannot be empty")
    private String address;
    @NotBlank(message = "drivingLicenseNumber cannot be empty")
    private String drivingLicenseNumber;
    @NotBlank(message = "state cannot be empty")
    private String state;
    @NotBlank(message = "birthday cannot be empty")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;

    // "gender":"MALE",
    // "address":"02 olowucompond olayaju kwara state",
    // "state":"Kwara",
    // "city":"Idofian",
    // "drivingLicenseNumber":"23454657859",
    // "birthday":

}
