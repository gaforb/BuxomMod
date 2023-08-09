package BuxomMod.blockMods;

import BuxomMod.BuxomMod;
import BuxomMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

public class ChibiBlock extends AbstractBlockModifier {
    public static final String ID = BuxomMod.makeID("SpicyBlock");
    public final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ChibiBlock() {}

    @Override
    public String getName() {
        return cardStrings.NAME;
    }

    @Override
    public String getDescription() {
        return cardStrings.DESCRIPTION;
    }

    public Texture customBlockImage() {
        return TextureLoader.getTexture("BuxomModResources/images/powers/Chibi32.png");
    }

    @Override
    public AbstractBlockModifier makeCopy() {
        return null;
    }
}
