var setting = {
    view: {
        dblClickExpand: false
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid"
        }
    },
    callback: {
        beforeClick: beforeClick,
        onClick: onClick
    }
};

function beforeClick(treeId, treeNode) {
    var check = (treeNode && !treeNode.isParent);
    if (!check) alert("不能选择父节点...");
    return check;
}

function onClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        v = "";
    nodes.sort(function compare(a,b){return a.id-b.id;});
    for (var i=0, l=nodes.length; i<l; i++) {
        v += nodes[i].name + ",";
    }
    if (v.length > 0 ) v = v.substring(0, v.length-1);
    var cityObj = $("#dataSel");
    $("#dataSelHidden").val(nodes[0].id)
    cityObj.attr("value", v);
}

function showMenu() {
    var cityObj = $("#dataSel");
    var cityOffset = $("#dataSel").offset();

    $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
        hideMenu();
    }
}

$(document).ready(function(){
    axios.post('/dataClass/query'
        ,{}
        ,{headers: {token: sessionStorage.getItem('token')}}
    ).then(function (res) {
        //alert(JSON.stringify(res.data))
        $.fn.zTree.init($("#treeDemo"), setting, res.data);
    })

});

function addOne(){
    var num = $('#addDiv .layui-input-block input:hidden').length;
    num = num/2;
    var tr = "<div class='layui-input-block flag'>";
        tr += "图片：<input onchange='subFile(this,1)' type='file'  accept='image/*'  >";
        tr += "<input type='text' readonly  name='fileImage' value='' />";//js空值传值，位置不能动
        tr += "文件：<input onchange='subFile(this,1)' type='file'   accept='*/*'>" ;
        tr += "<input type='text' readonly  name='file' value='' />";//js空值传值，位置不能动
        tr += "名称：<input type='text' name='dataName' class='form-control' style='width: 176px;' placeholder='子类名称/Subclass name'>" ;
        tr += "<input type='button'  onclick='subOne(this)' value='减少子类目' />";
        tr +="</div>";

    $('#addDiv').append(tr);

    return false
}

function subOne(thisOne){
    $(thisOne).parent().remove();
}


function subFile(file,flag){
    var formData = new FormData();
    formData.append("file", $(file)[0].files[0]);

    $.ajax({
        url: '/data/addFile',
        type: 'POST',
        cache: false, //上传文件不需要缓存
        data: formData,
        processData: false, // 告诉jQuery不要去处理发送的数据
        contentType: false, // 告诉jQuery不要去设置Content-Type请求头
        success: function (msg) {
            console.log(msg)
            if(2==flag) {
                $(file).next().text(msg.replace(new RegExp('"', "gm"), ""));
            }else{
                $(file).next().val(msg.replace(new RegExp('"', "gm"), ""));
            }
        }
    });
}

layui.use(['form','upload'], function(){
    var form = layui.form;

    //监听提交
    form.on('submit(formDemo)', function(data){
        var array = [],
            dataType = parseInt($("#dataSelHidden").val()),
            state = parseInt($("#state").val()),
            flag = $('.flag');

        if(0 == flag.length) {
            layer.alert("至少保存一个类目")
            return false
        }
        flag.each(function(index,item){
            var file = $(item).find("input[name='file']").val(),
            fileImage = $(item).find("input[name='fileImage']").val(),
            dataName = $(item).find("input[name='dataName']").val();
            array.push({dataType:dataType,state:state, fileImage:fileImage, file:file,dataName:dataName})

        });

        axios.post('/data/add',array
            ,{headers: {token: sessionStorage.getItem('token')}}
        ).then(function (res) {
            if (res.data.error) {
                layer.alert(res.data.error)
            }else if (res.data.message) {
                layer.alert(res.data.message)
            }  else {
                layer.msg("添加成功")
            }

        });

        return false
    });
});