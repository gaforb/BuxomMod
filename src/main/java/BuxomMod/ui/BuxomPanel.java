package BuxomMod.ui;

import BuxomMod.BuxomMod;
import BuxomMod.powers.CommonPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.core.Settings;

import java.util.List;

import static BuxomMod.characters.TheBuxom.logger;

public class BuxomPanel {
    public int minSize;
    public int baseSize;
    public int size;
    public static int buxomCounterThisTurn;
    public static int buxomGainedThisTurn;
    public static int buxomLostThisTurn;
    private static final int Y_POS = 500;
    private static final int X_POS = 30;
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
                        "Buxom Power Amt: " + BuxomMod.getPwrAmt(player, CommonPower.POWER_ID) + "NL Buxom Manager Amt: " + size,
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
        public void hoverStarted(Hitbox hitbox)
        {

        }

        @Override
        public void startClicking(Hitbox hitbox)
        {

        }

        @Override
        public void clicked(Hitbox hitbox)
        {
        }
    }*/
    public void grow(int amount) {
        logger.info("before size: " + size);
        size += amount;
        logger.info("after size: " + size);
        buxomCounterThisTurn++;
        buxomGainedThisTurn += amount;
    }
    public void shrink(int amount) {
        logger.info("size: " + size);
        int toShrink = amount;
        if ((size - toShrink) <= 0) {
            toShrink = size - 1;
        }
        size -= toShrink;
    }
}
