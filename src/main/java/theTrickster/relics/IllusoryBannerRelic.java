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
import theTrickster.DefaultMod;
import theTrickster.util.TextureLoader;

import static theTrickster.DefaultMod.makeRelicOutlinePath;
import static theTrickster.DefaultMod.makeRelicPath;

public class IllusoryBannerRelic extends CustomRelic {

    /*
     * Boss relic.
     * Every card played, gain 1 block and deal 1 damage to all enemies.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("IllusoryBannerRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public IllusoryBannerRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(
                new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 1));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction((AbstractCreature)null,
                        DamageInfo.createDamageMatrix(1, true),
                        DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(OpticalIllusionRelic.ID);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(OpticalIllusionRelic.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(OpticalIllusionRelic.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

