package com.demeng7215.stafftime.commands;

import com.demeng7215.demlib.api.CustomCommand;
import com.demeng7215.demlib.api.messages.MessageUtils;
import com.demeng7215.stafftime.StaffTime;
import com.demeng7215.stafftime.utils.Playtime;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class PlaytimeCmd extends CustomCommand {

	private StaffTime i;

	public PlaytimeCmd(StaffTime i) {
		super("playtime");

		this.i = i;

		setDescription("Check a staff member's playtime.");
		setAliases(Collections.singletonList("stafftime"));
	}

	@Override
	protected void run(CommandSender sender, String[] args) {

		if (!sender.isOp() && !sender.hasPermission(i.getSettings().getString("view-playtime-permission"))) {
			MessageUtils.tell(sender, i.getSettings().getString("messages.no-permission"));
			return;
		}

		if (!checkArgsStrict(args, 1, sender, i.getSettings().getString("messages.invalid-usage"))) return;

		OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);

		if (!i.getStorage().getConfigurationSection("playtime").getKeys(false)
				.contains(target.getUniqueId().toString())) {
			MessageUtils.tell(sender, i.getSettings().getString("messages.invalid-player")
					.replace("%target%", args[0]));
			return;
		}

		MessageUtils.tell(sender, i.getSettings().getString("messages.playtime")
				.replace("%time%", new Playtime(i, i.getStorage().getInt("playtime." +
						target.getUniqueId())).parsePlaytime())
				.replace("%target%", args[0]));
	}
}
