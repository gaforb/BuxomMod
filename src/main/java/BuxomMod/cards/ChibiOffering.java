package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;
import BuxomMod.orbs.DeviousChibi;
import BuxomMod.powers.ExposedPower;
import BuxomMod.powers.MilkPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static BuxomMod.BuxomMod.makeCardPath;
import static BuxomMod.BuxomMod.payMilkCost;

public class ChibiOffering extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Weirdness Apply X (+1) keywords to yourself.
     */

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(ChibiOffering.class.getSimpleName());
    public static final String IMG = makeCardPath("ChibiAttraction.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;
    private static final int MAGIC = 5;
    private static final int UPGRADE_PLUS_MAGIC = 3;
    private static final int MILKCOST = 2;


    // /STAT DECLARATION/

    public ChibiOffering() {

        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;

    }

    // Actions the card should do.
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ExposedPower(p, p, -1), -1));
        addToBot(new ApplyPowerAction(p, p, new MilkPower(p, p, magicNumber), magicNumber));
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
