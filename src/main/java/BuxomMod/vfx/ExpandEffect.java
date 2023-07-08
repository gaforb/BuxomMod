package BuxomMod.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;



public class ExpandEffect
        extends AbstractGameEffect
{


    public ExpandEffect(float x, float y, int amount) {
        /*this.x = power.owner.hb.cX;
        this.y = power.owner.hb.cY;
        CardCrawlGame.sound.play(DefaultMod.makeID("GASP"));
        this.img = TextureLoader.getTexture(DefaultMod.EXPAND_EFFECT);
        this.duration = 0.7F;
        this.startingDuration = 0.7F;
        this.color = Color.WHITE.cpy();*\

        /*AbstractDungeon.effectsQueue.add(new HealNumberEffect(x, y, amount));

        for (int i = 0; i < 18; i++) {
            AbstractDungeon.effectsQueue.add(new HealVerticalLineEffect(x +

                    MathUtils.random(-X_JITTER * 1.5F, X_JITTER * 1.5F), y + OFFSET_Y +
                    MathUtils.random(-Y_JITTER, Y_JITTER)));
        }*/
    }


    public void update() {
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {}

    public void dispose() {}
}