package LehmanaMod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import LehmanaMod.DefaultMod;
import LehmanaMod.powers.CommonPower;
import LehmanaMod.cards.ToplessStatus;
import LehmanaMod.util.TextureLoader;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;

import static LehmanaMod.DefaultMod.makeRelicOutlinePath;
import static LehmanaMod.DefaultMod.makeRelicPath;

public class DwarfBoobsRelic extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CommonPower(AbstractDungeon.player, AbstractDungeon.player, this.counter), this.counter));
    }

    public void onReceivePower(AbstractPower power, AbstractCreature target) {
        if ((power instanceof LehmanaMod.powers.CommonPower) && (target == AbstractDungeon.player)) {
            if (AbstractDungeon.player.getPower("CommonPower").amount >= 15) {
                if (this.triggered == false) {
                    flash();
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new ToplessStatus()));
                    this.triggered = true;
                }
            }
        }
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
}
