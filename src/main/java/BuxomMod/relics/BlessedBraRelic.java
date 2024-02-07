package BuxomMod.relics;

import BuxomMod.BuxomMod;
import BuxomMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import static BuxomMod.BuxomMod.*;

public class BlessedBraRelic extends BuxomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = BuxomMod.makeID("BlessedBraRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BlessedBraRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BlessedBraRelic.png"));
    public boolean used = false;
    public BlessedBraRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    // Description
    public void onRest() {
        flash();
        braManager.permaCapacity += 3;
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
