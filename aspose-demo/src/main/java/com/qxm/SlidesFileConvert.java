package com.qxm;

import com.aspose.slides.License;
import com.aspose.slides.Presentation;
import com.aspose.slides.SaveFormat;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @ClassName: {@link SlidesFileConvert}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/7 16:13
 * @Description PPT文件转换实现类
 */
public class SlidesFileConvert extends AbstractFileConvert {
    @Override
    public void license(InputStream is) throws Exception {
        License license = new License();
        license.setLicense(is);
    }

    @Override
    public void fileToPdf(FileOutputStream os, String filePath) throws Exception {
        Presentation pres = new Presentation(filePath);
        pres.save(os, SaveFormat.Pdf);
    }
}
