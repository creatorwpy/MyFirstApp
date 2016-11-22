package com.mycompany.util.common;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Harmy on 2015/11/11 0011.
 */
public class GzipUtil {
    public static void compress(InputStream is, OutputStream os)
            throws Exception {

        GZIPOutputStream gos = new GZIPOutputStream(os);

        int count;
        byte data[] = new byte[1024];
        while ((count = is.read(data, 0, 1024)) != -1) {
            gos.write(data, 0, count);
        }
        gos.finish();
        gos.flush();
        gos.close();
    }

    public static void decompress(InputStream is, OutputStream os)
            throws Exception {

        GZIPInputStream gis = new GZIPInputStream(is);

        int count;
        byte data[] = new byte[1024];
        while ((count = gis.read(data, 0, 1024)) != -1) {
            os.write(data, 0, count);
        }
        gis.close();
    }

}
