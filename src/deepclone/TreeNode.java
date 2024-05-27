package deepclone;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<I> {

    private I item;
    private final List<TreeNode<I>> children = new ArrayList<>();

    public TreeNode(I item) {
        this.item = item;
    }

    public void addChild(TreeNode<I> child) {
        if (child != null) {
            children.add(child);
        }
    }

    public I getItem() {
        return item;
    }

    public void setItem(I item) {
        this.item = item;
    }

    public List<TreeNode<I>> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "item=" + item +
                ", children=" + children +
                '}';
    }
}
