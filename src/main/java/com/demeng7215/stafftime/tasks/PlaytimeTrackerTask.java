package com.demeng7215.stafftime.tasks;

import com.demeng7215.demlib.api.messages.MessageUtils;
import com.demeng7215.stafftime.StaffTime;

import java.io.IOException;
import java.util.UUID;

public class PlaytimeTrackerTask implements Runnable {

	private StaffTime i;

	public PlaytimeTrackerTask(StaffTime i) {
		this.i = i;
	}

	@Override
	public void run() {

		for (UUID uuid : StaffTime.getAfkTracker().keySet()) {

			if (StaffTime.getAfkTracker().get(uuid).getViolations() >=
					i.getSettings().getInt("minutes-before-considered-afk")) return;

			i.getStorage().set("playtime." + uuid.toString(),
					i.getStorage().getInt("playtime." + uuid.toString()) + 1);
		}

		try {
			i.storageFile.saveConfig();
		} catch (final IOException ex) {
			MessageUtils.error(ex, 2, "Failed to save data.", false);
		}
	}
}
