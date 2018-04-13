package com.example.stu.yinfengdemo;

import android.text.TextUtils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stu on 2018/3/14.
 */

class XMlHandler<T> extends DefaultHandler {
    String rootElemntName;
    Map<String, String> dataMap;
    StringBuilder stringBuilder;
    List<T> dataList;
    T data;
    Class<T> clz;
    private Map<String, Class> attrs;


    private String currentTag;

    /**
     * 要解析的单个实体的根元素名称
     *
     * @param rootElemntName
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    XMlHandler(String rootElemntName, Class<T> clz, Map<String, Class> attrsMap) {
        this.rootElemntName = rootElemntName;
        this.clz = clz;
        this.attrs = attrsMap;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        dataMap = new HashMap<String, String>();
        stringBuilder = new StringBuilder();
        dataList = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        //赋值给当前标签名称
        currentTag = qName;
        if (rootElemntName.equals(qName)) {
            try {
                data = clz.newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        if (qName.equals("value")) {

            for (int i = 0; i < attributes.getLength(); i++) {
                dataMap.put(i + "", attributes.getValue(i));
            }

        }


        //每次对一个标签解析前，都先置为空
        stringBuilder.setLength(0);
        //如果某个标签中有属性，则将其保存到map中，保存规则：key = “标签名称_属性名称” value = “属性值”
        if (attributes != null && dataMap != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                dataMap.put(qName + "_" + attributes.getQName(i), attributes.getValue(i));
            }

        }


    }

    @Override
    public void characters(char[] ch, int start, int len) throws SAXException {
        super.characters(ch, start, len);
        stringBuilder.append(ch, start, len);
        Field[] fields = clz.getDeclaredFields();
        try {
            if (!TextUtils.isEmpty(currentTag) && data != null) {
                for (Field field : fields) {
                    String name = field.getName();
                    if (currentTag.equals(name)) {
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);
                        Method method = data.getClass().getMethod("set" + name, field.getType());
                        method.invoke(data, stringBuilder.toString());

                    }
                }
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (rootElemntName.equals(qName)) {
            try {
                if (attrs != null) {

                    for (String attrName : attrs.keySet()) {
                        String methodName = "set" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
                        Method method = data.getClass().getMethod(methodName, attrs.get(attrName));
                        method.invoke(data, dataMap.get(attrName));
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            dataList.add(data);
        }

    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }


    public List<T> getDataList() {
        return dataList;
    }

    public T getData() {
        return data;
    }
}