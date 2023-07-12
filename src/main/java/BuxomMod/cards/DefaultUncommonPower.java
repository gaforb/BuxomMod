package BuxomMod.cards;

import BuxomMod.orbs.AttackChibi;
import BuxomMod.orbs.DeviousChibi;
import BuxomMod.powers.CommonPower;
import basemod.abstracts.CustomOrb;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import BuxomMod.DefaultMod;
import BuxomMod.characters.TheDefault;
import BuxomMod.orbs.DefenseChibi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static BuxomMod.DefaultMod.makeCardPath;

public class DefaultUncommonPower extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Weirdness Apply X (+1) keywords to yourself.
     */

    // TEXT DECLARATION 

    public static final String ID = DefaultMod.makeID(DefaultUncommonPower.class.getSimpleName());
    public static final String IMG = makeCardPath("Chibis.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    // /STAT DECLARATION/

    public DefaultUncommonPower() {

        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;

    }
    
    // Actions the card should do.
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new IncreaseMaxOrbAction(magicNumber));
        addToBot(new ChannelAction(new DefenseChibi()));
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
