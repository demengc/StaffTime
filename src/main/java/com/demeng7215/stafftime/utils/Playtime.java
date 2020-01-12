package com.demeng7215.stafftime.utils;

import com.demeng7215.stafftime.StaffTime;

public class Playtime {

	private StaffTime i;

	private final int totalMinutes;

	private final String weeks;
	private final String days;
	private final String hours;
	private final String minutes;

	public Playtime(StaffTime i, int totalMinutes) {
		this.i = i;
		this.totalMinutes = totalMinutes;
		this.weeks = convertTwoDigits(totalMinutes / 10080);
		this.days = convertTwoDigits((totalMinutes % 10080) / 1440);
		this.hours = convertTwoDigits(((totalMinutes % 10080) % 1440) / 60);
		this.minutes = convertTwoDigits((((totalMinutes % 10080) % 1440) % 60));
	}

	public String parsePlaytime() {
		return i.getSettings().getString("playtime-format")
				.replace("%weeks%", this.weeks)
				.replace("%days%", this.days)
				.replace("%hours%", this.hours)
				.replace("%minutes%", this.minutes);
	}

	private String convertTwoDigits(int i) {

		if (i == 0) {
			return "00";
		}

		if (i / 10 == 0) {
			return "0" + i;
		}

		return String.valueOf(i);
	}
}
