package com.scaffold.template.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User {
    private Long id;

    @NotNull(message = "El usuario debe tener un nombre de usuario")
    private String userName;

    @NotNull
    @Email(message = "El mail debe ser un mail valido!")
    private String email;
}
