package com.example.stu.yinfengdemo;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.util.Xml;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Stu on 2018/3/14.
 */

public class XmlUtils {
    /**
     * @param xml            要解析的xml数据
     * @param rootElemntName 要解析的内容的根标签名称
     * @param clz            要转换成的实体类，
     * @param attrs          key值为要解析的xml标签中的属性值， value 值为属性值类型
     * @return
     * @author 郑明亮
     * @Email zhengmingliang911@gmail.com
     * @Time 2017年4月9日 下午6:32:02
     * @Description <p> 将xml转换为指定对象 </P>
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public <T> List<T> parseXml2Bean(String xml, String rootElemntName, Class<T> clz, Map<String, Class> attrs) {

        XMlHandler<T> handler = null;
        try (ByteArrayInputStream stream = new ByteArrayInputStream(xml.getBytes());) {
            handler = new XMlHandler<>(rootElemntName, clz, attrs);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(stream, handler);
//            System.out.println(handler.getDataList());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return handler.getDataList();
    }

    public InputStream getXml() {
        /*异常*/
        String url2 = "http://api.znjtys.com:80/files/ecm/1132573/20180313225636146452.xml";
        String url = "https://api.znjtys.com/files/ecm/36187/20170415205959605208.xml";
        OkHttpClient mHttpClient = new OkHttpClient();
        String json = "";
        InputStream is = null;
        Request request = new Request.Builder().url(url).build();
        okhttp3.Response response = null;
        try {

            response = mHttpClient.newCall(request).execute();
            json = response.body().string();
            is = response.body().byteStream();
            return is;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;

    }

    public String getXmls() {
        String url2 = "http://api.znjtys.com:80/files/ecm/1132573/20180313225636146452.xml";
        String url = "https://api.znjtys.com/files/ecm/36187/20170415205959605208.xml";
        String url3 = "http://api.znjtys.com:80/files/ecm/1124341/20180302194036844835.xml";
        String url4 = "http://api.znjtys.com:80/files/ecm/1124363/20180302195222602701.xml";
        String url5 = "http://api.znjtys.com:80/files/ecm/1124411/20180302201903230167.xml";
        String url6 ="http://api.znjtys.com:80/files/ecm/1124353/20180302200004410939.xml";
        OkHttpClient mHttpClient = new OkHttpClient();
        String json = "";
        //InputStream is = null;
        Request request = new Request.Builder().url(url5).build();
        okhttp3.Response response = null;
        try {

            response = mHttpClient.newCall(request).execute();
            json = response.body().string();
            //is = response.body().byteStream();
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;

    }

    public InputStream getInputStream() {
        String url2 = "http://api.znjtys.com:80/files/ecm/1132573/20180313225636146452.xml";
        String url = "https://api.znjtys.com/files/ecm/36187/20170415205959605208.xml";
        OkHttpClient mHttpClient = new OkHttpClient();
        String json = "";
        InputStream is = null;
        Request request = new Request.Builder().url(url).build();
        okhttp3.Response response = null;
        try {

            response = mHttpClient.newCall(request).execute();
            is = response.body().byteStream();
            return is;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;

    }

    public short[] parseAECGRecord(InputStream stream) {
        short[] data = null;
        XmlPullParser xmlParse = Xml.newPullParser();

        try {
            xmlParse.setInput(stream, "utf-8");
            int evnType = xmlParse.getEventType();

            for (byte dataOffset = 0; evnType != 1; evnType = xmlParse.next()) {
                switch (evnType) {
                    case 2:
                        String tag = xmlParse.getName();
                        String value;
                        if (tag.equalsIgnoreCase("code")) {
                            evnType = xmlParse.next();
                            value = xmlParse.getAttributeValue(0);
                            if (value.equalsIgnoreCase("MDC_ECG_LEAD_I")) {
                                dataOffset = 0;
                            } else if (value.equalsIgnoreCase("MDC_ECG_LEAD_II")) {
                                dataOffset = 1;
                            } else if (value.equalsIgnoreCase("MDC_ECG_LEAD_III")) {
                                dataOffset = 2;
                            } else if (value.equalsIgnoreCase("MDC_ECG_LEAD_AVR")) {
                                dataOffset = 3;
                            } else if (value.equalsIgnoreCase("MDC_ECG_LEAD_AVL")) {
                                dataOffset = 4;
                            } else if (value.equalsIgnoreCase("MDC_ECG_LEAD_AVF")) {
                                dataOffset = 5;
                            } else if (value.equalsIgnoreCase("MDC_ECG_LEAD_V1")) {
                                dataOffset = 6;
                            } else if (value.equalsIgnoreCase("MDC_ECG_LEAD_V2")) {
                                dataOffset = 7;
                            } else if (value.equalsIgnoreCase("MDC_ECG_LEAD_V3")) {
                                dataOffset = 8;
                            } else if (value.equalsIgnoreCase("MDC_ECG_LEAD_V4")) {
                                dataOffset = 9;
                            } else if (value.equalsIgnoreCase("MDC_ECG_LEAD_V5")) {
                                dataOffset = 10;
                            } else if (value.equalsIgnoreCase("MDC_ECG_LEAD_V6")) {
                                dataOffset = 11;
                            }
                        } else if (tag.equalsIgnoreCase("digits")) {
                            evnType = xmlParse.next();
                            value = xmlParse.getText();
                            if (value != null) {
                                String[] array = value.split(" ");
                                if (array.length > 0) {
                                    if (dataOffset == 0) {
                                        data = new short[array.length * 12];
                                    }

                                    for (int i = 0; i < array.length; ++i) {
                                        data[12 * i + dataOffset] = Short.parseShort(array[i]);
                                    }
                                }
                            }
                        }
                    case 3:
                }
            }
        } catch (Exception var10) {
        }

        return data;
    }
}
