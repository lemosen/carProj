/**
 * 树节点对象
 */
class TreeNode<T> {
    constructor(id: any, data: T) {
        this._id = id;
        this._data = data;
    }

    private _id: any;

    get id(): any {
        return this._id;
    }

    private _data: T;

    get data(): T {
        return this._data;
    }

    private _children: TreeNode<T>[] = [];

    get children(): TreeNode<T>[] {
        return this._children;
    }

    exists(id: any) {
        return this.getChild(id) !== null;
    }

    getChild(id: any) {
        for (let index = this._children.length - 1; index >= 0; --index) {
            if (this._children[index].id === id) {
                return this._children[index];
            }
        }

        return null;
    }

    removeChild(id: any) {
        for (let index = this._children.length - 1; index >= 0; --index) {
            if (this._children[index].id === id) {
                this._children.splice(index, 1);
                break;
            }
        }
    }

    addChild(id: any, item: T) {
        if (this.exists(id)) {
            // TODO: 重复添加, 报错
            return;
        }

        this._children.push(new TreeNode(id, item));
    }
}

/**
 * 树对象, 其中 root 保存所有一级节点
 */
class Tree<T> {
    private nodes: { [key: string]: TreeNode<T>; } = {};

    constructor(idField?: string, parentIdField?: string, rootValue?: any) {
        this._idField = idField;
        this._parentIdField = parentIdField;
        this._rootValue = rootValue;

        if (this._idField == null) this._idField = "id";
        if (this._parentIdField == null) this._parentIdField = "parentId";

        this._root = new TreeNode(this._rootValue, null);
    }

    private _idField: string = "id";

    get idField(): string {
        return this._idField;
    }

    private _parentIdField: string = "parentId";

    get parentIdField(): string {
        return this._parentIdField;
    }

    private _rootValue: any;

    get rootValue(): any {
        return this._rootValue;
    }

    private _root: TreeNode<T>;

    get root(): TreeNode<T> {
        return this._root;
    }

    clear() {
        this.nodes = {};
        this._root = new TreeNode(this._rootValue, null);
    }

    addNodes(items: T[]) {
        if (items == null || items.length === 0) {
            return;
        }

        let toAddNodes: TreeNode<T>[] = [];

        for (let index = 0; index < items.length; ++index) {
            let item = items[index];
            let id = this.getId(item);
            if (id == null) {
                // TODO: ID为NULL, 报错
                continue;
            }

            let parentId = this.getParentId(item);

            if (id === parentId) {
                // TODO: ID和父ID相同, 报错
            }

            if (this.nodes.hasOwnProperty(id)) {
                // TODO: 重复添加记录,报错
                continue;
            }

            let node = new TreeNode<T>(id, item);

            let parentNode = this.getNodeById(parentId);
            if (parentNode != null) {
                // 父节点已经在树中, 直接添加
                parentNode.addChild(id, item);
                this.nodes[id] = node;

            } else {
                toAddNodes.push(node);
            }
        }

        while (toAddNodes.length > 0) {
            let count = 0;
            for (let index = toAddNodes.length - 1; index >= 0; --index) {
                let node = toAddNodes[index];
                let parentId = this.getParentId(node.data);

                let parentNode = this.getNodeById(parentId);
                if (parentNode != null) {
                    // 父节点已经在树中, 直接添加
                    parentNode.addChild(node.id, node.data);
                    this.nodes[node.id] = node;

                    count++;
                }
            }

            if (count == 0) {
                // TODO: 存在不能处理的节点数据, 报错
                break;
            }
        }
    }

    getNodeById(id: any) {
        let node = id == this._rootValue ? this._root : this.nodes[id];
        return node;
    }

    getId(item: T): any {
        if (item == null) {
            return null;
        }
        return item[this._idField];
    }

    getParentId(item: T): any {
        if (item == null) {
            return null;
        }
        return item[this._parentIdField];
    }
}

export {
    Tree,
    TreeNode
};
