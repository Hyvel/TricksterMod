package theTrickster.cards.attacks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;

import java.lang.reflect.Field;

import static theTrickster.TricksterMod.makeCardPath;

public class EyeForEye extends AbstractDynamicCard {

    public static final Logger logger = LogManager.getLogger(EyeForEye.class.getName());

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(EyeForEye.class.getSimpleName());
    public static final String IMG = makeCardPath("attacks/EyeForEye.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    // /STAT DECLARATION/


    public EyeForEye() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damageTypeForTurn = damageType = DamageInfo.DamageType.THORNS;
        baseDamage = 0;
    }

    public void update() {
        super.update();
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage (AbstractMonster mo) {
//        super.calculateCardDamage(mo);

        if(mo == null) {
            this.damage = 0;
            this.rawDescription = DESCRIPTION;
            this.initializeDescription();
            return;
        }

        if(mo.intent != AbstractMonster.Intent.ATTACK &&
                mo.intent != AbstractMonster.Intent.ATTACK_BUFF &&
                mo.intent != AbstractMonster.Intent.ATTACK_DEBUFF &&
                mo.intent != AbstractMonster.Intent.ATTACK_DEFEND) {
            this.damage = 0;
            this.rawDescription = EXTENDED_DESCRIPTION[0] + 0 + EXTENDED_DESCRIPTION[1] + DESCRIPTION;
            this.initializeDescription();
            return;
        }

        int intentDmg = 0;
        boolean isMultiDmg = false;
        int intentMultiAmt = 0;

        try {
            Field intentDmgField = AbstractMonster.class.getDeclaredField("intentDmg");
            intentDmgField.setAccessible(true);
            intentDmg = (int) intentDmgField.get(mo);

            Field isMultiDmgField = AbstractMonster.class.getDeclaredField("isMultiDmg");
            isMultiDmgField.setAccessible(true);
            isMultiDmg = (boolean) isMultiDmgField.get(mo);

            Field intentMultiAmtField = AbstractMonster.class.getDeclaredField("intentMultiAmt");
            intentMultiAmtField.setAccessible(true);
            intentMultiAmt = (int) intentMultiAmtField.get(mo);
        }
        catch (Exception e) {
            logger.error("Error with Eye For Eye card", e);
        }

        if(isMultiDmg) {
            this.damage = intentDmg;
            this.rawDescription = EXTENDED_DESCRIPTION[0] + intentDmg + EXTENDED_DESCRIPTION[2] +
                    intentMultiAmt + EXTENDED_DESCRIPTION[3] + DESCRIPTION;
            this.initializeDescription();
        }
        else {
            this.damage = intentDmg;
            this.rawDescription = EXTENDED_DESCRIPTION[0] + intentDmg + EXTENDED_DESCRIPTION[1] + DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if(m == null || (m.intent != AbstractMonster.Intent.ATTACK &&
                m.intent != AbstractMonster.Intent.ATTACK_BUFF &&
                m.intent != AbstractMonster.Intent.ATTACK_DEBUFF &&
                m.intent != AbstractMonster.Intent.ATTACK_DEFEND)) {
            return;
        }

        int intentDmg = 0;
        boolean isMultiDmg = false;
        int intentMultiAmt = 0;

        try {
            Field intentDmgField = AbstractMonster.class.getDeclaredField("intentDmg");
            intentDmgField.setAccessible(true);
            intentDmg = (int) intentDmgField.get(m);

            Field isMultiDmgField = AbstractMonster.class.getDeclaredField("isMultiDmg");
            isMultiDmgField.setAccessible(true);
            isMultiDmg = (boolean) isMultiDmgField.get(m);

            Field intentMultiAmtField = AbstractMonster.class.getDeclaredField("intentMultiAmt");
            intentMultiAmtField.setAccessible(true);
            intentMultiAmt = (int) intentMultiAmtField.get(m);
        }
        catch (Exception e) {
            logger.error("Error with Eye For Eye card", e);
        }

        if(!isMultiDmg) {
            intentMultiAmt = 1;
        }

        for(int i=0; i < intentMultiAmt; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, intentDmg, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }

    }


    @Override
    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
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
