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
        IFileConvert fileConvert = new WordFileConvert();
        String pdfFilePath = fileConvert.fileToPdf("C:\\Users\\AbelEthan\\IdeaProjects\\code-demo\\aspose-demo\\src\\main\\resources\\testWord.docx");
        System.out.println(System.currentTimeMillis() - startMillis);
        System.out.println(pdfFilePath);
    }
}
