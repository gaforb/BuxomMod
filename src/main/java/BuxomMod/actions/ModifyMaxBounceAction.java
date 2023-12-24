package BuxomMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import static BuxomMod.BuxomMod.braManager;
import static BuxomMod.BuxomMod.braPanel;

public class ModifyMaxBounceAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int magicNumber;
    private AbstractPlayer p;
    private int amt;
    private int energyOnUse;
    private boolean upgraded;

    public ModifyMaxBounceAction(int amount) {
        this.amt = amount;
    }
    
    @Override
    public void update() {
        braManager.maxBounce += amt;
        braPanel.capacityVfx();
        this.isDone = true;
    }
}
