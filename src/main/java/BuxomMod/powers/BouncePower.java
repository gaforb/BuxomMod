package BuxomMod.powers;

import BuxomMod.BuxomMod;
import BuxomMod.cards.BigBounceStatus;
import BuxomMod.cards.BuxomStatus;
import BuxomMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
        this.amount += buxom;
        if (this.owner.hasPower(BigBouncePower.POWER_ID)) {
            while (this.amount >= 10) {
                this.amount -= 10;
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction((AbstractCard) new BigBounceStatus(), 1));
            }
        } else {
            while (this.amount >= 7) {
                this.amount -= 7;
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction((AbstractCard) new BuxomStatus(), 1));
            }
        }
    }
    public void onInitialApplication() {
        if (!this.owner.hasPower(BigBouncePower.POWER_ID) && this.amount >= 8) {
            createStatusCards();
        }
        else if ((this.owner.hasPower(BigBouncePower.POWER_ID)) && this.amount >= 10) {
            createStatusCards();
        }
        appliedThisTurn = true;
    }
    public void stackPower(int stackAmount) { // At the end of your turn
        if (!appliedThisTurn) {
            createStatusCards();
        }
        else { appliedThisTurn = false; }
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
