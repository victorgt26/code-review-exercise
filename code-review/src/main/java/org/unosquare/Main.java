package org.unosquare;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Node> entry = List.of(new Node(1, 0), new Node(2, 1), new Node(3, 1), new Node(4, 2), new Node(5, 4), new Node(6, 5));

        ArrayList<Node> nodes = new ArrayList<>(entry);

        nodes.sort(Comparator.comparing(Node::getId));

        if (nodes.stream().anyMatch(node -> node.getId() == 0 || node.getId().equals(node.getParentId()))) {
            throw new RuntimeException("Invalid node");
        }

        if (hasCyclicBranch(nodes)) {
            throw new RuntimeException("Tree ciclyc");
        }

        System.out.println(buildTreeNode(nodes));
    }

    public static boolean hasCyclicBranch(List<Node> nodes) {
        for (Node node : nodes) {
            if (isCiclyc(node, getChildrens(node.getId(), nodes), nodes)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCiclyc(Node node, List<Node> childrens, List<Node> nodes) {
        if (childrens.stream().anyMatch(children -> children.getId().equals(node.getParentId()))) {
            return true;
        } else {
            for (Node children : childrens) {
                return isCiclyc(node, getChildrens(children.getId(), nodes), nodes);
            }
        }
        return false;
    }

    public static List<Node> buildTreeNode(List<Node> nodes) {
        List<Node> roots = getRootNodes(nodes);
        roots.forEach(root -> buildBranch(root, nodes));
        return roots;
    }

    public static void buildBranch(Node parent, List<Node> nodes) {
        List<Node> childrens = getChildrens(parent.getId(), nodes);
        parent.setChildrens(childrens);
        childrens.forEach(children -> buildBranch(children, nodes));
    }

    public static List<Node> getRootNodes(List<Node> nodes) {
        return nodes.stream().filter(item -> item.getParentId() == 0).toList();
    }

    public static List<Node> getChildrens(Integer parentId, List<Node> nodes) {
        return nodes.stream().filter(item -> parentId.equals(item.getParentId())).toList();
    }
}

class Node {

    private Integer id;
    private Integer parentId;
    private List<Node> childrens;

    public Node(Integer id, Integer parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public List<Node> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Node> childrens) {
        this.childrens = childrens;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Node{" + "id=" + id + ", parentId=" + parentId + ", childrens=" + childrens + '}';
    }
}