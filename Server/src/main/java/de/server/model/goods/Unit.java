package de.server.model.goods;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class represents the table "tb_unit".
 */
@Setter
@Getter
@Table(name = "tb_unit")
@Entity
public class Unit {

    /**
     * The ID of the unit.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_unit_nr", nullable = false)
    private Integer unitId;

    /**
     * The name of the unit.
     */
    @Column(name = "cl_unit_name", length = 30)
    private String unitName;

    /**
     * An empty constructor.
     */
    public Unit() {
    }

    /**
     * Unit constructor, sets the unit name.
     *
     * @param unitName The name of the unit.
     */
    public Unit(String unitName) {
        this.unitName = unitName;
    }
}