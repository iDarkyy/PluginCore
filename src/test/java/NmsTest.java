import me.idarkyy.plugincore.nms.NmsClass;

public class NmsTest {
    public void init() {
        NmsClass nmsClass = NmsClass.getNmsClass("PlayerPlayOutMove");

        Object object = nmsClass.initialize()
                .withArguments("hello", 15)
                .withParams(String.class, Integer.class)
                .construct();

        NmsClass.NmsField field = nmsClass.getField("name", object);

        String message = field.asString();

        if (message.equalsIgnoreCase("hello")) {
            System.out.println("Works!");
        }

        if (field.asInteger() == null) {
            System.out.println("Not an integer");
        }

        String messageFromMethod = (String) nmsClass.getMethod("getName").invoke(object);

        System.out.println(messageFromMethod);
    }
}
