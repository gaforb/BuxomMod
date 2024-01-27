package BuxomMod.relics;

import BuxomMod.BuxomMod;
import BuxomMod.actions.IncreaseStartingBuxomAction;
import BuxomMod.powers.CommonPower;
import BuxomMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static BuxomMod.BuxomMod.*;

public class TempNakedRelic extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = BuxomMod.makeID("NakedRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ToplessArtifact.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ToplessArtifact.png"));

    public TempNakedRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.

    public void onEquip() {
        this.counter = 1;
        braManager.changeNaked(true);
    }
    public void atBattleStart() {
        if (usedUp == false) {
            flash();
        }
    }
    public void onVictory() {
        this.counter--;
        if (counter <= 0) {
            this.usedUp();
        }
    }
    public void onUnequip() {
        braManager.changeNaked(false);
    }

    /*public void onApplyPowerRelic(AbstractPower power, AbstractCreature target, AbstractCreature source) { // At the end of your turn
        if (power instanceof CommonPower) {
            AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(target, 1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }*/
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
}
