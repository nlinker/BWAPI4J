package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class DefilerMound extends Building implements Organic, ResearchingFacility {

    private Researcher researcher;
    
    protected DefilerMound(int id, int timeSpotted) {
        
        super(id, UnitType.Zerg_Defiler_Mound, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public void update(int[] unitData, int index) {

        this.researcher.update(unitData, index);
        super.update(unitData, index);
    }
    
    public boolean researchPlague() {
        
        return this.researcher.research(TechType.Plague);
    }
    
    public boolean upgradeMetasynapticNode() {
        
        return this.researcher.upgrade(UpgradeType.Metasynaptic_Node);
    }
    
    public boolean researchConsume() {
        
        return this.researcher.research(TechType.Consume);
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
