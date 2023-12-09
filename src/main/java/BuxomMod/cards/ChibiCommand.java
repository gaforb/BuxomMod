package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.actions.ChibiCommandAction;
import BuxomMod.characters.TheBuxom;
import BuxomMod.powers.ExposedPower;
import BuxomMod.powers.MilkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

import static BuxomMod.BuxomMod.makeCardPath;

public class ChibiCommand extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * For Each Loop x2" "Apply 1 Vulnerable to all enemies, 2(3) times.
     */

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(ChibiCommand.class.getSimpleName());
    public static final String IMG = makeCardPath("ChibiCommand.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;
    private final int UPGRADE_PLUS_DAMAGE = 2;
    private int MAGIC = 1;
    private final int UPGRADE_PLUS_MAGIC = 1;

    // /STAT DECLARATION/


    public ChibiCommand() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChibiCommandAction((AbstractCreature)(AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), magicNumber, false));
        addToBot(new ChibiCommandAction((AbstractCreature)(AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), magicNumber, false));
    }

    /*@Override

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower b = AbstractDungeon.player.getPower(CommonPower.POWER_ID);
        if (b != null) {
            int bdiv = b.amount;
            AbstractDungeon.actionManager.addToBottom(
                    new AddTemporaryHPAction(p, p, bdiv));
            bdiv = b.amount/2;
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(p, p, p.getPower("BuxomMod:CommonPower"), bdiv));
            AbstractDungeon.actionManager.addToBottom(
                    new HealAction(p, p, bdiv));
        }
    }*/

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}