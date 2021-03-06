package theTrickster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makePowerPath;

public class MistFormPower extends AbstractPower {
    public static final String POWER_ID = TricksterMod.makeID("MistFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("mistForm84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("mistForm32.png"));

    public MistFormPower(AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void onAfterCardPlayed(AbstractCard card) {
        if(card.cost == 0 || card.costForTurn == 0) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            if (Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new CleaveEffect()));
            } else {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(owner, new CleaveEffect(), 0.2F));
            }

            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction(owner,
                            DamageInfo.createDamageMatrix(amount, true),
                            DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE,
                            true));
        }
    }

}

