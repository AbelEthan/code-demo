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
    public void testSlidesToPdf() {
        long startMillis = System.currentTimeMillis();
        AbstractFileConvert fileConvert = new SlidesFileConvert();
        String pdfFilePath = fileConvert.getResultPath("C:\\Users\\AbelEthan\\IdeaProjects\\code-demo\\aspose-demo\\src\\main\\resources\\testPpt.pptx");
        System.out.println(System.currentTimeMillis() - startMillis);
        System.out.println(pdfFilePath);
    }

    @Test
    public void testCellsToPdf() {
        long startMillis = System.currentTimeMillis();
        AbstractFileConvert fileConvert = new CellsFileConvert();
        String pdfFilePath = fileConvert.getResultPath("C:\\Users\\AbelEthan\\IdeaProjects\\code-demo\\aspose-demo\\src\\main\\resources\\testExcel.xlsx");
        System.out.println(System.currentTimeMillis() - startMillis);
        System.out.println(pdfFilePath);
    }

    @Test
    public void testFileConvertEnum() {
        long wordsStartMillis = System.currentTimeMillis();
        AbstractFileConvert wordsFileConvert = FileConvertEnum.getFileConvert("WORD");
        wordsFileConvert.getResultPath("C:\\Users\\AbelEthan\\IdeaProjects\\code-demo\\aspose-demo\\src\\main\\resources\\testWord.docx");
        System.out.println("WORD转换时间：" + (System.currentTimeMillis() - wordsStartMillis));
        long slidesStartMillis = System.currentTimeMillis();
        AbstractFileConvert slidesFileConvert = FileConvertEnum.getFileConvert("PPT");
        slidesFileConvert.getResultPath("C:\\Users\\AbelEthan\\IdeaProjects\\code-demo\\aspose-demo\\src\\main\\resources\\testPpt.pptx");
        System.out.println("PPT转换时间：" + (System.currentTimeMillis() - slidesStartMillis));
        long cellsStartMillis = System.currentTimeMillis();
        AbstractFileConvert cellsFileConvert = FileConvertEnum.getFileConvert("EXCEL");
        cellsFileConvert.getResultPath("C:\\Users\\AbelEthan\\IdeaProjects\\code-demo\\aspose-demo\\src\\main\\resources\\testExcel.xlsx");
        System.out.println("EXCEL转换时间：" + (System.currentTimeMillis() - cellsStartMillis));
    }
}
