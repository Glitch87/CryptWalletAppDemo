package ru.Tkachenko.cryptwalletappdemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class RegistrationDto {
    @Length(min = 5, max = 25, message = "Name length must be from 5 to 25")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Name must contain only letters a-z, A-Z and numbers")
    @Schema(description = "Username", example = "user12345",accessMode = Schema.AccessMode.WRITE_ONLY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String username;
    @Length(min = 10, max = 60, message = "Name length must be from 10 to 60")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$", message = "Email must contain only letters a-z,A-Z, numbers")
    @Schema(description = "Email", example = "user12345@mail.com",accessMode = Schema.AccessMode.WRITE_ONLY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;
    @Schema(example ="xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",accessMode = Schema.AccessMode.READ_ONLY)
    private String secretKey;

    public RegistrationDto(String secretKey) {
        this.secretKey = secretKey;
    }
    public RegistrationDto(String username, String email) {
        this.username=username;
        this.email=email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
