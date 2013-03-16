package me.asofold.bpl.friendlyhopper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.asofold.bpl.friendlyhopper.checkers.CheckerFactory;
import me.asofold.bpl.friendlyhopper.checkers.block.BlockChecker;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Quick attempt to prevent hopper / dropper griefing.
 * 
 * @author mc_dev
 *
 */
public class FriendlyHopper extends JavaPlugin implements Listener{
	
	/**
	 * Faces to check for a placed block.
	 */
	private static final BlockFace[] faces = new BlockFace[]{
		BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST,
		BlockFace.UP, BlockFace.DOWN
	};
	
	private final List<BlockChecker> blockCheckers = new ArrayList<BlockChecker>();
	
	private final Set<Integer> checkIds = new HashSet<Integer>(Arrays.asList(
			Material.TRAPPED_CHEST.getId(), Material.DROPPER.getId(), Material.HOPPER.getId()));

	@Override
	public void onDisable() {
		Logger logger = getLogger();
		
		// Remove checkers.
		blockCheckers.clear();
		
		// Finished.
		logger.info(getDescription().getFullName() + " is disabled.");
	}

	@Override
	public void onEnable() {
		Logger logger = getLogger();
		
		// Setup checkers.
		addBlockCheckers(new CheckerFactory().getAvailableBlockCheckers());
		
		// Register events.
		getServer().getPluginManager().registerEvents(this, this);
		
		// Ready !
		logger.info(getDescription().getFullName() + " is enabled.");
	}

	public void addBlockCheckers(Collection<BlockChecker> blockCheckers)
	{
		for (BlockChecker checker : blockCheckers){
			addBlockChecker(checker);
		}
	}

	public void addBlockChecker(BlockChecker checker) {
		if (!blockCheckers.contains(checker)){
			blockCheckers.add(checker);
			getLogger().info("Added BlockChecker: " + checker.getName());
		}
		else{
			getLogger().info("Skipped adding already present BlockChecker: " + checker.getName());
		}
	}
	
	/**
	 * Run all block checkers, does not sanity check block id (!).
	 * @param player
	 * @param block
	 * @return True if the player is allowed to place hopper / dropper next to that block.
	 */
	public boolean checkBlock(final Player player, final Block block){
		for (int i = 0; i < blockCheckers.size(); i++){
			final BlockChecker checker = blockCheckers.get(i);
			try{
				if (!checker.checkBlock(player, block)){
					return false;
				}
			}
			catch(Throwable t){
				getLogger().log(Level.WARNING, "BlockChecker (" + checker.getName() + ") encountered an exception: " + t.getClass().getSimpleName(), t);
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param block
	 * @return True if checkBlock should be called for this block.
	 */
	public boolean shouldCheck(Block block){
		if (checkIds.contains(block.getTypeId())){
			return true;
		}
		if (block.getState() instanceof InventoryHolder){
			return true;
		}
		return false;
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onBlockPlace(final BlockPlaceEvent event){
		final ItemStack stack = event.getItemInHand();
		if (stack == null) return;
		final int id = stack.getTypeId();
		if (id != Material.HOPPER.getId() && id != Material.DROPPER.getId()){
			// TODO: Is griefing possible with placing chests against droppers !?.
			return;
		}
		final Block block = event.getBlock();
		for (int i = 0; i < faces.length; i ++) {
			final Block ref = block.getRelative(faces[i]);
			if (!shouldCheck(ref)) continue;
			if (!checkBlock(event.getPlayer(), ref)){
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onInteract(final PlayerInteractEvent event){
		final ItemStack stack = event.getPlayer().getItemInHand();
		if (stack == null) return;
		final int id = stack.getTypeId();
		if (id == Material.HOPPER_MINECART.getId()){
			event.setUseInteractedBlock(Result.DENY);
			event.setUseItemInHand(Result.DENY);
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onCraft(final CraftItemEvent event){
		if (event.getSlot() < 0){
			 // Just in case.
			return;
		}
		final Recipe recipe = event.getRecipe();
		if (recipe == null){
			return;
		}
		final ItemStack stack = recipe.getResult();
		if (stack != null && stack.getTypeId() == Material.HOPPER_MINECART.getId()){
			event.setResult(Result.DENY);
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onDispense(final BlockDispenseEvent event){
		final ItemStack stack = event.getItem();
		if (stack != null && stack.getTypeId() == Material.HOPPER_MINECART.getId()){
			event.setCancelled(true);
		}
	}
	
//	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
//	public void onVehicleMove(final VehicleMoveEvent event){
//		final Vehicle vehicle = event.getVehicle();
//		// TODO: stacked vehicles ?
//		// TODO: consider removing vehicles.
//	}
	
}

