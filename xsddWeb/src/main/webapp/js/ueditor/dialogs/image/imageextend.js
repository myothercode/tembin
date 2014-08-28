/**
 * Created by Administrtor on 2014/8/11.
 */


;(function($) {
    $.fn.image_editor = {
        editor: null,
        init: function(editorid, keyid) {
            var _editor = this.getEditor(editorid);
            _editor.ready(function() {
                _editor.setDisabled();
                _editor.hide();
            });
        },
        getEditor: function(editorid) {
            this.editor = UE.getEditor(editorid);
            return this.editor;
        },
        show: function(id) {
            var _editor = this.editor;
            //注意这里只需要获取编辑器，无需渲染，如果强行渲染，在IE下可能会不兼容（切记）
            //和网上一些朋友的代码不同之处就在这里
            $("#" + id).click(function() {
                var image = _editor.getDialog("insertimage");
                image.render();
                image.open();
            });
        },
        render: function(editorid) {
            var _editor = this.getEditor(editorid);
            _editor.render();
        }
    };

    /*        $(function() {
     image.init("myeditor");
     image.show("image");
     });*/
})(jQuery);

/* var editor = UE.getEditor('myeditor', {
 imageUrl: "{:U(GROUP_NAME.'/Cases/upload/')}",
 imagePath: "__ROOT__/Uploads/",
 imageManagerUrl: "{:U(GROUP_NAME.'/cases/imagesManager/')}",
 imageManagerPath: "__ROOT__/",
 });*/