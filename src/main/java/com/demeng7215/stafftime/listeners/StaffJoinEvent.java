package com.demeng7215.stafftime.listeners;

import com.demeng7215.stafftime.StaffTime;
import com.demeng7215.stafftime.utils.StaffProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class StaffJoinEvent implements Listener {

	private StaffTime i;

	public StaffJoinEvent(StaffTime i) {
		this.i = i;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onStaffJoin(PlayerJoinEvent e) {

		if (!StaffTime.getAfkTracker().containsKey(e.getPlayer().getUniqueId()))
			for (String rank : StaffTime.getPermissions().getPlayerGroups(e.getPlayer()))

				if (i.getSettings().getStringList("staff-ranks").contains(rank)) {
					StaffTime.getAfkTracker().put(e.getPlayer().getUniqueId(),
							new StaffProfile(e.getPlayer().getLocation(), 0));
					break;
				}
	}
}
