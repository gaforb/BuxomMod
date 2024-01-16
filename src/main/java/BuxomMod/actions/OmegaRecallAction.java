package BuxomMod.actions;

import BuxomMod.BuxomMod;
import BuxomMod.powers.ExposedPower;
import BuxomMod.relics.BuxomRelic;
import BuxomMod.relics.SilkRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static BuxomMod.BuxomMod.logger;
import static BuxomMod.BuxomMod.makeID;

public class OmegaRecallAction extends AbstractGameAction {
    private AbstractPlayer p;
    private int amt;

    public OmegaRecallAction(final AbstractPlayer p, int amount) {
        this.p = p;
        this.amt = amount;
    }

    /*At the start of your turn, draw 1 non-status card. If you have 3 or more status cards in hand, draw an additional non-status card.
    Loop through deck for non-status cards and put them in a list
    Draw !M! of them
    If cards not in hand, stop here
    If cards in hand, check for status
    If more than X status cards, draw an additional card from the list
     */
    
    @Override
    public void update() {
        CardGroup nonStatusCardsDrawPile = BuxomMod.specialGetCardsOfType(AbstractDungeon.player.drawPile, AbstractCard.CardType.STATUS, true);
        for (int i = 0; i < amt; i++) {
            logger.info("getting non-status cards in draw pile: " + nonStatusCardsDrawPile);
            if (nonStatusCardsDrawPile.size() > 0) {
                logger.info("top card: " + nonStatusCardsDrawPile.getTopCard());
                AbstractDungeon.player.drawPile.moveToHand(nonStatusCardsDrawPile.getTopCard());
            }
        }
        if (BuxomMod.specialGetCardsOfType(AbstractDungeon.player.hand, AbstractCard.CardType.STATUS).size() >= 3) {
            BuxomMod.logger.info("3 or more status cards in hand");
            if (nonStatusCardsDrawPile.size() > 0) {
                logger.info("top card: " + nonStatusCardsDrawPile.getTopCard());
                AbstractDungeon.player.drawPile.moveToHand(nonStatusCardsDrawPile.getTopCard());
            }
        }
        this.isDone = true;
    }
}
