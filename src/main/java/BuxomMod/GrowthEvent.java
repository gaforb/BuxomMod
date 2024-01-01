package BuxomMod;

import BuxomMod.characters.TheBuxom;
import com.badlogic.gdx.math.Interpolation;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static BuxomMod.BuxomMod.logger;

public class GrowthEvent {
    private AbstractPlayer p = AbstractDungeon.player;
    public float timer;
    public float timerStart;
    public int howMuch;
    public float animStart;
    public float animTarget;
    public boolean isDone;
    public boolean initiated;
    public GrowthEvent(Float timer, int howMuch){
        this.timer = timer;
        this.timerStart = timer;
        this.howMuch = howMuch;
        logger.info("New growth event");
        logger.info("Timer: " +timer);
        logger.info("Timerstart: " + timerStart);
        logger.info("HowMuch: " + howMuch);
    }
    public void initiate() {
        this.animStart = ((TheBuxom)p).realDisplaySize;
        this.animTarget = animStart + howMuch;
        logger.info("animStart: " + animStart);
        logger.info("animTarget: " + animTarget);
        this.initiated = true;
    }
    public void apply() {
        if (!initiated) {
            initiate();
        }
        float rate = 0;
        logger.info("Timer: " + timer);
        if (timer > 0F) {
            timer -= 1F;
            rate = (Interpolation.swing.apply(animStart, animTarget, (timerStart-timer)/timerStart));
            logger.info("Rate: " + rate);
            logger.info("animStart: " + animStart);
            logger.info("animTarget: " + animTarget);
            logger.info("a: " + (timerStart-timer)/timer);
            ((TheBuxom)p).realDisplaySize = rate;
            if (rate > animStart) {
                ((TheBuxom)p).adjustDisplaySize();
                ((TheBuxom)p).setBoobsStage();
            }
        }
        else { this.isDone = true;}
    }
}
