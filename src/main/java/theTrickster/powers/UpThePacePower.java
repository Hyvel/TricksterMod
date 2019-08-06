package theTrickster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTrickster.TricksterMod;
import theTrickster.util.RandomZeroCostCardHelper;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makePowerPath;

public class UpThePacePower extends AbstractPower {
    public static final String POWER_ID = TricksterMod.makeID("UpThePacePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("upThePace84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("upThePace32.png"));

    public UpThePacePower(AbstractCreature owner, int cardsAmount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = cardsAmount;
        type = PowerType.BUFF;
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        priority = 25;

        updateDescription();
    }




    public void updateDescription() {
        if (amount <= 1) {
            description = DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
    }

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();

            for(int i = 0; i < amount; ++i) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(
                        RandomZeroCostCardHelper.returnTrulyRandomZeroCostCardInCombat().makeCopy(), 1, false));
            }
        }
    }
}
