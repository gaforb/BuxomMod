package BuxomMod.cards;

import BuxomMod.powers.CommonPower;
import BuxomMod.powers.MilkPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import BuxomMod.DefaultMod;
import BuxomMod.characters.TheDefault;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;

import static BuxomMod.DefaultMod.makeCardPath;

public class HeavyLactation extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * For Each Loop x2" "Apply 1 Vulnerable to all enemies, 2(3) times.
     */

    // TEXT DECLARATION 

    public static final String ID = DefaultMod.makeID(HeavyLactation.class.getSimpleName());
    public static final String IMG = makeCardPath("HeavyLactation.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private int BLOCK = 3;
    private final int UPGRADE_PLUS_BLOCK = 1;

    // /STAT DECLARATION/

    
    public HeavyLactation() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        this.selfRetain = false;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractPower b = AbstractDungeon.player.getPower(CommonPower.POWER_ID);
        if (b != null) {
            int bdiv = b.amount;
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, new MilkPower(p, p, bdiv), bdiv));
            bdiv = b.amount/2;
            AbstractDungeon.actionManager.addToBottom(
                    new HealAction(p, p, bdiv));
        }
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
            this.selfRetain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}