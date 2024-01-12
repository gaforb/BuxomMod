package BuxomMod.relics;

import BuxomMod.BuxomMod;
import BuxomMod.actions.ModifyCapacityAction;
import BuxomMod.powers.CommonPower;
import BuxomMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static BuxomMod.BuxomMod.*;

public class OmegaThreadRelic extends BuxomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = BuxomMod.makeID("OmegaThreadRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("OmegaThread.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("OmegaThread.png"));
    public boolean used = false;

    public OmegaThreadRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onGrow(int amount) {
        super.onGrow(amount);
        if (braManager.buxomGainedThisTurn > 5 && !this.used) {
            flash();
            addToBot(new ModifyCapacityAction(AbstractDungeon.player, 5));
            this.used = true;
        }
    }

    public void onPlayerEndTurn() {
        this.used = false;
    }

    /*public void onPlayerEndTurn() {
        if (braManager.straining) {
            this.counter += 1;
        }
        else {this.counter = 0;}
        if (this.counter >= 3) {
            this.counter = 0;
            int cdiv = braManager.maxCapacity - getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID);
            addToBot(new ModifyCapacityAction(AbstractDungeon.player, cdiv));
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.counter = 0;
    }*/

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
