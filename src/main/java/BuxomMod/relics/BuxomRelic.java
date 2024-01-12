package BuxomMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public abstract class BuxomRelic extends CustomRelic {
    public BuxomRelic(String id, Texture texture, Texture outline, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        super(id, texture, outline, tier, sfx);
    }
    public void onGrow(int amount) {}
    public void onShrink(int amount) {}
    public void onExpose() {}
}
