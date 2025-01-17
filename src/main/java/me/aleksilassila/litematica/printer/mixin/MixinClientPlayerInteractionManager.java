package me.aleksilassila.litematica.printer.mixin;

import me.aleksilassila.litematica.printer.interfaces.IClientPlayerInteractionManager;
import me.aleksilassila.litematica.printer.printer.PlacementGuide;
import me.aleksilassila.litematica.printer.printer.Printer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Method;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager implements IClientPlayerInteractionManager {
	@Shadow
	private MinecraftClient client;

    @Override
	public void rightClickBlock(BlockPos pos, Direction side, Vec3d hitVec)
	{
		interactBlock(client.player, client.world, Hand.MAIN_HAND,
			new BlockHitResult(hitVec, side, pos, false));
		interactItem(client.player, client.world, Hand.MAIN_HAND);
//		System.out.println("Printer interactBlock: pos: (" + pos.toShortString() + "), side: " + side.getName() + ", vector: " + hitVec.toString());
	}

	@Shadow
	public abstract ActionResult interactBlock(
            ClientPlayerEntity clientPlayerEntity_1, ClientWorld clientWorld_1,
            Hand hand_1, BlockHitResult blockHitResult_1);

	@Shadow
	public abstract ActionResult interactItem(PlayerEntity playerEntity_1,
                                              World world_1, Hand hand_1);

//	@Inject(at = @At("HEAD"), method = "interactBlock")
//	public void interactBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
//		System.out.println("Player interactBlock: pos: (" + hitResult.getBlockPos().toShortString() + "), side: " + hitResult.getSide().getName() + ", vector: " + hitResult.getPos().toString());
//		PlacementGuide.Action a = Printer.getPrinter().guide.getAction(hitResult.getBlockPos());
//		for (Direction side : a.getSides().keySet()) {
//			System.out.println("Side: " + side + ", " + a.getSides().get(side).toString());
//		}
//		System.out.println("Valid: " + a.getValidSide(world, hitResult.getBlockPos()));
//		System.out.println("Look: " + a.getLookDirection());
//	}
}
