package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public abstract class Building extends PlayerUnit {

    protected class Researcher implements ResearchingFacility {

        private boolean isUpgrading;
        private boolean isResearching;
        private int remainingResearchTime;
        private int remainingUpgradeTime;

        public void update(int[] unitData, int index) {

            this.isUpgrading = unitData[index + Unit.IS_UPGRADING_INDEX] == 1;
            this.isResearching = unitData[index + Unit.IS_RESEARCHING_INDEX] == 1;
            this.remainingResearchTime = unitData[index + Unit.REMAINING_RESEARCH_TIME_INDEX];
            this.remainingUpgradeTime = unitData[index + Unit.REMAINING_UPGRADE_TIME_INDEX];
        }

        public int getRemainingResearchTime() {
            
            return this.remainingResearchTime;
        }
        
        public int getRemainingUpgradeTime() {
            
            return this.remainingUpgradeTime;
        }
        
        public boolean isUpgrading() {
            
            return this.isUpgrading;
        }

        public boolean isResearching() {
            
            return this.isResearching;
        }

        public boolean cancelResearch() {
            
            return issueCommand(id, UnitCommandType.Cancel_Research.ordinal(), -1, -1, -1, -1);
        }

        public boolean cancelUpgrade() {
            
            return issueCommand(id, UnitCommandType.Cancel_Upgrade.ordinal(), -1, -1, -1, -1);
        }

        public boolean research(TechType techType) {
            
            return issueCommand(id, UnitCommandType.Research.ordinal(), -1, -1, -1, techType.getId());
        }

        public boolean upgrade(UpgradeType upgrade) {
            
            return issueCommand(id, UnitCommandType.Research.ordinal(), -1, -1, -1, upgrade.getId());
        }
    }

    protected class Trainer {

        private boolean isTraining;
        private int trainingQueueSize;
        private int remainingTrainTime;
        private int rallyPositionX;
        private int rallyPositionY;
        private int rallyUnitId;

        public void update(int[] unitData, int index) {
            
            this.isTraining = unitData[index + Unit.IS_TRAINING_INDEX] == 1;
            this.trainingQueueSize = unitData[index + Unit.TRAINING_QUEUE_SIZE_INDEX];
            this.remainingTrainTime = unitData[index + Unit.TRAINING_QUEUE_SIZE_INDEX];
            this.rallyUnitId = unitData[index + Unit.RALLY_UNIT_INDEX];
            this.rallyPositionX = unitData[index + Unit.RALLY_POSITION_X_INDEX];
            this.rallyPositionY = unitData[index + Unit.RALLY_POSITION_Y_INDEX];
        }

        public Position getRallyPosition() {
            
            return new Position(rallyPositionX, rallyPositionY);
        }
        
        public int getRemainingTrainingTime() {
            
            return this.remainingTrainTime;
        }
        
        public Unit getRallyUnit() {
            
            return getUnit(this.rallyUnitId);
        }
        
        public boolean isTraining() {
            
            return this.isTraining;
        }

        public int getTrainingQueueSize() {
            
            return this.trainingQueueSize;
        }

        public boolean train(UnitType type) {
            
            return issueCommand(id, UnitCommandType.Train.ordinal(), type.getId(), -1, -1, -1);
        }

        public boolean cancelTrain(int slot) {
            
            return issueCommand(id, UnitCommandType.Cancel_Train_Slot.ordinal(), -1, -1, -1, slot);
        }

        public boolean cancelTrain() {
            
            return issueCommand(id, UnitCommandType.Cancel_Train.ordinal(), -1, -1, -1, -1);
        }

        public boolean setRallyPoint(Position p) {
            
            return issueCommand(id, UnitCommandType.Set_Rally_Position.ordinal(), -1, p.getX(), p.getY(), -1);
        }

        public boolean setRallyPoint(Unit target) {
            
            return issueCommand(id, UnitCommandType.Set_Rally_Unit.ordinal(), target.getId(), -1, -1, -1);
        }
    }

    protected class Flyer implements FlyingBuilding {

        private boolean isLifted;

        public void update(int[] unitData, int index) {
            
            this.isLifted = unitData[index + Unit.IS_LIFTED_INDEX] == 1;
        }

        @Override
        public boolean lift() {
            
            return issueCommand(id, UnitCommandType.Lift.ordinal(), -1, -1, -1, -1);
        }

        @Override
        public boolean land(Position p) {
            
            return issueCommand(id, UnitCommandType.Land.ordinal(), -1, p.getX(), p.getY(), -1);
        }

        @Override
        public boolean move(Position p) {
            
            return issueCommand(id, UnitCommandType.Move.ordinal(), -1, p.getX(), p.getY(), -1);
        }

        @Override
        public boolean isLifted() {
            
            return isLifted;
        }
    }

    protected int probableConstructionStart;
    protected int remainingBuildTime;
    private int builderId;

    protected Building(int id, UnitType type, int timeSpotted) {
        
        super(id, type);
        this.probableConstructionStart = calculateProbableConstructionStart(timeSpotted);
    }

    @Override
    public void update(int[] unitData, int index) {

        this.builderId = unitData[index + Unit.BUILD_UNIT_ID_INDEX];
        this.remainingBuildTime = unitData[index + Unit.REMAINING_BUILD_TIME_INDEX];

        super.update(unitData, index);
    }

    public SCV getBuildUnit() {
        
        return (SCV) super.getUnit(builderId);
    }

    public int getBuildTime() {
        
        return this.type.buildTime();
    }

    public int getRemainingBuildTime() {
        
        return this.remainingBuildTime;
    }

    private int calculateProbableConstructionStart(int currentFrame) {

        int time = currentFrame;
        if (this.isCompleted()) {
            time = currentFrame - this.type.buildTime();
        } else {
            time = currentFrame - (this.getHitPoints() / this.type.maxHitPoints()) * this.type.buildTime();
        }
        return time;
    }

    public int getProbableConstructionStart() {
        
        return this.probableConstructionStart;
    }

    /**
     * Returns the distance to given position from where this unit was located when it last was visible.
     * @param position tile position to measure distance to
     * @return distance in tiles
     */
    public int getLastKnownDistance(TilePosition position) {

        // compute x distance
        int distX = super.getLastKnownTilePosition().getX() - position.getX();
        if (distX < 0) {
            distX = position.getX() - (super.getLastKnownTilePosition().getX() + this.type.tileWidth());
            if (distX < 0) {
                distX = 0;
            }
        }

        // compute y distance
        int distY = super.getLastKnownTilePosition().getY() - position.getY();
        if (distY < 0) {
            distY = position.getY() - (super.getLastKnownTilePosition().getY() + this.type.tileHeight());
            if (distY < 0) {
                distY = 0;
            }
        }
        return (int) Math.sqrt(distX * distX + distY * distY);
    }

    /**
     * Returns the distance to given position from where this unit was located when it last was visible.
     * @param position position to measure distance to
     * @return distance in pixels
     */
    public double getLastKnownDistance(Position position) {

        int left = position.getX() - 1;
        int top = position.getY() - 1;
        int right = position.getX() + 1;
        int bottom = position.getY() + 1;

        // compute x distance
        int distX = (super.getLastKnownPosition().getX() - this.type.dimensionLeft()) - right;
        if (distX < 0) {
            distX = left - (super.getLastKnownPosition().getX() + this.type.dimensionRight());
            if (distX < 0) {
                distX = 0;
            }
        }

        // compute y distance
        int distY = (super.getLastKnownPosition().getY() - this.type.dimensionUp()) - bottom;
        if (distY < 0) {
            distY = top - (super.getLastKnownPosition().getY() + this.type.dimensionDown());
            if (distY < 0) {
                distY = 0;
            }
        }
        return (int) Math.sqrt(distX * distX + distY * distY);
    }
}
