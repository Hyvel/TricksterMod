package theTrickster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makeRelicOutlinePath;
import static theTrickster.TricksterMod.makeRelicPath;

public class RunicTorusRelic extends CustomRelic {

    /*
     * Boss relic.
     * Every 0 cost card played, gain 2 block.
     */

    // ID, images, text.
    public static final String ID = TricksterMod.makeID("RunicTorusRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public RunicTorusRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.cost == 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(
                    new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(
                    new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 2));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

