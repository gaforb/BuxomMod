package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.actions.ExposeAction;
import BuxomMod.actions.OmegaRecallAction;
import BuxomMod.characters.TheBuxom;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static BuxomMod.BuxomMod.makeCardPath;

public class OmegaMisfire extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(OmegaMisfire.class.getSimpleName());
    public static final String IMG = makeCardPath("OmegaMisfire.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 0;
    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    private int retainTimer = 2;

    // /STAT DECLARATION/


    public OmegaMisfire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        this.selfRetain = true;
    }

    public void onRetained() {
        retainTimer--;
        if (retainTimer <= 1) {
            this.glowColor = BuxomMod.BUXOM_PINK;
        }
    }
    public void atTurnStart() {
        if (retainTimer <= 0) {
            addToBot(new NewQueueCardAction(this, true));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        retainTimer = 2;
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        addToBot(new ExposeAction(p));
        addToBot(new OmegaRecallAction(p, magicNumber, false));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}