package io.github.jawa2401.container;

import io.github.jawa2401.Main;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class YContainers {

    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Main.MOD_ID);
    public static final RegistryObject<ContainerType<StoneGeneratorContainer>> STONE_GENERATOR_CONTAINER = CONTAINERS.register("stone_generator_container",  () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        return new StoneGeneratorContainer(windowId, world, pos, inv, inv.player);
    }));
    public static void register(IEventBus eventBus)
    {
        CONTAINERS.register(eventBus);
    }
}
