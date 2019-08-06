package theTrickster;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomRelic;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theTrickster.cards.attacks.*;
import theTrickster.cards.powers.*;
import theTrickster.cards.skills.*;
import theTrickster.characters.TheTrickster;
import theTrickster.potions.PresciencePotion;
import theTrickster.relics.*;
import theTrickster.util.IDCheckDontTouchPls;
import theTrickster.util.RandomZeroCostCardHelper;
import theTrickster.util.TextureLoader;
import theTrickster.variables.DefaultCustomVariable;
import theTrickster.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

@SpireInitializer
public class TricksterMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        PostDrawSubscriber,
        PostPowerApplySubscriber {

    public static final Logger logger = LogManager.getLogger(TricksterMod.class.getName());
    private static String modID;

//    // Mod-settings settings. On/off savable button
    public static Properties theTricksterDefaultSettings = new Properties();
    public static final String GENERATE_CARDS_FROM_OTHER_MODS_SETTINGS = "generateCardsFromOtherMods";
    public static boolean generateCardsFromOtherMods = false; // The boolean we'll be setting on/off (true/false)

    //For the in-game mod settings panel.
    private static final String MODNAME = "The Trickster";
    private static final String AUTHOR = "Hyvel";
    private static final String DESCRIPTION = "Adds a new character: the Trickster.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_BROWN = CardHelper.getColor(64.0f, 70.0f, 10.0f);
    
    // Potion Colors in RGB
    public static final Color PRESCIENCE_POTION_LIQUID = CardHelper.getColor(150.0f, 75.0f, 40.0f); // Orange-ish Red
    public static final Color PRESCIENCE_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PRESCIENCE_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

    private static final String ATTACK_TRICKSTER_BROWN = "theTricksterResources/images/512/bg_attack_trickster_brown.png";
    private static final String SKILL_TRICKSTER_BROWN = "theTricksterResources/images/512/bg_skill_trickster_brown.png";
    private static final String POWER_TRICKSTER_BROWN = "theTricksterResources/images/512/bg_power_trickster_brown.png";
    
    private static final String ENERGY_ORB_TRICKSTER_BROWN = "theTricksterResources/images/512/card_trickster_brown_orb.png";
    private static final String CARD_ENERGY_ORB_BROWN = "theTricksterResources/images/512/card_small_orb_trickster.png";
    
    private static final String ATTACK_TRICKSTER_BROWN_PORTRAIT = "theTricksterResources/images/1024/bg_attack_trickster_brown.png";
    private static final String SKILL_TRICKSTER_BROWN_PORTRAIT = "theTricksterResources/images/1024/bg_skill_trickster_brown.png";
    private static final String POWER_TRICKSTER_BROWN_PORTRAIT = "theTricksterResources/images/1024/bg_power_trickster_brown.png";
    private static final String ENERGY_ORB_TRICKSTER_BROWN_PORTRAIT = "theTricksterResources/images/1024/card_trickster_brown_orb.png";
    
    private static final String THE_DEFAULT_BUTTON = "theTricksterResources/images/charSelect/TricksterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "theTricksterResources/images/charSelect/TricksterPortrait.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "theTricksterResources/images/char/tricksterCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "theTricksterResources/images/char/tricksterCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "theTricksterResources/images/char/tricksterCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theTricksterResources/images/Badge.png";


    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public TricksterMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);

        setModID("theTrickster");

        logger.info("Done subscribing");
        
        logger.info("Creating the color " + TheTrickster.Enums.COLOR_BROWN.toString());
        
        BaseMod.addColor(TheTrickster.Enums.COLOR_BROWN, DEFAULT_BROWN, DEFAULT_BROWN, DEFAULT_BROWN,
                DEFAULT_BROWN, DEFAULT_BROWN, DEFAULT_BROWN, DEFAULT_BROWN,
                ATTACK_TRICKSTER_BROWN, SKILL_TRICKSTER_BROWN, POWER_TRICKSTER_BROWN, ENERGY_ORB_TRICKSTER_BROWN,
                ATTACK_TRICKSTER_BROWN_PORTRAIT, SKILL_TRICKSTER_BROWN_PORTRAIT, POWER_TRICKSTER_BROWN_PORTRAIT,
                ENERGY_ORB_TRICKSTER_BROWN_PORTRAIT, CARD_ENERGY_ORB_BROWN);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The mod Button is added below in receivePostInitialize()
        theTricksterDefaultSettings.setProperty(GENERATE_CARDS_FROM_OTHER_MODS_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("tricksterMod", "theTricksterConfig", theTricksterDefaultSettings);
            config.load();
            generateCardsFromOtherMods = config.getBool(GENERATE_CARDS_FROM_OTHER_MODS_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = TricksterMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = TricksterMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = TricksterMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Trickster Mod. Hi. =========================");
        TricksterMod tricksterMod = new TricksterMod();
        logger.info("========================= /Trickster Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheTrickster.Enums.THE_TRICKSTER.toString());
        
        BaseMod.addCharacter(new TheTrickster("The Trickster", TheTrickster.Enums.THE_TRICKSTER),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheTrickster.Enums.THE_TRICKSTER);
        
        receiveEditPotions();
        logger.info("Added " + TheTrickster.Enums.THE_TRICKSTER.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton(
                "Allow 0 cost card generation to generate cards from other mods.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position, color, font
                generateCardsFromOtherMods, // Boolean used
                settingsPanel, // The mod panel in which this button will be
                (label) -> {},
                (button) -> { // button action:
                    generateCardsFromOtherMods = button.enabled; // Toggle our boolean setting.
                    try {
                        // Then set the settings and save them
                        SpireConfig config = new SpireConfig("tricksterMod", "theTricksterConfig", theTricksterDefaultSettings);
                        config.setBool(GENERATE_CARDS_FROM_OTHER_MODS_SETTINGS, generateCardsFromOtherMods);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        
        // No new events in this mod.
        
        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");


        // Adding a card pool. There might be a better way...
        RandomZeroCostCardHelper.initializeZeroCostCardPools();
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion.
        BaseMod.addPotion(PresciencePotion.class, PRESCIENCE_POTION_LIQUID, PRESCIENCE_POTION_HYBRID,
                PRESCIENCE_POTION_SPOTS, PresciencePotion.POTION_ID, TheTrickster.Enums.THE_TRICKSTER);

        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        
        ArrayList<Class> relicClasses = new ArrayList<>(Arrays.asList(
            OpticalIllusionRelic.class,
            StrangeDollRelic.class,
            BinocularsRelic.class,
            OcarinaRelic.class,
            BoneWhistleRelic.class,
            SnailBroochRelic.class,
            DesertRoseRelic.class,
            MeteoriteRelic.class,
            IllusoryBannerRelic.class,
            AncientPicklesRelic.class,
            RunicTorusRelic.class
        ));

        try {
            for(Class relicClass : relicClasses) {
                CustomRelic relic = (CustomRelic) relicClass.newInstance();
                // Add Character specific relic.
                BaseMod.addRelicToCustomPool(relic, TheTrickster.Enums.COLOR_BROWN);
                UnlockTracker.markRelicAsSeen(relic.relicId);
            }
        }
        catch (IllegalAccessException | InstantiationException e) {
            logger.error("Cannot add a relic: ", e);
        }


        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");

        ArrayList<Class> cardClasses = new ArrayList<>(Arrays.asList(
            EyeForEye.class,
            Impale.class,
            CapriciousThrust.class,
            KeepCool.class,
            BlurTheLines.class,
            SuperiorTactics.class,
            DejaVu.class,
            BattlePlan.class,
            BulletRain.class,
            Audacity.class,
            Volley.class,
            OminousSmile.class,
            LightTrick.class,
            DivingSlice.class,
            SharpBlade.class,
            ThickVest.class,
            Handicraft.class,
            Strike_Brown.class,
            Defend_Brown.class,
            DaringSlash.class,
            BackStep.class,
            SwiftSlash.class,
            IntrepidRush.class,
            Gunblade.class,
            Reposition.class,
            Safeguard.class,
            Gunblade.class,
            BetterSafe.class,
            EnhancingDrug.class,
            TriedTechniques.class,
            RampingUpStrike.class,
            DebilitatingShot.class,
            NickAndSkid.class,
            Feint.class,
            BladeSurge.class,
            TacticalStrike.class,
            RecklessShot.class,
            IntrepidRush.class,
            HandGrenade.class,
            RampingUpDefend.class,
            Distract.class,
            TacticalDefend.class,
            RampingUpDefend.class,
            KillThemAll.class,
            TrickyStrike.class,
            TrickyDefend.class,
            Repetition.class,
            HotSteel.class,
            ImpulsiveStrike.class,
            ImpulsiveDefend.class,
            SmokeOfConfusion.class,
            CautiousManeuver.class,
            Decoy.class,
            SmokeAndMirrors.class,
            PlayingWithFire.class,
            UpThePace.class,
            HiddenPistol.class,
            Practice.class,
            PistolWhip.class,
            Salvage.class,
            DeepPockets.class,
            Efficiency.class,
            FancyCape.class,
            Anticipation.class,
            MentalStrength.class,
            TickingPackage.class,
            Firecrackers.class,
            FirstAidKit.class,
            StopTheBleeding.class,
            SwiftAsTheWind.class,
            MistForm.class,
            Parry.class,
            StudiedSnipe.class,
            Frenzy.class,
            ForbiddenMedicine.class,
            CreateAnOpening.class
        ));

        try {
            for(Class cardClass : cardClasses) {
                CustomCard card = (CustomCard) cardClass.newInstance();
                BaseMod.addCard(card);
                // Unlock the cards so that they are all "seen" in the library, no grinding needed.
                UnlockTracker.unlockCard(card.cardID);
            }
        }
        catch (IllegalAccessException | InstantiationException e) {
            logger.error("Cannot add a card: ", e);
        }
        
        logger.info("Done adding cards!");
    }

    // ================ /ADD CARDS/ ===================

    // Thick Vest power card
    @Override
    public void receivePostDraw(AbstractCard card) {
        if(card.type != AbstractCard.CardType.STATUS) {
            return;
        }
        AbstractPlayer player = AbstractDungeon.player;
        if (!AbstractDungeon.player.hasPower("theTrickster:ThickVestPower")) {
            return;
        }
        AbstractPower power;
        Iterator var3 = player.powers.iterator();
        while(var3.hasNext()) {
            power = (AbstractPower) var3.next();
            if (power.ID.equals("theTrickster:ThickVestPower")) {
                power.flash();
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(power.owner, power.owner, power.amount));
            }
        }
    }

    //Strange Doll relic
    @Override
    public void receivePostPowerApplySubscriber(AbstractPower var1, AbstractCreature var2, AbstractCreature var3) {
        boolean hasStrangeDollRelic = AbstractDungeon.player.hasRelic("theTrickster:StrangeDollRelic");
        if (!hasStrangeDollRelic) {
            return;
        }
        if(var1.type == AbstractPower.PowerType.DEBUFF && var2 instanceof AbstractPlayer) {
            AbstractDungeon.player.getRelic("theTrickster:StrangeDollRelic").flash();
            AbstractDungeon.actionManager.addToBottom(
                    new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, 3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }

    }

    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/TricksterMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/TricksterMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/TricksterMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/TricksterMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/TricksterMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/TricksterMod-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/TricksterMod-Orb-Strings.json");
        
        logger.info("Done edittting strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/TricksterMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
