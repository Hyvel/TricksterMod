package theTrickster.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class RandomZeroCostCardHelper {


    public static CardGroup zeroCostCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);


    public static void initializeZeroCostCardPools() {

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
            } while(UnlockTracker.isCardLocked((String)c.getKey()) && !Settings.treatEverythingAsUnlocked());

            zeroCostCardPool.addToBottom((AbstractCard)c.getValue());
        }

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
