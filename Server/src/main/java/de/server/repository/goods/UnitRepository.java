package de.server.repository.goods;

import de.server.model.goods.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Contains the database query of the table Unit.
 */
@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {

    //Query list of all units, sorted by unit name
    List<Unit> findByOrderByUnitNameAsc();

    //Query, unit with the name exists.
    boolean existsByUnitNameIs(String unitName);

    //Query finds a unit with the name.
    Unit findByUnitName(String unitName);

}