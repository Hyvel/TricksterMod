package theTrickster.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;
import theTrickster.util.TheTricksterTags;

import static theTrickster.TricksterMod.makeCardPath;

public class TacticalDefend extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(TacticalDefend.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("TacticalDefend.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 1;
    private static final int BLOCK = 12;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    private static final int STR = -1;

    // /STAT DECLARATION/


    public TacticalDefend() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        this.tags.add(TheTricksterTags.DEFEND);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new StrengthPower(p, STR), STR));
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
