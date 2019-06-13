package theTrickster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTrickster.DefaultMod;
import theTrickster.util.TextureLoader;

import static theTrickster.DefaultMod.makePowerPath;

public class SwiftAsTheWindPower extends AbstractPower {
    public static final String POWER_ID = DefaultMod.makeID("SwiftAsTheWindPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("swiftAsTheWind84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("swiftAsTheWind32.png"));

    private static final int CARDS_TO_PLAY = 3;
    public int counter;

    public SwiftAsTheWindPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.counter = CARDS_TO_PLAY;

        updateDescription();
    }

    public void updateDescription() {
        if (this.counter == 1) {
            this.description = DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[2];
        }

        if(this.amount == 1) {
            this.description += this.amount + DESCRIPTIONS[3];
        }
        else {
            this.description += this.amount + DESCRIPTIONS[4];
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        --this.counter;
        if (this.counter == 0) {
            this.counter = CARDS_TO_PLAY;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
        }
        this.updateDescription();
    }

    public void atStartOfTurn() {
        this.counter = CARDS_TO_PLAY;
        this.updateDescription();
    }

}

