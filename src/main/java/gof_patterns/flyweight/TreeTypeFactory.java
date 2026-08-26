package gof_patterns.flyweight;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TreeTypeFactory {
    private static final Map<String, TreeType> CACHE = new ConcurrentHashMap<>();

    public static TreeType get(String species, Color leafColor) {
        String key = species + "|" + leafColor.getRGB();
        return CACHE.computeIfAbsent(key,
                k -> new TreeType(species, leafColor, loadTexture(species)));
    }

    private static BufferedImage loadTexture(String species) {
        return null;
    }
}