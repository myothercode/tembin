
$(document).ready(function(){
    $('.tooltips').append("<span></span>");
    $('.tooltips:not([tooltip-position])').attr('tooltip-position','bottom');


    $(".tooltips").mouseenter(function(){
        $(this).find('span').empty().append($(this).attr('tooltip'));
    });
});
