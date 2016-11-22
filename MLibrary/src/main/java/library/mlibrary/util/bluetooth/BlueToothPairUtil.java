package library.mlibrary.util.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Harmy on 2015/9/17 0017.
 */
public class BlueToothPairUtil {
    /**
     * 与设备配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    public static boolean createBond(BluetoothDevice btDevice)
            throws Exception {

        Method createBondMethod = btDevice.getClass().getMethod("createBond");
        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    /**
     * 与设备解除配对 参考源码：platform/packages/apps/Settings.git
     * /Settings/src/com/android/settings/bluetooth/CachedBluetoothDevice.java
     */
    public static boolean removeBond(BluetoothDevice btDevice)
            throws Exception {
        Method removeBondMethod = btDevice.getClass().getMethod("removeBond");
        Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice);
        return returnValue.booleanValue();
    }

    public static boolean setPin(BluetoothDevice btDevice, String str) throws Exception {
        try {
            Method removeBondMethod = btDevice.getClass().getDeclaredMethod("setPin",
                    new Class[]
                            {byte[].class});
            Boolean returnValue = (Boolean) removeBondMethod.invoke(btDevice,
                    new Object[]
                            {str.getBytes()});
            return returnValue.booleanValue();
        } catch (SecurityException e) {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    // 取消用户输入
    public static boolean cancelPairingUserInput(BluetoothDevice device)
            throws Exception {
        Method createBondMethod = device.getClass().getMethod("cancelPairingUserInput");
        cancelBondProcess(device);
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
        return returnValue.booleanValue();
    }

    // 取消配对
    public static boolean cancelBondProcess(BluetoothDevice device)
            throws Exception {
        Method createBondMethod = device.getClass().getMethod("cancelBondProcess");
        Boolean returnValue = (Boolean) createBondMethod.invoke(device);
        return returnValue.booleanValue();
    }

    /**
     * @param clsShow
     */
    public static void printAllInform(Class clsShow) {
        try {
            // 取得所有方法
            Method[] hideMethod = clsShow.getMethods();
            int i = 0;
            for (; i < hideMethod.length; i++) {
            }
            // 取得所有常量
            Field[] allFields = clsShow.getFields();
            for (i = 0; i < allFields.length; i++) {
            }
        } catch (SecurityException e) {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean pair(String strAddr, String strPsw) {
        boolean result = false;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        bluetoothAdapter.cancelDiscovery();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(strAddr);

        if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
            try {
                boolean flag1 = setPin(device, strPsw); // 手机和蓝牙采集器配对
                boolean flag2 = createBond(device);
//                remoteDevice = device; // 配对完毕就把这个设备对象传给全局的remoteDevice
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                removeBond(device);
                //ClsUtils.createBond(device.getClass(), device);
                boolean flag1 = setPin(device, strPsw); // 手机和蓝牙采集器配对
                boolean flag2 = createBond(device);
//                remoteDevice = device; // 如果绑定成功，就直接把这个设备对象传给全局的remoteDevice
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
