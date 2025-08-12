package com.thebytebeyond.cmware.modules;

import com.thebytebeyond.cmware.CMWare;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.settings.*;
import net.minecraft.client.MinecraftClient;

public class AutoStopThreshold extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> autoStopEnabled = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-stop")
        .description("Automatically sends #stop if player exceeds coordinates.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> stopOnce = sgGeneral.add(new BoolSetting.Builder()
        .name("stop-once")
        .description("Only send #stop once until player returns below threshold.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> maxX = sgGeneral.add(new DoubleSetting.Builder()
        .name("max-x")
        .defaultValue(1000.0)
        .build()
    );

    private final Setting<Double> maxY = sgGeneral.add(new DoubleSetting.Builder()
        .name("max-y")
        .defaultValue(150.0)
        .build()
    );

    private final Setting<Double> maxZ = sgGeneral.add(new DoubleSetting.Builder()
        .name("max-z")
        .defaultValue(1000.0)
        .build()
    );

    private boolean hasStopped = false;

    public AutoStopThreshold() {
        // use the category instance provided by your addon
        super(CMWare.CATEGORY, "auto-stop-threshold", "Stops Baritone if you exceed set coordinates.");
    }

    public void onActivate() {
        hasStopped = false;
    }

    // use whatever tick/update method your Meteor Module expects (onTick/tick/etc)
    public void onTick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (!autoStopEnabled.get() || mc == null || mc.player == null) return;

        double x = mc.player.getX();
        double y = mc.player.getY();
        double z = mc.player.getZ();

        boolean exceedsThreshold = x > maxX.get() || y > maxY.get() || z > maxZ.get();
        boolean belowThreshold = x <= maxX.get() && y <= maxY.get() && z <= maxZ.get();

        if (exceedsThreshold && (!stopOnce.get() || !hasStopped)) {
            if (mc.getNetworkHandler() != null) {
                mc.getNetworkHandler().sendChatMessage("#stop");
                // per your rule: no sendMessage used
                hasStopped = true;
            }
        }

        if (stopOnce.get() && hasStopped && belowThreshold) {
            hasStopped = false;
        }
    }
}
