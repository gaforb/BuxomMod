package BuxomMod.powers;

import BuxomMod.cards.BigBounceStatus;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import BuxomMod.BuxomMod;
import BuxomMod.util.TextureLoader;
import BuxomMod.cards.BuxomStatus;

import static BuxomMod.BuxomMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class CommonPower extends TwoAmountPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = BuxomMod.makeID("CommonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("buxom84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("buxom32.png"));
    public boolean appliedThisTurn = false;
    public int buxomCounterThisTurn = 0;
    public int buxomGainedThisTurn = 0;

    public CommonPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount2;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.greenColor2 = Color.PINK;

        updateDescription();
    }
    // On use card, apply (amount) of Dexterity. (Go to the actual power card for the amount)

    // Note: If you want to apply an effect when a power is being applied you have 3 options:
    //onInitialApplication is "When THIS power is first applied for the very first time only."
    //onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
    //onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."
    public void playApplyPowerSfx() {
        /* 30 */     CardCrawlGame.sound.play(BuxomMod.makeID("HEARTBEAT"), 0.5F);
        /*    */   }

    /*public void onUseCard(AbstractCard card, final UseCardAction action) {
        if (card instanceof ExpansiveWall) {
            DefaultMod.logger.info("card is ExpansiveWall");
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.source, this.source, (card.block + (this.amount))));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source,
                    new CommonPower(this.source, this.source, 1), 1));
        }
        if (card instanceof KCupBra) {
            DefaultMod.logger.info("card is KCupBra");
            if (this.amount <= 3) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.source, this.source, (card.block)));
            }
        }
    }*/
    /*public void createStatusCards() {
        this.amount2 += this.amount;
        if (this.owner.hasPower(BigBouncePower.POWER_ID)) {
            while (this.amount2 >= 7) {
                this.amount2 -= 7;
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction((AbstractCard) new BigBounceStatus(), 1));
            }
        } else {
            while (this.amount2 >= 5) {
                this.amount2 -= 5;
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction((AbstractCard) new BuxomStatus(), 1));
            }
        }
    }
    */
    public void reducePower(int reduceAmount) {
        if (this.amount - reduceAmount <= 0) {
            this.fontScale = 8.0F;
            this.amount = 0;
        } else {
            this.fontScale = 8.0F;
            this.amount -= reduceAmount;
        }
        for (AbstractPower pow : this.owner.powers) {
            if (pow instanceof BraPower) {
                ((BraPower) pow).onShrink(reduceAmount);
            }
        }
    }
    public void onRemove() {
        for (AbstractPower pow : this.owner.powers) {
            if (pow instanceof BraPower) {
                ((BraPower) pow).onShrink(this.amount);
            }
        }
    }

    public void atStartOfTurn() {
        this.appliedThisTurn = false;
        this.buxomCounterThisTurn = 0;
        this.buxomGainedThisTurn = 0;
    }

    public void onInitialApplication() {
        this.appliedThisTurn = true;
        this.buxomCounterThisTurn++;
        buxomGainedThisTurn += this.amount;
        for (AbstractPower pow : this.owner.powers) {
            if (pow instanceof BraPower) {
                ((BraPower) pow).onGrow(this.amount);
                ((BraPower) pow).breakCheck();
            }
        }
    }
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) { // At the end of your turn
        if (isPlayer) {
            addToTop(new ApplyPowerAction(this.owner, this.owner, new BouncePower(this.owner, this.owner, this.amount)));
        }
    }

   public void stackPower(int stackAmount) {
        this.appliedThisTurn = true;
       this.buxomCounterThisTurn++;
       this.buxomGainedThisTurn += stackAmount;
       BuxomMod.logger.info("Times gained this turn: " + buxomCounterThisTurn);
       BuxomMod.logger.info("Amount gained this turn: " + buxomGainedThisTurn);
       super.stackPower(stackAmount);
       for (AbstractPower pow : this.owner.powers) {
           if (pow instanceof BraPower) {
               ((BraPower) pow).onGrow(stackAmount);
               ((BraPower) pow).breakCheck();
           }
       }
   }
   @Override
   public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
       super.renderAmount(sb, x, y, c);
       if (this.amount2 > 0) {
           if (!this.isTurnBased) {
               this.greenColor2.a = c.a;
               c = this.greenColor2;
           }

           FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
       } else if (this.amount2 < 0 && this.canGoNegative2) {
           this.redColor2.a = c.a;
           c = this.redColor2;
           FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
       }

   }

    /*public void atEndOfTurnPreEndTurnCards(boolean isPlayer) { // At the end of your turn
        Random rand = new Random();
        int r = rand.nextInt(10);
        int re = rand.nextInt(10);
        int rt = rand.nextInt(10);
        if (this.amount >= r) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction((AbstractCard) new BuxomStatus(), 1, true, true));
        }
        if (this.amount >= 6) {
            r = rand.nextInt(10);
            if ((this.amount - 5) >= r) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction((AbstractCard) new BuxomStatus(), 1, true, true));
            }
        }
        if (this.amount >= 11) {
            re = rand.nextInt(10);
            if ((this.amount - 10) >= re) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction((AbstractCard) new ExposureStatus(), 1, true, true));
            }
        }
        if (this.amount >= 16) {
            re = rand.nextInt(10);
            if ((this.amount - 16) >= re) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction((AbstractCard) new ToplessStatus(), 1, true, true));
            }
        }
    }*/
        /*r = rand.nextInt(50);
        if ((this.amount) >= r) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction((AbstractCard)new ToplessStatus(), 1, true, true));
        }*/

    /*public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            if ((AbstractDungeon.player.hasPower(KCupBra.class.getSimpleName())) && (this.amount >= 4)) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.source, this.source, KCupBra.class.getSimpleName()));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction((AbstractCard) new BrokenBraK(), 1));
            }
        }
    }*/


        /*    boolean f = false;
        if (AbstractDungeon.player.hasPower("CommonPower")) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p.name == "CommonPower" && p.amount >= min && p.amount <= max) {
                    f = true;
                }
            }
        }
        return f;
    }*/
    // At the end of the turn, remove gained Dexterity.
    /*@Override
    public void atEndOfTurn(final boolean isPlayer) {
        int count = 0;
        for (final AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            // This is how you iterate through arrays (like the one above) and card groups like
            // "AbstractDungeon.player.masterDeck.getAttacks().group" - every attack in your actual master deck.
            // Read up on java's enhanced for-each loops if you want to know more on how these work.

            ++count; // At the end of your turn, increase the count by 1 for each card played this turn.
        }

        if (count > 0) {
            flash(); // Makes the power icon flash.
            for (int i = 0; i < count; ++i) {
                AbstractDungeon.actionManager.addToBottom(
                        new ReducePowerAction(owner, owner, DexterityPower.POWER_ID, amount));
                // Reduce the power by 1 for each count - i.e. for each card played this turn.
                // DO NOT HARDCODE YOUR STRINGS ANYWHERE: i.e. don't write any Strings directly i.e. "Dexterity" for the power ID above.
                // Use the power/card/relic etc. and fetch it's ID like shown above. It's really bad practice to have "Strings" in your code:

                /*
                 * 1. It's bad for if somebody likes your mod enough (or if you decide) to translate it.
                 * Having only the JSON files for translation rather than 15 different instances of "Dexterity" in some random cards is A LOT easier.
                 *
                 * 2. You don't have a centralised file for all strings for easy proof-reading, and if you ever want to change a string
                 * you now have to go through all your files individually.
                 *
                 * 3. Without hardcoded strings, editing a string doesn't require a compile, saving you time (unless you clean+package).
                 *

            }
        }

    }
    */

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        }   else if (amount > 1) {
            description = DESCRIPTIONS[0];
        }
    }

    public AbstractPower makeCopy() {
        return new CommonPower(owner, source, amount);
    }
}
