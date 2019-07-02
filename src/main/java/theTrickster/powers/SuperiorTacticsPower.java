package theTrickster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makePowerPath;

public class SuperiorTacticsPower extends AbstractPower {
    public static final String POWER_ID = TricksterMod.makeID("SuperiorTacticsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("superiorTactics84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("superiorTactics32.png"));

    private static final int CARDS_TO_RETAIN = 2;

    public SuperiorTacticsPower(AbstractCreature owner, int cardsAmount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = cardsAmount;
        type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.priority = 6;

        updateDescription();
    }

    public void atEndOfRound() {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner,POWER_ID ));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }

    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && !AbstractDungeon.player.hand.isEmpty() &&
                !AbstractDungeon.player.hasRelic("Runic Pyramid") &&
                !AbstractDungeon.player.hasPower("Equilibrium")) {
            AbstractDungeon.actionManager.addToBottom(new RetainCardsAction(this.owner, CARDS_TO_RETAIN));
        }

    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        }
        else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

}
