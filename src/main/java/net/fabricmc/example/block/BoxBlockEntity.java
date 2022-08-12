package net.fabricmc.example.block;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.gui.BoxScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BoxBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    public final SimpleInventory simpleInventory = new SimpleInventory(9);
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    //this is the int we want to sync, it gets increased by one each tick
    private int syncedInt;
    //PropertyDelegate is an interface which we will implement inline here.
    //It can normally contain multiple integers as data identified by the index, but in this example we only have one.
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return syncedInt;
        }

        @Override
        public void set(int index, int value) {
            syncedInt = value;
        }

        //this is supposed to return the amount of integers you have in your delegate, in our example only one
        @Override
        public int size() {
            return 9;
        }
    };

    public BoxBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleMod.BOX_BLOCK_ENTITY, pos, state);
    }

    //These Methods are from the NamedScreenHandlerFactory Interface

    //increase the synced Integer by one each tick, we only do this on the server for demonstration purposes.
    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (!world.isClient)
            ((BoxBlockEntity) blockEntity).syncedInt++;
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        //We provide this to the screenHandler as our class Implements Inventory
        //Only the Server has the Inventory at the start, this will be synced to the client in the ScreenHandler

        //Similar to the inventory: The server has the PropertyDelegate and gives it to the server instance of the screen handler directly
        return new BoxScreenHandler(syncId, playerInventory, this.simpleInventory, propertyDelegate);
    }

    @Override
    public Text getDisplayName() {
        // For versions 1.18.2 and below, please use return new TranslatableText(getCachedState().getBlock().getTranslationKey());
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }
}
