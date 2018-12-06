package TreasureDemo;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

//老师方法
public class TestTreasure2 {
    public static void main(String[] args) throws Exception {
        ClassLoader cl = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                try {
                    FileInputStream in = new FileInputStream("I:\\13\\20181201\\Treasure.class");
                    byte[] bytes = new byte[1024 * 8];
                    int len = in.read(bytes);

                    return defineClass(name, bytes, 0, len);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        // 根据类名加载类, 得到类对象
        Class<?> clazz = cl.loadClass("com.westos.Treasure");
        // 访问私有构造方法，创建对象实例
        Constructor<?> cons = clazz.getDeclaredConstructor();
        cons.setAccessible(true);
        Object obj = cons.newInstance();
        // 找到有注解的方法，并反射调用
        Arrays.stream(clazz.getMethods()).filter(m -> m.getAnnotations().length > 0).findFirst().ifPresent((method) -> {
            try {
                method.invoke(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }
    }

        /*
        public void com.westos.Treasure.me247e3d53e724001a4e828add5698d3c()
public void com.westos.Treasure.m58bc2e8a640640c2b29559a2c377ab13()
public void com.westos.Treasure.mc21085e018bc48f586b0365a2778263d()
         */

