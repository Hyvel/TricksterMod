package theTrickster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.DefaultMod;
import theTrickster.util.TextureLoader;

import static theTrickster.DefaultMod.makeRelicOutlinePath;
import static theTrickster.DefaultMod.makeRelicPath;

public class SnailBroochRelic extends CustomRelic {

    /*
     * Rare relic.
     * Whenever you play 4 or less cards in a turn, gain 1 additional energy at the start of your next turn.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("SnailBroochRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    private static final int ENERGY = 1;
    private static final int MAX_CARDS_TO_PLAY = 4;
    private boolean firstTurn = true;

    public SnailBroochRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    public void atBattleStart() {
        this.counter = 0;
        this.firstTurn = true;
    }

    public void atTurnStart() {
        if (this.counter <= MAX_CARDS_TO_PLAY && !this.firstTurn) {
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(ENERGY));
        } else {
            this.firstTurn = false;
        }

        this.counter = 0;
        this.beginLongPulse();
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        ++this.counter;
        if (this.counter > MAX_CARDS_TO_PLAY) {
            this.stopPulse();
        }

    }

    public void onVictory() {
        this.counter = -1;
        this.stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }}

