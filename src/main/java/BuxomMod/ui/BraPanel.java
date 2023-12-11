package BuxomMod.ui;

import BuxomMod.BuxomMod;
import BuxomMod.powers.CommonPower;
import BuxomMod.util.TextureLoader;
import basemod.ClickableUIElement;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Map;

import static BuxomMod.BuxomMod.*;

public class BraPanel extends ClickableUIElement {
    public float hb_x = 138F * Settings.scale;
    public float hb_y = 270F * Settings.scale;
    public float hb_w = 120F * Settings.scale;
    public float hb_h = 48F * Settings.scale;
    public Hitbox hb = new Hitbox(this.hb_x, this.hb_y, this.hb_w, this.hb_h);
    private static Texture BRA_TEXTURE = ImageMaster.loadImage(BuxomMod.getModID() + "Resources/images/ui/Buxom84.png");
    private static Texture BOUNCE_TEXTURE = ImageMaster.loadImage(BuxomMod.getModID() + "Resources/images/ui/Bounce84.png");
    private static final int Y_POS = 600;
    private static final int X_POS = 120;
    public Color hbTextColor = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    public static float vfxTimer = 0.0F;
    private Color hbBgColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
    private Color hbShadowColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
    private Color pinkHbBarColor = BuxomMod.BUXOM_PINK;
    private float buxomBarWidth;
    private float targetBuxomBarWidth;
    private static final float SHOW_HB_TIME = 0.7F;
    private static final float HB_Y_OFFSET_DIST = 12.0F * Settings.scale;
    public float hbAlpha = 0.0F;
    private float hbYOffset = HB_Y_OFFSET_DIST * 5.0F;
    private static final float BUXOM_BAR_PAUSE_DURATION = 1.2F;
    private static final float BUXOM_BAR_WIDTH = 120.0F * Settings.scale;
    private static final float BUXOM_BAR_HEIGHT = 20.0F * Settings.scale;
    private static final float BUXOM_BAR_OFFSET_Y = 0F * Settings.scale;
    private static final float BUXOM_TEXT_OFFSET_Y = 0F * Settings.scale;
    private static final float BUXOM_BG_OFFSET_X = 0F * Settings.scale;
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(BuxomMod.makeID("BraPanel")).TEXT;
    public static final Map<String, String> TEXT_DICT = CardCrawlGame.languagePack.getUIString(makeID("BraPanel")).TEXT_DICT;
    public ArrayList<PowerTip> tips = new ArrayList();
    public boolean renderTip = false;

    private boolean show = true;
    private float hbShowTimer = 0.0F;

    public BraPanel(Texture image) {
        super(image);
        this.tips.add(new PowerTip(TEXT_DICT.get("Name"), TEXT[0]));
        this.hitbox = hb;
        setClickable(false);
    }

    public void toggleShow()
    {
        show = !show;
    }

    private void renderBuxomBg(SpriteBatch sb, float x, float y) {
        //sb.setColor(Color.BLACK);
        //sb.draw(ImageMaster.HB_SHADOW_L, x - BUXOM_BAR_HEIGHT, y - BUXOM_BG_OFFSET_X + 3.0F * Settings.scale, BUXOM_BAR_HEIGHT, BUXOM_BAR_HEIGHT);
        //sb.draw(ImageMaster.HB_SHADOW_B, x, y + 3.0F * Settings.scale, BUXOM_BAR_WIDTH, BUXOM_BAR_HEIGHT);
        //sb.draw(ImageMaster.HB_SHADOW_R, x + BUXOM_BAR_WIDTH, y - BUXOM_BG_OFFSET_X + 3.0F * Settings.scale, BUXOM_BAR_HEIGHT, BUXOM_BAR_HEIGHT);
        sb.setColor(Color.BLACK);
        //if (buxomBarWidth != 0) {
            sb.draw(ImageMaster.HEALTH_BAR_L, x - BUXOM_BAR_HEIGHT, y + BUXOM_BAR_OFFSET_Y, BUXOM_BAR_WIDTH, BUXOM_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_B, x, y + BUXOM_BAR_OFFSET_Y, BUXOM_BAR_WIDTH, BUXOM_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + BUXOM_BAR_WIDTH, y + BUXOM_BAR_OFFSET_Y, BUXOM_BAR_HEIGHT, BUXOM_BAR_HEIGHT);
        //}
    }
    private void renderPinkBuxomBar(SpriteBatch sb, float x, float y) {
        sb.setColor(Color.PINK);
        if (buxomBarWidth > 0) {
            sb.draw(ImageMaster.HEALTH_BAR_L, x - BUXOM_BAR_HEIGHT, y + BUXOM_BAR_OFFSET_Y, BUXOM_BAR_HEIGHT, BUXOM_BAR_HEIGHT);
        }
        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + BUXOM_BAR_OFFSET_Y, this.hb.width * buxomBarWidth / braManager.maxCapacity, BUXOM_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.hb.width * buxomBarWidth / braManager.maxCapacity, y + BUXOM_BAR_OFFSET_Y, BUXOM_BAR_HEIGHT, BUXOM_BAR_HEIGHT);
    }
    public void renderBuxom(SpriteBatch sb) {
        if (Settings.hideCombatElements) {
            return;
        }

        float x = this.hb.x;
        float y = this.hb.y;

        renderBuxomBg(sb, x, y);

        if (buxomBarWidth != 0) {
            renderPinkBuxomBar(sb, x, y);
        }
        /*if (braManager.straining) {
            vfxTimer -= Gdx.graphics.getDeltaTime();
            if (vfxTimer < 0.0F && !Settings.hideLowerElements) {
                AbstractDungeon.effectList.add(pulseVfx(x, y));
                vfxTimer = 2.0F;
            }
        }*/
        renderBuxomText(sb, y);
    }

    /*private void updateHbPopInAnimation() {
        if (this.hbShowTimer > 0.0F) {
            this.hbShowTimer -= Gdx.graphics.getDeltaTime();
            if (this.hbShowTimer < 0.0F) {
                this.hbShowTimer = 0.0F;
            }

            this.hbAlpha = Interpolation.fade.apply(0.0F, 1.0F, 1.0F - this.hbShowTimer / 0.7F);
            this.hbYOffset = Interpolation.exp10Out.apply(HB_Y_OFFSET_DIST * 5.0F, 0.0F, 1.0F - this.hbShowTimer / 0.7F);
        }
    }*/

    private void renderBuxomText(SpriteBatch sb, float y) {
        Color color = Color.WHITE;
        if (!braManager.inCapacity()) {
            color = Color.RED;
        }
        FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) + "/" + braManager.maxCapacity, this.hb.cX, y + BUXOM_BAR_OFFSET_Y + BUXOM_TEXT_OFFSET_Y + 5.0F * Settings.scale, color);
    }
    public static AbstractGameEffect capacityVfx(float x, float y) {
        return new VfxBuilder(TextureLoader.getTexture(BUXOM_BAR_EFFECT), 1.0f)
                .useAdditiveBlending()
                .setX(x)
                .setY(y)
                .playSoundAt(0f, "POWER_FOCUS")
                .scale(0.7f, 1.3f, VfxBuilder.Interpolations.ELASTICOUT)
                .fadeOut(0.75f)
                .build();
    }

    public static AbstractGameEffect breakVfx(float x, float y) {
        return new VfxBuilder(TextureLoader.getTexture(BREAK_EFFECT), 1.0f)
                .useAdditiveBlending()
                .setX(x)
                .setY(y)
                .playSoundAt(0f, makeID("RIP_SHORT"))
                .scale(0.7f, 1.3f, VfxBuilder.Interpolations.ELASTICOUT)
                .fadeOut(0.75f)
                .build();
    }

    public static AbstractGameEffect pulseVfx(float x, float y) {
        return new VfxBuilder(TextureLoader.getTexture(BUXOM_BAR_EFFECT), 1.0f)
                .useAdditiveBlending()
                .setX(x)
                .setY(y)
                .scale(0.7f, 1.0f, VfxBuilder.Interpolations.ELASTICOUT)
                .fadeOut(0.75f)
                .build();
    }

    public void capacityVfx() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(capacityVfx(hb.cX, hb.cY * Settings.scale)));
    }
    public void pulseVfx() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(pulseVfx(hb.cX, (hb.cY + BUXOM_BAR_OFFSET_Y) * Settings.scale)));
    }
    public void breakVfx() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(breakVfx(hb.cX, hb.cY * Settings.scale)));
    }

    public void update(AbstractPlayer player) {
        buxomBarWidth = BuxomMod.getPwrAmt(player, CommonPower.POWER_ID);
        if (buxomBarWidth >= braManager.maxCapacity) {
            buxomBarWidth = braManager.maxCapacity;
        }
        /*if (braManager.straining) {
            vfxTimer -= Gdx.graphics.getDeltaTime();
            if (vfxTimer < 0.0F && !Settings.hideLowerElements) {
                AbstractDungeon.effectList.add(pulseVfx(hb.cX, (hb.y + 8F) * Settings.scale));
                vfxTimer = 2.0F;
            }
        }*/
        this.updateHitbox();
        if (this.hitbox.hovered) {
            this.onHover();
        } else {
            this.onUnhover();
        }

        if (this.hitbox.hovered && InputHelper.justClickedLeft && this.isClickable()) {
            this.onClick();
        }
    }

    public void render(SpriteBatch sb, AbstractPlayer player, Color c) {
        Color braColor = Settings.PURPLE_RELIC_COLOR;
        sb.setColor(Color.WHITE);
        if (AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                && !player.isDead
        ) {
            StringBuilder braTitle = new StringBuilder();
            if (!braManager.broken && !braManager.straining) {
                braColor = Color.PINK;
                braTitle.append(TEXT_DICT.get("Name"));
            } else if (braManager.straining){
                braColor = Color.RED;
                braTitle.append(TEXT_DICT.get("Straining"));
            } else {
                braColor = Color.RED;
                braTitle.append(TEXT_DICT.get("Broken"));
            }

            /*StringBuilder body = new StringBuilder();
                body.append("\n" + "Capacity: " + braManager.minCapacity + "-" + braManager.maxCapacity);*/


            if (show) {
                sb.setColor(Color.WHITE);
                hb.render(sb);
                renderBuxom(sb);
                FontHelper.renderFontCentered(
                    sb,
                    FontHelper.panelNameFont,
                    braTitle.toString(),
                    hb.cX * Settings.scale,
                        (hb.y + 36.0F) * Settings.scale,
                        braColor);
                /*FontHelper.renderFontLeftTopAligned(
                    sb,
                    FontHelper.tipHeaderFont,
                    body.toString(),
                    X_POS * Settings.scale,
                    Settings.HEIGHT - Y_POS * Settings.scale,
                    braColor);*/
                if (renderTip == true) {
                    TipHelper.queuePowerTips(hb.x + hb.width + 16F, hb.y, this.tips);
                }
                float halfWidth;
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
                }

                this.renderHitbox(sb);
            }
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

    private class BuxomPanelHitboxListener implements HitboxListener
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
