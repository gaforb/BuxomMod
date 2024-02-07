package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.actions.ExposeAction;
import BuxomMod.characters.TheBuxom;
import BuxomMod.powers.ExposedPower;
import BuxomMod.powers.SurprisePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import static BuxomMod.BuxomMod.makeCardPath;

public class Surprise extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(Surprise.class.getSimpleName());
    public static final String IMG = makeCardPath("Surprise.png");

    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 0;
    private static final int UPGRADE_COST = 0;
    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    // /STAT DECLARATION/


    public Surprise() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        this.selfRetain = true;
        this.exhaust = true;
    }

    /*@Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new SurprisePower(p, p, -1), -1));
    }*/
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExposeAction(p));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}