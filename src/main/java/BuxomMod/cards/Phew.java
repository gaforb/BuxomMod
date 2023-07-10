package BuxomMod.cards;

import BuxomMod.powers.CommonPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import BuxomMod.DefaultMod;
import BuxomMod.characters.TheDefault;

import static BuxomMod.DefaultMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Phew extends AbstractDynamicCard {

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
    public static final String ID = DefaultMod.makeID(Phew.class.getSimpleName());
    public static final String IMG = makeCardPath("Phew.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;  // COST = ${COST}
    private static final int MAGIC = 2;    // DAMAGE = ${DAMAGE}
    private static final int BLOCK = 7;    // DAMAGE = ${DAMAGE}
    private static final int UPGRADE_PLUS_MAGIC = 1;
    private static final int UPGRADE_PLUS_BLOCK = 2;// UPGRADE_PLUS_DMG = ${UPGRADED_DAMAGE_INCREASE}

    // /STAT DECLARATION/


    public Phew() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = BLOCK;
        this.isInnate = false;
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        this.cantUseMessage = "My breasts aren't big enough!";
            if (DefaultMod.getPwrAmt(p, CommonPower.POWER_ID) >= 1) {
                canUse = true;
            }
        else canUse = false;
        return canUse;
    }
    // Actions the card should do.
    @Override
    /*public void use(AbstractPlayer p, AbstractMonster m) {
        boolean hasStatus = false;
        AbstractDungeon.actionManager.addToBottom(
            new ReducePowerAction(p, p, p.getPower("BuxomMod:CommonPower"), magicNumber));
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.STATUS) {
                hasStatus = true;
            }
        }
        if (hasStatus == false) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        }
    }*/

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(p, p, p.getPower("BuxomMod:CommonPower"), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
