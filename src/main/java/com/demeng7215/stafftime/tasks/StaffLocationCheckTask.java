package com.demeng7215.stafftime.tasks;

import com.demeng7215.stafftime.StaffTime;
import com.demeng7215.stafftime.utils.StaffProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StaffLocationCheckTask implements Runnable {

	@Override
	public void run() {

		for (UUID uuid : StaffTime.getAfkTracker().keySet()) {

			Player p = Bukkit.getServer().getPlayer(uuid);

			if (StaffTime.getAfkTracker().get(uuid).getLocation().equals(p.getLocation())) {
				StaffTime.getAfkTracker().put(uuid, new StaffProfile(p.getLocation(),
						StaffTime.getAfkTracker().get(uuid).getViolations() + 1));
				return;
			}

			StaffTime.getAfkTracker().put(uuid, new StaffProfile(p.getLocation(), 0));
		}
	}
}
