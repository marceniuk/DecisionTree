package org.abego.treelayout.demo.svg;

import org.abego.treelayout.*;
//import java.awt.Dimension;
//import java.awt.Rectangle;
//import java.awt.geom.Rectangle2D;
import java.util.*;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.demo.TextInBox;

// Referenced classes of package org.abego.treelayout.demo.svg:
//            SVGUtil

public class SVGForTextInBoxTree
{

    private final TreeLayout treeLayout;
    private String svgText;

    private TreeForTreeLayout getTree()
    {
        return treeLayout.getTree();
    }

    private Iterable getChildren(TextInBox parent)
    {
        return getTree().getChildren(parent);
    }

    private Rectangle2D.Double getBoundsOfNode(TextInBox node)
    {
        return (Rectangle2D.Double)treeLayout.getNodeBounds().get(node);
    }

    public SVGForTextInBoxTree(TreeLayout treeLayout)
    {
        this.treeLayout = treeLayout;
    }

    private void generateEdges(StringBuilder result, TextInBox parent)
    {
        if(!getTree().isLeaf(parent))
        {
            Rectangle2D.Double b1 = getBoundsOfNode(parent);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();
            TextInBox child;
            for(Iterator i$ = getChildren(parent).iterator(); i$.hasNext(); generateEdges(result, child))
            {
                child = (TextInBox)i$.next();
                Rectangle2D.Double b2 = getBoundsOfNode(child);
                result.append(SVGUtil.line(java.lang.Double.valueOf(x1), java.lang.Double.valueOf(y1), java.lang.Double.valueOf(b2.getCenterX()), java.lang.Double.valueOf(b2.getCenterY()), "stroke:black; stroke-width:2px;"));
            }

        }
    }

    private void generateBox(StringBuilder result, TextInBox textInBox)
    {
        Rectangle2D.Double box = getBoundsOfNode(textInBox);
        result.append(SVGUtil.rect(java.lang.Double.valueOf(box.x + 1.0D), java.lang.Double.valueOf(box.y + 1.0D), java.lang.Double.valueOf(box.width - 2D), java.lang.Double.valueOf(box.height - 2D), "fill:orange; stroke:rgb(0,0,0);", "rx=\"10\""));
        String lines[] = textInBox.text.split("\n");
        int fontSize = 12;
        int x = (int)box.x + fontSize / 2 + 2;
        int y = (int)box.y + fontSize + 1;
        String style = String.format("font-family:sans-serif;font-size:%dpx;", new Object[] {
            Integer.valueOf(fontSize)
        });
        for(int i = 0; i < lines.length; i++)
        {
            result.append(SVGUtil.text(Integer.valueOf(x), Integer.valueOf(y), style, lines[i]));
            y += fontSize;
        }

    }

    private String generateDiagram()
    {
        StringBuilder result = new StringBuilder();
        generateEdges(result, (TextInBox)getTree().getRoot());
        TextInBox textInBox;
        for(Iterator i$ = treeLayout.getNodeBounds().keySet().iterator(); i$.hasNext(); generateBox(result, textInBox))
        {
            textInBox = (TextInBox)i$.next();
        }

        Dimension size = treeLayout.getBounds().getBounds().getSize();
        return SVGUtil.svg(Integer.valueOf(size.width), Integer.valueOf(size.height), result.toString());
    }

    public String getSVG()
    {
        if(svgText == null)
        {
            svgText = SVGUtil.doc(generateDiagram());
        }
        return svgText;
    }
}
