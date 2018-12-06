package TreasureDemo;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestTreasure {
    public static void main(String[] args) throws Exception {
        // 类加载器, 作用：加载一个不在classpath下的类
        ClassLoader cl = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                try {
                    FileInputStream in = new FileInputStream("I:\\13\\20181201\\Treasure.class");
                    byte[] bytes = new byte[1024 * 8];
                    int len = in.read(bytes);

                    // 调用父类的方法根据字节数组加载类
                    return defineClass(name, bytes, 0, len);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        // 根据类名加载类, 得到类对象
        Class<?> clazz = cl.loadClass("com.westos.Treasure");
//        Object obj = clazz.newInstance();
        //调用私有构造
        Constructor<?> cons = clazz.getDeclaredConstructor();
        cons.setAccessible(true);
        //创建实例对象
        Object obj = cons.newInstance();
        //获取注解
        Resource methodAnnotation = clazz.getAnnotation(Resource.class);
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
             if(methodAnnotation!=null){
                   method.invoke(obj);
             }
        }
    }
}

        /*
        public void com.westos.Treasure.me247e3d53e724001a4e828add5698d3c()
public void com.westos.Treasure.m58bc2e8a640640c2b29559a2c377ab13()
public void com.westos.Treasure.mc21085e018bc48f586b0365a2778263d()
         */

