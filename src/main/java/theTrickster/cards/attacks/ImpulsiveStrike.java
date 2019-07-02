package theTrickster.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;

import static theTrickster.TricksterMod.makeCardPath;

public class ImpulsiveStrike extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(ImpulsiveStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("ImpulsiveStrike.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 0;

    private static final int DAMAGE = 12;

    private static final int MINUS_DAMAGE_PER_TURN = 2;

    // /STAT DECLARATION/


    public ImpulsiveStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CardTags.STRIKE);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MINUS_DAMAGE_PER_TURN;
        exhaust = true;
        this.tags.add(CardTags.STRIKE);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }


    public void applyPowers() {
        baseDamage = DAMAGE - ((GameActionManager.turn - 1) * magicNumber );

        super.applyPowers();

        isDamageModified = baseDamage != DAMAGE;
        initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster m) {
        baseDamage = DAMAGE - ((GameActionManager.turn - 1) * magicNumber );

        super.calculateCardDamage(m);

        isDamageModified = damage != DAMAGE;
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isInnate = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
