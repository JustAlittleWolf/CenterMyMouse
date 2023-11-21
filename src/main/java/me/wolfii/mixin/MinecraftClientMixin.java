package me.wolfii.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Final
    @Shadow
    public Mouse mouse;
    @Shadow
    public Screen currentScreen;
    @Shadow
    public abstract Window getWindow();

    @Inject(method = "onWindowFocusChanged", at = @At("TAIL"))
    private void test(boolean focused, CallbackInfo ci) {
        if(!focused) return;
        if(this.currentScreen != null) return;
        if(this.mouse == null) return;
        if(!this.mouse.isCursorLocked()) return;
        System.out.println("HELLO WORLD");
        ((MouseAccessor)mouse).setX(this.getWindow().getWidth() / 2d);
        ((MouseAccessor)mouse).setY(this.getWindow().getHeight() / 2d);
        InputUtil.setCursorParameters(this.getWindow().getHandle(), InputUtil.GLFW_CURSOR_NORMAL, mouse.getX(), mouse.getY());
        InputUtil.setCursorParameters(this.getWindow().getHandle(), InputUtil.GLFW_CURSOR_DISABLED, mouse.getX(), mouse.getY());
    }
}
