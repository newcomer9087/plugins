/*
 * Copyright (c) 2020, BegOsrs <https://github.com/begosrs>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package begosrs.barbarianassault.ticktimer;

import begosrs.barbarianassault.BaMinigameConfig;
import begosrs.barbarianassault.BaMinigamePlugin;
import begosrs.barbarianassault.Role;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import static net.runelite.api.MenuOpcode.RUNELITE_OVERLAY_CONFIG;
import net.runelite.client.ui.overlay.OverlayLayer;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.TitleComponent;

@Slf4j
@Singleton
public class RunnerTickTimerOverlay extends OverlayPanel
{
	private static final int DEFAULT_WIDTH = 20;

	private final BaMinigamePlugin plugin;
	private final BaMinigameConfig config;

	@Inject
	private RunnerTickTimerOverlay(BaMinigamePlugin plugin, BaMinigameConfig config)
	{
		super(plugin);
		setPosition(OverlayPosition.TOP_CENTER);
		setLayer(OverlayLayer.ABOVE_SCENE);
		getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "Ba minigame overlay"));
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		final Role role = plugin.getRole();
		if (role == Role.DEFENDER && config.showRunnerTickTimerDefender() ||
			role == Role.ATTACKER && config.showRunnerTickTimerAttacker())
		{
			final RunnerTickTimer runnerTickTimer = plugin.getRunnerTickTimer();
			if (runnerTickTimer != null && runnerTickTimer.isDisplaying())
			{
				final String tickTimer = runnerTickTimer.getText();
				if (tickTimer != null)
				{
					panelComponent.getChildren().add(TitleComponent.builder().text(tickTimer).color(Color.WHITE).build());
					panelComponent.setPreferredSize(new Dimension(DEFAULT_WIDTH, 0));
				}

				return super.render(graphics);
			}
		}

		return null;
	}

}
