package theTrickster.cards.skills;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;

import static theTrickster.TricksterMod.makeCardPath;

public class FancyCape extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(FancyCape.class.getSimpleName());
    public static final String IMG = makeCardPath("skills/FancyCape.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 0;

    private static final int HEAL = 2;

    private static final int ART_AND_DRAW = 1;
    private static final int UPGRADE_PLUS_ART_AND_DRAW = 1;

    // /STAT DECLARATION/


    public FancyCape() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = ART_AND_DRAW;
        tags.add(CardTags.HEALING);
        exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new ArtifactPower(p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, HEAL));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_ART_AND_DRAW);
            initializeDescription();
        }
    }
}
