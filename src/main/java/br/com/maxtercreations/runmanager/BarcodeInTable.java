package br.com.maxtercreations.runmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;

public class BarcodeInTable {
	public static final String DEST = "./target/sandbox/barcodes/barcode_in_table.pdf";

	private static ArrayList<String> numbers = new ArrayList<String>();

	public static void main(String[] args) throws Exception {

		try {
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Gustavo\\Desktop\\rel.txt"));
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					String n = String.format("%06d", Integer.valueOf(line.split(";")[0]));

					sb.append(n);
					numbers.add(n);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				String everything = sb.toString();
				System.out.println(everything);
			} finally {
				br.close();
			}
		} catch (Exception e) {

		}

		File file = new File(DEST);
		file.getParentFile().mkdirs();

		new BarcodeInTable().manipulatePdf(DEST);
	}

	protected void manipulatePdf(String dest) throws Exception {
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
		Document doc = new Document(pdfDoc);

		Table table = new Table(5);

		for (int i = 1; i <= 650; i++) {

			Barcode128 code128 = new Barcode128(pdfDoc);
			code128.setBaseline(-2);
			code128.setSize(8);
			code128.setCode(String.format("%06d", i));
			code128.setCodeType(Barcode128.CODE128);
			
			Image code128Image = new Image(code128.createFormXObject(pdfDoc));
			code128Image.setWidth(80);
			code128Image.setMarginLeft(10);
			code128Image.setMarginBottom(5);

			Cell cell = new Cell().add(code128Image);
			cell.setWidth(100);

			table.addCell(cell);
		}

		doc.add(table);

		doc.close();
	}
}