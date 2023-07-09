package BuxomMod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class BraPower extends TwoAmountPower {
    public BraPower() {}

    public void broken() {}

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
