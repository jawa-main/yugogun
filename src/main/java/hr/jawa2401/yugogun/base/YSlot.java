package hr.jawa2401.yugogun.base;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class YSlot {
    public int index;
    public int xPos;
    public int yPos;

    public YSlot(int idx, int x, int y)
    {
        index = idx;
        xPos = x;
        yPos = y;
    }

    public SlotItemHandler toSlot(IItemHandler h)
    {
        return new SlotItemHandler(h, index, xPos, yPos);
    }
}
