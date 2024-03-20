/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import goods_issue.dataAccess.IssuesDAO;
import goods_issue.dataAccess.StorageDAO;
import goods_issue.dataAccess.UserDAO;
import goods_issue.model.Issues;
import goods_issue.model.Storage;
import goods_issue.model.User;
import java.io.ByteArrayOutputStream;
//import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Trong Huy
 */
public class GenerateIssuesPDFCtrl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {

            UserDAO userDao = new UserDAO();
            String i_id = request.getParameter("id");
            IssuesDAO issuesDao = new IssuesDAO();
            StorageDAO storageDao = new StorageDAO();
            Issues i = issuesDao.getIssuesByIssuesID(i_id);
            User u = userDao.selectById(new User(i.getuId()));
            Storage s = storageDao.selectById(new Storage(i.getsId()));
            User e = userDao.selectById(new User(i.geteId()));
            List<Issues> issuesItems = issuesDao.getAllIssuesDetail(i_id);
            double total = issuesDao.getTotalIssuesPrice(issuesItems);
            
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            // Open the document
            document.open();

//            Image image = Image.getInstance ("src/resources/logo.jpg");//Header Image
//			image.scaleAbsolute(540f, 72f);//image width,height 
            PdfPTable irdTable = new PdfPTable(2);
            irdTable.addCell(getIRDCell("Note No"));
            irdTable.addCell(getIRDCell("Note Date"));
            irdTable.addCell(getIRDCell(i.getiId())); // pass invoice number
            irdTable.addCell(getIRDCell(i.getDate())); // pass invoice date				

            PdfPTable irhTable = new PdfPTable(3);
            irhTable.setWidthPercentage(100);

            irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(getIRHCell("Goods delivery note", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
            irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
            PdfPCell invoiceTable = new PdfPCell(irdTable);
            invoiceTable.setBorder(0);
            irhTable.addCell(invoiceTable);

            FontSelector fs = new FontSelector();
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD);
            fs.addFont(font);
            Phrase bill = fs.process("Bill To"); // customer information
            Paragraph name = new Paragraph(u.getFullName());
            name.setIndentationLeft(20);
            Paragraph contact = new Paragraph(u.getPhone());
            contact.setIndentationLeft(20);
            Paragraph email = new Paragraph(u.getEmail());
            email.setIndentationLeft(20);
            Paragraph address = new Paragraph(u.getDeliveryAddress());
            address.setIndentationLeft(20);

            PdfPTable billTable = new PdfPTable(6); //one page contains 15 records 
            billTable.setWidthPercentage(100);
            billTable.setWidths(new float[]{1, 2, 2, 2, 1, 2});
            billTable.setSpacingBefore(30.0f);
            billTable.addCell(getBillHeaderCell("Index"));
            billTable.addCell(getBillHeaderCell("Item"));
            billTable.addCell(getBillHeaderCell("Origin"));
            billTable.addCell(getBillHeaderCell("Unit Price($)"));
            billTable.addCell(getBillHeaderCell("Quantity"));
            billTable.addCell(getBillHeaderCell("Amount($)"));
            
            int index = 0;
            for(Issues item : issuesItems) {
                index++;
                billTable.addCell(getBillRowCell(String.valueOf(index)));
                billTable.addCell(getBillRowCell(item.getpName()));
                billTable.addCell(getBillRowCell(item.getpOrigin()));
                billTable.addCell(getBillRowCell(String.valueOf(item.getpPrice())));
                billTable.addCell(getBillRowCell(String.valueOf(item.getQty())));
                billTable.addCell(getBillRowCell(String.valueOf(item.getpPrice() + item.getQty())));
            }


//            billTable.addCell(getBillRowCell("2"));
//            billTable.addCell(getBillRowCell("Accessories"));
//            billTable.addCell(getBillRowCell("Nokia Lumia 610 Panel \n Serial:TIN3720 "));
//            billTable.addCell(getBillRowCell("200.0"));
//            billTable.addCell(getBillRowCell("1"));
//            billTable.addCell(getBillRowCell("200.0"));
//
//            billTable.addCell(getBillRowCell("3"));
//            billTable.addCell(getBillRowCell("Other"));
//            billTable.addCell(getBillRowCell("16Gb Memorycard \n Serial:UR8531 "));
//            billTable.addCell(getBillRowCell("420.0"));
//            billTable.addCell(getBillRowCell("1"));
//            billTable.addCell(getBillRowCell("420.0"));
//
//            billTable.addCell(getBillRowCell(" "));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//
//            billTable.addCell(getBillRowCell(" "));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//
//            billTable.addCell(getBillRowCell(" "));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//
//            billTable.addCell(getBillRowCell(" "));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//
//            billTable.addCell(getBillRowCell(" "));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//
//            billTable.addCell(getBillRowCell(" "));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//
//            billTable.addCell(getBillRowCell(" "));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//
//            billTable.addCell(getBillRowCell(" "));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//
//            billTable.addCell(getBillRowCell(" "));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//
//            billTable.addCell(getBillRowCell(" "));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//
//            billTable.addCell(getBillRowCell(" "));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//
//            billTable.addCell(getBillRowCell(" "));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));
//            billTable.addCell(getBillRowCell(""));

            PdfPTable validity = new PdfPTable(1);
            validity.setWidthPercentage(100);
            validity.addCell(getValidityCell(" "));
            validity.addCell(getValidityCell("Warranty"));
            validity.addCell(getValidityCell(" * Products purchased comes with 1 year national warranty \n   (if applicable)"));
            validity.addCell(getValidityCell(" * Warranty should be claimed only from the respective manufactures"));
            PdfPCell summaryL = new PdfPCell(validity);
            summaryL.setColspan(3);
            summaryL.setPadding(1.0f);
            billTable.addCell(summaryL);

            PdfPTable accounts = new PdfPTable(2);
            accounts.setWidthPercentage(100);
            accounts.addCell(getAccountsCell("Subtotal"));
            accounts.addCell(getAccountsCellR(total + ""));
            accounts.addCell(getAccountsCell("Discount (0%)"));
            accounts.addCell(getAccountsCellR("0"));
            accounts.addCell(getAccountsCell("Tax(10%)"));
            accounts.addCell(getAccountsCellR(total * 10 / 100 + ""));
            accounts.addCell(getAccountsCell("Total"));
            accounts.addCell(getAccountsCellR(total + total * 10 / 100 + ""));
            PdfPCell summaryR = new PdfPCell(accounts);
            summaryR.setColspan(3);
            billTable.addCell(summaryR);

            PdfPTable describer = new PdfPTable(1);
            describer.setWidthPercentage(100);
            describer.addCell(getdescCell(" "));
            describer.addCell(getdescCell("Goods once sold will not be taken back or exchanged || Subject to product justification || Product damage no one responsible || "
                    + " Service only at concarned authorized service centers"));

            document.open();//PDF document opened........	

//            document.add(image);
            document.add(irhTable);
            document.add(bill);
            document.add(name);
            document.add(contact);
            document.add(address);
            document.add(email);
            document.add(billTable);
            document.add(describer);

//            document.close();
            // Close the document
            document.close();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=invoice.pdf");
            response.setContentLength(baos.size());
            response.getOutputStream().write(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static void setHeader() {

    }

    public static PdfPCell getIRHCell(String text, int alignment) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
        /*	font.setColor(BaseColor.GRAY);*/
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public static PdfPCell getIRDCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    public static PdfPCell getBillHeaderCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        return cell;
    }

    public static PdfPCell getBillRowCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        return cell;
    }

    public static PdfPCell getBillFooterCell(String text) {
        PdfPCell cell = new PdfPCell(new Paragraph(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        return cell;
    }

    public static PdfPCell getValidityCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorder(0);
        return cell;
    }

    public static PdfPCell getAccountsCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setPadding(5.0f);
        return cell;
    }

    public static PdfPCell getAccountsCellR(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthTop(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(5.0f);
        cell.setPaddingRight(20.0f);
        return cell;
    }

    public static PdfPCell getdescCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        return cell;
    }

}
