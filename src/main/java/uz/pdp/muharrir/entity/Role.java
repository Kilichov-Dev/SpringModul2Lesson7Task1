package uz.pdp.muharrir.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.muharrir.entity.enums.PermissionType;
import uz.pdp.muharrir.entity.enums.RoleType;
import uz.pdp.muharrir.entity.template.AbcEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends AbcEntity {
    @Column(unique = true, nullable = false)
    private String name;
    //
//    @Enumerated(value = EnumType.STRING)
//    private RoleType roleName;
    @Enumerated(value = EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<PermissionType> permissionTypeList;
    @Column(length = 600)
    private String description;
}
