package BuxomMod.cards;

import BuxomMod.powers.CommonPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;

import static BuxomMod.BuxomMod.braManager;
import static BuxomMod.BuxomMod.makeCardPath;

public class OmegaArc extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION 

    public static final String ID = BuxomMod.makeID(OmegaArc.class.getSimpleName());
    public static final String IMG = makeCardPath("OmegaArc.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 2;
    private static final int DAMAGE = 5;
    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_DMG = 2;

    // /STAT DECLARATION/


    public OmegaArc() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
        this.isMultiDamage = true;
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

        if (!braManager.broken) {
            for (int i = 0; i < magicNumber; i++) {
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAllEnemiesAction(p, multiDamage, this.damageTypeForTurn,
                                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        } else {
            for (int i = 0; i < magicNumber; i++) {
                AbstractDungeon.actionManager.addToBottom(
                        new DamageRandomEnemyAction(new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
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