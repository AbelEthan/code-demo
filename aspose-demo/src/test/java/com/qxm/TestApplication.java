package com.qxm;

import org.junit.Test;

/**
 * @ClassName: {@link TestApplication}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/7 14:44
 * @Description
 */
public class TestApplication {

    @Test
    public void testWordToPdf() {
        long startMillis = System.currentTimeMillis();
        AbstractFileConvert fileConvert = new WordsFileConvert();
        String pdfFilePath = fileConvert.getResultPath("C:\\Users\\AbelEthan\\IdeaProjects\\code-demo\\aspose-demo\\src\\main\\resources\\testWord.docx");
        System.out.println(System.currentTimeMillis() - startMillis);
        System.out.println(pdfFilePath);
    }

    @Test
    public void testSlidesToPdf(){
        long startMillis = System.currentTimeMillis();
        AbstractFileConvert fileConvert = new SlidesFileConvert();
        String pdfFilePath = fileConvert.getResultPath("C:\\Users\\AbelEthan\\IdeaProjects\\code-demo\\aspose-demo\\src\\main\\resources\\testPpt.pptx");
        System.out.println(System.currentTimeMillis() - startMillis);
        System.out.println(pdfFilePath);
    }

    @Test
    public void testCellsToPdf(){
        long startMillis = System.currentTimeMillis();
        AbstractFileConvert fileConvert = new CellsFileConvert();
        String pdfFilePath = fileConvert.getResultPath("C:\\Users\\AbelEthan\\IdeaProjects\\code-demo\\aspose-demo\\src\\main\\resources\\testExcel.xlsx");
        System.out.println(System.currentTimeMillis() - startMillis);
        System.out.println(pdfFilePath);
    }


}
