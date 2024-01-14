package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.actions.ModifyMaxBounceAction;
import BuxomMod.actions.OmegaRecallAction;
import BuxomMod.actions.RepairBraAction;
import BuxomMod.characters.TheBuxom;
import BuxomMod.powers.OmegaRecallPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static BuxomMod.BuxomMod.makeCardPath;

public class OmegaRecall extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(OmegaRecall.class.getSimpleName());
    public static final String IMG = makeCardPath("OmegaRecall.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;

    private int MAGIC = 1;
    private final int UPGRADE_PLUS_MAGIC = 1;

    // /STAT DECLARATION/


    public OmegaRecall() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        this.isInnate = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new OmegaRecallPower(p, p, magicNumber)));
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
            this.isInnate = true;
            initializeDescription();
        }
    }
}