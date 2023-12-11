package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.actions.CreateStatusCardAction;
import BuxomMod.characters.TheBuxom;
import BuxomMod.powers.CommonPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BuxomMod.BuxomMod.getPwrAmt;
import static BuxomMod.BuxomMod.makeCardPath;

public class OmegaBonk extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(OmegaBonk.class.getSimpleName());
    public static final String IMG = makeCardPath("OmegaBonk.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;
    private static final int DAMAGE = 15;
    private static final int UPGRADE_PLUS_DMG = 6;
    private static final int MAGIC = 2;

    // /STAT DECLARATION/


    public OmegaBonk() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        this.cardsToPreview = new BuxomStatus();
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
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new CreateStatusCardAction(p.drawPile, new BuxomStatus(), magicNumber));
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