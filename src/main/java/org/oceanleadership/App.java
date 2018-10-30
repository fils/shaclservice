package org.oceanleadership;

import org.apache.jena.rdf.model.Model;
// import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
// import org.apache.jena.riot.RDFFormat;
import org.topbraid.jenax.util.JenaUtil;
import org.topbraid.shacl.util.ModelPrinter;
import org.topbraid.shacl.validation.ValidationUtil;
import java.io.FileInputStream;
import java.io.File;
import io.javalin.Javalin;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;

public class App {
	public static void main(String[] args) {
		Javalin app = Javalin.create().start(7000);

		app.get("/", ctx -> ctx.html("<div>SHACL convertion requires POST (ref swagger docs)</div>"));

		app.post("/", ctx -> {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>  Starting SHACL request <<<<<<<<<<<<<<<<<<<<<<<<<<<");
		

			File f = File.createTempFile("prefix-", "-suffix");
			f.createNewFile();
			f.deleteOnExit();
			FileInputStream fis = FileUtils.openInputStream(f);

			File f2 = File.createTempFile("prefix-", "-suffix");
			f2.createNewFile();
			f2.deleteOnExit();
			FileInputStream fis2 = FileUtils.openInputStream(f2);

			Model dataModel = JenaUtil.createMemoryModel();
			Model shapesModel = JenaUtil.createMemoryModel();

			ctx.uploadedFiles("datag").forEach(file -> {
				try {
					FileUtils.copyInputStreamToFile(file.getContent(), f);
					dataModel.read(fis, "urn:dummy", org.apache.jena.util.FileUtils.langTurtle);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			ctx.uploadedFiles("shapeg").forEach(file -> {
				try {
					FileUtils.copyInputStreamToFile(file.getContent(), f2);
					shapesModel.read(fis2, "urn:dummy", org.apache.jena.util.FileUtils.langTurtle);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			//String dref = ctx.formParam("dataref");
			//String sref = ctx.formParam("shaperef");
			String dref = "dataref";
			String sref = "shaperef";

			System.out.println("----> " + dref);
			System.out.println("----> " + sref);
			// options
			// The blank node for each sh:ValidationRecord comes from the constructor of
			// ValidationEngine,
			// which sets this.report to be reportModel.createResource(...) unless it is
			// passed in.
			// So you could call this constructor via the ValidationEngineFactory with an
			// already given
			// URI resource if you want to assign it a URI.

			// Process the report
			System.out.println("Processing data and shape graphs");
			Resource report = ValidationUtil.validateModel(dataModel, shapesModel, false);

			System.out.println("Adding data and shape graph references to SHACL output graph");
			// String BASE = "http://datafacility.org/";
			Model model = org.apache.jena.rdf.model.ModelFactory.createDefaultModel();
			// model.setNsPrefix("", BASE);
			// Resource r1 = model.createResource(BASE + "r1");
			Property p1 = model.createProperty("http://www.w3.org/2004/02/skos/core#related");
			// Property p2 = model.createProperty(BASE + "p2");
			RDFNode v1 = model.createLiteral(dref);
			RDFNode v2 = model.createLiteral(sref);
			// RDFNode v1 =
			// model.createTypedLiteral("http://opencoredata.org/id/dataset/XYZ",
			// org.apache.jena.datatypes.xsd.XSDDatatype.XSDstring);
			// RDFNode v2 = model.createTypedLiteral("2",
			// org.apache.jena.datatypes.xsd.XSDDatatype.XSDinteger);

			// r1.addProperty(p1, v1).addProperty(p1, v2);
			report.addProperty(p1, v1).addProperty(p1, v2);

			// option, add metadata to the report object that holds my associated URI.

			// System.out.println(ModelPrinter.get().print(report.getModel()));
			System.out.println("Done, returning ctx");
			System.out.println(" ");
			System.out.println(" ");
			
			ctx.result(ModelPrinter.get().print(report.getModel()));

		});
	}
}
