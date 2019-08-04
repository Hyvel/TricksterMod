package theTrickster.cards.skills;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;
import theTrickster.util.TheTricksterTags;

import static theTrickster.TricksterMod.makeCardPath;

public class ImpulsiveDefend extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(ImpulsiveDefend.class.getSimpleName());
    public static final String IMG = makeCardPath("skills/ImpulsiveDefend.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 0;
    private static final int BLOCK = 12;

    private static final int MINUS_BLOCK_PER_TURN = 2;

    // /STAT DECLARATION/


    public ImpulsiveDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        tags.add(TheTricksterTags.DEFEND);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MINUS_BLOCK_PER_TURN;
        exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }


    public void applyPowers() {
        baseBlock = BLOCK - ((GameActionManager.turn - 1) * magicNumber );

        super.applyPowers();

        isBlockModified = block != BLOCK;
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isInnate = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
