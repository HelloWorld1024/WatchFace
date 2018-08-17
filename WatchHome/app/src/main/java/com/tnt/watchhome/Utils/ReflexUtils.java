package com.tnt.watchhome.Utils;

import android.content.Context;
import android.content.res.Resources;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Coding by Zhonghua.
 * Created on 2017/7/31.
 */

public class ReflexUtils {
    private final String TAG = getClass().getSimpleName();

    /**
     * 反射执行一个空参数的方法
     *
     * @param classPath 类路径
     * @param funName   方法名
     * @return object
     */
    public static Object processFunction(String classPath, String funName) {
        try {
            Method method = Class.forName(classPath).getDeclaredMethod(funName);
            method.setAccessible(true);
            return method.invoke(Class.forName(classPath).newInstance());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射执行一个空参数的方法
     *
     * @param object  待执行方法的类
     * @param funName 方法名
     * @return object
     */
    public static Object processFunction(Object object, String funName) {
        try {
            Method method = object.getClass().getDeclaredMethod(funName);
            method.setAccessible(true);
            return method.invoke(object);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射执行一个指定方法并传入指定参数
     *
     * @param object     实例
     * @param MethodName 方法名
     * @param types      方法参数类型
     * @param params     带传入参数
     * @return 返回执行该方法后的return
     */
    public static Object processFunction(Object object, String MethodName, Class<?>[] types,
                                         Object[] params) {
        Object retObject = null;
        try {
            Method meth = object.getClass().getMethod(MethodName, types);
            meth.setAccessible(true);
            retObject = meth.invoke(object, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retObject;
    }

    /**
     * 反射执行一个空参数的方法
     *
     * @param classPath 类路径
     * @param funName   方法名
     * @return object
     */
    public static Object processStaticFunction(String classPath, String funName) {
        try {
            Method method = Class.forName(classPath).getDeclaredMethod(funName);
            method.setAccessible(true);
            return method.invoke(null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射执行一个空参数的静态方法
     *
     * @param object  待执行方法的类
     * @param funName 方法名
     * @return object
     */
    public static Object processStaticFunction(Object object, String funName) {
        try {
            Method method = object.getClass().getDeclaredMethod(funName);
            method.setAccessible(true);
            return method.invoke(null);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射执行一个指定静态方法并传入指定参数
     *
     * @param object     实例
     * @param MethodName 方法名
     * @param types      方法参数类型
     * @param params     带传入参数
     * @return 返回执行该方法后的return
     */
    public static Object processStaticFunction(Object object, String MethodName, Class<?>[]
            types, Object[] params) {
        Object retObject = null;
        try {
            Method meth = object.getClass().getMethod(MethodName, types);
            retObject = meth.invoke(null, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retObject;
    }

    /**
     * 在运行时加载指定的类，并调用指定的方法
     *
     * @param classPath  Java的类路径
     * @param MethodName 方法名
     * @param types      方法的参数类型
     * @param params     方法的参数值
     * @return object
     */
    public static Object processStaticFunction(String classPath, String MethodName, Class<?>[]
            types, Object[] params) {
        Object retObject = null;
        try {
            Method meth = Class.forName(classPath).getMethod(MethodName, types);
            retObject = meth.invoke(null, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retObject;
    }

    /**
     * 通过反射设置一个内部变量的值
     *
     * @param object    待执行方法的类
     * @param fieldName 变量名
     * @param value     待设置的值
     */
    public static void setObjField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射获取一个内部变量的值
     *
     * @param object    待执行方法的类
     * @param fieldName 变量名
     */
    public static Object getObjField(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 通过反射获取一个内部变量的值
     *
     * @param clsName   .
     * @param fieldName 变量名
     */
    public static Object getObjStaticField(String clsName, String fieldName) {
        try {
            Field field = Class.forName(clsName).getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Enum类中所有的定义
     *
     * @param clsName .
     * @return .
     */
    public static Object[] getEnumValues(String clsName) {
        try {
            return Class.forName(clsName).getEnumConstants();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Object[0];
    }

    /**
     * 获取Enum类中的一个定义
     *
     * @param clsName   .
     * @param fieldName 定义的枚举值
     * @return 定义
     */
    public static Object getEnumValue(String clsName, String fieldName) {
        try {
            Object[] objects = Class.forName(clsName).getEnumConstants();
            for (Object object : objects) {
                if (object.toString().equalsIgnoreCase(fieldName)) {
                    return object;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反向查找boolean资源
     *
     * @param context
     * @param name
     * @param value_default
     * @return
     */
    public static boolean getBooleanValueByName(Context context, String packageName, String name,
                                                boolean
            value_default) {
        int idString = getIdByName(packageName, "bool", name);
        if (idString != 0) {
            try {
                return context.getResources().getBoolean(idString);
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
                return value_default;
            }
        }
        return value_default;
    }

    /**
     * 反向查找资源id
     *
     * @param packageName .
     * @param className   .
     * @param name        .
     * @return .
     */
    public static int getIdByName(String packageName, String className, String name) {
        Class r = null;
        int id = 0;
        try {
            r = Class.forName(packageName + ".R");

            Class[] classes = r.getClasses();
            Class desireClass = null;

            for (int i = 0; i < classes.length; i++) {
                if (classes[i].getName().split("\\$")[1].equals(className)) {
                    desireClass = classes[i];
                    break;
                }
            }

            if (desireClass != null)
                id = desireClass.getField(name).getInt(desireClass);
        } catch (ClassNotFoundException | IllegalArgumentException | SecurityException |
                NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return id;
    }
}
