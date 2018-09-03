var setting = {
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        selectedMulti: false,

    },
    check: {
        enable: false
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid"
        }
    },
    edit: {
        enable: true,
        showRenameBtn:false
    },
    callback: {
        onClick: zTreeOnClick,
        beforeRemove: zTreeBeforeRemove,
        onRemove: zTreeOnRemove
    }
};

//var zNodes=[];

$(document).ready(function(){
    axios.post('/dataClass/query'
        ,{}
        ,{headers: {token: sessionStorage.getItem('token')}}
    ).then(function (res) {
        $.fn.zTree.init($("#treeDemo"), setting, res.data);
    })

});

function zTreeOnRemove(event, treeId, treeNode) {
    if(confirm('真的删除么?')){
        //此处treeNode 为当前节点
        var ids =[] ;
        ids = getAllChildrenNodes(treeNode,ids);
        ids.unshift(treeNode.getParentNode().id,treeNode.id)

        axios.get('/dataClass/del/'+ids
            ,{}
            ,{headers: {token: sessionStorage.getItem('token')}}
        ).then(function (res) {
            if(res){
                alert("删除成功！");
            }
        })
    }



}

function getAllChildrenNodes(treeNode,ids){
    if (treeNode.isParent) {
        var childrenNodes = treeNode.children;
        if (childrenNodes) {
            for (var i = 0; i < childrenNodes.length; i++) {
                ids.push(childrenNodes[i].id);
                ids = getAllChildrenNodes(childrenNodes[i], ids);
            }
        }
    }
    return ids;
}


function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='add node' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    var flag = 1;

    if (btn) btn.bind("click", function(){
        axios.post('/dataClass/add/'
            ,{id:treeNode.id}
            ,{headers: {token: sessionStorage.getItem('token')}}
        ).then(function (res) {
            // alert(res.data)
            if(0 == res.data){
                alert("类目下面有资料，无法添加子类目！")
                return false
            }
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.addNodes(treeNode, {id:res.data, pId:treeNode.id, name:"new node" + res.data});
            $("#tempid").val(res.data)
        })
        return false;
    });
};
function removeHoverDom(treeId, treeNode) {
    // if(0 != treeNode.id){
    //     $("#addBtn_"+treeNode.tId).unbind().remove();
    // }

};

function zTreeOnClick(event, treeId, treeNode) {
    if(0 != treeNode.id){
        // alert(treeNode.tId + ", 222" + treeNode.name+","+treeId+","+treeNode.id);

        axios.get('/dataClass/queryData?id='+treeNode.id
            ,{}
            ,{headers: {token: sessionStorage.getItem('token')}}
        ).then(function (res) {
           // alert(JSON.stringify(res.data)+"==="+res.data[0].imageUrl)
            $("#tempid").val(treeNode.id)
            $("#name").val(res.data[0].name)
            $("#ename").val(res.data[0].ename)
            $("#imageUrl").val(res.data[0].imageUrl)

            $("#sbmt").css("visibility",'visible');
        })
    }else{
        $("#sbmt").css("visibility",'hidden');
    }

};

function zTreeBeforeRemove(treeId, treeNode) {
    if(0 == treeNode.id){
        return false;
    }else{
        $("#addBtn_"+treeNode.tId).unbind().remove();
    }


};

function save(){
    var formData = new FormData();
    formData.append("name", $("#name").val());
    formData.append("ename", $("#ename").val());
    formData.append("file", $("#file")[0].files[0]);
    formData.append("id", parseInt($("#tempid").val()));
    formData.append("imageUrl",$("#imageUrl").val());

    $.ajax({
        url: '/dataClass/update',
        type: 'POST',
        cache: false, //上传文件不需要缓存
        data: formData,
        processData: false, // 告诉jQuery不要去处理发送的数据
        contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        success: function (msg) {
            if(msg){
                $("#sbmt").css("visibility",'hidden');
                alert("修改成功");
                // editName();
            }else{
                alert("修改失败");
            }
        }
    });
}
