package fr.dugnolle.rp;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.WindCharge;
import net.minecraft.world.phys.Vec3;

public class Sceptre extends Item {
    public static final ResourceKey<Item> SCEPTRE_RESOURCE_KEY = ModItemIds.create("sceptre");

    public static final Item SCEPTRE = ModItems.register(
        SCEPTRE_RESOURCE_KEY,
        Sceptre::new,
        new Sceptre.Properties()
    );

    public static void initialize() {
        // This method ensures the class is loaded and the item is registered.

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT)
		    .register((creativeTab) -> creativeTab.accept(SCEPTRE));
    }

    public Sceptre(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player user, InteractionHand hand) {
        // Ensure we don't spawn the lightning only on the client.
        // This is to prevent desync.
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        
        if (user.gameMode() == GameType.SURVIVAL) {
            ItemStack amethystShards = Utils.getItem(user, Items.AMETHYST_SHARD);
            if (amethystShards.isEmpty()) {
                return InteractionResult.FAIL;
            }

            amethystShards.shrink(1);
        }

        Vec3 viewDirection = user.getLookAngle();
        Vec3 start = user.getEyePosition();

        WindCharge windCharge = new WindCharge(EntityTypes.WIND_CHARGE, level);
        windCharge.setPos(start);
        windCharge.setDeltaMovement(viewDirection);
        windCharge.setOwner(user);
        level.addFreshEntity(windCharge);

        // Apply cooldown to the sceptre
        ItemStack sceptreStack = user.getItemInHand(hand);
        user.getCooldowns().addCooldown(sceptreStack, 20); // 20 ticks = 1 second

        return InteractionResult.SUCCESS;
    }
}
