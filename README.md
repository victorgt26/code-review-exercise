# Code Review Exercise

This is the solution for a code review and optimization exercise for managing a tree.

The first lines are the data with which the algorithm can be tested.

        List<Node> entry = List.of(new Node(1, 0), new Node(2, 1), new Node(3, 1), new Node(4, 2), new Node(5, 4), new Node(6, 5));

        ArrayList<Node> nodes = new ArrayList<>(entry);


The next line is the optimization for the ordering of the data.

    nodes.sort(Comparator.comparing(Node::getId));

Next, validation is performed on each item in the list. Note: root nodes will have parentId zero.

    if (nodes.stream().anyMatch(node -> node.getId() == 0 || node.getId().equals(node.getParentId()))) {
        throw new RuntimeException("Invalid node");
    }

Next, we recursively validate that the nodes do not have a cyclic nesting

    if (hasCyclicBranch(nodes)) {
        throw new RuntimeException("Tree ciclyc");
    }

Finally, we print on the screen the structure of the tree built recursively

    System.out.println(buildTreeNode(nodes));
