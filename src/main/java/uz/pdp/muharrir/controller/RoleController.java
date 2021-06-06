package uz.pdp.muharrir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.muharrir.aop.CheckPermission;
import uz.pdp.muharrir.entity.Role;
import uz.pdp.muharrir.payload.ApiResponse;
import uz.pdp.muharrir.payload.RoleDto;
import uz.pdp.muharrir.service.RoleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @PostMapping
    public HttpEntity<?> addRole(@Valid @RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = roleService.addRole(roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    //    @PreAuthorize(value = "hasAuthority('ADD_ROLE')")
    @CheckPermission(value = "EDIT_ROLE")
    @PutMapping("{id}")
    public HttpEntity<?> editRole(@PathVariable Long id, @Valid @RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = roleService.editRole(id, roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_ROLE')")
    @DeleteMapping("/id")
    public HttpEntity<?> deleteRole(@RequestParam Long id) {
        ApiResponse apiResponse = roleService.deleteRole(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('VIEW_ROLE')")
    @GetMapping()
    public HttpEntity<?> getAll() {
        List<Role> all = roleService.getAll();
        if (all.isEmpty()) return ResponseEntity.status(409).body(all);
        return ResponseEntity.ok(all);
    }
}
