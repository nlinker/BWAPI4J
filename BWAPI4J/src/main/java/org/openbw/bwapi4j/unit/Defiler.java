package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.Race;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Defiler extends MobileUnit implements Organic, SpellCaster, Burrowable {

    private static final Logger logger = LogManager.getLogger();

    private int energy;
    private boolean burrowed;

    Defiler(int id) {
        
        super(id, UnitType.Zerg_Defiler);
    }
    
    @Override
    public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        this.energy = 0;
        this.burrowed = false;
        return super.initialize(unitData, index, allUnits);
    }

    @Override
    public int update(int[] unitData, int index) {

        this.burrowed = unitData[index + Unit.IS_BURROWED_INDEX] == 1;
        this.energy = unitData[index + Unit.ENERGY_INDEX];
        super.update(unitData, index);

        return index;
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }
    
    /**
     * Consumes target Zerg mobile unit (except larva).
     * @param target Zerg unit (no larva)
     * @return true if spell is successful, false else
     */
    public boolean consume(MobileUnit target) {
        
        if (this.energy < TechType.Spawn_Broodlings.energyCost()) {
            
            return false;
        } else if (target.type.getRace() != Race.Zerg || target.type == UnitType.Zerg_Larva) {
            
            logger.info("Consume spell does not work on {} (only non-larva Zerg units can be consumed)", target);
            return false;
        } else {
            return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), target.getId(), -1, -1, TechType.Spawn_Broodlings.getId());
        }
    }
    
    public boolean plague(Position position) {
        
        if (this.energy < TechType.Plague.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, position.getX(), position.getY(), TechType.Plague.getId());
        }
    }
    
    public boolean darkSwarm(Position position) {
        
        if (this.energy < TechType.Dark_Swarm.energyCost()) {
            
            return false;
        } else {
            
            return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, position.getX(), position.getY(), TechType.Dark_Swarm.getId());
        }
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
}
