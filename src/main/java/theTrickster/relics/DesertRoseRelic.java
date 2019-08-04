package theTrickster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
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

    public static final String ID = TricksterMod.makeID("DesertRoseRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("desert_rose.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("desert_rose.png"));

    public DesertRoseRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        counter = 3;
        // Reinitializing the description with the correct counter.
        description = getUpdatedDescription();
        resetTips();
    }

    public void resetTips() {
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void setCounter(int counter) {
        counter = counter;
        if (counter == 0) {
            img = ImageMaster.loadImage(makeRelicPath("used_desert_rose.png"));
            usedUp();
        } else if (counter == 1) {
            description = DESCRIPTIONS[2];
            resetTips();
        } else {
            description = DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
            resetTips();
        }
    }

    public void onObtainCard(AbstractCard c) {
        if(!c.canUpgrade() || counter <= 0) {
            return;
        }
        flash();
        --counter;
        c.upgrade();
        setCounter(counter);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }
}

