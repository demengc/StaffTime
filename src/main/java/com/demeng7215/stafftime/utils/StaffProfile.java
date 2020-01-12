package com.demeng7215.stafftime.utils;

import org.bukkit.Location;

public final class StaffProfile {

	private Location location;
	private int violations;

	public StaffProfile(Location location, int violations) {
		this.location = location;
		this.violations = violations;
	}

	public Location getLocation() {
		return this.location;
	}

	public int getViolations() {
		return this.violations;
	}

}
