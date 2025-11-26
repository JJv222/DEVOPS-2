package com.piis.cinema.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "Create user request",
        description = "Schema to hold user information"
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Schema(
            description = "Worker name",
            example = "Jan"
    )
    @NotBlank
    private String name;
    @Schema(
            description = "Worker surname",
            example = "Nowak"
    )
    @NotBlank
    private String surname;
    @Schema(
            description = "user phone",
            example = "+48 123 456 789"
    )
    @NotBlank
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String phone;
    @Schema(
            description = "User email",
            example = "user@gmail.com"
    )
    @NotBlank
    @Email
    private String email;
    @Schema(
            description = "Password",
            example = "password"
    )
    @NotBlank
    private String password;
    @Schema(
            description = "User role",
            example = "WORKER"
    )
    @NotBlank
    private String role;
}
