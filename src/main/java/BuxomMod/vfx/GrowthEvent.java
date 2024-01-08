package BuxomMod.vfx;

import BuxomMod.characters.TheBuxom;
import com.badlogic.gdx.math.Interpolation;
import com.esotericsoftware.spine.Bone;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static BuxomMod.BuxomMod.logger;

public class GrowthEvent extends AbstractSizeEvent{
    public GrowthEvent(Float timer, float howMuch){
        super(timer, howMuch);
        this.timer = timer;
        this.timerStart = timer;
        this.howMuch = howMuch;
        logger.info("New growth event");
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
        float rate = 0F;
        if (timer > 0F) {
            timer -= 1F;
            rate = (Interpolation.exp5.apply(animStart, animTarget, (timerStart-timer)/timerStart));
            /*logger.info("Rate: " + rate);
            logger.info("animStart: " + animStart);
            logger.info("animTarget: " + animTarget);
            logger.info("a: " + (timerStart-timer)/timer);*/
            ((TheBuxom)AbstractDungeon.player).realDisplaySize = rate;
            if (rate > animStart) {
                ((TheBuxom)AbstractDungeon.player).adjustDisplaySize();
                ((TheBuxom)AbstractDungeon.player).setBoobsStage();
            }
        }
        else { ((TheBuxom)AbstractDungeon.player).setBoobsStage();
            this.isDone = true;}
    }
}
