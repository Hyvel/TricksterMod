package theTrickster.cards.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;

import java.util.Iterator;

import static theTrickster.TricksterMod.makeCardPath;

public class SmokeOfConfusion extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(SmokeOfConfusion.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("SmokeOfConfusion.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int WEAK = 1;
    private static final int VULNERABLE = 1;

    // /STAT DECLARATION/


    public SmokeOfConfusion() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var3.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var3.next();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                    new WeakPower(mo, WEAK, false), WEAK,
                    true, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                    new VulnerablePower(mo, VULNERABLE, false), VULNERABLE,
                    true, AbstractGameAction.AttackEffect.NONE));
        }

    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
