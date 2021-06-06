package uz.pdp.muharrir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.muharrir.entity.Role;
import uz.pdp.muharrir.payload.ApiResponse;
import uz.pdp.muharrir.payload.RoleDto;
import uz.pdp.muharrir.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public ApiResponse addRole(RoleDto roleDto) {
        boolean existsByName = roleRepository.existsByName(roleDto.getName());
        if (existsByName) {
            return new ApiResponse("Bunday lavozim mavjud!", false);
        }
        Role role = new Role(
                roleDto.getName(),
                roleDto.getPermissionList(),
                roleDto.getDescription());
        roleRepository.save(role);
        return new ApiResponse("Success added!", true);
    }

    public ApiResponse editRole(Long id, RoleDto roleDto) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (!optionalRole.isPresent()) {
            return new ApiResponse("Role not found!", false);
        }
        Role role = optionalRole.get();
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());

        role.setPermissionTypeList(roleDto.getPermissionList());
        roleRepository.save(role);
        return new ApiResponse("Successfully editing", true);
    }

    public ApiResponse deleteRole(Long id) {
        try {

            Optional<Role> optionalRole = roleRepository.findById(id);
            if (!optionalRole.isPresent()) {
                return new ApiResponse("Role not found!", false);
            }
            roleRepository.deleteById(id);
            return new ApiResponse("Role deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting!", false);
        }
    }

    public List<Role> getAll() {
        List<Role> all = roleRepository.findAll();
        return all;
    }

}
//KV18138541630
