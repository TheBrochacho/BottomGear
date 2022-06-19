package com.github.matt159.bottomgear.mixin.mixins.common.minecraft;

import com.github.matt159.bottomgear.mixin.interfaces.minecraft.IRegistryNamespacedMixin;
import net.minecraft.util.RegistryNamespaced;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(RegistryNamespaced.class)
public abstract class RegistryNamespacedMixin implements IRegistryNamespacedMixin {

    @Shadow @Final protected Map field_148758_b;

    @Override
    @SuppressWarnings("unchecked")
    public Map getUnderlyingMap() {
        return field_148758_b;
    }
}
