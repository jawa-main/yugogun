package io.github.jawa2401.TE;

import io.github.jawa2401.Main;
import io.github.jawa2401.blocks_items.YBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class YTEs {
    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Main.MOD_ID);

    public static RegistryObject<TileEntityType<StoneGeneratorTE>> STONE_GENERATOR_TILE = TILE_ENTITIES.register("stone_generator_tile", () -> TileEntityType.Builder.create(StoneGeneratorTE::new, YBlocks.STONE_GENERATOR_BLOCK.get()).build(null));


    public static void register(IEventBus bus) {
        TILE_ENTITIES.register(bus);
    }
}
