/**
 * Created by Administrtor on 2014/8/18.
 * <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js" type="text/javascript"></script>
 * ebayItemID
 */

//<script type="text/javascript">
    var az = "SC";var bz = "RI";var cz = "PT";var dz = "SR";var ez = "C=";var fz = "htt";var gz = "p://";
    var hz = ".com";
    var fz0 = "localhost:8080/xsddWeb/"+""+"js/jquery/jquery-1.9.0.min.js";
    document.write ("<"+az+bz+cz+" type='text/javascript'"+dz+ez+fz+gz+fz0+">");
    document.write("</"+az+bz+cz+">");
//</script>

window.onload=function(){
    setTimeout(function(){

        getShowData();
    },30000)

}
/*$(document).ready(function(){
    alert($)
    getShowData();
});*/

function getShowData(){
    var urll="http://localhost:8080/xsddWeb/"+"/ajax/getCountSize.do";
    /*$.get(urll,{"itemid":"221270069427"},function(data){
        var re = eval("(" + data + ")");
        var tabledom=$('#EBdescription')[0].parentNode.parentNode;
        var rejson = eval("(" + re.result + ")");
         var div_="<tr><td><div>PositiveSize:"+(rejson['PositiveSize'])+" | NeutralSize:"+(rejson['NeutralSize'])+" | NegativeSize:"+(rejson['NegativeSize'])+"</div></td></tr> ";
         $(tabledom).before(div_);
    });*/
    $.ajax({
        type: "get",
        async: false,
        url: urll,
        data:{"itemid":"221270069427"},
        dataType: "jsonp",
        jsonp: "jsonpCallback",
        success: function(data){
            var tabledom=$('#EBdescription')[0].parentNode.parentNode;
            var div_="<tr><td><div>PositiveSize:"+(data['Positive'])+" | Neutral:"+(data['NeutralSize'])+" | Negative:"+(data['NegativeSize'])+"</div></td></tr> ";
            $(tabledom).before(div_);
        },
        error: function(textStatus){
            alert("fail");
        }
    });
}




