package BuxomMod.relics;

import BuxomMod.BuxomMod;
import BuxomMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;

import static BuxomMod.BuxomMod.*;

public class SilkRelic extends BuxomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = BuxomMod.makeID("SilkRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SilkRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SilkRelic.png"));
    public boolean used = false;
    public SilkRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }


    @Override
    public void onExpose() {
        if (!used) {
            this.used = true;
            logger.info("Used Silk");
        }
    }

    @Override
    public void onVictory() {
        this.used = false;
        logger.info("Refreshed Silk");
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
