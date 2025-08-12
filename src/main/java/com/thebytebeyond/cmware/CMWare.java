package com.thebytebeyond.cmware;

import com.thebytebeyond.cmware.modules.AutoStopThreshold;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import net.minecraft.util.Identifier;

public class CMWare extends MeteorAddon {
    public static final String NAME = "CMWare";
    public static final Identifier ICON = Identifier.of("cmware", "assets/cmware_icon.png");
    public static final Category CATEGORY = new Category("CMWare");

    public static final HudGroup HUD_GROUP = new HudGroup("CMWare");

    @Override
    public void onInitialize() {
        Modules.get().add(new AutoStopThreshold());
    }

    @Override
    public String getPackage() {
        return "com.thebytebeyond.cmware";
    }
}
