package BuxomMod.cards;

import BuxomMod.powers.CommonPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;

import static BuxomMod.BuxomMod.makeCardPath;

public class DefaultUncommonAttack extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION 

    public static final String ID = BuxomMod.makeID(DefaultUncommonAttack.class.getSimpleName());
    public static final String IMG = makeCardPath("BigBounce.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_DMG = 4;

    // /STAT DECLARATION/


    public DefaultUncommonAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    /*public void applyPowers() {
        AbstractPower b = AbstractDungeon.player.getPower(CommonPower.POWER_ID);
        if (b != null) {
            this.baseDamage += b.amount;
        }
        super.applyPowers();
        if (b != null) {
            this.baseDamage -= b.amount;
        }
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower b = AbstractDungeon.player.getPower(CommonPower.POWER_ID);
        if (b != null) {
            this.baseDamage += b.amount;
        }
        super.calculateCardDamage(mo);
        if (b != null) {
            this.baseDamage -= b.amount;
        }
    }
    /*public void applyPowers() {
            AbstractPower buxom = AbstractDungeon.player.getPower("CommonPower");
            if (buxom != null) {
                this.baseDamage += buxom.amount;
                System.out.print(buxom.amount + " Buxom damage added");
            }
            super.applyPowers();
            if (buxom != null) {
                this.baseDamage -= buxom.amount;
                System.out.print(buxom.amount + " Buxom damage removed");
            }
    }*/
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower b = AbstractDungeon.player.getPower(CommonPower.POWER_ID);
        AbstractDungeon.actionManager.addToBottom( // The action managed queues all the actions a card should do.
        // addToTop - first
        // addToBottom - last
        // 99.99% of the time you just want to addToBottom all of them.
        // Please do that unless you need to add to top for some specific reason.
        new DamageAction(m, new DamageInfo(p, baseDamage, damageTypeForTurn),
        // a list of existing actions can be found at com.megacrit.cardcrawl.actions but
        // Chances are you'd instead look at "hey my card is similar to this basegame card"
        // Let's find out what action *it* uses.
        // I.e. i want energy gain or card draw, lemme check out Adrenaline
        // P.s. if you want to damage ALL enemies OUTSIDE of a card, check out the custom orb.
        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if (b != null) {
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(p, p, p.getPower(CommonPower.POWER_ID), 1));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, b.amount, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}