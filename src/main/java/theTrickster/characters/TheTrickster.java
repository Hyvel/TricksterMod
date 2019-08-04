package theTrickster.characters;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theTrickster.TricksterMod;
import theTrickster.cards.attacks.*;
import theTrickster.cards.skills.*;
import theTrickster.relics.OpticalIllusionRelic;

import java.util.ArrayList;

import static theTrickster.TricksterMod.*;
import static theTrickster.characters.TheTrickster.Enums.COLOR_BROWN;


public class TheTrickster extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(TricksterMod.class.getName());

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_TRICKSTER;
        @SpireEnum(name = "DEFAULT_BROWN_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_BROWN;
        @SpireEnum(name = "DEFAULT_BROWN_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("TricksterCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "theTricksterResources/images/char/tricksterCharacter/orb/1.png",
            "theTricksterResources/images/char/tricksterCharacter/orb/2.png",
            "theTricksterResources/images/char/tricksterCharacter/orb/3.png",
            "theTricksterResources/images/char/tricksterCharacter/orb/4.png",
            "theTricksterResources/images/char/tricksterCharacter/orb/5.png",
            "theTricksterResources/images/char/tricksterCharacter/orb/border.png",
            "theTricksterResources/images/char/tricksterCharacter/orb/1d.png",
            "theTricksterResources/images/char/tricksterCharacter/orb/2d.png",
            "theTricksterResources/images/char/tricksterCharacter/orb/3d.png",
            "theTricksterResources/images/char/tricksterCharacter/orb/4d.png",
            "theTricksterResources/images/char/tricksterCharacter/orb/5d.png",};


    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================

    public TheTrickster(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "theTricksterResources/images/char/tricksterCharacter/orb/vfx.png", null,
                new SpriterAnimation(
                        "theTricksterResources/images/char/tricksterCharacter/Spriter/theTrickster.scml"));


        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(null, // required call to load textures and setup energy/loadout.
                THE_DEFAULT_SHOULDER_1, // campfire pose
                THE_DEFAULT_SHOULDER_2, // another campfire pose
                THE_DEFAULT_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale);

        // =============== /TEXT BUBBLE LOCATION/ =================

        //Set Ascension level to 20 to avoid grinding
        CharStat charStat = this.getCharStat();
        Prefs statPref = (Prefs) ReflectionHacks.getPrivate(charStat, CharStat.class, "pref");
        if (statPref.getInteger("ASCENSION_LEVEL", 1) != 20) {
            statPref.putInteger("ASCENSION_LEVEL", 20);
            statPref.putInteger("LAST_ASCENSION_LEVEL", 0);
            statPref.flush();
            logger.info("Set max ascension level to 20 and last ascension to 0. This should be the first time the mod is launched.");
        }

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        retVal.add(Strike_Brown.ID);
        retVal.add(Strike_Brown.ID);
        retVal.add(Strike_Brown.ID);
        retVal.add(Strike_Brown.ID);
        retVal.add(Strike_Brown.ID);
        retVal.add(Defend_Brown.ID);
        retVal.add(Defend_Brown.ID);
        retVal.add(Defend_Brown.ID);
        retVal.add(Defend_Brown.ID);
        retVal.add(Defend_Brown.ID);
        retVal.add(DaringSlash.ID);
        retVal.add(BackStep.ID);

        return retVal;
    }

    // Starting Relics
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(OpticalIllusionRelic.ID);
        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // HP reduction when starting a run at Ascension 14 or higher.
    // (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_BROWN;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return TricksterMod.DEFAULT_BROWN;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
//        return new DefaultCommonAttack();
        return new BackStep();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheTrickster(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return TricksterMod.DEFAULT_BROWN;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return TricksterMod.DEFAULT_BROWN;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Text shown when the character is about to attack the heart.
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

}
