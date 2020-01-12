package com.demeng7215.stafftime;

import com.demeng7215.demlib.DemLib;
import com.demeng7215.demlib.api.BlacklistSystem;
import com.demeng7215.demlib.api.Common;
import com.demeng7215.demlib.api.Registerer;
import com.demeng7215.demlib.api.files.CustomConfig;
import com.demeng7215.demlib.api.messages.MessageUtils;
import com.demeng7215.stafftime.commands.PlaytimeCmd;
import com.demeng7215.stafftime.listeners.StaffJoinEvent;
import com.demeng7215.stafftime.listeners.StaffLeaveEvent;
import com.demeng7215.stafftime.tasks.PlaytimeTrackerTask;
import com.demeng7215.stafftime.tasks.StaffLocationCheckTask;
import com.demeng7215.stafftime.utils.StaffProfile;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class StaffTime extends JavaPlugin {

	public CustomConfig settingsFile;
	public CustomConfig storageFile;

	private static Permission perms = null;

	private static Map<UUID, StaffProfile> afkTracker = new HashMap<>();

	@Override
	public void onEnable() {

		DemLib.setPlugin(this, "qvczsW8p3W79f7fJ");
		MessageUtils.setPrefix("&7[&2StaffTime&7] &r");

		try {
			BlacklistSystem.checkBlacklist();
		} catch (final IOException ex) {
			MessageUtils.error(ex, 1, "Failed to authenticate.", false);
		}

		for (Player p : Bukkit.getOnlinePlayers()) p.kickPlayer("Disconnected: Please re-join.");

		getLogger().info("Loading settings file...");
		try {
			settingsFile = new CustomConfig("settings.yml");
			storageFile = new CustomConfig("storage.yml");
		} catch (final Exception ex) {
			MessageUtils.error(ex, 1, "Failed to load settings.yml.", true);
			return;
		}

		MessageUtils.setPrefix(getSettings().getString("prefix"));

		getLogger().info("Registering commands...");
		Registerer.registerCommand(new PlaytimeCmd(this));

		getLogger().info("Registering listeners...");
		Registerer.registerListeners(new StaffJoinEvent(this));
		Registerer.registerListeners(new StaffLeaveEvent(this));

		getLogger().info("Hooking into Vault...");
		setupPermissions();

		getLogger().info("Starting tasks...");
		Bukkit.getScheduler().runTaskTimer(this, new StaffLocationCheckTask(), 1200L, 1200L);
		Bukkit.getScheduler().runTaskTimer(this, new PlaytimeTrackerTask(this), 1200L, 1200L);

		MessageUtils.console("&aStaffTime v" + Common.getVersion() + " has been successfully enabled.");
	}

	@Override
	public void onDisable() {
		MessageUtils.console("&cStaffTime v" + Common.getVersion() + " has been successfully disabled.");
	}

	public FileConfiguration getSettings() {
		return settingsFile.getConfig();
	}

	public FileConfiguration getStorage() {
		return storageFile.getConfig();
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	public static Permission getPermissions() {
		return perms;
	}

	public static Map<UUID, StaffProfile> getAfkTracker() {
		return afkTracker;
	}
}
