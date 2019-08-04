package theTrickster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makeRelicOutlinePath;
import static theTrickster.TricksterMod.makeRelicPath;

public class BinocularsRelic extends CustomRelic {

    /*
     * Uncommon relic.
     * Gain 3 Dexterity on the first turn of combat
     */

    public static final String ID = TricksterMod.makeID("BinocularsRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("binoculars.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("binoculars.png"));

    public BinocularsRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 3), 3));
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseDexterityPower(AbstractDungeon.player, 3), 3));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }}

