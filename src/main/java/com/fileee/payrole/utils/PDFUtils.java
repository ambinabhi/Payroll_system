package com.fileee.payrole.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.fileee.payrole.beans.Payrole;
import com.lowagie.text.DocumentException;

public class PDFUtils {
	
	private static Logger logger = LoggerFactory.getLogger(PDFUtils.class);

	public static void generatePayrolePDF(Payrole payrole) throws IOException, DocumentException {
		String htmlPayrole = parsePayrolefTemplate(payrole);
		generatePdfFromHtml(htmlPayrole, payrole.getStaffId());
	}

	private static void generatePdfFromHtml(String htmlPayrole, Integer staffId) throws IOException, DocumentException {
		
		logger.info("PDF for ID " + staffId);
		
		String filename = "fileee_payroll_" + staffId + ".pdf";
		String outputFolder = System.getProperty("user.home") + File.separator + filename;
		OutputStream outputStream = new FileOutputStream(outputFolder);

		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(htmlPayrole);
		renderer.layout();
		renderer.createPDF(outputStream);

		outputStream.close();

	}

	private static String parsePayrolefTemplate(Payrole payrole) {

		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		logger.info("HTML for ID " + payrole.getStaffId());

		Context context = new Context();
		context.setVariable("empoyee_name", payrole.getStaffName());
		context.setVariable("email", payrole.getEmailId());
		context.setVariable("salary_type", payrole.getSalaryType());
		context.setVariable("duration", payrole.getDuration());
		context.setVariable("working_hours", payrole.getTotalHours());
		context.setVariable("wage", String.valueOf(payrole.getWage()));
		

		return templateEngine.process("payroll_template", context);

	}
}
