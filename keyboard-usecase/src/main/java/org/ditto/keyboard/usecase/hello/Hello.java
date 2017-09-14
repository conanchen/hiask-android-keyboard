package org.ditto.keyboard.usecase.hello;

import android.content.Context;

/**
 * Created by admin on 2017/8/3.
 */

public class Hello {
    private final  String name;
    private final Context context;

    public Hello(Context context,String name) {
        this.context = context;
        this.name = name;
    }

    public String getName() {
        return name+" got "+context.toString();
    }
}
