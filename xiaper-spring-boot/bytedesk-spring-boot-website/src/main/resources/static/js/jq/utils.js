/**
 * bytedesk.com
 */
var utils = {
  //
  switchEmoji: function() {
    if (!data.disabled) {
      data.show_emoji = !data.show_emoji;
    }
  },
  switchAgent: function() {
    data.showLeaveMessage = false;
    $("#byteDesk-chat").show();
    $("#byteDesk-leave").hide();
    $("#byteDesk-rate").hide();
    data.isRobot = false;
    httpapi.requestThread();
  },
  switchLeaveMessage: function() {
    console.log("switch leave message");
    data.showLeaveMessage = true;
    $("#byteDesk-chat").hide();
    $("#byteDesk-leave").show();
    $("#byteDesk-rate").hide();
  },
  hideLeaveMessage: function() {
    data.showLeaveMessage = false;
    $("#byteDesk-chat").show();
    $("#byteDesk-leave").hide();
  },
  switchRobot: function() {
    console.log("switch robot");
    data.showLeaveMessage = false;
    $("#byteDesk-chat").show();
    $("#byteDesk-leave").hide();
    $("#byteDesk-rate").hide();
    data.isRobot = true;
    httpapi.requestRobot();
  },
  switchEmotion: function() {
    console.log("switch emotion");
    data.show_emoji = !data.show_emoji;
    if (data.show_emoji) {
      $("#input-emoji-box").show();
      console.log('show')
    } else {
      $("#input-emoji-box").hide();
      console.log('hide')
    }
  },
  switchRate: function() {
    console.log("switch rate");
    data.rateDialogVisible = true;
    $("#byteDesk-chat").hide();
    $("#byteDesk-leave").hide();
    $("#byteDesk-rate").show();
  },
  hideRate: function() {
    data.rateDialogVisible = false;
    $("#byteDesk-chat").show();
    $("#byteDesk-rate").hide();
  },
  showUploadDialog: function() {
    console.log("show upload dialog");
    if (data.isRobot) {
      alert("自助服务暂不支持图片");
      return;
    }
    if (data.isThreadClosed) {
      alert("会话已经结束");
      return;
    }
    $('input[id=imagefile]').click();
  },
  clearMessages: function() {
    console.log("clearMessages");
  },
  emotionUrl: function(file) {
    return data.emojiBaseUrl + file;
  },
  // emotionClicked: function(emotion) {
  //   data.inputContent += emotion;
  //   data.show_emoji = false;
  // },
  imageClicked: function(imageUrl) {
    // console.log('image clicked:', imageUrl)
    data.currentImageUrl = imageUrl;
    window.open(imageUrl);
    // data.imageDialogVisible = true;
  },
  fileClicked: function(fileUrl) {
    window.open(fileUrl);
  },
  voiceClicked: function(voiceUrl) {
    data.currentVoiceUrl = voiceUrl;
    window.open(voiceUrl);
  },
  //
  is_self: function(message) {
    return message.user.visitor;
  },
  // 发送状态
  is_sending: function(message) {
    return message.status === "sending";
  },
  is_stored: function(message) {
    return message.status === "stored";
  },
  is_received: function(message) {
    return message.status === "received";
  },
  is_error: function(message) {
    return message.status === "error";
  },
  is_read: function(message) {
    return message.status === "readCount";
  },
  // 消息类型
  is_type_text: function(message) {
    return (
      message.type === "text" ||
      message.type === "notification_thread" ||
      message.type === "notification_auto_close"
    );
  },
  is_type_robot: function(message) {
    return message.type === "robot";
  },
  is_type_image: function(message) {
    return message.type === "image";
  },
  is_type_file: function(message) {
    return message.type === "file";
  },
  is_type_voice: function(message) {
    return message.type === "voice";
  },
  is_type_questionnaire: function(message) {
    return message.type === "questionnaire";
  },
  is_type_company: function(message) {
    return message.type === "company";
  },
  is_type_workGroup: function(message) {
    return message.type === "workGroup";
  },
  is_type_notification: function(message) {
    return (
      message.type !== "notification_preview" &&
      message.type !== "notification_thread" &&
      message.type !== "notification_auto_close" &&
      message.type.startsWith("notification")
    );
  },
  //  在发送信息之后，将输入的内容中属于表情的部分替换成emoji图片标签
  //  再经过v-html 渲染成真正的图片
  replaceFace: function(content) {
    if (content === null || content === undefined) {
      return "";
    }
    var emotionMap = data.emotionMap;
    var reg = /\[[\u4E00-\u9FA5NoOK]+\]/g;
    var matchresult = content.match(reg);
    var result = content;
    if (matchresult) {
      for (var i = 0; i < matchresult.length; i++) {
        result = result.replace(
          matchresult[i],
          "<img height='25px' width='25px' style='margin-bottom:4px;' src='" +
            data.emotionBaseUrl +
            emotionMap[matchresult[i]] +
            "'>"
        );
      }
    }
    return result;
  },
  //
  scrollToBottom: function() {
    // 聊天记录滚动到最底部
    $("#message-ul").animate(
      { scrollTop: $("#message-ul")[0].scrollHeight },
      "slow"
    );
  },
  //
  pushToMessageArray: function(message) {
    // var contains = data.messages.some(msg => {
    //   return msg.id === message.id;
    // });
    var contains = false;
    for (var i = 0; i < data.messages.length; i++) {
      var msg = data.messages[i];
      if (msg.id === message.id) {
        contains = true;
      }
    }
    if (!contains) {
      data.messages.push(message);
    } else {
      return;
    }
    // TODO: 插入到界面
    if (utils.is_type_notification(message)) {
      $("#message-ul").append(
        "<li><p class='timestamp'>" +
          "<span>" +
          message.createdAt +
          "</span><br/>" +
          "<span>" +
          message.content +
          "</span>" +
          "</p></li>"
      );
    } else {
      //
      var content =
        "<p class='timestamp'><span>" + message.createdAt + "</span><br/></p>";
      content +=
        "<img class='avatar' width='30' height='30' src='" +
        message.user.avatar +
        "'/>";
      if (!utils.is_self(message)) {
        content += "<div class='nickname'>" + message.user.nickname + "</div>";
      }
      if (utils.is_type_text(message)) {
        content += "<div class='text'>" + 
        utils.replaceFace(message.content) + 
        "</div>";
      } else if (utils.is_type_image(message)) {
        content +=
          "<div class='text'>" +
          "<img src='" +
          message.imageUrl +
          "' alt='[图片]' class='image' onclick=\"utils.imageClicked('" +
          encodeURI(message.imageUrl) +
          "')\"/>" +
          "</div>";
      } else if (utils.is_type_file(message)) {
        content +=
          "<div class='text'>" +
          "<img src='https://www.bytedesk.com/img/input/file.png' alt='[文件]' style='file' onclick=\"utils.fileClicked('" +
          encodeURI(message.fileUrl) +
          "')\"/>" +
          "<span><a href='" +
          encodeURI(message.fileUrl) +
          "' target='_blank'>查看文件</a></span >" +
          "</div>";
      } else if (utils.is_type_voice(message)) {
        content +=
          "<div class='text'>" +
          "<img src='https://www.bytedesk.com/img/input/voice_received.png' alt='[语音]' style='voice' onclick=\"utils.voiceClicked('" +
          message.voiceUrl +
          "')\"/>" +
          "</div>";
      } else if (utils.is_type_robot(message)) {
        console.log("robot:", message.content);
        // var question = "";
        // for (var i = 0; i < message.answers.length; i++) {
        //   var answer = message.answers[i];
        //   question += "<span style='color:#007bff; cursor: pointer;' onclick='httpapi.getAnswer(" + answer.aid + ")'>" + answer.question + "</span>";
        // }
        content += "<div class='text'>" + message.content + "</div>";
      } else if (utils.is_type_questionnaire(message)) {
        var questionnaire = "";
        for (
          var i = 0;
          i <
          message.questionnaire.questionnaireItems[0].questionnaireItemItems
            .length;
          i++
        ) {
          var item =
            message.questionnaire.questionnaireItems[0].questionnaireItemItems[
              i
            ];
          questionnaire +=
            "<br/><span style='color: #007bff; cursor: pointer;' onclick='httpapi.chooseQuestionnaire(" +
            item.qid +
            ")'>" +
            item.content +
            "</span>";
        }
        content +=
          "<div class='text'>" +
          "<span>" +
          message.questionnaire.questionnaireItems[0].title +
          "</span>" +
          questionnaire +
          "</div>";
      } else if (utils.is_type_company(message)) {
        var company = "";
        for (var i = 0; i < message.company.countries.length; i++) {
          var item = message.company.countries[i];
          company +=
            "<br/><span style='color: #007bff; cursor: pointer;' onclick='httpapi.chooseCountry(" +
            message.company.cid +
            ", " +
            item.cid +
            ")'>" +
            item.name +
            "</span>";
        }
        content +=
          "<div class='text'>" +
          "<span>" +
          message.content +
          "</span>" +
          company +
          "</div>";
      } else if (utils.is_type_workGroup(message)) {
        var workGroup = "";
        for (var i = 0; i < message.workGroups.length; i++) {
          var item = message.workGroups[i];
          workGroup +=
            "<br/><span style='color: #007bff; cursor: pointer;' onclick='httpapi.chooseWorkGroup(" +
            item.wid +
            ")'>" +
            item.nickname +
            "</span>";
        }
        content +=
          "<div class='text'>" +
          "<span>" +
          message.content +
          "</span>" +
          workGroup +
          "</div>";
      }
      //
      if (utils.is_self(message)) {
        $("#message-ul").append(
          "<li><div class='self'>" + content + "</div></li>"
        );
      } else {
        $("#message-ul").append("<li><div class='other'>" + content + "</div></li>");
      }
    }
    //
    utils.scrollToBottom();
  },
  //
  getUrlParam: function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return decodeURIComponent(r[2]);
    return null; //返回参数值
  },
  //
  pushMessage: function(message) {
    utils.pushToMessageArray(message)
  },
  //
  pushAnswers: function(answers) {
    var content = "";
    for (var i = 0; i < answers.length; i++) {
      var answer = answers[i];
      content +=
        "<li class='byteDesk-question' onclick='httpapi.getAnswer(" +
        answer.aid +
        ")'>" +
        answer.question +
        "</li>";
    }
    $("#byteDesk-question").append(content);
  },
  //
  updateConnection: function(isConnected) {
    if (isConnected) {
      $("#byteDesk-connected-image").attr(
        "src",
        "https://bytedesk.oss-cn-shenzhen.aliyuncs.com/util/connected.png"
      );
    } else {
      $("#byteDesk-connected-image").attr(
        "src",
        "https://bytedesk.oss-cn-shenzhen.aliyuncs.com/util/disconnected.png"
      );
    }
  },
  //
  onKeyUp: function(event) {
    var key = event.keyCode || window.event.keyCode;
    console.log("onKeyUp:", key);
    if (key === 13) {
      stompapi.sendTextMessage();
    }
    // 发送消息预知
    stompapi.onInputChange();
  },
  //
  toggleInputTip: function(show) {
    if (show) {
      $("#bd-message-tip").show();
    } else {
      $("#bd-message-tip").hide();
    }
  },
  //
  rateStarChoose: function(index) {
    if (index == 1) {
      if ($("#ratestar1").attr("src") == "img/rate/ratestar_selected.png") {
        $("#ratestar2").attr("src", "img/rate/ratestar_unselected.png");
        $("#ratestar3").attr("src", "img/rate/ratestar_unselected.png");
        $("#ratestar4").attr("src", "img/rate/ratestar_unselected.png");
        $("#ratestar5").attr("src", "img/rate/ratestar_unselected.png");
      } else {
        $("#ratestar1").attr("src", "img/rate/ratestar_selected.png");
      }
      $("#ratescore").text("恶劣");
      data.rateScore = 1;
    } else if (index == 2) {
      if ($("#ratestar2").attr("src") == "img/rate/ratestar_selected.png") {
        $("#ratestar3").attr("src", "img/rate/ratestar_unselected.png");
        $("#ratestar4").attr("src", "img/rate/ratestar_unselected.png");
        $("#ratestar5").attr("src", "img/rate/ratestar_unselected.png");
      } else {
        $("#ratestar1").attr("src", "img/rate/ratestar_selected.png");
        $("#ratestar2").attr("src", "img/rate/ratestar_selected.png");
      }
      $("#ratescore").text("较差");
      data.rateScore = 2;
    } else if (index == 3) {
      if ($("#ratestar3").attr("src") == "img/rate/ratestar_selected.png") {
        $("#ratestar4").attr("src", "img/rate/ratestar_unselected.png");
        $("#ratestar5").attr("src", "img/rate/ratestar_unselected.png");
      } else {
        $("#ratestar2").attr("src", "img/rate/ratestar_selected.png");
        $("#ratestar3").attr("src", "img/rate/ratestar_selected.png");
      }
      $("#ratescore").text("一般");
      data.rateScore = 3;
    } else if (index == 4) {
      if ($("#ratestar4").attr("src") == "img/rate/ratestar_selected.png") {
        $("#ratestar5").attr("src", "img/rate/ratestar_unselected.png");
      } else {
        $("#ratestar1").attr("src", "img/rate/ratestar_selected.png");
        $("#ratestar2").attr("src", "img/rate/ratestar_selected.png");
        $("#ratestar3").attr("src", "img/rate/ratestar_selected.png");
        $("#ratestar4").attr("src", "img/rate/ratestar_selected.png");
      }
      $("#ratescore").text("较好");
      data.rateScore = 4;
    } else if (index == 5) {
      if ($("#ratestar5").attr("src") == "img/rate/ratestar_selected.png") {
      } else {
        $("#ratestar1").attr("src", "img/rate/ratestar_selected.png");
        $("#ratestar2").attr("src", "img/rate/ratestar_selected.png");
        $("#ratestar3").attr("src", "img/rate/ratestar_selected.png");
        $("#ratestar4").attr("src", "img/rate/ratestar_selected.png");
        $("#ratestar5").attr("src", "img/rate/ratestar_selected.png");
      }
      $("#ratescore").text("非常满意");
      data.rateScore = 5;
    }
  },
  //
  guid: function() {
    function s4() {
      return Math.floor((1 + Math.random()) * 0x10000)
        .toString(16)
        .substring(1);
    }
    return (
      s4() +
      s4() +
      "-" +
      s4() +
      "-" +
      s4() +
      "-" +
      s4() +
      "-" +
      s4() +
      s4() +
      s4()
    );
  },
  //
  emotionClicked: function(emotionText) {
    console.log('imageclicked:'+emotionText);
    var originalText = $("#inputcontent").val();
    $("#inputcontent").val(originalText + emotionText);
    $("#input-emoji-box").hide();
    data.show_emoji = false
  },
  processEmotion: function(body) {
    var emotionMap = {
      "[微笑]": "appkefu_f001.png",
      "[撇嘴]": "appkefu_f002.png",
      "[色]": "appkefu_f003.png",
      "[发呆]": "appkefu_f004.png",
      "[得意]": "appkefu_f005.png",
      "[流泪]": "appkefu_f006.png",
      "[害羞]": "appkefu_f007.png",
      "[闭嘴]": "appkefu_f008.png",
      "[睡]": "appkefu_f009.png",
      "[大哭]": "appkefu_f010.png",

      "[尴尬]": "appkefu_f011.png",
      "[发怒]": "appkefu_f012.png",
      "[调皮]": "appkefu_f013.png",
      "[呲牙]": "appkefu_f014.png",
      "[惊讶]": "appkefu_f015.png",
      "[难过]": "appkefu_f016.png",
      "[酷]": "appkefu_f017.png",
      "[冷汗]": "appkefu_f018.png",
      "[抓狂]": "appkefu_f019.png",
      "[吐]": "appkefu_f020.png",

      "[偷笑]": "appkefu_f021.png",
      "[愉快]": "appkefu_f022.png",
      "[白眼]": "appkefu_f023.png",
      "[傲慢]": "appkefu_f024.png",
      "[饥饿]": "appkefu_f025.png",
      "[困]": "appkefu_f026.png",
      "[惊恐]": "appkefu_f027.png",
      "[流汗]": "appkefu_f028.png",
      "[憨笑]": "appkefu_f029.png",
      "[悠闲]": "appkefu_f030.png",

      "[奋斗]": "appkefu_f031.png",
      "[咒骂]": "appkefu_f032.png",
      "[疑问]": "appkefu_f033.png",
      "[嘘]": "appkefu_f034.png",
      "[晕]": "appkefu_f035.png",
      "[疯了]": "appkefu_f036.png",
      "[衰]": "appkefu_f037.png",
      "[骷髅]": "appkefu_f038.png",
      "[敲打]": "appkefu_f039.png",
      "[再见]": "appkefu_f040.png",

      "[擦汗]": "appkefu_f041.png",
      "[抠鼻]": "appkefu_f042.png",
      "[鼓掌]": "appkefu_f043.png",
      "[糗大了]": "appkefu_f044.png",
      "[坏笑]": "appkefu_f045.png",
      "[左哼哼]": "appkefu_f046.png",
      "[右哼哼]": "appkefu_f047.png",
      "[哈欠]": "appkefu_f048.png",
      "[鄙视]": "appkefu_f049.png",
      "[委屈]": "appkefu_f050.png",

      ///////////////////////////////
      "[快哭]": "appkefu_f051.png",
      "[阴险]": "appkefu_f052.png",
      "[亲亲]": "appkefu_f053.png",
      "[吓]": "appkefu_f054.png",
      "[可怜]": "appkefu_f055.png",
      "[菜刀]": "appkefu_f056.png",
      "[西瓜]": "appkefu_f057.png",
      "[啤酒]": "appkefu_f058.png",
      "[篮球]": "appkefu_f059.png",
      "[乒乓]": "appkefu_f050.png",

      "[咖啡]": "appkefu_f061.png",
      "[饭]": "appkefu_f062.png",
      "[猪头]": "appkefu_f063.png",
      "[玫瑰]": "appkefu_f064.png",
      "[凋谢]": "appkefu_f065.png",
      "[嘴唇]": "appkefu_f066.png",
      "[爱心]": "appkefu_f067.png",
      "[心碎]": "appkefu_f068.png",
      "[蛋糕]": "appkefu_f069.png",
      "[闪电]": "appkefu_f070.png",

      "[炸弹]": "appkefu_f071.png",
      "[刀]": "appkefu_f072.png",
      "[足球]": "appkefu_f073.png",
      "[瓢虫]": "appkefu_f074.png",
      "[便便]": "appkefu_f075.png",
      "[月亮]": "appkefu_f076.png",
      "[太阳]": "appkefu_f077.png",
      "[礼物]": "appkefu_f078.png",
      "[拥抱]": "appkefu_f079.png",
      "[强]": "appkefu_f080.png",

      "[弱]": "appkefu_f081.png",
      "[握手]": "appkefu_f082.png",
      "[胜利]": "appkefu_f083.png",
      "[抱拳]": "appkefu_f084.png",
      "[勾引]": "appkefu_f085.png",
      "[拳头]": "appkefu_f086.png",
      "[差劲]": "appkefu_f087.png",
      "[爱你]": "appkefu_f088.png",
      "[No]": "appkefu_f089.png",
      "[OK]": "appkefu_f080.png",

      "[爱情]": "appkefu_f091.png",
      "[飞吻]": "appkefu_f092.png",
      "[跳跳]": "appkefu_f093.png",
      "[发抖]": "appkefu_f094.png",
      "[怄火]": "appkefu_f095.png",
      "[转圈]": "appkefu_f096.png",
      "[磕头]": "appkefu_f097.png",
      "[回头]": "appkefu_f098.png",
      "[跳绳]": "appkefu_f099.png",
      "[投降]": "appkefu_f100.png",

      "[激动]": "appkefu_f101.png",
      "[乱舞]": "appkefu_f102.png",
      "[献吻]": "appkefu_f103.png",
      "[左太极]": "appkefu_f104.png",
      "[右太极]": "appkefu_f105.png"
    };
    var reg = /\[[\u4E00-\u9FA5NoOK]+\]/g;
    var matchresult = body.match(reg);
    var result = body;
    if (matchresult) {
      for (i = 0; i < matchresult.length; i++) {
        result = result.replace(
          matchresult[i],
          "<img height='25px' width='25px' src = 'img/emo/" +
            emotionMap[matchresult[i]] +
            "'>"
        );
      }
    }

    return result;
  }
};
