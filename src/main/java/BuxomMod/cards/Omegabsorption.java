package BuxomMod.cards;

import BuxomMod.actions.GrowAction;
import basemod.helpers.VfxBuilder;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import BuxomMod.powers.CommonPower;
import BuxomMod.util.TextureLoader;
import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;
import com.megacrit.cardcrawl.actions.animations.VFXAction;

import static BuxomMod.BuxomMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Omegabsorption extends AbstractDynamicCard {

/*
 * "Hey, I wanna make a bunch of cards now." - You, probably.
 * ok cool my dude no problem here's the most convenient way to do it:
 *
 * Copy all of the code here (Ctrl+A > Ctrl+C)
 * Ctrl+Shift+A and search up "file and code template"
 * Press the + button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
 * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with ${NAME}
 * And then you can do custom ones like ${DAMAGE} and ${TARGET} if you want.
 * I'll leave some comments on things you might consider replacing with what.
 *
 * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template you'll
 * likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe you want a
 * universal template where you delete everything unnecessary - up to you)
 *
 * You can create templates for anything you ever want to. Cards, relics, powers, orbs, etc. etc. etc.
 */

// TEXT DECLARATION

public static final String ID = BuxomMod.makeID(Omegabsorption.class.getSimpleName());
public static final String IMG = makeCardPath("Omegabsorption.png");// "public static final String IMG = makeCardPath("${NAME}.png");
// This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


// /TEXT DECLARATION/


// STAT DECLARATION

private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
private static final CardType TYPE = CardType.SKILL;       //
public static final CardColor COLOR = TheBuxom.Enums.COLOR_PINK;

private static final int COST = 0;  // COST = ${COST}
private static final int UPGRADED_COST = 0; // UPGRADED_COST = ${UPGRADED_COST}

private static final int BLOCK = 5;    // DAMAGE = ${DAMAGE}
private static final int UPGRADE_PLUS_BLOCK = 3;  // UPGRADE_PLUS_DMG = ${UPGRADED_DAMAGE_INCREASE}
private static final int MAGIC = 1;
private static final int UPGRADE_PLUS_MAGIC = 1;

// /STAT DECLARATION/


public Omegabsorption() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        }

/*private AbstractGameEffect buildVfx(float startX, Hitbox hb) {
    return new VfxBuilder(TextureLoader.getTexture("BuxomModResources/images/vfx/expand_effect.png"), Settings.ACTION_DUR_MED)

            .fadeIn(0.25f)
            .fadeOut(0.25f)
            .setScale(1.25f)
            .playSoundAt(0.35f, DefaultMod.makeID("GASP"))
            .build();
}*/
// Actions the card should do.
/*private AbstractGameEffect vfx2(float x, float y) {
    return new VfxBuilder(TextureLoader.getTexture("BuxomModResources/images/vfx/library_effect_2.png"), 2.0f)
            .setX(x)
            .setY(y)
            .playSoundAt(0f, DefaultMod.makeID("RIP_MEDIUM"))
            .scale(0.5f, 0.6f, VfxBuilder.Interpolations.ELASTICOUT)
            .fadeIn(0.5f)
            .fadeOut(1.0f)
            .build();
}
private AbstractGameEffect vfx(float x, float y) {
    return new VfxBuilder(TextureLoader.getTexture("BuxomModResources/images/vfx/library_effect.png"), 1.0f)
            .setX(x)
            .setY(y)
            .playSoundAt(0.75f, DefaultMod.makeID("SUDDEN_GASP"))
            .playSoundAt(0.35f, DefaultMod.makeID("HEARTBEAT"))
            .scale(0.45f, 0.5f)
            .fadeIn(0.25f)
            .triggerVfxAt(0.5f, 1, this::vfx2)
            .build();
}*/
private AbstractGameEffect vfx(float x, float y) {
    return new VfxBuilder(TextureLoader.getTexture("BuxomModResources/images/vfx/expand_effect.png"), 1.0f)
            .setX(x)
            .setY(y)
            .playSoundAt(0.35f, BuxomMod.makeID("HEARTBEAT"))
            .playSoundAt(0.35f, BuxomMod.makeID("LOW_GASP"))
            .scale(0.45f, 0.5f, VfxBuilder.Interpolations.ELASTIC)
            .fadeIn(0.25f)
            .andThen(1.0f)
            .scale(0.5f, 0.55f, VfxBuilder.Interpolations.ELASTIC)
            .fadeOut(0.75f)
            .build();
}
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ExhaustAction(1, false));
        addToBot(new VFXAction(vfx(p.drawX, p.drawY)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new CommonPower(p, p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, magicNumber));
        addToBot(new GrowAction(magicNumber));
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