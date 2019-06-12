package theTrickster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class AnticipationAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private int energyGain;
    private int draw;
    private AbstractMonster targetMonster;
    private AbstractPlayer player;

    public AnticipationAction(AbstractMonster m, AbstractPlayer p, int energyGain, int draw) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.energyGain = energyGain;
        this.draw = draw;
        this.targetMonster = m;
        this.player = p;
    }

    public void update() {
        if (this.targetMonster == null || this.targetMonster.intent != AbstractMonster.Intent.ATTACK && this.targetMonster.intent != AbstractMonster.Intent.ATTACK_BUFF && this.targetMonster.intent != AbstractMonster.Intent.ATTACK_DEBUFF && this.targetMonster.intent != AbstractMonster.Intent.ATTACK_DEFEND) {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        } else {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.energyGain));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.player, this.draw));
        }

        this.isDone = true;
    }

    static {
        // We keep the Spot Weakness card UI Text.
        uiStrings = CardCrawlGame.languagePack.getUIString("OpeningAction");
        TEXT = uiStrings.TEXT;
    }
}
