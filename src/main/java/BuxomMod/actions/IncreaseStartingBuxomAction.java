package BuxomMod.actions;

import BuxomMod.BuxomMod;
import BuxomMod.powers.CommonPower;
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
import com.megacrit.cardcrawl.vfx.combat.GainPowerEffect;

import static BuxomMod.BuxomMod.braManager;
import static BuxomMod.BuxomMod.makeID;

public class IncreaseStartingBuxomAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int amt;

    public IncreaseStartingBuxomAction(final AbstractPlayer p, final int amount) {
        this.amt = amount;
        this.p = p;
    }
    
    @Override
    public void update() {
        AbstractDungeon.effectsQueue.add(new GainPowerEffect(new CommonPower(p, p, 0)));
        braManager.permaSize += amt;
        this.isDone = true;
    }
}
