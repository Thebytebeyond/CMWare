package com.thebytebeyond.cmware;

import com.thebytebeyond.cmware.hud.AutoStopHud;
import com.thebytebeyond.cmware.modules.AutoStopThreshold;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.Category;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class CMWare extends MeteorAddon {
    public static final String NAME = "CMWare";
    public static final Category CATEGORY = new Category(NAME);
    public static final HudGroup HUD_GROUP = new HudGroup(NAME);

    @Override
    public void onInitialize() {
        registerCategory();
        registerModules();
        registerHud();
        printLoadedModules();
    }

    private void registerCategory() {
        try {
            Method registerMethod = null;
            try {
                registerMethod = Modules.get().getClass().getMethod("registerCategory", Category.class);
            } catch (NoSuchMethodException ignored) {}

            if (registerMethod != null) {
                registerMethod.invoke(Modules.get(), CATEGORY);
                return;
            }

            Field categoriesField = findCategoryField();
            if (categoriesField != null) {
                categoriesField.setAccessible(true);
                Object categoriesObj = categoriesField.get(Modules.get());
                if (categoriesObj instanceof List<?> list) {
                    if (!list.contains(CATEGORY)) categoriesObj.getClass().getMethod("add", Object.class).invoke(categoriesObj, CATEGORY);

                } else {
                    categoriesObj.getClass().getMethod("add", Object.class).invoke(categoriesObj, CATEGORY);
                }
            } else {
                System.out.println("CMWare: Unable to locate Modules' category container — continuing.");
            }

        } catch (Throwable t) {
            System.out.println("CMWare: category registration threw: " + t);
        }
    }

    private Field findCategoryField() {
        Class<?> modulesClass = Modules.get().getClass();
        String[] possibleNames = {"categories", "categoryList", "catList", "registeredCategories"};

        for (String name : possibleNames) {
            try {
                return modulesClass.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {}
        }

        for (Field field : modulesClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object val = field.get(Modules.get());
                if (val instanceof List<?> list && (list.isEmpty() || list.get(0) instanceof Category)) {
                    return field;
                }
            } catch (IllegalAccessException ignored) {}
        }

        return null;
    }

    private void registerModules() {
        try {
            Modules.get().add(new AutoStopThreshold());
        } catch (Throwable t) {
            System.out.println("CMWare: failed to add AutoStopThreshold module: " + t);
            t.printStackTrace();
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
