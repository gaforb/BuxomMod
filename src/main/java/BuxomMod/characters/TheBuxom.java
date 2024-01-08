package BuxomMod.characters;

import BuxomMod.powers.CommonPower;
import BuxomMod.powers.ExposedPower;
import BuxomMod.util.TextureLoader;
import BuxomMod.vfx.AbstractSizeEvent;
import BuxomMod.vfx.ShrinkEvent;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.*;
import com.esotericsoftware.spine.attachments.Attachment;
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
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import BuxomMod.BuxomMod;
import BuxomMod.cards.*;
import BuxomMod.relics.DefaultClickableRelic;
import BuxomMod.relics.JCupRelic;
import BuxomMod.relics.DwarfBoobsRelic;
import BuxomMod.relics.PlaceholderRelic2;
import BuxomMod.vfx.GrowthEvent;

import java.util.ArrayList;
import java.util.Iterator;

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
    public static final int STARTING_HP = 80;
    public static final int MAX_HP = 80;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================

    // =============== STRINGS =================

    private static final String ID = makeID("DefaultCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;
    public static String boobBoneNID = "boobn";
    public static String boobBoneFID = "boobf";
    public static String atlasURL = "BuxomModResources/images/char/character/LehmanaSprite9.atlas";
    public static String skeletonURL = "BuxomModResources/images/char/character/LehmanaSprite9.json";
    public static final String size1animName = "idle";
    public static final String size2animName = "idle2";
    public static final String size3animName = "idle3";
    public static final String size4animName = "idle4";
    public static final String exposeAnimName = "expose";
    public static final String unexposeAnimName = "unexpose";
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
    public Skin exposed = new Skin("exposed");
    public Skin clothed = new Skin("clothed");
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
        exposed.addAttachment(getSkeleton().findSlotIndex(shirtTopSlot.toString()), "shirt_top", shirtTopVisible);
        exposed.addAttachment(getSkeleton().findSlotIndex(chestSlot.toString()), "chest_exposed", chestExposed);

        clothed.addAttachment(getSkeleton().findSlotIndex(shirtTopSlot.toString()), "shirt_top_placeholder", shirtTopPlaceholder);
        clothed.addAttachment(getSkeleton().findSlotIndex(chestSlot.toString()), "chest", chestClothed);

        //skeleton.getData().getDefaultSkin().addAttachment(getSkeleton().findSlotIndex(chestSlot.toString()), "chest_exposed", chestExposed);


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
        AnimationState.TrackEntry e = state.setAnimation(1, "idle", true);
        this.stateData.setDefaultMix(1.0F);
        e.setTimeScale(1.0F);
        AnimationState.TrackEntry f = state.setAnimation(2, "idle_boobs", true);


        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    //animations
    public float scalerate = 0.025F;
    public float threshhold1 = 10;
    public float threshhold2 = 20;
    public float threshhold3 = 30;
    public float animTime = 60F;
    public float animTimer = 60F;
    public float messageTimer = 60F;
    public float animStart = 0;
    public float animTarget = 0;
    public String sizeToApply = size1animName;
    public int currRange = 0;
    public float targetDisplaySize = 1F;
    public float realDisplaySize = 1F;
    public float adjustedDisplaySize = 1F;
    public boolean naked = false;
    public Slot shirtTopSlot = skeleton.findSlot("shirt top");
    public Attachment shirtTopPlaceholder = skeleton.getAttachment(String.valueOf(shirtTopSlot), "shirt_top_placeholder");
    public Attachment shirtTopVisible = skeleton.getAttachment(String.valueOf(shirtTopSlot), "shirt top");
    public Slot chestSlot = skeleton.findSlot("chest");
    public Attachment chestClothed = skeleton.getAttachment(String.valueOf(chestSlot), "chest_clothed");
    public Attachment chestExposed = skeleton.getAttachment(String.valueOf(chestSlot), "chest_ex");
    public Animation lastAnim;
    public ArrayList<AbstractSizeEvent> sizeQueue = new ArrayList<AbstractSizeEvent>();

    public static AbstractGameEffect growVfx(float x, float y) {
        return new VfxBuilder(TextureLoader.getTexture(STARTING_BUXOM_ICON), 0.3f)
                .useAdditiveBlending()
                .setX(x)
                .setY(y)
                .fadeOut(0.3f)
                .build();
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }


    public void timedMessage() {
        if (messageTimer <= 0) {
            messageTimer = 600F;
            /*logger.info("-------------------------------------------");
            for (Slot s : skeleton.getSlots()) {
                if (s != null) {
                    logger.info("Slot: " + s);
                }
                if (s.getAttachment() != null) {
                    logger.info("Attachment: " + s.getAttachment().getName());
                }
                else {logger.info("Attachment: null");}
                logger.info("Index: " + s.getData().getIndex());
                logger.info("-------------------------------------------");
                logger.info("current skin: " + skeleton.getSkin());
            }*/
        }
        messageTimer -= 1F;
    }
    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        loadAnimation(
                atlasURL,
                skeletonURL,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(1, "idle", true);
        this.stateData.setDefaultMix(0.1F);
        e.setTimeScale(1.0F);
        AnimationState.TrackEntry f = state.setAnimation(2, "idle_boobs", true);
        currRange = 0;
        targetDisplaySize = 1;
        realDisplaySize = 1;
        adjustedDisplaySize = 1;
        animStart = 0;
        animTarget = 0;
    }

    public void resetIdle() {
        if (this.state.getCurrent(1).getAnimation().getName() == null) {
            AnimationState.TrackEntry e = this.state.setAnimation(1, size1animName, true);
        }
    }

    public void setNakedAttachments(boolean naked) {
        if (naked) {
            skeleton.setAttachment("shirt top", "shirt_top_placeholder");
            skeleton.setAttachment("chest", "chest_naked");
            skeleton.setAttachment("fshoulder", "fshoulder_naked");
            skeleton.setAttachment("Nshoulder", "nshoulder_naked");
            skeleton.setAttachment("thighf", "thighf_naked");
            skeleton.setAttachment("thighn", "thighn_naked");
            skeleton.setAttachment("pelvis", "pelvis_naked");
            skeleton.setAttachment("calfF", "calfF_naked");
            skeleton.setAttachment("calfn", "calfn_naked");
        }
        else {
            skeleton.setAttachment("fshoulder", "fshoulder");
            skeleton.setAttachment("Nshoulder", "nshoulder");
            skeleton.setAttachment("thighf", "thighf");
            skeleton.setAttachment("thighn", "thighn");
            skeleton.setAttachment("pelvis", "pelvis");
            skeleton.setAttachment("calfF", "calfF");
            skeleton.setAttachment("calfn", "calfn");
        }
    }

    public void changeStateBoobs(String stateName) {
        String boobsName;
        if (this.hasPower(ExposedPower.POWER_ID)) {
            skeleton.setAttachment("shirt top", "shirt top");
            skeleton.setAttachment("chest", "chest_ex");
        }
        else {
            skeleton.setAttachment("shirt top", "shirt_top_placeholder");
            skeleton.setAttachment("chest", "chest_clothed");
        }
        switch (stateName) {
            case size1animName:
                boobsName = "boobs1";
                if (this.hasPower(ExposedPower.POWER_ID) || this.naked) { boobsName += "-ex"; }
                this.skeleton.setAttachment("boobs", boobsName);
                this.skeleton.setAttachment("boobn", "boobn_placeholder");
                this.skeleton.setAttachment("boobf", "boobf_placeholder");
                break;
            case size2animName:
                boobsName = "boobs2";
                if (this.hasPower(ExposedPower.POWER_ID) || this.naked) { boobsName += "-ex"; }
                skeleton.setAttachment("boobs", boobsName);
                skeleton.setAttachment("boobn", "boobn_placeholder");
                skeleton.setAttachment("boobf", "boobf_placeholder");
                break;
            case size3animName:
                skeleton.setAttachment("boobs", "boobs3");
                if (this.hasPower(ExposedPower.POWER_ID) || this.naked) {
                    skeleton.setAttachment("boobs", null);
                    skeleton.setAttachment("boobn", "boobsEx3N");
                    skeleton.setAttachment("boobf", "boobsEx3F");
                }
                break;
            case size4animName:
                skeleton.setAttachment("shirt top", "shirt top");
                skeleton.setAttachment("chest", "chest_ex");
                skeleton.setAttachment("boobs", null);
                skeleton.setAttachment("boobn", "boobn");
                skeleton.setAttachment("boobf", "boobf");
                break;
        }
    }

    public void changeStateBodyAnimation(String stateName) {
        AnimationState.TrackEntry e;
        switch (stateName) {
            case size1animName:
                e = this.state.addAnimation(1, stateName, true, 0.0F);
                break;
            case size2animName:
                e = this.state.addAnimation(1, stateName, true, 0.0F);
                break;
            case size3animName:
                e = this.state.addAnimation(1, stateName, true, 0.0F);
                break;
            case size4animName:
                e = this.state.addAnimation(1, stateName, true, 0.0F);
                break;
        }
        logger.info("Switched body anim to: " + this.state.getCurrent(1).getAnimation().getName());
    }

    /*public void changeState(String stateName) {
        AnimationState.TrackEntry e;
        switch (stateName) {
            case size1animName:
                if (this.hasPower(ExposedPower.POWER_ID)) {
                    stateName += "_ex";
                }
                loadAnimation(
                        atlasURL,
                        skeletonURL,
                        1.0f);
                e = this.state.setAnimation(1, stateName, true);
                getSkeleton().setAttachment(boobsSlotName, "boobs1");
                getSkeleton().setAttachment("boobn", null);
                getSkeleton().setAttachment("boobf", null);
                getSkeleton().setAttachment("face", "face1");
                getSkeleton().setAttachment("chest", "chest");
                break;
            case size2animName:
                if (this.hasPower(ExposedPower.POWER_ID)) {
                    stateName += "_ex";
                }
                loadAnimation(
                        atlasURL,
                        skeletonURL,
                        1.0f);
                e = this.state.setAnimation(1, stateName, true);
                getSkeleton().setAttachment(boobsSlotName, "boobs2");
                getSkeleton().setAttachment("boobn", null);
                getSkeleton().setAttachment("boobf", null);
                getSkeleton().setAttachment("face", "face1");
                getSkeleton().setAttachment("chest", "chest");
                break;
            case size3animName:
                if (this.hasPower(ExposedPower.POWER_ID)) {
                    stateName += "_ex";
                }
                loadAnimation(
                        atlasURL,
                        skeletonURL,
                        1.0f);
                e = this.state.setAnimation(1, stateName, true);
                getSkeleton().setAttachment(boobsSlotName, "boobs3-ex");
                getSkeleton().setAttachment("boobn", null);
                getSkeleton().setAttachment("boobf", null);
                getSkeleton().setAttachment("face", "face2");
                getSkeleton().setAttachment("chest", "chest");
                break;
            case size4animName:
                loadAnimation(
                        atlasURL,
                        skeletonURL,
                        1.0f);
                e = this.state.setAnimation(1, stateName, true);
                getSkeleton().setAttachment(boobsSlotName, null);
                getSkeleton().setAttachment("boobn", "boobn");
                getSkeleton().setAttachment("boobf", "boobf");
                getSkeleton().setAttachment("face", "face2");
                getSkeleton().setAttachment("chest", "chest_ex");
                break;
        }
    }*/

    public String getExposedName(String anim) {
        if (this.hasPower(ExposedPower.POWER_ID)) {
            return anim + "_ex";
        }
        return anim;
    }

    public void updateExposed() {
        AnimationState.TrackEntry e;
        String currAnimName = this.state.getCurrent(2).getAnimation().getName();
        if (this.hasPower(ExposedPower.POWER_ID) && (!currAnimName.contains("_ex") && !currAnimName.contains("idle4"))) {
            logger.info("currAnimName: " + currAnimName);
            changeStateBoobs(currAnimName);
            skeleton.setSkin(exposed);
            logger.info("exposed, current skin: " + skeleton.getSkin());
        } else if (!this.hasPower(ExposedPower.POWER_ID) && currAnimName.contains("_ex")) {
            String currAnimNameClothed = currAnimName.substring(0, currAnimName.length() - 9);
            logger.info(currAnimNameClothed);
            changeStateBoobs(currAnimNameClothed);
            skeleton.setSkin(clothed);
            logger.info("unexposed, current skin: " + skeleton.getSkin());
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
    private AbstractGameEffect vfx(float x, float y) {
        return new VfxBuilder(TextureLoader.getTexture("BuxomModResources/images/vfx/expand_effect.png"), 1.0f)
                .setX(x)
                .setY(y)
                .playSoundAt(0.35f, BuxomMod.makeID("HEARTBEAT"))
                .playSoundAt(0.35f, BuxomMod.makeID("LOW_GASP"))
                .build();
    }
    public void beginGrowth(float amount) {
        targetDisplaySize = getPwrAmt(this, CommonPower.POWER_ID) + amount;
        logger.info("getpwramt: " + getPwrAmt(this, CommonPower.POWER_ID));
        logger.info("BeginGrowth, target display size: " + targetDisplaySize);
        if (amount <= 5) {
            sizeQueue.add(new GrowthEvent(animTime, amount));
        } else {
            float perEvent = amount / 3;
            sizeQueue.add(new GrowthEvent(animTime, perEvent));
            sizeQueue.add(new GrowthEvent(animTime, perEvent));
            sizeQueue.add(new GrowthEvent(animTime, perEvent));
        }
        /*logger.info("growthqueue size: " + growthQueue.size());
        logger.info("Animate growth for " + animTime + " frames");
        logger.info("realDisplaySize (" + realDisplaySize + ") < targetDisplaySize (" + targetDisplaySize + ")");
        logger.info("animTarget (" + animTarget + "), animCurrent (" + animStart + ")");*/
    }
    public void beginShrink(float amount) {
        logger.info("BeginShrink, target display size: " +targetDisplaySize);
        sizeQueue.add(new ShrinkEvent(animTime, amount));
        logger.info("growthqueue size: " + sizeQueue.size());
        /*logger.info("Animate growth for " + animTime + " frames");
        logger.info("realDisplaySize (" + realDisplaySize + ") < targetDisplaySize (" + targetDisplaySize + ")");
        logger.info("animTarget (" + animTarget + "), animCurrent (" + animStart + ")");*/
    }
    public void animateSize() {
        Iterator i = sizeQueue.iterator();
        if (i.hasNext()) {
            AbstractSizeEvent e = (AbstractSizeEvent) i.next();
            if (e.isDone) {
                logger.info("Size event isDone: " + e.isDone + ", removing");
                sizeQueue.remove(e);
                setIdleStage();
            }
            e.apply();
        }
    }
    public void adjustDisplaySize() {
        adjustedDisplaySize = realDisplaySize;
        while (adjustedDisplaySize >= 10) {
            if (adjustedDisplaySize >= threshhold3) {
                adjustedDisplaySize -= threshhold3;
                break;
            }
            else {adjustedDisplaySize -= 10;}
        }
        logger.info("adjusted display size: " + adjustedDisplaySize);
    }
    public void setIdleStage() {
        switch (currRange) {
            case 3:
                changeStateBodyAnimation(size4animName);
                break;
            case 2:
                changeStateBodyAnimation(size3animName);
                break;
            case 1:
                changeStateBodyAnimation(size2animName);
                break;
            case 0:
                changeStateBodyAnimation(size1animName);
                break;
        }
        logger.info("changed idle stage: " + currRange + ". current animation: " + this.state.getCurrent(1).getAnimation().getName());
    }
    public void setBoobsStage() {
        switch (currRange) {
            case 3:
                sizeToApply = size4animName;
                break;
            case 2:
                sizeToApply = size3animName;
                break;
            case 1:
                sizeToApply = size2animName;
                break;
            case 0:
                sizeToApply = size1animName;
                break;
        }
    }
    public void calculateScale() {
        targetDisplaySize = getPwrAmt(this, CommonPower.POWER_ID);
        animateSize();
        if (realDisplaySize > targetDisplaySize && sizeQueue.isEmpty()) {
            logger.info("realDisplaySize- (" + realDisplaySize + ") > targetDisplaySize (" + targetDisplaySize + ")");
            logger.info("sizeQueue is empty, size is: " + sizeQueue.size());
            beginShrink(realDisplaySize - targetDisplaySize);
        }
        /*if (Math.abs(realDisplaySize - targetDisplaySize) <= 0.03){
            logger.info("realDisplaySize (" + realDisplaySize + "), targetDisplaySize (" + targetDisplaySize +")");
            realDisplaySize = targetDisplaySize;
        }*/
        if (realDisplaySize >= threshhold3) {
            if (currRange != 3) {
                logger.info("threshold3 exceeded");
            }
            currRange = 3;
        }
        else if (realDisplaySize >= threshhold2 && realDisplaySize < threshhold3) {
            if (currRange != 2) {
                logger.info("threshold2 exceeded");
            }
            currRange = 2;
        }
        else if (realDisplaySize >= threshhold1 && realDisplaySize < threshhold2) {
            if (currRange != 1) {
                logger.info("threshold1 exceeded");
            }
            currRange = 1;
        }
        else {
            if (currRange != 0) {
                logger.info("threshold1 de-exceeded");
            }
            currRange = 0;
        }
    }

    public void updateScale(float scaler) {
        resetIdle();
        Bone boobN = getSkeleton().findBone(boobBoneNID);
        Bone boobF = getSkeleton().findBone(boobBoneFID);
        Float addScale = scaler*scalerate + 1F;
        boobN.setScale(addScale);
        boobF.setScale(addScale);

        //updateExposed();
        //logger.info("update function: current skin: " + skeleton.getSkin());
        timedMessage();
        //logger.info("state applied: current skin: " + skeleton.getSkin());
        //logger.info("state applied: current chest attachment: " + skeleton.getAttachment(skeleton.findSlotIndex(String.valueOf(chestSlot)),String.valueOf(chestSlot)));
    }
    @Override
    public void renderPlayerImage(SpriteBatch sb) {
        //logger.info("renderPlayerImage: " + getSkeleton().findSlot(boobsSlotName).getAttachment().getName());
        calculateScale();
        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);
        this.skeleton.updateWorldTransform();
        this.skeleton.setPosition(this.drawX + this.animX, this.drawY + this.animY);
        this.skeleton.setColor(this.tint.color);
        this.skeleton.setFlip(this.flipHorizontal, this.flipVertical);
        updateScale(adjustedDisplaySize);
        changeStateBoobs(sizeToApply);
        setNakedAttachments(this.naked);
        sb.end();
        CardCrawlGame.psb.begin();
        sr.draw(CardCrawlGame.psb, this.skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
    }
    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        //buxomPanel.render(sb, this);
    }

    @Override
    public void update() {
        super.update();
        //buxomPanel.update(this);
        startingBuxomPanel.update(this);
        braPanel.update(this);
        bounceMaxPanel.update(this);
        debugInfoPanel.update(this);
        //logger.info("update: " + getSkeleton().findSlot(boobsSlotName).getAttachment().getName());
        braManager.strainCheck();
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

    /*public static void renderBuxomPanel(SpriteBatch sb, AbstractPlayer player)
    {
        logger.info("panel render called");
        buxomPanel.render(sb, player);
    }*/

}
