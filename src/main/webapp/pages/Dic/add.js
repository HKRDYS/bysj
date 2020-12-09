layui
    .config({
        base: "js/"
    })
    .use(
        ['form', 'layer', 'jquery', 'layedit', 'laydate', 'upload'],
        function () {
            var form = layui.form, layer = layui.layer, laypage = layui.laypage, layedit = layui.layedit,
                laydate = layui.laydate, $ = layui.jquery, upload = layui.upload;

            function getUParam(name, id) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
                var r = decodeURIComponent(
                    $("#" + id).attr("src").substr(
                        $("#" + id).attr("src").indexOf("?"))
                        .substr(1)).match(reg); // 匹配目标参数
                if (r != null)
                    return unescape(r[2]);
                return ""; // 返回参数值
            }

            var ctxPath = getUParam("ctx", "addjs");
            var id = getUParam("id", "addjs");
            // 普通图片上传
            var uploadInst = upload
                .render({
                    elem: '#test1',
                    multiple: true,
                    url: ctxPath + '/common/upload',
                    done: function (res) {
                        // 如果上传失败
                        if (res.code > 0) {
                            return layer.msg('上传失败');
                        }
                        // 上传成功
                        $('#demo1').append('<img src=' + ctxPath + '/upload/' + res.url + '  class="layui-upload-img" width="100px" height="100px">')
                        // 上传成功
                        var pic = $('#pics');
                        if (pic.val().length > 0)
                            pic.val(pic.val() + "#" + res.url);
                        else
                            pic.val(res.url);
                    },
                    error: function () {
                        // 演示失败状态，并实现重传
                        var demoText = $('#demoText');
                        demoText
                            .html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                        demoText.find('.demo-reload').on('click',
                            function () {
                                uploadInst.upload();
                            });
                    }
                });

            // 监听提交
            form.on('submit(adddd)', function (data) {
                parent.resetSwClose(true);
                layer.msg('正在保存。。。', {time: 1000}, function () {
                });
                var url = "";
                if (id != null && id != '')
                    url = ctxPath + "/dic/update";
                else
                    url = ctxPath + "/dic/add";
                $.getJSON(url, data.field,
                    function (data) {
                        if (data.code == '200') {
                            parent.layer.closeAll();
                            parent.reloadList();
                        } else {
                            layer.msg('数据保存失败', {
                                time: 1000
                            }, function () {
                                alert(300)
                            });
                            parent.resetSwClose(false);
                        }
                    });
                return false;
            });
            //获取信息
            if (id != null && id != '') {
                $.getJSON(ctxPath + "/dic/get", "id=" + id, function (jsondata) {
                    if (jsondata.code == '200') {
                        console.log(JSON.stringify(jsondata.rd));
                        //表单初始赋值
                        form.val('formtable', JSON.parse(JSON.stringify(jsondata.rd)));
                    } else {
                        $("#subpost").attr("disabled", "disabled").addClass("layui-btn-disabled");
                        layer.msg(jsondata.msg, {time: 2000}, function () {
                            parent.layer.closeAll('iframe');
                        });
                    }
                });
            }


        })
