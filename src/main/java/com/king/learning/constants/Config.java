package com.king.learning.constants;
import java.io.File;

public class Config{
    
    private Config(){}

    public static String filePath;

    static {
        File f1 = new File("");
        filePath = f1.getAbsolutePath()+"/rule-config/";
    }
};