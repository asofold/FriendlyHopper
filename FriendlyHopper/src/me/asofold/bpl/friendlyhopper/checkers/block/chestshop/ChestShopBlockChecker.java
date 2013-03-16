package me.asofold.bpl.friendlyhopper.checkers.block.chestshop;

import me.asofold.bpl.friendlyhopper.checkers.CheckerUtils;
import me.asofold.bpl.friendlyhopper.checkers.block.BlockChecker;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.Acrobot.ChestShop.Security;

public class ChestShopBlockChecker implements BlockChecker {

	public ChestShopBlockChecker(){
		CheckerUtils.assertPluginPresent("ChestShop");
	}
	
	@Override
	public String getName() {
		return "ChestShop";
	}
	
	@Override
	public boolean checkBlock(Player player, Block block) {
		return Security.canAccess(player, block);
	}

}
