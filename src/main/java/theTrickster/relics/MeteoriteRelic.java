package theTrickster.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theTrickster.TricksterMod;
import theTrickster.util.TextureLoader;

import static theTrickster.TricksterMod.makeRelicOutlinePath;
import static theTrickster.TricksterMod.makeRelicPath;

public class MeteoriteRelic extends CustomRelic {

    /*
     * Shop relic.
     * At the start of your turn, gain 2 Strength and lose 1 Dexterity.
     */

    public static final String ID = TricksterMod.makeID("MeteoriteRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("meteorite.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("meteorite.png"));

    public MeteoriteRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.HEAVY);
    }

    public void atTurnStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, -1), -1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }}

