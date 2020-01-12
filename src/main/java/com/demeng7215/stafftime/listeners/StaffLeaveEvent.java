package com.demeng7215.stafftime.listeners;

import com.demeng7215.stafftime.StaffTime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class StaffLeaveEvent implements Listener {

	private StaffTime i;

	public StaffLeaveEvent(StaffTime i) {
		this.i = i;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onStaffJoin(PlayerQuitEvent e) {
		StaffTime.getAfkTracker().remove(e.getPlayer().getUniqueId());
	}
}
