package org.abego.treelayout.demo.svg;

import java.io.*;

public class SVGUtil
{

    public SVGUtil()
    {
    }

    public static String svg(String width, String height, String content)
    {
        return String.format("<svg width=\"%s\" height=\"%s\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink" +
"=\"http://www.w3.org/1999/xlink\">\n%s</svg>\n"
, new Object[] {
            width, height, content
        });
    }

    public static String svg(Number width, Number height, String content)
    {
        return svg(Integer.toString(width.intValue()), Integer.toString(height.intValue()), content);
    }

    public static String svg(String x, String y, String width, String height, String content)
    {
        return String.format("<svg x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" xmlns=\"http://www.w3.org/2000" +
"/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n%s</svg>\n"
, new Object[] {
            x, y, width, height, content
        });
    }

    public static String svg(Number x, Number y, Number width, Number height, String content)
    {
        return svg(Integer.toString(x.intValue()), Integer.toString(y.intValue()), Integer.toString(width.intValue()), Integer.toString(height.intValue()), content);
    }

    public static String rect(String x, String y, String width, String height, String style, String extraAttributes)
    {
        return String.format("<rect x=\"%s\" y=\"%s\" width=\"%s\" height=\"%s\" style=\"%s\" %s/>\n", new Object[] {
            x, y, width, height, style, extraAttributes
        });
    }

    public static String rect(String x, String y, String width, String height, String style)
    {
        return rect(x, y, width, height, style, "");
    }

    public static String rect(Number x, Number y, Number width, Number height, String style, String extraAttributes)
    {
        return rect(Integer.toString(x.intValue()), Integer.toString(y.intValue()), Integer.toString(width.intValue()), Integer.toString(height.intValue()), style, extraAttributes);
    }

    public static String rect(Number x, Number y, Number width, Number height, String style)
    {
        return rect(x, y, width, height, style, "");
    }

    public static String line(String x1, String y1, String x2, String y2, String style)
    {
        return String.format("<line x1=\"%s\" y1=\"%s\" x2=\"%s\" y2=\"%s\" style=\"%s\" />\n", new Object[] {
            x1, y1, x2, y2, style
        });
    }

    public static String line(Number x1, Number y1, Number x2, Number y2, String style)
    {
        return line(Integer.toString(x1.intValue()), Integer.toString(y1.intValue()), Integer.toString(x2.intValue()), Integer.toString(y2.intValue()), style);
    }

    public static String text(String x, String y, String style, String text)
    {
        return String.format("<text x=\"%s\" y=\"%s\" style=\"%s\">\n%s\n</text>\n", new Object[] {
            x, y, style, text
        });
    }

    public static String text(Number x, Number y, String style, String text)
    {
        return text(Integer.toString(x.intValue()), Integer.toString(y.intValue()), style, text);
    }

    public static String doc(String content)
    {
        return String.format("<?xml version=\"1.0\" standalone=\"no\" ?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD S" +
"VG 20010904//EN\" \"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">\n" +
"%s\n"
, new Object[] {
            content
        });
    }

    private static boolean viewSVG(File file)
        throws IOException
    {
        if("Mac OS X".equals(System.getProperty("os.name")))
        {
            Runtime.getRuntime().exec(String.format("open -a /Applications/Safari.app %s", new Object[] {
                file.getAbsoluteFile()
            }));
            return true;
        } else
        {
            return false;
        }
    }

    public static void main(String args[])
        throws IOException
    {
        String s = doc(svg(Integer.valueOf(160), Integer.valueOf(200), (new StringBuilder()).append(rect(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(160), Integer.valueOf(200), "fill:red;")).append(svg(Integer.valueOf(10), Integer.valueOf(10), Integer.valueOf(100), Integer.valueOf(100), rect(Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(100), Integer.valueOf(100), "fill:orange; stroke:rgb(0,0,0);"))).append(line(Integer.valueOf(20), Integer.valueOf(20), Integer.valueOf(100), Integer.valueOf(100), "stroke:black; stroke-width:2px;")).append(line(Integer.valueOf(20), Integer.valueOf(100), Integer.valueOf(100), Integer.valueOf(20), "stroke:black; stroke-width:2px;")).append(text(Integer.valueOf(10), Integer.valueOf(140), "font-family:verdana; font-size:20px; font-weight:bold;", "Hello world")).toString()));
        File file = new File("demo.svg");
        /* marceniuk
        FileWriter w = new FileWriter(file);
        w.write(s);
        w.close();
        System.out.println(String.format("File written: %s", new Object[] {
            file.getAbsolutePath()
        }));
        if(args.length > 0 && args[0].equals("-view") && !viewSVG(file))
        {
            System.err.println("'-view' not supported on this platform");
        }
        marceniuk */
    }
}
