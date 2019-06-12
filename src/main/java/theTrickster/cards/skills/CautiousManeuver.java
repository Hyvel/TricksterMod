package theTrickster.cards.skills;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.DefaultMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;

import static theTrickster.DefaultMod.makeCardPath;

public class CautiousManeuver extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(CautiousManeuver.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("CautiousManeuver.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    private static final int BONUS_BLOCK = 7;
    // /STAT DECLARATION/


    public CautiousManeuver() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = BONUS_BLOCK;
    }


    public void applyPowers() {
        // Hack: We hijack baseDamage in order to display the correct description.
        // (The problem is we can't use the perfected strike approach and do this directly in abstractCard methods)

        //Reset baseBlock
        if (upgraded) {
            baseBlock = BLOCK + UPGRADE_PLUS_BLOCK;
        }
        else {
            baseBlock = BLOCK;
        }

        if (AbstractDungeon.player.isBloodied) {
            // Add bonus block.
            baseBlock += BONUS_BLOCK;

            super.applyPowers();

            if ( (upgraded && baseBlock != BLOCK) || (!upgraded && baseBlock != BLOCK + UPGRADE_PLUS_BLOCK)) {
                isBlockModified = true;
            }
            else {
                isBlockModified = false;
            }
            initializeDescription();
        }
        else {
            super.applyPowers();
        }
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
