package theTrickster.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import theTrickster.characters.TheTrickster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class RandomZeroCostCardHelper {

    //zeroCostCardPool has 0 cost cards of any colors.
    //alternateZeroCostCardPool has 0 cost cards of colorless or brown (this mod) color.
    public static CardGroup zeroCostCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    public static CardGroup alternateZeroCostCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);



    private static void initStandardCardPool() {
        zeroCostCardPool.clear();

        AbstractCard card = null;
        Iterator var2 = CardLibrary.cards.entrySet().iterator();

        while(true) {
            Map.Entry c;
            do {
                do {
                    if (!var2.hasNext()) {
                        return;
                    }

                    c = (Map.Entry)var2.next();
                    card = (AbstractCard)c.getValue();
                } while(card.rarity == AbstractCard.CardRarity.BASIC || card.rarity == AbstractCard.CardRarity.SPECIAL);
            } while(card.type == AbstractCard.CardType.STATUS || card.cost != 0);

            zeroCostCardPool.addToBottom((AbstractCard)c.getValue());
        }
    }

    private static void initAlternateCardPool() {
        alternateZeroCostCardPool.clear();

        AbstractCard card = null;
        Iterator var2 = CardLibrary.cards.entrySet().iterator();

        while(true) {
            Map.Entry c;
            do {
                do {
                    do {
                        if (!var2.hasNext()) {
                            return;
                        }

                        c = (Map.Entry)var2.next();
                        card = (AbstractCard)c.getValue();
                    } while(card.rarity == AbstractCard.CardRarity.BASIC || card.rarity == AbstractCard.CardRarity.SPECIAL);
                } while(card.type == AbstractCard.CardType.STATUS || card.cost != 0);
            } while(card.color != AbstractCard.CardColor.COLORLESS && card.color != TheTrickster.Enums.COLOR_BROWN);

            alternateZeroCostCardPool.addToBottom((AbstractCard)c.getValue());
        }
    }

    public static void initializeZeroCostCardPools() {
        initStandardCardPool();
        initAlternateCardPool();
    }


    public static AbstractCard returnTrulyRandomZeroCostCardInCombat() {
        ArrayList<AbstractCard> list = new ArrayList();
        Iterator var1 = zeroCostCardPool.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (!c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        return (AbstractCard)list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }
}
