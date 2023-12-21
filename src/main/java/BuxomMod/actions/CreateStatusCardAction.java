package BuxomMod.actions;

import BuxomMod.cards.BigBounceStatus;
import BuxomMod.cards.BuxomStatus;
import BuxomMod.powers.BigBouncePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class CreateStatusCardAction extends AbstractGameAction {
    private AbstractCard cardToMake;
    private int amount;
    private CardGroup group;

    public CreateStatusCardAction(CardGroup group, AbstractCard card, int amount) {
        this.cardToMake = card;
        this.amount = amount;
        this.group = group;
    }
    
    @Override
    public void update() {
        if (cardToMake instanceof BuxomStatus) {
            if (AbstractDungeon.player.hasPower(BigBouncePower.POWER_ID)) {
                cardToMake = new BigBounceStatus();
            }
        }
        if (group == AbstractDungeon.player.discardPile) {
            addToBot(new MakeTempCardInDiscardAction(cardToMake, amount));
        }
        else if (group == AbstractDungeon.player.drawPile) {
            addToBot(new MakeTempCardInDrawPileAction(cardToMake, amount, true, true));
        }
        else if (group == AbstractDungeon.player.hand) {
            addToBot(new MakeTempCardInHandAction(cardToMake, amount));
        }
        isDone = true;
    }
}
