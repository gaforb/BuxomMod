package BuxomMod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;

import static BuxomMod.BuxomMod.makeCardPath;

public class LibraryOld extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION 

    public static final String ID = BuxomMod.makeID(Library.class.getSimpleName());
    public static final String IMG = makeCardPath("Library.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    // /STAT DECLARATION/


    public LibraryOld() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = block = BLOCK;
        this.exhaust = true;
        this.cardsToPreview = new OmegaLibrary();
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
        OmegaLibrary generatedCard = new OmegaLibrary();
        if (upgraded) {
            generatedCard.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(generatedCard, 1));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}