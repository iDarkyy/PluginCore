package me.idarkyy.plugincore.worldmanager.options.all;

import me.idarkyy.plugincore.utils.NumberUtils;
import me.idarkyy.plugincore.worldmanager.options.WorldOption;

public class Seed extends WorldOption<Long> {
    private Seed(long seed) {
        setValue(seed);

        index = "seed";
    }

    public static Seed of(String seed) {
        if (!NumberUtils.isLong(seed)) {
            throw new IllegalArgumentException("The inputted argument is not a long (as string)");
        }

        return new Seed(Long.valueOf(seed));
    }

    public static Seed of(long seed) {
        return new Seed(seed);
    }

    public long getSeed() {
        return getValue();
    }
}
