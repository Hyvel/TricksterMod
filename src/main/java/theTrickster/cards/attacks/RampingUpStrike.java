package theTrickster.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;

import static theTrickster.TricksterMod.makeCardPath;

public class RampingUpStrike extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(RampingUpStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("attacks/RampingUpStrike.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int DAMAGE = 1;
    private static final int DAMAGE_PER_ELAPSED_TURN = 2;

    // /STAT DECLARATION/


    public RampingUpStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        tags.add(CardTags.STRIKE);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = DAMAGE_PER_ELAPSED_TURN;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public void applyPowers() {
        baseDamage = DAMAGE + (magicNumber * (GameActionManager.turn - 1));
        super.applyPowers();

        //reset the base damage (necessary as when isDamageModified is false, the damage is set to baseDamage)
        baseDamage = DAMAGE;
        isDamageModified = damage != baseDamage;
    }

    public void calculateCardDamage(AbstractMonster m) {
        baseDamage = DAMAGE + (magicNumber * (GameActionManager.turn - 1));
        super.calculateCardDamage(m);

        //reset the base damage (necessary as when isDamageModified is false, the damage is set to baseDamage)
        baseDamage = DAMAGE;
        isDamageModified = damage != baseDamage;
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
