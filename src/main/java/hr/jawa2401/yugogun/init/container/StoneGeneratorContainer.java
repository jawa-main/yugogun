package hr.jawa2401.yugogun.init.container;

import hr.jawa2401.yugogun.base.YContainer;
import hr.jawa2401.yugogun.base.YSlot;
import hr.jawa2401.yugogun.init.YBlocks;
import hr.jawa2401.yugogun.init.YContainers;
import hr.jawa2401.yugogun.init.tile_entities.StoneGeneratorTE;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StoneGeneratorContainer extends YContainer {
    @Override
    public int getSlotCount() {
        return 3;
    }

    public StoneGeneratorContainer(int winId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(YContainers.STONE_GENERATOR_CONTAINER.get(), winId, world, pos, playerInventory, playerEntity, YBlocks.STONE_GENERATOR_BLOCK.get(), 8, 86,
                new YSlot(StoneGeneratorTE.SLOT_WATER_IN, 26, 36),
                new YSlot(StoneGeneratorTE.SLOT_ROCK_OUT, 80, 36),
                new YSlot(StoneGeneratorTE.SLOT_LAVA_IN, 134, 36));
    }
}
