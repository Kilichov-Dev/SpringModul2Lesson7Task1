package uz.pdp.muharrir.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {

    @NotNull(message = "Username bosh bo'lmasin!")
    private String username;

    @NotNull(message = "fullName bosh bo'lmasin!")
    private String fullName;

    @NotNull(message = "password bosh bo'lmasin!")
    private String password;

    @NotNull(message = "Role bosh bo'lmasin!")
    private Long roleId;

}
