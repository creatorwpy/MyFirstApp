package library.mlibrary.util.common;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

/**
 * 主题包换肤工具
 */
public class ReflectionUtil {

    /**
     * 基于包名获取Context实例
     *
     * @param context
     * @param skinPkgName 包名
     * @return
     * @throws NameNotFoundException
     */
    public static Context getSkinContext(Context context, String skinPkgName)
            throws NameNotFoundException {
        return context.createPackageContext(skinPkgName,
                Context.CONTEXT_IGNORE_SECURITY);
    }

    /**
     * @param context
     * @param skinPkgName 包名
     * @param name        资源名称
     * @param defType     资源类型，如color,drawable等
     * @return
     * @throws NameNotFoundException
     */
    public static int getIdentifier(Context context, String skinPkgName,
                                    String name, String defType) throws NameNotFoundException {
        return getSkinContext(context, skinPkgName).getResources()
                .getIdentifier(name, defType, skinPkgName);
    }


    /**
     * 基于getIdentifier对color的封装，便于获取颜色资源文件
     *
     * @param context
     * @param skinPkgName 包名
     * @param name        颜色名称
     * @return
     * @throws NameNotFoundException
     */
    public static int color(Context context, String skinPkgName, String name)
            throws NameNotFoundException {
        return getSkinContext(context, skinPkgName).getResources().getColor(
                getIdentifier(context, skinPkgName, name, "color"));
    }

    /**
     * 基于getIdentifier对dimension的封装，便于获取颜色资源文件
     *
     * @param context
     * @param skinPkgName 包名
     * @param name        dimension的名称
     * @return
     * @throws NameNotFoundException
     */
    public static float dimen(Context context, String skinPkgName, String name)
            throws NameNotFoundException {
        return getSkinContext(context, skinPkgName).getResources()
                .getDimension(
                        getIdentifier(context, skinPkgName, name, "dimen"));
    }

    /**
     * 基于getIdentifier对drawable的封装，便于获取颜色资源文件
     *
     * @param context
     * @param skinPkgName 包名
     * @param name        drawable的名称
     * @return
     * @throws NameNotFoundException
     */
    public static Drawable drawable(Context context, String skinPkgName,
                                    String name) throws NameNotFoundException {
        return getSkinContext(context, skinPkgName).getResources().getDrawable(
                getIdentifier(context, skinPkgName, name, "drawable"));
    }

    /**
     * 基于getIdentifier对string的封装，便于获取颜色资源文件
     *
     * @param context
     * @param skinPkgName 包名
     * @param name        string的名称
     * @return
     * @throws NameNotFoundException
     */
    public static String string(Context context, String skinPkgName, String name)
            throws NameNotFoundException {
        return getSkinContext(context, skinPkgName).getResources().getString(
                getIdentifier(context, skinPkgName, name, "string"));
    }

}