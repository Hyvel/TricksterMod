package theTrickster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makePowerPath;

public class MentalStrengthPower extends AbstractPower {
    public static final String POWER_ID = TricksterMod.makeID("MentalStrengthPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("mentalStrength84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("mentalStrength32.png"));

    public MentalStrengthPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        //priority = 25;

        updateDescription();
    }

    public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals("Artifact") && source == this.owner && target == this.owner) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
        }

    }

}

