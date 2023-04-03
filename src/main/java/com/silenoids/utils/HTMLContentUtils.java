package com.silenoids.utils;

import static j2html.TagCreator.*;

public class HTMLContentUtils {
    public static String getHelpContent() {
        return body(
                h1("Helping you out!"),
                p("wewe bello facimme"),
                img().withSrc(getImgSrc("donateBtn.png")),
                br(),
                img().withSrc(getImgSrc("giftest.gif"))
        ).render();
    }

    private static String getImgSrc(String imgPath) {
        return HTMLContentUtils.class.getClassLoader().getResource(imgPath).toString();
    }

}
