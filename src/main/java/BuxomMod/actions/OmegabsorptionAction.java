//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package BuxomMod.actions;

import BuxomMod.BuxomMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.Iterator;

import static BuxomMod.characters.TheBuxom.logger;

public class OmegabsorptionAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private boolean canPickZero;
    public static int numExhausted;
    public int drawCards;
    public int capacityLoss;

    public OmegabsorptionAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero, int capacityLoss, int drawCards) {
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
        this.drawCards = drawCards;
    }

    public void update() {
        AbstractCard c = null;
        if (this.duration == this.startDuration) {
            if (this.p.hand.size() == 0) {
                logger.info("this.p.hand.size() == 0");
                this.isDone = true;
                return;
            }

            int i;
            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                logger.info("!this.anyNumber && this.p.hand.size() <= this.amount");
                this.amount = this.p.hand.size();
                numExhausted = this.amount;
                i = this.p.hand.size();

                for(i = 0; i < i; ++i) {
                    c = this.p.hand.getTopCard();
                }

                CardCrawlGame.dungeon.checkForPactAchievement();
                this.tickDuration();
                return;
            }

            if (!this.isRandom) {
                logger.info("!this.isRandom");
                numExhausted = this.amount;
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
                this.tickDuration();
                return;
            }

            for(i = 0; i < this.amount; ++i) {
                this.p.hand.moveToExhaustPile(this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng));
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            logger.info("!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved");
            Iterator var4 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(var4.hasNext()) {
                c = (AbstractCard)var4.next();
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        if (c != null && BuxomMod.getType(c) == AbstractCard.CardType.STATUS) {
            logger.info("c != null && BuxomMod.getType(c) == AbstractCard.CardType.STATUS");
            addToBot(new DrawCardAction(drawCards));
        } else if (c != null && BuxomMod.getType(c) != AbstractCard.CardType.STATUS) {
            logger.info("c != null && BuxomMod.getType(c) != AbstractCard.CardType.STATUS");
            addToBot(new ModifyCapacityAction(p, -drawCards));
        }

        this.isDone = true;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}
