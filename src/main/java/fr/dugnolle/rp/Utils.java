package fr.dugnolle.rp;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Utils {
    public static ItemStack getItem(Player player, Item item) {
        Inventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);

            if (stack.is(item)) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }
}
