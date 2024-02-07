package BuxomMod.events;

import BuxomMod.BuxomMod;
import BuxomMod.actions.IncreaseStartingBuxomAction;
import BuxomMod.cards.LactatingSkill;
import BuxomMod.ui.BraManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.exordium.LivingWall;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static BuxomMod.BuxomMod.*;

public class FoundOutEvent extends AbstractImageEvent {


    public static final String ID = BuxomMod.makeID("FoundOutEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("foundoutinitial.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;
    private String secondImgUrl = makeEventPath("foundoutrelief.png");
    private String thirdImgUrl = makeEventPath("foundoutanxiety.png");
    private Choice choice;
    private boolean pickCard;

    private float HEALTH_LOSS_PERCENTAGE = 0.15F; // 3%
    private float HEALTH_LOSS_PERCENTAGE_LOW_ASCENSION = 0.10F; // 5%
    private float HEAL_PERCENTAGE = 0.20F;


    private int healthdamage; //The actual number of how much Max HP we're going to lose.
    private int healAmt;

    public FoundOutEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        choice = null;

        if (AbstractDungeon.ascensionLevel >= 15) { // If the player is ascension 15 or above, lose 5% max hp. Else, lose just 3%.
            healthdamage = (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE);
        } else {
            healthdamage = (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE_LOW_ASCENSION);
        }

        healAmt = (int) ((float) AbstractDungeon.player.maxHealth * HEAL_PERCENTAGE);

        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0] + healAmt + OPTIONS[1]); // Inspiration - Gain a Random Starting Relic
        imageEventText.setDialogOption(OPTIONS[2] + healthdamage + OPTIONS[3]);
        pickCard = false;
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0: // If you press button the first button (Button at index 0), in this case: Inspiration.
                        this.choice = Choice.DONT;
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.loadImage(secondImgUrl);// Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[6]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        AbstractDungeon.player.heal(this.healAmt);
                        braManager.permaSize += 2;
                        screenNum = 1; // Screen set the screen number to 1. Once we exit the switch (i) statement,
                        // we'll still continue the switch (screenNum) statement. It'll find screen 1 and do it's actions
                        // (in our case, that's the final screen, but you can chain as many as you want like that)
                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Denial
                        this.choice = Choice.DO;
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
                        this.imageEventText.loadImage(thirdImgUrl);
                        AbstractDungeon.player.damage(new DamageInfo((AbstractCreature)null, this.healthdamage));
                        AbstractDungeon.player.decreaseMaxHealth(this.healthdamage);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[6]);
                        CardCrawlGame.sound.play("CARD_EXHAUST");
                        if (CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0) {
                            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[3], false, false, false, true);
                        }
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        this.pickCard = true;

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

    private static enum Choice {
        DONT,
        DO;

        private Choice() {
        }
    }

    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            switch (this.choice) {
                case DO:
                    super.update();
                    if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                        AbstractCard c = (AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        AbstractDungeon.player.masterDeck.removeCard(c);
                        AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
                    }
                    break;
                default:
                    break;
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.pickCard = false;
        }

    }

}
