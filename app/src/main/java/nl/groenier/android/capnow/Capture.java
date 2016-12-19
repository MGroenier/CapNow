package nl.groenier.android.capnow;

import com.google.gson.annotations.Expose;

/**
 * Created by Martijn on 06/12/2016.
 */

public class Capture {

    @Expose private String title;
    @Expose private String file_format;
    @Expose private String file_name;
    @Expose private String file_location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile_format() {
        return file_format;
    }

    public void setFile_format(String file_format) {
        this.file_format = file_format;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_location() {
        return file_location;
    }

    public void setFile_location(String file_location) {
        this.file_location = file_location;
    }

    @Override
    public String toString() {
        return "Capture{" +
                "title='" + title + "'," +
                "file_format='" + file_format + "'," +
                "file_name='" + file_name + "'," +
                "file_location='" + file_location + "'" +
                '}';
    }
}
