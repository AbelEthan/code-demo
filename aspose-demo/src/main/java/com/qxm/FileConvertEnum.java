package com.qxm;

/**
 * @ClassName: {@link FileConvertEnum}
 * @Author AbelEthan
 * @Email AbelEthan@126.com
 * @Date 2023/2/7 16:45
 * @Description
 */
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
