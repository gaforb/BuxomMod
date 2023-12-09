package BuxomMod.actions;

import BuxomMod.ui.BraPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BuxomMod.BuxomMod.braManager;
import static BuxomMod.BuxomMod.braPanel;

public class ModifyCapacityAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int magicNumber;
    private AbstractPlayer p;
    private int amt;
    private int energyOnUse;
    private boolean upgraded;
    
    public ModifyCapacityAction(final AbstractPlayer p, int amount) {
        this.p = p;
        this.amt = amount;
    }
    
    @Override
    public void update() {
        braManager.maxCapacity += amt;
        braPanel.capacityVfx();
        this.isDone = true;
    }
}
