package de.server.repository.userAndSelfServiceStand;

import de.server.model.userAndSelfServiceStand.SelfServiceStandUserNr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelfServiceStandUserNrRepository extends JpaRepository<SelfServiceStandUserNr, Integer> {

    boolean existsBySelfServiceStandNr_SelfServiceStandNIdAndUserNr_UserNameAndClRoleNr_RoleName(Integer selfServiceStandNId, String userName, String roleName);

    boolean existsBySelfServiceStandNr_SelfServiceStandNIdAndUserNr_UserId(Integer selfServiceStandNId, Integer userId);

    boolean existsBySelfServiceStandNr_SelfServiceStandNameAndUserNr_UserName(String selfServiceStandName, String userName);

    List<SelfServiceStandUserNr> findBySelfServiceStandNr_SelfServiceStandNId(Integer selfServiceStandNId);

    boolean existsBySelfServiceStandNr_SelfServiceStandNIdAndClRoleNr_RoleNameAndUserNr_UserName(Integer selfServiceStandNId, String roleName, String userName);

    SelfServiceStandUserNr findBySelfServiceStandNr_SelfServiceStandNameAndUserNr_UserName(String selfServiceStandName, String userName);

    List<SelfServiceStandUserNr> findByUserNr_UserName(String userName);


}