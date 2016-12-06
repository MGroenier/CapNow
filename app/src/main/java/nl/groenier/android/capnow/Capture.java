package nl.groenier.android.capnow;

import com.google.gson.annotations.Expose;

/**
 * Created by Martijn on 06/12/2016.
 */

public class Capture {

    @Expose private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Capture{" +
                "name='" + name + '\'' +
                '}';
    }
}
