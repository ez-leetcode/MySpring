package com.spring.MySpring.spring;

import com.spring.MySpring.mybatis.executor.MyExecutor;
import com.spring.MySpring.springmvc.Controller;
import com.spring.MySpring.springmvc.Service;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MyApplicationContext {

    private Class<?> configClass;

    //单例池
    private ConcurrentHashMap<String,Object> singletonObjectsMap = new ConcurrentHashMap<>();

    //Bean定义池
    private ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    //处理器
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public MyApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        //扫描路径  生成 beanDefinition  存到mao里
        scan(configClass);
        //根据作用域需求生成bean放入单例池
        for(String beanName:beanDefinitionMap.keySet()){
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition.getScope().equals("singleton")){
                //如果是单例的作用域  生成   放入单例池
                Object object = createBean(beanDefinition);
                singletonObjectsMap.put(beanName,object);
            }
        }
        //dev
        for(String s:beanDefinitionMap.keySet()){
            BeanDefinition beanDefinition = beanDefinitionMap.get(s);
            System.out.println(beanDefinition.getClassName().getName());
            System.out.println(beanDefinition.getScope());
        }
        System.out.println("------");
        for(String s:singletonObjectsMap.keySet()){
            Object o = singletonObjectsMap.get(s);
            System.out.println(o);
        }
    }

    private Object createBean(BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getClassName();
        //根据class  反射获取对象实例
        Object instance = null;
        try {
            //对象创建
            instance = clazz.getDeclaredConstructor().newInstance();
            //依赖注入
            for(Field field:clazz.getDeclaredFields()){
                //加了Autowired注解的
                if(field.isAnnotationPresent(Autowired.class)){
                    //从map里获取bean
                    Object o = singletonObjectsMap.get(field.getName());
                    //这上面会出现循环依赖问题
                    field.setAccessible(true);
                    field.set(instance,o);
                }
            }
            //判断是否实现Aware接口
            if(instance instanceof BeanNameAware){
                //这有问题
                ((BeanNameAware)instance).setBeanName(beanDefinition.getClassName().getName());
            }
            //前置处理
            for(BeanPostProcessor beanPostProcessor:beanPostProcessorList){
                instance = beanPostProcessor.postProcessBeforeInitialization(instance,beanDefinition.getClassName().getName());
            }

            //初始化
            if(instance instanceof InitializingBean){
                try {
                    ((InitializingBean)instance).afterPropertiesSet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //后置处理
            for(BeanPostProcessor beanPostProcessor:beanPostProcessorList){
                instance = beanPostProcessor.postProcessAfterInitialization(instance,beanDefinition.getClassName().getName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return instance;
    }

    private void scan(Class<?> configClass) {
        //ComponentScan componentScan = configClass.getDeclaredAnnotation(ComponentScan.class);
        //String path = componentScan.value();
        //path = path.replace(".","/");
        //System.out.println(path);
        ClassLoader classLoader = MyApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource("com/spring/MySpring/test");
        URL springResource = classLoader.getResource("com/spring/MySpring/spring");
        File file = new File(resource.getFile());
        File springFile = new File(springResource.getFile());
        parseFile(classLoader, file);
        parseFile(classLoader, springFile);
    }

    private void parseFile(ClassLoader classLoader, File file) {
        //获取加载的文件
        if(file.isDirectory()){
            File [] files = file.listFiles();
            for(File f:files){
                String fileName = f.getAbsolutePath();
                String className = fileName.substring(fileName.indexOf("com"),fileName.indexOf(".class"));
                className = className.replace("\\",".");
                //加载的所有类
                Class<?> clazz = null;
                try{
                    clazz = classLoader.loadClass(className);
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
                //对遍历查所有的bean
                if(clazz.isAnnotationPresent(Component.class)){
                    //是否是特殊的beanPostProcessor
                    System.out.println(clazz.getName());
                    if(BeanPostProcessor.class.isAssignableFrom(clazz)){
                        try {
                            BeanPostProcessor instance = (BeanPostProcessor) clazz.getDeclaredConstructor().newInstance();
                            //放入list
                            beanPostProcessorList.add(instance);
                            continue;
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    //先生成BeanDefinition
                    BeanDefinition beanDefinition = new BeanDefinition();
                    Component componentAnnotation = clazz.getDeclaredAnnotation(Component.class);
                    //设置类属性
                    String beanName = componentAnnotation.value();
                    beanDefinition.setClassName(clazz);
                    //有作用域注解
                    if(clazz.isAnnotationPresent(Scope.class)){
                        Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                        beanDefinition.setScope(scopeAnnotation.value());
                    }else{
                        beanDefinition.setScope("singleton");
                    }
                    beanDefinitionMap.put(beanName,beanDefinition);
                }
            }
        }
    }

    public Object getBean(String beanName){
        if(beanDefinitionMap.containsKey(beanName)){
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition == null){
                //找不到bean信息
                throw new NullPointerException();
            }else{
                if(beanDefinition.getScope().equals("singleton")){
                    Object o = singletonObjectsMap.get(beanName);
                    return o;
                }else{
                    //作用域不是单例
                    Object o = createBean(beanDefinition);
                    return o;
                }
            }
        }
        return null;
    }

    //返回实现了接口的bean
    public List<Object> getBeanByInterface(Class clazz){
        List<Object> ret = new ArrayList<>();
        for(String s:beanDefinitionMap.keySet()){
            Object o = beanDefinitionMap.get(s);
            if(o.getClass().isAssignableFrom(clazz)){
                ret.add(o);
            }
        }
        return ret;
    }
}
