package com.qxm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: {@link AbstractFileConvert}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/7 14:27
 * @Description 文件转换接口
 */
public abstract class AbstractFileConvert {

    /**
     * pdf文件后缀
     */
    private static final String FILE_PDF_SUFFIX = ".pdf";

    /**
     * 获取文件全路径
     *
     * @param filePath
     * @param fileSuffix
     * @return
     */
    private String getSuffixPath(String filePath, String fileSuffix) {
        int i = filePath.lastIndexOf(".");
        String path = filePath.substring(0, i);
        return path + fileSuffix;
    }

    /**
     * 获取许可证
     *
     * @return
     */
    private boolean getLicense() {
        boolean result = false;
        InputStream is = null;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            is = loader.getResourceAsStream("license.xml");
            license(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 获取文件转换后地址
     *
     * @param filePath
     * @return
     */
    public String getResultPath(String filePath) {
        if (!getLicense()) {
            return null;
        }
        String pdfPath = null;
        FileOutputStream os = null;
        try {
            pdfPath = getSuffixPath(filePath, FILE_PDF_SUFFIX);
            os = new FileOutputStream(pdfPath);
            fileToPdf(os, filePath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return pdfPath;
    }

    /**
     * 验证许可证
     *
     * @param is
     * @throws Exception
     */
    public abstract void license(InputStream is) throws Exception;

    /**
     * 文件转为pdf
     *
     * @param os
     * @param filePath
     * @throws Exception
     */
    public abstract void fileToPdf(FileOutputStream os, String filePath) throws Exception;

}
