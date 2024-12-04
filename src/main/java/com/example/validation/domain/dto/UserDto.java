package com.example.validation.domain.dto;

import com.example.validation.error.validation.object.FieldsValueMatch;
import com.example.validation.error.validation.field.PhoneNumberConstraint;
import com.example.validation.error.validation.field.TempServiceValidation;
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
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "repeatEmail",
                message = "Почты не совпадают"
        ),
})
public class UserDto {

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 5, message = "Имя должно быть длиннее 5 символов")
    @Size(max = 50, message = "Имя должно быть короче 50 символов")
    private String username;

    @NotBlank(message = "Почта не может быть пустой")
    private String email;

    @NotNull(message = "Повтор почты должен быть заполнен")
    private String repeatEmail;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @NotNull(message = "Повтор пароля должен быть заполнен")
    private String repeatPassword;

    @PhoneNumberConstraint
    private String phoneNumber;

    @TempServiceValidation
    private Boolean temp;

}
