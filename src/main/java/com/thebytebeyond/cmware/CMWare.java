package com.thebytebeyond.cmware;

import com.thebytebeyond.cmware.hud.AutoStopHud;
import com.thebytebeyond.cmware.modules.AutoStopThreshold;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.Category;

public class CMWare extends MeteorAddon {
    public static final String NAME = "CMWare";
    public static final Category CATEGORY = new Category(NAME);
    public static final HudGroup HUD_GROUP = new HudGroup("Auto Stop");

    @Override
    public void onRegisterCategories() {
        registerCategory();
    }

    @Override
    public void onInitialize() {
        /* register categories (NEVER IN onInitialize) FIRST, then
           add Modules, then
           HUD (& any other graphics black magic)
           (Debug) MODULE LOADING LOGGER
         */
        Modules.get().add(new AutoStopThreshold());
        Hud.get().register(AutoStopHud.INFO);
        printLoadedModules();
    }

    //0.2.1 improved readability by using barebones catches
    private void registerCategory() {
        try {
                Modules.registerCategory(CATEGORY);
        }
        catch (NoSuchMethodError e) {
            throw new RuntimeException("MeteorClient version too old or incompatible — CATEGORY not registered", e);
        }
    }

    private void registerModules() {
        try {
            Modules.get().add(new AutoStopThreshold());
        } catch (Throwable t) {
            System.out.println("CMWare: failed to add AutoStopThreshold module: " + t);
            t.printStackTrace();
            //TODO: Make logger more robust(?)
        }
    }

    private void registerHud() {
        Hud.get().register(AutoStopHud.INFO);
    }

    private void printLoadedModules() {
        System.out.println("Modules loaded:");
        for (var m : Modules.get().getAll()) {
            System.out.println(m.name);
        }
    }

    @Override
    public String getPackage() {
        return "com.thebytebeyond.cmware";
    }
}
