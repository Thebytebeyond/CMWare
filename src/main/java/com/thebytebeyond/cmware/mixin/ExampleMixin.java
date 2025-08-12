package com.thebytebeyond.cmware.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.screen.TitleScreen; // harmless example target
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ExampleMixin {
    @Inject(method = "init", at = @At("HEAD"))
    private void onInit(CallbackInfo ci) {
        System.out.println("CMWare ExampleMixin injected!");
    }
}
