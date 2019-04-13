package me.idarkyy.plugincore.nms;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class NmsClass {
    private static final String SERVER_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    private static HashMap<String, NmsClass> cache = new HashMap<>();
    private Class<?> clazz;

    private NmsClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static NmsClass getNmsClass(String name) {
        String key = "net.minecraft.server." + SERVER_VERSION + "." + name;

        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        try {
            return cache.put(key, new NmsClass(Class.forName(key)));
        } catch (ClassNotFoundException e) {
            System.out.println("No NMS class found: " + key);
        }

        return null;
    }

    public Constructor<?> getConstructor(Class<?>... params) {
        try {
            return clazz.getConstructor(params);
        } catch (NoSuchMethodException e) {
            System.out.println("Warning - No constructor with params: " + Arrays.toString(params) + "\nClass: " + clazz.getPackage().toString());
        }

        return null;
    }

    public NmsMethod getMethod(String name, Class<?>... parameterTypes) {
        try {
            return new NmsMethod(clazz.getMethod(name, parameterTypes));
        } catch (NoSuchMethodException e) {
            System.out.println("No method called " + name + " in nms class " + clazz.getSimpleName());
        }

        return null;
    }

    public NmsField getField(String name, Object instance) {
        try {
            return new NmsField(clazz.getField(name), instance);
        } catch (NoSuchFieldException e) {
            System.out.println("No field called " + name + " in nms class " + clazz.getSimpleName());
        }

        return null;
    }

    public Class<?> getRawClass() {
        return clazz;
    }

    public ObjectInitializer initialize() {
        return new ObjectInitializer(this);
    }

    public static class ObjectInitializer {
        private Class<?>[] params = new Class[]{};
        private Object[] args = new Object[]{};

        private NmsClass nmsClass;

        private ObjectInitializer(NmsClass nmsClass) {
            Objects.requireNonNull(nmsClass);

            this.nmsClass = nmsClass;
        }

        public ObjectInitializer withParams(Class<?>... params) {
            this.params = params;
            return this;
        }

        public ObjectInitializer withArguments(Object... args) {
            this.args = args;
            return this;
        }

        public Object construct() {
            try {
                return nmsClass.getConstructor(params).newInstance(args);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class NmsField {
        private Field field;
        private Object instance;

        private NmsField(Field field, Object instance) {
            this.field = field;
            this.instance = instance;
        }

        public Object value() {
            try {
                return field.get(instance);
            } catch (IllegalAccessException e) {
                System.out.println("Field " + field.getName() + " in class " + field.getClass().getSimpleName() + " is inaccessible, setting accessibility to true");
                field.setAccessible(true);
                return value();
            }
        }

        public String asString() {
            Object object = value();

            if (object instanceof String) {
                return (String) object;
            }

            return null;
        }

        public Short asInteger() {
            Object object = value();

            if (object instanceof Short) {
                return (Short) object;
            }

            return null;
        }

        public Boolean asBoolean() {
            Object object = value();

            if (object instanceof Boolean) {
                return (Boolean) object;
            }

            return null;
        }

        public Double asDouble() {
            Object object = value();

            if (object instanceof Double) {
                return (Double) object;
            }

            return null;
        }

        public Long asLong() {
            Object object = value();

            if (object instanceof Long) {
                return (Long) object;
            }

            return null;
        }

        public Float asFloat() {
            Object object = value();

            if (object instanceof Float) {
                return (Float) object;
            }

            return null;
        }

        public Byte asByte() {
            Object object = value();

            if (object instanceof Byte) {
                return (Byte) object;
            }

            return null;
        }

        public Short asShort() {
            Object object = value();

            if (object instanceof Short) {
                return (Short) object;
            }

            return null;
        }

        public Character asChar() {
            Object object = value();

            if (object instanceof Character) {
                return (Character) object;
            }

            return null;
        }
    }

    public static class NmsMethod {
        private Method method;

        public NmsMethod(Method method) {
            this.method = method;
        }

        public Object invoke(Object instance, Object... arguments) {
            try {
                return method.invoke(instance, arguments);
            } catch (IllegalAccessException e) {
                System.out.println("Method " + method.getName() + " in class " + method.getClass().getSimpleName() + " is inaccessible, setting accessibility to true");
                method.setAccessible(true);
                return invoke(instance, arguments);
            } catch (InvocationTargetException e) {
                System.out.println("An error occurred while executing net.minecraft.server method #" + method.getName() + " in class " + method.getClass().getSimpleName());
                e.printStackTrace();
            }

            return null;
        }

        public Method getRawMethod() {
            return method;
        }
    }
}
