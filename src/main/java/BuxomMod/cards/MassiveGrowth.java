package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.actions.IncreaseBuxomGainAction;
import BuxomMod.characters.TheBuxom;
import BuxomMod.powers.CommonPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static BuxomMod.BuxomMod.makeCardPath;

public class MassiveGrowth extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(MassiveGrowth.class.getSimpleName());
    public static final String IMG = makeCardPath("BrokenBraT.png");

    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;
    private static final int MAGIC = 6;
    private static final int SECOND_MAGIC = 1;

    // /STAT DECLARATION/


    public MassiveGrowth() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.misc = MAGIC;
        magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC;
        this.baseMagicNumber = misc;
        this.exhaust = true;
        this.isEthereal = true;
    }

    // Actions the card should do.
    /*@Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new PlatedArmorPower(p, block), block));
    }*/

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DiscardAction(p, p, 2, true));
        BuxomMod.logger.info("Before MN: " + magicNumber + " Base MN: " + baseMagicNumber + " SMN: " + defaultSecondMagicNumber + " Base SMN: " + defaultBaseSecondMagicNumber + " Misc: " + misc);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new CommonPower(p, p, magicNumber), magicNumber));
        addToBot(new IncreaseBuxomGainAction(this.uuid, this.misc, defaultSecondMagicNumber));
    }

    public void applyPowers() {
        this.baseMagicNumber = this.misc;
        this.magicNumber = this.baseMagicNumber;
        super.applyPowers();
        this.initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}