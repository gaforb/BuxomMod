package BuxomMod.vfx;

import BuxomMod.characters.TheBuxom;
import BuxomMod.util.TextureLoader;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.math.Interpolation;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static BuxomMod.BuxomMod.STARTING_BUXOM_ICON;
import static BuxomMod.BuxomMod.logger;

public abstract class AbstractSizeEvent {
    public AbstractPlayer owner;
    public float timer;
    public float timerStart;
    public float howMuch;
    public float animStart;
    public float animTarget;
    public boolean isDone;
    public boolean initiated;
    public AnimationState.TrackEntry e;
    public AbstractSizeEvent(Float timer, float howMuch, AbstractPlayer owner){
        this.owner = owner;
        this.timer = timer;
        this.timerStart = timer;
        this.howMuch = howMuch;
        logger.info("New size event");
        logger.info("Timer: " +timer);
        logger.info("Timerstart: " + timerStart);
        logger.info("HowMuch: " + howMuch);
    }
    public AbstractSizeEvent(Float timer, float howMuch, AbstractPlayer owner, Animation anim){
        this.owner = owner;
        this.timer = timer;
        this.timerStart = timer;
        this.howMuch = howMuch;
        logger.info("New size event");
        logger.info("Timer: " +timer);
        logger.info("Timerstart: " + timerStart);
        logger.info("HowMuch: " + howMuch);
    }
    public AbstractGameEffect growVfx(float x, float y, float duration) {
        return new VfxBuilder(TextureLoader.getTexture(STARTING_BUXOM_ICON), duration)
                .useAdditiveBlending()
                .setX(x)
                .setY(y)
                .fadeOut(duration)
                .build();
    }
    public AbstractGameEffect growVfxF(float duration) {
        return new VfxBuilder(TextureLoader.getTexture(STARTING_BUXOM_ICON), duration)
                .useAdditiveBlending()
                .setX(((TheBuxom)this.owner).getBoobFXPosition())
                .setY(((TheBuxom)this.owner).getBoobFYPosition())
                .fadeOut(duration)
                .build();
    }
    public AbstractGameEffect growVfxN(float duration) {
        return new VfxBuilder(TextureLoader.getTexture(STARTING_BUXOM_ICON), duration)
                .useAdditiveBlending()
                .setX(((TheBuxom)this.owner).getBoobNXPosition())
                .setY(((TheBuxom)this.owner).getBoobNYPosition())
                .fadeOut(duration)
                .build();
    }
    public void initiate() {
        this.animStart = ((TheBuxom)AbstractDungeon.player).realDisplaySize;
        this.animTarget = animStart + howMuch;
        logger.info("animStart: " + animStart);
        logger.info("animTarget: " + animTarget);
        this.initiated = true;
    }
    public void apply() {
        if (!initiated) {
            initiate();

        }

    }
}
