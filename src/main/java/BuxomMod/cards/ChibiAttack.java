package BuxomMod.cards;

import BuxomMod.powers.CommonPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import BuxomMod.DefaultMod;
import BuxomMod.characters.TheDefault;
import BuxomMod.orbs.AttackChibi;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import static BuxomMod.DefaultMod.makeCardPath;

public class ChibiAttack extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Weirdness Apply X (+1) keywords to yourself.
     */

    // TEXT DECLARATION 

    public static final String ID = DefaultMod.makeID(ChibiAttack.class.getSimpleName());
    public static final String IMG = makeCardPath("ChibiAttack.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC = 1;


    // /STAT DECLARATION/

    public ChibiAttack() {

        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        if (p.getPower(CommonPower.POWER_ID) != null && p.getPower(CommonPower.POWER_ID).amount < 5) {
            canUse = false;
            this.cantUseMessage = "My breasts aren't big enough!";
        }
        return canUse;
    }

    // Actions the card should do.
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractPower b = p.getPower(CommonPower.POWER_ID);
        if (b != null) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, b.amount, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        addToBot((AbstractGameAction)new ChannelAction((AbstractOrb)new AttackChibi()));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}
