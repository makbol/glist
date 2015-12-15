/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.util;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author uriel
 */
public class ColorRandom {
    private final ThreadLocalRandom random;

    public ColorRandom() {
        this.random = ThreadLocalRandom.current();
    }

    public String nextColor() {
        Color c = Color.getHSBColor(random.nextFloat(), 1.0f, 1.0f);
        return Integer.toHexString(c.getRGB());
    }


}
