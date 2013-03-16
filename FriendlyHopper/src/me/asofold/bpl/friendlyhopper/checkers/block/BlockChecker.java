package me.asofold.bpl.friendlyhopper.checkers.block;

import me.asofold.bpl.friendlyhopper.checkers.Checker;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface BlockChecker extends Checker{
	/**
	 * 
	 * @param player
	 * @param block
	 * @return True if allowed to place hopper / dropper next to it.
	 */
	public boolean checkBlock(Player player, Block block);
}
