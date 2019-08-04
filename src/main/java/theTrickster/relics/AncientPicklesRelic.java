package theTrickster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makeRelicOutlinePath;
import static theTrickster.TricksterMod.makeRelicPath;

public class AncientPicklesRelic extends CustomRelic {

    /*
     * Boss relic.
     * Gain 1 energy at the start of each turn. Start each combat with 2 Weak and 2 Frail.
     */

    public static final String ID = TricksterMod.makeID("AncientPicklesRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ancient_pickles.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ancient_pickles.png"));

    public AncientPicklesRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 2, false), 2));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, 2, false), 2));
    }

    public String getUpdatedDescription() {
        return AbstractDungeon.player != null ? this.setDescription(AbstractDungeon.player.chosenClass) : this.setDescription((AbstractPlayer.PlayerClass)null);
    }

    private String setDescription(AbstractPlayer.PlayerClass c) {
        return this.DESCRIPTIONS[0] + this.DESCRIPTIONS[1];
    }

    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = this.setDescription(c);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

}
