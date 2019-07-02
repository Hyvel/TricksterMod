package theTrickster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makeRelicOutlinePath;
import static theTrickster.TricksterMod.makeRelicPath;

public class OpticalIllusionRelic extends CustomRelic {

    /*
     * Starting relic.
     * Every 3 cards played, gain 2 block and deal 2 damage to all ennemies.
     */

    // ID, images, text.
    public static final String ID = TricksterMod.makeID("OpticalIllusionRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public OpticalIllusionRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
            ++this.counter;
            if (this.counter % 4 == 0) {
                this.flash();
                this.counter = 0;
                AbstractDungeon.actionManager.addToBottom(
                        new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(
                        new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 3));
                AbstractDungeon.actionManager.addToBottom(
                        new DamageAllEnemiesAction((AbstractCreature)null,
                                DamageInfo.createDamageMatrix(3, true),
                                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HEAVY));
            }
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }}
