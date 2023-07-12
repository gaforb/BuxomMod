package BuxomMod.powers;

import java.util.Random;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import BuxomMod.BuxomMod;
import BuxomMod.cards.ChibiBlock;
import BuxomMod.cards.ChibiAttack;
import BuxomMod.util.TextureLoader;

public class ChibiPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = BuxomMod.makeID("ChibiPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("BuxomModResources/images/powers/Chibi84.png");
    private static final Texture tex32 = TextureLoader.getTexture("BuxomModResources/images/powers/Chibi32.png");

    public ChibiPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    /*public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            flash();
            Random rand = new Random();
            int r = rand.nextInt(2);
            if (r == 0) {
                AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            } else if (r == 1) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new CommonPower(this.source, this.source, card.cost), card.cost));
            } else if (r == 2) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.source, this.source, new ChibiPower(this.source, this.source, card.cost), card.cost));
            }
        }
    }*/

    public void atStartOfTurn() {
        for (int i = 0; i < this.amount; i++) {
            Random rand = new Random();
            int r = rand.nextInt(2);
            if (r == 1) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction((AbstractCard)new ChibiAttack(), 1));
            }
            else {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction((AbstractCard)new ChibiBlock(), 1));
            }
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ChibiPower(owner, source, amount);
    }
}
