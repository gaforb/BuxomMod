package BuxomMod.cards;

import BuxomMod.DefaultMod;
import BuxomMod.characters.TheDefault;
import BuxomMod.powers.BraBreakerPower;
import BuxomMod.powers.BraPower;
import BuxomMod.powers.CommonPower;
import BuxomMod.relics.DwarfBoobsRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Random;

import static BuxomMod.DefaultMod.getPwrAmt;
import static BuxomMod.DefaultMod.makeCardPath;

public class BraBreaker extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * TOUCH Deal 30(35) damage.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BraBreaker.class.getSimpleName());
    public static final String IMG = makeCardPath("BraBreaker.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;

    private static final int MAGIC = 5;
    private static final int UPGRADE_PLUS_MAGIC = 3;

    // /STAT DECLARATION/


    public BraBreaker() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean bra = false;
        for (AbstractPower pow : p.powers) { //if wearing a bra
            if (pow instanceof BraPower) {
                bra = true;
                ((BraPower) pow).growToBreak();
                if (p.hasRelic(DwarfBoobsRelic.ID)) {
                    p.getRelic(DwarfBoobsRelic.ID).counter += 2;
                }
                addToBot(new ApplyPowerAction(p, p, new BraBreakerPower(p, p, this.magicNumber), this.magicNumber));
            }
        }
        if (bra = true) {
            Random randInt = new Random();
            int randomBra = randInt.nextInt(2);
            AbstractCard braID = null;
            switch (randomBra) {
                case 0: braID = new BrokenBraK();
                    break;
                case 1: braID = new BrokenBraM();
                    break;
                case 2: braID = new BrokenBraT();
                    break;
            }
            addToBot(new MakeTempCardInHandAction(braID));
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_MAGIC);
        }
    }
}