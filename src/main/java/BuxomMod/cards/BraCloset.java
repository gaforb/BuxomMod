package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.actions.ModifyCapacityAction;
import BuxomMod.actions.RepairBraAction;
import BuxomMod.characters.TheBuxom;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static BuxomMod.BuxomMod.makeCardPath;

public class BraCloset extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(BraCloset.class.getSimpleName());
    public static final String IMG = makeCardPath("BraCloset.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;
    private static final int MAGIC = 8;
    private static final int UPGRADE_PLUS_MAGIC = 3;
    private static final int SECOND_MAGIC = 2;
    private static final int UPGRADE_SECOND_MAGIC = 1;

    // /STAT DECLARATION/


    public BraCloset() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC;
        this.exhaust = true;
        this.isInnate = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RepairBraAction());
        AbstractDungeon.actionManager.addToBottom(new ModifyCapacityAction(p, magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, defaultSecondMagicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(UPGRADE_SECOND_MAGIC);
            this.isInnate = true;
            initializeDescription();
        }
    }
}