package com.example.iocapp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * creation date: 2020-02-26 17:54
 * description ：
 */
public class ListenerInvocationHandler implements InvocationHandler {

    //定义一个 对象，和一个方法，来匹配activity.onClick();
    private Object activity;
    private Method activityMethod;

    public ListenerInvocationHandler(Object activity, Method activityMethod) {
        this.activity = activity;
        this.activityMethod = activityMethod;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return activityMethod.invoke(activity, args);
    }
}
