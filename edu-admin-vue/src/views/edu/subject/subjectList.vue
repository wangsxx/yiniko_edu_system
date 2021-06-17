<template>
    <div class="app-container">
        <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

        <el-tree
                ref="tree2"
                :data="data2"
                :props="defaultProps"
                :filter-node-method="filterNode"
                class="filter-tree"
                default-expand-all
        />

    </div>
</template>

<script>
    import subject from "../../../api/subject/subject";

    export default {
        name: "subjectList",
        data() {
            return {
                filterText: '',
                data2: [],
                // 标签名字
                defaultProps: {
                    children: 'children',
                    label: 'title'
                }
            }
        },
        watch: {
            filterText(val) {
                this.$refs.tree2.filter(val)
            }
        },
        created() {
            this.getSubject()
        },
        methods: {
            filterNode(value, data) {
                if (!value) return true
                return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
            },
            getSubject(){
                subject.getSubjectList()
                    .then(response => {
                        this.data2 = response.data.subjectList
                })
            }
        }
    }
</script>


<style scoped>

</style>