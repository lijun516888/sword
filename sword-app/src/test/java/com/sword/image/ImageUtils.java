///*
//package com.sword.image;
//
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import org.xhtmlrenderer.swing.Java2DRenderer;
//import org.xhtmlrenderer.util.FSImageWriter;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//public class ImageUtils {
//
//    public static void main(String[] args) throws Exception {
//        Configuration configuration = new Configuration();
//        configuration.setDirectoryForTemplateLoading(new File("/Users/lijun/Documents/intellij/sword/sword-app/src/test/java/com/sword/image/"));
//        //加载邮件模板并生成邮件正文
//        Map<String, Object> model = Maps.newHashMap();
//        List<Map<String, Object>> ds = Lists.newArrayList();
//        Map<String, Object> d1 = Maps.newHashMap();
//        d1.put("serial", "1");
//        d1.put("bxgs", "1");
//        d1.put("xm", "1");
//        d1.put("onlineAgent", "1");
//        d1.put("tjTotal", "1");
//        d1.put("tjMoney", "1");
//        d1.put("cjTotal", "1");
//        d1.put("cjMoney", "1");
//        d1.put("ytTotal", "1");
//        d1.put("ytMoney", "1");
//        ds.add(d1);
//        model.put("ds", ds);
//        Template t2 = configuration.getTemplate("email.ftl"); // freeMarker template
//        String content2 = FreeMarkerTemplateUtils.processTemplateIntoString(t2, model);
//        int widthImage = 1000;
//        int heightImage = 500;
//        String inputFileName = "aaa.xhtml";
//        String outFileName = "/Users/lijun/Documents/intellij/sword/sword-app/src/test/java/com/sword/image/aaa.jpg";
//        File image= ImageUtils.convertXhtmlToImage(content2,inputFileName,outFileName,widthImage,heightImage);
//    }
//
//    public static File convertXhtmlToImage(String html, String inputFilename, String outputFilename, int widthImage, int heightImage)throws IOException {
//        //将html转成文件
//        FileWriter writer = new FileWriter(inputFilename);
//        writer.write(html);
//        writer.flush();
//        writer.close();
//        //将xhtml文件转成图片
//        final File f = new File(inputFilename);
//        final Java2DRenderer renderer = new Java2DRenderer(f, widthImage, heightImage);
//        final BufferedImage img = renderer.getImage();
//        final FSImageWriter imageWriter = new FSImageWriter();
//        imageWriter.setWriteCompressionQuality(0.9f);
//        imageWriter.write(img, outputFilename);
//        final File fout = new File(outputFilename);
//        return fout;
//    }
//
//}
//*/
