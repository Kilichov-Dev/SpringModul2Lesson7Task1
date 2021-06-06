package uz.pdp.muharrir.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.muharrir.entity.Role;
import uz.pdp.muharrir.entity.User;
import uz.pdp.muharrir.entity.enums.PermissionType;
import uz.pdp.muharrir.repository.RoleRepository;
import uz.pdp.muharrir.repository.UserRepository;
import uz.pdp.muharrir.utils.AppConstans;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Value("${spring.datasource.initialization-mode}")
    private String modeType;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (modeType.equals("always")) {


            PermissionType[] permissionTypes = PermissionType.values();
            Role admin = roleRepository.save(new Role(
                    AppConstans.ADMIN,
                    Arrays.asList(permissionTypes),
                    "Sistema egasi"
            ));

            Role user = roleRepository.save(new Role(
                    AppConstans.USER,
                    Arrays.asList(
                            PermissionType.DELETE_MY_COMMENT,
                            PermissionType.ADD_COMMENT, PermissionType.EDIT_COMMENT),
                    "Oddiy foydalanuvchi"
            ));

            userRepository.save(new User(
                    "Admin",
                    "admin",
                    passwordEncoder.encode("admin123"),
                    admin,
                    true
            ));

            userRepository.save(new User(
                    "User",
                    "user",
                    passwordEncoder.encode("user123"),
                    user,
                    true
            ));
        }

    }
}
