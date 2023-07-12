package BuxomMod.cards;

import BuxomMod.DefaultMod;
import BuxomMod.characters.TheDefault;
import BuxomMod.powers.BigBouncePower;
import BuxomMod.powers.CommonPower;
import BuxomMod.powers.LactatingPower;
import BuxomMod.powers.MilkPower;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static BuxomMod.DefaultMod.makeCardPath;

public class BigBouncePowerCard extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * In-Progress Form At the start of your turn, play a TOUCH.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BigBouncePowerCard.class.getSimpleName());
    public static final String IMG = makeCardPath("BigBouncePower.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int MAGIC = 2;

    // /STAT DECLARATION/


    public BigBouncePowerCard() {

        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.isInnate = false;
        baseMagicNumber = magicNumber = MAGIC;
        this.cardsToPreview = new BigBounceStatus();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(p, p, new CommonPower(p, p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BigBouncePower(p, p, -1), -1));
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
