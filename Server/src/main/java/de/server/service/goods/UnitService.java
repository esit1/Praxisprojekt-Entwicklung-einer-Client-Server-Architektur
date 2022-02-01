package de.server.service.goods;

import de.server.model.goods.Unit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UnitService {

    /**
     * The method only deletes a unit.
     *
     * @param unitName The unit name.
     * @return unit delete, true or false.
     */
    boolean deleteUnitEntry(String unitName);

    /**
     * Method creates a new unit.
     *
     * @param unitName The name of the unit.
     * @return The new unit.
     */
    Unit createUnitEntry(String unitName);

    /**
     * Method returns a sorted list with all units.
     *
     * @return json list with all units
     */
    List<Unit> getUnitsList();

    /**
     * The method only changes the name of a unit.
     *
     * @param oldUnitName old unit name
     * @param newUnitName new unit name
     * @return new unit name
     */
    Unit updateUnitEntry(String oldUnitName, String newUnitName);

}
