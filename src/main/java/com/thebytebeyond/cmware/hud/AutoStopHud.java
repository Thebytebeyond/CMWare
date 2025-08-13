package com.thebytebeyond.cmware.hud;

import com.thebytebeyond.cmware.CMWare;
import com.thebytebeyond.cmware.modules.AutoStopThreshold;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.render.color.Color;

public class AutoStopHud extends HudElement {
    public static final HudElementInfo<AutoStopHud> INFO = new HudElementInfo<>(
        CMWare.HUD_GROUP,
        "auto-stop-status",
        "Shows the status of the AutoStopThreshold module.",
        AutoStopHud::new
    );

    public AutoStopHud() {
        super(INFO);
    }

    @Override
    public void render(HudRenderer renderer) {
        AutoStopThreshold mod = Modules.get().get(AutoStopThreshold.class);
        boolean on = mod != null && mod.isActive();

        String text = on ? "AutoStop: ON" : "AutoStop: OFF";

        double x = box.getRenderX();
        double y = box.getRenderY();

        Color colorHud = on ? Color.GREEN : Color.RED;
        renderer.text(text, x, y, colorHud, true);

        box.setSize(renderer.textWidth(text), renderer.textHeight());
    }
}
