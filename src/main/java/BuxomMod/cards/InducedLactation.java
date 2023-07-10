package BuxomMod.cards;

import BuxomMod.DefaultMod;
import BuxomMod.characters.TheDefault;
import BuxomMod.powers.CommonPower;
import BuxomMod.powers.LactatingPower;
import BuxomMod.powers.MilkPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BuxomMod.DefaultMod.makeCardPath;

public class InducedLactation extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * For Each Loop x2" "Apply 1 Vulnerable to all enemies, 2(3) times.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(InducedLactation.class.getSimpleName());
    public static final String IMG = makeCardPath("InducedLactation.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;

    private int MAGIC = 4;
    private final int UPGRADE_PLUS_MAGIC = 2;

    // /STAT DECLARATION/


    public InducedLactation() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        this.shuffleBackIntoDrawPile = false;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p,
                        new MilkPower(p, p, magicNumber), magicNumber));
        }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.shuffleBackIntoDrawPile = true;
        }
    }
}