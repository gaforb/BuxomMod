package BuxomMod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import BuxomMod.DefaultMod;
import BuxomMod.powers.CommonPower;
import BuxomMod.cards.ToplessStatus;
import BuxomMod.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.evacipated.cardcrawl.mod.stslib.relics.OnAnyPowerAppliedRelic;

import static BuxomMod.DefaultMod.makeRelicOutlinePath;
import static BuxomMod.DefaultMod.makeRelicPath;

public class DwarfBoobsRelic extends CustomRelic implements OnAnyPowerAppliedRelic{

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());

    public static final String ID = DefaultMod.makeID("DwarfBoobsRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DwarfBoobsRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DwarfBoobsRelic.png"));

    public DwarfBoobsRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        this.counter = 1;
    }

    // Flash at the start of Battle.
    private boolean triggered = false;
    public void atBattleStart() {
        flash();
        this.triggered = false;
        logger.info("Trigger unset");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CommonPower(AbstractDungeon.player, AbstractDungeon.player, this.counter), this.counter));
    }

    public void onReceivePower(AbstractPower power, AbstractCreature target) {
        if ((power.ID == CommonPower.POWER_ID) && (target == AbstractDungeon.player)) {
            logger.info("Power gained");
            if (AbstractDungeon.player.getPower(CommonPower.POWER_ID).amount >= 15) {
                logger.info("Power exceeds 15");
                if (this.triggered == false) {
                    logger.info("Effect triggered");
                    flash();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new ToplessStatus()));
                    this.triggered = true;
                    logger.info("Trigger set");
                }
                else logger.info("Already triggered this combat");
            }
            else logger.info("Power does not exceed 15");
        }
        else logger.info("Not the right power");
    }
    /*
    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }
*/
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onAnyPowerApply(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        return false;
    }
}
