define(function(require,exports,module) {
    const TreeStore = require('./model/tree-store');
    const t = require('../../../src/locale/index').t;
    const emitter = require('../../../src/mixins/emitter');

    module.exports = {
        name: 'ElTree',
        template: require('./tree.tpl'),
        mixins: [emitter],

        components: {
            ElTreeNode: require('./tree-node.js')
        },

        data() {
            return {
                store: null,
                root: null,
                currentNode: null
            };
        },

        props: {
            data: {
                type: Array
            },
            emptyText: {
                type: String,
                default() {
                    return t('el.tree.emptyText');
                }
            },
            nodeKey: String,
            checkStrictly: Boolean,
            defaultExpandAll: Boolean,
            expandOnClickNode: {
                type: Boolean,
                default: true
            },
            autoExpandParent: {
                type: Boolean,
                default: true
            },
            defaultCheckedKeys: Array,
            defaultExpandedKeys: Array,
            renderContent: Function,
            showCheckbox: {
                type: Boolean,
                default: false
            },
            props: {
                default() {
                    return {
                        children: 'children',
                        label: 'label',
                        icon: 'icon'
                    };
                }
            },
            lazy: {
                type: Boolean,
                default: false
            },
            highlightCurrent: Boolean,
            currentNodeKey: [String, Number],
            load: Function,
            filterNodeMethod: Function,
            accordion: Boolean
        },

        computed: {
            children: {
                set(value) {
                    this.data = value;
                },
                get() {
                    return this.data;
                }
            }
        },

        watch: {
            defaultCheckedKeys(newVal) {
                this.store.defaultCheckedKeys = newVal;
                this.store.setDefaultCheckedKey(newVal);
            },
            defaultExpandedKeys(newVal) {
                this.store.defaultExpandedKeys = newVal;
                this.store.setDefaultExpandedKeys(newVal);
            },
            currentNodeKey(newVal) {
                this.store.setCurrentNodeKey(newVal);
            },
            data(newVal) {
                this.store.setData(newVal);
            }
        },

        methods: {
            filter(value) {
                if (!this.filterNodeMethod) throw new Error('[Tree] filterNodeMethod is required when filter');
                this.store.filter(value);
            },
            getNodeKey(node, index) {
                const nodeKey = this.nodeKey;
                if (nodeKey && node) {
                    return node.data[nodeKey];
                }
                return index;
            },
            getCheckedNodes(leafOnly) {
                return this.store.getCheckedNodes(leafOnly);
            },
            getCheckedKeys(leafOnly) {
                return this.store.getCheckedKeys(leafOnly);
            },
            setCheckedNodes(nodes, leafOnly) {
                if (!this.nodeKey) throw new Error('[Tree] nodeKey is required in setCheckedNodes');
                this.store.setCheckedNodes(nodes, leafOnly);
            },
            setCheckedKeys(keys, leafOnly) {
                if (!this.nodeKey) throw new Error('[Tree] nodeKey is required in setCheckedNodes');
                this.store.setCheckedKeys(keys, leafOnly);
            },
            setChecked(data, checked, deep) {
                this.store.setChecked(data, checked, deep);
            },
            handleNodeExpand(nodeData, node, instance) {
                this.broadcast('ElTreeNode', 'tree-node-expand', node);
            }
        },

        created() {
            this.isTree = true;

            this.store = new TreeStore({
                key: this.nodeKey,
                data: this.data,
                lazy: this.lazy,
                props: this.props,
                load: this.load,
                currentNodeKey: this.currentNodeKey,
                checkStrictly: this.checkStrictly,
                defaultCheckedKeys: this.defaultCheckedKeys,
                defaultExpandedKeys: this.defaultExpandedKeys,
                autoExpandParent: this.autoExpandParent,
                defaultExpandAll: this.defaultExpandAll,
                filterNodeMethod: this.filterNodeMethod
            });

            this.root = this.store.root;
        }
    };
})