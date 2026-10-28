package fr.dugnolle.rp;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityTypes;

public class JudgeHammer {
    public static final ResourceKey<Item> JUDGE_HAMMER_RESOURCE_KEY = ModItemIds.create("judge_hammer");

    public static final Item JUDGE_HAMMER = ModItems.register(
        JUDGE_HAMMER_RESOURCE_KEY,
        Item::new,
        new Item.Properties().stacksTo(1)
    );

    public static void initialize() {

    CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
        .register(tab -> tab.accept(JUDGE_HAMMER));

    AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {

        if (!player.getMainHandItem().is(JUDGE_HAMMER)) {
            return InteractionResult.PASS;
        }

        if (!level.getBlockState(pos).is(Blocks.SMITHING_TABLE)) {
            return InteractionResult.PASS;
        }

        
        if (level instanceof ServerLevel serverLevel) {

            LightningBolt lightning =
                new LightningBolt(EntityTypes.LIGHTNING_BOLT, serverLevel);

            lightning.setPos(
                pos.getX() + 0.5,
                pos.getY() + 1.0,
                pos.getZ() + 0.5
            );

            lightning.setVisualOnly(true);
            serverLevel.addFreshEntity(lightning);
        }


                return InteractionResult.SUCCESS;
            });
        }
}
