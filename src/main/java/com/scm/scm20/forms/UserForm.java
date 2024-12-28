package com.scm.scm20.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message="Username is Required")
    @Size(min = 3, message = "Minimum 3 characters are Required")
    private String name;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 6, message = "Minimum 8 characters are Required")
    private String password;

    @NotBlank(message = "Phone Number is Required")
    @Size(min = 10, max = 10,  message = "Minimum 8 characters are Required")
    private String phoneNumber;

    @NotBlank(message = "About is Required")
    private String about;
}
