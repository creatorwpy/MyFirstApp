package library.mlibrary.util.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.Set;

/**
 * Created by Harmy on 2015/9/16 0016.
 */
public class BlueToothUtil {
    /**
     * 蓝牙是否打开
     *
     * @return
     */
    public static boolean isOpen() {
        return BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    /**
     * 打开蓝牙
     *
     * @return
     */
    public static boolean openBlueTooth() {
        return BluetoothAdapter.getDefaultAdapter().enable();
    }

    /**
     * 关闭蓝牙
     *
     * @return
     */
    public static boolean closeBlueTooth() {
        return BluetoothAdapter.getDefaultAdapter().disable();
    }

    /**
     * 搜索设备
     *
     * @return
     */
    public static boolean searchDevice() {
        return BluetoothAdapter.getDefaultAdapter().startDiscovery();
    }

    /**
     * 停止搜索设备
     *
     * @return
     */
    public static boolean stopSearch() {
        return BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
    }

    /**
     * 正在搜索设备
     *
     * @return
     */
    public static boolean isSearching() {
        return BluetoothAdapter.getDefaultAdapter().isDiscovering();
    }

    /**
     * 获取已经配对的设备
     *
     * @return
     */
    public static Set<BluetoothDevice> getBondedDevices() {
        return BluetoothAdapter.getDefaultAdapter().getBondedDevices();
    }
}
