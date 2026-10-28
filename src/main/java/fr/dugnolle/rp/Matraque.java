package fr.dugnolle.rp;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class Matraque {
    public static final ResourceKey<Item> MATRAQUE_RESOURCE_KEY = ModItemIds.create("matraque");

    public static final ToolMaterial MATRAQUE_MATERIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 500, 6.0F, 2.0F, 14, ItemTags.IRON_TOOL_MATERIALS);

    public static final Item MATRAQUE = ModItems.register(
        MATRAQUE_RESOURCE_KEY,
        Item::new,
        new Item.Properties().sword(MATRAQUE_MATERIAL, 1f, -2f)
    );

    public static void initialize() {
        // This method ensures the class is loaded and the item is registered.

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT)
		    .register((creativeTab) -> creativeTab.accept(MATRAQUE));
    }
}
