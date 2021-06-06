package uz.pdp.muharrir.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.muharrir.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    boolean existsByName(String name);
}
