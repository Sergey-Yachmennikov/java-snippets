package gof_patterns.flyweight;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class TreeType {
    private final String species;
    private final Color leafColor;
    private final BufferedImage texture;

    TreeType(String species, Color leafColor, BufferedImage texture) {
        this.species = species;
        this.leafColor = leafColor;
        this.texture = texture;
    }

    // Внешнее состояние приходит параметрами
    public void draw(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, null);
    }
}