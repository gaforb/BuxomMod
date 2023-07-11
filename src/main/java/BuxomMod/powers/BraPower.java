package BuxomMod.powers;

import BuxomMod.DefaultMod;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BuxomMod.DefaultMod.getPwrAmt;

public abstract class BraPower extends TwoAmountPower {

    public int minCapacity = 0;
    public BraPower() {}

    public void broken() {}

    public void growToBreak() {
        int bdiff = (this.amount2 - getPwrAmt(this.owner, CommonPower.POWER_ID));
        addToBot(new ApplyPowerAction(this.owner, this.owner, new CommonPower(this.owner, this.owner, bdiff), bdiff));
    }

    public boolean inCapacity() {
        if (DefaultMod.getPwrAmt(this.owner, CommonPower.POWER_ID) > this.amount2) {
            return false;
        } else if (DefaultMod.getPwrAmt(this.owner, CommonPower.POWER_ID) < this.minCapacity) {
            return false;
        }
        else {
            return true;
        }
    }
    public void breakCheck() {
        for (AbstractPower pow : this.owner.powers) {
            if (pow instanceof CommonPower) {
                if (pow.amount > this.amount2) {
                    this.broken();
                    }
                }
            }
        }

    public void atEndOfTurn(boolean isPlayer) { // At the end of your turn
        breakCheck();
    }
}
