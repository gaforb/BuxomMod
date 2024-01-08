package BuxomMod.vfx;

import BuxomMod.characters.TheBuxom;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static BuxomMod.BuxomMod.logger;

public abstract class AbstractSizeEvent {
    public float timer;
    public float timerStart;
    public float howMuch;
    public float animStart;
    public float animTarget;
    public boolean isDone;
    public boolean initiated;
    public AbstractSizeEvent(Float timer, float howMuch){
        this.timer = timer;
        this.timerStart = timer;
        this.howMuch = howMuch;
        logger.info("New size event");
        logger.info("Timer: " +timer);
        logger.info("Timerstart: " + timerStart);
        logger.info("HowMuch: " + howMuch);
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
