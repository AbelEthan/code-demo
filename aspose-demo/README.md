> `Java`实现`Office`转`PDF`工具`Aspose`

- 首先我们先引入响应的`pom`配置(由于默认maven仓库下不下来．文末提供下载地址)
 ```xml
<dependency>
	<groupId>com.aspose</groupId>
	<artifactId>aspose-words</artifactId>
	<version>20.7</version>
	<classifier>jdk17</classifier>
</dependency>
<dependency>
	<groupId>com.aspose</groupId>
	<artifactId>aspose-slides</artifactId>
	<version>20.7</version>
	<classifier>jdk16</classifier>
</dependency>
<dependency>
	<groupId>com.aspose</groupId>
	<artifactId>aspose-cells</artifactId>
	<version>20.7</version>
</dependency>
 ```



> 采用工厂模式进行office系列转换
- 新建抽象类`AbstractFileConvert`
 ```java
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
 ```

- 实现`WORD`文件转换,新建`WordsFileConvert`
 ```java
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
 ```

- 实现`PPT`文件转换,新建`SlidesFileConvert`
 ```java
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
 ```

- 实现`EXCEL`文件转换,新建`CellsFileConvert`
 ```java
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
 ```

- 在`resources`目录下新建`license.xml`文件
 ```xml
 <?xml version="1.0" encoding="UTF-8" ?>
<License>
  <Data>
    <Products>
      <Product>Aspose.Total for Java</Product>
    </Products>
    <EditionType>Enterprise</EditionType>
    <SubscriptionExpiry>20991231</SubscriptionExpiry>
    <LicenseExpiry>20991231</LicenseExpiry>
    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>
  </Data>
  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>
</License>
 ```

- 新建工厂枚举`FileConvertEnum`
```java
public enum FileConvertEnum {
    WORD(new WordsFileConvert()),
    PPT(new SlidesFileConvert()),
    EXCEL(new CellsFileConvert()),
    ;

    private AbstractFileConvert fileConvert;

    FileConvertEnum(AbstractFileConvert fileConvert) {
        this.fileConvert = fileConvert;
    }

    public AbstractFileConvert getFileConvert() {
        return fileConvert;
    }

    public static AbstractFileConvert getFileConvert(String fileFormat){
        FileConvertEnum[] fileConvertEnums = values();
        for (FileConvertEnum fileConvertEnum : fileConvertEnums) {
            if (fileConvertEnum.name().equals(fileFormat)){
                return fileConvertEnum.fileConvert;
            }
        }
        return null;
    }
}
```

- 使用方式
 ```java
public class TestApplication {

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
 ```

> 下载地址

1. [未破解版](https://repository.aspose.com/repo/com/aspose/)

2. 破解版
    1. [aspose-words-20.7](https://download.csdn.net/download/abelethan/18861696)
    2. [aspose-slides-20.7](https://download.csdn.net/download/abelethan/18861763)
    3. [aspose-cells-20.7](https://download.csdn.net/download/abelethan/18861803)
    4. [aspose-20.7](https://download.csdn.net/download/abelethan/18861940)
