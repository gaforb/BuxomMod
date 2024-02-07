package BuxomMod.vfx;

import BuxomMod.characters.TheBuxom;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static BuxomMod.BuxomMod.logger;

public class PauseEvent extends AbstractSizeEvent{
    public PauseEvent(Float timer, AbstractPlayer owner){
        super(timer, 0, owner);
        this.timer = timer;
        this.timerStart = timer;
        this.howMuch = 0;
        logger.info("New pause event");
        logger.info("Timer: " + timer);
        logger.info("Timerstart: " + timerStart);
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
            //AbstractDungeon.effectsQueue.add(growVfxF(timer/60F));
            //AbstractDungeon.effectsQueue.add(growVfxN(timer/60F));
        }
        float rate = 0F;
        if (timer > 0F) {
            timer -= 1F;
        }
        else { ((TheBuxom)AbstractDungeon.player).setBoobsStage();
            this.isDone = true;}
    }
}
