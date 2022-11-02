package ru.pasha.pycharmplugin.pojo;

public class Info {

    private Integer fanOut;
    private Integer fanIn;
    private Integer abs;
    private Integer classes;

    public Info() {
        this.fanOut = 0;
        this.fanIn = 0;
        this.abs = 0;
        this.classes = 0;
    }

    public Integer getFanOut() {
        return fanOut;
    }

    public void increaseFanOut() {
        ++this.fanOut;
    }

    public Integer getFanIn() {
        return fanIn;
    }

    public void increaseFanIn() {
        ++this.fanIn;
    }

    public Integer getAbs() {
        return abs;
    }

    public void increaseAbs() {
        ++this.abs;
    }

    public Integer getClasses() {
        return classes;
    }

    public void increaseClasses() {
        ++this.classes;
    }

    @Override
    public String toString() {
        return "Info{" +
                "fanOut=" + fanOut +
                ", fanIn=" + fanIn +
                ", abs=" + abs +
                '}';
    }
}
