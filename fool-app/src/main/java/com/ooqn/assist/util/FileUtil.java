package com.ooqn.assist.util;

import java.io.File;

public class FileUtil {
    public static File getNewFile(File prent,String fileName){
        File file = new File(prent, fileName);
        if(!file.exists()){
            return file;
        }

        int index=1;
        String extName = cn.hutool.core.io.FileUtil.extName(fileName);
        String name = cn.hutool.core.io.FileUtil.getPrefix(fileName);
        while (true){
            file = new File(prent, name + "(" + index + ")." + extName);
            if(!file.exists()){
                return file;
            }
            index++;
        }
    }
}
