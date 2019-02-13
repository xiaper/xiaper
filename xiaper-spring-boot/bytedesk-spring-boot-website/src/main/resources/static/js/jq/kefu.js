/**
 * bytedesk.com
 */
var kefu = {
  /**
   * 
   */
  created: function() {
    //
    data.adminUid = utils.getUrlParam("uid");
    data.workGroupWid = utils.getUrlParam("wid");
    data.subDomain = window.location.host.split(".").length > 2
        ? window.location.host.split(".")[0]
        : "";
    data.type = utils.getUrlParam("type");
    data.agentUid = utils.getUrlParam("aid");
    //
    data.uid = localStorage.uid;
    data.username = localStorage.username;
    data.password = localStorage.password;
    if (data.password === undefined) {
        data.password = data.username;
    }
    var tokenLocal = localStorage.getItem(data.token);
    if (tokenLocal != null) {
      data.passport.token = JSON.parse(tokenLocal);
    }
    // TODO: 获取浏览器信息，提交给服务器
    console.log(
      "adminUid：" + data.adminUid + " workGroupWid:" + data.workGroupWid + " subDomain:" + data.subDomain
    );
  },
  /**
   * 
   */
  mounted: function() {
    // console.log("mount");
    if (
      data.passport.token.access_token !== null &&
      data.passport.token.access_token !== undefined &&
      data.passport.token.access_token !== ""
    ) {
      //
      httpapi.login();
    } else if (
      data.username !== null &&
      data.username !== undefined &&
      data.username !== ""
    ) {
      if (data.username === utils.getUrlParam("username")) {
        // 非第一次使用自定义用户名
        httpapi.login();
      } else {
        // 保存自定义用户名到服务器
        // console.log("save username 1");
        // httpapi.saveUsername();
        httpapi.requestUsername();
      }
    } else if (utils.getUrlParam("username")) {
      // 保存自定义用户名到服务器
      // console.log("save username 2");
      // httpapi.saveUsername();
      httpapi.requestUsername();
    } else {
      httpapi.requestUsername();
    }
  }
};

/**
 * 
 */
$(document).ready(function () {
  // ie Ajax cross domain requests
  $.support.cors = true;
  // 使ie支持startsWith
  if (!String.prototype.startsWith) {
    String.prototype.startsWith = function(searchString, position) {
       position = position || 0;
      return this.indexOf(searchString, position) === position;
    };
  }
  // 使ie支持includes
  if (!String.prototype.includes) {
    String.prototype.includes = function (str) {
      var returnValue = false;
      if (this.indexOf(str) !== -1) {
        returnValue = true;
      }
      return returnValue;
    }
  }
  // 屏幕宽度小于340的时候，隐藏右侧栏
  console.log("window width: " + $(window).width())
  if ($(window).width() <= 340) {
    $("#byteDesk-right").hide();
    $("#byteDesk-chat").width("100%");
    $("#input-pc").width("100%");
    $("#input-pc").height("115px");
    $("#input-pc-send").height("25px");
    $("#input-pc-send").css("line-height","25px");
    $("#input-pc-send").css("bottom","30px");
    $("#input-pc-send").css("right","20px");
    $("#inputcontent").height("50px");
  }
  //
  $('input[id=imagefile]').change(function(result) {
    console.log("upload:", $(this).val(), $(this));
    //
    var formdata = new FormData();
    formdata.append("file_name", utils.guid());
    formdata.append("username", data.username);
    formdata.append("file", $('input[id=imagefile]')[0].files[0]);
    formdata.append("client", data.client);
    //
    $.ajax({
      url: data.HTTP_HOST + "/visitor/api/upload/image",
      contentType: false,
      cache: false,
      processData: false,
      mimeTypes:"multipart/form-data",
      type: "post",
      data: formdata,
      success:function(response){
        console.log('upload response:', response.data)
        var imageUrl = response.data;
        stompapi.sendImageMessage(imageUrl);
      },
      error: function(error) {
        console.log(error);
      }
    });
 });
  //
  kefu.created();
  kefu.mounted();
});
