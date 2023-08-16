package BuxomMod.actions;

import BuxomMod.BuxomMod;
import BuxomMod.powers.CommonPower;
import BuxomMod.powers.MilkPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Donu;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class HungryDwarfAction extends AbstractGameAction {
    private int increaseHpAmount;
    private DamageInfo info;
    private static final float DURATION = 0.1F;
    private int milk;
    private int buxom;
    private int energy;

    public HungryDwarfAction(AbstractCreature target, DamageInfo info, int howMuchMilk, int howMuchBuxom, int howMuchEnergy) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
        this.milk = howMuchMilk;
        this.buxom = howMuchBuxom;
        this.energy = howMuchEnergy;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.duration == 0.1F && this.target != null) {
            addToBot(new VFXAction(new BiteEffect(target.hb.cX, target.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(target, info,
                            AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            BuxomMod.logger.info("target.isDying = " + target.isDying);
            BuxomMod.logger.info("target.currentHealth = " + target.currentHealth);
            BuxomMod.logger.info("target.halfDead = " + target.halfDead);
            if ((target.isDying || target.currentHealth <= 0) && !target.halfDead) {
                BuxomMod.logger.info("target died");
                addToBot(new ApplyPowerAction(p, p, new MilkPower(p, p, milk)));
                addToBot(new ApplyPowerAction(p, p, new CommonPower(p, p, buxom)));
                addToBot(new GainEnergyAction(energy));
            } else { BuxomMod.logger.info("target didn't die");}

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        this.isDone = true;
    }
}
