package BuxomMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StatusUpgradeAction extends AbstractGameAction {
    private static final float DURATION = 3.0F;
    private AbstractCard cardToUpgrade = null;

    public StatusUpgradeAction(AbstractCard card) {
        this.duration = 3.0F;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.cardToUpgrade = card;
    }


    public void update() {
        if (this.duration == 3.0F) {
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c.cardID == cardToUpgrade.cardID) {
                    c.upgrade();
                }
            }

            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cardID == cardToUpgrade.cardID) {
                    c.upgrade();
                }
            }

            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.cardID == cardToUpgrade.cardID) {
                    c.upgrade();
                }
            }
        }
    }
}
