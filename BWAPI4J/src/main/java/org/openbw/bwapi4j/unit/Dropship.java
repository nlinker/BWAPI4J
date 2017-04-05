package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Dropship extends MobileUnit implements Mechanical {

	private static final Logger logger = LogManager.getLogger();
	
	private boolean isLoaded;
	
	public Dropship(int id) {
		super(id, UnitType.Terran_Dropship);
	}
	
	@Override
	public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {
		
		return super.initialize(unitData, index, allUnits);
	}

	@Override
	public int update(int[] unitData, int index) {
		
		this.isLoaded = unitData[Unit.IS_LOADED_INDEX] == 1;
		return super.update(unitData, index);
	}
	
	public boolean isLoaded() {
		return this.isLoaded;
	}
	
	public boolean load(MobileUnit target) {
		return load(target, false);
	}
	
	public boolean load(MobileUnit target, boolean queued) {
		
		if (target.isFlyer()) {
			logger.error("Can't load a {} into a dropship. Only non-flying units allowed.", target);
			return false;
		} else {
			return issueCommand(this.id, UnitCommandType.Load.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
		}
	}
	
	public boolean unload(MobileUnit target) {
		return issueCommand(this.id, UnitCommandType.Unload.ordinal(), target.getId(), -1, -1, -1);
	}
	
	public boolean unloadAll() {
		return unloadAll(false);
	}
	
	public boolean unloadAll(boolean queued) {
		return issueCommand(this.id, UnitCommandType.Unload_All.ordinal(), -1, -1, -1, queued ? 1 : 0);
	}
	
	public boolean unloadAll(Position p) {
		return unloadAll(p, false);
	}
	
	public boolean unloadAll(Position p, boolean queued) {
		return issueCommand(this.id, UnitCommandType.Unload_All_Position.ordinal(), -1, p.getX(), p.getY(), queued ? 1 : 0);
	}
}
