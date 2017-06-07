package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Hydralisk extends MobileUnit implements Organic, Burrowable {

    private boolean burrowed;
    
    Hydralisk(int id) {
        super(id, UnitType.Zerg_Hydralisk);
    }
    
    @Override
    public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        this.burrowed = false;
        return super.initialize(unitData, index, allUnits);
    }

    @Override
    public int update(int[] unitData, int index) {

        this.burrowed = unitData[index + Unit.IS_BURROWED_INDEX] == 1;
        return super.update(unitData, index);
    }
    
    @Override
    public boolean burrow() {
        return issueCommand(this.id, UnitCommandType.Burrow.ordinal(), -1, -1, -1, -1);
    }

    @Override
    public boolean unburrow() {
        return issueCommand(this.id, UnitCommandType.Unburrow.ordinal(), -1, -1, -1, -1);
    }

    @Override
    public boolean isBurrowed() {
        return this.burrowed;
    }
    
    public boolean morph() {
        return issueCommand(this.id, UnitCommandType.Morph.ordinal(), -1, -1, -1, UnitType.Zerg_Lurker.getId());
    }
}
