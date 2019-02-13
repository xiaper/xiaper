/**
 * float js 小icon
 * @version 1.0.8
 * @author www.bytedesk.com
 * @date 2018/10/15
 */
(function () {

    /**
     * https://github.com/ChenShenhai/blog/issues/8
     *
     * js列表加载索引
     * @type {number}
     * @private
     */
    var _index = 0;

    /**
     * 加载js文件
     * @name loadJs
     * @param {String} url
     * @param {Function} callback   文件加载后回调时间
     */
    function loadJs(url, callback) {
        var _script = document.createElement('script');
        _script.src = url;
        callback = callback || function(){};

        if(navigator.userAgent.indexOf("MSIE") > 0) {
            _script.onreadystatechange = function () {
                //
                if('loaded' === this.readyState || 'complete' === this.readyState){
                    callback();
                    this.onload = this.onreadystatechange = null;
                    this.parentNode.removeChild(this);
                }
            }
        } else {
            _script.onload = function() {
                callback();
                this.onload = this.onreadystatechange = null;
                this.parentNode.removeChild(this);
            }
        }

        document.getElementsByTagName('head')[0].appendChild(_script);
    }

    /**
     * 加载js文件列表
     * @name loadJsList
     * @param {Array} arr
     */
    function loadJsList() {

        if( _index < vendorJs.length ) {

            loadJs(vendorJs[_index], function() {
                _index ++;
                loadJsList(vendorJs);
            })
        }
    }

    // 样式表
    var floatCss = document.createElement('link');
    floatCss.setAttribute('href','//cdn.bytedesk.com/css/float.css');
    floatCss.setAttribute('rel','stylesheet');
    floatCss.setAttribute('type','text/css');
    document.getElementsByTagName("head")[0].appendChild(floatCss);

    //
    var elementCss = document.createElement('link');
    elementCss.setAttribute('href','//cdnjs.cloudflare.com/ajax/libs/element-ui/2.4.0/theme-chalk/index.css');
    elementCss.setAttribute('rel','stylesheet');
    elementCss.setAttribute('type','text/css');
    document.getElementsByTagName("head")[0].appendChild(elementCss);

    //
    var alicdnCss = document.createElement('link');
    alicdnCss.setAttribute('href','//at.alicdn.com/t/font_761687_dmeuhcj8d3i.css');
    alicdnCss.setAttribute('rel','stylesheet');
    alicdnCss.setAttribute('type','text/css');
    document.getElementsByTagName("head")[0].appendChild(alicdnCss);

    // 第三方库
    var vendorJs = [
        '//cdnjs.cloudflare.com/ajax/libs/UAParser.js/0.7.18/ua-parser.min.js',
        '//cdnjs.cloudflare.com/ajax/libs/fingerprintjs2/1.8.0/fingerprint2.min.js',
        '//cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.1/moment.min.js',
        '//cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js',
        '//cdn.bytedesk.com/js/vendor/stomp/1.2/stomp.min.js',
        '//cdnjs.cloudflare.com/ajax/libs/vue/2.5.16/vue.min.js',
        '//cdnjs.cloudflare.com/ajax/libs/element-ui/2.4.0/index.js',
        '//cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.min.js',
        // 自定义的js
        'http://154.8.212.157/js/deploy/dxz/small.js',
        'http://154.8.212.157/js/deploy/dxz/html.js',
        'http://154.8.212.157/js/deploy/dxz/client.js'
    ];
    loadJsList(vendorJs);


})();


