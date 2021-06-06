package uz.pdp.muharrir.payload;

import lombok.Data;
import uz.pdp.muharrir.entity.enums.PermissionType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.security.Permission;
import java.util.List;

@Data
public class RoleDto {

    @NotBlank
    private String name;
    private String description;
    @NotEmpty
    private List<PermissionType> permissionList;
}
