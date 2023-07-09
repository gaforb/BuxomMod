package BuxomMod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class BraPower extends TwoAmountPower {

    public int minCapacity = 0;
    public BraPower() {}

    public void broken() {}

    public boolean inCapacity() {
        for (AbstractPower pow : this.owner.powers) {
            if (pow instanceof CommonPower) {
                if (pow.amount > this.amount2) {
                    return false;
                } else if (pow.amount < this.minCapacity) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        return false;
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
