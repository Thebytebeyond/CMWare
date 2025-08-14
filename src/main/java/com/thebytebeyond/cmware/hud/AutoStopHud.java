package com.thebytebeyond.cmware.hud;

import com.thebytebeyond.cmware.CMWare;
import com.thebytebeyond.cmware.modules.AutoStopThreshold;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
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
        boolean breached = mod != null && mod.isThresholdBreached();
        String text = breached ? "AutoStop: ON" : "AutoStop: OFF";

        double x = box.getRenderX();
        double y = box.getRenderY();

        Color colorHud = breached ? Color.GREEN : Color.RED;
        renderer.text(text, x, y, colorHud, true, 1.5);

        box.setSize(renderer.textWidth(text), renderer.textHeight());
    }
}
