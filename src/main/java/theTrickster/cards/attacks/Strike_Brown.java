package theTrickster.cards.attacks;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theTrickster.DefaultMod;
import theTrickster.cards.AbstractDynamicCard;
import theTrickster.characters.TheDefault;
import theTrickster.characters.TheTrickster;

import static theTrickster.DefaultMod.makeCardPath;

public class Strike_Brown extends AbstractDynamicCard {

        // TEXT DECLARATION

        public static final String ID = DefaultMod.makeID(Strike_Brown.class.getSimpleName());
        public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("Strike_Brown.png");
        // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


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
            //Tagging the strike
            this.tags.add(BaseModCardTags.BASIC_STRIKE);
            this.tags.add(CardTags.STRIKE);
        }


        // Actions the card should do.
        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }


        // Upgraded stats.
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
