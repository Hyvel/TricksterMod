package theTrickster.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;
import theTrickster.util.TheTricksterTags;

import java.util.Iterator;

import static theTrickster.TricksterMod.makeCardPath;

public class TrickyStrike extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(TrickyStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("attacks/TrickyStrike.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int DAMAGE = 2;

    private static final int DMG_BY_DEFEND = 1;
    private static final int UPGRADE_PLUS_DMG_BY_DEFEND = 1;

    // /STAT DECLARATION/


    public TrickyStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = DMG_BY_DEFEND;
        this.tags.add(CardTags.STRIKE);
    }

    private static int countDefendCards() {
        int count = 0;
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (isDefend(c)) {
                ++count;
            }
        }

        var1 = AbstractDungeon.player.drawPile.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (isDefend(c)) {
                ++count;
            }
        }

        var1 = AbstractDungeon.player.discardPile.group.iterator();

        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (isDefend(c)) {
                ++count;
            }
        }

        return count;
    }

    private static boolean isDefend(AbstractCard c) {
        return c.hasTag(TheTricksterTags.DEFEND);
    }

    public void applyPowers() {
        baseDamage = DAMAGE + (magicNumber * countDefendCards());

        super.applyPowers();

        isDamageModified = damage != DAMAGE;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster m) {
        baseDamage = DAMAGE + (magicNumber * countDefendCards());

        super.calculateCardDamage(m);

        isDamageModified = damage != DAMAGE;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DMG_BY_DEFEND);
            initializeDescription();
        }
    }
}
