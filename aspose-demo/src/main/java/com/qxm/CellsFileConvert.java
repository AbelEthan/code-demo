package com.qxm;

import com.aspose.cells.License;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @ClassName: {@link CellsFileConvert}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/7 16:32
 * @Description EXCEL文件转换实现类
 */
public class CellsFileConvert extends AbstractFileConvert {
    @Override
    public void license(InputStream is) throws Exception {
        License license = new License();
        license.setLicense(is);
    }

    @Override
    public void fileToPdf(FileOutputStream os, String filePath) throws Exception {
        Workbook workbook = new Workbook(filePath);
        workbook.save(os, SaveFormat.PDF);
    }
}
