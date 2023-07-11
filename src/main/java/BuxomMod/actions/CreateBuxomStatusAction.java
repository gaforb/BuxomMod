package BuxomMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class CreateBuxomStatusAction extends AbstractGameAction {
    private boolean toBottom;
    private boolean randomSpot;
    private AbstractCard cardToMake;
    private int amount;
    private CardGroup group;

    public CreateBuxomStatusAction(final AbstractPlayer p, CardGroup group, AbstractCard card, int amount, boolean toBottom, boolean randomSpot) {
        this.cardToMake = card;
        this.amount = amount;
        this.toBottom = toBottom;
        this.randomSpot = randomSpot;
    }
    
    @Override
    public void update() {
        for (int i = 0; i < amount; i++) {
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(this.cardToMake, this.randomSpot, this.toBottom));
        }
        isDone = true;
    }
}
