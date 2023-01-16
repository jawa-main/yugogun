package hr.jawa2401.yugogun.init.tile_entities;

import hr.jawa2401.yugogun.YConfig;
import hr.jawa2401.yugogun.init.YTileEntities;
import hr.jawa2401.yugogun.util.ItemUtil;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IronExtractorTE extends TileEntity implements ITickableTileEntity {

    public static final int SLOT_ROCK_IN = 0;
    public static final int SLOT_IRON_OUT = 1;

    public static final int SLOT_COUNT = 2;

    public float ironGenerationProgress = 0;

    private final ItemStackHandler itemHandler = createHandler();

    public IronExtractorTE(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public IronExtractorTE() {
        this(YTileEntities.IRON_EXTRACTOR_TILE.get());
    }

    private ItemStackHandler createHandler() {
        System.out.println("slotCount(IRON):" + SLOT_COUNT);
        return new ItemStackHandler(SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.DOWN) {
                // output slot
                ItemStackHandler _h = new ItemStackHandler(SLOT_COUNT) {
                    @Nonnull
                    @Override
                    public ItemStack extractItem(int slot, int amount, boolean simulate) {
                        if (slot != SLOT_IRON_OUT) return ItemStack.EMPTY;
                        if (!simulate) return ItemStack.EMPTY;
                        return ItemUtil.extract(itemHandler.getStackInSlot(slot), amount);
                    }
                };

                LazyOptional<IItemHandler> lazyOptional = LazyOptional.of(() -> _h);
                return lazyOptional.cast();
            } else // for code readability
            {
                // input slot
                ItemStackHandler _h = new ItemStackHandler(SLOT_COUNT) {
                    @Nonnull
                    @Override
                    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                        if (!simulate) return ItemStack.EMPTY;
                        if (slot != SLOT_ROCK_IN) return ItemStack.EMPTY;
                        if (stack.getItem() != Items.COBBLESTONE) return ItemStack.EMPTY;

                        itemHandler.setStackInSlot(slot, stack);

                        return stack;
                    }
                };

                LazyOptional<IItemHandler> lazyOptional = LazyOptional.of(() -> _h);
                return lazyOptional.cast();
            }
//            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.write(compound);
    }

    public void generateIron() {
        if (itemHandler.getStackInSlot(SLOT_ROCK_IN).getItem() != Items.COBBLESTONE) return;
        if (itemHandler.getStackInSlot(SLOT_IRON_OUT).getCount() == 64) return;

        // called every tick
        if (ironGenerationProgress < 1) {
            ironGenerationProgress += YConfig.ironGenerateInterval;
        } else {
            // generate iron
            ItemStack ingot = itemHandler.getStackInSlot(SLOT_IRON_OUT);
            if (ingot.getItem() == Items.AIR) {
                ingot = new ItemStack(Items.IRON_INGOT);
                ingot.setCount(0);
            }
            ingot.setCount(ingot.getCount() + 1);

            itemHandler.setStackInSlot(SLOT_IRON_OUT, ingot);
            ironGenerationProgress = 0;
        }
    }

    @Override
    public void tick() {
        generateIron();
    }
}