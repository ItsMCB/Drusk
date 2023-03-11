package me.itsmcb.drusk;

import libs.dev.dejvokep.boostedyaml.serialization.YamlSerializer;
import libs.dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import me.itsmcb.drusk.features.drusk.DruskCMDFeature;
import me.itsmcb.drusk.features.flyspeed.FlySpeedFeat;
import me.itsmcb.drusk.features.gamemode.GamemodeFeat;
import me.itsmcb.drusk.features.head.HeadFeat;
import me.itsmcb.drusk.features.hooked.bungeecord.connect.ConnectFeature;
import me.itsmcb.drusk.features.Tab.TabFeat;
import me.itsmcb.drusk.features.status.StatusFeature;
import me.itsmcb.drusk.features.inventory.InventoryFeat;
import me.itsmcb.drusk.features.tools.ToolsFeature;
import me.itsmcb.drusk.features.skin.SkinFeature;
import me.itsmcb.drusk.features.spawn.SpawnFeature;
import me.itsmcb.drusk.features.specialevents.SpecialEventsFeature;
import me.itsmcb.drusk.features.teleport.TeleportFeat;
import me.itsmcb.drusk.features.weext.WorldEditExtensionsFeat;
import me.itsmcb.vexelcore.bukkit.api.managers.BukkitFeatureManager;
import me.itsmcb.vexelcore.bukkit.api.managers.LocalizationManager;
import me.itsmcb.vexelcore.bukkit.api.managers.PermissionManager;
import me.itsmcb.vexelcore.common.api.config.BoostedConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class Drusk extends JavaPlugin {

    private Drusk instance;
    private BukkitFeatureManager bukkitFeatureManager;
    private LocalizationManager localizationManager;
    private PermissionManager permissionManager;

    private BoostedConfig mainConfig;

    public BukkitFeatureManager getBukkitFeatureManager() {
        return bukkitFeatureManager;
    }
    public LocalizationManager getLocalizationManager() {
        return localizationManager;
    }
    public PermissionManager getPermissionManager() { return permissionManager; }

    public BoostedConfig getMainConfig() {
        return mainConfig;
    }

    @Override
    public void onEnable() {
        this.instance = this;
        // Config
        YamlSerializer mainConfigSerializer = new SpigotSerializer();
        mainConfig = new BoostedConfig(getDataFolder(),"config", getResource("config.yml"), mainConfigSerializer);

        // Set permissions
        this.permissionManager = new PermissionManager();
        permissionManager.set("admin","drusk.admin");

        // Load configurations and options
        // todo hook into future localization plugin to get default server language
        this.localizationManager = new LocalizationManager(this, "en_US");
        localizationManager.register("en_US");

        // Register features
        this.bukkitFeatureManager = new BukkitFeatureManager();
        bukkitFeatureManager.register(new DruskCMDFeature(instance));
        //bukkitFeatureManager.register(new DoubleJumpFeature(instance));
        bukkitFeatureManager.register(new ConnectFeature(instance));
        //bukkitFeatureManager.register(new PlaceFlag(instance));
        bukkitFeatureManager.register(new ToolsFeature(instance));
        bukkitFeatureManager.register(new SpecialEventsFeature(instance));
        bukkitFeatureManager.register(new SpawnFeature(instance));
        bukkitFeatureManager.register(new StatusFeature(instance));
        bukkitFeatureManager.register(new SkinFeature(instance));
        bukkitFeatureManager.register(new InventoryFeat(instance));
        bukkitFeatureManager.register(new FlySpeedFeat(instance));
        bukkitFeatureManager.register(new WorldEditExtensionsFeat(instance));
        bukkitFeatureManager.register(new HeadFeat(instance));
        bukkitFeatureManager.register(new TeleportFeat(instance));
        bukkitFeatureManager.register(new TabFeat(instance));
        //bukkitFeatureManager.register(new KaboomFeat(instance));
        // Game mode stuff
        bukkitFeatureManager.register(new GamemodeFeat(instance));
        bukkitFeatureManager.reload();
    }
}
