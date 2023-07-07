package utils;

import java.lang.Byte;

public class FileUpload {
    
    String nameFile;
    String path;
    byte[] tabByte;

    public FileUpload()
    {

    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getTabByte() {
        return tabByte;
    }

    public void setTabByte(byte[] tabByte) {
        this.tabByte = tabByte;
    }

}
