package com.marotech.vending.action.security;

import com.github.freva.asciitable.AsciiTable;
import com.marotech.vending.action.BaseActionBean;
import com.marotech.vending.action.RequiresOneRoleOf;
import com.marotech.vending.util.Constants;
import com.marotech.vending.util.FileReader;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@UrlBinding("/web/security")
@RequiresOneRoleOf({Constants.SYS_ADMIN})
public class SecurityActionBean extends BaseActionBean {

    @Getter
    @Setter
    @Validate
    private String type;
    @Getter
    private String content;

    @DefaultHandler
    public Resolution view() throws IOException {
        FileReader fileReader = new FileReader();
        if (StringUtils.isNotBlank(type)) {
            if (type.equals(ROLES)) {
                byte[] bytes = fileReader.readFileFromClasspathAsBytes("roles.xml");
                content = new String(bytes);
                content = StringEscapeUtils.escapeHtml(content);
            } else if (type.equals(FEATURES)) {
                byte[] bytes = fileReader.readFileFromClasspathAsBytes("features.xml");
                content = new String(bytes);
                content = StringEscapeUtils.escapeHtml(content);
            } else if (type.equals(POLICIES)) {
                byte[] bytes = fileReader.readFileFromClasspathAsBytes("security-policy.xml");
                content = new String(bytes);
                content = StringEscapeUtils.escapeHtml(content);
                content = content.replaceAll("&lt;protected-element", "<br/><br/>    &lt;protected-element");
                content = content.replaceAll("&lt;/protected-element", "<br/>    &lt;/protected-element");
            }
            content = content.replaceAll("&lt;feature", "<br/>  &lt;feature");
            content = content.replaceAll("&lt;/features", "<br/>  &lt;/features");
            content = content.replaceAll("&lt;role", "<br/> &lt;role");
            content = content.replaceAll("&lt;/roles", "<br/> &lt;/roles");
            return new ForwardResolution(VIEW_JSP);
        }
        return new ForwardResolution(JSP);
    }

    @HandlesEvent("ascii")
    public Resolution ascii() throws Exception {
        // Step 1: Read the data from a file
        InputStream inputStream = this.getClass().getClassLoader().
                getResourceAsStream("security-policy.xml");
        Document doc = DocumentBuilderFactory.newInstance().
                newDocumentBuilder().parse(inputStream);
        doc.getDocumentElement().normalize();
        NodeList elementNodes = doc.getElementsByTagName("protected-element");

        TreeSet<String> allRolesSet = new TreeSet<>();
        LinkedHashMap<String, Set<String>> permissionMap = new LinkedHashMap<>();

        for (int i = 0; i < elementNodes.getLength(); i++) {
            Element elem = (Element) elementNodes.item(i);
            String elementName = elem.getAttribute("name");

            NodeList roleNodes = elem.getElementsByTagName("role");
            Set<String> roles = new HashSet<>();
            for (int j = 0; j < roleNodes.getLength(); j++) {
                Element roleElem = (Element) roleNodes.item(j);
                String roleName = roleElem.getAttribute("name");
                roles.add(roleName);
                allRolesSet.add(roleName);
            }
            permissionMap.put(elementName, roles);
        }

        String[] headers = new String[allRolesSet.size() + 1];
        headers[0] = "Feature";
        int c = 1;
        for (String role : allRolesSet) {
            headers[c++] = role;
        }

        String[][] data = new String[permissionMap.size()][headers.length];
        int r = 0;
        for (Map.Entry<String, Set<String>> entry : permissionMap.entrySet()) {
            data[r][0] = entry.getKey();
            int col = 1;
            for (String role : allRolesSet) {
                data[r][col++] = entry.getValue().contains(role) ? "X" : "";
            }
            r++;
        }

        // Get ASCII table string using AsciiTable library
        String asciiTable = AsciiTable.getTable(headers, data);

        // Step 2: Convert ASCII string to AWT BufferedImage
        BufferedImage img = asciiToImage(asciiTable);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(img, "png", baos);
        baos.flush();
        byte[] imageInBytes = baos.toByteArray();
        baos.close();

        return new StreamingResolution("image/png",
                new ByteArrayInputStream(imageInBytes));
    }

    @HandlesEvent("image")
    public Resolution image() throws Exception {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("security-policy.xml");
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        doc.getDocumentElement().normalize();

        NodeList elementNodes = doc.getElementsByTagName("protected-element");

        // Collect all unique roles in sorted order
        TreeSet<String> allRoles = new TreeSet<>();
        LinkedHashMap<String, Set<String>> permissionMap = new LinkedHashMap<>();

        for (int i = 0; i < elementNodes.getLength(); i++) {
            Element elem = (Element) elementNodes.item(i);
            String elementName = elem.getAttribute("name");

            NodeList roleNodes = elem.getElementsByTagName("role");
            Set<String> roles = new HashSet<>();
            for (int j = 0; j < roleNodes.getLength(); j++) {
                Element roleElem = (Element) roleNodes.item(j);
                String roleName = roleElem.getAttribute("name");
                roles.add(roleName);
                allRoles.add(roleName);
            }
            permissionMap.put(elementName, roles);
        }

        // PDF setup
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.LETTER);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        float yPosition = yStart;
        float rowHeight = 20;
        float tableBottomY = margin;

        int numColumns = allRoles.size() + 1; // +1 for protected element column
        float colWidth = tableWidth / numColumns;

        // Set fonts
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        // Draw Header Row Background
        contentStream.setNonStrokingColor(200, 200, 200); // light gray
        contentStream.addRect(margin, yPosition - rowHeight, tableWidth, rowHeight);
        contentStream.fill();
        contentStream.setNonStrokingColor(0, 0, 0); // reset to black

        // Draw header text
        float textx = margin + 2;
        float texty = yPosition - 15;
        contentStream.beginText();
        contentStream.newLineAtOffset(textx, texty);
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.showText("Feature");
        contentStream.endText();

        int col = 1;
        for (String role : allRoles) {
            float x = margin + col * colWidth + 2;
            contentStream.beginText();
            contentStream.newLineAtOffset(x, texty);
            contentStream.showText(role);
            contentStream.endText();
            col++;
        }

        yPosition -= rowHeight;

        // Set font for data rows
        contentStream.setFont(PDType1Font.HELVETICA, 11);

        // Draw table rows
        for (Map.Entry<String, Set<String>> entry : permissionMap.entrySet()) {
            if (yPosition < tableBottomY + rowHeight) {
                // Add new page if needed
                contentStream.close();
                page = new PDPage(PDRectangle.LETTER);
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                yPosition = yStart;
            }

            // Draw row background on alternate rows
            if ((permissionMap.keySet().toArray(new String[0]).length % 2) == 0) {
                contentStream.setNonStrokingColor(240, 240, 240);
                contentStream.addRect(margin, yPosition - rowHeight, tableWidth, rowHeight);
                contentStream.fill();
                contentStream.setNonStrokingColor(0, 0, 0);
            }

            // Draw protected element text
            contentStream.beginText();
            contentStream.newLineAtOffset(margin + 2, yPosition - 15);
            contentStream.showText(entry.getKey());
            contentStream.endText();

            // Draw "X" for roles in columns
            col = 1;
            for (String role : allRoles) {
                if (entry.getValue().contains(role)) {
                    float x = margin + col * colWidth + (colWidth / 3);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(x, yPosition - 15);
                    contentStream.showText("X");
                    contentStream.endText();
                }
                col++;
            }

            // Draw horizontal line below row
            contentStream.moveTo(margin, yPosition - rowHeight);
            contentStream.lineTo(margin + tableWidth, yPosition - rowHeight);
            contentStream.stroke();

            yPosition -= rowHeight;
        }

        // Draw vertical lines for columns
        contentStream.moveTo(margin, yStart);
        contentStream.lineTo(margin, yPosition);
        for (int i = 1; i <= numColumns; i++) {
            float x = margin + i * colWidth;
            contentStream.moveTo(x, yStart);
            contentStream.lineTo(x, yPosition);
        }
        contentStream.stroke();

        contentStream.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();

        // Prepare input stream from bytes for StreamingResolution
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return new StreamingResolution("application/pdf", bais);
    }

    private static BufferedImage asciiToImage(String ascii) {
        String[] lines = ascii.split("\n");
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);

        // Calculate image size
        BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = temp.createGraphics();
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = 0;
        for (String line : lines) {
            int lineWidth = fm.stringWidth(line);
            if (lineWidth > width) {
                width = lineWidth;
            }
        }
        int height = fm.getHeight() * lines.length;
        g2d.dispose();

        // Create image
        BufferedImage image = new BufferedImage(width + 10, height + 10, BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(Color.BLACK);

        int y = fm.getAscent() + 5;
        for (String line : lines) {
            g2d.drawString(line, 5, y);
            y += fm.getHeight();
        }

        g2d.dispose();
        return image;
    }


    @Override
    public String getNavSection() {
        return "security";
    }

    public static final String ROLES = "ROLES";
    public static final String POLICIES = "POLICIES";
    public static final String FEATURES = "FEATURES";
    private static final String JSP = "/WEB-INF/jsp/security/list.jsp";
    private static final String VIEW_JSP = "/WEB-INF/jsp/security/view.jsp";
}
