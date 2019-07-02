package theTrickster.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;
import theTrickster.powers.ForbiddenMedicinePower;

import static theTrickster.TricksterMod.makeCardPath;

public class ForbiddenMedicine extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(ForbiddenMedicine.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int HEALING = 3;
    private static final int UPGRADE_PLUS_HEALING = 1;

    private static final int WOUNDS_AMOUNT = 1;

    // /STAT DECLARATION/


    public ForbiddenMedicine() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = HEALING;
        this.tags.add(CardTags.HEALING);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new ForbiddenMedicinePower(p, magicNumber, WOUNDS_AMOUNT), magicNumber));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_HEALING);
            initializeDescription();
        }
    }
}

