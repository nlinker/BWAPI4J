package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.UnitType;

public class SupplyDepot extends Unit implements Mechanical {

	public SupplyDepot(int id) {
		super(id, UnitType.Terran_Supply_Depot);
	}
}
