package hr.jawa2401.yugogun.init;

import hr.jawa2401.yugogun.Main;
import hr.jawa2401.yugogun.init.tile_entities.IronExtractorTE;
import hr.jawa2401.yugogun.init.tile_entities.StoneGeneratorTE;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class YTileEntities {
    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Main.MOD_ID);

    public static RegistryObject<TileEntityType<StoneGeneratorTE>> STONE_GENERATOR_TILE = TILE_ENTITIES.register("stone_generator_tile", () -> TileEntityType.Builder.create(StoneGeneratorTE::new, YBlocks.STONE_GENERATOR_BLOCK.get()).build(null));
    public static RegistryObject<TileEntityType<IronExtractorTE>> IRON_EXTRACTOR_TILE = TILE_ENTITIES.register("iron_extractor_tile", () -> TileEntityType.Builder.create(IronExtractorTE::new, YBlocks.IRON_EXTRACTOR_BLOCK.get()).build(null));


    public static void register(IEventBus bus) {
        TILE_ENTITIES.register(bus);
    }
}
