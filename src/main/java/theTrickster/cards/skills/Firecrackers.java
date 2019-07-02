package theTrickster.cards.skills;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;
import theTrickster.powers.DelayedDamagePower;

import java.util.Iterator;

import static theTrickster.TricksterMod.makeCardPath;

public class Firecrackers extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = TricksterMod.makeID(Firecrackers.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("Firecrackers.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

    private static final int COST = 1;

    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DAMAGE = 4;

    // /STAT DECLARATION/


    public Firecrackers() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = DAMAGE;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var3.hasNext()) {
            AbstractMonster mo = (AbstractMonster)var3.next();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                    new DelayedDamagePower(mo, p, this.magicNumber), this.magicNumber));
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}
