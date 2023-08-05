package BuxomMod.powers;

import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import BuxomMod.BuxomMod;
import BuxomMod.cards.BrokenBraT;
import BuxomMod.util.TextureLoader;
import com.megacrit.cardcrawl.powers.ThornsPower;

public class TCupPower extends BraPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = BuxomMod.makeID("TCupPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("BuxomModResources/images/powers/TCup84.png");
    private static final Texture tex32 = TextureLoader.getTexture("BuxomModResources/images/powers/TCup32.png");

    private int thornsGained = 0;

    public TCupPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.maxCapacity = 30;
        this.source = source;
        this.minCapacity = 20;
        this.bounceBonus = 0;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }


    public void onGrow(int growthAmount){ // At the end of your turn
        if (inCapacity()) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new ThornsPower(this.source, this.amount), this.amount));
            thornsGained += this.amount;
        }
    }

    public void broken(){
        flash();
        if (owner.hasPower(ThornsPower.POWER_ID)) {
            addToTop(new ReducePowerAction(owner, owner, owner.getPower(ThornsPower.POWER_ID), this.amount));
        }
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(owner, owner, this, this.amount));
        addToBot(new ApplyPowerAction(owner, owner, new ExposedPower(owner, owner, -1), -1));
    }
    /*public void atEndOfTurnPreEndTurnCards(boolean isPlayer) { // At the end of your turn
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if ((power instanceof CommonPower) && (power.amount > this.maxCapacity)) {
                flash();
                if (AbstractDungeon.player.hasPower("DexterityPower")) {
                    AbstractDungeon.actionManager.addToBottom(
                            new ReducePowerAction(owner, owner, AbstractDungeon.player.getPower("DexterityPower"), AbstractDungeon.player.getPower("DexterityPower").amount));
                }
                AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(owner, owner, this, this.amount));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction((AbstractCard)new BrokenBraT(), 1, true, true));
            }
        }
    }*/
    public void onRemove() {
        addToTop(new RemoveSpecificPowerAction(owner, owner, ThornsPower.POWER_ID));
    }
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new RarePower(owner, source, amount);
    }
}
