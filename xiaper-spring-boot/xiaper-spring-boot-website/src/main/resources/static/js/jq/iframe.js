/**
 * 嵌入iframe
 */
(function () {

  var contentHtml = '<div style="position: fixed; bottom: 0px; right: 10px;">\n' +
      '<iframe id="byteDesk-kefu-iframe" width="300" height="500" src="chat.html" frameborder="0" scrolling="no"></iframe>\n' +
    '</div>';
  //
  var byteDesk = document.getElementById('byteDesk');
  byteDesk.insertAdjacentHTML('beforeend', contentHtml);

})();

