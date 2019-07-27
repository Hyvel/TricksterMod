package theTrickster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makeRelicOutlinePath;
import static theTrickster.TricksterMod.makeRelicPath;

public class DesertRoseRelic extends CustomRelic {

    /*
     * Rare relic.
     * Upgrade the next 3 upgradable cards you obtain.
     */

    // ID, images, text.
    public static final String ID = TricksterMod.makeID("DesertRoseRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("desert_rose.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("desert_rose.png"));

    public DesertRoseRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        this.counter = 3;
        // Reinitializing the description with the correct counter.
        this.description = this.getUpdatedDescription();
        this.resetTips();
    }

    public void resetTips() {
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void setCounter(int counter) {
        this.counter = counter;
        if (counter == 0) {
            // todo: add used up image
            //this.img = ImageMaster.loadImage("images/relics/usedOmamori.png");
            this.usedUp();
        } else if (counter == 1) {
            this.description = DESCRIPTIONS[2];
            this.resetTips();
        } else {
            this.description = DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];
            this.resetTips();
        }
    }

    public void onObtainCard(AbstractCard c) {
        if(!c.canUpgrade() || this.counter <= 0) {
            return;
        }
        this.flash();
        --this.counter;
        c.upgrade();
        this.setCounter(this.counter);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }
}

