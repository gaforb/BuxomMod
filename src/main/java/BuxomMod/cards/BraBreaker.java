package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;
import BuxomMod.powers.BraPower;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BuxomMod.BuxomMod.*;

@AutoAdd.Ignore
public class BraBreaker extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * TOUCH Deal 30(35) damage.
     */


    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(BraBreaker.class.getSimpleName());
    public static final String IMG = makeCardPath("BraBreaker.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 2;

    private static final int MAGIC = 4;
    private static final int DAMAGE = 0;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    // /STAT DECLARATION/


    public BraBreaker() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseDamage = damage = DAMAGE;
    }


    // Actions the card should do.
    /*public void use(AbstractPlayer p, AbstractMonster m) {
        boolean bra = false;
        for (AbstractPower pow : p.powers) { //if wearing a bra
            if (pow instanceof BraPower) {
                bra = true;
                ((BraPower) pow).growToBreak();
                if (p.hasRelic(DwarfBoobsRelic.ID)) {
                    p.getRelic(DwarfBoobsRelic.ID).counter += 2;
                }
                addToBot(new ApplyPowerAction(p, p, new BraBreakerPower(p, p, this.magicNumber), this.magicNumber));
            }
        }
        if (bra == false) {
            Random randInt = new Random();
            int randomBra = randInt.nextInt(2);
            AbstractCard braID = null;
            switch (randomBra) {
                case 0: braID = new BrokenBraK();
                    break;
                case 1: braID = new BrokenBraM();
                    break;
                case 2: braID = new BrokenBraT();
                    break;
            }
            addToBot(new MakeTempCardInHandAction(braID));
        }
    }*/
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        canUse = false;
        this.cantUseMessage = "My bra is broken!";
        if (!braManager.broken) {
            return true;
        }
        return false;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        braManager.growToBreak();
        this.baseDamage = getStatusCount(p) * this.magicNumber;
        BuxomMod.logger.info(this.baseDamage + " total damage");
        this.calculateCardDamage((AbstractMonster) null);
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.FIRE, false));
    }

    public void applyPowers() {
        this.baseDamage = getStatusCount(AbstractDungeon.player) * this.magicNumber;
        BuxomMod.logger.info(this.baseDamage + " total damage");
        super.applyPowers();
        this.initializeDescription();
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
        }
    }
}