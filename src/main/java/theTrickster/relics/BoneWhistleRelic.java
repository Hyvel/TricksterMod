package theTrickster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makeRelicOutlinePath;
import static theTrickster.TricksterMod.makeRelicPath;

public class BoneWhistleRelic extends CustomRelic {

    /*
     * Rare relic.
     * Every time you draw a status, deal 3 damage to a random enemy.
     */

    public static final String ID = TricksterMod.makeID("BoneWhistleRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("bone_whistle.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("bone_whistle.png"));

    private static final int DAMAGE = 3;

    public BoneWhistleRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }

    public void onCardDraw(AbstractCard drawnCard) {
        if(drawnCard.type == AbstractCard.CardType.STATUS) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(
                    new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, DAMAGE, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }}



