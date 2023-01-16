package hr.jawa2401.yugogun.init.container;

import hr.jawa2401.yugogun.base.YContainer;
import hr.jawa2401.yugogun.base.YSlot;
import hr.jawa2401.yugogun.init.YBlocks;
import hr.jawa2401.yugogun.init.YContainers;
import hr.jawa2401.yugogun.init.tile_entities.IronExtractorTE;
import hr.jawa2401.yugogun.init.tile_entities.StoneGeneratorTE;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IronExtractorContainer extends YContainer<IronExtractorTE> {
    @Override
    public int getSlotCount() {
        return 2;
    }

    public IronExtractorContainer(int winId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        super(YContainers.IRON_EXTRACTOR_CONTAINER.get(), winId, world, pos, playerInventory, playerEntity, YBlocks.IRON_EXTRACTOR_BLOCK.get(), 8, 86,
                new YSlot(IronExtractorTE.SLOT_ROCK_IN, 80, 11),
                new YSlot(IronExtractorTE.SLOT_IRON_OUT, 80, 55)
        );
    }
}
