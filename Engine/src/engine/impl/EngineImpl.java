package engine.impl;

import engine.api.Engine;
import engine.jaxb.generated.STLSheet;
import engine.jaxb.parser.STLSheetToSheet;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import sheet.api.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EngineImpl implements Engine {

    private final static String JAXB_XML_GENERATED_PACKAGE_NAME = "engine.jaxb.generated";

    private Sheet sheet;

    private EngineImpl() {}

    public static EngineImpl CreateEngine() {
        return new EngineImpl();
    }

    @Override
    public void ReadXMLInitFile(String filename) {
        try {
            InputStream inputStream = new FileInputStream(new File(filename));
            STLSheet stlSheet = deserializeFrom(inputStream);
            this.sheet = STLSheetToSheet.generate(stlSheet);
            System.out.println();
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ShowSheetStatus() {

    }

    @Override
    public void ShowCellStatus(String cell) {

    }

    @Override
    public void UpdateCellStatus(String cell, String status) {

    }

    @Override
    public void ShowVersions() {

    }

    @Override
    public void ShowVersion(String version) {

    }

    private static STLSheet deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GENERATED_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (STLSheet) unmarshaller.unmarshal(inputStream);
    }
}
