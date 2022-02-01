package de.server.repository.userAndSelfServiceStand;

import de.server.model.userAndSelfServiceStand.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The class contains queries related to the user role.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    //Query list with all user roles, sorted by role name
    List<Role> getByOrderByRoleNameAsc();

    Role findByRoleName(String roleName);
}