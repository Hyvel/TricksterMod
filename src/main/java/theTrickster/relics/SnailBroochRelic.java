package theTrickster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makeRelicOutlinePath;
import static theTrickster.TricksterMod.makeRelicPath;

public class SnailBroochRelic extends CustomRelic {

    /*
     * Rare relic.
     * Whenever you play 4 or less cards in a turn, gain 1 additional energy at the start of your next turn.
     */

    public static final String ID = TricksterMod.makeID("SnailBroochRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("snail_brooch.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("snail_brooch.png"));

    private static final int ENERGY = 1;
    private static final int MAX_CARDS_TO_PLAY = 4;
    private boolean firstTurn = true;

    public SnailBroochRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    public void atBattleStart() {
        counter = 0;
        firstTurn = true;
    }

    public void atTurnStart() {
        if (counter <= MAX_CARDS_TO_PLAY && !firstTurn) {
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(ENERGY));
        } else {
            firstTurn = false;
        }

        counter = 0;
        beginLongPulse();
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        ++counter;
        if (counter > MAX_CARDS_TO_PLAY) {
            stopPulse();
        }

    }

    public void onVictory() {
        counter = -1;
        stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }}

