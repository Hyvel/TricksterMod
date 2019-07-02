package theTrickster.cards.attacks;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theTrickster.TricksterMod;
import theTrickster.actions.BladeSurgeAction;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;

import static theTrickster.TricksterMod.makeCardPath;

public class BladeSurge extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(BladeSurge.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("BladeSurge.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = -1;

    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 2;

    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 2;


    // /STAT DECLARATION/


    public BladeSurge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }
        AbstractDungeon.actionManager.addToBottom(
                new BladeSurgeAction(p, m, this.damage, this.block, this.damageTypeForTurn,
                        this.freeToPlayOnce, this.energyOnUse));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
