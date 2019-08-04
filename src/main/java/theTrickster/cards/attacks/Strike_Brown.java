package theTrickster.cards.attacks;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.TricksterMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheTrickster;

import static theTrickster.TricksterMod.makeCardPath;

public class Strike_Brown extends AbstractDynamicCard {

        // TEXT DECLARATION

        public static final String ID = TricksterMod.makeID(Strike_Brown.class.getSimpleName());
        public static final String IMG = makeCardPath("attacks/Strike_Brown.png");

        // /TEXT DECLARATION/


        // STAT DECLARATION

        private static final CardRarity RARITY = CardRarity.BASIC;
        private static final CardTarget TARGET = CardTarget.ENEMY;
        private static final CardType TYPE = CardType.ATTACK;
        public static final CardColor COLOR = TheTrickster.Enums.COLOR_BROWN;

        private static final int COST = 1;
        private static final int UPGRADED_COST = 1;

        private static final int DAMAGE = 6;
        private static final int UPGRADE_PLUS_DMG = 3;

        // /STAT DECLARATION/


        public Strike_Brown() {
            super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
            baseDamage = DAMAGE;
            tags.add(BaseModCardTags.BASIC_STRIKE);
            tags.add(CardTags.STRIKE);
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
                upgradeDamage(UPGRADE_PLUS_DMG);
                upgradeBaseCost(UPGRADED_COST);
                initializeDescription();
            }
        }
}
