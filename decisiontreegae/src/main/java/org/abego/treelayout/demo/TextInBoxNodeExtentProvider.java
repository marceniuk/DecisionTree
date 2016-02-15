package org.abego.treelayout.demo;

import org.abego.treelayout.NodeExtentProvider;

// Referenced classes of package org.abego.treelayout.demo:
//            TextInBox

public class TextInBoxNodeExtentProvider
    implements NodeExtentProvider
{

    public TextInBoxNodeExtentProvider()
    {
    }

    public double getWidth(TextInBox treeNode)
    {
        return (double)treeNode.width;
    }

    public double getHeight(TextInBox treeNode)
    {
        return (double)treeNode.height;
    }

    //public volatile double getHeight(Object x0)
    public  double getHeight(Object x0)
    {
        return getHeight((TextInBox)x0);
    }

    //public volatile double getWidth(Object x0)
    public  double getWidth(Object x0)
    {
        return getWidth((TextInBox)x0);
    }
}
