package BuxomMod.powers;

import BuxomMod.BuxomMod;
import BuxomMod.actions.CreateStatusCardAction;
import BuxomMod.cards.BigBounceStatus;
import BuxomMod.cards.BuxomStatus;
import BuxomMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BouncePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = BuxomMod.makeID(BouncePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("BuxomModResources/images/powers/Bounce84.png");
    private static final Texture tex32 = TextureLoader.getTexture("BuxomModResources/images/powers/Bounce32.png");
    private static final AbstractPlayer p = AbstractDungeon.player;

    public BouncePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    public boolean appliedThisTurn;
    public void createStatusCards() {
        int buxom = BuxomMod.getPwrAmt(this.owner, CommonPower.POWER_ID);
        BuxomMod.logger.info("BouncePower: " + buxom + " buxom stacks to be applied");
        if (!appliedThisTurn) {
            this.amount += buxom;
        }
        else {appliedThisTurn = false;}
        BuxomMod.logger.info("BouncePower: "+ this.amount + " bounce stacks");
        /*if (this.owner.hasPower(BigBouncePower.POWER_ID)) {
            while (this.amount >= 10) {
                BuxomMod.logger.info("BouncePower: 10 stacks reached with big bounce, making status cards");
                this.amount -= 10;
                BuxomMod.logger.info("BouncePower: " + amount + " stacks remaining");
                AbstractDungeon.actionManager.addToBottom(new CreateStatusCardAction(p.discardPile, new BuxomStatus(), 1));
            }
        } else {*/
            while (this.amount >= BuxomMod.braManager.maxBounce) {
                BuxomMod.logger.info("BouncePower: " + BuxomMod.braManager.maxBounce + " stacks reached");
                this.amount -= BuxomMod.braManager.maxBounce;
                BuxomMod.logger.info("BouncePower: " + amount + " stacks remaining");
                AbstractDungeon.actionManager.addToBottom(new CreateStatusCardAction(p.discardPile, new BuxomStatus(), 1));
            }
        }
    public void onInitialApplication() {
        appliedThisTurn = true;
        createStatusCards();
        /*if (!this.owner.hasPower(BigBouncePower.POWER_ID) && this.amount >= 8) {
            createStatusCards();
        }
        else if ((this.owner.hasPower(BigBouncePower.POWER_ID)) && this.amount >= 10) {
            createStatusCards();
        }*/
    }
    public void stackPower(int stackAmount) { // At the end of your turn
        if (!appliedThisTurn) {
            BuxomMod.logger.info("Bounce not initially applied this turn, creating status cards");
            createStatusCards();
        }
        else {BuxomMod.logger.info("Bounce initially applied this turn, not creating status cards");
            appliedThisTurn = false; }
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
    @Override
    public AbstractPower makeCopy() {
        return new BouncePower(owner, source, amount);
    }
}
