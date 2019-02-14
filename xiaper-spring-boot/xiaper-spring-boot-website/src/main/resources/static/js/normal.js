/**
 * icon size: normal
 * @version 1.0.3
 * @author www.bytedesk.com
 * @date 2018/10/03
 */
(function () {

    var contentHtml = '<div id="byteDesk-start" class="byteDesk-start-normal" style="display: none;">\n' +
        '            <div class="byteDesk-start-normal-div">\n' +
        '                <i class="iconfont icon-agent" style="font-size: 25px;"></i>\n' +
        '            </div>\n' +
        '        </div>';
    //
    var byteDesk = document.getElementById('byteDesk');
    byteDesk.insertAdjacentHTML('beforeend', contentHtml);

})();
