/*
 * Copyright (c) 2023 AbelEthan Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
