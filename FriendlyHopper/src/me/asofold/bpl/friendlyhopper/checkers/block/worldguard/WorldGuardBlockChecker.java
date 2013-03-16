package me.asofold.bpl.friendlyhopper.checkers.block.worldguard;

import me.asofold.bpl.friendlyhopper.checkers.CheckerUtils;
import me.asofold.bpl.friendlyhopper.checkers.block.BlockChecker;
import me.asofold.bpl.friendlyhopper.plshared.plugins.PluginGetter;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class WorldGuardBlockChecker implements BlockChecker {
	
	private final PluginGetter<WorldGuardPlugin> wg;
	
	public WorldGuardBlockChecker(){
		CheckerUtils.assertPluginPresent("WorldGuard");
		wg = new PluginGetter<WorldGuardPlugin>("WorldGuard");
		wg.registerEvents(Bukkit.getPluginManager().getPlugin("FriendlyHopper")); // Not optimal.
		wg.fetchPlugin();
	}

	@Override
	public String getName() {
		return "WorldGuard";
	}

	@Override
	public boolean checkBlock(Player player, Block block) {
		return wg.getPlugin().canBuild(player, block);
	}

}
