package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;
import BuxomMod.powers.CommonPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BuxomMod.BuxomMod.makeCardPath;

public class BouncyBlock extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(BouncyBlock.class.getSimpleName());
    public static final String IMG = makeCardPath("Bouncy.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static final int BLOCK = 9;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int UPGRADE_PLUS_BLOCK = 0;

    // /STAT DECLARATION/


    public BouncyBlock() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
        damage = baseDamage = DAMAGE;
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
        addToBot(new GainBlockAction(p, block));
        CardGroup statusCardsHand = p.hand.getCardsOfType(CardType.STATUS);
        for (AbstractCard card : statusCardsHand.group) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}