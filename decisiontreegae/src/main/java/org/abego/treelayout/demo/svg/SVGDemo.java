package org.abego.treelayout.demo.svg;

import java.io.PrintStream;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.demo.SampleTreeFactory;
import org.abego.treelayout.demo.TextInBoxNodeExtentProvider;
import org.abego.treelayout.util.DefaultConfiguration;

// Referenced classes of package org.abego.treelayout.demo.svg:
//            SVGForTextInBoxTree

public class SVGDemo
{

    public SVGDemo()
    {
    }

    public static void main(String args[])
    {
        org.abego.treelayout.TreeForTreeLayout tree = SampleTreeFactory.createSampleTree();
        double gapBetweenLevels = 50D;
        double gapBetweenNodes = 10D;
        DefaultConfiguration configuration = new DefaultConfiguration(gapBetweenLevels, gapBetweenNodes);
        TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
        TreeLayout treeLayout = new TreeLayout(tree, nodeExtentProvider, configuration);
        SVGForTextInBoxTree generator = new SVGForTextInBoxTree(treeLayout);
        System.out.println(generator.getSVG());
    }
}
