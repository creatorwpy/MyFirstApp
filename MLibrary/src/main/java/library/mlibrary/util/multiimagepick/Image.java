package library.mlibrary.util.multiimagepick;

/**
 * Created by harmy on 2016/10/21 0021.
 */
public class Image {
    private String path;
    private String name;
    private long time;
    private boolean selected;

    public Image(String path, String name, long time) {
        this.path = path;
        this.name = name;
        this.time = time;
        this.selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
