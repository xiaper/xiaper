/**
 * html js
 * @version 1.1.1
 * @author www.bytedesk.com
 * @date 2018/10/18
 */
(function () {

    var contentHtml = '<div id="byteDesk-app-wrapper" style="display: none">\n' +
        '\n' +
        '            <div id="byteDesk-app" v-if="byteDeskVisible">\n' +
        '\n' +
        '                <!--标题部分-->\n' +
        '                <div id="byteDesk-title">\n' +
        '\n' +
        '                    <div id="byteDesk-name">\n' +
        '                        <div style="height: 100%;">\n' +
        '                            <img :src="avatar" width="50px" height="50px" style="float: left; margin-top: 12px;">\n' +
        '                            <div style="margin-left: 60px;">\n' +
        '                                <div style="font-size: 15px; color: white; height: 10px; margin-top: -10px;">\n' +
        '                                    {{ title }}\n' +
        '                                </div>\n' +
        '                                <div style="height: 10px;font-size: 10px; margin-top: 10px;">\n' +
        '                                    {{ description }}\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '\n' +
        '                    <div id="byteDesk-buttons">\n' +
        '                        <span id="byteDesk-agent" style="cursor: pointer;" @click="switchAgent">\n' +
        '                            人工客服\n' +
        '                        </span>\n' +
        '                        <span id="byteDesk-lm" style="cursor: pointer; padding-left: 10px;" @click="switchLeaveMessage">\n' +
        '                            我要留言\n' +
        '                        </span>\n' +
        '                        <span id="byteDesk-robot" style="cursor: pointer; padding-left: 10px;" @click="switchRobot">\n' +
        '                            自助答疑\n' +
        '                        </span>\n' +
        '                    </div>\n' +
        '\n' +
        '                    <div id="byteDesk-close">\n' +
        '                        <!--<span style="cursor: pointer; margin-right: 20px;" @click="maxWindow"><i class="iconfont icon-max"></i></span>-->\n' +
        '                        <span style="cursor: pointer;" @click="close"><i class="iconfont icon-close"></i></span>\n' +
        '                    </div>\n' +
        '\n' +
        '                </div>\n' +
        '                <!-- ./ end of 标题部分-->\n' +
        '\n' +
        '                <!--主体部分-->\n' +
        '                <div style="width: 100%; height: 100%; margin-left: 0px;">\n' +
        '\n' +
        '                    <el-dialog\n' +
        '                            title="满意度评价"\n' +
        '                            :visible.sync="rateDialogVisible"\n' +
        '                            :modal="false"\n' +
        '                            width="30%"\n' +
        '                            center>\n' +
        '                        <el-rate style="text-align: center; margin-bottom: 40px;"\n' +
        '                                 v-model="rateScore"\n' +
        '                                 show-text\n' +
        '                                 :texts="[\'极差\', \'失望\', \'一般\', \'满意\', \'非常满意\']">\n' +
        '                        </el-rate>\n' +
        '                        <el-input v-focus type="textarea" :rows="2" placeholder="请输入附言" v-model="rateContent"></el-input>\n' +
        '                        <span slot="footer" class="dialog-footer">\n' +
        '                                <el-button @click="rateDialogVisible = false">取 消</el-button>\n' +
        '                                <el-button type="primary" @click="rate()">评 价</el-button>\n' +
        '                            </span>\n' +
        '                    </el-dialog>\n' +
        '\n' +
        '                    <el-dialog title="查看大图"\n' +
        '                               :modal="false"\n' +
        '                               :visible.sync="imageDialogVisible"\n' +
        '                               width="30%">\n' +
        '                        <span><img :src="currentImageUrl" alt="[查看大图]" style="height: 100%; width: 100%;"/></span>\n' +
        '                        <span slot="footer" class="dialog-footer">\n' +
        '                                <el-button type="primary" @click="imageDialogVisible = false">确 定</el-button>\n' +
        '                            </span>\n' +
        '                    </el-dialog>\n' +
        '\n' +
        '                    <div id="byteDesk-main" v-if="!showLeaveMessage">\n' +
        '\n' +
        '                        <!--消息记录pc-->\n' +
        '                        <div id="byteDesk-message-pc">\n' +
        '\n' +
        '                            <ul class="byteDesk-message-ul" ref="list">\n' +
        '\n' +
        '                                <!--<div v-if="thread.tid !== 0 && !thread.last" class="byteDesk-pullrefresh" @click="loadMoreMessages()">更多聊天记录</div>-->\n' +
        '                                <li v-for="message in messages" :key="message.mid">\n' +
        '\n' +
        '                                    <p class="byteDesk-timestamp">\n' +
        '                                        <span>{{ message.createdAt | datetime }}</span><br/>\n' +
        '                                        <span v-if="is_type_notification(message)">{{ message.content }}</span>\n' +
        '                                    </p>\n' +
        '\n' +
        '                                    <div v-if="!is_type_notification(message)" :class="{ self: is_self(message) }">\n' +
        '                                        <img v-if="show_header" class="byteDesk-avatar" width="30" height="30" :src="message.user.avatar" />\n' +
        '                                        <div v-if="!is_self(message)" class="byteDesk-nickname">{{ message.user.nickname }}</div>\n' +
        '                                        <div v-if="is_type_text(message)" class="byteDesk-text" v-html="replaceFace(message.content)"></div>\n' +
        '                                        <div v-if="is_type_robot(message)" class="byteDesk-text">\n' +
        '                                            <span>{{ message.content }}</span>\n' +
        '                                            <span v-for="answer in message.answers" :key="answer.id">\n' +
        '                                                <br>\n' +
        '                                                <span style="color:#007bff; cursor: pointer;" @click="getAnswer(answer.aid)">{{ answer.question }}</span>\n' +
        '                                            </span>\n' +
        '                                        </div>\n' +
        '                                        <div v-if="is_type_questionnaire(message)" class="byteDesk-text">\n' +
        '                                            <span>{{ message.questionnaire.questionnaireItems[0].title }}</span>\n' +
        '                                            <span v-for="item in message.questionnaire.questionnaireItems[0].questionnaireItemItems" :key="item.id">\n' +
        '                                                <br/>\n' +
        '                                                <span style="color:#007bff; cursor: pointer;" @click="chooseQuestionnaire(item.qid)">{{ item.content }}</span>\n' +
        '                                            </span>\n' +
        '                                        </div>\n' +
        '                                        <div v-if="is_type_company(message)" class="byteDesk-text">\n' +
        '                                            <span>{{ message.content }}</span>\n' +
        '                                            <span v-for="item in message.company.countries" :key="item.id">\n' +
        '                                                <br/>\n' +
        '                                                <span style="color:#007bff; cursor: pointer;" @click="chooseCountry(message.company.cid, item.cid)">{{ item.name }}</span>\n' +
        '                                            </span>\n' +
        '                                        </div>\n' +
        '                                        <div v-if="is_type_workGroup(message)" class="byteDesk-text">\n' +
        '                                            <span>{{ message.content }}</span>\n' +
        '                                            <span v-for="item in message.workGroups" :key="item.id">\n' +
        '                                                <br/>\n' +
        '                                                <span style="color:#007bff; cursor: pointer;" @click="chooseWorkGroup(item.wid)">{{ item.nickname }}</span>\n' +
        '                                            </span>\n' +
        '                                        </div>\n' +
        '                                        <div v-if="is_type_image(message)" class="byteDesk-text">\n' +
        '                                            <img :src="message.imageUrl" alt="[图片]" style="padding-top: 10px; padding-bottom: 10px; width: 100px; height: 100px;" @click="imageClicked(message.imageUrl)"/>\n' +
        '                                        </div>\n' +
        '                                        <div v-if="is_type_file(message)" class="text">\n' +
        '                                            <img src="https://www.bytedesk.com/img/input/file_selected.png" alt="[文件]"\n' +
        '                                                 style="padding-top: 10px; padding-bottom: 10px; width: 25px; height: 20px;"\n' +
        '                                                 @click="fileClicked(message.fileUrl)"/>\n' +
        '                                            <span><a :href="message.fileUrl" target="_blank">查看文件</a></span>\n' +
        '                                        </div>\n' +
        '                                        <div v-if="is_type_voice(message)" class="text">\n' +
        '                                            <img src="https://www.bytedesk.com/img/input/voice_received.png" alt="[语音]"\n' +
        '                                                 style="padding-top: 10px; padding-bottom: 10px; width: 25px; height: 20px;"\n' +
        '                                                 @click="voiceClicked(message.voiceUrl)"/>\n' +
        '                                        </div>\n' +
        '                                        <div class="status" v-if="is_self(message)">\n' +
        '                                            <i v-if="is_sending(message)" class="fa fa-spinner fa-spin" style="font-size:12px"></i>\n' +
        '                                            <i v-if="is_error(message)" class="fa fa-times-circle" style="font-size:12px"></i>\n' +
        '                                        </div>\n' +
        '                                    </div>\n' +
        '\n' +
        '                                </li>\n' +
        '                            </ul>\n' +
        '\n' +
        '                        </div>\n' +
        '\n' +
        '                        <!--输入框pc-->\n' +
        '                        <div id="byteDesk-input-pc">\n' +
        '                            <transition name="showbox">\n' +
        '                                <div class="byteDesk-input-emoji-box" v-show="showEmoji">\n' +
        '                                    <li v-for="item in emojis" :key="item.file">\n' +
        '                                        <img :src="emotionUrl(item.file)" :data="item.title" @click="emotionClicked(item.title)">\n' +
        '                                    </li>\n' +
        '                                </div>\n' +
        '                            </transition>\n' +
        '                            <div class="byteDesk-input-pc-buttons">\n' +
        '                                <li class=\'iconfont icon-emoji byteDesk-input-emoji\' @click="switchEmoji"></li>\n' +
        '                                <el-upload class="byteDesk-upload-image"\n' +
        '                                           :on-preview="handlePreview"\n' +
        '                                           :on-remove="handleRemove"\n' +
        '                                           :before-remove="beforeRemove"\n' +
        '                                           :on-exceed="handleExceed"\n' +
        '                                           :file-list="uploadedImageList"\n' +
        '                                           :data="upload_data"\n' +
        '                                           :name="upload_name"\n' +
        '                                           :action="uploadImageServerUrl"\n' +
        '                                           :show-file-list="false"\n' +
        '                                           :on-success="handleImageUploadSuccess"\n' +
        '                                           :before-upload="beforeImageUpload"\n' +
        '                                           :disabled="disabled">\n' +
        '                                    <li class="iconfont icon-image"></li>\n' +
        '                                </el-upload>\n' +
        '                                <!--<li class=\'iconfont icon-clear byteDesk-message-clear\' @click="clearMessages"></li>-->\n' +
        '                                <li class=\'iconfont icon-rate byteDesk-message-rate\' @click="rateDialogVisible = true"></li>\n' +
        '                            </div>\n' +
        '                            <el-input id="byteDesk-input-textarea" v-focus type="textarea" :rows="2" placeholder="请输入内容"\n' +
        '                                      v-model="inputContent"\n' +
        '                                      @input="onInputChange"\n' +
        '                                      @keyup.enter.native="onKeyUp"></el-input>\n' +
        '                            <!--FIXME: button位置有待优化-->\n' +
        '                            <el-button id="byteDesk-input-pc-send" size="mini" @click="sendTextMessage" :disabled="sendButtonDisabled">发送</el-button>\n' +
        '                            <div id="byteDesk-logo">\n' +
        '                                <a href="https://www.bytedesk.com" target="_blank">客服软件由萝卜丝提供</a>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '\n' +
        '                    </div>\n' +
        '\n' +
        '                    <div id="byteDesk-leave" v-if="showLeaveMessage">\n' +
        '\n' +
        '                        <el-form ref="leaveMessageForm" :model="leaveMessageForm" :rules="rules" label-width="100px" style="padding-top: 60px;">\n' +
        '                            <el-form-item label="手机" prop="mobile">\n' +
        '                                <el-input v-model="leaveMessageForm.mobile" style="width: 250px;" clearable></el-input>\n' +
        '                            </el-form-item>\n' +
        '                            <el-form-item label="邮箱" prop="email">\n' +
        '                                <el-input v-model="leaveMessageForm.email" style="width: 250px;" clearable></el-input>\n' +
        '                            </el-form-item>\n' +
        '                            <el-form-item label="留言" prop="content">\n' +
        '                                <el-input rows="4" type="textarea" v-model="leaveMessageForm.content" style="width: 250px; margin-left: 0px;"></el-input>\n' +
        '                            </el-form-item>\n' +
        '                            <el-form-item>\n' +
        '                                <el-button type="primary" style="width: 100px; margin-left: 70px;" @click="leaveMessage">提交</el-button>\n' +
        '                            </el-form-item>\n' +
        '                        </el-form>\n' +
        '\n' +
        '                    </div>\n' +
        '\n' +
        '                    <div id="byteDesk-right">\n' +
        '\n' +
        '                        <div id="byteDesk-split"></div>\n' +
        '\n' +
        '                        <div id="byteDesk-faq" style="height: 100%; overflow-y: auto;">\n' +
        '\n' +
        '                            <div style="margin-top: 10px;">常见问题</div>\n' +
        '\n' +
        '                            <div v-for="answer in answers.content" :key="answer.id" style="margin-top: 10px;">\n' +
        '\n' +
        '                                <div class="byteDesk-question" @click="getAnswer(answer.aid)">{{ answer.question }}</div>\n' +
        '\n' +
        '                            </div>\n' +
        '\n' +
        '                        </div>\n' +
        '\n' +
        '                        <div id="byteDesk-connected">\n' +
        '\n' +
        '                            <img id="byteDesk-connected-image" :src="connectedImage"/>\n' +
        '\n' +
        '                            <div style="margin-left: 20px;">\n' +
        '\n' +
        '                                <span v-if="isConnected" style="color: green;">连接正常</span>\n' +
        '\n' +
        '                                <span v-if="!isConnected" style="color: red;">连接断开</span>\n' +
        '\n' +
        '                            </div>\n' +
        '\n' +
        '                        </div>\n' +
        '\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <!-- ./end of 主体部分-->\n' +
        '\n' +
        '                <!--TODO: 兼容手机界面-->\n' +
        '\n' +
        '            </div>\n' +
        '\n' +
        '        </div>';

    //
    var byteDesk = document.getElementById('byteDesk');
    byteDesk.insertAdjacentHTML('beforeend', contentHtml);

})();
