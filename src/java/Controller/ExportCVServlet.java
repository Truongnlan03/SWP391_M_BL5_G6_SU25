package Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import DAOs.CVTemplateDAO;
import Models.CVTemplate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;



@WebServlet(name = "ExportCVServlet", urlPatterns = {"/export-cv"})
public class ExportCVServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect("list-cv");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("list-cv");
            return;
        }

        CVTemplateDAO dao = new CVTemplateDAO();
        CVTemplate cv = dao.getCVById(id);

        if (cv == null) {
            response.sendRedirect("list-cv");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=CV_" + cv.getFullName() + ".pdf");

        try (OutputStream out = response.getOutputStream()) {
            Document document = new Document(PageSize.A4, 40, 40, 50, 40);
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLACK);
            Font sectionTitleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.DARK_GRAY);

            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setWidths(new int[]{1, 3});

            headerTable.addCell(getAvatarCell(cv.getPdfFilePath()));

            PdfPCell infoCell = new PdfPCell();
            infoCell.setBorder(Rectangle.NO_BORDER);
            infoCell.addElement(new Paragraph("Họ và tên: " + safeText(cv.getFullName()), normalFont));
            infoCell.addElement(new Paragraph("Vị trí ứng tuyển: " + safeText(cv.getJobPosition()), normalFont));
            infoCell.addElement(new Paragraph("Ngày sinh: " + (cv.getBirthDate() != null ? cv.getBirthDate().toString() : "Chưa cập nhật"), normalFont));
            infoCell.addElement(new Paragraph("Giới tính: " + safeText(cv.getGender()), normalFont));
            infoCell.addElement(new Paragraph("SĐT: " + safeText(cv.getPhone()), normalFont));
            infoCell.addElement(new Paragraph("Email: " + safeText(cv.getEmail()), normalFont));
            infoCell.addElement(new Paragraph("Website: " + safeText(cv.getWebsite()), normalFont));
            infoCell.addElement(new Paragraph("Địa chỉ: " + safeText(cv.getAddress()), normalFont));
            headerTable.addCell(infoCell);

            document.add(headerTable);
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("MỤC TIÊU NGHỀ NGHIỆP", sectionTitleFont));
            document.add(new Paragraph(safeText(cv.getCareerGoal()), normalFont));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("HỌC VẤN", sectionTitleFont));
            document.add(new Paragraph(safeText(cv.getEducation()), normalFont));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("KINH NGHIỆM LÀM VIỆC", sectionTitleFont));
            document.add(new Paragraph(safeText(cv.getWorkExperience()), normalFont));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("CHỨNG CHỈ", sectionTitleFont));
            document.add(new Paragraph(safeText(cv.getCertificates()), normalFont));

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private PdfPCell getAvatarCell(String imagePath) {
        try {
            if (imagePath != null && !imagePath.isEmpty() && !imagePath.toLowerCase().endsWith(".svg")) {
                Image avatar;
                if (imagePath.startsWith("http")) {
                    avatar = Image.getInstance(URI.create(imagePath).toURL());
                } else {
                    String realPath = getServletContext().getRealPath("/" + imagePath);
                    avatar = Image.getInstance(realPath);
                }

                avatar.scaleAbsolute(120, 120);

                PdfPCell imgCell = new PdfPCell(avatar, true);
                imgCell.setBorder(Rectangle.NO_BORDER);
                imgCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                imgCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                imgCell.setFixedHeight(120);
                return imgCell;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getNoImageCell();
    }

    private PdfPCell getNoImageCell() {
        PdfPCell imgCell = new PdfPCell(new Phrase("No Image"));
        imgCell.setBorder(Rectangle.NO_BORDER);
        return imgCell;
    }

    private String safeText(String text) {
        return (text != null && !text.trim().isEmpty()) ? text : "Chưa cập nhật";
    }
}
