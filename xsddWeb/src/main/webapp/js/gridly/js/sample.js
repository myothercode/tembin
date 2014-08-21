// Generated by CoffeeScript 1.6.3

  function initDraug() {
      $('.gridly').gridly();
      $('.gridly').gridly('draggable');
      $('.gridly .brick').click(function (event) {
          var size;
          event.preventDefault();
          event.stopPropagation();
          $(this).toggleClass('small');
          $(this).toggleClass('large');
          if ($(this).hasClass('small')) {
              size = 140;
          }
          if ($(this).hasClass('large')) {
              size = 300;
          }
          $(this).data('width', size);
          $(this).data('height', size);
          return $('.gridly').gridly('layout');
      });
      return $('.gridly .delete').click(function (event) {
          event.preventDefault();
          event.stopPropagation();
          $(this).closest('.brick').remove();
          return $('.gridly').gridly('layout');
      });
  }


