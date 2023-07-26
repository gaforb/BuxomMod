package BuxomMod.actions;

import BuxomMod.orbs.AttackChibi;
import BuxomMod.orbs.DefenseChibi;
import BuxomMod.orbs.DeviousChibi;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class ChannelRandomChibiAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int number;
    private AbstractPlayer p;

    public ChannelRandomChibiAction(int amount) {
        this.p = p;
        this.number = amount;
    }
    
    @Override
    public void update() {
        ArrayList<AbstractOrb> orbs = new ArrayList();
        orbs.add(new DefenseChibi());
        orbs.add(new AttackChibi());
        orbs.add(new DeviousChibi());
        for (int i = 0; i < number; i++) {
            AbstractOrb orb = (AbstractOrb)orbs.get(MathUtils.random(orbs.size() - 1));
            addToBot((AbstractGameAction)new ChannelAction(orb));
        }
        isDone = true;
    }
}
