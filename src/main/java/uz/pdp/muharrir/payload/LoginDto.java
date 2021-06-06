package uz.pdp.muharrir.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data

public class LoginDto {
    @NotNull(message = "Username bosh bo'lmasin!")
    private String username;

    @NotNull(message = "password bosh bo'lmasin!")
    private String password;

}

