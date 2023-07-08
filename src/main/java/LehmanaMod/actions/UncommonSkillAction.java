package LehmanaMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import LehmanaMod.powers.CommonPower;

public class UncommonSkillAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int magicNumber;
    private AbstractPlayer p;
    private int buxomAmt;
    private int energyOnUse;
    private boolean upgraded;
    
    public UncommonSkillAction(final AbstractPlayer p, AbstractPower b) {
        this.p = p;
        this.buxomAmt = b.amount;
    }
    
    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, buxomAmt));
        isDone = true;
    }
}
