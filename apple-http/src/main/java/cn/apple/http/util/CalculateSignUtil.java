package cn.afterturn.http.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.afterturn.http.entity.enums.SignTypeEnmu;
import cn.afterturn.http.entity.params.ParamsEntity;

/**
 * 提供计算签名的方法
 * 
 * @author JueYue
 * @date 2014年10月14日 下午3:27:46
 */
public final class CalculateSignUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateSignUtil.class);

    public static class ASEComparator implements Comparator<ParamsEntity> {

        @Override
        public int compare(ParamsEntity prve, ParamsEntity next) {
            return prve.getOrder() - next.getOrder();
        }

    }

    public static class ASENameComparator implements Comparator<ParamsEntity> {

        @Override
        public int compare(ParamsEntity prve, ParamsEntity next) {
            return prve.getName().compareTo(next.getName());
        }

    }

    public static class DESCNameComparator implements Comparator<ParamsEntity> {

        @Override
        public int compare(ParamsEntity prve, ParamsEntity next) {
            return next.getName().compareTo(prve.getName());
        }

    }

    private static final Logger logger = LoggerFactory.getLogger(CalculateSignUtil.class);

    public static String getSignMD5(String sign) {
        logger.debug("计算签名参数{}", sign);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(sign.getBytes());
            byte[] b = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 计算签名
     * 
     * @param sign
     * @param list
     * @return
     */
    public static String signCal(SignTypeEnmu sign, String defaultKey, List<ParamsEntity> list) {
        switch (sign) {
            case MD5:
                Collections.sort(list, new ASENameComparator());
                return signCalKeyAndName(defaultKey, list, false);
            case DESC_MD5:
                Collections.sort(list, new DESCNameComparator());
                return signCalKeyAndName(defaultKey, list, false);
            case ORDER_MD5:
                Collections.sort(list, new ASEComparator());
                return signCalKeyAndName(defaultKey, list, false);
            case UPPER_MD5:
                Collections.sort(list, new ASENameComparator());
                return signCalKeyAndName(defaultKey, list, true);
            case UPPER_DESC_MD5:
                Collections.sort(list, new DESCNameComparator());
                return signCalKeyAndName(defaultKey, list, true);
            case UPPER_ORDER_MD5:
                Collections.sort(list, new ASEComparator());
                return signCalKeyAndName(defaultKey, list, true);
            case KEY_MD5:
                Collections.sort(list, new ASENameComparator());
                return signCalKey(defaultKey, list, false);
            case KEY_DESC_MD5:
                Collections.sort(list, new DESCNameComparator());
                return signCalKey(defaultKey, list, false);
            case KEY_ORDER_MD5:
                Collections.sort(list, new ASEComparator());
                return signCalKey(defaultKey, list, false);
            case KEY_UPPER_MD5:
                Collections.sort(list, new ASENameComparator());
                return signCalKey(defaultKey, list, true);
            case KEY_UPPER_DESC_MD5:
                Collections.sort(list, new DESCNameComparator());
                return signCalKey(defaultKey, list, true);
            case KEY_UPPER_ORDER_MD5:
                Collections.sort(list, new ASEComparator());
                return signCalKey(defaultKey, list, true);
            default:
                break;
        }
        return "";
    }

    private static String signCalKey(String defaultKey, List<ParamsEntity> list, boolean isUpper) {
        StringBuilder sb = new StringBuilder();
        ParamsEntity entity;
        for (int i = 0; i < list.size(); i++) {
            entity = list.get(i);
            if (entity.isSign()) {
                sb.append(entity.getValue());
            }
        }
        if (StringUtils.isNotEmpty(defaultKey)) {
            sb.append(defaultKey);
        }
        return isUpper ? getSignMD5(sb.toString()).toUpperCase() : getSignMD5(sb.toString());
    }

    private static String signCalKeyAndName(String defaultKey, List<ParamsEntity> list,
                                            boolean isUpper) {
        StringBuilder sb = new StringBuilder();
        ParamsEntity entity;
        for (int i = 0; i < list.size(); i++) {
            entity = list.get(i);
            if (entity.isSign()) {
                sb.append(entity.getName());
                sb.append("=");
                sb.append(entity.getValue());
            }
        }
        if (StringUtils.isNotEmpty(defaultKey)) {
            sb.append(defaultKey);
        }
        return isUpper ? getSignMD5(sb.toString()).toUpperCase() : getSignMD5(sb.toString());
    }

}
