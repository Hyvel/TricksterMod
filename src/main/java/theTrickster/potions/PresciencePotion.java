package theTrickster.potions;

import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import theTrickster.TricksterMod;

public class PresciencePotion extends AbstractPotion {


    public static final String POTION_ID = TricksterMod.makeID("PresciencePotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public PresciencePotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.M, PotionColor.SMOKE);

        potency = getPotency();

        if(potency == 1) {
            description = DESCRIPTIONS[0];
        }
        else {
            description = DESCRIPTIONS[1] + potency + DESCRIPTIONS[2];
        }

        isThrown = false;

        tips.add(new PowerTip(name, description));

    }

    @Override
    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new SeekAction(potency));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new PresciencePotion();
    }

    @Override
    public int getPotency(final int ascensionLevel) {
        return 1;
    }
}
