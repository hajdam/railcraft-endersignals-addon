package mods.railcraft_ender.common.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mods.railcraft_ender.common.modules.ModuleSignals;
import mods.railcraft.common.modules.ModuleManager;

@Mod(modid = Railcraft_Ender.MODID, name = Railcraft_Ender.MODNAME, version = Railcraft_Ender.MODVER, dependencies = "after:Railcraft")
public class Railcraft_Ender {

    public static final String MODID = "railcraft_ender";
    public static final String MODNAME = "Railcraft Ender Signals Additions";
    public static final String MODVER = "0.1.1";

    @Instance("railcraft_ender")
    public static Railcraft_Ender instance;

    @SidedProxy(clientSide = "mods.railcraft_ender.common.core.ClientProxy", serverSide = "mods.railcraft_ender.common.core.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (ModuleManager.Module.SIGNALS.isEnabled()) {
            ModuleSignals.preInit();
        }

        proxy.preInitClient();
    }

    @EventHandler
    public static void init(FMLInitializationEvent event) {
        if (ModuleManager.Module.SIGNALS.isEnabled()) {
            ModuleSignals.init();
        }

        proxy.initClient();
    }

    public static Railcraft_Ender getMod() {
        return instance;
    }
}
