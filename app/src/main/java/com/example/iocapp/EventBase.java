package com.example.iocapp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * creation date: 2020-02-23 16:20
 * description ：
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    //    textView.setOnClickListener（new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    });

    String listenerSetter();

    Class<?> listenerType();

    String callbackMethod();

}
