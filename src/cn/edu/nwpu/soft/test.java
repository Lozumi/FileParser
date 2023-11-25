package cn.edu.nwpu.soft;

import org.dom4j.DocumentException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public class test {
    public static void main(String[] args) throws DocumentException, IOException, ParseException, InvocationTargetException, InstantiationException, IllegalAccessException {
        FileLoad fileLoad = new FileLoad("ds.xml",3);
    }
}
