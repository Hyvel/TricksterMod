package theTrickster.cards.skills;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.status.Wound;
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

public class OminousSmile extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(OminousSmile.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("OminousSmile.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int DEBUFF = 2;
    private static final int UPGRADE_PLUS_DEBUFF = 1;

    // /STAT DECLARATION/


    public OminousSmile() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = DEBUFF;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInHandAction(new Wound(), 1));

        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var3.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var3.next();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                    new WeakPower(mo, magicNumber, false), magicNumber,
                    true, AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                    new VulnerablePower(mo, magicNumber, false), magicNumber,
                    true, AbstractGameAction.AttackEffect.NONE));
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DEBUFF);
            initializeDescription();
        }
    }
}
