package BuxomMod.powers;

import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;
import BuxomMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ExposedPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = BuxomMod.makeID(ExposedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("BuxomModResources/images/powers/Exposed84.png");
    private static final Texture tex32 = TextureLoader.getTexture("BuxomModResources/images/powers/Exposed32.png");
    private boolean justApplied = false;
    public static float MULTIPLIER = 0.03F;
    public static float INTERCEPT = 0.50F;

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
        description = DESCRIPTIONS[0] + (INTERCEPT*100) + DESCRIPTIONS[1] + (MULTIPLIER * 100) + DESCRIPTIONS[2] + Math.round((100 * (INTERCEPT + MULTIPLIER * BuxomMod.getPwrAmt(owner, CommonPower.POWER_ID)))) + DESCRIPTIONS[3];
    }

    public void onInitialApplication() {
        ((TheBuxom)owner).changeState(((TheBuxom)owner).state.getCurrent(0).getAnimation().getName());
        addToBot(new LoseBlockAction(owner, owner, owner.currentBlock));
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage * (1F + INTERCEPT + (MULTIPLIER * BuxomMod.getPwrAmt(owner, CommonPower.POWER_ID))) : damage;
    }

    public float modifyBlock(float blockAmount) {
        if (BuxomMod.inBraCapacity(owner)) {
            return blockAmount;
        }
        return blockAmount * 0.0F;
    }
    public void atEndOfRound() {
        if (!(BuxomMod.getPwrAmt(owner, CommonPower.POWER_ID) >= 30)) {
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
