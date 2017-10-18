package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

public class Gateway extends Building implements Mechanical, TrainingFacility {

    private Trainer trainer;

    protected Gateway(int id, int timeSpotted) {
        
        super(id, UnitType.Protoss_Gateway, timeSpotted);
        this.trainer = new Trainer();
    }

    @Override
    public void update(int[] unitData, int index) {

        this.trainer.update(unitData, index);
        super.update(unitData, index);
    }

    public boolean trainZealot() {
        
        return trainer.train(UnitType.Protoss_Zealot);
    }

    public boolean trainDragoon() {
        
        return trainer.train(UnitType.Protoss_Dragoon);
    }

    public boolean trainHighTemplar() {
        
        return trainer.train(UnitType.Protoss_High_Templar);
    }

    public boolean trainDarkTemplar() {
        
        return trainer.train(UnitType.Protoss_Dark_Templar);
    }

    @Override
    public boolean isTraining() {
        
        return this.trainer.isTraining();
    }

    @Override
    public int getTrainingQueueSize() {
        
        return this.trainer.getTrainingQueueSize();
    }

    @Override
    public boolean cancelTrain(int slot) {
        
        return this.trainer.cancelTrain(slot);
    }

    @Override
    public boolean cancelTrain() {
        
        return this.trainer.cancelTrain();
    }

    @Override
    public boolean setRallyPoint(Position p) {
        
        return this.trainer.setRallyPoint(p);
    }

    @Override
    public boolean setRallyPoint(Unit target) {
        
        return this.trainer.setRallyPoint(target);
    }
}