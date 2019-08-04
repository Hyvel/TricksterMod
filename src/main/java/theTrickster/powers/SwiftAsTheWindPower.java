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
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makePowerPath;

public class SwiftAsTheWindPower extends AbstractPower {
    public static final String POWER_ID = TricksterMod.makeID("SwiftAsTheWindPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("swiftAsTheWind84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("swiftAsTheWind32.png"));

    private static final int CARDS_TO_PLAY = 3;
    public int counter;

    public SwiftAsTheWindPower(AbstractCreature owner, int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        counter = CARDS_TO_PLAY;

        updateDescription();
    }

    public void updateDescription() {
        if (counter == 1) {
            description = DESCRIPTIONS[0] + counter + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + counter + DESCRIPTIONS[2];
        }

        if(amount == 1) {
            description += amount + DESCRIPTIONS[3];
        }
        else {
            description += amount + DESCRIPTIONS[4];
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        --counter;
        if (counter == 0) {
            counter = CARDS_TO_PLAY;
            flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(owner, amount));
        }
        updateDescription();
    }

    public void atStartOfTurn() {
        counter = CARDS_TO_PLAY;
        updateDescription();
    }

}

