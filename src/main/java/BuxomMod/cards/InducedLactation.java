package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.actions.CreateStatusCardAction;
import BuxomMod.characters.TheBuxom;
import BuxomMod.powers.ExposedPower;
import BuxomMod.powers.MilkPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static BuxomMod.BuxomMod.braManager;
import static BuxomMod.BuxomMod.makeCardPath;

public class InducedLactation extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * For Each Loop x2" "Apply 1 Vulnerable to all enemies, 2(3) times.
     */

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(InducedLactation.class.getSimpleName());
    public static final String IMG = makeCardPath("InducedLactation.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;

    private int MAGIC = 4;
    private int UPGRADE_MAGIC = 2;

    // /STAT DECLARATION/


    public InducedLactation() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }
    public boolean freeToPlay() {
        if (AbstractDungeon.isPlayerInDungeon()) {
            if (AbstractDungeon.player.hasPower(ExposedPower.POWER_ID) && !braManager.embarrassingList.contains(this.uuid)) {
                return true;
            }
        }
        return super.freeToPlay();
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p,
                        new MilkPower(p, p, magicNumber), magicNumber));
        addToBot(new CreateStatusCardAction(p.hand, new AftershockStatus(), 1));
        braManager.embarrassingList.add(this.uuid);
        BuxomMod.logger.info("embarrassinglist: " + braManager.embarrassingList);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}