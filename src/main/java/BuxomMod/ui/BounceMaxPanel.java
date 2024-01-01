package BuxomMod.ui;

import BuxomMod.BuxomMod;
import BuxomMod.util.TextureLoader;
import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

import static BuxomMod.BuxomMod.*;

public class BounceMaxPanel extends ClickableUIElement {
    public float hb_x = 80F * Settings.scale;
    public float hb_y = 282F * Settings.scale;
    public float hb_w = 48F * Settings.scale;
    public float hb_h = 48F * Settings.scale;
    public Hitbox hb = new Hitbox(this.hb_x, this.hb_y, this.hb_w, this.hb_h);
    public Color hbTextColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);

    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(makeID("BounceMaxPanel")).TEXT;
    public static String NAME = TEXT[0];
    public static String DESCRIPTION = TEXT[1];
    public ArrayList<PowerTip> tips = new ArrayList();
    public boolean renderTip = false;

    private boolean show = false;
    private float hbShowTimer = 0.0F;
    private String buxomNumber = "";

    public BounceMaxPanel(Texture image) {
        super(image);
        this.image = TextureLoader.getTexture(MAX_BOUNCE_ICON);
        this.tips.add(new PowerTip(NAME, DESCRIPTION));
        this.hitbox = hb;
        setClickable(false);
    }

    public void toggleShow()
    {
        show = !show;
    }
    public void updateDescription() {
        DESCRIPTION = TEXT[1];
    }

    public void update(AbstractPlayer player) {

        this.updateHitbox();
        updateDescription();
        if (this.hitbox.hovered) {
            this.onHover();
        } else {
            this.onUnhover();
        }

        if (this.hitbox.hovered && InputHelper.justClickedLeft && this.isClickable()) {
            this.onClick();
        }
        buxomNumber = String.valueOf(braManager.maxBounce);
        if (AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                && !player.isDead
        ) {
            show = true;
        } else {show = false;}
    }

    public void render(SpriteBatch sb, AbstractPlayer player, Color c) {
        sb.setColor(Color.WHITE);

            if (show) {
                sb.setColor(Color.WHITE);
                sb.draw(image, hb.x+6F, hb.y+6F);
                FontHelper.renderFontRightAligned(
                    sb,
                    FontHelper.panelNameFont,
                    buxomNumber,
                    hb.x * Settings.scale,
                        (hb.cY) * Settings.scale,
                        Color.WHITE);
                if (renderTip == true) {
                    TipHelper.queuePowerTips(hb.x + hb.width + 16F, hb.y, this.tips);
                }
                /*float halfWidth;
                float halfHeight;
                if (this.image != null) {
                    halfWidth = (float)this.image.getWidth() / 2.0F;
                    halfHeight = (float)this.image.getHeight() / 2.0F;
                    sb.draw(this.image, this.x - halfWidth + halfWidth * Settings.scale, this.y - halfHeight + halfHeight * Settings.scale, halfWidth, halfHeight, (float)this.image.getWidth(), (float)this.image.getHeight(), Settings.scale, Settings.scale, this.angle, 0, 0, this.image.getWidth(), this.image.getHeight(), false, false);
                    if (this.tint.a > 0.0F) {
                        sb.setBlendFunction(770, 1);
                        sb.setColor(this.tint);
                        sb.draw(this.image, this.x - halfWidth + halfWidth * Settings.scale, this.y - halfHeight + halfHeight * Settings.scale, halfWidth, halfHeight, (float)this.image.getWidth(), (float)this.image.getHeight(), Settings.scale, Settings.scale, this.angle, 0, 0, this.image.getWidth(), this.image.getHeight(), false, false);
                        sb.setBlendFunction(770, 771);
                    }
                } else if (this.region != null) {
                    halfWidth = (float)this.region.packedWidth / 2.0F;
                    halfHeight = (float)this.region.packedHeight / 2.0F;
                    sb.draw(this.region, this.x - halfWidth + halfWidth * Settings.scale, this.y - halfHeight + halfHeight * Settings.scale, halfWidth, halfHeight, (float)this.region.packedWidth, (float)this.region.packedHeight, Settings.scale, Settings.scale, this.angle);
                    if (this.tint.a > 0.0F) {
                        sb.setBlendFunction(770, 1);
                        sb.setColor(this.tint);
                        sb.draw(this.region, this.x - halfWidth + halfWidth * Settings.scale, this.y - halfHeight + halfHeight * Settings.scale, halfWidth, halfHeight, (float)this.region.packedWidth, (float)this.region.packedHeight, Settings.scale, Settings.scale, this.angle);
                        sb.setBlendFunction(770, 771);
                    }
                }*/

                this.renderHitbox(sb);
            }
        }

    @Override
    protected void onHover() {
        renderTip = true;
    }

    @Override
    protected void onUnhover() {
        renderTip = false;
    }

    @Override
    protected void onClick() {

    }

    private class StartingBuxomPanelHitboxListener implements HitboxListener
    {
        @Override
        public void hoverStarted(Hitbox hitbox) {}
        @Override
        public void startClicking(Hitbox hitbox)
        {

        }

        @Override
        public void clicked(Hitbox hitbox) {

        }
    }

}
