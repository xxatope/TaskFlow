package ru.edu.taskflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.taskflow.entity.Role;
import ru.edu.taskflow.entity.RoleName;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);

}
