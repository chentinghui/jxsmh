function getRootPath(){
    var strFullPath=window.document.location.href;
    var strPath=window.document.location.pathname;
    var pos=strFullPath.indexOf(strPath);
    var prePath=strFullPath.substring(0,pos);
    var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
    alert(prePath+postPath)
    return prePath+postPath;
}


//图片类型验证
function verificationPicFile(file) {
    var fileTypes = [".jpg", ".png",".ico"];
    var filePath = file.value;
    //当括号里面的值为0、空字符、false 、null 、undefined的时候就相当于false
    if(filePath){
        var isNext = false;
        var fileEnd = filePath.substring(filePath.indexOf("."));
        for (var i = 0; i < fileTypes.length; i++) {
            if (fileTypes[i] == fileEnd) {
                isNext = true;
                break;
            }
        }
        if (!isNext){
            alert('不接受此文件类型,请上传图片.jpg.png格式');
            file.value = "";
            return false;
        }
    }else {
        return false;
    }
}

//图片大小验证
function verificationPicFileSize(file) {
    var fileSize = 0;
    var fileMaxSize = 1024;//1M
    var filePath = file.value;
    if(filePath){
        fileSize =file.files[0].size;
        var size = fileSize / 1024;
        if (size > fileMaxSize) {
            alert("文件大小不能大于1M！");
            file.value = "";
            return false;
        }else if (size <= 0) {
            alert("文件大小不能为0M！");
            file.value = "";
            return false;
        }
    }else{
        return false;
    }
}

//图片尺寸验证
function verificationPicFileLength(file) {
    var filePath = file.value;
    if(filePath){
        //读取图片数据
        var filePic = file.files[0];
        var reader = new FileReader();
        reader.onload = function (e) {
            var data = e.target.result;
            //加载图片获取图片真实宽度和高度
            var image = new Image();
            image.onload=function(){
                var width = image.width;
                var height = image.height;
                if ((width > 539 | height > 539) && width==height){
                    alert("文件尺寸符合！");
                }else {
                    alert("文件尺寸应为正方形：并大于等于540*540！");
                    file.value = "";
                    return false;
                }
            };
            image.src= data;
        };
        reader.readAsDataURL(filePic);
    }else{
        return false;
    }
}

//日期格式化
function Format(datetime,fmt) {
    if (parseInt(datetime)==datetime) {
        if (datetime.length==10) {
            datetime=parseInt(datetime)*1000;
        } else if(datetime.length==13) {
            datetime=parseInt(datetime);
        }
    }
    datetime=new Date(datetime);
    var o = {
        "M+" : datetime.getMonth()+1,                 //月份
        "d+" : datetime.getDate(),                    //日
        "h+" : datetime.getHours(),                   //小时
        "m+" : datetime.getMinutes(),                 //分
        "s+" : datetime.getSeconds(),                 //秒
        "q+" : Math.floor((datetime.getMonth()+3)/3), //季度
        "S"  : datetime.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

$(document).ready(function() {
    if (sessionStorage.getItem('token') === null) {
        // alert('请先登录',{icon: 1, title:'提示'}, function(index){
        //     window.parent.location.href= '/login.html';
        // });
        window.parent.location.href= '/login.html';
    }
});