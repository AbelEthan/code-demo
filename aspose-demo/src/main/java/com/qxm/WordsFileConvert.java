package com.qxm;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @ClassName: {@link WordsFileConvert}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/7 14:31
 * @Description WORD文件转换实现类
 */
public class WordsFileConvert extends AbstractFileConvert {
    @Override
    public void license(InputStream is) throws Exception {
        License license = new License();
        license.setLicense(is);
    }

    @Override
    public void fileToPdf(FileOutputStream os, String filePath) throws Exception {
        Document doc = new Document(filePath);
        doc.save(os, SaveFormat.PDF);
    }
}
