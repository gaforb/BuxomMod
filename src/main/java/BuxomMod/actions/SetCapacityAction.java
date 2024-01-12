package BuxomMod.actions;

import BuxomMod.BuxomMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import static BuxomMod.BuxomMod.braManager;
import static BuxomMod.BuxomMod.braPanel;

public class SetCapacityAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int magicNumber;
    private AbstractPlayer p;
    private int amt;
    private int energyOnUse;
    private boolean upgraded;

    public SetCapacityAction(final AbstractPlayer p, int amount) {
        this.p = p;
        if (BuxomMod.maxCapacityLimit) { this.amt = Math.min(amount, braManager.maxTotalCapacity); }
        else { this.amt = amount; }
    }
    
    @Override
    public void update() {
        braManager.maxCapacity = Math.max(amt, braManager.maxCapacity);
        braPanel.capacityVfx();
        this.isDone = true;
    }
}
