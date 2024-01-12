package BuxomMod.relics;

import BuxomMod.BuxomMod;
import BuxomMod.actions.ModifyCapacityAction;
import BuxomMod.powers.CommonPower;
import BuxomMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BuxomMod.BuxomMod.*;

public class LotionRelic extends BuxomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = BuxomMod.makeID("LotionRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("LotionRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("LotionRelic.png"));
    public boolean used = false;
    public LotionRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }


    @Override
    public void onExpose() {
        if (!used) {
            addToBot(new GainEnergyAction(1));
            this.used = true;
            logger.info("Used Lotion");
        }
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        this.used = false;
        logger.info("Refreshed Lotion");
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
