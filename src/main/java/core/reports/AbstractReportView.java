package core.reports;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class AbstractReportView extends AbstractView {

	protected Font font;
	protected Font courierFont;

	protected Format dateFormat;
	protected Format dateTimeFormat;

	public AbstractReportView() {
		setContentType("application/pdf");

		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		font = FontFactory.getFont(FontFactory.HELVETICA, 9f);
		courierFont = FontFactory.getFont(FontFactory.COURIER, 9f);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ByteArrayOutputStream baos = createTemporaryOutputStream();

		Document document = newDocument();
		PdfWriter writer = newWriter(document, baos);
		prepareWriter(model, writer, request);
		buildReportMetadata(model, document, request);

		document.open();
		buildDocument(model, document, writer, request, response);
		document.close();

		writeToResponse(response, baos);
	}

	protected Font getDefaultFont() {
		return font;
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	protected Document newDocument() {
		return new Document(PageSize.A4);
	}

	protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
		return PdfWriter.getInstance(document, os);
	}

	protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
			throws DocumentException {
		writer.setViewerPreferences(getViewerPreferences());
	}

	protected int getViewerPreferences() {
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}

	protected void buildReportMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
	}

	protected abstract void buildDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

}
