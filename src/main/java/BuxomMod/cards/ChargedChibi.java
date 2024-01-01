package BuxomMod.cards;

import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;
import BuxomMod.patches.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawPower;

import static BuxomMod.BuxomMod.makeCardPath;

public class ChargedChibi extends AbstractDynamicCard {

    public static final String ID = BuxomMod.makeID(ChargedChibi.class.getSimpleName());
    public static final String IMG = makeCardPath("ChargedChibi.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

    private static final int COST = -2;
    private static final int DAMAGE = 20;
    private static final int UPGRADE_PLUS_DAMAGE = 5;


    public ChargedChibi() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        this.isMultiDamage = true;
        this.tags.add(CustomTags.BOUNCY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.type = CardType.STATUS;
        boolean result = super.canUse(p, m);
        this.type = CardType.SKILL;
        return result;
    }

    public void triggerOnExhaust() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn,
                AbstractGameAction.AttackEffect.FIRE));
        addToBot(new MakeTempCardInDiscardAction(this, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}
