package theTrickster.cards.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;
import theTrickster.powers.StopTheBleedingPower;

import static theTrickster.TricksterMod.makeCardPath;

public class StopTheBleeding extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(StopTheBleeding.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int CHARGES = 2;
    private static final int UPGRADE_PLUS_CHARGES = 1;

    // /STAT DECLARATION/


    public StopTheBleeding() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = CHARGES;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new StopTheBleedingPower(p, magicNumber), magicNumber));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_CHARGES);
            initializeDescription();
        }
    }
}

