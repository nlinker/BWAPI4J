package org.openbw.bwapi4j.unit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.UnitType;

public class UnitFactory {

    private static final Logger logger = LogManager.getLogger();
    
    public Unit createUnit(int unitId, UnitType unitType, int timeSpotted) {

        Unit unit;
        switch (unitType) {
        case Terran_Academy:
            unit = new Academy(unitId, timeSpotted);
            break;
        case Terran_Armory:
            unit = new Armory(unitId, timeSpotted);
            break;
        case Terran_Barracks:
            unit = new Barracks(unitId, timeSpotted);
            break;
        case Terran_Battlecruiser:
            unit = new BattleCruiser(unitId);
            break;
        case Terran_Bunker:
            unit = new Bunker(unitId, timeSpotted);
            break;
        case Terran_Command_Center:
            unit = new CommandCenter(unitId, timeSpotted);
            break;
        case Terran_Comsat_Station:
            unit = new ComsatStation(unitId, timeSpotted);
            break;
        case Terran_Control_Tower:
            unit = new ControlTower(unitId, timeSpotted);
            break;
        case Terran_Covert_Ops:
            unit = new CovertOps(unitId, timeSpotted);
            break;
        case Terran_Dropship:
            unit = new Dropship(unitId);
            break;
        case Terran_Engineering_Bay:
            unit = new EngineeringBay(unitId, timeSpotted);
            break;
        case Terran_Factory:
            unit = new Factory(unitId, timeSpotted);
            break;
        case Terran_Firebat:
            unit = new Firebat(unitId);
            break;
        case Terran_Ghost:
            unit = new Ghost(unitId);
            break;
        case Terran_Goliath:
            unit = new Goliath(unitId);
            break;
        case Terran_Machine_Shop:
            unit = new MachineShop(unitId, timeSpotted);
            break;
        case Terran_Marine:
            unit = new Marine(unitId);
            break;
        case Terran_Medic:
            unit = new Medic(unitId);
            break;
        case Terran_Missile_Turret:
            unit = new MissileTurret(unitId, timeSpotted);
            break;
        case Terran_Nuclear_Silo:
            unit = new NuclearSilo(unitId, timeSpotted);
            break;
        case Terran_Physics_Lab:
            unit = new PhysicsLab(unitId, timeSpotted);
            break;
        case Terran_Refinery:
            unit = new Refinery(unitId, timeSpotted);
            break;
        case Terran_Science_Facility:
            unit = new ScienceFacility(unitId, timeSpotted);
            break;
        case Terran_Science_Vessel:
            unit = new ScienceVessel(unitId);
            break;
        case Terran_SCV:
            unit = new SCV(unitId);
            break;
        case Terran_Siege_Tank_Tank_Mode:
        case Terran_Siege_Tank_Siege_Mode:
            unit = new SiegeTank(unitId);
            break;
        case Terran_Vulture_Spider_Mine:
            unit = new SpiderMine(unitId);
            break;
        case Terran_Starport:
            unit = new Starport(unitId, timeSpotted);
            break;
        case Terran_Supply_Depot:
            unit = new SupplyDepot(unitId, timeSpotted);
            break;
        case Terran_Valkyrie:
            unit = new Valkyrie(unitId);
            break;
        case Terran_Vulture:
            unit = new Vulture(unitId);
            break;
        case Terran_Wraith:
            unit = new Wraith(unitId);
            break;
        case Zerg_Zergling:
            unit = new Zergling(unitId);
            break;
        case Zerg_Hydralisk:
            unit = new Hydralisk(unitId);
            break;
        case Zerg_Ultralisk:
            unit = new Ultralisk(unitId);
            break;
        case Zerg_Broodling:
            unit = new Broodling(unitId);
            break;
        case Zerg_Drone:
            unit = new Drone(unitId);
            break;
        case Zerg_Overlord:
            unit = new Overlord(unitId);
            break;
        case Zerg_Mutalisk:
            unit = new Mutalisk(unitId);
            break;
        case Zerg_Guardian:
            unit = new Guardian(unitId);
            break;
        case Zerg_Queen:
            unit = new Queen(unitId);
            break;
        case Zerg_Defiler:
            unit = new Defiler(unitId);
            break;
        case Zerg_Scourge:
            unit = new Scourge(unitId);
            break;
        case Zerg_Lurker:
            unit = new Lurker(unitId);
            break;
        case Zerg_Infested_Terran:
            unit = new InfestedTerran(unitId);
            break;
        case Zerg_Devourer:
            unit = new Devourer(unitId);
            break;
        case Zerg_Larva:
            unit = new Larva(unitId);
            break;
        case Zerg_Egg:
            unit = new Egg(unitId);
            break;
        case Zerg_Cocoon:
            unit = new Cocoon(unitId);
            break;
        case Zerg_Lurker_Egg:
            unit = new LurkerEgg(unitId);
            break;
        case Resource_Mineral_Field:
        case Resource_Mineral_Field_Type_2:
        case Resource_Mineral_Field_Type_3:
            unit = new MineralPatch(unitId);
            break;
        case Resource_Vespene_Geyser:
            unit = new VespeneGeyser(unitId);
            break;
        case Terran_Nuclear_Missile:
            unit = new NuclearMissile(unitId);
            break;
            
        case Zerg_Infested_Command_Center:
        case Zerg_Hatchery:
        case Zerg_Lair:
        case Zerg_Hive:
        case Zerg_Nydus_Canal:
        case Zerg_Hydralisk_Den:
        case Zerg_Defiler_Mound:
        case Zerg_Greater_Spire:
        case Zerg_Queens_Nest:
        case Zerg_Evolution_Chamber:
        case Zerg_Ultralisk_Cavern:
        case Zerg_Spire:
        case Zerg_Spawning_Pool:
        case Zerg_Creep_Colony:
        case Zerg_Spore_Colony:
        case Zerg_Sunken_Colony:
        case Zerg_Extractor:
        
        case Protoss_Corsair:
        case Protoss_Dark_Templar:
        case Protoss_Dark_Archon:
        case Protoss_Probe:
        case Protoss_Zealot:
        case Protoss_Dragoon:
        case Protoss_High_Templar:
        case Protoss_Archon:
        case Protoss_Shuttle:
        case Protoss_Scout:
        case Protoss_Arbiter:
        case Protoss_Carrier:
        case Protoss_Interceptor:
        case Protoss_Reaver:
        case Protoss_Observer:
        case Protoss_Scarab:
        case Protoss_Nexus:
        case Protoss_Robotics_Facility:
        case Protoss_Pylon:
        case Protoss_Assimilator:
        case Protoss_Observatory:
        case Protoss_Gateway:
        case Protoss_Photon_Cannon:
        case Protoss_Citadel_of_Adun:
        case Protoss_Cybernetics_Core:
        case Protoss_Templar_Archives:
        case Protoss_Forge:
        case Protoss_Stargate:
        case Protoss_Fleet_Beacon:
        case Protoss_Arbiter_Tribunal:
        case Protoss_Robotics_Support_Bay:
        case Protoss_Shield_Battery:
        
        case Spell_Scanner_Sweep:
            unit = new ScannerSweep(unitId, timeSpotted);
            break;
        case Spell_Disruption_Web:
            unit = new DisruptionWeb(unitId, timeSpotted);
            break;
        case Spell_Dark_Swarm:
            unit = new DarkSwarm(unitId, timeSpotted);
            break;
            
        // ignore turrets (for now)
        case Terran_Goliath_Turret:
        case Terran_Siege_Tank_Tank_Mode_Turret:
        case Terran_Siege_Tank_Siege_Mode_Turret:
            
        // ignore all critter units
        case Critter_Rhynadon:
        case Critter_Bengalaas:
        case Critter_Scantid:
        case Critter_Kakaru:
        case Critter_Ragnasaur:
        case Critter_Ursadon:
            
        // ignore all hero units
        case Hero_Gui_Montag:
        case Hero_Sarah_Kerrigan:
        case Hero_Alan_Schezar:
        case Hero_Alan_Schezar_Turret:
        case Hero_Jim_Raynor_Vulture:
        case Hero_Jim_Raynor_Marine:
        case Hero_Tom_Kazansky:
        case Hero_Magellan:
        case Hero_Edmund_Duke_Tank_Mode:
        case Hero_Edmund_Duke_Tank_Mode_Turret:
        case Hero_Edmund_Duke_Siege_Mode:
        case Hero_Edmund_Duke_Siege_Mode_Turret:
        case Hero_Arcturus_Mengsk:
        case Hero_Hyperion:
        case Hero_Norad_II:
        case Hero_Torrasque:
        case Hero_Matriarch:
        case Hero_Infested_Kerrigan:
        case Hero_Unclean_One:
        case Hero_Hunter_Killer:
        case Hero_Devouring_One:
        case Hero_Kukulza_Mutalisk:
        case Hero_Kukulza_Guardian:
        case Hero_Yggdrasill:
        case Hero_Dark_Templar:
        case Hero_Zeratul:
        case Hero_Tassadar_Zeratul_Archon:
        case Hero_Fenix_Zealot:
        case Hero_Fenix_Dragoon:
        case Hero_Tassadar:
        case Hero_Mojo:
        case Hero_Warbringer:
        case Hero_Gantrithor:
        case Hero_Danimoth:
        case Hero_Aldaris:
        case Hero_Artanis:
        case Hero_Raszagal:
        case Hero_Samir_Duran:
        case Hero_Alexei_Stukov:
        case Hero_Gerard_DuGalle:
        case Hero_Infested_Duran:
            
        // ignore all unused units
        case Unused_Terran1:
        case Unused_Terran2:
        case Unused_Protoss1:
        case Unused_Protoss2:
        case Unused_Zerg1:
        case Unused_Zerg2:
        case Unused_Cave:
        case Unused_Cave_In:
        case Unused_Cantina:
        case Unused_Mining_Platform:
        case Unused_Independant_Command_Center:
        case Unused_Independant_Jump_Gate:
        case Unused_Ruins:
        case Unused_Khaydarin_Crystal_Formation:
        case Unused_Zerg_Marker:
        case Unused_Terran_Marker:
        case Unused_Protoss_Marker:
        
        // ignore all special units
        case Terran_Civilian:
        case Special_Cargo_Ship:
        case Special_Mercenary_Gunship:
        case Special_Map_Revealer:
        case Special_Crashed_Norad_II:
        case Special_Ion_Cannon:
        case Special_Overmind_With_Shell:
        case Special_Overmind:
        case Special_Mature_Chrysalis:
        case Special_Cerebrate:
        case Special_Cerebrate_Daggoth:
        case Special_Stasis_Cell_Prison: // TODO might be required
        case Special_Khaydarin_Crystal_Form:
        case Special_Protoss_Temple:
        case Special_XelNaga_Temple:
        case Special_Independant_Starport:
        case Special_Warp_Gate:
        case Special_Psi_Disrupter:
        case Special_Zerg_Beacon:
        case Special_Terran_Beacon:
        case Special_Protoss_Beacon:
        case Special_Zerg_Flag_Beacon:
        case Special_Terran_Flag_Beacon:
        case Special_Protoss_Flag_Beacon:
        case Special_Power_Generator:
        case Special_Overmind_Cocoon:
        case Special_Floor_Missile_Trap:
        case Special_Floor_Hatch:
        case Special_Upper_Level_Door:
        case Special_Right_Upper_Level_Door:
        case Special_Pit_Door:
        case Special_Right_Pit_Door:
        case Special_Floor_Gun_Trap:
        case Special_Wall_Missile_Trap:
        case Special_Wall_Flame_Trap:
        case Special_Right_Wall_Missile_Trap:
        case Special_Right_Wall_Flame_Trap:
        case Special_Start_Location:
        
        // ignore all powerup units
        case Powerup_Uraj_Crystal:
        case Powerup_Khalis_Crystal:
        case Powerup_Flag:
        case Powerup_Young_Chrysalis:
        case Powerup_Psi_Emitter:
        case Powerup_Data_Disk:
        case Powerup_Khaydarin_Crystal:
        case Powerup_Mineral_Cluster_Type_1:
        case Powerup_Mineral_Cluster_Type_2:
        case Powerup_Protoss_Gas_Orb_Type_1:
        case Powerup_Protoss_Gas_Orb_Type_2:
        case Powerup_Zerg_Gas_Sac_Type_1:
        case Powerup_Zerg_Gas_Sac_Type_2:
        case Powerup_Terran_Gas_Tank_Type_1:
        case Powerup_Terran_Gas_Tank_Type_2:
            
            unit = null;
            break;
        default:
            logger.error("UnitType {} is not supported.", unitType);
            throw new UnsupportedUnitException("UnitType " + unitType + " is not supported.");
        }
        return unit;
    }
}
