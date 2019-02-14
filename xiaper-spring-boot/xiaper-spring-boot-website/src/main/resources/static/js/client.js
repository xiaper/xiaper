/**
 * client js
 * @version 1.2.0
 * @author www.bytedesk.com
 * @date 2018/11/12
 */
(function () {

    var bytedeskapp = new Vue({
        el: '#byteDesk-app',
        data() {
            return {
                byteDeskVisible: false,
                avatar: '//chainsnow.oss-cn-shenzhen.aliyuncs.com/images/20180711094054.png',
                title: '智能客服',
                description: 'www.bytedesk.com',
                HTTP_HOST: 'https://api.bytedesk.com',
                STOMP_HOST: 'https://stomp.bytedesk.com',

                imageDialogVisible: false,
                currentImageUrl: '',
                currentVoiceUrl: '',
                dialogTableVisible: false,
                show_emoji: false,
                emojiBaseUrl: '//chainsnow.oss-cn-shenzhen.aliyuncs.com/emojis/gif/',
                inputContent: '',
                messages: [],
                // 上传图片相关参数
                upload_headers: {
                    "X-CSRF-TOKEN": "",
                    "X-Requested-With": "XMLHttpRequest",
                    "Accept": "application/json",
                    "Authorization" : "Bearer "
                },
                upload_data: {
                    file_name: 'test.png',
                    username: ''
                },
                upload_name: 'file',
                // 接收图片服务器地址
                uploadImageServerUrl: "https://upload.bytedesk.com/visitor/api/upload/image",
                // 上传图片结果集, 格式：{name: "", url: ""}
                uploadedImageList: [],

                /**
                 * 留言
                 */
                showLeaveMessage: false,
                leaveMessageForm: {
                    mobile: '',
                    email: '',
                    content: ''
                },
                rules: {
                    mobile: [
                        { required: true, message: '请输入手机号码', trigger: 'change' }
                    ],
                    content: [
                        { required: true, message: '请输入留言内容', trigger: 'blur' }
                    ]
                },

                /**
                 * 满意度评价相关
                 */
                // 仅允许评价一次
                isRated: false,
                // 是否客服邀请评价
                isInviteRate: false,
                // 满意度评价对话框是否可见
                rateDialogVisible: false,
                // 满意度评分
                rateScore: 5,
                // 满意度附言
                rateContent: '',

                /**
                 *
                 */
                isLoading: false,
                stompClient: '',
                sessionId: '',
                preSessionId: '',
                browseInviteBIid: '',
                passport: {
                    token: {
                        access_token: '',
                        expires_in: 0,
                        jti: '',
                        refresh_token: '',
                        scope: '',
                        token_type: ''
                    }
                },
                adminUid: '',
                workGroupWid: '',
                subDomain: '',
                client: 'web',
                // 聊天记录
                messages: [],
                thread: {
                    id: 0,
                    tid: ''
                },
                // 已经订阅的topic
                subscribedTopics: [],
                // 加载聊天记录offset
                page: 0,
                // 是否是最后一批聊天记录
                last: false,
                // workGroup/visitor/contact/group
                type: 'workGroup',
                // 指定客服
                agentUid: '',
                // 当前访客用户名
                uid: '',
                username: '',
                password: '',
                nickname: '',
                // 本地存储access_token的key
                token: 'token',
                isConnected: false,
                isReconnect: false,
                answers: [],
                isRobot: false,
                //
                emotionBaseUrl: 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/emojis/gif/',
                // 表情
                emotionMap: {
                    '[微笑]': '100.gif',
                    '[撇嘴]': '101.gif',
                    '[色]': '102.gif',
                    '[发呆]': '103.gif',
                    '[得意]': '104.gif',
                    '[流泪]': '105.gif',
                    '[害羞]': '106.gif',
                    '[闭嘴]': '107.gif',
                    '[睡]': '108.gif',
                    '[大哭]': '109.gif',

                    '[尴尬]': '110.gif',
                    '[发怒]': '111.gif',
                    '[调皮]': '112.gif',
                    '[呲牙]': '113.gif',
                    '[惊讶]': '114.gif',
                    '[难过]': '115.gif',
                    '[酷]': '116.gif',
                    '[冷汗]': '117.gif',
                    '[抓狂]': '118.gif',
                    '[吐]': '119.gif',

                    '[偷笑]': '120.gif',
                    '[愉快]': '121.gif',
                    '[白眼]': '122.gif',
                    '[傲慢]': '123.gif',
                    '[饥饿]': '124.gif',
                    '[困]': '125.gif',
                    '[惊恐]': '126.gif',
                    '[流汗]': '127.gif',
                    '[憨笑]': '128.gif',
                    '[悠闲]': '129.gif',

                    '[奋斗]': '130.gif',
                    '[咒骂]': '131.gif',
                    '[疑问]': '132.gif',
                    '[嘘]': '133.gif',
                    '[晕]': '134.gif',
                    '[疯了]': '135.gif',
                    '[衰]': '136.gif',
                    '[骷髅]': '137.gif',
                    '[敲打]': '138.gif',
                    '[再见]': '139.gif',

                    '[擦汗]': '140.gif',
                    '[抠鼻]': '141.gif',
                    '[鼓掌]': '142.gif',
                    '[糗大了]': '143.gif',
                    '[坏笑]': '144.gif',
                    '[左哼哼]': '145.gif',
                    '[右哼哼]': '146.gif',
                    '[哈欠]': '147.gif',
                    '[鄙视]': '148.gif',
                    '[委屈]': '149.gif',

                    '[快哭]': '150.gif',
                    '[阴险]': '151.gif',
                    '[亲亲]': '152.gif',
                    '[吓]': '153.gif',
                    '[可怜]': '154.gif',
                    '[菜刀]': '155.gif',
                    '[西瓜]': '156.gif',
                    '[啤酒]': '157.gif',
                    '[篮球]': '158.gif',
                    '[乒乓]': '159.gif',

                    '[咖啡]': '160.gif',
                    '[饭]': '161.gif',
                    '[猪头]': '162.gif',
                    '[玫瑰]': '163.gif',
                    '[凋谢]': '164.gif',
                    '[嘴唇]': '165.gif',
                    '[爱心]': '166.gif',
                    '[心碎]': '167.gif',
                    '[蛋糕]': '168.gif',
                    '[闪电]': '169.gif',

                    '[炸弹]': '170.gif',
                    '[刀]': '171.gif',
                    '[足球]': '172.gif',
                    '[瓢虫]': '173.gif',
                    '[便便]': '174.gif',
                    '[月亮]': '175.gif',
                    '[太阳]': '176.gif',
                    '[礼物]': '177.gif',
                    '[拥抱]': '178.gif',
                    '[强]': '179.gif',

                    '[弱]': '180.gif',
                    '[握手]': '181.gif',
                    '[胜利]': '182.gif',
                    '[抱拳]': '183.gif',
                    '[勾引]': '184.gif',
                    '[拳头]': '185.gif',
                    '[差劲]': '186.gif',
                    '[爱你]': '187.gif',
                    '[No]': '188.gif',
                    '[OK]': '189.gif',

                    '[爱情]': '190.gif',
                    '[飞吻]': '191.gif',
                    '[跳跳]': '192.gif',
                    '[发抖]': '193.gif',
                    '[怄火]': '194.gif',
                    '[转圈]': '195.gif',
                    '[磕头]': '196.gif',
                    '[回头]': '197.gif',
                    '[跳绳]': '198.gif',
                    '[投降]': '199.gif',

                    '[激动]': '201.gif',
                    '[乱舞]': '202.gif',
                    '[献吻]': '203.gif',
                    '[左太极]': '204.gif',
                    '[右太极]': '205.gif'
                },
                // emoji表情, code代表来自微信端的表情字符，目前已经在服务器端处理替换为title字段，故code字段暂无用途
                emojis: [
                    { title: '[微笑]', file: '100.gif' },
                    { title: '[撇嘴]', file: '101.gif' },
                    { title: '[色]', file: '102.gif' },
                    { title: '[发呆]', file: '103.gif' },
                    { title: '[得意]', file: '104.gif' },
                    { title: '[流泪]', file: '105.gif' },
                    { title: '[害羞]', file: '106.gif' },
                    { title: '[闭嘴]', file: '107.gif' },
                    { title: '[睡]', file: '108.gif' },
                    { title: '[大哭]', file: '109.gif' },

                    { title: '[尴尬]', file: '110.gif' },
                    { title: '[发怒]', file: '111.gif' },
                    { title: '[调皮]', file: '112.gif' },
                    { title: '[呲牙]', file: '113.gif' },
                    { title: '[惊讶]', file: '114.gif' },
                    { title: '[难过]', file: '115.gif' },
                    { title: '[酷]', file: '116.gif' },
                    { title: '[冷汗]', file: '117.gif' },
                    { title: '[抓狂]', file: '118.gif' },
                    { title: '[吐]', file: '119.gif' },

                    { title: '[偷笑]', file: '120.gif' },
                    { title: '[愉快]', file: '121.gif' },
                    { title: '[白眼]', file: '122.gif' },
                    { title: '[傲慢]', file: '123.gif' },
                    { title: '[饥饿]', file: '124.gif' },
                    { title: '[困]', file: '125.gif' },
                    { title: '[惊恐]', file: '126.gif' },
                    { title: '[流汗]', file: '127.gif' },
                    { title: '[憨笑]', file: '128.gif' },
                    { title: '[悠闲]', file: '129.gif' },

                    { title: '[奋斗]', file: '130.gif' },
                    { title: '[咒骂]', file: '131.gif' },
                    { title: '[疑问]', file: '132.gif' },
                    { title: '[嘘]', file: '133.gif' },
                    { title: '[晕]', file: '134.gif' },
                    { title: '[疯了]', file: '135.gif' },
                    { title: '[衰]', file: '136.gif' },
                    { title: '[骷髅]', file: '137.gif' },
                    { title: '[敲打]', file: '138.gif' },
                    { title: '[再见]', file: '139.gif' },

                    { title: '[擦汗]', file: '140.gif' },
                    { title: '[抠鼻]', file: '141.gif' },
                    { title: '[鼓掌]', file: '142.gif' },
                    { title: '[糗大了]', file: '143.gif' },
                    { title: '[坏笑]', file: '144.gif' },
                    { title: '[左哼哼]', file: '145.gif' },
                    { title: '[右哼哼]', file: '146.gif' },
                    { title: '[哈欠]', file: '147.gif' },
                    { title: '[鄙视]', file: '148.gif' },
                    { title: '[委屈]', file: '149.gif' },

                    { title: '[快哭]', file: '150.gif' },
                    { title: '[阴险]', file: '151.gif' },
                    { title: '[亲亲]', file: '152.gif' },
                    { title: '[吓]', file: '153.gif' }
                ]
            };
        },
        computed: {
            showEmoji () {
                // !this.disabled &&
                return this.show_emoji
            },
            disabled () {
                return this.thread.tid === ''
            },
            sendButtonDisabled () {
                return this.inputContent.trim().length === 0
            },
            threadTopic() {
                return 'thread.' + this.thread.tid;
            },
            show_header() {
                return true
            },
            connectedImage () {
                return this.isConnected ?
                    'https://bytedesk.oss-cn-shenzhen.aliyuncs.com/util/connected.png'
                    : 'https://bytedesk.oss-cn-shenzhen.aliyuncs.com/util/disconnected.png'
            }
        },
        methods: {
            maxWindow() {
                // FIXME: 跳转到单独对话页面需要携带原访客uid，否则跨域名跳转无法同步会话，&float=true
                window.open(this.HTTP_HOST + '/visitor/chat?uid=' + this.adminUid +
                    '&wid=' + this.workGroupWid +
                    '&username=' + this.username +
                    '&nickname=' + this.nickname +
                    '&type=workGroup')
            },
            close() {
                // 关闭窗口和对话
                this.byteDeskVisible = false;
                document.getElementById("byteDesk-start").style.display = '';
            },
            switchAgent () {
                this.showLeaveMessage = false;
                this.isRobot = false;
                this.requestThread();
            },
            switchLeaveMessage () {
                console.log('leave message');
                this.showLeaveMessage = true;
            },
            switchRobot () {
                console.log('robot')
                this.showLeaveMessage = false;
                this.isRobot = true;
                this.requestRobot();
            },
            switchEmoji () {
                console.log("switchEmoji");
                // if (!this.disabled) {
                this.show_emoji = !this.show_emoji
                // }
            },
            clearMessages () {
                console.log("clearMessages")
            },
            emotionUrl (file) {
                return this.emojiBaseUrl + file;
            },
            emotionClicked (emotion) {
                this.inputContent += emotion;
                this.show_emoji = false
            },
            imageClicked (imageUrl) {
                // console.log('image clicked:', imageUrl)
                this.currentImageUrl = imageUrl;
                this.imageDialogVisible = true
            },
            fileClicked (fileUrl) {
                window.open(fileUrl)
            },
            voiceClicked (voiceUrl) {
                this.currentVoiceUrl = voiceUrl
                window.open(voiceUrl)
            },
            is_self (message) {
                return message.user.visitor
            },
            // 发送状态
            is_sending (message) {
                return message.status === 'sending'
            },
            is_stored (message) {
                return message.status === 'stored'
            },
            is_received (message) {
                return message.status === 'received'
            },
            is_error (message) {
                return message.status === 'error'
            },
            is_read (message) {
                return message.status === 'readCount'
            },
            // 消息类型
            is_type_text (message) {
                return message.type === 'text'
            },
            is_type_image (message) {
                return message.type === 'image'
            },
            is_type_file (message) {
                return message.type === 'file'
            },
            is_type_voice (message) {
                return message.type === 'voice'
            },
            is_type_robot (message) {
                return message.type === 'robot'
            },
            is_type_questionnaire (message) {
                return message.type === 'questionnaire'
            },
            is_type_company (message) {
                return message.type === 'company'
            },
            is_type_workGroup (message) {
                return message.type === 'workGroup'
            },
            is_type_notification (message) {
                return message.type !== 'notification_preview' && message.type.startsWith('notification')
            },
            player_url () {
                return '' + this.currentVoiceUrl
            },
            //  在发送信息之后，将输入的内容中属于表情的部分替换成emoji图片标签
            //  再经过v-html 渲染成真正的图片
            replaceFace (content) {
                var emotionMap = this.emotionMap
                var reg = /\[[\u4E00-\u9FA5NoOK]+\]/g
                var matchresult = content.match(reg)
                var result = content
                if (matchresult) {
                    for (var i = 0; i < matchresult.length; i++) {
                        result = result.replace(matchresult[i], '<img height=\'25px\' width=\'25px\' style=\'margin-top:5px;\' src=\'' + this.emotionBaseUrl + emotionMap[matchresult[i]] + '\'>')
                    }
                }
                return result
            },
            handleImageDialogClose (done) {
                done()
            },
            scrollToBottom () {
                // 聊天记录滚动到最底部
                let vm = this
                this.$nextTick(() => {
                    const ul = vm.$refs.list
                    if (ul != null) {
                        ul.scrollTop = ul.scrollHeight
                    }
                })
            },
            /**
             * 上传图片相关函数
             */
            handleRemove(file, uploadedImageList) {
                console.log(file, uploadedImageList);
                // 删除
                for (var i = 0 ; i < this.uploadedImageList.length; i++) {
                    if (this.uploadedImageList[i].url === file.url) {
                        this.uploadedImageList.splice(i,1);
                    }
                }
            },
            handlePreview(file) {
                // TODO: 预览
                console.log(file);
            },
            handleExceed(files, uploadedImageList) {
                this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + uploadedImageList.length} 个文件`);
            },
            beforeRemove(file, uploadedImageList) {
                return this.$confirm(`确定移除 ${ file.name }？`);
            },
            handleImageUploadSuccess(result, file) {
                console.log("upload image success result:", result, " file:", file);
                if (result.status_code === 200) {
                    // this.uploadedImageList.push({name: file.name, url: result.data});
                    // 发送消息
                    let imageUrl = result.data;
                    this.sendImageMessage(imageUrl);
                }
                else {
                    this.$message.error("上传图片错误，请稍后重试...");
                }
            },
            beforeImageUpload(file) {
                console.log("before image upload file:", file)
                const isJPGorPNG = (file.type === 'image/jpeg' || file.type === 'image/png');
                const isLt2M = file.size / 1024 / 1024 < 2;
                // 验证图片格式
                if (!isJPGorPNG) {
                    this.$message.error('上传头像图片只能是 jpg 或 png 格式!');
                }
                // 验证图片大小
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                // 设置文件名
                let filename = moment(new Date()).format('YYYYMMDDHHmmss');
                if (file.type === 'image/jpeg') {
                    this.upload_data.file_name = filename + '.jpg';
                }
                else if (file.type === 'image/png') {
                    this.upload_data.file_name = filename + '.png';
                }
                // 返回
                return isJPGorPNG && isLt2M;
            },
            /**
             * 工具函数
             */
            initAxios() {
                // http response 拦截器
                axios.interceptors.response.use(
                    response => {
                        return response
                    },
                    error => {
                        if (error.response) {
                            switch (error.response.status) {
                                // FIXME: 修复因为400引起的错误，多数情况是因为切换测试、线上环境引起
                                // case 400:
                                //     console.log('axios interception error 400');
                                //     break;
                                case 401:
                                    console.log('axios interception error 401');
                                case 403:
                                    // 401 清除token信息并登录
                                    // 403 无权限
                                    console.log('axios interception error 403');
                                    localStorage.removeItem('username');
                                    localStorage.removeItem('nickname');
                                    localStorage.removeItem('uid');
                                    localStorage.removeItem('token');
                                    // localStorage.clear();
                                    // localStorage.removeItem(bytedeskapp.token);
                                    if (bytedeskapp.username !== undefined
                                        && bytedeskapp.username !== null
                                        && bytedeskapp.username !== '') {
                                        bytedeskapp.login()
                                    } else {
                                        bytedeskapp.requestUsername();
                                    }
                            }
                        }
                        return 'return axios interception error'
                    })
            },
            getUrlParam(name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
                var r = window.location.search.substr(1).match(reg);  //匹配目标参数
                if (r != null) return decodeURIComponent(r[2]);
                return null; //返回参数值
            },
            /**
             * 1. 首先判断是否已经注册过
             * 2. 如果已经注册过，则直接调用登录接口
             * 3. 如果没有注册过，则从服务器请求用户名
             *
             * FIXME: 暂未考虑浏览器不支持localStorage的情况
             */
            requestUsername () {
                this.username = localStorage.username;
                this.password = localStorage.password;
                if (this.username) {
                    if (this.password === undefined || this.password === null) {
                        this.password = this.username;
                    }
                    this.login();
                } else {
                    //
                    axios.get( this.HTTP_HOST + '/visitor/api/username', {
                        params: {
                            'subDomain': this.subDomain,
                            'client': this.client
                        }
                    }).then(response => {
                        console.log('username:', response.data);

                        // 登录
                        bytedeskapp.uid = response.data.data.uid;
                        bytedeskapp.username = response.data.data.username;
                        bytedeskapp.password = bytedeskapp.username;
                        bytedeskapp.nickname = response.data.data.nickname;

                        // 本地存储
                        localStorage.uid = bytedeskapp.uid;
                        localStorage.username = bytedeskapp.username;
                        localStorage.nickname = bytedeskapp.nickname;
                        localStorage.password = bytedeskapp.password;

                        // 登录
                        bytedeskapp.login();

                    }).catch(error => {
                        console.log(error)
                    });
                }
            },
            /**
             * 2. oauth2登录
             */
            login () {
                axios({
                    method: 'post',
                    url: this.HTTP_HOST + '/oauth/token',
                    params: {
                        'username': this.username,
                        'password': this.password,
                        'grant_type': 'password',
                        'scope': 'all'
                    },
                    auth: {
                        username: 'client',
                        password: 'secret'
                    }
                }).then(function (response) {
                    console.log('login success: ',response.data);
                    //
                    if (response.data !== null && response.data !== undefined) {
                        // 本地存储，
                        bytedeskapp.passport.token = response.data;
                        // 本地存储
                        localStorage.username = bytedeskapp.username;
                        localStorage.password = bytedeskapp.password;
                        // localStorage 存储
                        localStorage.setItem(bytedeskapp.token, JSON.stringify(response.data));
                        // 建立长连接
                        bytedeskapp.byteDeskConnect();
                        // 通知服务器，访客浏览网页中
                        bytedeskapp.fingerPrint2();
                        // 加载常见问题
                        bytedeskapp.getTopAnswers();
                    }
                }).catch(function (error) {
                    console.log(error)
                })
            },
            /**
             * 获取设备指纹
             * TODO: fingerPrint2 与 browse 合并为一个接口？
             */
            fingerPrint2() {
                new Fingerprint2({
                    preprocessor: function(key, value) {
                        if (key == "user_agent") {
                            // https://github.com/faisalman/ua-parser-js
                            var parser = new UAParser(value);
                            var result = JSON.stringify(parser.getResult());
                            // console.log(result);
                            return result;
                        } else {
                            return value
                        }
                    }
                }).get(function(result, components) {
                    // console.log(result); //a hash, representing your device fingerprint
                    // console.log(components); // an array of FP components
                    const params = new URLSearchParams();
                    params.append('hash', result);
                    for (var index in components) {
                        var obj = components[index];
                        var key = obj.key;
                        var value = obj.value;
                        params.append(key, value.toString());
                    }
                    axios.post(bytedeskapp.HTTP_HOST + "/api/fingerprint2/browser?access_token=" + bytedeskapp.passport.token.access_token, params)
                        .then(response => {
                            // console.log("fingerprint2: ", response.data);
                        }).catch(error => {
                        console.log(error);
                    });
                });
            },
            /**
             * 通知服务器，访客浏览网页中
             * TODO: 修改为POST请求方式
             */
            browse() {
                //
                var keywords = '';//document.getElementsByName('keywords')[0].content;
                var description = '';//document.getElementsByName('description')[0].content;
                var url = window.location.href;
                url = url.endsWith('#') ? url.substring(0, url.length - 1) : url;
                //
                axios.post(this.HTTP_HOST + '/api/browse/notify?access_token=' + this.passport.token.access_token, {
                    adminUid: this.adminUid,
                    workGroupWid: this.workGroupWid,
                    client: this.client,
                    sessionId: this.sessionId,
                    referrer: encodeURI(document.referrer),
                    url: encodeURI(url),
                    title: encodeURI(document.title),
                    keywords: encodeURI(keywords),
                    description: encodeURI(description)
                }).then(response => {
                    console.log('browse:', response.data);
                }).catch(error => {
                    console.log(error)
                });
            },
            acceptInviteBrowse() {
                //
                axios.post(this.HTTP_HOST + '/api/browse/invite/accept?access_token=' + this.passport.token.access_token, {
                    biid: this.browseInviteBIid,
                    client: this.client
                }).then(response => {
                    console.log('browse invite accept:', response.data);
                    // TODO: 打开对话窗口
                    bytedeskapp.byteDeskVisible = true;
                    // FIXME: 应该调用指定客服接口
                    bytedeskapp.requestThread();
                }).catch(error => {
                    console.log(error)
                });
            },
            rejectInviteBrowse() {
                //
                axios.post(this.HTTP_HOST + '/api/browse/invite/reject?access_token=' + this.passport.token.access_token, {
                    biid: this.browseInviteBIid,
                    client: this.client
                }).then(response => {
                    console.log('browse invite reject:', response.data);
                }).catch(error => {
                    console.log(error)
                });
            },
            /**
             * 断开重连之后更新session id
             */
            updateSessionId() {
                console.log('session id:', this.sessionId, ' pre session id:', this.preSessionId);
                axios.post(this.HTTP_HOST + '/api/browse/update/sessionId?access_token=' + this.passport.token.access_token, {
                    sessionId: this.sessionId,
                    preSessionId: this.preSessionId
                }).then(response => {
                    console.log('update session id:', response.data);
                }).catch(error => {
                    console.log(error)
                });
            },
            /**
             * 请求会话
             */
            requestThread() {
                //
                axios.get(this.HTTP_HOST + '/api/thread/request?access_token=' + this.passport.token.access_token, {
                    params: {
                        'uId': this.adminUid,
                        'wId': this.workGroupWid,
                        'type': this.type,
                        'aId': this.agentUid,
                        'client': this.client
                    }
                }).then(response => {
                    console.log('message:', response.data);
                    let message = response.data.data;
                    if (response.data.status_code === 200) {
                        //
                        bytedeskapp.messages.push(message);
                        // 1. 保存thread
                        bytedeskapp.thread = message.thread;
                        // 2. 订阅会话消息
                        bytedeskapp.subscribeTopic(bytedeskapp.threadTopic);
                        // 3. 加载聊天记录
                        bytedeskapp.loadMoreMessages();
                        // 4. 头像、标题、描述
                        if (message.thread.agent) {
                            bytedeskapp.avatar = message.thread.agent.avatar;
                            bytedeskapp.title = message.thread.agent.nickname;
                            bytedeskapp.description = message.thread.agent.description;
                        }

                    } else if (response.data.status_code === 201) {
                        // 继续之前会话
                        let thread = response.data.data;
                        // 1. 保存thread
                        bytedeskapp.thread = thread;
                        // 2. 订阅会话消息
                        bytedeskapp.subscribeTopic(bytedeskapp.threadTopic);
                        // 3. 加载聊天记录
                        bytedeskapp.loadMoreMessages();
                        // 4. 头像、标题、描述
                        if (thread.agent) {
                            bytedeskapp.avatar = thread.agent.avatar;
                            bytedeskapp.title = thread.agent.nickname;
                            bytedeskapp.description = thread.agent.description;
                        }

                    } else if (response.data.status_code === 202) {
                        // 排队
                        bytedeskapp.messages.push(message);
                        // 1. 保存thread
                        bytedeskapp.thread = message.thread;
                    } else if (response.data.status_code === 203) {
                        // 当前非工作时间，请自助查询或留言
                        bytedeskapp.messages.push(message);
                        // 1. 保存thread
                        bytedeskapp.thread = message.thread;
                    } else if (response.data.status_code === 204) {
                        // 当前无客服在线，请自助查询或留言
                        bytedeskapp.messages.push(message);
                        // 1. 保存thread
                        bytedeskapp.thread = message.thread;
                    } else if (response.data.status_code === 205) {
                        // 插入业务路由，相当于咨询前提问问卷（选择 或 填写表单）
                        bytedeskapp.messages.push(message);
                        // 1. 保存thread
                        bytedeskapp.thread = message.thread;
                    } else if (response.data.status_code === -1) {
                        // access token invalid
                        bytedeskapp.login();
                    } else if (response.data.status_code === -2) {
                        // sid 或 wid 错误
                        this.$message.error('siteId或者工作组id错误');
                    }
                    //
                    bytedeskapp.scrollToBottom()
                }).catch(error => {
                    console.log(error)
                });
            },
            requestRobot() {
                console.log("自助答疑")
                this.initAnswer()
            },
            /**
             * 满意度评价
             */
            rate() {
                // 隐藏满意度评价dialog
                this.rateDialogVisible = false;
                // 判断是否已经评价过，避免重复评价
                if (bytedeskapp.isRated) {
                    this.$message({ message: '不能重复评价', type: 'warning' });
                    return;
                }
                axios.post(this.HTTP_HOST + "/api/rate/do?access_token=" + bytedeskapp.passport.token.access_token, {
                    tid: this.thread.tid,
                    score: this.rateScore + "",
                    note: this.rateContent,
                    invite: this.isInviteRate ? "1" : "0",
                    client: 'web'
                }).then(response => {
                    console.log("rate: ", response.data);
                    this.isRated = true
                }).catch(error => {
                    console.log(error);
                });
            },
            /**
             * 加载更多聊天记录
             * TODO: 访客端暂时不开放聊天记录
             */
            loadMoreMessages() {
                // axios.get(this.HTTP_HOST + '/api/messages/user?access_token=' + this.passport.token.access_token, {
                //     params: {
                //         'uid': this.uid,
                //         'page': this.page,
                //         'size': 100,
                //         'client': 'web'
                //     }
                // }).then(response => {
                //     console.log(response.data);
                //     if (response.data.status_code === 200)  {
                //         //
                //         response.data.data.content.forEach(message => {
                //             let contains = bytedeskapp.messages.some(msg => {
                //                 return msg.id === message.id
                //             });
                //             if (!contains) {
                //                 bytedeskapp.messages.unshift(message)
                //             }
                //         });
                //         this.scrollToBottom();
                //     } else {
                //         this.$message.warning(response.data.message)
                //     }
                //
                // }).catch(error => {
                //     console.log(error)
                // })
            },
            /**
             * 加载常见问题
             */
            initAnswer() {
                axios.get(this.HTTP_HOST + '/api/answer/init?access_token=' + this.passport.token.access_token, {
                    params:{
                        'uid': this.adminUid,
                        'tid': this.thread.tid,
                        'client': 'web'
                    }
                })
                    .then(function (response) {
                        console.log("query answer success:",response.data);
                        if (response.data.status_code === 200)  {
                            //
                            let queryMessage = response.data.data;
                            //
                            bytedeskapp.messages.push(queryMessage);
                            bytedeskapp.scrollToBottom()
                        } else {
                            this.$message.warning(response.data.message)
                        }
                    })
                    .catch(function (error) {
                        console.log("query answers error:",error);
                    });
            },
            getTopAnswers() {
                axios.get(this.HTTP_HOST + '/api/answer/top?access_token=' + this.passport.token.access_token, {
                    params:{
                        'uid': this.adminUid,
                        'client': 'web'
                    }
                })
                    .then(function (response) {
                        console.log("fetch answers success:",response.data);
                        if (response.data.status_code === 200)  {
                            bytedeskapp.answers = response.data.data
                        } else {
                            this.$message.warning(response.data.message)
                        }
                    })
                    .catch(function (error) {
                        console.log("fetch answers error:",error);
                    });
            },
            // 根据aid，请求智能答案
            getAnswer(aid) {
                axios.get(this.HTTP_HOST + '/api/answer/query?access_token=' + this.passport.token.access_token, {
                    params:{
                        'uid': this.adminUid,
                        'tid': this.thread.tid,
                        'aid': aid,
                        'client': 'web'
                    }
                })
                    .then(function (response) {
                        console.log("query answer success:",response.data);
                        if (response.data.status_code === 200)  {
                            //
                            let queryMessage = response.data.data.query;
                            let replyMessage = response.data.data.reply;
                            //
                            bytedeskapp.messages.push(queryMessage);
                            bytedeskapp.messages.push(replyMessage);
                            //
                            bytedeskapp.scrollToBottom()
                        } else {
                            this.$message.warning(response.data.message)
                        }
                    })
                    .catch(function (error) {
                        console.log("query answers error:",error);
                    });
            },
            // 更加输入内容，请求智能答案
            messageAnswer(content) {
                axios.get(this.HTTP_HOST + '/api/answer/message?access_token=' + this.passport.token.access_token, {
                    params:{
                        'uid': this.adminUid,
                        'tid': this.thread.tid,
                        'content': content,
                        'client': 'web'
                    }
                })
                    .then(function (response) {
                        console.log("query answer success:",response.data);
                        if (response.data.status_code === 200 ||
                            response.data.status_code === 201)  {
                            //
                            let queryMessage = response.data.data.query;
                            let replyMessage = response.data.data.reply;
                            //
                            bytedeskapp.messages.push(queryMessage);
                            bytedeskapp.messages.push(replyMessage);
                            bytedeskapp.scrollToBottom()
                        } else {
                            this.$message.warning(response.data.message)
                        }
                    })
                    .catch(function (error) {
                        console.log("query answers error:",error);
                    });
            },
            rateAnswer(rate) {
                //
                axios.post(this.HTTP_HOST + '/api/answer/rate?access_token=' + this.passport.token.access_token, {
                    uid: this.adminUid,
                    aid: this.aid,
                    rate: rate,
                    client: 'web'
                }).then(response => {
                    console.log("success:", response.data);
                    if (response.data.status_code === 200)  {
                        //
                    } else {
                        this.$message.warning(response.data.message)
                    }
                }).catch(error => {
                    console.log(error);
                });
            },
            /**
             * 留言
             */
            leaveMessage() {
                this.$refs['leaveMessageForm'].validate((valid) => {
                    if (valid) {
                        axios.post(this.HTTP_HOST + "/api/leavemsg/save?access_token=" + bytedeskapp.passport.token.access_token, {
                            uid: this.adminUid,
                            mobile: this.leaveMessageForm.mobile,
                            email: this.leaveMessageForm.email,
                            content: this.leaveMessageForm.content,
                            client: 'web'
                        }).then(response => {
                            console.log("leave message: ", response.data);
                            if (response.data.status_code === 200) {
                                this.$message({ message: '留言成功', type: 'success'});
                            } else {
                                this.$message.error(response.data.message);
                            }
                        }).catch(error => {
                            console.log(error);
                            this.$message.error('留言失败');
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            /**
             * 选择问卷答案
             */
            chooseQuestionnaire(itemQid) {
                console.log(itemQid);
                axios.get(this.HTTP_HOST + '/api/thread/questionnaire?access_token=' + this.passport.token.access_token, {
                    params:{
                        'tId': this.thread.tid,
                        'itemQid': itemQid,
                        'client': 'web'
                    }
                })
                    .then(function (response) {
                        console.log("choose questionnaire success:",response.data);
                        if (response.data.status_code === 200 ||
                            response.data.status_code === 201)  {
                            //
                            let message = response.data.data;
                            // 添加消息
                            bytedeskapp.messages.push(message);
                            // 滚动到底部
                            bytedeskapp.scrollToBottom()
                        } else {
                            this.$message.warning(response.data.message)
                        }
                    })
                    .catch(function (error) {
                        console.log("choose questionnaire error:",error);
                    });
            },
            /**
             * 选择要留学国家
             */
            chooseCountry(companyCid, countryCid) {
                axios.get(this.HTTP_HOST + '/api/thread/country?access_token=' + this.passport.token.access_token, {
                    params:{
                        'tId': this.thread.tid,
                        'companyCid': companyCid,
                        'countryCid': countryCid,
                        'client': 'web'
                    }
                })
                    .then(function (response) {
                        console.log("choose country success:",response.data);
                        if (response.data.status_code === 200 ||
                            response.data.status_code === 201)  {
                            //
                            let message = response.data.data;
                            // 添加消息
                            bytedeskapp.messages.push(message);
                            // 滚动到底部
                            bytedeskapp.scrollToBottom()
                        } else {
                            this.$message.warning(response.data.message)
                        }
                    })
                    .catch(function (error) {
                        console.log("choose country error:",error);
                    });
            },
            /**
             * 选择工作组
             */
            chooseWorkGroup(wId) {
                axios.get(this.HTTP_HOST + '/api/thread/workGroup?access_token=' + this.passport.token.access_token, {
                    params:{
                        'tId': this.thread.tid,
                        'wId': wId,
                        'client': 'web'
                    }
                })
                    .then(function (response) {
                        console.log("choose workGroup success:",response.data);
                        let message = response.data.data;
                        if (response.data.status_code === 200) {
                            //
                            bytedeskapp.messages.push(message);
                            // 1. 保存thread
                            bytedeskapp.thread = message.thread;
                            // 2. 订阅会话消息
                            bytedeskapp.subscribeTopic(bytedeskapp.threadTopic);
                            // 3. 加载聊天记录
                            bytedeskapp.loadMoreMessages();
                            // 4. 头像、标题、描述
                            if (message.thread.agent) {
                                bytedeskapp.avatar = message.thread.agent.avatar;
                                bytedeskapp.title = message.thread.agent.nickname;
                                bytedeskapp.description = message.thread.agent.description;
                            }

                        } else if (response.data.status_code === 201) {
                            // 继续之前会话
                            let thread = response.data.data;
                            // 1. 保存thread
                            bytedeskapp.thread = thread;
                            // 2. 订阅会话消息
                            bytedeskapp.subscribeTopic(bytedeskapp.threadTopic);
                            // 3. 加载聊天记录
                            bytedeskapp.loadMoreMessages();
                            // 4. 头像、标题、描述
                            if (thread.agent) {
                                bytedeskapp.avatar = thread.agent.avatar;
                                bytedeskapp.title = thread.agent.nickname;
                                bytedeskapp.description = thread.agent.description;
                            }

                        } else if (response.data.status_code === 202) {
                            // 排队
                            bytedeskapp.messages.push(message);
                            // 1. 保存thread
                            bytedeskapp.thread = message.thread;
                        } else if (response.data.status_code === 203) {
                            // 当前非工作时间，请自助查询或留言
                            bytedeskapp.messages.push(message);
                            // 1. 保存thread
                            bytedeskapp.thread = message.thread;
                        } else if (response.data.status_code === 204) {
                            // 当前无客服在线，请自助查询或留言
                            bytedeskapp.messages.push(message);
                            // 1. 保存thread
                            bytedeskapp.thread = message.thread;
                        } else if (response.data.status_code === 205) {
                            // 插入业务路由，相当于咨询前提问问卷（选择 或 填写表单）
                            bytedeskapp.messages.push(message);
                            // 1. 保存thread
                            bytedeskapp.thread = message.thread;
                        } else if (response.data.status_code === -1) {
                            // access token invalid
                            bytedeskapp.login();
                        } else if (response.data.status_code === -2) {
                            // sid 或 wid 错误
                            this.$message.error('siteId或者工作组id错误');
                        }
                        //
                        bytedeskapp.scrollToBottom()
                    })
                    .catch(function (error) {
                        console.log("choose workGroup error:",error);
                    });
            },
            /**
             *
             */
            pushMessage(messageObject) {
                let contains = bytedeskapp.messages.some(message  =>  {
                    return message.id === messageObject.id
                })
                if (!contains) {
                    bytedeskapp.messages.push(messageObject);
                }
            },
            /**
             * 必须添加前缀 '/topic/'
             * @param topic
             */
            subscribeTopic(topic) {
                // 防止重复订阅
                if (this.subscribedTopics.includes(topic)) {
                    return;
                }
                this.subscribedTopics.push(topic);
                //
                bytedeskapp.stompClient.subscribe('/topic/' + topic, function (message) {
                    // console.log('message :', message, 'body:', message.body);
                    var messageObject = JSON.parse(message.body);
                    if (messageObject.type === 'notification_browse_invite') {
                        bytedeskapp.browseInviteBIid = messageObject.browseInvite.bIid;
                        // TODO: 检查是否已经有进行中会话，如果已经存在，则返回

                        // 当前不存在进行中会话
                        bytedeskapp.$msgbox({
                            title: '邀请会话',
                            message: '客服邀请您参加会话',
                            showCancelButton: true,
                            confirmButtonText: '接受',
                            cancelButtonText: '拒绝',
                            beforeClose: (action, instance, done) => {
                                console.log('beforeClose:', action)
                                if (action === 'confirm') {
                                    instance.confirmButtonLoading = true;
                                    instance.confirmButtonText = '接入中...';
                                    //
                                    bytedeskapp.acceptInviteBrowse()
                                } else {
                                    bytedeskapp.rejectInviteBrowse()
                                }
                                //
                                instance.confirmButtonLoading = false;
                                done()
                            }
                        }).then(action => {
                            console.log('then:', action)
                        })
                    } else if (messageObject.type === 'notification_queue') {
                        // 排队
                    } else if (messageObject.type === 'notification_queue_accept') {
                        // 1. 保存thread
                        bytedeskapp.thread = messageObject.thread;
                        // 2. 订阅会话消息
                        bytedeskapp.subscribeTopic(bytedeskapp.threadTopic);
                    } else if (messageObject.type === 'notification_close'
                        || messageObject.type === 'notification_auto_close') {
                        // TODO: 会话关闭，添加按钮方便用户点击重新请求会话
                    }

                    if (messageObject.type !== 'notification_preview') {
                        bytedeskapp.isRobot = false;
                        bytedeskapp.pushMessage(messageObject);
                        bytedeskapp.scrollToBottom();
                    } else {
                        // TODO: 监听客服端输入状态
                    }

                    // https://stomp-js.github.io/stomp-websocket/codo/extra/docs-src/Usage.md.html
                    // and acknowledge it
                    // FIXME: PRECONDITION_FAILED - unknown delivery tag 8
                    // message.ack()
                    // }, {ack: 'client'});
                });
            },
            /**
             * 订阅一对一会话,
             * 必须携带前缀 '/user/'
             *
             * @param queue
             */
            subscribeQueue(queue) {
                bytedeskapp.stompClient.subscribe('/user/queue/' + queue, function (message) {
                    console.log(queue , ":", message, 'body:', message.body);
                });
            },
            /**
             * 输入框变化
             */
            onInputChange (content) {
                console.log(content);
                // if (content.trim().length > 0) {
                this.stompClient.send("/app/" + this.threadTopic, {}, JSON.stringify({'type': 'notification_preview', 'content': content, 'client': 'web'}));
                // }
            },
            /**
             * 发送消息
             */
            onKeyUp(e) {
                if (e.keyCode === 13 && this.inputContent.trim().length > 0) {
                    this.inputContent = this.inputContent.trim();
                    this.sendTextMessage()
                }
            },
            sendTextMessage () {
                //
                if (this.inputContent.trim().length === 0) {
                    return;
                }
                //
                if (this.isRobot) {
                    this.messageAnswer(this.inputContent);
                } else {
                    // 发送/广播会话消息
                    this.stompClient.send("/app/" + this.threadTopic, {}, JSON.stringify({'type': 'text', 'chat': 'thread', 'content': this.inputContent, 'client': 'web'}));
                }
                // 清空输入框
                this.inputContent = "";
                // 清空消息预知
                this.stompClient.send("/app/" + this.threadTopic, {}, JSON.stringify({'type': 'notification_preview', 'content': '', 'client': 'web'}));
            },
            sendImageMessage (imageUrl) {
                // 发送/广播会话消息
                this.stompClient.send("/app/" + this.threadTopic, {}, JSON.stringify({'type': 'image', 'chat': 'thread', 'imageUrl': imageUrl, 'client': 'web'}));
            },
            /**
             * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#websocket-stomp-authentication
             *
             * https://stomp.bytedesk.com
             */
            byteDeskConnect() {
                var socket = new SockJS(this.STOMP_HOST + '/stomp/?access_token=' + this.passport.token.access_token);
                this.stompClient = Stomp.over(socket);
                this.stompClient.reconnect_delay = 1000;
                // client will send heartbeats every 10000ms, default 10000
                this.stompClient.heartbeat.outgoing = 20000;
                // client does not want to receive heartbeats from the server, default 10000
                this.stompClient.heartbeat.incoming = 20000;
                // to disable logging, set it to an empty function:
                // this.stompClient.debug = function (value) {}
                // 连接bytedesk，如果后台开启了登录，需要登录之后才行
                this.stompClient.connect({}, function (frame) {
                    // console.log('stompConnected: ' + frame + " username：" + frame.headers['user-name']);
                    bytedeskapp.isConnected = true;
                    // 获取 websocket 连接的 sessionId
                    // FIXME: Uncaught TypeError: Cannot read property '1' of null
                    let sessionUrl = /\/([^\/]+)\/websocket/.exec(socket._transport.url)
                    if (sessionUrl != null) {
                        bytedeskapp.sessionId = /\/([^\/]+)\/websocket/.exec(socket._transport.url)[1];
                    }
                    // console.log("connected, sessionId: " + bytedeskapp.sessionId);
                    // 订阅会话消息，处理断开重连的情况
                    if (bytedeskapp.thread.tid !== null && bytedeskapp.thread.tid !== undefined && bytedeskapp.thread.tid !== '') {
                        bytedeskapp.subscribeTopic(bytedeskapp.threadTopic);
                    }
                    // 订阅个人消息, 接受邀请会话
                    bytedeskapp.subscribeTopic('user.' + bytedeskapp.uid);
                    // 订阅错误消息
                    // bytedeskapp.subscribeQueue('errors');
                    // 重连更新session id
                    if (bytedeskapp.isReconnect) {
                        bytedeskapp.updateSessionId();
                    } else {
                        // 通知服务器，访客浏览网页中
                        bytedeskapp.browse();
                    }
                    //
                    if (!bytedeskapp.byteDeskVisible) {
                        // 建立连接之后显示开始会话按钮
                        document.getElementById("byteDesk-start").style.display = '';
                    }
                }, function (error) {
                    console.log('连接断开【' + error + '】');
                    bytedeskapp.isConnected = false;
                    bytedeskapp.preSessionId = bytedeskapp.sessionId;
                    // 为断开重连做准备
                    bytedeskapp.subscribedTopics = [];
                    // 10秒后重新连接，实际效果：每10秒重连一次，直到连接成功
                    setTimeout(function () {
                        console.log("reconnecting...");
                        bytedeskapp.isReconnect = true;
                        bytedeskapp.byteDeskConnect();
                    }, 10000);
                })
            }
        },
        directives: {
            focus: {
                // When the bound element is inserted into the DOM...
                inserted: function (el) {
                    el.focus()
                }
            }
        },
        created() {
            // 监听点击打开关闭窗口事件
            // document.getElementById("byteDesk-start")
            // FIXME: 仅能够匹配第一个？
            document.querySelector('[id^="byteDesk-start"]').addEventListener("click", function(){
                // TODO: 处理没有连接成功的情况
                if (!bytedeskapp.isConnected) {
                    //
                }
                //
                bytedeskapp.byteDeskVisible = !bytedeskapp.byteDeskVisible;
                // TODO: 判断是否已经开启会话thread，如果没有，则请求会话
                if (bytedeskapp.thread.tid === null
                    || bytedeskapp.thread.tid === undefined
                    || bytedeskapp.thread.tid === '') {
                    //
                    if (bytedeskapp.passport.token.access_token !== null
                        && bytedeskapp.passport.token.access_token !== undefined
                        && bytedeskapp.passport.token.access_token !== '') {
                        // 窗口打开的时候，请求
                        if (bytedeskapp.byteDeskVisible) {
                            // console.log('show');
                            document.getElementById("byteDesk-app-wrapper").style.display = '';
                            document.getElementById("byteDesk-start").style.display = 'none';
                            //
                            bytedeskapp.requestThread();
                        }
                    } else {
                        bytedeskapp.login()
                    }
                }
                // toggle
                if (bytedeskapp.byteDeskVisible) {
                    document.getElementById("byteDesk-start").style.display = 'none';
                }
            });
            //
            this.adminUid = window.adminUid;
            this.workGroupWid = window.workGroupWid;
            this.subDomain = window.subDomain;
            this.type = window.type;
            this.agentUid = window.agentUid;
            this.uid = localStorage.uid;
            this.username = localStorage.username;
            this.password = localStorage.password;
            if (this.password === undefined || this.password === null) {
                this.password = this.username;
            }
            this.nickname = localStorage.nickname;
            //
            var tokenLocal = localStorage.getItem(this.token);
            if (tokenLocal !== undefined && tokenLocal !== 'undefined') {
                this.passport.token = JSON.parse(tokenLocal);
            }
            // console.log("adminUid：" + this.adminUid + " workGroupWid:" + this.workGroupWid + " subDomain:" + this.subDomain)
        },
        mounted() {
            this.initAxios();
            if (this.username !== null
                && this.username !== undefined
                && this.username !== '') {
                this.login()
            } else {
                this.requestUsername();
            }
        },
        filters: {
            datetime (value) {
                if (typeof value === 'string') {
                    return moment(value).format('MM-DD HH:mm:ss')
                } else {
                    return value
                }
            }
        }
    });

})();