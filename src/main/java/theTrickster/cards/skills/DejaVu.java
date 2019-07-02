package theTrickster.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;

import static theTrickster.TricksterMod.makeCardPath;

public class DejaVu extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(DejaVu.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("DejaVu.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 0;

    private static final int BLUR = 1;
    private static final int UPGRADE_PLUS_BLUR = 1;


    // /STAT DECLARATION/

    public DejaVu() {
        this(0);
    }

    public DejaVu(int upgrades) {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = BLUR;
        this.timesUpgraded = upgrades;
    }

    public AbstractCard makeCopy() {
        return new DejaVu(this.timesUpgraded);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BlurPower(p, magicNumber), magicNumber));
    }

    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        upgradeMagicNumber(UPGRADE_PLUS_BLUR);
        this.upgraded = true;
        ++this.timesUpgraded;
        this.name = NAME + "+" + this.timesUpgraded;
        this.rawDescription = UPGRADE_DESCRIPTION;
        this.initializeTitle();
        initializeDescription();
    }
}
