package BuxomMod.events;

import BuxomMod.cards.LactatingSkill;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import BuxomMod.BuxomMod;

import static BuxomMod.BuxomMod.makeEventPath;
import static BuxomMod.BuxomMod.makeID;

public class ChibiEvent extends AbstractImageEvent {


    public static final String ID = BuxomMod.makeID("ChibiEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("chibisblack.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;
    private String secondImgUrl = makeEventPath("chibis.png");

    private float HEALTH_LOSS_PERCENTAGE = 0.03F; // 3%
    private float HEALTH_LOSS_PERCENTAGE_LOW_ASCENSION = 0.05F; // 5%

    private int healthdamage; //The actual number of how much Max HP we're going to lose.

    public ChibiEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        if (AbstractDungeon.ascensionLevel >= 15) { // If the player is ascension 15 or above, lose 5% max hp. Else, lose just 3%.
            healthdamage = (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE);
        } else {
            healthdamage = (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE_LOW_ASCENSION);
        }

        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[2] + healthdamage + OPTIONS[3]); // Inspiration - Gain a Random Starting Relic
        imageEventText.setDialogOption(OPTIONS[0]);
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.loadImage(secondImgUrl);// Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[6]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        AbstractDungeon.player.decreaseMaxHealth(this.healthdamage);
                        CardCrawlGame.sound.play(makeID("LOW_GASP"));
                        AbstractDungeon.player.masterDeck.addToTop(new LactatingSkill());
                        AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(new LactatingSkill(), (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                        screenNum = 1; // Screen set the screen number to 1. Once we exit the switch (i) statement,
                        // we'll still continue the switch (screenNum) statement. It'll find screen 1 and do it's actions
                        // (in our case, that's the final screen, but you can chain as many as you want like that)
                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Denial

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[6]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        // Same as before. A note here is that you can also do
                        // imageEventText.clearAllDialogs();
                        // imageEventText.setDialogOption(OPTIONS[1]);
                        // imageEventText.setDialogOption(OPTIONS[4]);
                        // (etc.)
                        // And that would also just set them into slot 0, 1, 2... in order, just like what we do in the very beginning
                        break; // Onto screen 1 we go.
                }
                break;
            case 1: // Welcome to screenNum = 1;
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
        }
    }

    public void update() { // We need the update() when we use grid screens (such as, in this case, the screen for selecting a card to remove)
        super.update(); // Do everything the original update()
         // Create the card removal effect
        // if you want to continue using the other selected cards for something

    }

}
