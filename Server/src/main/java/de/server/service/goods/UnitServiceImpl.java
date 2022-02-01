package de.server.service.goods;

import de.server.model.goods.Unit;
import de.server.repository.goods.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class contains methods that are used to manage the unit.
 */
@Service
public class UnitServiceImpl implements UnitService {

    /**
     * unitRepository to handle unit information.
     */
    private final UnitRepository unitRepository;


    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    /**
     * The method only changes the name of a unit.
     *
     * @param oldUnitName old unit name
     * @param newUnitName new unit name
     * @return new unit name
     */
    public Unit updateUnitEntry(String oldUnitName, String newUnitName) {

        Unit changeUnit = new Unit();
        changeUnit.setUnitId(unitRepository.findByUnitName(oldUnitName).getUnitId());

        changeUnit.setUnitName(newUnitName);

        //change unit at the database
        unitRepository.save(changeUnit);

        return changeUnit;
    }

    /**
     * Method returns a sorted list with all units.
     *
     * @return json list with all units
     */
    public List<Unit> getUnitsList() {
        return unitRepository.findByOrderByUnitNameAsc();
    }

    /**
     * Method creates a new unit.
     *
     * @param unitName The name of the unit.
     * @return The new unit.
     */
    public Unit createUnitEntry(String unitName) {
        return unitRepository.save(new Unit(unitName));
    }

    /**
     * The method only deletes a unit.
     *
     * @param unitName The unit name.
     * @return unit delete, true or false.
     */
    public boolean deleteUnitEntry(String unitName) {

        boolean unitDelete = false;

        if (unitRepository.existsByUnitNameIs(unitName)) {
            Unit deleteUnit = new Unit();
            deleteUnit.setUnitId(unitRepository.findByUnitName(unitName).getUnitId());

            //Delete Unit at the database
            unitRepository.delete(deleteUnit);

            unitDelete = true;
        }
        return unitDelete;
    }
}
