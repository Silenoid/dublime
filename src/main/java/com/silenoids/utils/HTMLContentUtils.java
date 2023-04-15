package com.silenoids.utils;

import static j2html.TagCreator.*;

public class HTMLContentUtils {
    public static String getHelpContent() {
        return body(
                h1("Helping you out!"),
                    h2("cioè aspe!"),
                        h3("ma quindi!"),
                        code("questo è un codice"),
                        br(),
                        p("wewe bello facimme"),
                        div(),
                        img().withSrc(getImgSrc("donateBtn.png"))
        ).render();
    }

    private static String getImgSrc(String imgPath) {
        return HTMLContentUtils.class.getClassLoader().getResource(imgPath).toString();
    }

}
