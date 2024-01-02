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
