/**
 * bytedesk.com
 */
var httpapi = {
    /**
     * 工具函数
     */
    initAxios: function () {
        // http response 拦截器
    },
    /**
     * 1. 首先判断是否已经注册过
     * 2. 如果已经注册过，则直接调用登录接口
     * 3. 如果没有注册过，则从服务器请求用户名
     * FIXME: 暂未考虑浏览器不支持localStorage的情况
     */
    requestUsername: function () {
        //
        data.username = localStorage.username;
        data.password = localStorage.password;
        if (data.username) {
            if (data.password == null) {
                data.password = data.username;
            }
            httpapi.login();
        } else {
            //
            $.ajax({
                url: data.HTTP_HOST + "/visitor/api/username",
                contentType: "application/json; charset=utf-8",
                type: "get",
                data: {
                    subDomain: data.subDomain,
                    client: data.client
                },
                success:function(response){
                    // 登录
                    data.uid = response.data.uid;
                    data.username = response.data.username;
                    data.password = data.username;
                    data.nickname = response.data.nickname;
                    // 本地存储
                    localStorage.uid = data.uid;
                    localStorage.username = data.username;
                    localStorage.password = data.password;
                    // 登录
                    httpapi.login();
                },
                error: function(error) {
                    //Do Something to handle error
                    console.log(error);
                }
            });
        }
    },
    /**
     * 保存自定义用户名
     */
    saveUsername: function () {
        //
        var username = utils.getUrlParam("username");
        var nickname =
            utils.getUrlParam("nickname") == null
                ? username
                : utils.getUrlParam("username");
        console.log("username self:", username, nickname);
        //
        $.ajax({
            url: data.HTTP_HOST + "/visitor/api/register/user",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: "post",
            data: JSON.stringify({
                username: username,
                nickname: nickname,
                password: username, // 用户名作为默认密码
                subDomain: data.subDomain,
                client: data.client
            }),
            success:function(response){
                // 登录
                data.uid = response.data.uid;
                data.username = response.data.username;
                data.password = data.username;
                data.nickname = response.data.nickname;
                // 本地存储
                localStorage.uid = data.uid;
                localStorage.username = data.username;
                localStorage.password = data.password;
                // 登录
                httpapi.login();
            },
            error: function(error) {
                //Do Something to handle error
                console.log(error);
            }
        });
    },
    /**
     * 2. oauth2登录
     */
    login: function () {
        console.log('do login: ', data.username, data.password);
        //
        $.ajax({
            url: data.HTTP_HOST + "/oauth/token",
            type: "post",
            data: {
                "username": data.username,
                "password": data.password,
                "grant_type": "password",
                "scope": "all"
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Basic Y2xpZW50OnNlY3JldA==');
            },
            success:function(response){
                console.log("login success: ", response);
                // 本地存储，
                data.passport.token = response;
                // 本地存储
                localStorage.username = data.username;
                localStorage.password = data.password;
                localStorage.subDomain = data.subDomain;
                // localStorage 存储
                localStorage.setItem(data.token, JSON.stringify(response));
                // 请求会话
                httpapi.requestThread();
                // 加载常见问题
                httpapi.getTopAnswers();
            },
            error: function(error) {
                //Do Something to handle error
                console.log(error);
            }
        });
    },
    /**
     * 获取设备指纹
     */
    fingerPrint2: function () {
        // new Fingerprint2({
        //   preprocessor(key, value) {
        //     if (key == "user_agent") {
        //       // http://github.com/faisalman/ua-parser-js
        //       var parser = new UAParser(value);
        //       var result = JSON.stringify(parser.getResult());
        //       console.log(result);
        //       return result;
        //     } else {
        //       return value;
        //     }
        //   }
        // }).get(function (result, components) {
        //   // console.log(result); //a hash, representing your device fingerprint
        //   // console.log(components); // an array of FP components
        //   var params = new URLSearchParams();
        //   params.append("hash", result);
        //   for (var index in components) {
        //     var obj = components[index];
        //     var key = obj.key;
        //     var value = obj.value;
        //     params.append(key, value.toString());
        //   }
        //   $.ajax({
        //     url: data.HTTP_HOST +
        //     "/api/fingerprint2/browser?access_token=" +
        //     data.passport.token.access_token,
        //     type: "post",
        //     data: params,
        //     success:function(response){
        //       console.log("fingerprint2: ", response);
        //     },
        //     error: function(error) {
        //       //Do Something to handle error
        //       console.log(error);
        //     }
        //   });
        // });
    },
    /**
     * 通知服务器，访客浏览网页中
     * TODO: 修改为POST请求方式
     */
    browse: function () {
        //
        var keywords = document.getElementsByName("keywords")[0].content;
        var description = document.getElementsByName("description")[0].content;
        var url = window.location.href;
        url = url.endsWith("#") ? url.substring(0, url.length - 1) : url;
        //
        $.ajax({
            url: data.HTTP_HOST +
            "/api/browse/notify?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: "post",
            data: JSON.stringify({
                adminUid: data.adminUid,
                workGroupWid: data.workGroupWid,
                client: data.client,
                sessionId: data.sessionId,
                referrer: encodeURI(document.referrer),
                url: encodeURI(url),
                title: encodeURI(document.title),
                keywords: encodeURI(keywords),
                description: encodeURI(description)
            }),
            success:function(response){
                console.log("browse invite accept:", response.data);
            },
            error: function(error) {
                //Do Something to handle error
                console.log(error);
            }
        });
    },
    acceptInviteBrowse: function () {
        //
        $.ajax({
            url: data.HTTP_HOST +
            "/api/browse/invite/accept?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: "post",
            data: JSON.stringify({
                biid: data.browseInviteBIid,
                client: data.client
            }),
            success:function(response){
                console.log("browse invite accept:", response.data);
            },
            error: function(error) {
                //Do Something to handle error
                console.log(error);
            }
        });
    },
    rejectInviteBrowse: function () {
        //
        $.ajax({
            url: data.HTTP_HOST +
            "/api/browse/invite/reject?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: "post",
            data: JSON.stringify({
                biid: data.browseInviteBIid,
                client: data.client
            }),
            success:function(response){
                console.log("browse invite reject:", response.data);
            },
            error: function(error) {
                console.log(error);
            }
        });
    },
    /**
     * 断开重连之后更新session id
     */
    updateSessionId: function () {
        // console.log('session id:', data.sessionId, ' pre session id:', data.preSessionId);
        $.ajax({
            url: data.HTTP_HOST +
            "/api/browse/update/sessionId?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: "post",
            data: JSON.stringify({
                sessionId: data.sessionId,
                preSessionId: data.preSessionId
            }),
            success:function(response){
                console.log("update session id:", response.data);
            },
            error: function(error) {
                console.log(error);
            }
        });
    },
    /**
     * 请求会话
     */
    requestThread: function () {
        console.log('start request thread')
        $.ajax({
            url: data.HTTP_HOST +
            "/api/thread/request?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            type: "get",
            data: {
                uId: data.adminUid,
                wId: data.workGroupWid,
                type: data.type,
                aId: data.agentUid,
                client: data.client
            },
            success:function(response){
                console.log("message:", response.data);
                var message = response.data;
                if (response.status_code === 200) {
                    //
                    utils.pushToMessageArray(message);
                    // 1. 保存thread
                    data.thread = message.thread;
                    // 3. 加载聊天记录
                    httpapi.loadMoreMessages();
                    // 4. 设置窗口左上角标题
                    if (data.thread.appointed) {
                        data.title = data.thread.agent.nickname;
                    } else {
                        data.title = data.thread.workGroup.nickname;
                    }
                } else if (response.status_code === 201) {
                    message.content = "继续之前会话";
                    utils.pushToMessageArray(message);
                    // 1. 保存thread
                    data.thread = message.thread;
                    // 3. 加载聊天记录
                    httpapi.loadMoreMessages();
                    // 4. 头像、标题、描述
                    if (data.thread.appointed) {
                        data.title = data.thread.agent.nickname;
                    } else {
                        data.title = data.thread.workGroup.nickname;
                    }
                } else if (response.status_code === 202) {
                    // 排队
                    utils.pushToMessageArray(message);
                    // 1. 保存thread
                    data.thread = message.thread;
                } else if (response.status_code === 203) {
                    // 当前非工作时间，请自助查询或留言
                    utils.pushToMessageArray(message);
                    // 1. 保存thread
                    data.thread = message.thread;
                    // 4. 设置窗口左上角标题
                    if (data.thread.appointed) {
                        data.title = data.thread.agent.nickname;
                    } else {
                        data.title = data.thread.workGroup.nickname;
                    }
                } else if (response.status_code === 204) {
                    // 当前无客服在线，请自助查询或留言
                    utils.pushToMessageArray(message);
                    // 1. 保存thread
                    data.thread = message.thread;
                    // 4. 设置窗口左上角标题
                    if (data.thread.appointed) {
                        data.title = data.thread.agent.nickname;
                    } else {
                        data.title = data.thread.workGroup.nickname;
                    }
                    // TODO: 显示留言界面
                    utils.switchLeaveMessage();
                } else if (response.status_code === 205) {
                    // 插入业务路由，相当于咨询前提问问卷（选择 或 填写表单）
                    utils.pushToMessageArray(message);
                    // 1. 保存thread
                    data.thread = message.thread;
                } else if (response.status_code === -1) {
                    httpapi.login();
                } else if (response.status_code === -2) {
                    // sid 或 wid 错误
                    alert("siteId或者工作组id错误");
                } else if (response.status_code === -3) {
                    alert("您已经被禁言");
                }
                utils.scrollToBottom();
                // 建立长连接
                stompapi.byteDeskConnect();
            },
            error: function(error) {
                console.log(error);
            }
        });
        // 请求指纹
        httpapi.fingerPrint2();
    },
    /**
     *
     */
    requestRobot: function () {
        console.log("自助答疑");
        httpapi.initAnswer();
    },
    /**
     * 满意度评价
     */
    rate: function () {
        // 隐藏满意度评价dialog
        data.rateDialogVisible = false;
        // 判断是否已经评价过，避免重复评价
        if (data.isRated) {
            alert("不能重复评价");
            return;
        }
        data.rateContent = $("#suggestcontent").val();
        //
        $.ajax({
            url: data.HTTP_HOST +
            "/api/rate/do?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: "post",
            data: JSON.stringify({
                uid: data.adminUid,
                wid: data.workGroupWid,
                aid: data.agentUid,
                type: data.type,
                tid: data.thread.tid,
                score: data.rateScore + "", // 考虑到兼容ios客户端，需要转换为字符串
                note: data.rateContent,
                invite: data.isInviteRate ? "1" : "0", // 考虑到兼容ios客户端，需要转换为字符串
                client: data.client
            }),
            success:function(response){
                console.log("rate: ", response.data);
                data.isRated = true;
                //
                if (response.status_code === 200) {
                    alert("评价成功");
                    $("#byteDesk-chat").show();
                    $("#byteDesk-rate").hide();
                } else {
                    alert(response.message);
                }
            },
            error: function(error) {
                console.log(error);
                alert(error);
            }
        });
    },
    /**
     * 关闭当前窗口
     */
    closeWebPage: function () {
        $.ajax({
            url: data.HTTP_HOST +
            "/api/thread/visitor/close?access_token=" +
            data.passport.token.access_token,
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify({
                tid: data.thread.tid,
                client: data.client
            }),
            success:function(response){
                console.log("close thread: ", response.data);
                // 关闭当前窗口
                if (navigator.userAgent.indexOf("MSIE") > 0) {
                    if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
                        window.opener = null;
                        window.close();
                    } else {
                        window.open("", "_top");
                        window.top.close();
                    }
                } else if (navigator.userAgent.indexOf("Firefox") > 0) {
                    window.location.href = "about:blank ";
                    window.opener = null;
                    window.open("", "_self", "");
                    window.close();
                } else {
                    window.opener = null;
                    window.open("", "_self", "");
                    window.close();
                }
            },
            error: function(error) {
                console.log(error);
                alert(error);
            }
        });
    },
    /**
     * 加载更多聊天记录
     * TODO: 访客端暂时不开放聊天记录
     */
    loadMoreMessages: function () {
        $.ajax({
            url: data.HTTP_HOST +
            "/api/messages/user?access_token=" +
            data.passport.token.access_token,
            type: "get",
            data: {
                uid: data.uid,
                page: data.page,
                size: 100,
                client: data.client
            },
            success:function(response){
                console.log(response.data);
                if (response.status_code === 200) {
                    //
                    for (var i = 0; i < response.data.content.length; i++) {
                        var message = response.data.content[i];
                        //
                        var contains = false;
                        for (var j = 0; j < data.messages.length; j++) {
                            var msg = data.messages[j];
                            if (msg.id === message.id) {
                                contains = true;
                            }
                        }
                        if (!contains) {
                            data.messages.push(message);
                        }
                    }
                    utils.scrollToBottom();
                } else {
                    alert(response.message);
                }
            },
            error: function(error) {
                console.log(error);
                alert(error);
            }
        });
    },
    /**
     * 加载常见问题
     */
    initAnswer: function () {
        $.ajax({
            url: data.HTTP_HOST +
            "/api/answer/init?access_token=" +
            data.passport.token.access_token,
            type: "get",
            data: {
                uid: data.adminUid,
                tid: data.thread.tid,
                client: data.client
            },
            success:function(response){
                console.log("query answer success:", response.data);
                if (response.status_code === 200) {
                    //
                    var queryMessage = response.data;
                    //
                    utils.pushToMessageArray(queryMessage);
                    utils.scrollToBottom();
                } else {
                    alert(response.message);
                }
            },
            error: function(error) {
                console.log("query answers error:", error);
            }
        });
    },
    /**
     *
     */
    getTopAnswers: function () {
        $.ajax({
            url: data.HTTP_HOST +
            "/api/answer/top?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            type: "get",
            data: {
                uid: data.adminUid,
                client: data.client
            },
            success:function(response){
                console.log("fetch answers success:", response.data);
                if (response.status_code === 200) {
                    data.answers = response.data;
                    utils.pushAnswers(data.answers.content);
                } else {
                    alert(response.message);
                }
            },
            error: function(error) {
                console.log("fetch answers error:", error);
            }
        });
    },
    //
    getAnswer: function (aid) {
        $.ajax({
            url: data.HTTP_HOST +
            "/api/answer/query?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            type: "get",
            data: {
                uid: data.adminUid,
                tid: data.thread.tid,
                aid: aid,
                client: data.client
            },
            success:function(response){
                console.log("query answer success:", response.data);
                if (response.status_code === 200) {
                    //
                    var queryMessage = response.data.query;
                    var replyMessage = response.data.reply;
                    //
                    utils.pushToMessageArray(queryMessage);
                    utils.pushToMessageArray(replyMessage);
                    utils.scrollToBottom();
                } else {
                    alert(response.message);
                }
            },
            error: function(error) {
                console.log("query answers error:", error);
            }
        });
    },
    // 输入内容，请求智能答案
    messageAnswer: function (content) {
        $.ajax({
            url: data.HTTP_HOST +
            "/api/answer/message?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            type: "get",
            data: {
                uid: data.adminUid,
                tid: data.thread.tid,
                content: content,
                client: data.client
            },
            success:function(response){
                console.log("query answer success:", response.data);
                if (
                    response.status_code === 200 ||
                    response.status_code === 201
                ) {
                    //
                    var queryMessage = response.data.query;
                    var replyMessage = response.data.reply;
                    //
                    utils.pushToMessageArray(queryMessage);
                    utils.pushToMessageArray(replyMessage);
                    utils.scrollToBottom();
                } else {
                    alert(response.message);
                }
            },
            error: function(error) {
                console.log("query answers error:", error);
            }
        });
    },
    rateAnswer: function (rate) {
        //
        $.ajax({
            url: data.HTTP_HOST +
            "/api/answer/rate?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: "post",
            data: JSON.stringify({
                uid: data.adminUid,
                aid: data.aid,
                rate: rate,
                client: data.client
            }),
            success:function(response){
                console.log("success:", response.data);
                if (response.status_code === 200) {
                    //
                } else {
                    alert(response.message);
                }
            },
            error: function(error) {
                console.log("query answers error:", error);
            }
        });
    },
    /**
     * 留言
     */
    leaveMessage: function () {
        var mobile = $("#leavemsgmobile").val();
        var email = $("#leavemsgemail").val();
        var content = $("#leavemsgcontent").val();
        $.ajax({
            url: data.HTTP_HOST +
            "/api/leavemsg/save?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: "post",
            data: JSON.stringify({
                uid: data.adminUid,
                wid: data.workGroupWid,
                aid: data.agentUid,
                type: data.type,
                mobile: mobile,
                email: email,
                content: content,
                client: data.client
            }),
            success:function(response){
                console.log("leave message: ", response.data);
                if (response.status_code === 200) {
                    alert("留言成功");
                    $("#byteDesk-chat").show();
                    $("#byteDesk-leave").hide();
                } else {
                    alert(response.message);
                }
            },
            error: function(error) {
                console.log(error);
                alert("留言失败");
            }
        });
    },
    /**
     * 选择问卷答案
     */
    chooseQuestionnaire: function (itemQid) {
        console.log("choose questionnaire: " + itemQid);
        // 只允许同时进行一个会话
        if (data.isThreadStarted) {
            alert("不能重复请求");
            return;
        }
        $.ajax({
            url: data.HTTP_HOST +
            "/api/thread/questionnaire?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            type: "get",
            data: {
                tId: data.thread.tid,
                itemQid: itemQid,
                client: data.client
            },
            success:function(response){
                console.log("choose questionnaire success:", response.data);
                if (
                    response.status_code === 200 ||
                    response.status_code === 201
                ) {
                    //
                    var message = response.data;
                    // 添加消息
                    utils.pushToMessageArray(message);
                    // 滚动到底部
                    utils.scrollToBottom();
                } else {
                    alert(response.message);
                }
            },
            error: function(error) {
                console.log("choose questionnaire error:", error);
            }
        });
    },
    /**
     * 选择要留学国家
     */
    chooseCountry: function (companyCid, countryCid) {
        $.ajax({
            url: data.HTTP_HOST +
            "/api/thread/country?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            type: "get",
            data: {
                tId: data.thread.tid,
                companyCid: companyCid,
                countryCid: countryCid,
                client: data.client
            },
            success:function(response){
                console.log("choose country success:", response.data);
                if (
                    response.status_code === 200 ||
                    response.status_code === 201
                ) {
                    //
                    var message = response.data;
                    // 添加消息
                    utils.pushToMessageArray(message);
                    // 滚动到底部
                    utils.scrollToBottom();
                } else {
                    alert(response.message);
                }
            },
            error: function(error) {
                console.log("choose country error:", error);
            }
        });
    },
    /**
     * 选择工作组
     */
    chooseWorkGroup: function (wId) {
        console.log("choose workgroup:", wId);
        // 只允许同时进行一个会话
        if (data.isThreadStarted) {
            alert("不能重复请求");
            return;
        }
        $.ajax({
            url: data.HTTP_HOST +
            "/api/thread/choose/workGroup?access_token=" +
            data.passport.token.access_token,
            contentType: "application/json; charset=utf-8",
            type: "get",
            data: {
                tId: data.thread.tid,
                wId: wId,
                client: data.client
            },
            success:function(response){
                console.log("choose workGroup success:", response.data);
                var message = response.data;
                if (response.status_code === 200) {
                    //
                    utils.pushToMessageArray(message);
                    // 1. 保存thread
                    data.thread = message.thread;
                    // 2. 订阅会话消息
                    stompapi.subscribeTopic(data.threadTopic());
                    // 3. 加载聊天记录
                    httpapi.loadMoreMessages();
                    // 4. 头像、标题、描述
                    if (data.thread.appointed) {
                        data.title = data.thread.agent.nickname;
                    } else {
                        data.title = data.thread.workGroup.nickname;
                    }
                    // 防止重复点击
                    data.isThreadStarted = true;
                } else if (response.status_code === 201) {
                    // message.content = '继续之前会话';
                    utils.pushToMessageArray(message);
                    // 1. 保存thread
                    data.thread = message.thread;
                    // 2. 订阅会话消息
                    stompapi.subscribeTopic(data.threadTopic());
                    // 3. 加载聊天记录
                    httpapi.loadMoreMessages();
                    // 4. 头像、标题、描述
                    if (data.thread.appointed) {
                        data.title = data.thread.agent.nickname;
                    } else {
                        data.title = data.thread.workGroup.nickname;
                    }
                    // 防止重复点击
                    data.isThreadStarted = true;
                } else if (response.status_code === 202) {
                    // 排队
                    utils.pushToMessageArray(message);
                    // 1. 保存thread
                    data.thread = message.thread;
                    // 防止重复点击
                    data.isThreadStarted = true;
                } else if (response.status_code === 203) {
                    // 当前非工作时间，请自助查询或留言
                    utils.pushToMessageArray(message);
                    // 1. 保存thread
                    data.thread = message.thread;
                } else if (response.status_code === 204) {
                    // 当前无客服在线，请自助查询或留言
                    utils.pushToMessageArray(message);
                    // 1. 保存thread
                    data.thread = message.thread;
                } else if (response.status_code === 205) {
                    // 插入业务路由，相当于咨询前提问问卷（选择 或 填写表单）
                    utils.pushToMessageArray(message);
                    // 1. 保存thread
                    data.thread = message.thread;
                } else if (response.status_code === -1) {
                    // access token invalid
                    httpapi.login();
                } else if (response.status_code === -2) {
                    // sid 或 wid 错误
                    alert("siteId或者工作组id错误");
                } else if (response.status_code === -3) {
                    alert("您已经被禁言");
                }
                //
                utils.scrollToBottom();
            },
            error: function(error) {
                console.log("choose workGroup error:", error);
            }
        });
    }
};

