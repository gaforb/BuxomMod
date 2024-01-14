package BuxomMod.relics;

import BuxomMod.actions.IncreaseStartingBuxomAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import BuxomMod.BuxomMod;
import BuxomMod.powers.CommonPower;
import BuxomMod.util.TextureLoader;

import static BuxomMod.BuxomMod.*;

public class NakedRelic extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = BuxomMod.makeID("NakedRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ToplessArtifact.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ToplessArtifact.png"));

    public NakedRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.

    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
        braManager.changeNaked(true);
        addToBot(new IncreaseStartingBuxomAction(AbstractDungeon.player, 5));
    }
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CommonPower(AbstractDungeon.player, AbstractDungeon.player, 5), 5));
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
