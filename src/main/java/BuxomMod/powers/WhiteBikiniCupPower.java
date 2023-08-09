package BuxomMod.powers;

import BuxomMod.BuxomMod;
import BuxomMod.cards.BrokenBraWhiteBikini;
import BuxomMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WhiteBikiniCupPower extends BraPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = BuxomMod.makeID(WhiteBikiniCupPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("BuxomModResources/images/powers/WhiteBikini84.png");
    private static final Texture tex32 = TextureLoader.getTexture("BuxomModResources/images/powers/WhiteBikini32.png");
    private boolean upgraded;

    public WhiteBikiniCupPower(final AbstractCreature owner, final AbstractCreature source, final int amount, final boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.maxCapacity = 15;
        this.source = source;
        this.minCapacity = 5;
        this.bounceBonus = 5;
        this.upgraded = upgraded;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    /*public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        AbstractPlayer p = AbstractDungeon.player;// At the end of your turn
        if ((p.getPower("BuxomMod:CommonPower") != null) && (p.getPower("BuxomMod:CommonPower").amount >= this.amount2)) {
            flash();
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(owner, owner, new StrengthPower(owner, -2), -2));
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(owner, owner, this, this.amount));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction((AbstractCard) new BrokenBraK(), 1, true, true));
        }
    }*/
    private int buffAmount = 2;
    /*public void onGrow(int howMuch) {
        if (this.inCapacity() == true) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new MilkPower(owner, owner, this.amount)));
        }
    }*/
    /*public void update() {
        if ((this.owner.getPower("BuxomMod:CommonPower") != null) && (this.owner.getPower("BuxomMod:CommonPower").amount >= this.amount2)) {
                flash();
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(owner, owner, new StrengthPower(owner, -2), -2));
                AbstractDungeon.actionManager.addToBottom(
                        new ReducePowerAction(owner, owner, this, this.amount));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction((AbstractCard) new BrokenBraK(), 1, true, true));
            }
        }*/
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        this.breakCheck();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new LactatingPower(owner, owner, 1), 1));
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power instanceof LactatingPower) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new MilkPower(owner, owner, this.amount)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new CommonPower(owner, owner, this.amount), this.amount));
        }
    }
    public void broken() {
        flash();
        AbstractCard brokenBra = new BrokenBraWhiteBikini();
        AbstractDungeon.actionManager.addToTop(
                new RemoveSpecificPowerAction(owner, owner, WhiteBikiniCupPower.POWER_ID));
        if (upgraded) {
            brokenBra.upgrade();
        }
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction((AbstractCard) brokenBra, 1, true, true));
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
        return new WhiteBikiniCupPower(owner, source, amount, upgraded);
    }
}
