package dev.jackwhatley.playermanager;

import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PlayerManagerMod.MODID)
public class PlayerManagerMod
{
    public static final String MODID = "jwserverplayermanager";

    public PlayerManagerMod()
    {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onEntityJoin(EntityJoinLevelEvent event)
    {
        if (event.getLevel().isClientSide() || !Config.isModEnabled) return;

        Entity joinedEntity = event.getEntity();
        
        if (joinedEntity instanceof Player joinedPlayer) {
            String playerName = joinedPlayer.getDisplayName().getString();
            Utils.LogIfAllowed(String.format("Detected player %s joined", playerName));
            
            if (joinedPlayer instanceof ServerPlayer serverPlayer && Utils.CheckChance()) {
                if (!Config.targetNames.contains(playerName)) return;

                Utils.LogIfAllowed(String.format("Disconnected player %s", playerName));
                serverPlayer.connection.disconnect(Component.literal(Config.kickMsg));
            }
        }
    }
}
