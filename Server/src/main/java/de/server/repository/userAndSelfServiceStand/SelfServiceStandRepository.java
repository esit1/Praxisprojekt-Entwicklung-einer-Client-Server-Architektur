package de.server.repository.userAndSelfServiceStand;

import de.server.model.userAndSelfServiceStand.SelfServiceStand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfServiceStandRepository extends JpaRepository<SelfServiceStand, Integer> {

    SelfServiceStand findBySelfServiceStandName(String selfServiceStandName);

    SelfServiceStand findBySelfServiceStandNId(Integer selfServiceStandNId);

    boolean existsBySelfServiceStandName(String selfServiceStandName);


}