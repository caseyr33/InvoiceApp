package controller;


import java.io.FileOutputStream;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;



public class PdfGenerator {
    private static String FILE = "Receipt.pdf";
    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL, BaseColor.RED);
    private static Font small = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
    private static String invoice = "";
    private static String compName = "";
    private static String dateInv = "";
    private static String product = "";

   
    public static void init(String toEmail, String NameCompany, String date, String products, String invoiceNo) {
    	invoice = invoiceNo;
    	compName = NameCompany;
    	dateInv = date;
    	product = products;
    	try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document);
           
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("PDF test");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Daniel Iliev");
        document.addCreator("HankSauce InvoiceApp");
    }

    private static void addTitlePage(Document document)throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Receipt No: " + invoice + " for the date of " + dateInv.substring(0, 11), catFont));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Purchase made by: " + compName, catFont));
        addEmptyLine(preface, 3);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph("The following products were delivered: ", smallBold));
        addEmptyLine(preface, 2);
        preface.add(new Paragraph(product, small));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Total: " + dateInv.substring(11), smallBold));
        addEmptyLine(preface, 3);
        if(InvoiceCreatorController.getPaymentType()==false) {
        	preface.add(new Paragraph("Please pay within 15 days.",redFont));}
        else {
        	preface.add(new Paragraph("Payment has already been received.",small));}
        	
   
        addEmptyLine(preface, 8);
        preface.add(new Paragraph("This document is an official Hank Sauce Receipt." /*Any tempering with this record is punishable by law."*/, redFont));

        

        document.add(preface);
        // Start a new page
        
    }

    

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

	
}