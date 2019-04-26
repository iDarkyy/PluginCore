package me.idarkyy.plugincore.worldmanager.builder;

import me.idarkyy.plugincore.worldmanager.options.WorldOption;
import me.idarkyy.plugincore.worldmanager.options.all.Environment;
import me.idarkyy.plugincore.worldmanager.options.all.GenerateStructures;
import me.idarkyy.plugincore.worldmanager.options.all.Generator;
import me.idarkyy.plugincore.worldmanager.options.all.Seed;
import org.bukkit.World;

import java.util.HashMap;

public class WorldBuilder {
    private String name;

    private HashMap<String, Object> options;

    public WorldBuilder(String name) {
        this.name = name;
    }

    public WorldBuilder options(WorldOption... options) {
        for (WorldOption option : options) {
            this.options.put(option.index, option.getValue());
        }

        return this;
    }

    public WorldBuilder.Result result() {
        return new WorldBuilder.Result(name, options);
    }

    public static class Result {
        private String name;
        private HashMap<String, Object> options;

        protected Result(String name, HashMap<String, Object> options) {
            this.name = name;
            this.options = options;
        }

        public String getName() {
            return name;
        }

        public HashMap<String, Object> getOptions() {
            return options;
        }

        public Long getSeed() {
            if (options.containsKey("seed")) {
                Seed seed = (Seed) options.get("seed");

                return seed.getSeed();
            }

            return null;
        }

        public String getGenerator() {
            if (options.containsKey("gen")) {
                Generator generator = (Generator) options.get("gen");

                return generator.getValue();
            }

            return null;
        }

        public World.Environment getEnvironment() {
            if (options.containsKey("env")) {
                Environment env = (Environment) options.get("env");

                return env.getValue();
            }

            return World.Environment.NORMAL;
        }

        public boolean generateStructures() {
            if (options.containsKey("genstructures")) {
                GenerateStructures generateStructures = (GenerateStructures) options.get("genstructures");

                return generateStructures.getValue();
            }

            return true;
        }
    }
}
