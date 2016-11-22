package library.mlibrary.util.multiimagepick;

import java.io.Serializable;

/**
 * Created by harmy on 2016/10/21 0021.
 */

public class Folder implements Serializable {
    private String name;
    private String path;
    private Image cover;
    private boolean isselected;
    private int count;

    @Override
    public boolean equals(Object o) {
        try {
            Folder other = (Folder) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Image getCover() {
        return cover;
    }

    public void setCover(Image cover) {
        this.cover = cover;
    }

}
