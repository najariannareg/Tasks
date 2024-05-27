package demo;


import deepclone.CopyUtils;
import demo.example.Human;
import demo.example.Man;
import demo.example.TreeNode;
import demo.example.Woman;

import java.util.List;

public class Demo {

    public static void main(String[] args) {

        Human parent = new Man("James", 80, null);
        Human man = new Man("John", 50, List.of("Notes from Underground"));
        Human child1 = new Man("Jack", 30, List.of("Crime & Punishment", "The Idiot"));
        Human child2 = new Woman("Jane", 25, new String[]{"Titanic", "The Godfather"});

        TreeNode<Human> root = new TreeNode<>(parent);
        TreeNode<Human> node1 = new TreeNode<>(man);
        TreeNode<Human> node2_1 = new TreeNode<>(child1);
        TreeNode<Human> node2_2 = new TreeNode<>(child2);

        root.addChild(node1);
        node1.addChild(node2_1);
        node1.addChild(node2_2);

        TreeNode<Human> copy = CopyUtils.deepCopy(root);

        System.out.println(copy);
    }


}