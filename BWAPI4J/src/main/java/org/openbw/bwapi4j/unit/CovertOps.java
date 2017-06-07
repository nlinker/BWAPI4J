package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class CovertOps extends Addon implements Mechanical, ResearchingFacility {

    private Researcher researcher;

    CovertOps(int id, int timeSpotted) {
        super(id, UnitType.Terran_Covert_Ops, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
        return super.initialize(unitData, index, allUnits);
    }

    @Override
    public int update(int[] unitData, int index) {

        this.researcher.update(unitData, index);
        return super.update(unitData, index);
    }

    public boolean researchPersonnelCloaking() {
        return this.researcher.research(TechType.Personnel_Cloaking);
    }

    public boolean researchLockdown() {
        return this.researcher.research(TechType.Lockdown);
    }

    public boolean upgradeOcularImplants() {
        return this.researcher.upgrade(UpgradeType.Ocular_Implants);
    }

    public boolean upgradeMoebiusReactor() {
        return this.researcher.upgrade(UpgradeType.Moebius_Reactor);
    }

    @Override
    public boolean isUpgrading() {
        return this.researcher.isUpgrading();
    }

    @Override
    public boolean isResearching() {
        return this.researcher.isResearching();
    }

    @Override
    public boolean cancelResearch() {
        return this.researcher.cancelResearch();
    }

    @Override
    public boolean cancelUpgrade() {
        return this.researcher.cancelUpgrade();
    }
}
