package com.qxm;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @ClassName: {@link ${NAME}}
 * @Author: AbelEthan
 * @Email AbelEthan@126.com
 * @Date ${DATE} ${TIME}
 * @Describes
 */
public class Main {
    public static void main(String[] args) throws Exception {
//        System.out.println(new String(zzYA2(new byte[]{101, 83, 105, 114, 108, 97, 117, 78, 98, 109, 114, 101})));
        license();
        String filePath = "D:\\Users\\Administrator\\Downloads\\学生期末成绩单.docx";
        Document doc = new Document(filePath);
        FileOutputStream os = new FileOutputStream("test.pdf");
        doc.save(os, SaveFormat.PDF);

//        modifyWordsJar();
    }

    /**
     * 修改words jar包里面的校验
     */
    public static void modifyWordsJar() {
        try {
            //这一步是完整的jar包路径,选择自己解压的jar目录
            ClassPool.getDefault().insertClassPath("D:\\apache\\apache-maven-3.6.3\\m2\\repository\\com\\aspose\\aspose-words\\23.6\\aspose-words-23.6-jdk17.jar");
            //获取指定的class文件对象
            CtClass clazz = ClassPool.getDefault().getCtClass("com.aspose.words.zzZnG");
            //从class对象中解析获取指定的方法
            CtMethod[] ctMethods = clazz.getDeclaredMethods("zzbE");
            CtMethod ctMethod = ctMethods[0];
//            for (CtMethod ctMethod : ctMethods) {
//                System.out.println(ctMethod.getLongName());
            ctMethod.setBody("{javax.xml.parsers.DocumentBuilderFactory var1 = com.aspose.words.internal.zzW0z.zzYUd();\n" +
                    "        javax.xml.parsers.DocumentBuilder var2 = var1.newDocumentBuilder();\n" +
                    "        org.w3c.dom.Document var3 = var2.parse($1);\n" +
                    "        org.w3c.dom.Element var4 = var3.getDocumentElement();\n" +
                    "        org.w3c.dom.Element var5 = zzZKA(var4, com.aspose.words.internal.zzZvK.zzW52().zzME(new byte[]{97, 68, 97, 116}));\n" +
                    "        org.w3c.dom.Element var6 = zzZKA(var4, com.aspose.words.internal.zzZvK.zzW52().zzME(new byte[]{105, 83, 110, 103, 116, 97, 114, 117, 101}));\n" +
                    "        if (var5 != null && var6 != null) {\n" +
                    "            com.aspose.words.zzXfP var7 = zzW2d(var5, var6);\n" +
                    "            if (var7.zzTU() == 0) {\n" +
                    "                throw new IllegalStateException(com.aspose.words.zzZUi.zzZWZ(com.aspose.words.zzYdj.zzZkq()));\n" +
                    "            } else if (zzWaf(var7.getData(), var7.zzVYM(), var7.zzYaj())) {\n" +
                    "                throw new IllegalStateException(com.aspose.words.zzZUi.zzZWZ(com.aspose.words.zzYdj.zzYAh()));\n" +
                    "            } else {\n" +
                    "                return var7;\n" +
                    "            }\n" +
                    "        } else {\n" +
                    "            throw new IllegalStateException(com.aspose.words.zzZUi.zzZWZ(com.aspose.words.zzYdj.zzwJ()));\n" +
                    "        }}");
//            }
            //这一步就是将破译完的代码放在桌面上
            clazz.writeFile("C:\\Users\\Administrator\\Desktop\\");
        } catch (Exception e) {
            System.out.println("错误==" + e);
        }
    }

    private static byte[] zzYA2(byte[] var0) {
        if (var0 != null && var0.length != 0) {
            if (var0.length == 1) {
                return var0;
            } else {
                byte[] var1 = new byte[var0.length];
                int var2 = var0.length % 2 == 0 ? var0.length - 1 : var0.length - 2;

                for (int var3 = 0; var3 <= var2; var3 += 2) {
                    var1[var3] = var0[var3 + 1];
                    var1[var3 + 1] = var0[var3];
                }

                if (var2 + 1 < var0.length) {
                    var1[var2 + 1] = var0[var2 + 1];
                }

                return var1;
            }
        } else {
            return null;
        }
    }

    public static void license() throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream is = loader.getResourceAsStream("license.xml");
        License license = new License();
        license.setLicense(is);
    }
}