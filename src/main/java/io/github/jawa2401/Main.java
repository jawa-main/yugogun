package io.github.jawa2401;

import io.github.jawa2401.TE.YTEs;
import io.github.jawa2401.blocks_items.YBlocks;
import io.github.jawa2401.blocks_items.YItems;
import io.github.jawa2401.container.StoneGeneratorContainer;
import io.github.jawa2401.container.YContainers;
import io.github.jawa2401.gui.StoneGeneratorScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
        YTEs.register(eventBus);
        YContainers.register(eventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() ->
        {
            System.out.println("doclient");
            ScreenManager.registerFactory(YContainers.STONE_GENERATOR_CONTAINER.get(), StoneGeneratorScreen::new);
        });
    }
}
