package BuxomMod.actions;

import BuxomMod.BuxomMod;
import BuxomMod.powers.ExposedPower;
import BuxomMod.relics.BuxomRelic;
import BuxomMod.relics.SilkRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static BuxomMod.BuxomMod.*;

public class ExposeAction extends AbstractGameAction {
    private AbstractPlayer p;

    public ExposeAction(final AbstractPlayer p) {
        this.p = p;
    }
    
    @Override
    public void update() {
        if (p.getRelic(SilkRelic.ID) == null || ((SilkRelic)p.getRelic(SilkRelic.ID)).used) {
            addToBot(new LoseBlockAction(p, p, p.currentBlock));
        }
        CardCrawlGame.sound.play(makeID(BuxomMod.makeID("SUDDEN_GASP")));
        for (AbstractRelic i : AbstractDungeon.player.relics) {
            if (i instanceof SilkRelic) {
                ((BuxomRelic)i).onExpose();
            }
            if (i instanceof BuxomRelic) {
                ((BuxomRelic)i).onExpose();
            }
        }
        addToBot(new ApplyPowerAction(p, p, new ExposedPower(p, p, 1)));
        this.isDone = true;
    }
}
