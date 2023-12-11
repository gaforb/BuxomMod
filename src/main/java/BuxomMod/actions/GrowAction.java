package BuxomMod.actions;

import BuxomMod.BuxomMod;
import BuxomMod.powers.BraPower;
import BuxomMod.ui.BuxomPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BuxomMod.characters.TheBuxom.logger;

public class GrowAction extends AbstractGameAction {
    public GrowAction(int howMuch) {
        amount = howMuch;
    }
    @Override
    public void update() {
        logger.info("GrowAction doesn't do anything");
        this.isDone = true;
    }
}
