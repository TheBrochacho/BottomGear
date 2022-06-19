package com.github.matt159.bottomgear.mixin.interfaces.minecraft;

import net.minecraft.item.Item;

import java.util.Map;

public interface IRegistryNamespacedMixin {
    Map<Item, String> getUnderlyingMap();
}
