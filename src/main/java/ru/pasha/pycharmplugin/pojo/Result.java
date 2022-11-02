package ru.pasha.pycharmplugin.pojo;

import com.intellij.openapi.vfs.VirtualFile;

import java.util.Map;

public class Result {

    private VirtualFile file;
    private Map<Float, Float> cord;

    public Result() {
    }

    public VirtualFile getFile() {
        return file;
    }

    public void setFile(VirtualFile file) {
        this.file = file;
    }

    public Map<Float, Float> getCord() {
        return cord;
    }

    public void setCord(Map<Float, Float> cord) {
        this.cord = cord;
    }

    @Override
    public String toString() {
        return "Result{" +
                "file=" + file +
                ", cord=" + cord +
                '}';
    }
}
