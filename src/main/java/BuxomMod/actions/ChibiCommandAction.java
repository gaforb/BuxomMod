package BuxomMod.actions;

import BuxomMod.BuxomMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ChibiCommandAction extends AbstractGameAction {
    private boolean exhaustCards;
    private int strengthToGain;

    public ChibiCommandAction(AbstractCreature target, int strength, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.source = (AbstractCreature) AbstractDungeon.player;
        this.target = target;
        this.exhaustCards = exhausts;
        this.strengthToGain = strength;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
                this.isDone = true;

                return;
            }
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                addToTop(new com.megacrit.cardcrawl.actions.common.PlayTopCardAction(this.target, this.exhaustCards));
                addToTop(new EmptyDeckShuffleAction());
                this.isDone = true;

                return;
            }
            if (!AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
                if (BuxomMod.getType(card) == AbstractCard.CardType.STATUS) {
                    BuxomMod.logger.info("Status card");
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, strengthToGain),  strengthToGain));
                    addToBot(new ChibiCommandAction(target, strengthToGain, exhaustCards));
                }
                BuxomMod.logger.info("Not a status card");
                AbstractDungeon.player.drawPile.group.remove(card);
                (AbstractDungeon.getCurrRoom()).souls.remove(card);
                card.exhaustOnUseOnce = this.exhaustCards;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;

                card.applyPowers();
                addToTop((AbstractGameAction)new NewQueueCardAction(card, this.target, false, true));
                addToTop((AbstractGameAction)new UnlimboAction(card));
                if (!Settings.FAST_MODE) {
                    addToTop((AbstractGameAction)new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    addToTop((AbstractGameAction)new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
            this.isDone = true;
        }
    }



}
