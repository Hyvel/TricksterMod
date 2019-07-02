package theTrickster.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;
import theTrickster.powers.EfficiencyPower;

import static theTrickster.TricksterMod.makeCardPath;

public class Efficiency extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(Efficiency.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("Efficiency .png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int CARDS_PLAYED_TWICE = 1;
    private static final int UPGRADE_PLUS_CARDS_PLAYED_TWICE = 1;

    // /STAT DECLARATION/


    public Efficiency() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = CARDS_PLAYED_TWICE;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new EfficiencyPower(p, this.magicNumber), this.magicNumber));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_CARDS_PLAYED_TWICE);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
