package library.mlibrary.util.inject;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;

import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.log.LogDebug;


public class InjectUtil {
    private static final String TAG = "InjectUtil";

    private InjectUtil() {
    }

    private static Field[] getAllFields(Class<?> classz, boolean b) {
        Field[] fields = classz.getDeclaredFields();
        Class<?> superclass = classz.getSuperclass();
        if (superclass != null && b) {
            return CommonUtil.concatArray(fields, getAllFields(superclass, b));
        } else {
            return fields;
        }
    }

    public static void injectActivityFields(Activity activity, boolean superclass) {
        Field[] fields = getAllFields(activity.getClass(), superclass);
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(activity) != null)
                        continue;
                    Inject viewInject = field.getAnnotation(Inject.class);
                    if (viewInject != null)
                        field.set(activity, activity.findViewById(viewInject.value()));
                } catch (NullPointerException e) {
                    LogDebug.e(TAG, "第一个参数为空！", e);
                } catch (IllegalArgumentException e) {
                    LogDebug.e(TAG, "第一个参数不是ui类的一个实例！", e);
                } catch (IllegalAccessException e) {
                    LogDebug.e(TAG, "字段 " + field.getName() + " 类型为 private，不可访问！", e);
                } catch (Exception e) {
                    LogDebug.e(TAG, "初始化行为监听器错误！", e);
                }
            }
        }
    }

    public static void injectDialogFields(Dialog dialog) {
        Field[] fields = dialog.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(dialog) != null)
                        continue;
                    Inject viewInject = field.getAnnotation(Inject.class);
                    if (viewInject != null)
                        field.set(dialog, dialog.findViewById(viewInject.value()));
                } catch (NullPointerException e) {
                    LogDebug.e(TAG, "第一个参数为空！", e);
                } catch (IllegalArgumentException e) {
                    LogDebug.e(TAG, "第一个参数不是ui类的一个实例！", e);
                } catch (IllegalAccessException e) {
                    LogDebug.e(TAG, "字段 " + field.getName() + " 类型为 private，不可访问！", e);
                } catch (Exception e) {
                    LogDebug.e(TAG, "初始化行为监听器错误！", e);
                }
            }
        }
    }

    public static void injectFragmentFields(Fragment fragmen, View content) {
        Field[] fields = fragmen.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(fragmen) != null)
                        continue;
                    Inject viewInject = field.getAnnotation(Inject.class);
                    if (viewInject != null)
                        field.set(fragmen, content.findViewById(viewInject.value()));
                } catch (NullPointerException e) {
                    LogDebug.e(TAG, "第一个参数为空！", e);
                } catch (IllegalArgumentException e) {
                    LogDebug.e(TAG, "第一个参数不是ui类的一个实例！", e);
                } catch (IllegalAccessException e) {
                    LogDebug.e(TAG, "字段 " + field.getName() + " 类型为 private，不可访问！", e);
                } catch (Exception e) {
                    LogDebug.e(TAG, "初始化行为监听器错误！", e);
                }
            }
        }
    }

    public static void injectViewFields(View view) {
        Field[] fields = view.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(view) != null)
                        continue;
                    Inject viewInject = field.getAnnotation(Inject.class);
                    if (viewInject != null)
                        field.set(view, view.findViewById(viewInject.value()));
                } catch (NullPointerException e) {
                    LogDebug.e(TAG, "第一个参数为空！", e);
                } catch (IllegalArgumentException e) {
                    LogDebug.e(TAG, "第一个参数不是ui类的一个实例！", e);
                } catch (IllegalAccessException e) {
                    LogDebug.e(TAG, "字段 " + field.getName() + " 类型为 private，不可访问！", e);
                } catch (Exception e) {
                    LogDebug.e(TAG, "初始化行为监听器错误！", e);
                }
            }
        }
    }

    public static void injectObjectFields(Object viewHolder, View convertView) {
        Field[] fields = viewHolder.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(viewHolder) != null)
                        continue;
                    Inject viewInject = field.getAnnotation(Inject.class);
                    if (viewInject != null)
                        field.set(viewHolder, convertView.findViewById(viewInject.value()));
                } catch (NullPointerException e) {
                    LogDebug.e(TAG, "第一个参数为空！", e);
                } catch (IllegalArgumentException e) {
                    LogDebug.e(TAG, "第一个参数不是ui类的一个实例！", e);
                } catch (IllegalAccessException e) {
                    LogDebug.e(TAG, "字段 " + field.getName() + " 类型为 private，不可访问！", e);
                } catch (Exception e) {
                    LogDebug.e(TAG, "初始化行为监听器错误！", e);
                }
            }
        }
    }

}
