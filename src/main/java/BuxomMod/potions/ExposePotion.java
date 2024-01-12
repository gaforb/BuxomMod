package BuxomMod.potions;

import BuxomMod.BuxomMod;
import BuxomMod.powers.ExposedPower;
import BuxomMod.powers.MilkPower;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ExposePotion extends CustomPotion {

    public static final String POTION_ID = BuxomMod.makeID("ExposePotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ExposePotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.BOLT, PotionColor.SMOKE);
        
        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();
        
        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1] + potency + DESCRIPTIONS[3];
        
       // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;
        
        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
        
    }
    // See that description? It has DESCRIPTIONS[1] instead of just hard-coding the "text " + potency + " more text" inside.
    // DO NOT HARDCODE YOUR STRINGS ANYWHERE, it's really bad practice to have "Strings" in your code:

    /*
     * 1. It's bad for if somebody likes your mod enough (or if you decide) to translate it.
     * Having only the JSON files for translation rather than 15 different instances of "Dexterity" in some random cards is A LOT easier.
     *
     * 2. You don't have a centralised file for all strings for easy proof-reading. If you ever want to change a string
     * you don't have to go through all your files individually/pray that a mass-replace doesn't screw something up.
     *
     * 3. Without hardcoded strings, editing a string doesn't require a compile, saving you time (unless you clean+package).
     *
     */

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            target = AbstractDungeon.player;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new ExposedPower(AbstractDungeon.player, AbstractDungeon.player, -1), -1));
            addToBot(new GainEnergyAction(potency));
            addToBot(new DrawCardAction(potency));
            BuxomMod.logger.info("potency " + getPotency());
        }
        // If you are in combat, gain strength and the "lose strength at the end of your turn" power, equal to the potency of this potion.
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new ExposePotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 1;
    }

    public void upgradePotion()
    {
      potency += 1;
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[2] + potency + DESCRIPTIONS[3];
      tips.clear();
      tips.add(new PowerTip(name, description));
    }
}
