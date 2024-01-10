package BuxomMod.ui;

import BuxomMod.BuxomMod;
import BuxomMod.characters.TheBuxom;
import BuxomMod.powers.BraBrokenPower;
import BuxomMod.powers.CommonPower;
import BuxomMod.powers.ExposedPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.UUID;

import static BuxomMod.BuxomMod.*;

public class BraManager {
    public int currentBuxom;
    public boolean straining;
    public boolean broken;
    public int buxomCounterThisTurn;
    public int buxomGainedThisTurn;
    public int minCapacity;
    public int maxCapacity;
    public int maxBounce;
    public int brokenBouncePenalty;
    public int buffAmount;
    public int permaSize;
    public int permaSizeStart;
    public ArrayList<UUID> embarrassingList = new ArrayList<>();
    public BraManager() {
    }
    public void setPermaSize(int permaSize) {
        this.permaSize = permaSize;
    }
    public int getPermaSize() {
        return permaSize;
    }
    public void onInitialApplication() {
        if (AbstractDungeon.player.hasRelic("BuxomMod:NakedRelic")) {
            growToBreak();
        }
    }

    public void startGame() {
        if (!CardCrawlGame.loadingSave) {
            permaSizeStart = 3;
            permaSize = permaSizeStart;
        }
        broken = false;
        straining = false;
    }

    public void onTurnStart() {
        buxomCounterThisTurn = 0;
        buxomGainedThisTurn = 0;
        embarrassingList.clear();
    }

    public void onStartCombat() {
        buxomCounterThisTurn = 0;
        buxomGainedThisTurn = 0;
        maxCapacity = 6;
        broken = false;
        straining = false;
        maxBounce = 8;
        brokenBouncePenalty = 3;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CommonPower(AbstractDungeon.player, AbstractDungeon.player, permaSize), permaSize));
        if (((TheBuxom)AbstractDungeon.player).naked == true) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new ExposedPower(AbstractDungeon.player, AbstractDungeon.player, -1), -1));
        }
    }

    public void onGrow(int growthAmount) {
        buxomCounterThisTurn++;
        buxomGainedThisTurn += growthAmount;
        breakCheck();
        ((TheBuxom)AbstractDungeon.player).beginGrowth(growthAmount);
        BuxomMod.logger.info("Times gained this turn: " + buxomCounterThisTurn);
        BuxomMod.logger.info("Amount gained this turn: " + buxomGainedThisTurn);
        if (BuxomMod.getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) >= 30 && !(AbstractDungeon.player.hasPower(ExposedPower.POWER_ID))) {
            BuxomMod.logger.info("Buxom: " + BuxomMod.getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) + ". Over 30 Buxom! Exposing!");
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new ExposedPower(AbstractDungeon.player, AbstractDungeon.player, -1), -1));
        }
    }
    public void onShrink(int shrinkAmount) {
        ((TheBuxom)AbstractDungeon.player).beginShrink(shrinkAmount);
    }
    public void braBreak() {
        braPanel.breakVfx();
        maxBounce -= brokenBouncePenalty;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BraBrokenPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ExposedPower(AbstractDungeon.player, AbstractDungeon.player, -1), -1));
        straining = false;
        broken = true;
    }
    public void braRepair() {
        if (broken) {
            broken = false;
            maxBounce += brokenBouncePenalty;
        }
    }

    public boolean inCapacity() {
        if (BuxomMod.getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) > this.maxCapacity) {
            return false;
        } else if (BuxomMod.getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) < this.minCapacity) {
            return false;
        }
        else {
            return true;
        }
    }
    public void changeNaked(boolean naked) {
        if (naked) {
            ((TheBuxom)AbstractDungeon.player).naked = true;
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                            new ExposedPower(AbstractDungeon.player, AbstractDungeon.player, -1), -1));
        }
        else {
            ((TheBuxom)AbstractDungeon.player).naked = false;
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, ExposedPower.POWER_ID));
        }
    }
    public void growToBreak() {
        int bdiff = (this.maxCapacity - getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID)) + 1;
        if (bdiff > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CommonPower(AbstractDungeon.player, AbstractDungeon.player, bdiff), bdiff));
        }
        else { BuxomMod.logger.info("buxom higher than bra capacity, no growing needed"); }
    }
    public void strainCheck() {
        if (getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) <= this.maxCapacity && straining) {
            //logger.info("Within capacity! No longer straining!");
            straining = false;
        }
        if (broken) {
            straining = false;
        }
        if (getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) > this.maxCapacity && !broken) {
            //logger.info("Buxom: " + getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) + ". Capacity: " + this.maxCapacity + ".");
            straining = true;
            //logger.info("Now straining!");
        }
    }
    public void breakCheck() {
        if (getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) > this.maxCapacity && !broken) {
            if (straining == true) {
                this.braBreak();
                logger.info("Buxom: " + getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) + ". Capacity: " + this.maxCapacity + ".");
                logger.info("Bra broke! No longer straining!");
            }
        }
    }
}
