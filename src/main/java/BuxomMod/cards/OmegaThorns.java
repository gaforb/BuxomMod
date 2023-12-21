package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.actions.CreateStatusCardAction;
import BuxomMod.characters.TheBuxom;
import BuxomMod.powers.CommonPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import static BuxomMod.BuxomMod.*;

// public class ${NAME} extends AbstractDynamicCard
public class OmegaThorns extends AbstractDynamicCard {

/*
 * "Hey, I wanna make a bunch of cards now." - You, probably.
 * ok cool my dude no problem here's the most convenient way to do it:
 *
 * Copy all of the code here (Ctrl+A > Ctrl+C)
 * Ctrl+Shift+A and search up "file and code template"
 * Press the + button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
 * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with ${NAME}
 * And then you can do custom ones like ${DAMAGE} and ${TARGET} if you want.
 * I'll leave some comments on things you might consider replacing with what.
 *
 * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template you'll
 * likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe you want a
 * universal template where you delete everything unnecessary - up to you)
 *
 * You can create templates for anything you ever want to. Cards, relics, powers, orbs, etc. etc. etc.
 */

// TEXT DECLARATION

public static final String ID = BuxomMod.makeID(OmegaThorns.class.getSimpleName());
public static final String IMG = makeCardPath("OmegaThorns.png");// "public static final String IMG = makeCardPath("${NAME}.png");
// This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


// /TEXT DECLARATION/


// STAT DECLARATION

private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
private static final CardType TYPE = CardType.SKILL;       //
public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(BuxomMod.makeID("BraBroken")).TEXT;

private static final int COST = 2;  // COST = ${COST}
private static final int UPGRADED_COST = 1; // UPGRADED_COST = ${UPGRADED_COST}
private static final int MAGIC = 2;
private static final int SECOND_MAGIC = 10;
private static final int UPGRADE_SECOND_MAGIC = -3;

// /STAT DECLARATION/


public OmegaThorns() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
    super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    baseMagicNumber = magicNumber = MAGIC;
    defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC;
    }

public boolean canUse(AbstractPlayer p, AbstractMonster m) {
    boolean canUse = super.canUse(p, m);
    if (!canUse) {
        return false;
    }
    canUse = false;
    this.cantUseMessage = TEXT[0];
    if (!braManager.broken) {
        return true;
    }
    return false;
}


// Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int b;
        for (b = getPwrAmt(p, CommonPower.POWER_ID); b >= 10; b -= 10) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                    new ThornsPower(p, magicNumber), magicNumber));
        }
    }


// Upgraded stats.
@Override
    public void upgrade() {
                if (!upgraded) {
                upgradeName();
                upgradeDefaultSecondMagicNumber(UPGRADE_SECOND_MAGIC);
                initializeDescription();
                }
            }
        }