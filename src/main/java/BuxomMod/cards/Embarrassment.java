package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;
import BuxomMod.powers.BigBouncePower;
import BuxomMod.powers.CommonPower;
import BuxomMod.powers.EmbarrassmentPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static BuxomMod.BuxomMod.makeCardPath;

public class Embarrassment extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * In-Progress Form At the start of your turn, play a TOUCH.
     */

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(Embarrassment.class.getSimpleName());
    public static final String IMG = makeCardPath("Embarassment.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 2;
    private static final int SECOND_MAGIC = 3;
    private static final int UPGRADE_SECOND_MAGIC = 1;

    // /STAT DECLARATION/


    public Embarrassment() {

        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.isInnate = false;
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new CommonPower(p, p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EmbarrassmentPower(p, p, defaultSecondMagicNumber), defaultSecondMagicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isInnate = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
