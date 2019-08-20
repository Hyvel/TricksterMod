package theTrickster.cards.skills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;

import static theTrickster.TricksterMod.makeCardPath;

public class CautiousManeuver extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(CautiousManeuver.class.getSimpleName());
    public static final String IMG = makeCardPath("skills/CautiousManeuver.png");


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    private static final int BONUS_BLOCK = 7;
    // /STAT DECLARATION/


    public CautiousManeuver() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = BONUS_BLOCK;
    }


    public void applyPowers() {
//        if (upgraded) {
//            baseBlock = BLOCK + UPGRADE_PLUS_BLOCK;
//        }
//        else {
//            baseBlock = BLOCK;
//        }

        if (AbstractDungeon.player.isBloodied) {
            int realBaseBlock = baseBlock;
            // Add bonus block.
            baseBlock += BONUS_BLOCK;
            super.applyPowers();

            //reset baseBlock (necessary as when isBlockModified is false, block is set to baseBlock)
            baseBlock = realBaseBlock;
            isBlockModified = block != BLOCK;
        }
        else {
            super.applyPowers();
        }
    }


    public void calculateCardDamage(AbstractMonster m) {
        if (AbstractDungeon.player.isBloodied) {
            int realBaseBlock = baseBlock;
            // Add bonus block.
            baseBlock += BONUS_BLOCK;
            super.calculateCardDamage(m);

            //reset baseBlock (necessary as when isBlockModified is false, block is set to baseBlock)
            baseBlock = realBaseBlock;
            isBlockModified = block != BLOCK;
        }
        else {
            super.calculateCardDamage(m);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
