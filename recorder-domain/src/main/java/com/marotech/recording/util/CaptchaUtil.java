package com.marotech.recording.util;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Random;

public class CaptchaUtil {

    public static final Random rand = new Random();
    private static final ExpiringMap<String, ImageData> addresses = new ExpiringMap<String, ImageData>(
            300);

    private static final ExpiringMap<String, List<ImageData>> addressData = new ExpiringMap<String, List<ImageData>>(
            300);
    private static final CaptchaUtil instance = new CaptchaUtil();

    private CaptchaUtil() {
    }

    public static CaptchaUtil getInstance() {
        return instance;
    }

    public static ImageData getStoredCaptcha(String address, String hash) {
        if (StringUtils.isBlank(address) || StringUtils.isBlank(hash)) {
            return null;
        }
        List<ImageData> list = addressData.get(address);
        if (list == null) {
            return null;
        }
        for (ImageData data : list) {
            if (data.getHash().equals(hash)) {
                return data;
            }
        }
        return null;
    }

    public static ImageData getSelectedStoredCaptcha(String address, String hash) {
        if (getStoredCaptcha(address, hash) == null) {
            return null;
        }
        return addresses.get(hash);
    }

    public static String storeCaptcha(String address, int pos) {
        List<ImageData> list = addressData.get(address);
        if (list == null) {
            createCaptcha(address);
            list = addressData.get(address);
        }
        String currHash = list.get(pos).getHash();
        addresses.put(currHash, list.get(pos));
        return list.get(pos).getImageId();
    }

    public static List<ImageData> createCaptcha(String address) {
        List<ImageData> list = addressData.get(address);
        if (list == null) {
            list = ImageData.createImageData(address);
            addressData.put(address, list);
        }
        return list;
    }
}
