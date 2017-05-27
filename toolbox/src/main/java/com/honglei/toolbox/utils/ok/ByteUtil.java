package com.honglei.toolbox.utils.ok;

import java.io.*;


/*******
 * 字节工具类
 * <ul>
 * <li>{@link #byte2Object(byte[] bytes)  ｝</li>byte[] 转为 对象
 * <li>{@link #object2Byte(Object obj)  ｝</li>对象 转为 byte[]
 * <li>{@link #byte2Bit(byte[] bytes)  ｝</li>byte 转为bit
 * </ul>
 ****/
public class ByteUtil {
    /**
     * byte[] 转为 对象
     *
     * @param bytes
     * @return
     */
    public static Object byte2Object(byte[] bytes) {
        ObjectInputStream ois = null;
        try {
            ois = null;
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (ois != null) try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 对象 转为 byte[]
     *
     * @param obj
     * @return
     */
    public static byte[] object2Byte(Object obj) {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }return null;
    }

    public static String byte2Bit(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Byte.SIZE * bytes.length; i++)
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

}
