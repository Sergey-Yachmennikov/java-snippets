package gof_patterns.flyweight;

import java.awt.*;
import java.awt.image.BufferedImage;


class Tree {
    private final int x, y;              // уникально
    private final String species;        // "берёза" — повторяется миллион раз

    public Tree(int x, int y, String species, Color leafColor, BufferedImage texture) {
        this.x = x;
        this.y = y;
        this.species = species;
        this.leafColor = leafColor;
        this.texture = texture;
    }

    private final Color leafColor;       // повторяется
    private final BufferedImage texture; // 2 МБ, повторяется!!

}