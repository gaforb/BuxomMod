package BuxomMod.ui;

import BuxomMod.BuxomMod;
import BuxomMod.cards.BigBounceStatus;
import BuxomMod.cards.BuxomStatus;
import BuxomMod.powers.BigBouncePower;
import BuxomMod.powers.BraPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.List;

import static BuxomMod.characters.TheBuxom.logger;

public class BraPanel {

    private static Texture BRA_TEXTURE = ImageMaster.loadImage(BuxomMod.getModID() + "Resources/images/ui/Buxom84.png");
    private static Texture BOUNCE_TEXTURE = ImageMaster.loadImage(BuxomMod.getModID() + "Resources/images/ui/Bounce84.png");
    private static final int Y_POS = 600;
    private static final int X_POS = 120;
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(BuxomMod.makeID("BuxomPanel")).TEXT;

    private boolean show = true;
    private Hitbox buxomPanelToggleHb;
    //private BuxomPanelHitboxListener buxomPanelToggleHbListener;
    private List<Hitbox> buxomPanelHbs;
    private Hitbox buxomPanelHb;
    public BraPanel(){

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

            StringBuilder body = new StringBuilder();
            for (AbstractPower pow : player.powers) {
                if (pow instanceof BraPower) {
                    body.append("\n" + pow.name + "\n" + "Capacity: " + ((BraPower) pow).minCapacity + "-" + ((BraPower) pow).maxCapacity);
                    if (((BraPower) pow).inCapacity()) {
                        braColor = Settings.GREEN_RELIC_COLOR;
                    }
                    else {braColor = Settings.RED_RELIC_COLOR;}
                }
            }

            if (show) {
                FontHelper.renderFontLeftTopAligned(
                    sb,
                    FontHelper.tipHeaderFont,
                    body.toString(),
                    X_POS * Settings.scale,
                    Settings.HEIGHT - Y_POS * Settings.scale,
                    braColor);
            }
        }
    }
    /*private class BuxomPanelHitboxListener implements HitboxListener
    {
        @Override
        public void hoverStarted(Hitbox hitbox) {}
    }*/

}
