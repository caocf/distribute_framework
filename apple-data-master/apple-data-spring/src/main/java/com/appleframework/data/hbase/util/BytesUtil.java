package com.appleframework.data.hbase.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * BytesUtil.
 * 
 * @author xinzhi
 */
public class BytesUtil {

    /** Empty bytes. */
    public static final byte[] EMPTY = {};

    /** Bytes [ZERO]. */
    public static final byte[] ZERO  = { (byte) 0 };

    /** Bytes [ONE]. */
    public static final byte[] ONE   = { (byte) 1 };

    /**
     * Increase bytes's last byte by 1.
     * 
     * <pre>
     * if each byte is 0xFF, append byte 0 at tail.
     * </pre>
     * */
    public static byte[] increaseLastByte(byte[] bytes) {
        if (bytes.length == 0) {
            return ZERO;
        }
        byte[] result = new byte[bytes.length];
        System.arraycopy(bytes, 0, result, 0, bytes.length);

        for (int i = result.length - 1; i >= 0; i--) {
            if ((result[i] & 0xFF) != 0xFF) {
                result[i] = (byte) ((result[i] & 0xFF) + 1);
                return result;
            }
        }
        return merge(result, ZERO);
    }

    /**
     * Merge bytes arrays into one bytes array.
     */
    public static byte[] merge(byte[]... bytesArray) {
        byte[] result = new byte[] {};

        if (bytesArray == null) {
            return result;
        }

        for (byte[] bytes : bytesArray) {
            if (bytes == null) {
                continue;
            }
            result = Bytes.add(result, bytes);
        }

        return result;
    }

    /**
     * Sub bytes.
     * */
    public static byte[] subBytes(byte[] bytes, int index, int length) {
        byte[] result = new byte[length];
        System.arraycopy(bytes, index, result, 0, length);
        return result;
    }

    /**
     * p's index of bytes.
     * */
    public static int index(byte[] bytes, byte[] p) {
        for (int i = 0; i + p.length <= bytes.length; i++) {
            boolean match = true;
            for (int j = 0; j < p.length; j++) {
                if (bytes[i + j] != p[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Split bytes on pattern p.
     * */
    public static List<byte[]> split(byte[] bytes, byte[] p) {
        List<byte[]> result = new ArrayList<byte[]>();
        byte[] tem = bytes;
        for (int index = index(tem, p); index != -1; index = index(tem, p)) {
            result.add(subBytes(tem, 0, index));
            tem = subBytes(tem, index + p.length, tem.length - index - p.length);
            if (tem.length == 0) {
                break;
            }
        }
        if (tem.length > 0) {
            result.add(tem);
        }

        return result;
    }

    private BytesUtil() {
    }
}