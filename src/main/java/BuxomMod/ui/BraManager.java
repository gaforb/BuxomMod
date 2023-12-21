package BuxomMod.ui;

import BuxomMod.BuxomMod;
import BuxomMod.powers.BraBrokenPower;
import BuxomMod.powers.CommonPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static BuxomMod.BuxomMod.*;

public class BraManager {
    public int currentBuxom;
    public boolean straining;
    public boolean broken;
    public int minCapacity;
    public int maxCapacity;
    public int maxBounce;
    public int brokenBouncePenalty;
    public int buffAmount;
    public int permaSize;
    public int permaSizeStart;
    public BraManager() {
    }
    public void setPermaSize(int permaSize) {
        this.permaSize = permaSize;
    }
    public int getPermaSize() {
        return permaSize;
    }
    public void onInitialApplication() {
        if (AbstractDungeon.player.hasRelic("BuxomMod:ToplessArtifact")) {
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

    public void onStartCombat() {
        maxCapacity = 10;
        broken = false;
        straining = false;
        maxBounce = 8;
        brokenBouncePenalty = 3;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CommonPower(AbstractDungeon.player, AbstractDungeon.player, permaSize), permaSize));
    }

    public void onGrow(int growthAmount) {
    }
    public void onShrink(int shrinkAmount) {
    }
    public void braBreak() {
        braPanel.breakVfx();
        maxBounce -= brokenBouncePenalty;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BraBrokenPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        straining = false;
        broken = true;
    }
    public void braRepair() {
        broken = false;
        maxBounce += brokenBouncePenalty;
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
    public void growToBreak() {
        int bdiff = (this.maxCapacity - getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID)) + 1;
        if (bdiff > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CommonPower(AbstractDungeon.player, AbstractDungeon.player, bdiff), bdiff));
        }
        else { BuxomMod.logger.info("buxom higher than bra capacity, no growing needed"); }
    }
    public void strainSanityCheck() {
        if (getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) < this.maxCapacity && straining) {
            logger.info("Within capacity! No longer straining!");
            straining = false;
        }
        if (broken) {
            straining = false;
        }
    }
    public void breakCheck() {
        if (getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) > this.maxCapacity && !broken) {
            if (straining == true) {
                this.braBreak();
                logger.info("Buxom: " + getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) + ". Capacity: " + this.maxCapacity + ".");
                logger.info("Bra broke! No longer straining!");
            }
            else {
                logger.info("Buxom: " + getPwrAmt(AbstractDungeon.player, CommonPower.POWER_ID) + ". Capacity: " + this.maxCapacity + ".");
                straining = true;
                logger.info("Now straining!");
            }
        } else {straining = false;}
    }

    public void atEndOfTurn(boolean isPlayer) { // At the end of your turn
        breakCheck();
    }

}
