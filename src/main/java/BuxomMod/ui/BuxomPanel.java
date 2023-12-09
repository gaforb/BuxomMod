package BuxomMod.ui;

import BuxomMod.BuxomMod;
import BuxomMod.cards.BigBounceStatus;
import BuxomMod.cards.BuxomStatus;
import BuxomMod.powers.BigBouncePower;
import BuxomMod.powers.BraPower;
import BuxomMod.powers.CommonPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.core.Settings;

import java.util.List;

import static BuxomMod.characters.TheBuxom.logger;

public class BuxomPanel {
    public int minSize = 1;
    public int baseSize = 1;
    public int size = 1;
    public int bounceStacks;
    public int bounceMax = 8;
    public static int buxomCounterThisTurn;
    public static int buxomGainedThisTurn;
    public static int buxomLostThisTurn;

    private static Texture BUXOM_TEXTURE = ImageMaster.loadImage(BuxomMod.getModID() + "Resources/images/ui/Buxom84.png");
    private static Texture BOUNCE_TEXTURE = ImageMaster.loadImage(BuxomMod.getModID() + "Resources/images/ui/Bounce84.png");
    private static final int Y_POS = 500;
    private static final int X_POS = 30;
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(BuxomMod.makeID("BraPanel")).TEXT;

    private boolean show = true;
    private Hitbox buxomPanelToggleHb;
    //private BuxomPanelHitboxListener buxomPanelToggleHbListener;
    private List<Hitbox> buxomPanelHbs;
    private Hitbox buxomPanelHb;
    public BuxomPanel(){

    }
    public void toggleShow()
    {
        show = !show;
    }
    public void update(AbstractPlayer player) {}

    public void render(SpriteBatch sb, AbstractPlayer player) {
        Color braColor = Settings.RED_RELIC_COLOR;
        if (AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                && !player.isDead
        ) {
            sb.setColor(Color.PINK);

            if (show) {
                FontHelper.renderFontLeftTopAligned(
                    sb,
                    FontHelper.tipHeaderFont,
                    "Buxom: " + size,
                    50 * Settings.scale,
                    Settings.HEIGHT - Y_POS * Settings.scale,
                    Settings.PURPLE_COLOR
                );
                FontHelper.renderFontLeftTopAligned(
                    sb,
                    FontHelper.tipHeaderFont,
                    "\nBounce: " + bounceStacks + "/" + bounceMax,
                    50 * Settings.scale,
                    Settings.HEIGHT - Y_POS * Settings.scale,
                    Settings.PURPLE_COLOR
                );
            }
        }
    }
    /*private class BuxomPanelHitboxListener implements HitboxListener
    {
        @Override
        public void hoverStarted(Hitbox hitbox) {}
    }*/
    public void grow(int amount) {
        logger.info("amount: " + amount);
        logger.info("before size: " + size);
        size += amount;
        logger.info("after size: " + size);
        buxomCounterThisTurn++;
        buxomGainedThisTurn += amount;
    }
    public void shrink(int amount) {
        logger.info("size: " + size);
        int toShrink = amount;
        if ((size - toShrink) <= minSize) {
            toShrink = size - minSize;
        }
        size -= toShrink;
    }

    /*public void createStatusCards() {
        bounceStacks += size;
        AbstractCard cardToCreate = new BuxomStatus();
        if (AbstractDungeon.player.hasPower(BigBouncePower.POWER_ID)) {
            cardToCreate = new BigBounceStatus();
        }
        while (bounceStacks >= bounceMax) {
            bounceStacks -= bounceMax;
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(cardToCreate, 1));
        }
    }*/
}
