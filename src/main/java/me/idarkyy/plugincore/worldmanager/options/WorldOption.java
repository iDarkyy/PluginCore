package me.idarkyy.plugincore.worldmanager.options;

public abstract class WorldOption<T> {
    public String index;

    private T value;

    protected T setValue(T value) {
        return this.value = value;
    }

    public T getValue() {
        return value;
    }
}
