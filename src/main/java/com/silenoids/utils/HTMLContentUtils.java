package com.silenoids.utils;

public class HTMLContentUtils {
    public static String getHelpContent() {
        // @formatter: off
        return
"""
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>Helping you out</h1>
<p>wewe bello facimme</p>
""" +
img("donateBtn.png") +
img("giftest.gif")
+ """
</body>
</html>
""";
        // @formatter: on
    }

    private static String img(String imgPath) {
        System.out.println(HTMLContentUtils.class.getClassLoader().getResource(imgPath));
        return "<center><img src=\"" +
                HTMLContentUtils.class.getClassLoader().getResource(imgPath)
                + "\" ></center>";
    }

}
