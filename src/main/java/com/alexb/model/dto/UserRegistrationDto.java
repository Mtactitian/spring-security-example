package com.alexb.model.dto;

import com.alexb.constraint.annotations.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@PasswordMatches
public class UserRegistrationDto {

    @Length(min = 2, max = 20)
    private String username;

    @Length(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;

    private String confirmationPassword;

}
