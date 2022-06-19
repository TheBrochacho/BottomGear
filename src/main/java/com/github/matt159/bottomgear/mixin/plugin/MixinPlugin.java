package com.github.matt159.bottomgear.mixin.plugin;

import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.IMixinPlugin;
import com.falsepattern.lib.mixin.ITargetedMod;
import com.github.matt159.bottomgear.Tags;
import org.apache.logging.log4j.Logger;

public class MixinPlugin implements IMixinPlugin {
    private static final Logger logger = IMixinPlugin.createLogger(Tags.MODNAME);

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public ITargetedMod[] getTargetedModEnumValues() {
        return TargetedMod.values();
    }

    @Override
    public IMixin[] getMixinEnumValues() {
        return Mixin.values();
    }
}