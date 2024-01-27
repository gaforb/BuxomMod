package BuxomMod.relics;

import BuxomMod.actions.ModifyCapacityAction;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import BuxomMod.BuxomMod;
import BuxomMod.util.TextureLoader;

import static BuxomMod.BuxomMod.makeRelicOutlinePath;
import static BuxomMod.BuxomMod.makeRelicPath;

public class DwarfBoobsRelic extends BuxomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = BuxomMod.makeID("DwarfBoobsRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DwarfBoobsRelic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DwarfBoobsRelic.png"));

    public DwarfBoobsRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    private boolean triggered = false;
    public void atBattleStart() {
        flash();
        /*int c = AbstractDungeon.player.masterDeck.size()/2;
        addToBot(new ModifyCapacityAction(AbstractDungeon.player, c/2));*/
    }

    public void update(){
        super.update();
        int c = AbstractDungeon.player.masterDeck.size();
        startingCapacityMod = (c/4);
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
