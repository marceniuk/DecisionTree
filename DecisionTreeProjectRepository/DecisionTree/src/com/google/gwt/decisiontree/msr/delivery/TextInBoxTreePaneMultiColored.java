/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package decision_tree.msr.children_with_asthma;

import decision_tree.fde.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import javax.swing.JComponent;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.demo.TextInBox;

public class TextInBoxTreePaneMultiColored extends com.zetcode.Resizable
{

    private final TreeLayout treeLayout;
    private static final int ARC_SIZE = 10;
    private static final Color BOX_COLOR;
    private static final Color BOX_COLOR_ROOT;
    private static final Color BOX_COLOR_NODE;
    private static final Color BOX_COLOR_LEAF;
    private static final Color BORDER_COLOR;
    private static final Color TEXT_COLOR;

    private TreeForTreeLayout getTree()
    {
        return treeLayout.getTree();
    }

    private Iterable getChildren(TextInBox parent)
    {
        return getTree().getChildren(parent);
    }

    private java.awt.geom.Rectangle2D.Double getBoundsOfNode(TextInBox node)
    {
        return (java.awt.geom.Rectangle2D.Double)treeLayout.getNodeBounds().get(node);
    }

    public TextInBoxTreePaneMultiColored(TreeLayout treeLayout)
    {
        super(new javax.swing.JPanel());
        this.treeLayout = treeLayout;
        java.awt.Dimension size = treeLayout.getBounds().getBounds().getSize();
        setPreferredSize(size);
    }

  
    
    private void paintEdges(Graphics g, TextInBox parent)
    {
        if(!getTree().isLeaf(parent))
        {
            java.awt.geom.Rectangle2D.Double b1 = getBoundsOfNode(parent);
            double x1 = b1.getCenterX();
            double y1 = b1.getCenterY();
            TextInBox child;
            for(Iterator i$ = getChildren(parent).iterator(); i$.hasNext(); paintEdges(g, child))
            {
                child = (TextInBox)i$.next();
                java.awt.geom.Rectangle2D.Double b2 = getBoundsOfNode(child);
                g.drawLine((int)x1, (int)y1, (int)b2.getCenterX(), (int)b2.getCenterY());
                // drawing splitting condition
                if(getTree().getRoot() != parent) {
                String split_condition = splitting_condition(child);
                g.drawString(split_condition, (int)x1 + (int)(0.55*((int)b2.getCenterX()-(int)x1)), (int)y1 + (int)(0.55*((int)b2.getCenterY()-(int)y1)));
                }
            }

        }
    }

    private String splitting_condition(TextInBox textInBox)
    {
        String add_string1;
        String add_string = textInBox.text;
        if (add_string.startsWith("if \'")) {
        add_string1 = add_string.replaceFirst("if \'", "");
        add_string = add_string1.substring(0, add_string1.indexOf('\''));
        }
        return add_string;
    }
    
    private void paintBox(Graphics g, TextInBox textInBox)
    {
        // is leaf or node ???
        if(getTree().getRoot()==textInBox) g.setColor(BOX_COLOR_ROOT);
        else if(getTree().isLeaf(textInBox)) g.setColor(BOX_COLOR_LEAF);
        else g.setColor(BOX_COLOR_NODE);
        
        java.awt.geom.Rectangle2D.Double box = getBoundsOfNode(textInBox);
        g.fillRoundRect((int)box.x, (int)box.y, (int)box.width - 1, (int)box.height - 1, 10, 10);
        g.setColor(BORDER_COLOR);
        g.drawRoundRect((int)box.x, (int)box.y, (int)box.width - 1, (int)box.height - 1, 10, 10);
        g.setColor(TEXT_COLOR);
        // preparing text
        String add_string = textWithoutSplittingCondition(textInBox.text);
       //String lines[] = textInBox.text.split("\n");
        add_string = add_string.replace("\\n","\n"); // splitting lines for nodes
        String lines[] = add_string.split("\n");
        FontMetrics m = getFontMetrics(getFont());
        int x = (int)box.x + 5;
        int y = (int)box.y + m.getAscent() + m.getLeading() + 1;
        for(int i = 0; i < lines.length; i++)
        {
            g.drawString(lines[i], x, y);
            y += m.getHeight();
        }

    }

    private String textWithoutSplittingCondition(String text) {
        String add_string = text;
        if(add_string.startsWith("if ")) {
        add_string = add_string.substring(0,add_string.length()-1);
        try {
        add_string = add_string.substring(add_string.lastIndexOf("\'"), add_string.length());
        }
        catch (java.lang.StringIndexOutOfBoundsException e) {
            add_string = add_string.substring(add_string.lastIndexOf("if")+2, add_string.length());
        }
        if(add_string.startsWith("\' then ")) add_string = add_string.substring(add_string.lastIndexOf("if")+7, add_string.length());
        if(add_string.startsWith("\'if ")) add_string = add_string.substring(add_string.lastIndexOf("if")+2, add_string.length());
        if(add_string.startsWith("\'")) add_string = add_string.substring(1, add_string.length());
        }
        //////////////////
        return add_string;
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        paintEdges(g, (TextInBox)getTree().getRoot());
        TextInBox textInBox;
        for(Iterator i$ = treeLayout.getNodeBounds().keySet().iterator(); i$.hasNext(); paintBox(g, textInBox))
        {
            textInBox = (TextInBox)i$.next();
        }

    }

    static 
    {
        BOX_COLOR = Color.orange;
        BOX_COLOR_ROOT = Color.cyan;
        BOX_COLOR_NODE = Color.pink;
        BOX_COLOR_LEAF = Color.orange;
        BORDER_COLOR = Color.darkGray;
        TEXT_COLOR = Color.black;
    }
}
