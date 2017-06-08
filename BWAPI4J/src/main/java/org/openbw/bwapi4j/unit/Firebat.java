package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Firebat extends MobileUnit implements Organic {

    private boolean isStimmed;

    protected Firebat(int id) {
        super(id, UnitType.Terran_Firebat);
    }

    @Override
    public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        return super.initialize(unitData, index, allUnits);
    }

    @Override
    public int update(int[] unitData, int index) {

        this.isStimmed = unitData[Unit.IS_STIMMED_INDEX] == 1;
        return super.update(unitData, index);
    }

    public boolean isStimmed() {
        return this.isStimmed;
    }

    public boolean stimPack() {
        return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, -1, -1, TechType.Stim_Packs.getId());
    }
}
