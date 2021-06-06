package uz.pdp.muharrir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.awt.AppContext;
import uz.pdp.muharrir.entity.User;
import uz.pdp.muharrir.exceptions.ResourceNotFoundException;
import uz.pdp.muharrir.payload.ApiResponse;
import uz.pdp.muharrir.payload.RegistrDto;
import uz.pdp.muharrir.repository.RoleRepository;
import uz.pdp.muharrir.repository.UserRepository;
import uz.pdp.muharrir.utils.AppConstans;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse registerUser(RegistrDto registrDto) {
        if (!registrDto.getPassword().equals(registrDto.getPrePassword())) {
            return new ApiResponse("Passwordlar bir biriga mos emas!", false);
        }
        boolean existsByUsername = userRepository.existsByUsername(registrDto.getUsername());

        if (existsByUsername) {
            return new ApiResponse("This name already exists!", false);
        }
        User user = new User(
                registrDto.getFullName(),
                registrDto.getUsername(),

                passwordEncoder.encode(registrDto.getPassword()),
                roleRepository.findByName(AppConstans.USER).orElseThrow(() -> new ResourceNotFoundException("Role", "name", AppConstans.USER)),
                true

        );
        userRepository.save(user);
        return new ApiResponse("Success registerd!", true);

    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public ApiResponse verifyEmail(String email) {
        Optional<User> optionalUser = userRepository.findByUsername(email);
        if (!optionalUser.isPresent()) {
            return new ApiResponse("Email not found!", false);
        }
        User user = optionalUser.get();
        user.setEnabled(true);
        userRepository.save(user);
        return new ApiResponse("Your email has been verified, WELLCOME!!!", true);

    }
}
