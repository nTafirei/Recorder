package com.marotech.recording.util;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.datatype.Artwork;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class ThumbnailGenerator {

    // Helper method to resize image to thumbnail (common for all visual media)
    private static BufferedImage createThumbnail(BufferedImage original, int width, int height) throws IOException {
        BufferedImage thumb = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = thumb.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(original.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose();
        return thumb;
    }

    // Detect MIME and generate thumbnail
    public static void generateThumbnail(String filePath, String outputPath, int thumbSize) throws Exception {
        String mimeType = Files.probeContentType(Paths.get(filePath));

        switch (mimeType != null ? mimeType : "") {
            case "audio/mpeg":  // MP3
                AudioFile audioFile = AudioFileIO.read(new File(filePath));
                Artwork artwork = audioFile.getTag().getFirstArtwork();
                if (artwork != null) {
                    BufferedImage cover = ImageIO.read(new ByteArrayInputStream(artwork.getBinaryData()));
                    BufferedImage thumb = createThumbnail(cover, thumbSize, thumbSize);
                    ImageIO.write(thumb, "png", new File(outputPath));
                } else {
                    // Default icon logic here
                    createDefaultThumbnail(outputPath, thumbSize);
                }
                break;
            case "video/mp4":
                // Using FFmpeg via ProcessBuilder (requires FFmpeg installed)
                ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-i", filePath, "-ss", "00:00:01", "-vframes", "1", "-y", outputPath);
                pb.start().waitFor();
                break;
            case "image/jpeg":
            case "image/png":
            case "image/gif":
                BufferedImage original = ImageIO.read(new File(filePath));
                BufferedImage thumb = createThumbnail(original, thumbSize, thumbSize);
                String format = mimeType.equals("image/png") ? "png" : "jpg";
                ImageIO.write(thumb, format, new File(outputPath));
                break;
            default:
                throw new IllegalArgumentException("Unsupported MIME: " + mimeType);
        }
    }

    private static void createDefaultThumbnail(String outputPath, int size) throws IOException {
        // Simple colored square as default
        BufferedImage defaultImg = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = defaultImg.createGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, size, size);
        g.dispose();
        ImageIO.write(defaultImg, "png", new File(outputPath));
    }
}
