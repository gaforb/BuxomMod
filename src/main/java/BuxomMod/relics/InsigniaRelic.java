package BuxomMod.relics;

import BuxomMod.BuxomMod;
import BuxomMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import static BuxomMod.BuxomMod.*;

public class InsigniaRelic extends BuxomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = BuxomMod.makeID("InsigniaRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ClaspRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ClaspRelic.png"));
    public boolean used = false;
    public InsigniaRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    // Description
    public void onRest() {
        flash();
        braManager.permaSize += 3;
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
