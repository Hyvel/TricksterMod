package theTrickster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makePowerPath;

public class ForbiddenMedicinePower extends AbstractPower {
    public static final String POWER_ID = TricksterMod.makeID("ForbiddenMedicinePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("forbiddenMedicine84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("forbiddenMedicine32.png"));

    private int woundsAmount;

    public ForbiddenMedicinePower(AbstractCreature owner, int amount, int cursesAmount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        woundsAmount = cursesAmount;
        type = PowerType.BUFF;
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        fontScale = 8.0F;
        amount += stackAmount;
        ++woundsAmount;
    }

    public void updateDescription() {
        if(woundsAmount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
        else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + woundsAmount + DESCRIPTIONS[3];
        }
    }

    public void atStartOfTurn() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new HealAction(owner, owner, amount));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Wound(), woundsAmount));
    }
}


