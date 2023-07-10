package BuxomMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import BuxomMod.DefaultMod;
import BuxomMod.characters.TheDefault;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import static BuxomMod.DefaultMod.makeCardPath;

public class Paizuri extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION 

    public static final String ID = DefaultMod.makeID(Paizuri.class.getSimpleName());
    public static final String IMG = makeCardPath("Paizuri.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

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
    }

    // Actions the card should do.
    /*@Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new PlatedArmorPower(p, block), block));
    }*/

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null && m.getIntentBaseDmg() >= 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, magicNumber));
            if (DefaultMod.payMilkCost(p, MILKCOST)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
            }
        }
        else {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, "Enemy does not intend to attack!", true));
        }
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