/*package BuxomMod.orbs;

import BuxomMod.powers.CommonPower;
import BuxomMod.powers.MilkPower;
import basemod.abstracts.CustomOrb;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BuxomMod.characters.TheDefault.logger;

public abstract class AbstractChibi extends CustomOrb {
    public int milkCost = 0;

    public AbstractChibi(String ID, String NAME, int basePassiveAmount, int baseEvokeAmount, String passiveDescription, String evokeDescription, String imgPath) {
        super(ID, NAME, basePassiveAmount, baseEvokeAmount, passiveDescription, evokeDescription, imgPath);
        this.milkCost = 0;
    }

    public boolean isMilkEffect() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(MilkPower.POWER_ID)) {
            logger.info("Milk power found");
            if (p.getPower(MilkPower.POWER_ID).amount >= this.milkCost) {
                logger.info("Enough milk found");
                return true;
            }
        }
        return false;
    }

    public void milkEffect(AbstractPlayer p, AbstractCreature m){}
    public void doMilkEffect(AbstractPlayer p, AbstractCreature m) {
        if (this.isMilkEffect()) {
            logger.info("Milk cost paid");
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, MilkPower.POWER_ID, this.milkCost));
            this.milkEffect(p, m);
        }
    }
}*/
