package com.qxm;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: {@link IFileConvert}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/7 14:27
 * @Description 文件转换接口
 */
public interface IFileConvert {

    /**
     * pdf文件后缀
     */
    String FILE_PDF_SUFFIX = ".pdf";

    /**
     * 获取文件全路径
     *
     * @param filePath
     * @param fileSuffix
     * @return
     */
    default String getResultPath(String filePath, String fileSuffix) {
        int i = filePath.lastIndexOf(".");
        String path = filePath.substring(0, i);
        return path + fileSuffix;
    }

    /**
     * 获取许可证
     *
     * @return
     */
    default boolean getLicense(){
        boolean result = false;
        InputStream is = null;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            is = loader.getResourceAsStream("license.xml");
            license(is);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (is != null){
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
     * 验证许可证
     *
     * @param is
     * @throws Exception
     */
    void license(InputStream is) throws Exception;

    /**
     * 文件转为pdf
     *
     * @param filePath
     * @return
     */
    String fileToPdf(String filePath);

}
