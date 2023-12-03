package BuxomMod.cards;

import BuxomMod.patches.CustomTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import BuxomMod.BuxomMod;
import BuxomMod.powers.CommonPower;

import static BuxomMod.BuxomMod.makeCardPath;

public class OmegaLibrary extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION 

    public static final String ID = BuxomMod.makeID(OmegaLibrary.class.getSimpleName());
    public static final String IMG = makeCardPath("OmegaLibrary.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCard.CardColor.COLORLESS;

    private static final int COST = 1;
    private static final int MAGIC = 3;
    private static final int SECOND_MAGIC = 3;

    // /STAT DECLARATION/


    public OmegaLibrary() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC;
        this.exhaust = true;
        this.tags.add(CustomTags.BOUNCY);
    }

    // Actions the card should do.
    /*@Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new PlatedArmorPower(p, block), block));
    }*/

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, magicNumber));
        Library generatedCard = new Library();
        if (upgraded) {
            generatedCard.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(generatedCard, 1, true, true));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new CommonPower(p, p, defaultSecondMagicNumber), defaultSecondMagicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}