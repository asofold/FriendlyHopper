package me.asofold.bpl.friendlyhopper.checkers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import me.asofold.bpl.friendlyhopper.checkers.block.BlockChecker;
import me.asofold.bpl.friendlyhopper.checkers.block.chestshop.ChestShopBlockChecker;
import me.asofold.bpl.friendlyhopper.checkers.block.lockette.LocketteBlockChecker;
import me.asofold.bpl.friendlyhopper.checkers.block.worldguard.WorldGuardBlockChecker;

public class CheckerFactory {
	public Collection<BlockChecker> getAvailableBlockCheckers(){
		List<BlockChecker> checkers = new LinkedList<BlockChecker>();
		
		try{
			checkers.add(new WorldGuardBlockChecker());
		}
		catch (Throwable t){}
		
		try{
			checkers.add(new ChestShopBlockChecker());
		}
		catch (Throwable t){}
		
		try{
			checkers.add(new LocketteBlockChecker());
		}
		catch (Throwable t){}
		
		return checkers;
	}
}
