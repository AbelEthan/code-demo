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
