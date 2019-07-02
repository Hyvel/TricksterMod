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
     */

    // ID, images, text.
    public static final String ID = TricksterMod.makeID("StrangeDollRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public StrangeDollRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }}
