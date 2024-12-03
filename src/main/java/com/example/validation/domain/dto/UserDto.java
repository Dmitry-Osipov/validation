package com.example.validation.domain.dto;

import com.example.validation.error.validation.FieldsValueMatch;
import com.example.validation.error.validation.PhoneNumberConstraint;
import com.example.validation.error.validation.TempServiceValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "repeatPassword",
                message = "Пароли не совпадают"
        )
})
public class UserDto {

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 5, message = "Имя должно быть длиннее 5 символов")
    @Size(max = 50, message = "Имя должно быть короче 50 символов")
    private String username;

    private String password;

    @NotNull
    private String repeatPassword;

    @PhoneNumberConstraint
    private String phoneNumber;

    @TempServiceValidation
    private Boolean temp;

}
