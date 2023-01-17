package hr.jawa2401.yugogun;

import hr.jawa2401.yugogun.init.YBlocks;
import hr.jawa2401.yugogun.init.YContainers;
import hr.jawa2401.yugogun.init.YItems;
import hr.jawa2401.yugogun.init.YTileEntities;
import hr.jawa2401.yugogun.init.screens.IronExtractorScreen;
import hr.jawa2401.yugogun.init.screens.StoneGeneratorScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MOD_ID)
public class Main {
    public static final String MOD_ID = "yugogun";

    public Main() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::doClientStuff);

        eventBus.register(this);
        YBlocks.BLOCKS.register(eventBus);
        YItems.ITEMS.register(eventBus);
        YTileEntities.register(eventBus);
        YContainers.register(eventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() ->
        {
            ScreenManager.registerFactory(YContainers.IRON_EXTRACTOR_CONTAINER.get(), IronExtractorScreen::new);
            ScreenManager.registerFactory(YContainers.STONE_GENERATOR_CONTAINER.get(), StoneGeneratorScreen::new);
        });
    }
}
