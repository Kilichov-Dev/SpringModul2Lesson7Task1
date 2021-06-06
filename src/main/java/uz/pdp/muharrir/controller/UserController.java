package uz.pdp.muharrir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.muharrir.entity.User;
import uz.pdp.muharrir.payload.ApiResponse;
import uz.pdp.muharrir.payload.UserDto;
import uz.pdp.muharrir.service.UserService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @PreAuthorize(value = "hasAnyAuthority(ADD_USER)")
    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto) throws MessagingException {
        ApiResponse apiResponse = userService.addUser(userDto);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority(DELETE_USER)")
    @DeleteMapping("/deleteMyAccount")
    public ResponseEntity<?> deleteMyAccount() {
        ApiResponse apiResponse = userService.deleteMyAccount();
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority(DELETE_MY_ACCOUNT)")
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam String username) {
        ApiResponse apiResponse = userService.deleteUser(username);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority(EDIT_USER)")
    @PatchMapping("/editUser")
    public ResponseEntity<?> editUser(@RequestParam String username, @Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.editUser(username, userDto);
        if (apiResponse.isSuccess()) return ResponseEntity.ok(apiResponse);
        return ResponseEntity.status(409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAnyAuthority(VIEW_USER)")
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Integer page, @RequestParam Integer size) {
        Page<User> users = userService.getUserPage(page, size);
        if (users.isEmpty()) return ResponseEntity.status(409).body(users);
        return ResponseEntity.ok(users);
    }
}
