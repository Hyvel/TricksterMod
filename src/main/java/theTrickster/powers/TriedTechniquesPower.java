package theTrickster.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theTrickster.DefaultMod;
import theTrickster.util.TextureLoader;

import static theTrickster.DefaultMod.makePowerPath;

public class TriedTechniquesPower extends AbstractPower {
    public static final String POWER_ID = DefaultMod.makeID("TriedTechniquesPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public TriedTechniquesPower(AbstractCreature owner, int cardsAmount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = cardsAmount;
        type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        priority = 25;

        updateDescription();
    }

    public void updateDescription() {
/*        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4];
        }*/
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

//    public void atStartOfTurn() {
//        this.flash();
//        for (int i = 0; i < amount; i++) {
//            if (AbstractDungeon.player.discardPile.size() > 0) {
//                AbstractDungeon.actionManager.addToBottom(new DiscardPileToHandAction(1));
//            }
//        }
//    }


    public void atStartOfTurn() {
        this.flash();
        AbstractPlayer player = AbstractDungeon.player;
        for (int i = 0; i < amount; i++) {
            if (player.hand.size() >= 10 || player.discardPile.size() == 0) {
                player.createHandIsFullDialog();
                return;
            }
            AbstractCard card;

//            int index = player.discardPile.group.size() - 1;
            int index = AbstractDungeon.cardRandomRng.random(player.discardPile.group.size() - 1);
            card = (AbstractCard)player.discardPile.group.get(index);
            player.hand.addToHand(card);
            card.lighten(false);
            player.discardPile.removeCard(card);
            player.hand.refreshHandLayout();
        }
    }
}

