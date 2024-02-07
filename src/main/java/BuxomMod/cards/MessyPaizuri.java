package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.actions.ExposeAction;
import BuxomMod.characters.TheBuxom;
import BuxomMod.patches.CustomTags;
import BuxomMod.powers.CommonPower;
import BuxomMod.powers.ExposedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import static BuxomMod.BuxomMod.*;

public class MessyPaizuri extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION

    public static final String ID = BuxomMod.makeID(MessyPaizuri.class.getSimpleName());
    public static final String IMG = makeCardPath("MessyPaizuri.png");

    // /TEXT DECLARATION/
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(BuxomMod.makeID("NoAttackIntent")).TEXT;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    // /STAT DECLARATION/


    public MessyPaizuri() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
    }

    // Actions the card should do.
    /*@Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new PlatedArmorPower(p, block), block));
    }*/

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
        int b = getPwrAmt(p, CommonPower.POWER_ID);
        addToBot(new ExposeAction(p));
        if (m != null && m.getIntentBaseDmg() >= 0) {
            this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -b), -b));
            if (m != null && !m.hasPower("Artifact")) {
                this.addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, b), b));
            }
        }
        else {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        }
        braManager.embarrassingList.add(this.uuid);
        BuxomMod.logger.info("embarrassinglist: " + braManager.embarrassingList);
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.selfRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}