package theTrickster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Iterator;

public class BlurTheLinesAction extends AbstractGameAction {
    private AbstractPlayer player;

    public BlurTheLinesAction(AbstractPlayer p) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.player = p;
    }

    public void update() {
        int dex = 0;
        int str = 0;
        AbstractPower power;
        Iterator var3 = player.powers.iterator();
        while(var3.hasNext()) {
            power = (AbstractPower) var3.next();
            if (power.ID.equals("Strength")) {
                str = power.amount;
            }
            if (power.ID.equals("Dexterity")) {
                dex = power.amount;
            }
        }

        // We want action type WAIT and not DEBUFF to not take artifact into account.
        AbstractGameAction rmvStr = new RemoveSpecificPowerAction(player, player, "Strength");
        rmvStr.actionType = ActionType.WAIT;
        AbstractGameAction rmvDex = new RemoveSpecificPowerAction(player, player, "Dexterity");
        rmvDex.actionType = ActionType.WAIT;
        AbstractDungeon.actionManager.addToTop(rmvStr);
        AbstractDungeon.actionManager.addToTop(rmvDex);

        // Adding str or dex of 0 is visually ugly (and useless).
        if(dex != 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(player, player, new StrengthPower(player, dex), dex));
        }
        if(str != 0 ) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(player, player, new DexterityPower(player, str), str));
        }

        this.isDone = true;
    }
}
