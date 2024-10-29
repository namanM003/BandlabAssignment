package com.bandlan.bandlabassignment.utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageTransformer {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    public static ByteArrayInputStream convertToJPG(BufferedImage bufferedImage) throws IOException {
        BufferedImage jpgImage = new BufferedImage(
                WIDTH,
                HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = jpgImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, jpgImage.getWidth(), jpgImage.getHeight());

        g.drawImage(bufferedImage.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(jpgImage, "jpg", byteArrayOutputStream);

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
