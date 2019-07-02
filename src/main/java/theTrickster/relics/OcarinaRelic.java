package theTrickster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makeRelicOutlinePath;
import static theTrickster.TricksterMod.makeRelicPath;

public class OcarinaRelic extends CustomRelic {

    /*
     * Uncommon relic.
     * The first time each combat you draw a Status, draw 3 cards.
     */

    // ID, images, text.
    public static final String ID = TricksterMod.makeID("OcarinaRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    private static final int CARDS = 3;
    private static boolean usedThisCombat = false;

    public OcarinaRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    public void atPreBattle() {
        usedThisCombat = false;
        this.pulse = true;
        this.beginPulse();
    }

    public void onCardDraw(AbstractCard drawnCard) {
        if(usedThisCombat) {
            return;
        }
        if(drawnCard.type == AbstractCard.CardType.STATUS) {
            this.flash();
            this.pulse = false;
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 3));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            usedThisCombat = true;
        }
    }

    public void onVictory() {
        this.pulse = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }}


