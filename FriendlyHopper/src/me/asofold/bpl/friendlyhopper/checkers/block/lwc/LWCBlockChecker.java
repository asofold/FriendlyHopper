package me.asofold.bpl.friendlyhopper.checkers.block.lwc;

import me.asofold.bpl.friendlyhopper.checkers.CheckerUtils;
import me.asofold.bpl.friendlyhopper.checkers.block.BlockChecker;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.griefcraft.lwc.LWC;
import com.griefcraft.model.Protection;

public class LWCBlockChecker implements BlockChecker {
	
	public LWCBlockChecker() {
		CheckerUtils.assertPluginPresent("LWC");
	}

	@Override
	public String getName() {
		return "LWC";
	}

	@Override
	public boolean checkBlock(final Player player, final Block block) {
		final LWC lwc = LWC.getInstance();
		final Protection protection = lwc.findProtection(block);
		return protection == null || lwc.canAccessProtection(player, protection);
	}

}
