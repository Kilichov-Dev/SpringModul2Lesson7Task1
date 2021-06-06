package uz.pdp.muharrir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.muharrir.component.MailSender;
import uz.pdp.muharrir.entity.Role;
import uz.pdp.muharrir.entity.User;
import uz.pdp.muharrir.payload.ApiResponse;
import uz.pdp.muharrir.payload.UserDto;
import uz.pdp.muharrir.repository.RoleRepository;
import uz.pdp.muharrir.repository.UserRepository;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    MailSender mailSender;
    @Autowired
    PasswordEncoder passwordEncoder;

    public ApiResponse addUser(UserDto userDto) throws MessagingException {
        boolean existsByUsername = userRepository.existsByUsername(userDto.getUsername());
        if (existsByUsername) {
            return new ApiResponse("Bunday usernameb bor!", false);
        }
        Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
        if (!optionalRole.isPresent()) {
            return new ApiResponse("Role not found1", false);
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setRole(optionalRole.get());
        user.setFullName(userDto.getFullName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        boolean mailTextAdd = mailSender.mailTextAdd(userDto.getUsername());
        if (mailTextAdd) {
            return new ApiResponse("Emailni tasdiqlang! Please", true);
        }
        return new ApiResponse("Error! Sending message!", false);
    }

    public ApiResponse deleteMyAccount() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userRepository.deleteById(user.getId());
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }
    }

    public ApiResponse deleteUser(String username) {
        try {
            Optional<User> byUsername = userRepository.findByUsername(username);
            if (!byUsername.isPresent()) return new ApiResponse("User not found", false);
            User user = byUsername.get();
            userRepository.deleteById(user.getId());
            return new ApiResponse("Succesfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting", false);
        }

    }

    public ApiResponse editUser(String username, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found!", false);
        }
        Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
        if (!optionalRole.isPresent()) {
            return new ApiResponse("Role not found!", false);
        }

        User user = optionalUser.get();
        user.setUsername(userDto.getUsername());
        user.setRole(optionalRole.get());
        user.setFullName(userDto.getFullName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ApiResponse("User Successfully editing!", true);

    }

    public Page<User> getUserPage(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<User> all = userRepository.findAll(pageable);
        return all;

    }
}
