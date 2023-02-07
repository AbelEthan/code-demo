package com.qxm;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: {@link WordFileConvert}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/7 14:31
 * @Description word文件转pdf实现类
 */
public class WordFileConvert implements IFileConvert {
    @Override
    public void license(InputStream is) throws Exception {
        License license = new License();
        license.setLicense(is);
    }

    @Override
    public String fileToPdf(String filePath) {
        //去除水印
        if (!getLicense()) {
            return null;
        }
        String pdfPath = null;
        FileOutputStream os = null;
        try {
            pdfPath = getResultPath(filePath, FILE_PDF_SUFFIX);
            os = new FileOutputStream(pdfPath);
            Document doc = new Document(filePath);
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return pdfPath;
    }


}
