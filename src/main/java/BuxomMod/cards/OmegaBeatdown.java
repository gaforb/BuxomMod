package BuxomMod.cards;

import BuxomMod.DefaultMod;
import BuxomMod.characters.TheDefault;
import BuxomMod.powers.CommonPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BuxomMod.DefaultMod.makeCardPath;

public class OmegaBeatdown extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(OmegaBeatdown.class.getSimpleName());
    public static final String IMG = makeCardPath("Beatdown.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 3;
    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    // /STAT DECLARATION/


    public OmegaBeatdown() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
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
        int b = DefaultMod.getPwrAmt(p, CommonPower.POWER_ID);
        for (int i = 0; i < this.magicNumber; ++i) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, b),
                            AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        for (int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            if (!(AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot)) {
                this.addToTop(new DamageAction(m, new DamageInfo(p, b),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}