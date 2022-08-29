import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.domain.Cat;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ZipFiileParseTests {

    ClassLoader classLoader = ZipFiileParseTests.class.getClassLoader();
    ClassLoader pdfloader = PDF.class.getClassLoader();

    @DisplayName("Чтение pdf файла")
    @Test
    void zipPDFTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("test.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        ZipFile zfile = new ZipFile(new File("src/test/resources/" + "test.zip"));
        while ((entry = zis.getNextEntry()) != null) {
            String name = entry.getName();
            if (name.endsWith(".pdf")) {
                try (InputStream resourceAsStream = zfile.getInputStream(entry)) {
                    PDF pdf = new PDF(resourceAsStream);
                    assertThat(pdf.numberOfPages).isEqualTo(17);
                    assertThat(pdf.text).contains("Пашагин");
                }
            }
        }
    }

    @DisplayName("Чтение XLS файла")
    @Test
    void zipXLSTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("test.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        ZipFile zfile = new ZipFile(new File("src/test/resources/" + "test.zip"));
        while ((entry = zis.getNextEntry()) != null) {
            String name = entry.getName();
            if (name.endsWith(".xls")) {
                try (InputStream resourceAsStream = zfile.getInputStream(entry)) {
                    XLS xls = new XLS(resourceAsStream);
                    assertThat(xls.excel.getSheetAt(0)
                            .getRow(0)
                            .getCell(0)
                            .getStringCellValue()).isEqualTo("Смета № 94877644 от '20.12.2016'");
                }
            }
        }
    }

    @DisplayName("Чтение DOC файла")
    @Test
    void zipDocTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("test.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        ZipFile zfile = new ZipFile(new File("src/test/resources/" + "test.zip"));
        while ((entry = zis.getNextEntry()) != null) {
            String name = entry.getName();
            if (name.endsWith(".docx")) {
                try (InputStream resourceAsStream = zfile.getInputStream(entry)) {
                    XWPFDocument doc = new XWPFDocument(resourceAsStream);
                    XWPFWordExtractor extract = new XWPFWordExtractor(doc);
                    assertThat(extract.getText()).contains("Selenium, Playwright, nightwatch");
                }
            }
        }
    }

    @Test
    void jsonByJaksonTests() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = classLoader.getResourceAsStream("cat.json");
        Cat jsonObject = mapper.readValue(new InputStreamReader(is), Cat.class);
        assertThat(jsonObject.getName()).isEqualTo("Marusya");
        assertThat(jsonObject.getColor()).isEqualTo("black");
        assertThat(jsonObject.getHasKittens()).isEqualTo(true);
        assertThat(jsonObject.getYear()).isEqualTo(6);
    }
}
