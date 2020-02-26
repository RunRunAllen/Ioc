package com.example.iocapp;

import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * creation date: 2020-02-23 14:05
 * description ：
 */
public class InjectUtils {

    private static int layoutId = 0;

    public static void inject(Object context) {
        injectLayout(context);
        injectView(context);
        injectEvent(context);
    }

    private static void injectEvent(Object context) {
        Class<?> clazz = context.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                //获取注解的 字节码文件
                Class<? extends Annotation> annntationClazz = annotation.annotationType();

                //获取三要素
                EventBase eventBase = annntationClazz.getAnnotation(EventBase.class);
                if (eventBase == null) {
                    continue;
                }
                String listenerSetter = eventBase.listenerSetter();
                Class<?> listenerType = eventBase.listenerType();
                String callbackMethod = eventBase.callbackMethod();

                // 获取具体的事件
                try {
                    Method value = annntationClazz.getDeclaredMethod("value");
                    int[] viewId = (int[]) value.invoke(annotation);
                    for (int id : viewId) {
                        //获取到button
                        Method viewMethod = clazz.getMethod("findViewById", int.class);
                        View view = (View) viewMethod.invoke(context, id);

                        // view.setOnClickListener( onClick())  通过动态代理调
                        ListenerInvocationHandler listenerInvocationHandler = new ListenerInvocationHandler(context, method);
                        //代理
                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                new Class[]{listenerType}, listenerInvocationHandler);
                        //调用方法  让代理对象执行 onClick()
                        if (view != null) {
                            Method setOnClickListener = view.getClass().getMethod(listenerSetter, listenerType);
                            setOnClickListener.invoke(view, proxy);

                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }


    }

    public static void injectLayout(Object context) {

        Class<?> clazz = context.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            layoutId = contentView.value();
        }

        // 通过反射去获取 setContentView
        try {
            Method method = context.getClass().getMethod("setContentView", int.class);
            method.invoke(context, layoutId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void injectView(Object context) {
        Class<?> clazz = context.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int valueId = viewInject.value();
                //运行到这里，每个按钮的ID已经取到了
                //注入就是反射执行findViewById方法
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    View view = (View) method.invoke(context, valueId);
                    field.setAccessible(true);
                    field.set(context, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
