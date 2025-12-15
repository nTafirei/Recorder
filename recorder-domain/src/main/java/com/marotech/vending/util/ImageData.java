package com.marotech.vending.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ImageData {

    private String hash;
    private String imageId;
    private String address;
    private long timeGenerated;

    ImageData(long timeGenerated, String address, String hash, int imageIndex) {
        super();
        this.hash = hash;
        this.address = address;
        this.timeGenerated = timeGenerated;
        this.imageId = keys.get(imageIndex);
    }

    public String getHash() {
        return hash;
    }

    public String getImageId() {
        return imageId;
    }

    public long getTimeGenerated() {
        return timeGenerated;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "ImageData [hash=" + hash + ", imageId=" + imageId
                + ", address=" + address + ", timeGenerated=" + timeGenerated
                + "]";
    }

    private static final List<String> keys = new ArrayList<>();
    private static final LinkedHashMap<String, String> imageData = new LinkedHashMap<String, String>();

    public static List<ImageData> createImageData(String address) {

        List<ImageData> list = new ArrayList<>();
        long time = System.currentTimeMillis();

        for (int i = 0; i < 9; i++) {
            String hash = MD5.encrypt(address + time + i);
            ImageData data = new ImageData(time, address, hash, i);
            list.add(data);
        }
        return list;
    }

    public static String getRealImagePath(String id) {
        return imageData.get(id);
    }

    static {
        keys.add("key");
        keys.add("flag");
        keys.add("clock");
        keys.add("bug");
        keys.add("pen");
        keys.add("house");
        keys.add("heart");
        keys.add("world");
        keys.add("light bulb");
        keys.add("musical note");
        imageData.put("bug", "16.png");
        imageData.put("pen", "19.png");
        imageData.put("key", "04.png");
        imageData.put("flag", "06.png");
        imageData.put("clock", "15.png");
        imageData.put("heart", "43.png");
        imageData.put("world", "99.png");
        imageData.put("house", "01.png");
        imageData.put("light bulb", "21.png");
        imageData.put("musical note", "40.png");
    }
}
