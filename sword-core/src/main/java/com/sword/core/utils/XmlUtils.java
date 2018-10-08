package com.sword.core.utils;

import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;

public class XmlUtils {

    /**
     * 字符串是否是XML格式
     * @param xml
     * @return
     */
    public static boolean isXml(String xml) {
        try {
            DocumentHelper.parseText(xml);
            return true;
        } catch (DocumentException e) {
            return false;
        }
    }

    /**
     *
     * @param xml
     * @return
     */
    public static Document parse(String xml) {
        try {
            if(isXml(xml)) {
                return DocumentHelper.parseText(xml);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串是否是XML格式
     * @param xml
     * @return
     */
    public static String removeDeclaration(String xml) {
        try {
            Document document = parse(xml);
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setSuppressDeclaration(true);
            StringWriter writer = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(writer, format);
            xmlWriter.write(document);
            xmlWriter.close();
            xml = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }

    /**
     * 格式化XML
     * @param xml
     * @return
     */
    public static String prettyXml(String xml) {
        try {
            Document document = parse(xml);
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setSuppressDeclaration(false);
            format.setEncoding("UTF-8");
            format.setIndent(true);
            format.setExpandEmptyElements(true);
            StringWriter writer = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(writer, format);
            xmlWriter.write(document);
            xmlWriter.close();
            xml = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public static String xmlToJson(String xml) {
        if(isXml(xml)) {
            XMLSerializer xmlSerializer = new XMLSerializer();
            return xmlSerializer.read(xml).toString();
        }
        return null;
    }

    public static String jsonToXml(String json) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setRootName("DigitalBill");
        xmlSerializer.setTypeHintsEnabled(false);
        xmlSerializer.setTypeHintsCompatibility(false);
        return xmlSerializer.write(JSONSerializer.toJSON(json));
    }

    public static void main(String[] args) {
        String xml = "<DigitalBill><a>1</a><b><c>5855555</c></b><d></d></DigitalBill>";
        // String xml = "<DigitalBill><a a1=\"1\" a2=\"2\"></a></DigitalBill>";
        String json = "{\"a\":{\"@a1\":\"1\",\"@a2\":\"2\"}}";
        System.out.println(prettyXml(xml));
        System.out.println(XmlUtils.xmlToJson(xml));
        System.out.println(prettyXml(XmlUtils.jsonToXml(json)));
        XMLSerializer xmlSerializer = new XMLSerializer();
        JsonConfig jsonConfig = new JsonConfig();
        System.out.println(JSONSerializer.toJSON(new Object[]{}, jsonConfig));
    }
}
