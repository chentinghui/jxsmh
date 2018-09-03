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
        beforeRemove: zTreeBeforeRemove
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

function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='add node' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(){
        axios.post('/dataClass/add/'
            ,{id:treeNode.id}
            ,{headers: {token: sessionStorage.getItem('token')}}
        ).then(function (res) {
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

    //alert($("#name").val()+'==2221='+ $("#ename").val()+"===="+$("#tempid").val())
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
            }else{
                alert("修改失败");
            }
        }
    });
}

// layui.use('form', function(){
//     var form = layui.form;
//     //监听提交
//     form.on('submit(formDemo)', function(data){
//         alert("---3333-")
//         layer.msg(JSON.stringify(data.field));
//         return false;
//     });
//
// });


