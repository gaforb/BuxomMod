package BuxomMod.relics;

import BuxomMod.BuxomMod;
import BuxomMod.actions.BraSelectAction;
import BuxomMod.actions.IncreaseStartingBuxomAction;
import BuxomMod.powers.CommonPower;
import BuxomMod.powers.LactatingPower;
import BuxomMod.powers.MilkPower;
import BuxomMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static BuxomMod.BuxomMod.*;

public class CowRelic extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = BuxomMod.makeID("CowRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CowRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CowRelic.png"));

    public CowRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    private boolean triggered = false;

    public void onEquip(){
        addToBot(new IncreaseStartingBuxomAction(AbstractDungeon.player, 2));
    }
    public void atBattleStart() {
        flash();
        this.triggered = false;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LactatingPower(AbstractDungeon.player, AbstractDungeon.player, 4), 4));
    }

    /*public void onReceivePower(AbstractPower power, AbstractCreature target) {
        if ((power instanceof CommonPower) && (target == AbstractDungeon.player)) {
            if (AbstractDungeon.player.getPower(CommonPower.POWER_ID).amount >= 15) {
                if (this.triggered == false) {
                    flash();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new ToplessStatus()));
                    this.triggered = true;
                }
            }
        }
    }

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
}
