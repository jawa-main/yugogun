package hr.jawa2401.yugogun.base;

import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class YTileEntityWithContainer<T extends Container> extends TileEntity {
    public YTileEntityWithContainer(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }
    public T container;
}
