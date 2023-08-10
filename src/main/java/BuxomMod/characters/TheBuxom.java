package BuxomMod.characters;

import BuxomMod.powers.CommonPower;
import BuxomMod.powers.ExposedPower;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import BuxomMod.BuxomMod;
import BuxomMod.cards.*;
import BuxomMod.relics.DefaultClickableRelic;
import BuxomMod.relics.JCupRelic;
import BuxomMod.relics.DwarfBoobsRelic;
import BuxomMod.relics.PlaceholderRelic2;

import java.util.ArrayList;

import static BuxomMod.BuxomMod.*;
import static BuxomMod.characters.TheBuxom.Enums.COLOR_PINK;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in DefaultMod-character-Strings.json in the resources

public class TheBuxom extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(BuxomMod.class.getName());

    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_BUXOM;
        @SpireEnum(name = "PINK_BUXOM_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_PINK;
        @SpireEnum(name = "PINK_BUXOM_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 2;

    // =============== /BASE STATS/ =================

    // =============== STRINGS =================

    private static final String ID = makeID("DefaultCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    public static String boobBoneNID = "boobn";
    public static String boobBoneFID = "boobf";
    public static String atlasURL = "BuxomModResources/images/char/character/LehmanaSprite4.atlas";
    public static String skeletonURL = "BuxomModResources/images/char/character/LehmanaSprite4_Armaturelehmana sprite.json";
    public static final String size1animName = "idle";
    public static final String size2animName = "idle2";
    public static final String size3animName = "idle3";
    public static final String boobsSlotName = "boobs";

    // =============== /STRINGS/ =================

    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "BuxomModResources/images/char/defaultCharacter/orb/layer1.png",
            "BuxomModResources/images/char/defaultCharacter/orb/layer2.png",
            "BuxomModResources/images/char/defaultCharacter/orb/layer3.png",
            "BuxomModResources/images/char/defaultCharacter/orb/layer4.png",
            "BuxomModResources/images/char/defaultCharacter/orb/layer5.png",
            "BuxomModResources/images/char/defaultCharacter/orb/layer6.png",
            "BuxomModResources/images/char/defaultCharacter/orb/layer1d.png",
            "BuxomModResources/images/char/defaultCharacter/orb/layer2d.png",
            "BuxomModResources/images/char/defaultCharacter/orb/layer3d.png",
            "BuxomModResources/images/char/defaultCharacter/orb/layer4d.png",
            "BuxomModResources/images/char/defaultCharacter/orb/layer5d.png",};

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================

    public TheBuxom(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "BuxomModResources/images/char/defaultCharacter/orb/vfx.png", null,
                new SpineAnimation(
                        atlasURL, skeletonURL, 0.3f));
        for (Skin s : skeleton.getData().getSkins()) {
            logger.info("Skin: " + s.getName());
        }
        for (SlotData s : skeleton.getData().getSlots()) {
            logger.info("Slot: " + s.getName() + " Attachment: " + s.getAttachmentName() + " Index: " + s.getIndex());
        }
        for (Animation a : skeleton.getData().getAnimations()) {
            logger.info("Animation: " + a.getName());
        }
        logger.info(skeletonURL + " loaded");


        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_BUXOM_SHOULDER_2, // campfire pose
                THE_BUXOM_SHOULDER_1, // another campfire pose
                THE_BUXOM_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================

        // =============== ANIMATIONS =================  

        loadAnimation(
                atlasURL,
                skeletonURL,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, size1animName, true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setDefaultMix(0.1F);
        e.setTimeScale(1.0F);


        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    //animations
    public float scalerate = 0.03F;
    public float threshhold1 = 15;
    public float threshhold2 = 25;
    public int currRange = 0;
    public Skeleton getSkeleton() {
        return skeleton;
    }

    public void changeState(String stateName) {
        AnimationState.TrackEntry e;
        switch (stateName) {
            case size1animName:
                loadAnimation(
                        atlasURL,
                        skeletonURL,
                        1.0f);
                e = this.state.setAnimation(0, getExposedName(size1animName), true);
                e.setTime(e.getEndTime() * MathUtils.random());
                getSkeleton().setAttachment(boobsSlotName, "boobs1");
                getSkeleton().setAttachment("face", "face1");
                getSkeleton().setAttachment("chest", "chest");
                getSkeleton().update(0.01F);
                break;
            case size2animName:
                loadAnimation(
                        atlasURL,
                        skeletonURL,
                        1.0f);
                e = this.state.setAnimation(0, getExposedName(size2animName), true);
                e.setTime(e.getEndTime() * MathUtils.random());
                getSkeleton().setAttachment(boobsSlotName, "boobs2");
                getSkeleton().setAttachment("face", "face2");
                getSkeleton().setAttachment("chest", "chest");
                getSkeleton().update(0.01F);
                break;
            case size3animName:
                loadAnimation(
                        atlasURL,
                        skeletonURL,
                        1.0f);
                e = this.state.setAnimation(0, size3animName, true);
                e.setTime(e.getEndTime() * MathUtils.random());
                getSkeleton().setAttachment(boobsSlotName, null);
                getSkeleton().setAttachment("face", "face2");
                getSkeleton().setAttachment("chest", "chest_ex");
                getSkeleton().update(0.01F);
                break;
        }
    }

    public String getExposedName(String anim) {
        if (this.hasPower(ExposedPower.POWER_ID)) {
            return anim + "_ex";
        }
        return anim;
    }

    public void updateExposed(Boolean expose) {
        String currAnimName = this.state.getCurrent(0).getAnimation().getName();
        if (expose == true && !currAnimName.contains("_ex")) {
            changeState(currAnimName + "_ex");
        } else if (expose == false && currAnimName.contains("_ex")) {
            changeState(currAnimName.substring(0, currAnimName.length() - 3));
        }
    }

    /*public void updateExposed() {
        AnimationState.TrackEntry e;
        String currAnimName = this.state.getCurrent(0).getAnimation().getName();
        logger.info("current animation: " + currAnimName);
        loadAnimation(
                atlasURL,
                skeletonURL,
                1.0f);
        e = this.state.setAnimation(0, getExposedName(), true);
        e.setTime(e.getEndTime() * MathUtils.random());
        getSkeleton().setAttachment(boobsSlotName, "boobs2_ex");
        getSkeleton().setAttachment("face", "face2");
        getSkeleton().setAttachment("chest", "chest");
    }*/

    /*public void updateExposed() {
        if (this.hasPower(ExposedPower.POWER_ID)) {
            getSkeleton().setAttachment("chest", "chest_ex");
            getSkeleton().setAttachment("shirt top", "shirt top");
            logger.info(this.state.getCurrent(0).getAnimation().getName());
            switch (this.state.getCurrent(0).getAnimation().getName()) {
                case size1animName:
                    logger.info("has exposed, currently idle");
                    getSkeleton().setAttachment(boobsSlotName, "boobs1-ex");
                    logger.info("updateExposed, idle: " + getSkeleton().findSlot(boobsSlotName).getAttachment().getName());
                    getSkeleton().setAttachment("face", "face2");
                    break;
                case size2animName:
                    logger.info("has exposed, currently idle_size3");
                    getSkeleton().setAttachment(boobsSlotName, "boobs2-ex");
                    logger.info("updateExposed, idle_size3: " + getSkeleton().findSlot(boobsSlotName).getAttachment().getName());
                    getSkeleton().setAttachment("face", "face2");
                    break;
            }
        }
        else {
            getSkeleton().setAttachment("chest", "chest");
            getSkeleton().setAttachment("face", "face1");
            getSkeleton().setAttachment("shirt top", null);
        }
    }*/

    public float calculateScale() {
        float size = getPwrAmt(this, CommonPower.POWER_ID);
        float displaySize = size;
        if (size >= threshhold2) {
            if (currRange != 2) {
                logger.info("threshold2 exceeded");
                changeState(size3animName);
            }
            displaySize = size - threshhold2;
            currRange = 2;
        }
        else if (size >= threshhold1 && size < threshhold2) {
            if (currRange != 1) {
                logger.info("threshold1 exceeded");
                changeState(size2animName);
            }
            displaySize = size - threshhold1;
            currRange = 1;
        }
        else {
            if (currRange != 0) {
                logger.info("threshold1 de-exceeded");
                changeState(size1animName);
            }
            displaySize = size;
            currRange = 0;
        }
        return displaySize;
    }

    public void updateScale(float scaler) {
        Bone boobN = getSkeleton().findBone(boobBoneNID);
        Bone boobF = getSkeleton().findBone(boobBoneFID);
        Float scale = scaler*scalerate + 1F;
        boobN.setScale(scale);
        boobN.update();
        boobF.setScale(scale);
        boobF.update();
    }
    @Override
    public void renderPlayerImage(SpriteBatch sb) {
        updateScale(calculateScale());
        logger.info("renderPlayerImage: " + getSkeleton().findSlot(boobsSlotName).getAttachment().getName());
        super.renderPlayerImage(sb);
    }
    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        //buxomPanel.render(sb, this);
        braPanel.render(sb, this);
    }

    @Override
    public void update() {
        super.update();
        //buxomPanel.update(this);
        braPanel.update(this);
        logger.info("update: " + getSkeleton().findSlot(boobsSlotName).getAttachment().getName());
    }





    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        retVal.add(DefaultCommonAttack.ID);
        retVal.add(DefaultCommonAttack.ID);
        retVal.add(DefaultCommonAttack.ID);
        retVal.add(DefaultCommonAttack.ID);

        retVal.add(DefaultCommonSkill.ID);
        retVal.add(DefaultCommonSkill.ID);
        retVal.add(DefaultCommonSkill.ID);
        retVal.add(DefaultCommonSkill.ID);

        retVal.add(BouncyExercise.ID);
        retVal.add(Omegabsorption.ID);

        return retVal;
    }
    /*public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        retVal.add(DefaultCommonAttack.ID);
        retVal.add(BouncyExercise.ID);
        retVal.add(OmegaForce.ID);

        retVal.add(DefaultCommonSkill.ID);
        retVal.add(ExpansiveWall.ID);
        retVal.add(DefaultRareSkill.ID);

        retVal.add(DefaultCommonPower.ID);
        retVal.add(ChibiSummon.ID);
        retVal.add(BovineForm.ID);

        retVal.add(DefaultAttackWithVariable.ID);
        retVal.add(OmegaFumes.ID);
        retVal.add(KCupBra.ID);
        retVal.add(MCupBra.ID);
        retVal.add(Omegabsorption.ID);

        return retVal;
    }*/

    // Starting Relics
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(DwarfBoobsRelic.ID);

        // Mark relics as seen - makes it visible in the compendium immediately
        // If you don't have this it won't be visible in the compendium until you see them in game
        UnlockTracker.markRelicAsSeen(JCupRelic.ID);
        UnlockTracker.markRelicAsSeen(PlaceholderRelic2.ID);
        UnlockTracker.markRelicAsSeen(DefaultClickableRelic.ID);

        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA(BuxomMod.makeID("RIP_SHORT"), 1.00f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return BuxomMod.makeID("RIP_SHORT");
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_PINK;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return BUXOM_PINK;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new DefaultCommonAttack();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheBuxom(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return BuxomMod.BUXOM_PINK;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return BuxomMod.BUXOM_PINK;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public static void renderBuxomPanel(SpriteBatch sb, AbstractPlayer player)
    {
        logger.info("panel render called");
        buxomPanel.render(sb, player);
    }

}
