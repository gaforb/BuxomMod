package BuxomMod.actions;

import BuxomMod.BuxomMod;
import BuxomMod.powers.BraPower;
import BuxomMod.ui.BuxomPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BuxomMod.characters.TheBuxom.logger;

public class ShrinkAction extends AbstractGameAction {
    public ShrinkAction(int howMuch) {
        amount = howMuch;
    }
    public void update() {
        logger.info("Shrink action registered");
        BuxomMod.buxomPanel.grow(amount);
        for (AbstractPower pow : AbstractDungeon.player.powers) {
            if (pow instanceof BraPower && ((BraPower) pow).inCapacity()) {
                ((BraPower) pow).onShrink(amount);
            }
        }
    }
}
