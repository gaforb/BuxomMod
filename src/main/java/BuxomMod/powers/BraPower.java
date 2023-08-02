package BuxomMod.powers;

import BuxomMod.BuxomMod;
import BuxomMod.ui.BuxomPanel;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BuxomMod.BuxomMod.buxomPanel;
import static BuxomMod.BuxomMod.getPwrAmt;

public abstract class BraPower extends TwoAmountPower {

    public int minCapacity = 0;
    public int maxCapacity = 0;
    public int bounceBonus = 0;
    public int buffAmount = 0;
    public BraPower() {
        this.greenColor2 = Color.CYAN;
    }

    /*public void onInitialApplication() {
        if (this.owner.hasPower(BouncePower.POWER_ID)) {
            ((BouncePower)this.owner.getPower(BouncePower.POWER_ID)).amount2 += this.bounceBonus;
        }
    }*/

    public void onInitialApplication() {
        if (AbstractDungeon.player.hasRelic("BuxomMod:ToplessArtifact")) {
            growToBreak();
        }
    }

    public void onGrow(int growthAmount) {
    }
    public void onShrink(int shrinkAmount) {
    }
    public void broken() {}



    public boolean inCapacity() {
        if (BuxomMod.getPwrAmt(this.owner, CommonPower.POWER_ID) > this.maxCapacity) {
            return false;
        } else if (BuxomMod.getPwrAmt(this.owner, CommonPower.POWER_ID) < this.minCapacity) {
            return false;
        }
        else {
            return true;
        }
    }
    public void growToBreak() {
        int bdiff = (this.maxCapacity - getPwrAmt(this.owner, CommonPower.POWER_ID)) + 1;
        addToBot(new ApplyPowerAction(this.owner, this.owner, new CommonPower(this.owner, this.owner, bdiff), bdiff));
    }
    public void breakCheck() {
        if (getPwrAmt(owner, CommonPower.POWER_ID) > this.maxCapacity || buxomPanel.size > this.maxCapacity) {
            this.broken();
        }
    }

    public void atEndOfTurn(boolean isPlayer) { // At the end of your turn
        breakCheck();
    }

}
