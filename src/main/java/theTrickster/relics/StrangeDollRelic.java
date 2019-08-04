package theTrickster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makeRelicOutlinePath;
import static theTrickster.TricksterMod.makeRelicPath;

public class StrangeDollRelic extends CustomRelic {

    /*
     * Common relic.
     * When you get debuffed, deal 3 damage to a random enemy
     * Effect is done in the receivePostPowerApplySubscriber method in TricksterMod.java
     */

    public static final String ID = TricksterMod.makeID("StrangeDollRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("strange_doll.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("strange_doll.png"));

    public StrangeDollRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }}
