package BuxomMod.cards;

import BuxomMod.actions.CreateStatusCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;

import static BuxomMod.BuxomMod.makeCardPath;

public class OmegaFumes extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Using the second magic number isn't very confusing, you just declare and use it the absolutely the same way you would your
     * your other ones (attack, block, magic, etc.)
     *
     * For how to create it, check out:
     * https://github.com/daviscook477/BaseMod/wiki/Dynamic-Variables
     * The files in this base that detail this are:
     * variables.DefaultSecondMagicNumber and cards.AbstractDefaultCard
     *
     * Apply 2(5) vulnerable and 4(9) poison to an enemy.
     */

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(OmegaFumes.class.getSimpleName());
    public static final String IMG = makeCardPath("OmegaFumesAttack.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;

    private static final int VULNERABLE = 2;
    private static final int UPGRADE_PLUS_VULNERABLE = 1;

    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DAMAGE = 4;

    // /STAT DECLARATION/

    public OmegaFumes() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = VULNERABLE;
        baseDamage = DAMAGE;
        this.exhaust = true;
        this.isMultiDamage = true;
        this.cardsToPreview = new AftershockStatus();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn,
                        AbstractGameAction.AttackEffect.FIRE));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(mo, p, new VulnerablePower(mo, magicNumber, false), this.magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new CreateStatusCardAction(p.drawPile, new AftershockStatus(), 2));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_VULNERABLE);
            this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
            this.initializeDescription();
        }
    }
}