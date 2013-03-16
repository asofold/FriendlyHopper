package me.asofold.bpl.friendlyhopper.checkers.block.lockette;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.yi.acru.bukkit.Lockette.Lockette;

import me.asofold.bpl.friendlyhopper.checkers.CheckerUtils;
import me.asofold.bpl.friendlyhopper.checkers.block.BlockChecker;

public class LocketteBlockChecker implements BlockChecker {
	
	public LocketteBlockChecker(){
		CheckerUtils.assertPluginPresent("Lockette");
	}
	
	@Override
	public String getName() {
		return "Lockette";
	}


	@Override
	public boolean checkBlock(Player player, Block block) {
		return Lockette.isUser(block, player.getName(), true);
	}

}
