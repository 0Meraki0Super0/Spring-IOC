package spring.Annotation;
import java.lang.reflect.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

//此类模拟Spring的IOC容器
public class myApplicationContext {
    private Class configClass;
    private final ConcurrentHashMap<String,Object> ioc=  //singletonObjects
        new ConcurrentHashMap<>();//IOC存放通过反射创建的对象（基于注解）

    public myApplicationContext(Class configClass) throws InstantiationException, IllegalAccessException {
        this.configClass = configClass;
        //得到SpringConfig配置的 @ComponentScan
        ComponentScan componentScan=(ComponentScan)this.configClass.getDeclaredAnnotation(ComponentScan.class);
        //通过@ComponentScan的value得到要扫描的 包->路径
        String path= componentScan.value();  // spring.Annotation.Component
        path=path.replace(".","/");

        //拿到包下资源
        ClassLoader classLoader=myApplicationContext.class.getClassLoader();//得到类的加载器
        URL resource = classLoader.getResource(path); //得到要扫描的包的URL
        File file = new File(resource.getFile());
        //将待加载的资源（.class）目录下的文件遍历
        if(file.isDirectory()){
            File[] files =file.listFiles();
            for(File f:files){
                //System.out.println(f);
                String absolutePath = f.getAbsolutePath();// D:\Java_code\spring\out\production\spring\spring·····.class
                if(absolutePath.endsWith(".class")){
                    //substring(起，末)，前闭后开。//获取到类名
                    String className=absolutePath.substring(absolutePath.lastIndexOf("\\")+1,absolutePath.indexOf(".class"));
                    //获取全类名
                    String classfullName =path.replace("/",".")+"."+className;
                    try {
                        //与Class.forName(classfullName)类似，都可以反射类的class。区别是Class.加载类时会调用静态方法
                        Class<?> aClass = classLoader.loadClass(classfullName);
                        if(aClass.isAnnotationPresent(Component.class)||aClass.isAnnotationPresent(Controller.class)||aClass.isAnnotationPresent(Service.class)||aClass.isAnnotationPresent(Repository.class)){
                            if(aClass.isAnnotationPresent(Component.class)) {
                                Component declaredAnnotation = aClass.getDeclaredAnnotation(Component.class);
                                String id = declaredAnnotation.value();
                                if (!"".endsWith(id)) {
                                    className = id;
                                }

                            Class<?> clazz = Class.forName(classfullName);
                            Object o = clazz.newInstance();
                            ioc.put(className,o);
                        }



                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
    public Object getBean(String name){
        return ioc.get(name);
    }
}