package theTrickster.util;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import theTrickster.TricksterMod;
import theTrickster.characters.TheTrickster;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class RandomZeroCostCardHelper {

    //zeroCostCardPool has 0 cost cards of the following colors : red, blue, green, colorless, this mod color.
    public static CardGroup zeroCostCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    //alternateZeroCostCardPool has 0 cost cards of any color, including other mods colors.
    public static CardGroup alternateZeroCostCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);



    private static void initAlternateCardPool() {
        alternateZeroCostCardPool.clear();

        AbstractCard card;
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

            alternateZeroCostCardPool.addToBottom((AbstractCard)c.getValue());
        }
    }

    private static void initStandardCardPool() {
        zeroCostCardPool.clear();

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
            } while(!BaseMod.isBaseGameCardColor(card.color) && card.color != TheTrickster.Enums.COLOR_BROWN);

            zeroCostCardPool.addToBottom((AbstractCard)c.getValue());
        }
    }

    public static void initializeZeroCostCardPools() {
        initStandardCardPool();
        initAlternateCardPool();
    }


    public static AbstractCard returnTrulyRandomZeroCostCardInCombat() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        Iterator var1;
        if(TricksterMod.generateCardsFromOtherMods) {
            var1 = alternateZeroCostCardPool.group.iterator();
        }
        else {
            var1 = zeroCostCardPool.group.iterator();
        }

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (!c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }

        return list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1));
    }
}
