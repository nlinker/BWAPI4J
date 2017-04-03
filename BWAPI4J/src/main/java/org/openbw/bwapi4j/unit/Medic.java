package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Medic extends MobileUnit implements SpellCaster, Organic {

	private int energy;
	
	public Medic(int id) {
		super(id, UnitType.Terran_Medic);
	}
	
	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		
		this.energy = 0;
		return super.initialize(unitData, index, allUnits);
	}

	@Override
	public int update(int[] unitData, int index) {
		
		this.energy = unitData[index + Unit.ENERGY_INDEX];
		super.update(unitData, index);
		
		return index;
	}
	
	public boolean healing(PlayerUnit unit) {
		return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), unit.getId(), -1, -1, TechType.Healing.getId());
	}
	
	public boolean opticalFlare(PlayerUnit unit) {
		return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), unit.getId(), -1, -1, TechType.Optical_Flare.getId());
	}
	
	public boolean restoration(PlayerUnit unit) {
		return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), unit.getId(), -1, -1, TechType.Restoration.getId());
	}

	@Override
	public int getEnergy() {
		return this.energy;
	}
}
