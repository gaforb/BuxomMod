package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;
import BuxomMod.powers.ExposedPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static BuxomMod.BuxomMod.braManager;
import static BuxomMod.BuxomMod.makeCardPath;

public class Flee extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(Flee.class.getSimpleName());
    public static final String IMG = makeCardPath("Flee.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    // /STAT DECLARATION/


    public Flee() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;
    }

    public boolean freeToPlay() {
        if (CardCrawlGame.isInARun()) {
            if (AbstractDungeon.player.hasPower(ExposedPower.POWER_ID) && !braManager.embarrassingList.contains(this.uuid)) {
                return true;
            }
        }
        return super.freeToPlay();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ExhaustAction(this.magicNumber, false, true, true));
        addToBot(new GainBlockAction(p, p, block));
        if (p.hasPower(ExposedPower.POWER_ID)) {
            addToBot(new DrawCardAction(magicNumber));
        }
        braManager.embarrassingList.add(this.uuid);
        BuxomMod.logger.info("embarrassinglist: " + braManager.embarrassingList);
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