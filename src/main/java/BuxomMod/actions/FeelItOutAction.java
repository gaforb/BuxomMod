package BuxomMod.actions;

import BuxomMod.BuxomMod;
import BuxomMod.cards.BigBounceStatus;
import BuxomMod.cards.BuxomStatus;
import BuxomMod.powers.BigBouncePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FeelItOutAction extends AbstractGameAction {
    public FeelItOutAction() {
    }
    
    @Override
    public void update() {
        CardGroup statusCardsHand = BuxomMod.specialGetCardsOfType(AbstractDungeon.player.hand, AbstractCard.CardType.STATUS);
        for (AbstractCard card : statusCardsHand.group) {
            card.triggerWhenDrawn();
        }
        isDone = true;
    }
}
