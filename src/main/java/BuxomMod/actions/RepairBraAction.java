package BuxomMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import static BuxomMod.BuxomMod.braManager;
import static BuxomMod.BuxomMod.braPanel;

public class RepairBraAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int magicNumber;
    private AbstractPlayer p;

    public RepairBraAction() {
    }
    
    @Override
    public void update() {
        braManager.braRepair();
        braPanel.capacityVfx();
        this.isDone = true;
    }
}
