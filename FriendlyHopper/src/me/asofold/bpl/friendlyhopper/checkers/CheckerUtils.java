package me.asofold.bpl.friendlyhopper.checkers;

import org.bukkit.Bukkit;

public class CheckerUtils {
	
	/**
	 * Throws a RuntimeException if the plugin is not present.
	 * @param name
	 */
	public static void assertPluginPresent(String name){
		if (Bukkit.getPluginManager().getPlugin(name) == null){
			throw new RuntimeException("Plugin not present: " + name);
		}
	}
}
