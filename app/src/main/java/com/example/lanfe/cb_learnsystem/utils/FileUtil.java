package com.example.lanfe.cb_learnsystem.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.os.Environment;
import android.util.Log;

public class FileUtil {
    private final static String TAG = "FileUtil";

    //由文件路径得到文件名
    public static String getFileName(String PATH) {
        int start = PATH.lastIndexOf("/");
        int end = PATH.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return PATH.substring(start + 1, end);
        } else {
            return "";
        }
    }

    // 获取当前目录下所有的doc文件
    public static List<String> GetDocFileName(String dir_PATH) {
        List<String> vecFile = new ArrayList<>();
        File file = new File(dir_PATH);
        File[] subFile = file.listFiles();

        for (File aSubFile : subFile) {
            // 判断是否为文件夹
            if (!aSubFile.isDirectory()) {
                String filename = aSubFile.getName();
                // 判断是否为.doc结尾
                if (filename.trim().toLowerCase().endsWith(".doc")) {
                    vecFile.add(filename);
                }
            }
        }
        return vecFile;
    }

    public static String createFile(String file_name) {
        String sdcard_path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String dir_path = String.format("%s/Download/%s", sdcard_path, "html");
        String file_path = String.format("%s/%s", dir_path, file_name);
        try {
            File dirFile = new File(dir_path);
            if (!dirFile.exists()) {
                dirFile.mkdir();
            }
            File myFile = new File(file_path);
            myFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file_path;
    }

    public static ZipEntry getPicEntry(ZipFile docxFile, int pic_index) {
        String entry_jpg = "word/media/image" + pic_index + ".jpeg";
        String entry_png = "word/media/image" + pic_index + ".png";
        String entry_gif = "word/media/image" + pic_index + ".gif";
        String entry_wmf = "word/media/image" + pic_index + ".wmf";
        ZipEntry pic_entry = null;
        pic_entry = docxFile.getEntry(entry_jpg);
        // 以下为读取docx的图片 转化为流数组
        if (pic_entry == null) {
            pic_entry = docxFile.getEntry(entry_png);
        }
        if (pic_entry == null) {
            pic_entry = docxFile.getEntry(entry_gif);
        }
        if (pic_entry == null) {
            pic_entry = docxFile.getEntry(entry_wmf);
        }
        return pic_entry;
    }

    public static byte[] getPictureBytes(ZipFile docxFile, ZipEntry pic_entry) {
        byte[] pictureBytes = null;
        try {
            InputStream pictIS = docxFile.getInputStream(pic_entry);
            ByteArrayOutputStream pOut = new ByteArrayOutputStream();
            byte[] b = new byte[1000];
            int len = 0;
            while ((len = pictIS.read(b)) != -1) {
                pOut.write(b, 0, len);
            }
            pictIS.close();
            pOut.close();
            pictureBytes = pOut.toByteArray();
            Log.d(TAG, "pictureBytes.length=" + pictureBytes.length);
            if (pictIS != null) {
                pictIS.close();
            }
            if (pOut != null) {
                pOut.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pictureBytes;

    }

    public static void writePicture(String pic_path, byte[] pictureBytes) {
        File myPicture = new File(pic_path);
        try {
            FileOutputStream outputPicture = new FileOutputStream(myPicture);
            outputPicture.write(pictureBytes);
            outputPicture.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
