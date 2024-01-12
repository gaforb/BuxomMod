package BuxomMod.cards;

import BuxomMod.powers.ExposedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import static BuxomMod.BuxomMod.braManager;
import static BuxomMod.BuxomMod.makeCardPath;

public class Paizuri extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION 

    public static final String ID = BuxomMod.makeID(Paizuri.class.getSimpleName());
    public static final String IMG = makeCardPath("Paizuri.png");

    // /TEXT DECLARATION/
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(BuxomMod.makeID("NoAttackIntent")).TEXT;
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
    private static final int MILKCOST = 2;

    // /STAT DECLARATION/


    public Paizuri() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        milkCost = baseMilkCost = MILKCOST;
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
        if (m != null && m.getIntentBaseDmg() >= 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, magicNumber));
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
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}