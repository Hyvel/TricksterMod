package theTrickster.cards.skills;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;

import java.util.ArrayList;
import java.util.Iterator;

import static theTrickster.TricksterMod.makeCardPath;

public class TrickyDefend extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(TrickyDefend.class.getSimpleName());
    public static final String IMG = makeCardPath("skills/TrickyDefend.png");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 1;
    private static final int BLOCK = 0;

    private static final int BLOCK_BY_STRIKE = 1;
    private static final int UPGRADE_PLUS_BLOCK_BY_STRIKE = 1;

    // /STAT DECLARATION/


    public TrickyDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = BLOCK_BY_STRIKE;
    }


    private static int countStrikeCards() {
        int count = 0;
        AbstractCard c;
        ArrayList<CardGroup> piles = new ArrayList<CardGroup>();
        piles.add(AbstractDungeon.player.hand);
        piles.add(AbstractDungeon.player.drawPile);
        piles.add(AbstractDungeon.player.discardPile);
        piles.add(AbstractDungeon.player.exhaustPile);

        for (CardGroup pile : piles) {
            Iterator iterator = pile.group.iterator();
            while(iterator.hasNext()) {
                c = (AbstractCard)iterator.next();
                if (isStrike(c)) {
                    ++count;
                }
            }
        }

        return count;
    }

    private static boolean isStrike(AbstractCard c) {
        return c.hasTag(CardTags.STRIKE);
    }


    public void applyPowers() {
        baseBlock = BLOCK + (magicNumber * countStrikeCards());

        super.applyPowers();

        isBlockModified = block != BLOCK;
        this.initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_BLOCK_BY_STRIKE);
            initializeDescription();
        }
    }
}
