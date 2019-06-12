package theTrickster;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Blur;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
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
import theTrickster.events.IdentityCrisisEvent;
import theTrickster.potions.PlaceholderPotion;
import theTrickster.potions.PresciencePotion;
import theTrickster.relics.BottledPlaceholderRelic;
import theTrickster.relics.DefaultClickableRelic;
import theTrickster.relics.OpticalIllusionRelic;
import theTrickster.relics.PlaceholderRelic;
import theTrickster.util.IDCheckDontTouchPls;
import theTrickster.util.RandomZeroCostCardHelper;
import theTrickster.util.TextureLoader;
import theTrickster.variables.DefaultCustomVariable;
import theTrickster.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Properties;

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "theTrickster:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "theTrickster". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class DefaultMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        PostDrawSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "The Trickster";
    private static final String AUTHOR = "Hyvel"; // And pretty soon - You!
    private static final String DESCRIPTION = "A base for Slay the Spire to start your own mod from, feat. the Default.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);
    public static final Color DEFAULT_BROWN = CardHelper.getColor(64.0f, 70.0f, 10.0f);
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
    

    private static final String ATTACK_TRICKSTER_BROWN = "theTricksterResources/images/512/bg_attack_trickster_brown.png";
    private static final String SKILL_TRICKSTER_BROWN = "theTricksterResources/images/512/bg_skill_trickster_brown.png";
    private static final String POWER_TRICKSTER_BROWN = "theTricksterResources/images/512/bg_power_trickster_brown.png";
    
    private static final String ENERGY_ORB_TRICKSTER_BROWN = "theTricksterResources/images/512/card_trickster_brown_orb.png";
    private static final String CARD_ENERGY_ORB_BROWN = "theTricksterResources/images/512/card_small_orb_trickster.png";
    
    private static final String ATTACK_TRICKSTER_BROWN_PORTRAIT = "theTricksterResources/images/1024/bg_attack_trickster_brown.png";
    private static final String SKILL_TRICKSTER_BROWN_PORTRAIT = "theTricksterResources/images/1024/bg_skill_trickster_brown.png";
    private static final String POWER_TRICKSTER_BROWN_PORTRAIT = "theTricksterResources/images/1024/bg_power_trickster_brown.png";
    private static final String ENERGY_ORB_TRICKSTER_BROWN_PORTRAIT = "theTricksterResources/images/1024/card_trickster_brown_orb.png";
    
    // Character assets //TODO
    private static final String THE_DEFAULT_BUTTON = "theTricksterResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "theTricksterResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "theTricksterResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "theTricksterResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "theTricksterResources/images/char/defaultCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod. //TODO
    public static final String BADGE_IMAGE = "theTricksterResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations //TODO
    public static final String THE_DEFAULT_SKELETON_ATLAS = "theTricksterResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "theTricksterResources/images/char/defaultCharacter/skeleton.json";
    
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
    
    public DefaultMod() {
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
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
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
        InputStream in = DefaultMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
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
        InputStream in = DefaultMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = DefaultMod.class.getPackage().getName(); // STILL NO EDIT ZONE
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
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        DefaultMod defaultmod = new DefaultMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheTrickster.Enums.THE_TRICKSTER.toString());
        
        BaseMod.addCharacter(new TheTrickster("The Trickster", TheTrickster.Enums.THE_TRICKSTER),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheTrickster.Enums.THE_TRICKSTER);

//        BaseMod.addCharacter(new TheDefault("the Default", TheDefault.Enums.THE_DEFAULT),
//                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheDefault.Enums.THE_DEFAULT);
        
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
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        
        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        //BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        
        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");


        // Adding a card pool. There might be a better way...
        RandomZeroCostCardHelper.initializeZeroCostCardPools();
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
//        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheTrickster.Enums.THE_TRICKSTER);

        BaseMod.addPotion(PresciencePotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PresciencePotion.POTION_ID, TheTrickster.Enums.THE_TRICKSTER);

        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        
        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
//        BaseMod.addRelicToCustomPool(new PlaceholderRelic(), TheTrickster.Enums.COLOR_BROWN);
//        BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), TheTrickster.Enums.COLOR_BROWN);
//        BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), TheTrickster.Enums.COLOR_BROWN);

        BaseMod.addRelicToCustomPool(new OpticalIllusionRelic(), TheTrickster.Enums.COLOR_BROWN);


        // This adds a relic to the Shared pool. Every character can find this relic.
        //BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);
        
        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
//        UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
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
        logger.info("Add variabls");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.
        
//        BaseMod.addCard(new OrbSkill());
//        BaseMod.addCard(new DefaultSecondMagicNumberSkill());
//        BaseMod.addCard(new DefaultCommonAttack());
//        BaseMod.addCard(new DefaultAttackWithVariable());
//        BaseMod.addCard(new DefaultCommonSkill());
//        BaseMod.addCard(new DefaultCommonPower());
//        BaseMod.addCard(new DefaultUncommonSkill());
//        BaseMod.addCard(new DefaultUncommonAttack());
//        BaseMod.addCard(new DefaultUncommonPower());
//        BaseMod.addCard(new DefaultRareAttack());
//        BaseMod.addCard(new DefaultRareSkill());
//        BaseMod.addCard(new DefaultRarePower());

        BaseMod.addCard(new Strike_Brown());
        BaseMod.addCard(new Defend_Brown());
        BaseMod.addCard(new DaringSlash());
        BaseMod.addCard(new BackStep());
        BaseMod.addCard(new SwiftSlash());
        BaseMod.addCard(new IntrepidRush());
        BaseMod.addCard(new Gunblade());
        BaseMod.addCard(new Reposition());
        BaseMod.addCard(new Safeguard());
        BaseMod.addCard(new Gunblade());

        BaseMod.addCard(new HeightenedObservation());
        BaseMod.addCard(new EnhancingDrug());
        BaseMod.addCard(new TriedTechniques());
        BaseMod.addCard(new RampingUpStrike());
        BaseMod.addCard(new DebilitatingShot());
        BaseMod.addCard(new NickAndSkid());
        BaseMod.addCard(new Feint());
        BaseMod.addCard(new BladeSurge());
        BaseMod.addCard(new TacticalStrike());
        BaseMod.addCard(new RecklessShot());

        BaseMod.addCard(new IntrepidRush());
        BaseMod.addCard(new HandGrenade());
        BaseMod.addCard(new RampingUpDefend());
        BaseMod.addCard(new Distract());
        BaseMod.addCard(new TacticalDefend());
        BaseMod.addCard(new RampingUpDefend());
        BaseMod.addCard(new HeightenedAggression());
        BaseMod.addCard(new TrickyStrike());
        BaseMod.addCard(new TrickyDefend());
        BaseMod.addCard(new Repetition());

        BaseMod.addCard(new HotSteel());
        BaseMod.addCard(new ImpulsiveStrike());
        BaseMod.addCard(new ImpulsiveDefend());
        BaseMod.addCard(new SmokeOfConfusion());
        BaseMod.addCard(new CautiousManeuver());
        BaseMod.addCard(new Decoy());
        BaseMod.addCard(new SmokeAndMirrors());
        BaseMod.addCard(new SkinInTheGame());
        BaseMod.addCard(new UpThePace());
        BaseMod.addCard(new HiddenPistol());

        BaseMod.addCard(new Practice());
        BaseMod.addCard(new PistolWhip());
        BaseMod.addCard(new AssertiveLunge());
        BaseMod.addCard(new Salvage());
        BaseMod.addCard(new DeepPockets());
        BaseMod.addCard(new Efficiency());
        BaseMod.addCard(new FancyCape());
        BaseMod.addCard(new Anticipation());
        BaseMod.addCard(new MentalStrength());
        BaseMod.addCard(new TickingPackage());

        BaseMod.addCard(new Firecrackers());
        BaseMod.addCard(new FirstAidKit());
        BaseMod.addCard(new StopTheBleeding());
        BaseMod.addCard(new SwiftAsTheWind());
        BaseMod.addCard(new MistForm());
        BaseMod.addCard(new Parry());
        BaseMod.addCard(new StudiedSnipe());
        BaseMod.addCard(new Frenzy());
        BaseMod.addCard(new ForbiddenMedicine());
        BaseMod.addCard(new CreateAnOpening());

        BaseMod.addCard(new BulletRain());
//        BaseMod.addCard(new PreciseSalvo());
        BaseMod.addCard(new Audacity());
        BaseMod.addCard(new Volley());
        BaseMod.addCard(new OminousSmile());
        BaseMod.addCard(new LightTrick());
        BaseMod.addCard(new DivingSlice());
        BaseMod.addCard(new SharpBlade());
        BaseMod.addCard(new ThickVest());
        BaseMod.addCard(new Handicraft());

        BaseMod.addCard(new BattlePlan());
        BaseMod.addCard(new DejaVu());
        BaseMod.addCard(new SuperiorTactics());
        BaseMod.addCard(new BlurTheLines());
        BaseMod.addCard(new KeepCool());
        BaseMod.addCard(new CapriciousThrust());
        BaseMod.addCard(new EyeForEye());


        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.
//        UnlockTracker.unlockCard(OrbSkill.ID);
//        UnlockTracker.unlockCard(DefaultSecondMagicNumberSkill.ID);
//        UnlockTracker.unlockCard(DefaultCommonAttack.ID);
//        UnlockTracker.unlockCard(DefaultAttackWithVariable.ID);
//        UnlockTracker.unlockCard(DefaultCommonSkill.ID);
//        UnlockTracker.unlockCard(DefaultCommonPower.ID);
//        UnlockTracker.unlockCard(DefaultUncommonSkill.ID);
//        UnlockTracker.unlockCard(DefaultUncommonAttack.ID);
//        UnlockTracker.unlockCard(DefaultUncommonPower.ID);
//        UnlockTracker.unlockCard(DefaultRareAttack.ID);
//        UnlockTracker.unlockCard(DefaultRareSkill.ID);
//        UnlockTracker.unlockCard(DefaultRarePower.ID);
        
        logger.info("Done adding cards!");
    }
    
    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.
    
    // ================ /ADD CARDS/ ===================

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

    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/DefaultMod-Orb-Strings.json");
        
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
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/DefaultMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
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
