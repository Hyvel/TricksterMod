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
import theTrickster.DefaultMod;
import theTrickster.util.TextureLoader;

import static theTrickster.DefaultMod.makePowerPath;

public class MistFormPower extends AbstractPower {
    public static final String POWER_ID = DefaultMod.makeID("MistFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("mistForm84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("mistForm32.png"));

    public MistFormPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onAfterCardPlayed(AbstractCard card) {
        if(card.cost == 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            if (Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new CleaveEffect()));
            } else {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new CleaveEffect(), 0.2F));
            }

            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction(this.owner,
                            DamageInfo.createDamageMatrix(this.amount, true),
                            DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE,
                            true));
        }
    }

}

