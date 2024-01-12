package BuxomMod.powers;

import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;
import BuxomMod.relics.BuxomRelic;
import BuxomMod.relics.LotionRelic;
import BuxomMod.relics.NakedRelic;
import BuxomMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static BuxomMod.BuxomMod.braManager;
import static BuxomMod.BuxomMod.makeID;

public class ExposedPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = BuxomMod.makeID(ExposedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("BuxomModResources/images/powers/Exposed84.png");
    private static final Texture tex32 = TextureLoader.getTexture("BuxomModResources/images/powers/Exposed32.png");
    private boolean justApplied = false;
    public static float MULTIPLIER = 0.01F;
    public static float INTERCEPT = 0.40F;

    public ExposedPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + Math.round(INTERCEPT*100) + DESCRIPTIONS[1] + (Math.round(MULTIPLIER * 100)) + DESCRIPTIONS[2] + Math.round((100 * (INTERCEPT + MULTIPLIER * BuxomMod.getPwrAmt(owner, CommonPower.POWER_ID)))) + DESCRIPTIONS[3];
    }

    public void onInitialApplication() {
        CardCrawlGame.sound.play(makeID(BuxomMod.makeID("SUDDEN_GASP")));
        addToBot(new LoseBlockAction(owner, owner, owner.currentBlock));
        for (AbstractRelic i : AbstractDungeon.player.relics) {
            if (i instanceof BuxomRelic) {
                ((BuxomRelic)i).onExpose();
            }
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage * (1F + INTERCEPT + (MULTIPLIER * BuxomMod.getPwrAmt(owner, CommonPower.POWER_ID))) : damage;
    }
    public void stackPower(int stackAmount) {
        if (AbstractDungeon.player.hasRelic(NakedRelic.ID)) {
            addToBot(new GainEnergyAction(1));
        }
    }
    public int onPlayerGainedBlock(int blockAmount) {
        if (blockAmount == 0) {
            BuxomMod.logger.info("Exposed: BlockAmount 0 detected! BlockAmount: " + blockAmount);
        }
        return blockAmount;
    }

    public float modifyBlock(float blockAmount) {
        /*if (!braManager.broken) {
            return blockAmount;
        }*/
        return blockAmount * 0.0F;
    }
    public void atEndOfRound() {
        if (!(BuxomMod.getPwrAmt(owner, CommonPower.POWER_ID) >= 30) && !((TheBuxom)this.owner).naked) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        updateDescription();
    }

    @Override
    public AbstractPower makeCopy() {
        return new ExposedPower(owner, source, amount);
    }
}
