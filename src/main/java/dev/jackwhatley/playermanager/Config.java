package dev.jackwhatley.playermanager;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.checkerframework.framework.qual.Unused;

import java.util.List;
import java.util.Vector;

@Mod.EventBusSubscriber(modid = PlayerManagerMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    
    private static final ForgeConfigSpec.BooleanValue IS_MOD_ENABLED = BUILDER
            .comment(" Enable or disable the mods functionality")
            .define("isEnabled", true);
    
    private static final ForgeConfigSpec.IntValue KICK_CHANCE = BUILDER
            .comment(" The percentage chance of the mod kicking a chosen user (0 - 100)%")
            .defineInRange("kickChance", 10, 0, 100);
    
    private static final ForgeConfigSpec.ConfigValue<String> KICK_MSG = BUILDER
            .comment(" The message to be displayed when disconnecting players, can be left blank")
            .define("kickMsg", "");
    
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> TARGET_NAMES = BUILDER
            .comment(" The usernames of the players to be kicked by this mod")
            .defineListAllowEmpty("playerNames", List.of(), Config::validatePlayerNames);
    
    private static final ForgeConfigSpec.BooleanValue LOG_ON_KICK = BUILDER
            .comment(" Enable or disable the mod logging if a player is kicked")
            .define("logOnKick", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean isModEnabled;
    public static boolean isLogEnabled;
    
    public static int kickChance;
    public static String kickMsg;
    public static List<String> targetNames;

    // may need future validation here, or the ability to put in player uuids
    private static boolean validatePlayerNames(final Object obj) { return true; }

    @SubscribeEvent
    @SuppressWarnings("unused")
    static void onConfigLoad(final ModConfigEvent event)
    {
        isModEnabled = IS_MOD_ENABLED.get();
        isLogEnabled = LOG_ON_KICK.get();
        
        kickChance = KICK_CHANCE.get();
        kickMsg = KICK_MSG.get();
        targetNames = new Vector<>(TARGET_NAMES.get());
    }
}
