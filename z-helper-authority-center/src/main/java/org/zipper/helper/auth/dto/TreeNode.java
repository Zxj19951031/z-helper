package org.zipper.helper.auth.dto;

import org.zipper.helper.web.entity.NodeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 带子节点的菜单结构，继承菜单实体
 * 这里其实扩展性得不到保证
 * 主要是想要提供树化和对象赋值的方法
 *
 * @author zhuxj
 * @since 2020/07/09
 */
public class TreeNode<T extends NodeEntity> {

    private T node;
    /**
     * 子节点列表
     */
    private List<TreeNode<T>> children;

    public List<TreeNode<T>> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    public T getNode() {
        return node;
    }

    public void setNode(T node) {
        this.node = node;
    }

    /**
     * 将list结构转换成tree结构
     *
     * @param list list
     * @return tree
     */
    public static <T extends NodeEntity> List<TreeNode<T>> parseTree(List<TreeNode<T>> list) {
        List<TreeNode<T>> treeList = new ArrayList<>();

        for (TreeNode<T> node : list) {
            if (node.getNode().getPid().equals(0L)) {
                treeList.add(node);
            }
            for (TreeNode<T> node2 : list) {
                if (node2.getNode().getPid().equals(node.getNode().getId())) {
                    node.getChildren().add(node2);
                }
            }
        }

        return treeList;
    }
}
