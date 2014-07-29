<%@include file= "WEB-INF/jsp/commonImport.jsp"%>
<html>



<body>
<h2>Hello World!</h2>
</body>

<script type="text/javascript">
    var data={'loginId':'cdfg'}
    var urll=path+"/xxlogin.do";
   $().invoke(
           urll,
           data,
           [function(m,r){
               alert(r+"ssssss")
           },
           function(m,r){
               alert(r+"cccccc")
           }]

   )
</script>

</html>
