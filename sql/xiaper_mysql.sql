-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2019-03-09 11:23:26
-- 服务器版本： 5.7.18
-- PHP 版本： 7.1.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `bytedesk`
--

-- --------------------------------------------------------

--
-- 表的结构 `answer`
--

CREATE TABLE `answer` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `media_id` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `weixin_url` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `workgroup_id` bigint(20) DEFAULT NULL,
  `is_related` bit(1) DEFAULT NULL,
  `query_count` int(11) DEFAULT NULL,
  `rate_helpful` int(11) DEFAULT NULL,
  `rate_useless` int(11) DEFAULT NULL,
  `aid` varchar(255) NOT NULL,
  `is_welcome` tinyint(1) DEFAULT '0',
  `is_recommend` tinyint(1) DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `answer_category`
--

CREATE TABLE `answer_category` (
  `answer_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `answer_query`
--

CREATE TABLE `answer_query` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `answer_id` bigint(20) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `answer_rate`
--

CREATE TABLE `answer_rate` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `answer_id` bigint(20) NOT NULL,
  `message_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL,
  `helpful` bit(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `answer_related`
--

CREATE TABLE `answer_related` (
  `answer_id` bigint(20) NOT NULL,
  `related_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `app`
--

CREATE TABLE `app` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `app_key` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL,
  `aid` varchar(255) NOT NULL,
  `is_default` tinyint(1) DEFAULT '0',
  `platform` varchar(255) DEFAULT NULL,
  `tip` varchar(255) DEFAULT NULL,
  `app_version` varchar(255) DEFAULT NULL,
  `pem_password` varchar(255) DEFAULT NULL,
  `pem_path` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `app`
--

INSERT INTO `app` (`id`, `created_at`, `updated_at`, `avatar`, `description`, `app_key`, `name`, `url`, `users_id`, `status`, `by_type`, `aid`, `is_default`, `platform`, `tip`, `app_version`, `pem_password`, `pem_path`) VALUES
(826123, '2018-12-30 20:04:00', '2018-12-30 20:04:00', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/chrome_default_avatar.png', '默认网站', NULL, '默认网站', 'www.bytedesk.com', 826122, 'debug', 'web', '201812302003591', 1, NULL, NULL, NULL, NULL, NULL),
(826780, '2019-01-01 15:24:47', '2019-01-01 15:24:47', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/chrome_default_avatar.png', 'ddddd', '201901011524462', '1234', 'dddd.com', 15, 'debug', 'web', '201901011524461', 0, NULL, NULL, NULL, NULL, NULL),
(826787, '2019-01-01 15:37:51', '2019-01-11 21:58:59', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/apple_default_avatar.png', '安卓版', '201901011537502', '萝卜丝', 'bytedesk.com', 15, 'release', 'app', '201901011537501', 0, 'android', '修复已知bug', '1.1', NULL, NULL),
(834087, '2019-01-09 22:41:40', '2019-01-09 22:41:40', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/chrome_default_avatar.png', '默认网站', NULL, '默认网站', 'www.bytedesk.com', 834086, 'debug', 'web', '201901092241393', 1, NULL, NULL, NULL, NULL, NULL),
(834468, '2019-01-11 21:26:27', '2019-01-11 21:57:57', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/apple_default_avatar.png', '苹果版', '201901112126262', '萝卜丝', 'bytedesk.com', 15, 'release', 'app', '201901112126261', 0, 'ios', '修复已知bug', '1.1', NULL, NULL),
(839321, '2019-01-24 03:12:27', '2019-01-24 03:12:27', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/chrome_default_avatar.png', '默认网站', NULL, '默认网站', 'www.bytedesk.com', 839320, 'debug', 'web', '201901241712253', 1, NULL, NULL, NULL, NULL, NULL),
(839335, '2019-01-24 03:23:50', '2019-01-24 03:23:50', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/chrome_default_avatar.png', '默认网站', NULL, '默认网站', 'www.bytedesk.com', 839334, 'debug', 'web', '201901241723501', 1, NULL, NULL, NULL, NULL, NULL),
(854808, '2019-03-04 18:20:19', '2019-03-04 18:20:19', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/chrome_default_avatar.png', '默认网站', NULL, '默认网站', 'www.bytedesk.com', 854807, 'debug', 'web', '201903050820183', 1, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `article`
--

CREATE TABLE `article` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` longtext,
  `rate_helpful` int(11) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `read_count` int(11) DEFAULT NULL,
  `aid` varchar(255) NOT NULL,
  `is_recommend` bit(1) DEFAULT NULL,
  `rate_useless` int(11) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `is_top` bit(1) DEFAULT NULL,
  `is_published` bit(1) DEFAULT NULL,
  `is_reship` bit(1) DEFAULT NULL,
  `reship_url` varchar(255) DEFAULT NULL,
  `is_markdown` tinyint(1) DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `article`
--

INSERT INTO `article` (`id`, `created_at`, `updated_at`, `content`, `rate_helpful`, `users_id`, `title`, `read_count`, `aid`, `is_recommend`, `rate_useless`, `summary`, `is_top`, `is_published`, `is_reship`, `reship_url`, `is_markdown`) VALUES
(185583, '2018-09-04 20:44:04', '2018-10-01 12:22:47', '<p>计划2018-09-07日上线第一版</p>', 1, 15, '上线公告', 6, '201809042044041', b'1', 0, NULL, b'0', b'1', b'0', NULL, 0),
(185610, '2018-09-04 21:03:53', '2019-02-23 20:21:51', '<p>常见问题帮助文档</p>', 0, 15, '什么是常见问题', 47, '201809042103521', b'1', 0, NULL, b'0', b'1', b'0', NULL, 0),
(185611, '2018-09-04 21:04:57', '2018-09-05 23:21:55', '<p>相似词帮助文档</p>', 0, 15, '什么是相似词', 2, '201809042104561', b'1', 0, NULL, b'0', b'1', b'0', NULL, 0),
(185639, '2018-09-04 21:44:36', '2018-09-05 23:21:51', '<p>什么是帮助文档</p>', 0, 15, '什么是帮助文档', 3, '201809042144341', b'0', 0, NULL, b'0', b'1', b'0', NULL, 0),
(185640, '2018-09-04 21:45:49', '2018-09-17 19:59:59', '<p>什么是排队访客</p>', 0, 15, '什么是排队访客', 5, '201809042145481', b'0', 0, NULL, b'0', b'1', b'0', NULL, 0),
(185641, '2018-09-04 21:46:06', '2018-09-17 20:00:00', '<p>什么是网站在线</p>', 0, 15, '什么是网站在线', 4, '201809042146051', b'0', 0, NULL, b'0', b'1', b'0', NULL, 0),
(185653, '2018-09-04 21:47:39', '2018-09-18 19:35:16', '<p>什么是客服账号</p>', 1, 15, '什么是客服账号', 4, '201809042147381', b'0', 0, NULL, b'0', b'1', b'0', NULL, 0),
(185654, '2018-09-04 21:47:56', '2018-09-06 11:14:54', '<p>什么是工作组</p>', 1, 15, '什么是工作组', 3, '201809042147551', b'0', 0, NULL, b'0', b'1', b'0', NULL, 0),
(185655, '2018-09-04 21:48:53', '2018-09-18 23:27:50', '<p>什么是会话</p>', 0, 15, '什么是会话', 6, '201809042148531', b'0', 0, NULL, b'0', b'1', b'0', NULL, 0),
(185656, '2018-09-04 21:49:08', '2018-09-18 23:27:48', '<p>什么是评价</p>', 0, 15, '什么是评价', 9, '201809042149071', b'0', 0, NULL, b'0', b'1', b'0', NULL, 0),
(185657, '2018-09-04 21:49:20', '2018-09-17 20:00:01', '<p>什么是留言</p>', 1, 15, '什么是留言', 9, '201809042149191', b'0', 0, NULL, b'0', b'1', b'0', NULL, 0),
(179767, '2018-08-25 21:18:19', '2018-10-25 21:19:32', '<p>安卓开发文档,  https://github.com/pengjinning/bytedesk-android</p>', 0, 15, '安卓开发文档', 10, '201808252118181', b'1', 0, NULL, b'0', b'1', b'0', NULL, 0),
(179768, '2018-08-25 21:18:47', '2018-10-11 10:29:49', '<p>iOS开发文档, https://github.com/pengjinning/bytedesk-ios</p>', 2, 15, 'iOS开发文档', 11, '201808252118461', b'1', 0, NULL, b'0', b'1', b'0', NULL, 0),
(179769, '2018-08-25 21:19:04', '2018-09-28 18:49:10', '<p><span style=\"background-color: rgb(255, 255, 255); color: rgb(36, 41, 46);\">登录后台-&gt;所有设置-&gt;所有客服-&gt;工作组-&gt;获取代码</span></p>', 0, 15, '网站对接文档', 13, '201808252119041', b'1', 0, NULL, b'0', b'1', b'0', NULL, 0),
(179770, '2018-08-25 21:19:26', '2018-12-27 11:49:04', '<p>共有三种对接方式：方法一： 授权绑定（推荐）；方法二：配置服务器；方法三：对接消息接口。可根据实际情况，任选其一。</p><p>推荐使用方法一，直接点击跳转授权即可，对于非技术人员来说，最为简单方便。</p><p>方法二稍为繁琐，需要有一定的技术基础。</p><p>方法三专为开发者提供，适用于已经绑定第三方平台，同时需要客服功能的公众号</p><p><br></p><p>在公众号“IP白名单”中添加：</p><p>47.96.102.83</p><p>47.98.54.86</p><p>47.99.38.99</p><p>47.106.239.170</p><p>47.91.207.85</p><p><br></p>', 0, 15, '公众号对接文档', 14, '201808252119251', b'1', 0, NULL, b'0', b'1', b'0', NULL, 0),
(179771, '2018-08-25 21:19:43', '2018-12-16 13:24:06', '<p>小程序对接文档</p>', 1, 15, '小程序对接文档', 11, '201808252119421', b'0', 0, NULL, b'0', b'1', b'0', NULL, 0),
(187924, '2018-09-07 12:01:31', '2018-11-11 13:29:21', '<h2><img src=\"https://mmbiz.qpic.cn/mmbiz_jpg/LwZPmXjm4WyOwZt0m9QOtvEI4nzPLNvWfOIeBjZczb3D0icC26puXolwwHRqKnS4fRSFRTePCRr1hxcnpXUJeuQ/640?wx_fmt=jpeg&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\"></h2><p class=\"ql-align-center\"><br></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(136, 136, 136);\">阿里妹导读：一提到调度，大家脑海中可能想起的是调度阿里云的海量机器资源，而对于阿里集团客户体验事业群（CCO）而言，我们要调度的不是机器，而是客服资源。今天，我们邀请阿里高级技术专家力君，为大家分享自动、智能的客服调度系统——XSigma。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-center\"><br></p><p class=\"ql-align-center\"><strong style=\"color: rgb(0, 122, 170);\">背景</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">为什么客服需要调度？阿里集团客户体验事业群（CCO）目前承接了阿里集团以及生态体的客户服务业务，我们的客户通过各个渠道来寻求解决各类问题，每天的进线量巨大，而且经常伴随着突发性进线，比如天猫代金券出了问题，在几分钟内就会造成几千通热线或在线咨询。面对种类繁多、海量、突发的客户问题，我们的服务能力往往难以满足，常常造成用户排队，甚至放弃，自然我们产生了对调度的需求。</span></p><p class=\"ql-align-center\"><img src=\"https://mmbiz.qpic.cn/mmbiz_png/EkgpOcjaJwiaP81WPKicIr9ezoG5jtCqoEkHkg3GNUSAq8DEkia5W4QkOgMicQYVHswJmXlYD7VhN4dShapuR5KH1A/640?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\"></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">客服调度的核心问题是什么？</span><strong style=\"color: rgb(62, 62, 62);\">提升客服资源的利用率和服务水平，用更少的客服资源获得更佳的用户体验。</strong><span style=\"color: rgb(62, 62, 62);\">如果我们招聘大量的客服，也能让用户获得更好的体验，但是容易造成人力浪费，更多的人手意味着更多的培训成本、管理成本和人力成本。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">与机器调度相比，客服调度有它的复杂点：</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">1）机房增加一台新物理机，机器虚拟化后就可以快速被使用，而招募一个新客服，得需要长时间的培训才能让他具备线上服务能力；</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">2）客服间差别大，不同客服的业务技能有区别，很难直接让B技能组的客服处理A技能组的任务，即使是掌握同一技能的客服，他们的服务能力也有大的差别，而机器差别不大，很多业务可以使用相同类型的机器；</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">3）客服是人，他有权利选择上班、小休，他的工作效率、质量会随着他的情绪、体验、服务的会员、工作时长等波动，调度时需要考虑他们的感受，而调度机器时无需顾忌；</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">4）突发场景多，业务问题、系统故障等都是无规律爆发，波动特别大，很难准确的提前排好一天的人力。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">现场管理员是否能应对如此复杂的客服调度？答案是否定的。在没有调度系统之前，现场管理员基本靠手工来调度，随着体量越来越大，缺陷逐渐暴露：</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">1）响应慢：比如周末线上排队时，现场管理员可能会收到电话反馈，然后再打开电脑去手工放个临时班等等，从排队发生到调度生效超过十几分钟很正常；&nbsp;</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">2）不精准：缺乏数据指导，统筹优化能力弱。举个例子，A技能组排队时现场管理员想将A技能组的流量切一些到B里，切多少，分给谁，可能都是拍脑袋决定，决策结果也无法沉淀；</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">3）手段缺：可用的手段非常少，无非就是手动排班放班、手工切个流，管控下小休、发个公告等，没有充分挖掘出客服的能力和潜力。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">明确了客服调度的核心问题，也知道了难点，更看到了目前的现状后，我们决定打造一款自动、智能的客服调度系统——XSigma。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><strong style=\"color: rgb(0, 122, 170);\">1. XSigma大图</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">XSigma调度系统按功能模块可以分为手、脑、眼几块。手就是能提升客服资源利用率、客服服务水平以及提升客户满意度的手段，比如溢出分流、预约回拨、现场管控、激励、排班、应急放班、培训等。手段这么多，在不同业务不同场景下如何抉择是一个难点，这里需要大脑也就是调度中心来做决策。决策产生的复杂调度逻辑如何能让现场管理员、业务人员和开发人员更好地理解？我们通过可视化技术将复杂的调度逻辑转化为可以理解的实时图形界面，即调度系统的眼睛-调度大屏。手、脑、眼功能具备后，如何让他们磨合得越来越好？我们通过仿真演练系统来锤炼。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-center\"><img src=\"https://mmbiz.qpic.cn/mmbiz_png/EkgpOcjaJwiaP81WPKicIr9ezoG5jtCqoEVgA4cxEpCEZN8r15QgxaKMavg49ARgu8jtUgqicUReRD2EvVoJCJ8fg/640?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\"></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">下面会对图里的模块一一介绍。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><strong style=\"color: rgb(0, 122, 170);\">2.&nbsp;提前准备：排好班</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">&nbsp;如果能预测好需，准备好供，那客服调度就成功了一半。在我们业务中，不同类型的客服排班模式不同。云客服采用的是自主选班模式，管理员只需设置好每个时间段的选班人数，让云客服根据自己的时间来自行选班。而SP（合作伙伴）采用的是排班模式，需要管理员根据每个时间段的话务量来安排每一个客服，既要能够保证每个时间段的接通率达到最大，又要能够协调好客服人员的休息和工作时间，保证每个客服人员的总工时大致相等，这非常考验管理员的统筹能力，当客服数目变多后，人工排班给管理员带来了巨大挑战。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">不管哪种模式，都需要提前预测未来两周的需要服务量（业务上按1~2周的粒度排班），这其实是个标准的时间序列预测问题。结合历史数据，我们可以按照部门-技能组的粒度预测出未来2周的服务量，当然，这种离线的预测只是一种近似，很难精准预测。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-center\"><img src=\"https://mmbiz.qpic.cn/mmbiz_png/EkgpOcjaJwiaP81WPKicIr9ezoG5jtCqoEZdHWoXqW4YMNjFoTIWoNmvI1HXWzhLME4qa5QunHVpCjojXicx43fiaQ/640?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\"></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">对于合作伙伴公司客服的排班，可以抽象为多约束条件下的优化问题，在实际场景中，我们采用了组合优化算法。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><strong style=\"color: rgb(0, 122, 170);\">3. 水平扩容：预测式应急放班</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">提前排班很难精确预估服务量，我们不可能提前知道下周一13点25分会出现个代金券问题导致大量用户进线咨询。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">对于这种突发性质的流量或者比上班服务量大的流量，我们能不能像调度机器一样，快速水平扩容一批客服来上班。对于社会化的云客服，我们可以做到，比如排队数超过某值时，自动触发云客服的应急放班。通过实践发现云客服从选班到上班一般需要十多分钟时间，如何进一步节省这十多分钟的黄金处理时间？将应急放班升级为预测式应急放班！提前几分钟预测到即将到来的大流量，提前放班。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">这里涉及两个模型，一个是服务量实时预测模型，该模型能根据实时数据如会员的操作行为，会员在小蜜的行为，故障场景，并结合历史进线量来综合预测某一技能组未来30分钟每一分钟的进线量。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">有了服务量预测数据输入后，应急放班模型就可以结合当前服务会员情况，未来30分钟客服排班情况、会员消耗速度、溢出关系等综合指标，来推断出是否要触发应急放班以及放班的服务量。一旦触发应急放班后，线下通知模块会通过电话、短信等手段来通知合适的客服来上班。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">与调度机器不同，我们需要时刻考虑客服感受，为了避免打扰没有上班意愿的客服，我们让客服自主设置是否要接收通知。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><strong style=\"color: rgb(0, 122, 170);\">4. 负载均衡：溢出、分流</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">尽管预测式应急放班效果不错，但目前只针对云客服有效，对于SP类这种非选班类的客服怎么办？我们发现，线上排队时，往往是某几个技能组出现大量排队场景，比如商家线爆了，消费者线的客服可能处于空闲状态。如何解决这种忙闲不均问题？一个直观的极端想法就将所有的组变成一个大池子组，通过负载均衡分配让每一个客服都处于繁忙状态，从而达到效率最大化。而事实上并不是所有的技能组之间都能互相承接，这里既要权衡业务，又要线下培训让客服具备多技能。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">XSigma提供了技能组相互分流、溢出的配置功能，只要满足触发条件，就能实时分流溢出，解决了以往靠现场管理员手工改客服技能组的痛苦。</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">&nbsp;</span></p><p class=\"ql-align-center\"><img src=\"https://mmbiz.qpic.cn/mmbiz_png/EkgpOcjaJwiaP81WPKicIr9ezoG5jtCqoEgTHlAEcQQMRxzzFrYx502tYMY6KZVcg3VyGv7fYn2V3LHFicXLbJzpA/640?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\"></p><p class=\"ql-align-center\"><img src=\"https://mmbiz.qpic.cn/mmbiz_png/EkgpOcjaJwiaP81WPKicIr9ezoG5jtCqoErbjUibLnmUS3NuoOqKR50JX75f4bz4ibV2bHqg83dibib9rAMibCicQ5QVbQ/640?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\"></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">对于一些场景而言，技能组间的溢出粒度有点粗，比如设置了A技能组排队可以溢出到B技能组，并不是B技能组的每一个客服都能承接A的业务，只有进行了培训的客服才能承接，XSigma同样提供了给客服打技能标签的功能。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><strong style=\"color: rgb(0, 122, 170);\">5. 垂直扩容：弹性+1</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">有些业务比较复杂，很难找到其他技能组进行溢出，我们将注意力转到正在上班的客服上。在线客服可以同时服务多个会员，如果一个客服最大服务能力是3，那么他最多同时服务3个会员，这个值由管理员根据客服的历史服务水平来设置。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">&nbsp;我们发现尽管很多小二的最大并发能力是相同的，在他们满负荷服务会员时，他们的服务水平有很大不同，他们的忙闲程度也有非常大的差异，为什么？</span></p><p class=\"ql-align-justify\"><br></p><ul><li class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">小二本身水平有差异</span></li></ul><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">如下图所示，某技能组的客服最大服务能力都是3，最近一个月这个技能组的客服在同时服务3个会员场景下的平均响应时间分布（平均响应时间正比于客服回复速度），可以看到数据呈一个大致正太分布，说明小二服务水平有差异。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-center\"><img src=\"https://mmbiz.qpic.cn/mmbiz_png/EkgpOcjaJwiaP81WPKicIr9ezoG5jtCqoEPryicN9hsqibCk3NzNmIpCcuHwnoFa5ouSjmx91mOoIpBuVF88dRIWNw/640?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\"></p><p class=\"ql-align-justify\"><br></p><ul><li class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">场景不同</span></li></ul><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">举个例子，A和B两个客服最大服务能力都是5，同样都在处理5个会员，但是A的5个会员差不多都到会话结束尾声了，B的5个会员都才刚刚开始，这个例子下A和B两个客服当下的忙闲程度明显不同。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">既然小二的服务水平有差别，实际场景千差万别，那能不能在技能组排队时刻让那些有余力的小二突破最大服务上限？</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">XSigma提供了两种策略来让小二突破服务上限。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">1）主动+1模式</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">当技能组达到触发条件时，XSigma会主动点亮客服工作台的+1按钮（如下图红框所示），客服可以点击来主动增加一个会员进线，这种方式相当于是将扩容权利交给客服，因为只有客服自己知道目前忙不忙。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-center\"><img src=\"https://mmbiz.qpic.cn/mmbiz_png/EkgpOcjaJwiaP81WPKicIr9ezoG5jtCqoEws1AniaWDniaKxUbWCbzCGr3lvlMIGXAj4Jsoph0Ro11JslG4rZoJcBw/640?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\"></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">2）强制+1模式</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">如果某些技能组是强管控类型，可以选择开启强制+1模式，XSigma会结合数据自动选择一些合适的客服来突破服务能力上限，比如他之前最大服务能力是5，我们会同时让他服务6个会员。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><strong style=\"color: rgb(0, 122, 170);\">6.&nbsp;削峰填谷：预约回拨</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">对于热线来说，小二不可能同时接好几个电话，而且业务上可承接的线下客服也少，这时候如果出现大面积排队怎么办？</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">通过数据分析发现，很多技能组在一天内的繁忙度在波动，有高峰也有低峰，下图所示展示了某技能组的剩余服务数，可以看到有两个繁忙时间段，10~13点，17~21点，这两个时间段的空闲服务数很多时候都是0，而其它时间段相对比较空闲，如果能将这些繁忙时间段的进线量腾挪到非繁忙时间段，这样就能大大提升客服的人员利用率，也能避免客户排队的烦恼。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">怎么做呢？通过预约回拨，将当下服务转变为未来服务。如下图所示，主要有两个模块构成。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">1）预约触发器。用户电话进来后，预约触发器会根据技能组的繁忙情况，来判定是否要触发预约；</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">2）回拨触发器。采用系统主动外呼模式，一旦发现技能组繁忙度处于低峰，就会触发回拨，只要用户电话被接通，就会以高优先级进入到分配环节，从而让客服人员在有效的工作时间内都在真正的与客户通话。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-center\"><img src=\"https://mmbiz.qpic.cn/mmbiz_png/EkgpOcjaJwiaP81WPKicIr9ezoG5jtCqoEGZv9AYbWz5EosObwhhC83QiaGLqbfwCVVIKMldLF9nzzGfqWeeVYxtQ/640?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\"></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><strong style=\"color: rgb(0, 122, 170);\">7.&nbsp;最优分配</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">调度的目标是：“提升客服资源的利用率和服务水平，用更少的客服资源获得更佳的用户体验”。前面这些策略的关注点更多是在提升客服资源利用率上，有没有什么策略能提升用户的满意度？我们从分配这一环节入手。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">本质上我们要解决的是“会员（任务）-客服匹配优化”问题。在传统模式下，分配就是从某技能组的排队队列中找到一个等待时间最长的会员，然后再找一个该技能组下最空闲的客服完成匹配。这种公平分配方式考虑维度单一，未能在全局层面上掌握和调度分配有关的会员、客服、问题等各类信息。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">匹配优化问题其实是二部图匹配问题，如图所示，在某一时刻，我们可以得到某技能组下未分配的客户（任务）以及具备剩余服务能力的客服，如果能知道每个任务与每个客服之间的匹配概率，那就可以通过稳定婚姻算法找到最佳匹配。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><img src=\"https://mmbiz.qpic.cn/mmbiz_png/EkgpOcjaJwiaP81WPKicIr9ezoG5jtCqoElGLQNBGYExTVSHIic93uejfvzwaYRpVm44Uat5ne5kwgawMEibyQwZ0A/640?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\"></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">如何求得任务与客服之间的匹配概率？抽象为分类回归问题，核心在于构建大量样本（x1,x2,x3,…,xn）(y)。针对一通历史会话任务，y是客户评分或会话时长（目标可选），而x既包含了客服特征如过去30天的满意度、平均响应时间等等离线指标，以及客服当前会话的服务会员数、最大会员数等实时指标，也包含了任务的特征，如问题类型、等待时间、订单编号、重复咨询次数等等。样本有了后，下面就是选择分类算法进行训练，最终我们采用了CNN。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">在迭代过程中发现，模型会将流量更多分配给好的客服，而指标相对较差的客服的流量则变少，为了避免少量客服上班接不到客反弹的情景，我们将公平性的指标引入到模型中。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><strong style=\"color: rgb(0, 122, 170);\">8. 智能培训：大黄机器人</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">通过最优分配来提升满意度的一个重要原因是将流量更多分给了能力强水平高的客服，而这部分客服的占比不高，为什么？为了应对11、12这两个特殊月份的高流量，业务团队要招募培训大量的云客服。这些新手涌入必然会对满意度带来影响，换句话说，如果要想进一步提升满意度指标，必须提升新手客服的服务水平。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">&nbsp;对于新手，在上岗前提升他们水平的唯一方式就是培训，传统的培训都是通过线下让云客服看视频等学习资料，然后进行笔试，通过后就直接上岗，带来的问题是很多新客服对平台的工具、解决方案都不熟悉就直接服务会员，会员体感较差。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">对比练车场景，我们发现练车有科目1、科目2、科目3等不同流程，科目1学习理论，科目2和科目3实战模拟，如果我们引入这种实战模拟就能大大提升新客服的服务水平。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">我们创新的提出了使用机器人（大黄）来培训客服这一全新的客服培训模式（已申请专利）。新客服在培训租户里，通过点击大黄头像，会产生一通非常真实的模拟会话，通过和会员聊天，不断学习平台工具使用，不断提升解决客户问题能力。一旦会话结束后，大黄机器人会对这通会话进行评价，并会告知应该使用某种具体的解决方案来回答用户问题。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-center\"><img src=\"https://mmbiz.qpic.cn/mmbiz_png/EkgpOcjaJwiaP81WPKicIr9ezoG5jtCqoEicp6STPwEHDibEaAr2ZaGDkYkX5Bejj7UFUBlvUiak7zpBP0AnQBFEbQA/640?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\"></p><p class=\"ql-align-center\"><img src=\"https://mmbiz.qpic.cn/mmbiz_png/EkgpOcjaJwiaP81WPKicIr9ezoG5jtCqoETxHQTzs9KYrrfp1Cjo0guEhUGBkib6ibgibM9fyLcrSLhvGWsdsVGyXXQ/640?wx_fmt=png&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1\"></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">对于新客服，目前必须完成大黄80通会话后才能上岗，整个财年培训客服几万人，服务会话量达到几百万轮次。abtest显示通过大黄试岗的客服不管在满意度、不满意、平均响应时间、平均服务时长等各项指标上都有非常明显提升。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><strong style=\"color: rgb(0, 122, 170);\">9. 统一的调度中心</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">从上面可以看到我们的客服调度策略多且复杂，每种策略都起到了一定提升客服资源的利用率和服务水平的作用。现在的问题来了，不同场景下这么多策略如何选择？比如现在技能组A突然排队100个会员，这个时候是直接溢出到其他技能组，还是触发主动+1或触发应急放班呢？这里需要一个大脑来做决策。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">如何让这个大脑适用于各种复杂的业务场景是难点。我们平台目前租户就有几十个，仅淘系这一个租户就划分了几十个客服部门，每个部门下又细分了一系列技能组，不同部门间业务场景不同。在严重缺乏历史数据积累情况下，很难直接通过训练一个决策模型来适应多种业务。于是我们的思路就转换为直接利用现场管理员的专家知识，让他们将决策逻辑沉淀为一条条的规则。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">目前平台上已经配置了上万条规则，每天生效的规则也有几千条，这些数据的沉淀让我们可以通过智能优化技术实现真正的智能调度决策大脑。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><strong style=\"color: rgb(0, 122, 170);\">10. 调度监控大屏</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">客服调度策略繁多、逻辑复杂，调度结果会切实影响整个环节参与者的感受，因此我们搭建了XSigma调度大屏，方便大家理解。在实践过程中发现调度大屏能建立起使用方对调度系统的信任感，降低开发人员和管理员发现、定位并解决系统问题的成本。举个例子，管理员在XSigma平台上设置一些规则，比如A技能组排队数&gt;=1触发溢出到B技能组，设置完后他心里没底，他也不知道设置的逻辑是否生效，往往会让开发同学再次确定下有没有生效，而现在有了可视化调度大屏，既能观察到各个技能组的服务量、剩余服务量等实时监控数据，也能看到实时调度各种策略生效的过程，以及每天调度的实时汇总明细数据。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><strong style=\"color: rgb(0, 122, 170);\">11. 仿真演练</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">在调度优化场景中，如何评估调度系统的好坏至关重要。有没有一种手段能评估XSigma是否能适应各种场景？能提前证明在双11这种大促期间也能顺畅的调度？能及时发现调度过程中出现的问题？这不仅是我们也是业务同学迫切需要知道的。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">仔细思考发现，要解决的问题和技术的全链路压测要解决的问题很相似，我们要做的其实是业务上的全链路压测，于是我们搭建了客服调度的仿真演练系统。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">基于大黄机器人，我们已经能模拟会员进线，通过定制改造，机器人可以制造各种主题类型的题目，比如双十一类型场景等。在此基础上，结合业务同学的预估量，可以设置出各个技能组的进线量。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">在双十一之前，业务同学使用这套演练系统大规模演练过两次，由于是基于真实服务量进行演练，而不是以前的口头相传的方式，让调度上下游每一个参与的同学都有压力感。在演练过程中发现的一些问题改进后，大大提升了我们应对大促突发流量的信心。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><strong style=\"color: rgb(0, 122, 170);\">&nbsp;12. 小结</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><span style=\"color: rgb(62, 62, 62);\">XSigma智能客服调度系统采用自动化配置、机器学习等技术，将复杂的调度问题分层处理，并在日益增长的会员任务基础上，不断精细化调度模型依赖的状态预估数值，不断提高调度模型的多目标规划能力，同时通过大量运用平台可视化技术，以实时、图表化的方式将系统运行状态呈现出来，最终在客服效率和用户体验时间上得到优化效果。该系统上线后，相比于往年，服务不可用时长这一业务核心指标直接下降98%。</span></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\"><br></p>', 0, 15, '为减少用户电话排队，阿里研发了智能客服调度系统', 7, '201809071201301', b'0', 0, NULL, b'0', b'1', b'0', NULL, 0),
(191954, '2018-09-14 18:01:23', '2018-10-01 12:26:04', '<h1>Introduction</h1><p class=\"ql-align-justify\">Spring boot and Let’s Encrypt is both widely used in the industry. Spring Boot gives power to make a Spring-powered application easily; while Let’s Encrypt offers free SSL/TLS certificates for enabling secure HTTPS connections between client and server (the most easily recoginized is that the browser will show a green padlock beside the URL).</p><p class=\"ql-align-justify\">But to integrate them has some difficulties as below:</p><ol><li>Certificates offered by Let’s Encrypt are valid for only 90 days; renewing process needs to be processed gradually.</li><li>Spring Boot needs to restart to adopt new certification. Which quite painful.</li><li>Let’s Encrypt program offer key in PEM files, while Spring Boot supports for PKCS12 type. It is neccesary to convert certificate and private key to PKCS12.</li></ol><h1>Require</h1><p class=\"ql-align-justify\">In this tutorial, we will use the following material:</p><ul><li>One server running Ubuntu 16.04</li><li>A fully registered domain name</li></ul><p class=\"ql-align-justify\">In my case, I use Google Azure - they offer all I need.</p><h1>Design</h1><p class=\"ql-align-justify\">For easy adopting Let’s Encrypt with Spring Boot, NginX is used as a SSL Proxy server.</p><ul><li>Spring Boot is running on port 8080 and not accessible from internet.</li><li>Request to port 80 is redirected to port 443 by NginX.</li><li>NginX receives all request from port 443 and redirect to port 8080.</li><li>Let’s Encrypt certificate is servered by NginX, NginX will be restarted after certification renewal is succeed.</li></ul><p class=\"ql-align-justify\"><a href=\"https://i.imgur.com/DerwAZl.png\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\"><img src=\"https://i.imgur.com/DerwAZl.png\" alt=\"NginX proxy\"></a></p><p class=\"ql-align-justify\"><span style=\"color: rgb(153, 153, 153);\">NginX proxy</span></p><h1>Step 1: Install Java (skip this part if you have Java installed)</h1><p class=\"ql-align-justify\">Open the terminal, add the PPA by running:</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\nsudo add-apt-repository ppa:webupd8team/java\n</pre><p class=\"ql-align-justify\">Install Java 8 (you can change the package name to oracle-java9-installer for Java 9)</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\nsudo apt update; sudo apt install oracle-java8-installer\n</pre><p class=\"ql-align-justify\">You now can check Java version after installing the package by running:</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\njavac -version\n</pre><p class=\"ql-align-justify\">And now for automatically set Java enviroment variables:</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\nsudo apt install oracle-java8-set-default\n</pre><h1>Step 2: running a simple Spring Boot application</h1><p class=\"ql-align-justify\">We will clone a simple program, which running on port 8080&nbsp;<a href=\"https://github.com/bachnxhedspi/simple-spring-boot-app\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\">here</a>.</p><p class=\"ql-align-justify\">Remote connect to your server and running:</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\n2\n3\ngit clone https://github.com/bachnxhedspi/simple-spring-boot-app\ncd simple-spring-boot-app\njava -jar gs-spring-boot-0.1.0.jar &amp;\n</pre><p class=\"ql-align-justify\">The Spring Boot is running successful if you see this:</p><p class=\"ql-align-justify\"><a href=\"https://i.imgur.com/TPXdyti.png\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\"><img src=\"https://i.imgur.com/TPXdyti.png\" alt=\"Spring Boot running successful\"></a></p><p class=\"ql-align-justify\"><span style=\"color: rgb(153, 153, 153);\">Spring Boot running successful</span></p><p class=\"ql-align-justify\">You can test it again by running:</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\ncurl localhost:8080\n</pre><p class=\"ql-align-justify\">It should return “Greetings from Spring Boot!”.</p><h1>Step 3: install NginX</h1><p class=\"ql-align-justify\">NginX installation tutorial can be found&nbsp;<a href=\"https://www.digitalocean.com/community/tutorials/how-to-install-nginx-on-ubuntu-16-04\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\">here</a>. Or you just need to run the following command:</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\n2\nsudo apt-get update\nsudo apt-get install nginx\n</pre><p class=\"ql-align-justify\">Now allow access to port 80 and port 443 in the Azure Portal by opening Network Security Group of your Virtual Machine.</p><p class=\"ql-align-justify\"><a href=\"https://i.imgur.com/E40rE3f.jpg\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\"><img src=\"https://i.imgur.com/E40rE3f.jpg\" alt=\"Create a new rule\"></a></p><p class=\"ql-align-justify\"><span style=\"color: rgb(153, 153, 153);\">Create a new rule</span></p><p class=\"ql-align-justify\"><a href=\"https://i.imgur.com/OQvnVBi.jpg\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\"><img src=\"https://i.imgur.com/OQvnVBi.jpg\" alt=\"Rule for allowing access to port 80 and 443\"></a></p><p class=\"ql-align-justify\"><span style=\"color: rgb(153, 153, 153);\">Rule for allowing access to port 80 and 443</span></p><p class=\"ql-align-justify\"><a href=\"https://i.imgur.com/B2hLEaY.png\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\"><img src=\"https://i.imgur.com/B2hLEaY.png\" alt=\"Access to the domain... NginX will show up\"></a></p><p class=\"ql-align-justify\"><span style=\"color: rgb(153, 153, 153);\">Access to the domain... NginX will show up</span></p><p class=\"ql-align-justify\">We will redirect all request to our website to port 8080. Open /etc/nginx/nginx.conf and insert to server part.</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n  server {\n      ....\n\n      # Change to your domain\n      server_name springbootdemo.centralus.cloudapp.azure.com;\n      \n      ....\n\n      # Redirect all request to port 8080\n      location / {\nproxy_pass http://127.0.0.1:8080;\n      }\n\n      ...\n</pre><p class=\"ql-align-justify\">And now restart nginx by runnning</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\nsudo service nginx restart\n</pre><p class=\"ql-align-justify\"><a href=\"https://i.imgur.com/UG9wSJn.png\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\"><img src=\"https://i.imgur.com/UG9wSJn.png\" alt=\"Redirect successful\"></a></p><p class=\"ql-align-justify\"><span style=\"color: rgb(153, 153, 153);\">Redirect successful</span></p><h1>Step 4: Acquire and adopt Let’s Encrypt by using Certbot</h1><p class=\"ql-align-justify\"><a href=\"https://certbot.eff.org/\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\">Certbot</a>&nbsp;is an easy-to-use automatic client that fetches and deploys Let’s Encrypt SSL/TLS certificates for our webserver.</p><p class=\"ql-align-justify\">Install Certbot</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\n2\n3\nsudo add-apt-repository ppa:certbot/certbot\nsudo apt-get update\nsudo apt-get install python-certbot-nginx\n</pre><p class=\"ql-align-justify\">Installing certificate to NginX by running:</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\nsudo certbot --nginx\n</pre><p class=\"ql-align-justify\">As usual, we can running domain challenging by the above command with tls-sni-01. But currently Let’s Encrypt has currently disabled the TLS-SNI-01 challenge due to&nbsp;<a href=\"https://community.letsencrypt.org/t/2018-01-11-update-regarding-acme-tls-sni-and-shared-hosting-infrastructure/50188\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\">a security report</a>. So we will implement this command instead.</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\nsudo certbot --authenticator standalone --installer nginx\n</pre><p class=\"ql-align-justify\">Remember to select the domain (by just press enter) and select for redirect through HTTPS (by enter 2) when being asked.</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20\n21\n22\n23\n24\n25\n26\n$ sudo certbot --authenticator standalone --installer nginx\n...\n\nWhich names would you like to activate HTTPS for?\n-------------------------------------------------------------------------------\n1: springbootdemo.centralus.cloudapp.azure.com\n-------------------------------------------------------------------------------\nSelect the appropriate numbers separated by commas and/or spaces, or leave input\nblank to select all options shown (Enter \'c\' to cancel):\nObtaining a new certificate\nPerforming the following challenges:\nhttp-01 challenge for springbootdemo.centralus.cloudapp.azure.com\nWaiting for verification...\nCleaning up challenges\nDeployed Certificate to VirtualHost /etc/nginx/nginx.conf for set([\'springbootdemo.centralus.cloudapp.azure.com\'])\nnginx: [error] invalid PID number \"\" in \"/run/nginx.pid\"\n\nPlease choose whether or not to redirect HTTP traffic to HTTPS, removing HTTP access.\n-------------------------------------------------------------------------------\n1: No redirect - Make no further changes to the webserver configuration.\n2: Redirect - Make all requests redirect to secure HTTPS access. Choose this for\nnew sites, or if you\'re confident your site works on HTTPS. You can undo this\nchange by editing your web server\'s configuration.\n-------------------------------------------------------------------------------\nSelect the appropriate number [1-2] then [enter] (press \'c\' to cancel): 2\nRedirecting all traffic on port 80 to ssl in /etc/nginx/nginx.conf\n</pre><p class=\"ql-align-justify\">You can check /etc/nginx/nginx.conf, it was modified by Certbot for using Let’s Encrypt certificate.</p><p class=\"ql-align-justify\">And now it’s done. Our website is secured by Let’s Encrypt certificate</p><p class=\"ql-align-justify\"><a href=\"https://i.imgur.com/SYiWUwa.png\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\"><img src=\"https://i.imgur.com/SYiWUwa.png\" alt=\"Well done, a A grade certificate. Rated by SSL Labs\"></a></p><p class=\"ql-align-justify\"><span style=\"color: rgb(153, 153, 153);\">Well done, a A grade certificate. Rated by SSL Labs</span></p><h1>Step 5: Setting Let’s Encrypt auto renewal</h1><p class=\"ql-align-justify\">But there is no silver bullet; there is a trade-off between certificates duration and security (you can read more about it&nbsp;<a href=\"https://letsencrypt.org/2015/11/09/why-90-days.html\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\">here</a>). Its duration is only 90 days, compare to normal paid certificatis which valid for some years. But it can be solved by set a crontab to auto renew it.</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\nsudo crontab -e\n</pre><p class=\"ql-align-justify\">It will open the crontab of su user, insert this line below</p><pre class=\"ql-syntax ql-align-right\" spellcheck=\"false\">1\n30 3 * * * certbot renew --post-hook \"service nginx reload\"\n</pre><p class=\"ql-align-justify\">It will run everyday at 3:30 AM; you can trust it because certbot will only run if the certificate is valid less than 30 days.</p><h1>Conclusion</h1><p class=\"ql-align-justify\">In this tutorial, we installed a Let’s Encrypt SSL/TLS certificate for securing our Spring Boot application by using NginX as a SSL proxy. If you have any questions, please look at the links below first.</p><p class=\"ql-align-justify\">Notice: for some distros (such as CentOS),&nbsp;<a href=\"https://en.wikipedia.org/wiki/Security-Enhanced_Linux\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\">SELinux</a>&nbsp;can cause permission denied while connecting upstream. Solution can be found&nbsp;<a href=\"https://stackoverflow.com/questions/23948527/13-permission-denied-while-connecting-to-upstreamnginx\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\">here</a>.</p><h1>Reference material</h1><p class=\"ql-align-justify\"><a href=\"http://tipsonubuntu.com/2016/07/31/install-oracle-java-8-9-ubuntu-16-04-linux-mint-18/\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\">http://tipsonubuntu.com/2016/07/31/install-oracle-java-8-9-ubuntu-16-04-linux-mint-18/</a></p><p class=\"ql-align-justify\"><a href=\"https://spr.com/part-1-getting-started-again-build-a-web-app-with-spring-boot/\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\">https://spr.com/part-1-getting-started-again-build-a-web-app-with-spring-boot/</a></p><p class=\"ql-align-justify\"><a href=\"https://www.digitalocean.com/community/tutorials/how-to-secure-nginx-with-let-s-encrypt-on-ubuntu-16-04\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\">https://www.digitalocean.com/community/tutorials/how-to-secure-nginx-with-let-s-encrypt-on-ubuntu-16-04</a></p><p class=\"ql-align-justify\"><a href=\"https://certbot.eff.org/docs/\" target=\"_blank\" style=\"color: rgb(56, 183, 234);\">https://certbot.eff.org/docs/</a></p><p><br></p>', 0, 15, 'Secure Spring Boot application with Let\'s Encrypt', 4, '201809141801221', b'0', 0, NULL, b'0', b'1', b'0', NULL, 0),
(193292, '2018-09-17 19:23:19', '2018-10-19 16:10:48', '<p>客服账号 帮助文档</p>', 0, 15, '客服账号', 15, '201809171923191', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(193293, '2018-09-17 19:23:33', '2018-09-18 23:52:58', '<p>工作组帮助文档</p>', 0, 15, '工作组', 13, '201809171923321', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(194187, '2018-09-18 23:25:37', '2018-10-19 16:10:55', '<p>代码样式</p>', 0, 15, '代码样式', 8, '201809182325351', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(194267, '2018-09-19 11:53:29', '2018-09-21 19:29:30', '<p>分享萝卜丝系统架构</p>', 0, 15, '萝卜丝架构分享', 5, '201809191153271', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(200955, '2018-09-25 22:49:34', '2018-10-19 16:10:55', '<p>常用语</p>', 0, 15, '常用语', 5, '201809252249331', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(215830, '2018-10-01 11:42:28', '2018-12-27 11:18:39', '<p>“萝卜丝” 是英文 Robot 的中文谐音，意为 机器人、智能 的意思，意味着我们的新平台业务均会围绕着“智能”展开，帮助客户提高工作效率</p><p><br></p>', 0, 15, '为什么起名“萝卜丝”', 4, '201810011142271', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(246468, '2018-10-06 09:58:09', '2018-10-25 21:19:51', '<p>调查问卷</p>', 0, 15, '什么是调查问卷', 4, '201810060958081', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(501647, '2018-10-25 21:24:48', '2018-10-25 21:34:32', '<p>意见反馈</p>', 0, 15, '什么是意见反馈', 1, '201810252124471', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(501648, '2018-10-25 21:25:19', '2019-01-23 09:41:28', '<p>什么是工单系统</p>', 0, 15, '什么是工单系统', 1, '201810252125181', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(501649, '2018-10-25 21:25:46', '2018-10-25 21:25:46', '<p>呼叫中心</p>', 0, 15, '什么是呼叫中心', 0, '201810252125451', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(501650, '2018-10-25 21:26:21', '2018-10-25 21:26:21', '<p>客户CRM</p>', 0, 15, '什么是客户CRM', 0, '201810252126201', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(501651, '2018-10-25 21:26:38', '2018-10-25 21:26:38', '<p>讨论社区</p>', 0, 15, '什么是讨论社区', 0, '201810252126371', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(501652, '2018-10-25 21:26:59', '2018-10-25 21:26:59', '<p>云通讯IM</p>', 0, 15, '什么是云通讯IM', 0, '201810252126581', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(501653, '2018-10-25 21:27:17', '2018-10-25 21:27:17', '<p>视频会议</p>', 0, 15, '什么是视频会议', 0, '201810252127161', b'0', 0, NULL, b'0', b'0', b'0', NULL, 0),
(501654, '2018-10-25 21:27:45', '2018-11-19 19:43:56', '# 人人客服', 0, 15, '什么是人人客服', 9, '201810252127441', b'0', 0, NULL, b'0', b'0', b'0', NULL, 1);

-- --------------------------------------------------------

--
-- 表的结构 `article_category`
--

CREATE TABLE `article_category` (
  `article_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `article_category`
--

INSERT INTO `article_category` (`article_id`, `category_id`) VALUES
(177434, 177282),
(177633, 177282),
(179767, 177657),
(179768, 177657),
(179769, 177657),
(179770, 177657),
(179771, 177657),
(185583, 185582),
(185610, 185609),
(185611, 185609),
(185639, 185609),
(185640, 185609),
(185641, 185609),
(185653, 185609),
(185654, 185609),
(185655, 185609),
(185656, 185609),
(185657, 185609),
(187924, 187923),
(191954, 187923),
(193292, 185609),
(193293, 185609),
(194187, 185609),
(194267, 187923),
(200955, 185609),
(215830, 185582),
(246468, 185609),
(501647, 185609),
(501648, 185609),
(501649, 185609),
(501650, 185609),
(501651, 185609),
(501652, 185609),
(501653, 185609),
(501654, 185609);

-- --------------------------------------------------------

--
-- 表的结构 `article_keyword`
--

CREATE TABLE `article_keyword` (
  `article_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `article_rate`
--

CREATE TABLE `article_rate` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `helpful` bit(1) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `article_read`
--

CREATE TABLE `article_read` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `article_read`
--

INSERT INTO `article_read` (`id`, `created_at`, `updated_at`, `article_id`, `users_id`) VALUES
(246469, '2018-10-06 09:58:16', '2018-10-06 09:58:16', 246468, 240463),
(246470, '2018-10-06 09:59:20', '2018-10-06 09:59:20', 246468, 240463),
(362459, '2018-10-15 21:51:36', '2018-10-15 21:51:36', 185610, 359323),
(362460, '2018-10-15 21:55:45', '2018-10-15 21:55:45', 185610, 359323),
(385146, '2018-10-19 16:10:35', '2018-10-19 16:10:35', 193292, 364168),
(385147, '2018-10-19 16:10:45', '2018-10-19 16:10:45', 185610, 364168),
(385148, '2018-10-19 16:10:46', '2018-10-19 16:10:46', 246468, 364168),
(385149, '2018-10-19 16:10:48', '2018-10-19 16:10:48', 193292, 364168),
(385150, '2018-10-19 16:10:55', '2018-10-19 16:10:55', 200955, 364168),
(385151, '2018-10-19 16:10:55', '2018-10-19 16:10:55', 194187, 364168),
(501644, '2018-10-25 21:19:32', '2018-10-25 21:19:32', 179767, 420004),
(501645, '2018-10-25 21:19:51', '2018-10-25 21:19:51', 246468, 420004),
(501646, '2018-10-25 21:20:29', '2018-10-25 21:20:29', 185610, 420004),
(501655, '2018-10-25 21:34:32', '2018-10-25 21:34:32', 501647, 420004),
(527168, '2018-10-27 11:05:02', '2018-10-27 11:05:02', 501654, 420004),
(527169, '2018-10-27 11:05:26', '2018-10-27 11:05:26', 501654, 420004),
(527176, '2018-10-27 11:10:45', '2018-10-27 11:10:45', 501654, 420004),
(527177, '2018-10-27 11:11:38', '2018-10-27 11:11:38', 501654, 420004),
(527178, '2018-10-27 11:11:59', '2018-10-27 11:11:59', 501654, 420004),
(558395, '2018-10-29 18:47:11', '2018-10-29 18:47:11', 179771, 420004),
(706403, '2018-11-09 22:38:23', '2018-11-09 22:38:23', 501654, 611971),
(706404, '2018-11-09 22:43:25', '2018-11-09 22:43:25', 501654, 611971),
(734560, '2018-11-11 11:21:57', '2018-11-11 11:21:57', 179770, 611971),
(734582, '2018-11-11 13:27:21', '2018-11-11 13:27:21', 179771, 611971),
(734583, '2018-11-11 13:29:21', '2018-11-11 13:29:21', 187924, 611971),
(773479, '2018-11-18 20:43:14', '2018-11-18 20:43:14', 501654, 771418),
(773606, '2018-11-19 19:43:56', '2018-11-19 19:43:56', 501654, 771418),
(796267, '2018-12-11 14:46:55', '2018-12-11 14:46:55', 179770, 793751),
(797893, '2018-12-16 13:23:57', '2018-12-16 13:23:57', 179770, 793751),
(797894, '2018-12-16 13:24:06', '2018-12-16 13:24:06', 179771, 793751),
(797895, '2018-12-16 13:25:21', '2018-12-16 13:25:21', 179770, 793751),
(825568, '2018-12-27 11:16:58', '2018-12-27 11:16:58', 179770, 793751),
(825569, '2018-12-27 11:18:39', '2018-12-27 11:18:39', 215830, 793751),
(825580, '2018-12-27 11:49:04', '2018-12-27 11:49:04', 179770, 793751),
(834983, '2019-01-13 22:08:43', '2019-01-13 22:08:43', 185610, 793751),
(838589, '2019-01-22 04:02:03', '2019-01-22 04:02:03', 185610, 793751),
(839056, '2019-01-23 07:42:31', '2019-01-23 07:42:31', 185610, 793751),
(839121, '2019-01-23 09:41:28', '2019-01-23 09:41:28', 501648, 793751),
(839272, '2019-01-24 00:55:08', '2019-01-24 00:55:08', 185610, 793751),
(839339, '2019-01-24 03:24:40', '2019-01-24 03:24:40', 185610, 793751),
(851716, '2019-02-23 20:21:51', '2019-02-23 20:21:51', 185610, 793751);

-- --------------------------------------------------------

--
-- 表的结构 `authority`
--

CREATE TABLE `authority` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `descriptions` varchar(255) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `authority`
--

INSERT INTO `authority` (`id`, `name`, `value`, `descriptions`, `by_type`) VALUES
(1, '当前会话', 'thread', '人工客服-当前会话', 'workgroup'),
(2, '会话监控', 'monitor', '人工客服-监控组内所有会话', 'workgroup'),
(3, '质量检查', 'quality', '人工客服-Quality Assurance(QA) 质量检查', 'workgroup'),
(4, '客服管理', 'admin', '人工客服-客服账号CRUD增删改查', 'workgroup'),
(5, '我的首页', 'home', '我的首页', 'workgroup'),
(6, '绩效数据', 'statistic', '绩效数据', 'workgroup'),
(7, '人工客服', 'chat', '人工客服', 'workgroup'),
(8, '智能客服', 'robot', '智能客服', 'workgroup'),
(9, '帮助中心', 'support', '帮助中心', 'workgroup'),
(10, '所有设置', 'setting', '所有设置', 'platform'),
(11, '意见反馈', 'feedback', '意见反馈', 'workgroup'),
(12, '工单系统', 'workorder', '工单系统', 'workgroup'),
(13, '呼叫中心', 'callcenter', '呼叫中心', 'workgroup'),
(14, '客户管理', 'crm', '客户管理', 'workgroup'),
(15, '调查问卷', 'wenjuan', '调查问卷', 'workgroup'),
(16, '内部协同', 'internal', '内部协同', 'workgroup'),
(17, '当前访客', 'visitor', '人工客服-当前访客', 'workgroup'),
(18, '历史记录', 'history', '人工客服-历史记录', 'workgroup');

-- --------------------------------------------------------

--
-- 表的结构 `block`
--

CREATE TABLE `block` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `bid` varchar(255) CHARACTER SET utf8 NOT NULL,
  `note` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `blocked_user_id` bigint(20) DEFAULT NULL,
  `users_id` bigint(20) DEFAULT NULL,
  `by_type` varchar(255) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `browse`
--

CREATE TABLE `browse` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `workgroup_id` bigint(20) NOT NULL,
  `visitor_id` bigint(20) NOT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `referrer_id` bigint(20) DEFAULT NULL,
  `url_id` bigint(20) DEFAULT NULL,
  `bid` varchar(255) NOT NULL,
  `actioned` varchar(255) DEFAULT NULL,
  `actioned_at` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `browse_invite`
--

CREATE TABLE `browse_invite` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_accepted` tinyint(1) DEFAULT '0',
  `actioned_at` datetime DEFAULT NULL,
  `b_iid` varchar(255) NOT NULL,
  `from_client` varchar(255) DEFAULT NULL,
  `to_client` varchar(255) DEFAULT NULL,
  `from_user_id` bigint(20) DEFAULT NULL,
  `to_user_id` bigint(20) DEFAULT NULL,
  `workgroup_id` bigint(20) NOT NULL,
  `browse_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `category`
--

CREATE TABLE `category` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `cid` varchar(255) NOT NULL,
  `by_type` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `category`
--

INSERT INTO `category` (`id`, `created_at`, `updated_at`, `name`, `category_id`, `users_id`, `cid`, `by_type`) VALUES
(185582, '2018-09-04 20:43:07', '2018-09-04 20:43:07', '公告中心', NULL, 15, '201809042043071', 'support'),
(185609, '2018-09-04 21:02:51', '2018-09-04 21:02:51', '帮助文档', NULL, 15, '201809042102511', 'support'),
(177657, '2018-08-22 17:12:50', '2018-08-22 17:12:50', '开发文档', NULL, 15, '201808221712491', 'support'),
(183549, '2018-09-02 12:51:12', '2018-09-02 12:54:30', '公告类别5', 177282, 15, '201809021251111', 'support'),
(183548, '2018-09-02 12:51:03', '2018-09-02 12:54:39', '公告类别24', 177282, 15, '201809021251011', 'support'),
(183547, '2018-09-02 12:50:54', '2018-09-02 12:50:54', '公告类别1', 177282, 15, '201809021250531', 'support'),
(186492, '2018-09-05 17:02:03', '2018-09-05 17:02:03', '英国留学', NULL, 15, '201809051702021', 'robot'),
(186510, '2018-09-05 17:14:47', '2018-09-05 17:21:40', '美国留学', NULL, 15, '201809051714451', 'robot'),
(187006, '2018-09-06 09:29:05', '2018-09-06 09:29:05', '加拿大留学', NULL, 15, '201809060929041', 'robot'),
(187923, '2018-09-07 11:54:53', '2018-09-07 11:54:53', '技术分享', NULL, 15, '201809071154531', 'support'),
(213679, '2018-09-29 18:40:48', '2018-09-29 18:40:48', '问候', NULL, 19, '201809291840471', 'cuw_platform'),
(188000, '2018-09-29 00:00:00', '2018-09-29 00:00:00', '询问联系方式', NULL, 19, '201809071154532', 'cuw_platform'),
(188001, '2018-09-29 00:00:00', '2018-09-29 00:00:00', '感谢', NULL, 19, '201809071154533', 'cuw_platform'),
(213680, '2018-09-29 00:00:00', '2018-09-29 00:00:00', '寒暄', NULL, 19, '201809291650492', 'cuw_platform'),
(213681, '2018-09-29 00:00:00', '2018-09-29 00:00:00', '告别', NULL, 19, '201809291650493', 'cuw_platform'),
(214602, '2018-09-30 11:19:42', '2018-09-30 17:24:31', '分组211', NULL, 15, '201809301119411', 'cuw_mine'),
(214603, '2018-09-30 11:19:48', '2018-09-30 17:22:12', '分组3123456', NULL, 15, '201809301119471', 'cuw_mine'),
(214604, '2018-09-30 11:20:11', '2018-09-30 17:23:04', '分组41', NULL, 15, '201809301120101', 'cuw_mine'),
(214862, '2018-09-30 14:16:07', '2018-09-30 14:16:07', '公司分组2', NULL, 15, '201809301416051', 'cuw_company'),
(214840, '2018-09-30 14:07:53', '2018-09-30 17:37:00', '公司分组1', NULL, 15, '201809301407521', 'cuw_company'),
(214863, '2018-09-30 14:16:13', '2018-09-30 14:16:13', '公司分组3', NULL, 15, '201809301416121', 'cuw_company'),
(734584, '2018-11-11 13:33:09', '2018-11-11 13:33:09', '电商手册', NULL, 15, '201811111333081', 'support'),
(794100, '2018-12-07 21:59:10', '2018-12-07 21:59:10', '分组1', NULL, 402942, '201812072159091', 'cuw_mine'),
(794114, '2018-12-07 22:01:50', '2018-12-07 22:01:50', '分组2', NULL, 402942, '201812072201491', 'cuw_mine'),
(794123, '2018-12-07 22:03:28', '2018-12-07 22:03:28', '公司分组4', NULL, 15, '201812072203271', 'cuw_company'),
(798081, '2018-12-16 23:55:51', '2018-12-16 23:55:51', 'FENZU2', NULL, 15, '201812162355511', 'cuw_company'),
(798079, '2018-12-16 23:55:28', '2018-12-16 23:55:28', 'FENZU1', NULL, 15, '201812162355271', 'cuw_company'),
(843301, '2019-01-30 06:47:28', '2019-01-30 06:47:28', 'feedback1', NULL, 15, '201901302047271', 'feedback'),
(843302, '2019-01-30 06:47:38', '2019-01-30 06:47:38', 'feedback2', NULL, 15, '201901302047371', 'feedback'),
(843303, '2019-01-30 06:47:44', '2019-01-30 06:47:44', 'feedback3', NULL, 15, '201901302047431', 'feedback');

-- --------------------------------------------------------

--
-- 表的结构 `comments`
--

CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `cid` varchar(255) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `post_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `company`
--

CREATE TABLE `company` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `cid` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `company`
--

INSERT INTO `company` (`id`, `created_at`, `updated_at`, `avatar`, `cid`, `description`, `name`, `users_id`) VALUES
(216819, '2018-10-02 18:13:10', '2018-10-22 00:56:20', NULL, 'CM0001', '789', '北京', 15),
(216821, '2018-10-02 18:13:16', '2018-10-02 18:13:16', NULL, 'CM0002', NULL, '长春', 15),
(216822, '2018-10-02 18:13:18', '2018-10-02 18:13:18', NULL, 'CM0003', NULL, '吉林市', 15),
(216824, '2018-10-02 18:13:20', '2018-10-02 18:13:20', NULL, 'CM0004', NULL, '烟台', 15),
(216825, '2018-10-02 18:13:22', '2018-10-02 18:13:22', NULL, 'CM0005', NULL, '沈阳', 15),
(216826, '2018-10-02 18:13:24', '2018-10-02 18:13:24', NULL, 'CM0006', NULL, '大连', 15),
(216827, '2018-10-02 18:13:26', '2018-10-02 18:13:26', NULL, 'CM0007', NULL, '太原', 15),
(216828, '2018-10-02 18:13:27', '2018-10-02 18:13:27', NULL, 'CM0008', NULL, '济南', 15),
(216829, '2018-10-02 18:13:30', '2018-10-02 18:13:30', NULL, 'CM0009', NULL, '兰州', 15),
(216830, '2018-10-02 18:13:31', '2018-10-02 18:13:31', NULL, 'CM0010', NULL, '哈尔滨', 15),
(216831, '2018-10-02 18:13:33', '2018-10-02 18:13:33', NULL, 'CM0011', NULL, '福州', 15),
(216832, '2018-10-02 18:13:34', '2018-10-02 18:13:34', NULL, 'CM0012', NULL, '青岛', 15),
(216839, '2018-10-02 18:17:59', '2018-10-02 18:17:59', NULL, 'CM0013', NULL, '厦门', 15),
(216840, '2018-10-02 18:18:00', '2018-10-02 18:18:00', NULL, 'CM0014', NULL, '郑州', 15),
(216841, '2018-10-02 18:18:02', '2018-10-02 18:18:02', NULL, 'CM0015', NULL, '常州', 15),
(216842, '2018-10-02 18:18:04', '2018-10-02 18:18:04', NULL, 'CM0016', NULL, '南京', 15),
(216843, '2018-10-02 18:18:06', '2018-10-02 18:18:06', NULL, 'CM0017', NULL, '徐州', 15),
(216844, '2018-10-02 18:18:07', '2018-10-02 18:18:07', NULL, 'CM0018', NULL, '湖北', 15),
(216845, '2018-10-02 18:18:09', '2018-10-02 18:18:09', NULL, 'CM0019', NULL, '武汉', 15),
(216846, '2018-10-02 18:18:10', '2018-10-02 18:18:10', NULL, 'CM0020', NULL, '合肥', 15),
(216847, '2018-10-02 18:18:12', '2018-10-02 18:18:12', NULL, 'CM0021', NULL, '苏州', 15),
(216848, '2018-10-02 18:18:12', '2018-10-02 18:18:12', NULL, 'CM0022', NULL, '洛阳', 15),
(216849, '2018-10-02 18:18:13', '2018-10-02 18:18:13', NULL, 'CM0023', NULL, '无锡', 15),
(216850, '2018-10-02 18:18:13', '2018-10-02 18:18:13', NULL, 'CM0024', NULL, '江西省', 15),
(216851, '2018-10-02 18:18:15', '2018-10-02 18:18:15', NULL, 'CM0025', NULL, '宜昌', 15),
(216852, '2018-10-02 18:18:15', '2018-10-02 18:18:15', NULL, 'CM0026', NULL, '华东', 15),
(216853, '2018-10-02 18:18:17', '2018-10-02 18:18:17', NULL, 'CM0027', NULL, '重庆', 15),
(216854, '2018-10-02 18:18:19', '2018-10-02 18:18:19', NULL, 'CM0028', NULL, '成都', 15),
(216855, '2018-10-02 18:18:19', '2018-10-02 18:18:19', NULL, 'CM0029', NULL, '东莞', 15),
(216856, '2018-10-02 18:18:20', '2018-10-02 18:18:20', NULL, 'CM0030', NULL, '广州', 15),
(216857, '2018-10-02 18:18:23', '2018-10-02 18:18:23', NULL, 'CM0031', NULL, '贵州', 15),
(216858, '2018-10-02 18:18:24', '2018-10-02 18:18:24', NULL, 'CM0032', NULL, '海南', 15),
(216859, '2018-10-02 18:18:26', '2018-10-02 18:18:26', NULL, 'CM0033', NULL, '昆明', 15),
(216860, '2018-10-02 18:18:27', '2018-10-02 18:18:27', NULL, 'CM0034', NULL, '南宁', 15),
(216861, '2018-10-02 18:18:28', '2018-10-02 18:18:28', NULL, 'CM0035', NULL, '深圳', 15),
(216862, '2018-10-02 18:18:31', '2018-10-02 18:18:31', NULL, 'CM0036', NULL, '邯郸', 15),
(216863, '2018-10-02 18:18:33', '2018-10-02 18:18:33', NULL, 'CM0037', NULL, '呼和浩特', 15),
(216864, '2018-10-02 18:18:34', '2018-10-02 18:18:34', NULL, 'CM0038', NULL, '唐山', 15),
(216865, '2018-10-02 18:18:37', '2018-10-02 18:18:37', NULL, 'CM0039', NULL, '温州', 15),
(216866, '2018-10-02 18:18:38', '2018-10-02 18:18:38', NULL, 'CM0040', NULL, '乌鲁木齐', 15),
(216867, '2018-10-02 18:18:39', '2018-10-02 18:18:39', NULL, 'CM0041', NULL, '西安', 15),
(216868, '2018-10-02 18:18:42', '2018-10-02 18:18:42', NULL, 'CM0042', NULL, '西宁', 15),
(216869, '2018-10-02 18:18:42', '2018-10-02 18:18:42', NULL, 'CM0043', NULL, '银川', 15),
(216870, '2018-10-02 18:18:43', '2018-10-02 18:18:43', NULL, 'CM0044', NULL, '浙江', 15),
(216871, '2018-10-02 18:18:45', '2018-10-02 18:18:45', NULL, 'CM0045', NULL, '石家庄', 15),
(216872, '2018-10-02 18:18:49', '2018-10-02 18:18:49', NULL, 'CM0046', NULL, '宁波', 15),
(216873, '2018-10-02 18:18:51', '2018-10-02 18:18:51', NULL, 'CM0047', NULL, '长沙', 15),
(216874, '2018-10-02 18:18:53', '2018-10-02 18:18:53', NULL, 'CM0048', NULL, '天津', 15),
(216875, '2018-10-02 18:18:55', '2018-10-19 18:48:14', NULL, 'CM0049', '佛山描述', '佛山', 15);

-- --------------------------------------------------------

--
-- 表的结构 `company_region`
--

CREATE TABLE `company_region` (
  `company_id` bigint(20) NOT NULL,
  `region_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `company_region`
--

INSERT INTO `company_region` (`company_id`, `region_id`) VALUES
(216819, 442202),
(216819, 442236),
(216819, 442244),
(216819, 442247),
(216819, 442271),
(216821, 442270),
(216821, 442289),
(216821, 442290),
(216821, 442293),
(216821, 442294),
(216822, 442208),
(216822, 442287),
(216822, 442292),
(216822, 442295),
(216822, 442305),
(216824, 442375),
(216824, 442379),
(216825, 442207),
(216825, 442265),
(216825, 442275),
(216825, 442276),
(216825, 442277),
(216825, 442278),
(216825, 442279),
(216825, 442280),
(216825, 442281),
(216825, 442282),
(216825, 442283),
(216825, 442284),
(216825, 442285),
(216825, 442291),
(216826, 442273),
(216826, 442286),
(216827, 442205),
(216827, 442255),
(216827, 442256),
(216827, 442258),
(216827, 442260),
(216827, 442532),
(216828, 442216),
(216828, 442372),
(216828, 442374),
(216828, 442378),
(216828, 442381),
(216828, 442383),
(216828, 442384),
(216828, 442385),
(216829, 442537),
(216829, 442538),
(216829, 442544),
(216829, 442546),
(216829, 442547),
(216829, 442559),
(216830, 442209),
(216830, 442267),
(216830, 442288),
(216830, 442297),
(216830, 442298),
(216830, 442299),
(216830, 442300),
(216830, 442301),
(216830, 442302),
(216830, 442303),
(216830, 442304),
(216830, 442306),
(216830, 442307),
(216830, 442308),
(216831, 442214),
(216831, 442233),
(216831, 442352),
(216831, 442353),
(216831, 442356),
(216831, 442358),
(216832, 442371),
(216832, 442373),
(216832, 442376),
(216832, 442377),
(216832, 442380),
(216832, 442382),
(216839, 442350),
(216839, 442354),
(216839, 442355),
(216839, 442357),
(216839, 442437),
(216839, 442444),
(216839, 442449),
(216839, 442450),
(216840, 442217),
(216840, 442388),
(216840, 442390),
(216840, 442393),
(216840, 442394),
(216840, 442396),
(216840, 442397),
(216840, 442402),
(216840, 442403),
(216840, 442404),
(216841, 442318),
(216841, 442321),
(216842, 442211),
(216842, 442317),
(216842, 442319),
(216842, 442320),
(216842, 442335),
(216842, 442338),
(216842, 442343),
(216842, 442349),
(216843, 442316),
(216843, 442322),
(216843, 442339),
(216843, 442345),
(216843, 442347),
(216843, 442400),
(216844, 442409),
(216844, 442411),
(216844, 442413),
(216844, 442416),
(216845, 442362),
(216845, 442401),
(216845, 442406),
(216845, 442410),
(216845, 442412),
(216845, 442414),
(216845, 442415),
(216846, 442213),
(216846, 442310),
(216846, 442312),
(216846, 442336),
(216846, 442337),
(216846, 442340),
(216846, 442341),
(216846, 442344),
(216846, 442346),
(216846, 442348),
(216847, 442210),
(216847, 442309),
(216847, 442323),
(216847, 442326),
(216847, 442327),
(216848, 442254),
(216848, 442257),
(216848, 442259),
(216848, 442387),
(216848, 442398),
(216848, 442399),
(216849, 442313),
(216849, 442314),
(216849, 442315),
(216849, 442334),
(216850, 442351),
(216850, 442359),
(216850, 442360),
(216850, 442363),
(216850, 442364),
(216850, 442365),
(216850, 442366),
(216850, 442367),
(216850, 442368),
(216851, 442218),
(216851, 442405),
(216851, 442407),
(216851, 442417),
(216851, 442418),
(216851, 442426),
(216851, 442432),
(216853, 442475),
(216853, 442480),
(216853, 442482),
(216853, 442484),
(216853, 442485),
(216853, 442486),
(216853, 442488),
(216854, 442223),
(216854, 442224),
(216854, 442470),
(216854, 442471),
(216854, 442473),
(216854, 442476),
(216854, 442477),
(216854, 442478),
(216854, 442479),
(216854, 442481),
(216854, 442483),
(216854, 442487),
(216854, 442489),
(216854, 442490),
(216854, 442491),
(216854, 442518),
(216854, 442520),
(216854, 442521),
(216854, 442545),
(216855, 442443),
(216855, 442446),
(216856, 442220),
(216856, 442434),
(216856, 442448),
(216857, 442472),
(216857, 442493),
(216857, 442494),
(216857, 442495),
(216857, 442496),
(216857, 442497),
(216857, 442498),
(216857, 442500),
(216857, 442501),
(216858, 442440),
(216858, 442466),
(216858, 442467),
(216858, 442468),
(216859, 442225),
(216859, 442226),
(216859, 442474),
(216859, 442492),
(216859, 442499),
(216859, 442503),
(216859, 442504),
(216859, 442505),
(216859, 442506),
(216859, 442507),
(216859, 442508),
(216859, 442509),
(216859, 442510),
(216859, 442511),
(216859, 442512),
(216859, 442513),
(216859, 442514),
(216859, 442515),
(216859, 442516),
(216859, 442517),
(216859, 442519),
(216859, 442522),
(216860, 442221),
(216860, 442222),
(216860, 442453),
(216860, 442454),
(216860, 442456),
(216860, 442457),
(216860, 442458),
(216860, 442459),
(216860, 442460),
(216860, 442461),
(216860, 442463),
(216860, 442464),
(216860, 442465),
(216860, 442469),
(216860, 442502),
(216861, 442234),
(216861, 442235),
(216861, 442436),
(216861, 442445),
(216862, 442242),
(216862, 442253),
(216862, 442370),
(216862, 442386),
(216862, 442389),
(216862, 442391),
(216862, 442392),
(216862, 442395),
(216863, 442206),
(216863, 442251),
(216863, 442262),
(216863, 442266),
(216863, 442269),
(216864, 442240),
(216864, 442245),
(216864, 442264),
(216864, 442274),
(216864, 442296),
(216865, 442332),
(216866, 442232),
(216866, 442524),
(216866, 442562),
(216866, 442563),
(216866, 442564),
(216866, 442565),
(216866, 442566),
(216866, 442567),
(216866, 442568),
(216866, 442569),
(216866, 442570),
(216866, 442571),
(216866, 442572),
(216866, 442573),
(216866, 442574),
(216866, 442575),
(216867, 442228),
(216867, 442526),
(216867, 442527),
(216867, 442528),
(216867, 442529),
(216867, 442530),
(216867, 442531),
(216867, 442533),
(216867, 442534),
(216867, 442541),
(216867, 442543),
(216868, 442227),
(216868, 442229),
(216868, 442230),
(216868, 442523),
(216868, 442535),
(216868, 442536),
(216868, 442539),
(216868, 442540),
(216868, 442542),
(216868, 442549),
(216868, 442550),
(216868, 442551),
(216868, 442552),
(216868, 442553),
(216868, 442554),
(216868, 442555),
(216868, 442561),
(216869, 442231),
(216869, 442263),
(216869, 442268),
(216869, 442272),
(216869, 442525),
(216869, 442548),
(216869, 442557),
(216869, 442558),
(216869, 442560),
(216870, 442325),
(216870, 442329),
(216870, 442330),
(216870, 442333),
(216870, 442342),
(216870, 442369),
(216871, 442204),
(216871, 442241),
(216871, 442243),
(216871, 442248),
(216871, 442249),
(216871, 442250),
(216871, 442252),
(216871, 442261),
(216871, 442556),
(216872, 442212),
(216872, 442311),
(216872, 442328),
(216872, 442331),
(216873, 442215),
(216873, 442219),
(216873, 442324),
(216873, 442361),
(216873, 442408),
(216873, 442419),
(216873, 442420),
(216873, 442421),
(216873, 442422),
(216873, 442423),
(216873, 442424),
(216873, 442425),
(216873, 442427),
(216873, 442428),
(216873, 442429),
(216873, 442430),
(216873, 442431),
(216874, 442238),
(216874, 442239),
(216874, 442246),
(216875, 442203),
(216875, 442237),
(216875, 442433),
(216875, 442435),
(216875, 442438),
(216875, 442439),
(216875, 442441),
(216875, 442442),
(216875, 442447),
(216875, 442451),
(216875, 442452),
(216875, 442455),
(216875, 442462);

-- --------------------------------------------------------

--
-- 表的结构 `country`
--

CREATE TABLE `country` (
  `id` bigint(20) NOT NULL,
  `cid` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `customer`
--

CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `cid` varchar(255) NOT NULL,
  `company` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `cuw`
--

CREATE TABLE `cuw` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `cid` varchar(255) NOT NULL,
  `category_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `cuw`
--

INSERT INTO `cuw` (`id`, `created_at`, `updated_at`, `content`, `name`, `users_id`, `count`, `cid`, `category_id`) VALUES
(6, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '您好，有什么可以帮您的？', '您好，有什么可以帮您的？', 19, 0, '201809291650494', 213679),
(7, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '您想了解哪方面呢？', '您想了解哪方面呢？', 19, 0, '201809291650495', 213679),
(8, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '上午好，很高兴问你服务', '上午好，很高兴问你服务', 19, 0, '201809291650496', 213679),
(9, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '欢迎光临，有什么可以为您效劳？', '欢迎光临，有什么可以为您效劳？', 19, 0, '201809291650497', 213679),
(10, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '请问怎么称呼您？', '请问怎么称呼您？', 19, 0, '201809291650498', 213679),
(11, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '方便的话请您提供一下您的联系电话，我电话给您沟通一下，这样更加直观', '方便的话请您提供一下您的联系电话，我电话给您沟通一下，这样更加直观', 19, 0, '201809291650499', 188000),
(12, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '您常用的QQ是多少呢？我加您一下，方便我们及时联系', '您常用的QQ是多少呢？我加您一下，方便我们及时联系', 19, 0, '201809291650411', 188000),
(13, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '请提供一下您的邮箱，我马上准备发送您所需要的资料', '请提供一下您的邮箱，我马上准备发送您所需要的资料', 19, 0, '201809291650412', 188000),
(14, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '感谢光临，欢迎再来', '感谢光临，欢迎再来', 19, 0, '201809291650413', 213681),
(15, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '抱歉让您久等了', '抱歉让您久等了', 19, 0, '201809291650414', 213680),
(16, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '祝您一天都有快乐的心情噢', '祝您一天都有快乐的心情噢', 19, 0, '201809291650415', 188001),
(17, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '您先休息下，我正在努力查询中…o(∩_∩)o', '您先休息下，我正在努力查询中…o(∩_∩)o', 19, 0, '201809291650416', 213680),
(18, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '欢迎您常来！祝您好心情！', '欢迎您常来！祝您好心情！', 19, 0, '201809291650417', 213681),
(19, '2017-09-26 16:00:00', '2017-09-26 16:00:00', '您的满意一直是我们的目标，如果有任何疑问欢迎您随时联系', '您的满意一直是我们的目标，如果有任何疑问欢迎您随时联系', 19, 0, '201809291650418', 213681),
(214746, '2018-09-30 13:30:48', '2018-09-30 13:30:48', '常用语1内容', '常用语1', 15, 0, '201809301330471', 214592),
(214748, '2018-09-30 13:31:12', '2018-09-30 17:48:33', '常用语3内容', '常用语3', 15, 0, '201809301331111', 214603),
(214749, '2018-09-30 13:31:24', '2018-09-30 17:24:20', '常用语41内容', '常用语41', 15, 0, '201809301331231', 214604),
(214847, '2018-09-30 14:11:19', '2018-09-30 14:11:19', '公司常用语1内容', '公司常用语1', 15, 0, '201809301411181', 214840),
(214864, '2018-09-30 14:16:39', '2018-09-30 17:48:46', '公司常用语2123内容', '公司常用语2123', 15, 0, '201809301416381', 214862),
(214908, '2018-09-30 14:29:20', '2018-09-30 14:29:20', '常用语12内容 ', '常用语12', 15, 0, '201809301429191', 214592),
(214910, '2018-09-30 14:30:41', '2018-09-30 17:48:53', '公司常用语123内容', '公司常用语123', 15, 0, '201809301430401', 214862),
(215292, '2018-09-30 17:52:32', '2018-09-30 17:52:32', '公司常用语31内容', '公司常用语31', 15, 0, '201809301752311', 214863),
(794113, '2018-12-07 22:01:39', '2018-12-07 22:01:39', '常用1-查给你用哪个', '常用语1', 402942, 0, '201812072201371', 794100),
(794117, '2018-12-07 22:02:01', '2018-12-07 22:02:01', '常用12-查给你用哪个', '常用语12', 402942, 0, '201812072202001', 794100),
(794118, '2018-12-07 22:02:15', '2018-12-07 22:02:15', '常用21-查给你用哪个', '常用语21', 402942, 0, '201812072202141', 794114),
(794150, '2018-12-07 22:27:56', '2018-12-07 22:27:56', 'dsdsdsd', 'sdsd', 15, 0, '201812072227551', 214862),
(794151, '2018-12-07 22:28:15', '2018-12-07 22:28:15', 'ereer', '2345', 15, 0, '201812072228141', 794123),
(798082, '2018-12-16 23:55:59', '2018-12-16 23:55:59', 'FET', 'REN', 15, 0, '201812162355591', 798081),
(798080, '2018-12-16 23:55:38', '2018-12-16 23:55:38', 'TEST1', 'TEST1', 15, 0, '201812162355361', 798079);

-- --------------------------------------------------------

--
-- 表的结构 `department`
--

CREATE TABLE `department` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `did` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `feedback`
--

CREATE TABLE `feedback` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `visitor_id` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `fid` varchar(255) NOT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `is_replied` bit(1) DEFAULT NULL,
  `reply_content` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `fingerprint`
--

CREATE TABLE `fingerprint` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `my_key` varchar(255) DEFAULT NULL,
  `my_value` longtext,
  `users_id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `is_system` bit(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `func_info`
--

CREATE TABLE `func_info` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `func_scope_category_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `groups`
--

CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `max_count` int(11) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `gid` varchar(255) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `announcement` varchar(255) DEFAULT NULL,
  `is_dismissed` bit(1) DEFAULT NULL,
  `num` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `groups`
--

INSERT INTO `groups` (`id`, `created_at`, `updated_at`, `description`, `max_count`, `nickname`, `by_type`, `users_id`, `gid`, `avatar`, `announcement`, `is_dismissed`, `num`) VALUES
(795085, '2018-12-09 14:34:49', '2018-12-09 14:34:49', '群组描述：这个家伙很懒，什么也没写', 10000, '超级管理员,王晓晓,李大大,张小小,白玉红', 'group', 15, '201812091434481', 'https://bytedesk.oss-cn-shenzhen.aliyuncs.com/avatars/group_default_avatar.png', '群组公告：这个家伙很懒，什么也没写', b'0', NULL),
(795261, '2018-12-09 16:39:25', '2018-12-09 16:39:25', '群组描述：这个家伙很懒，什么也没写', 10000, '超级管理员,张小小,白玉红', 'group', 15, '201812091639241', 'https://bytedesk.oss-cn-shenzhen.aliyuncs.com/avatars/group_default_avatar.png', '群组公告：这个家伙很懒，什么也没写', b'0', NULL),
(795276, '2018-12-09 16:44:41', '2018-12-09 16:44:41', '群组描述：这个家伙很懒，什么也没写', 10000, '超级管理员,申辰,李妍', 'group', 15, '201812091644401', 'https://bytedesk.oss-cn-shenzhen.aliyuncs.com/avatars/group_default_avatar.png', '群组公告：这个家伙很懒，什么也没写', b'0', NULL),
(796717, '2018-12-12 11:12:55', '2019-03-04 01:51:46', 'Test group description', 10000, 'Test group nickname', 'group', 161435, '201812121112551', 'https://bytedesk.oss-cn-shenzhen.aliyuncs.com/avatars/group_default_avatar.png', 'Test group announcement', b'1', NULL),
(797090, '2018-12-13 16:20:28', '2018-12-13 16:20:28', '群组描述：这个家伙很懒，什么也没写', 10000, '张小小,超级管理员,李大大,王晓晓,白玉红', 'group', 169157, '201812131620261', 'https://bytedesk.oss-cn-shenzhen.aliyuncs.com/avatars/group_default_avatar.png', '群组公告：这个家伙很懒，什么也没写', b'0', NULL),
(796773, '2018-12-12 12:08:11', '2018-12-12 12:08:11', '群组描述：这个家伙很懒，什么也没写', 10000, '王晓晓,超级管理员,李大大,张小小,白玉红', 'group', 169128, '201812121208101', 'https://bytedesk.oss-cn-shenzhen.aliyuncs.com/avatars/group_default_avatar.png', '群组公告：这个家伙很懒，什么也没写', b'0', NULL),
(835544, '2019-01-14 23:23:35', '2019-01-14 23:23:35', '群组描述：这个家伙很懒，什么也没写', 10000, '李大大,李靓竹,李益,李妍', 'group', 161435, '201901142323341', 'https://bytedesk.oss-cn-shenzhen.aliyuncs.com/avatars/group_default_avatar.png', '群组公告：这个家伙很懒，什么也没写', b'0', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `groups_admin`
--

CREATE TABLE `groups_admin` (
  `groups_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `groups_admin`
--

INSERT INTO `groups_admin` (`groups_id`, `users_id`) VALUES
(795085, 15),
(795261, 15),
(795276, 15),
(796717, 161435),
(796773, 169128),
(797090, 169157),
(835544, 161435);

-- --------------------------------------------------------

--
-- 表的结构 `groups_detail`
--

CREATE TABLE `groups_detail` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `groups_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `groups_member`
--

CREATE TABLE `groups_member` (
  `groups_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `groups_member`
--

INSERT INTO `groups_member` (`groups_id`, `users_id`) VALUES
(795085, 15),
(795085, 169128),
(795085, 169157),
(795085, 402937),
(795261, 15),
(795261, 169157),
(795261, 402937),
(795276, 15),
(795276, 402939),
(795276, 402940),
(796717, 161435),
(796717, 169128),
(796717, 169157),
(796717, 402937),
(796773, 15),
(796773, 161435),
(796773, 169157),
(796773, 402937),
(796773, 402938),
(797090, 15),
(797090, 161435),
(797090, 169128),
(797090, 169157),
(797090, 194670),
(797090, 402937),
(797090, 402938),
(835544, 161435),
(835544, 402940),
(835544, 402941),
(835544, 402942);

-- --------------------------------------------------------

--
-- 表的结构 `groups_muted`
--

CREATE TABLE `groups_muted` (
  `groups_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(855930),
(855930),
(855930);

-- --------------------------------------------------------

--
-- 表的结构 `invite`
--

CREATE TABLE `invite` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_accepted` tinyint(1) DEFAULT '0',
  `actioned_at` datetime DEFAULT NULL,
  `exit_at` datetime DEFAULT NULL,
  `from_client` varchar(255) DEFAULT NULL,
  `t_iid` varchar(255) NOT NULL,
  `to_client` varchar(255) DEFAULT NULL,
  `from_user_id` bigint(20) DEFAULT NULL,
  `thread_id` bigint(20) DEFAULT NULL,
  `to_user_id` bigint(20) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `ip`
--

CREATE TABLE `ip` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `area_id` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `city_id` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `country_id` varchar(255) DEFAULT NULL,
  `county` varchar(255) DEFAULT NULL,
  `county_id` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `isp` varchar(255) DEFAULT NULL,
  `isp_id` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `region_id` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `leave_message`
--

CREATE TABLE `leave_message` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) DEFAULT NULL,
  `visitor_id` bigint(20) NOT NULL,
  `lid` varchar(255) NOT NULL,
  `is_replied` bit(1) DEFAULT NULL,
  `reply` varchar(255) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL,
  `work_group_id` bigint(20) DEFAULT NULL,
  `agent_id` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `claimer_id` bigint(20) DEFAULT NULL,
  `sub_domain` varchar(255) DEFAULT NULL,
  `my_summary` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `message`
--

CREATE TABLE `message` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL,
  `queue_id` bigint(20) DEFAULT NULL,
  `thread_id` bigint(20) DEFAULT NULL,
  `users_id` bigint(20) DEFAULT NULL,
  `mid` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `format` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `length` int(11) DEFAULT NULL,
  `location_x` double DEFAULT NULL,
  `location_y` double DEFAULT NULL,
  `media_id` varchar(255) DEFAULT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  `is_played` bit(1) DEFAULT NULL,
  `scale` double DEFAULT NULL,
  `thumb_media_id` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `video_or_short_thumb_url` varchar(255) DEFAULT NULL,
  `video_or_short_url` varchar(255) DEFAULT NULL,
  `voice_url` varchar(255) DEFAULT NULL,
  `wid` varchar(255) DEFAULT NULL,
  `client` varchar(255) DEFAULT NULL,
  `cid` varchar(255) DEFAULT NULL,
  `gid` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `questionnaire_id` bigint(20) DEFAULT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `session_type` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` varchar(512) DEFAULT NULL,
  `destroy_after_reading` bit(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `message_answer`
--

CREATE TABLE `message_answer` (
  `message_id` bigint(20) NOT NULL,
  `answer_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `message_deleted`
--

CREATE TABLE `message_deleted` (
  `message_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `message_status`
--

CREATE TABLE `message_status` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `message_id` bigint(20) DEFAULT NULL,
  `users_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `message_workgroup`
--

CREATE TABLE `message_workgroup` (
  `message_id` bigint(20) NOT NULL,
  `workgroup_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `mini_program_info`
--

CREATE TABLE `mini_program_info` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `visit_status` int(11) DEFAULT NULL,
  `wid` varchar(255) NOT NULL,
  `wechat_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `monitor`
--

CREATE TABLE `monitor` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `notice`
--

CREATE TABLE `notice` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` varchar(512) DEFAULT NULL,
  `nid` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `is_processed` bit(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `notice_reader`
--

CREATE TABLE `notice_reader` (
  `notice_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `notice_users`
--

CREATE TABLE `notice_users` (
  `notice_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `notice_users`
--

INSERT INTO `notice_users` (`notice_id`, `users_id`) VALUES
(852844, 169128),
(852931, 15),
(854595, 15),
(854599, 402938);

-- --------------------------------------------------------

--
-- 表的结构 `oauth_client_details`
--

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(128) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` longtext,
  `autoapprove` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `oauth_client_details`
--

INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES
('client', NULL, '$2a$10$55oBftBZ.p9XGTOEc3W9xOIwH.YwuqwAZToiePusx6T/uk4heElPO', 'all', 'password,authorization_code,refresh_token,implicit,client_credentials', NULL, NULL, 2592000, 2592000, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `payment`
--

CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `ended_at` datetime DEFAULT NULL,
  `started_at` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `pay_feature`
--

CREATE TABLE `pay_feature` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `current_agent_count` int(11) DEFAULT '1',
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `post`
--

CREATE TABLE `post` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` longtext,
  `pid` varchar(255) NOT NULL,
  `read_count` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `is_top` bit(1) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `post_category`
--

CREATE TABLE `post_category` (
  `post_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `post_keyword`
--

CREATE TABLE `post_keyword` (
  `post_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `questionnaire`
--

CREATE TABLE `questionnaire` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `qid` varchar(255) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `questionnaire`
--

INSERT INTO `questionnaire` (`id`, `created_at`, `updated_at`, `name`, `qid`, `users_id`) VALUES
(1, '2018-10-06 00:00:00', '2018-10-06 00:00:00', '大学长业务咨询', '201810061551191', 15);

-- --------------------------------------------------------

--
-- 表的结构 `questionnaire_answer`
--

CREATE TABLE `questionnaire_answer` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `input_content` varchar(255) DEFAULT NULL,
  `qid` varchar(255) NOT NULL,
  `textarea_content` varchar(255) DEFAULT NULL,
  `questionnaire_item_id` bigint(20) NOT NULL,
  `questionnaire_item_item_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `questionnaire_answer_item`
--

CREATE TABLE `questionnaire_answer_item` (
  `questionnaire_answer_id` bigint(20) NOT NULL,
  `questionnaire_item_item_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `questionnaire_item`
--

CREATE TABLE `questionnaire_item` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `qid` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL,
  `questionnaire_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `questionnaire_item`
--

INSERT INTO `questionnaire_item` (`id`, `created_at`, `updated_at`, `qid`, `title`, `by_type`, `questionnaire_id`, `users_id`) VALUES
(1, '2018-10-06 00:00:00', '2018-10-06 00:00:00', '201810061551192', '请选择您需要咨询的业务类型:', 'radio', 1, 15);

-- --------------------------------------------------------

--
-- 表的结构 `questionnaire_item_item`
--

CREATE TABLE `questionnaire_item_item` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `qid` varchar(255) NOT NULL,
  `questionnaire_item_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `questionnaire_item_item`
--

INSERT INTO `questionnaire_item_item` (`id`, `created_at`, `updated_at`, `content`, `qid`, `questionnaire_item_id`, `users_id`) VALUES
(1, '2018-10-06 00:00:00', '2018-10-06 00:00:00', '留学', '201810061551181', 1, 15),
(2, '2018-10-06 00:00:00', '2018-10-06 00:00:00', '语培', '201810061551182', 1, 15),
(3, '2018-10-06 00:00:00', '2018-10-06 00:00:00', '移民', '201810061551183', 1, 15),
(4, '2018-10-06 00:00:00', '2018-10-06 00:00:00', '其他', '201810061551184', 1, 15);

-- --------------------------------------------------------

--
-- 表的结构 `queue`
--

CREATE TABLE `queue` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `agent_id` bigint(20) DEFAULT NULL,
  `visitor_id` bigint(20) NOT NULL,
  `workgroup_id` bigint(20) NOT NULL,
  `agent_client` varchar(255) DEFAULT NULL,
  `qid` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `thread_id` bigint(20) DEFAULT NULL,
  `actioned_at` datetime DEFAULT NULL,
  `client` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `rate`
--

CREATE TABLE `rate` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_auto` bit(1) DEFAULT NULL,
  `client` varchar(255) DEFAULT NULL,
  `is_invite` bit(1) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `agent_id` bigint(20) NOT NULL,
  `thread_id` bigint(20) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `visitor_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `region`
--

CREATE TABLE `region` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `lat` varchar(255) DEFAULT NULL,
  `lng` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `region`
--

INSERT INTO `region` (`id`, `code`, `lat`, `lng`, `name`, `parent_id`, `by_type`) VALUES
(442202, '110000', '39.92998577808', '116.39564503788', '北京市', NULL, 'province'),
(442203, '120000', '39.14392990331', '117.21081309155', '天津市', NULL, 'province'),
(442204, '130000', '38.613839749251', '115.66143362422', '河北省', NULL, 'province'),
(442205, '140000', '37.866565990509', '112.51549586384', '山西省', NULL, 'province'),
(442206, '150000', '43.468238221949', '114.41586754817', '内蒙古自治区', NULL, 'province'),
(442207, '210000', '41.621600105958', '122.75359155772', '辽宁省', NULL, 'province'),
(442208, '220000', '43.678846185241', '126.26287593078', '吉林省', NULL, 'province'),
(442209, '230000', '47.356591643111', '128.04741371499', '黑龙江省', NULL, 'province'),
(442210, '310000', '31.249161710015', '121.48789948569', '上海市', NULL, 'province'),
(442211, '320000', '33.013797169954', '119.36848893836', '江苏省', NULL, 'province'),
(442212, '330000', '29.159494120761', '119.95720242066', '浙江省', NULL, 'province'),
(442213, '340000', '31.859252417079', '117.21600520757', '安徽省', NULL, 'province'),
(442214, '350000', '26.050118295661', '117.98494311991', '福建省', NULL, 'province'),
(442215, '360000', '27.757258443441', '115.6760823667', '江西省', NULL, 'province'),
(442216, '370000', '36.099289929728', '118.52766339288', '山东省', NULL, 'province'),
(442217, '410000', '34.157183767956', '113.48680405753', '河南省', NULL, 'province'),
(442218, '420000', '31.20931625014', '112.41056219213', '湖北省', NULL, 'province'),
(442219, '430000', '27.695864052356', '111.72066354648', '湖南省', NULL, 'province'),
(442220, '440000', '23.408003729025', '113.39481755876', '广东省', NULL, 'province'),
(442221, '450000', '23.552254688119', '108.92427442706', '广西壮族自治区', NULL, 'province'),
(442222, '460000', '19.180500801261', '109.73375548794', '海南省', NULL, 'province'),
(442223, '500000', '29.544606108886', '106.53063501341', '重庆市', NULL, 'province'),
(442224, '510000', '30.367480937958', '102.8991597236', '四川省', NULL, 'province'),
(442225, '520000', '26.902825927797', '106.7349961033', '贵州省', NULL, 'province'),
(442226, '530000', '24.864212795483', '101.59295163701', '云南省', NULL, 'province'),
(442227, '540000', '31.367315402715', '89.137981684031', '西藏自治区', NULL, 'province'),
(442228, '610000', '35.860026261323', '109.50378929073', '陕西省', NULL, 'province'),
(442229, '620000', '38.103267343752', '102.45762459934', '甘肃省', NULL, 'province'),
(442230, '630000', '35.499761004275', '96.202543672261', '青海省', NULL, 'province'),
(442231, '640000', '37.321323112295', '106.15548126505', '宁夏回族自治区', NULL, 'province'),
(442232, '650000', '42.127000957642', '85.614899338339', '新疆维吾尔自治区', NULL, 'province'),
(442233, '710000', '24.086956718805', '121.97387097872', '台湾省', NULL, 'province'),
(442234, '810000', '22.29358599328', '114.18612410257', '香港特别行政区', NULL, 'province'),
(442235, '820000', '22.204117988443', '113.55751910182', '澳门特别行政区', NULL, 'province'),
(442236, '110100', '39.92998577808', '116.39564503788', '市辖区', 442202, 'city'),
(442237, '120100', '39.14392990331', '117.21081309155', '市辖区', 442203, 'city'),
(442238, '130100', '38.048958314615', '114.52208184421', '石家庄市', 442204, 'city'),
(442239, '130200', '39.650530922537', '118.18345059773', '唐山市', 442204, 'city'),
(442240, '130300', '39.945461565898', '119.60436761612', '秦皇岛市', 442204, 'city'),
(442241, '130400', '36.609307928471', '114.48269393234', '邯郸市', 442204, 'city'),
(442242, '130500', '37.069531196912', '114.52048681294', '邢台市', 442204, 'city'),
(442243, '130600', '38.886564548027', '115.49481016908', '保定市', 442204, 'city'),
(442244, '130700', '40.811188491103', '114.89378153033', '张家口市', 442204, 'city'),
(442245, '130800', '40.992521052457', '117.93382245584', '承德市', 442204, 'city'),
(442246, '130900', '38.297615350326', '116.86380647644', '沧州市', 442204, 'city'),
(442247, '131000', '39.518610625085', '116.70360222264', '廊坊市', 442204, 'city'),
(442248, '131100', '37.746929045857', '115.68622865291', '衡水市', 442204, 'city'),
(442249, '139000', '38.613839749251', '115.66143362422', '省直辖县级行政区划', 442204, 'city'),
(442250, '140100', '37.890277053968', '112.55086358906', '太原市', 442205, 'city'),
(442251, '140200', '40.113744499705', '113.29050867308', '大同市', 442205, 'city'),
(442252, '140300', '37.869529493223', '113.56923760163', '阳泉市', 442205, 'city'),
(442253, '140400', '36.201664385743', '113.12029208573', '长治市', 442205, 'city'),
(442254, '140500', '35.499834467226', '112.86733275751', '晋城市', 442205, 'city'),
(442255, '140600', '39.337671966221', '112.47992772666', '朔州市', 442205, 'city'),
(442256, '140700', '37.693361526798', '112.73851439992', '晋中市', 442205, 'city'),
(442257, '140800', '35.038859479812', '111.00685365308', '运城市', 442205, 'city'),
(442258, '140900', '38.461030572959', '112.72793882881', '忻州市', 442205, 'city'),
(442259, '141000', '36.099745443585', '111.53878759641', '临汾市', 442205, 'city'),
(442260, '141100', '37.527316096963', '111.14315660235', '吕梁市', 442205, 'city'),
(442261, '150100', '40.828318873082', '111.66035052005', '呼和浩特市', 442206, 'city'),
(442262, '150200', '40.647119425709', '109.84623853249', '包头市', 442206, 'city'),
(442263, '150300', '39.683177006785', '106.83199909716', '乌海市', 442206, 'city'),
(442264, '150400', '42.297112320317', '118.93076119217', '赤峰市', 442206, 'city'),
(442265, '150500', '43.633756072996', '122.26036326322', '通辽市', 442206, 'city'),
(442266, '150600', '39.816489560602', '109.99370625145', '鄂尔多斯市', 442206, 'city'),
(442267, '150700', '49.201636054604', '119.760821794', '呼伦贝尔市', 442206, 'city'),
(442268, '150800', '40.769179902429', '107.42380671968', '巴彦淖尔市', 442206, 'city'),
(442269, '150900', '41.022362946751', '113.11284639068', '乌兰察布市', 442206, 'city'),
(442270, '152200', '46.083757065182', '122.04816651407', '兴安盟', 442206, 'city'),
(442271, '152500', '43.939704842324', '116.02733968896', '锡林郭勒盟', 442206, 'city'),
(442272, '152900', '38.843075264408', '105.69568287113', '阿拉善盟', 442206, 'city'),
(442273, '210100', '41.808644783516', '123.43279092161', '沈阳市', 442207, 'city'),
(442274, '210200', '38.948709938304', '121.59347778144', '大连市', 442207, 'city'),
(442275, '210300', '41.118743682153', '123.00776332888', '鞍山市', 442207, 'city'),
(442276, '210400', '41.877303829591', '123.92981976705', '抚顺市', 442207, 'city'),
(442277, '210500', '41.325837626649', '123.77806236979', '本溪市', 442207, 'city'),
(442278, '210600', '40.129022826638', '124.33854311477', '丹东市', 442207, 'city'),
(442279, '210700', '41.130878875917', '121.14774873824', '锦州市', 442207, 'city'),
(442280, '210800', '40.668651066474', '122.23339137079', '营口市', 442207, 'city'),
(442281, '210900', '42.01925010706', '121.66082212857', '阜新市', 442207, 'city'),
(442282, '211000', '41.273339265569', '123.17245120515', '辽阳市', 442207, 'city'),
(442283, '211100', '41.141248022956', '122.07322781023', '盘锦市', 442207, 'city'),
(442284, '211200', '42.299757012125', '123.85484961462', '铁岭市', 442207, 'city'),
(442285, '211300', '41.571827667857', '120.44616270274', '朝阳市', 442207, 'city'),
(442286, '211400', '40.743029881318', '120.86075764476', '葫芦岛市', 442207, 'city'),
(442287, '220100', '43.898337607098', '125.3136424272', '长春市', 442208, 'city'),
(442288, '220200', '43.871988334359', '126.56454398883', '吉林市', 442208, 'city'),
(442289, '220300', '43.175524701126', '124.39138207368', '四平市', 442208, 'city'),
(442290, '220400', '42.923302619054', '125.13368605218', '辽源市', 442208, 'city'),
(442291, '220500', '41.736397129868', '125.94265013851', '通化市', 442208, 'city'),
(442292, '220600', '41.945859397018', '126.43579767535', '白山市', 442208, 'city'),
(442293, '220700', '45.136048970084', '124.83299453234', '松原市', 442208, 'city'),
(442294, '220800', '45.621086275219', '122.8407766791', '白城市', 442208, 'city'),
(442295, '222400', '42.896413603744', '129.48590195816', '延边朝鲜族自治州', 442208, 'city'),
(442296, '230100', '45.773224633239', '126.65771685545', '哈尔滨市', 442209, 'city'),
(442297, '230200', '47.347699813366', '123.98728894217', '齐齐哈尔市', 442209, 'city'),
(442298, '230300', '45.321539886551', '130.94176727325', '鸡西市', 442209, 'city'),
(442299, '230400', '47.338665903727', '130.29247205063', '鹤岗市', 442209, 'city'),
(442300, '230500', '46.655102062482', '131.17140173958', '双鸭山市', 442209, 'city'),
(442301, '230600', '46.596709020008', '125.02183973021', '大庆市', 442209, 'city'),
(442302, '230700', '47.734685075079', '128.91076597792', '伊春市', 442209, 'city'),
(442303, '230800', '46.81377960474', '130.28473458595', '佳木斯市', 442209, 'city'),
(442304, '230900', '45.77500536864', '131.01904804712', '七台河市', 442209, 'city'),
(442305, '231000', '44.588521152783', '129.60803539564', '牡丹江市', 442209, 'city'),
(442306, '231100', '50.250690090738', '127.50083029524', '黑河市', 442209, 'city'),
(442307, '231200', '46.646063926997', '126.98909457163', '绥化市', 442209, 'city'),
(442308, '232700', '51.991788968014', '124.19610419017', '大兴安岭地区', 442209, 'city'),
(442309, '310100', '31.249161710015', '121.48789948569', '市辖区', 442210, 'city'),
(442310, '320100', '32.057235501806', '118.77807440803', '南京市', 442211, 'city'),
(442311, '320200', '31.570037451923', '120.30545590054', '无锡市', 442211, 'city'),
(442312, '320300', '34.271553431092', '117.18810662318', '徐州市', 442211, 'city'),
(442313, '320400', '31.771396744684', '119.98186101346', '常州市', 442211, 'city'),
(442314, '320500', '31.317987367952', '120.61990711549', '苏州市', 442211, 'city'),
(442315, '320600', '32.014664540823', '120.87380095093', '南通市', 442211, 'city'),
(442316, '320700', '34.60154896701', '119.17387221742', '连云港市', 442211, 'city'),
(442317, '320800', '33.606512739276', '119.03018636466', '淮安市', 442211, 'city'),
(442318, '320900', '33.379861877121', '120.14887181794', '盐城市', 442211, 'city'),
(442319, '321000', '32.408505254568', '119.42777755117', '扬州市', 442211, 'city'),
(442320, '321100', '32.204409443599', '119.45583540513', '镇江市', 442211, 'city'),
(442321, '321200', '32.47605327483', '119.91960601619', '泰州市', 442211, 'city'),
(442322, '321300', '33.952049733709', '118.29689337855', '宿迁市', 442211, 'city'),
(442323, '330100', '30.259244461536', '120.21937541572', '杭州市', 442212, 'city'),
(442324, '330200', '29.885258965918', '121.57900597259', '宁波市', 442212, 'city'),
(442325, '330300', '28.002837594041', '120.69063473371', '温州市', 442212, 'city'),
(442326, '330400', '30.773992239582', '120.76042769896', '嘉兴市', 442212, 'city'),
(442327, '330500', '30.877925155691', '120.13724316328', '湖州市', 442212, 'city'),
(442328, '330600', '30.002364580528', '120.59246738555', '绍兴市', 442212, 'city'),
(442329, '330700', '29.102899105391', '119.65257570368', '金华市', 442212, 'city'),
(442330, '330800', '28.956910447536', '118.87584165151', '衢州市', 442212, 'city'),
(442331, '330900', '30.036010302554', '122.16987209835', '舟山市', 442212, 'city'),
(442332, '331000', '28.668283285674', '121.44061293594', '台州市', 442212, 'city'),
(442333, '331100', '28.456299552144', '119.92957584319', '丽水市', 442212, 'city'),
(442334, '340100', '31.866942260687', '117.28269909168', '合肥市', 442213, 'city'),
(442335, '340200', '31.366019787543', '118.38410842323', '芜湖市', 442213, 'city'),
(442336, '340300', '32.929498906698', '117.35707986588', '蚌埠市', 442213, 'city'),
(442337, '340400', '32.642811823748', '117.01863886329', '淮南市', 442213, 'city'),
(442338, '340500', '31.68852815888', '118.51588184662', '马鞍山市', 442213, 'city'),
(442339, '340600', '33.960023305364', '116.79144742863', '淮北市', 442213, 'city'),
(442340, '340700', '30.940929694666', '117.81942872881', '铜陵市', 442213, 'city'),
(442341, '340800', '30.537897817381', '117.05873877211', '安庆市', 442213, 'city'),
(442342, '341000', '29.734434856163', '118.293569632', '黄山市', 442213, 'city'),
(442343, '341100', '32.317350595384', '118.32457035098', '滁州市', 442213, 'city'),
(442344, '341200', '32.90121133057', '115.82093225905', '阜阳市', 442213, 'city'),
(442345, '341300', '33.636772385781', '116.98869241183', '宿州市', 442213, 'city'),
(442346, '341500', '31.755558355198', '116.50525268298', '六安市', 442213, 'city'),
(442347, '341600', '33.871210565302', '115.78792824512', '亳州市', 442213, 'city'),
(442348, '341700', '30.660019248161', '117.49447677159', '池州市', 442213, 'city'),
(442349, '341800', '30.951642354296', '118.75209631098', '宣城市', 442213, 'city'),
(442350, '350100', '26.047125496573', '119.33022110713', '福州市', 442214, 'city'),
(442351, '350200', '24.489230612469', '118.10388604566', '厦门市', 442214, 'city'),
(442352, '350300', '25.448450136734', '119.07773096396', '莆田市', 442214, 'city'),
(442353, '350400', '26.270835279362', '117.64219393404', '三明市', 442214, 'city'),
(442354, '350500', '24.901652383991', '118.60036234323', '泉州市', 442214, 'city'),
(442355, '350600', '24.517064779808', '117.67620467895', '漳州市', 442214, 'city'),
(442356, '350700', '26.643626474198', '118.18188294866', '南平市', 442214, 'city'),
(442357, '350800', '25.078685433515', '117.01799673877', '龙岩市', 442214, 'city'),
(442358, '350900', '26.656527419159', '119.54208214972', '宁德市', 442214, 'city'),
(442359, '360100', '28.689578000141', '115.89352754584', '南昌市', 442215, 'city'),
(442360, '360200', '29.303562768448', '117.18652262527', '景德镇市', 442215, 'city'),
(442361, '360300', '27.639544222952', '113.85991703301', '萍乡市', 442215, 'city'),
(442362, '360400', '29.719639526122', '115.99984802155', '九江市', 442215, 'city'),
(442363, '360500', '27.822321558629', '114.94711741679', '新余市', 442215, 'city'),
(442364, '360600', '28.241309597182', '117.03545018601', '鹰潭市', 442215, 'city'),
(442365, '360700', '25.845295536347', '114.93590907928', '赣州市', 442215, 'city'),
(442366, '360800', '27.113847650157', '114.99203871092', '吉安市', 442215, 'city'),
(442367, '360900', '27.811129895843', '114.40003867156', '宜春市', 442215, 'city'),
(442368, '361000', '27.95454517027', '116.36091886693', '抚州市', 442215, 'city'),
(442369, '361100', '28.457622553937', '117.95546387715', '上饶市', 442215, 'city'),
(442370, '370100', '36.682784727161', '117.02496706629', '济南市', 442216, 'city'),
(442371, '370200', '36.105214901274', '120.38442818368', '青岛市', 442216, 'city'),
(442372, '370300', '36.804684854212', '118.05913427787', '淄博市', 442216, 'city'),
(442373, '370400', '34.807883078386', '117.2793053833', '枣庄市', 442216, 'city'),
(442374, '370500', '37.487121155276', '118.58392633307', '东营市', 442216, 'city'),
(442375, '370600', '37.53656156286', '121.30955503009', '烟台市', 442216, 'city'),
(442376, '370700', '36.716114873051', '119.14263382297', '潍坊市', 442216, 'city'),
(442377, '370800', '35.402121664331', '116.60079762482', '济宁市', 442216, 'city'),
(442378, '370900', '36.188077758948', '117.08941491714', '泰安市', 442216, 'city'),
(442379, '371000', '37.528787081251', '122.09395836581', '威海市', 442216, 'city'),
(442380, '371100', '35.420225193144', '119.50717994299', '日照市', 442216, 'city'),
(442381, '371200', '36.233654133647', '117.68466691247', '莱芜市', 442216, 'city'),
(442382, '371300', '35.072409074391', '118.34076823661', '临沂市', 442216, 'city'),
(442383, '371400', '37.460825926305', '116.32816136356', '德州市', 442216, 'city'),
(442384, '371500', '36.455828514728', '115.98686913929', '聊城市', 442216, 'city'),
(442385, '371600', '37.405313941826', '117.96829241453', '滨州市', 442216, 'city'),
(442386, '371700', '35.262440496075', '115.46335977453', '菏泽市', 442216, 'city'),
(442387, '410100', '34.75661006414', '113.64964384986', '郑州市', 442217, 'city'),
(442388, '410200', '34.801854175837', '114.35164211776', '开封市', 442217, 'city'),
(442389, '410300', '34.657367817651', '112.44752476895', '洛阳市', 442217, 'city'),
(442390, '410400', '33.745301456524', '113.30084897798', '平顶山市', 442217, 'city'),
(442391, '410500', '36.110266722181', '114.35180650767', '安阳市', 442217, 'city'),
(442392, '410600', '35.755425874224', '114.29776983802', '鹤壁市', 442217, 'city'),
(442393, '410700', '35.307257557661', '113.91269016082', '新乡市', 442217, 'city'),
(442394, '410800', '35.234607554986', '113.21183588499', '焦作市', 442217, 'city'),
(442395, '410900', '35.753297888208', '115.02662744067', '濮阳市', 442217, 'city'),
(442396, '411000', '34.026739588655', '113.83531245979', '许昌市', 442217, 'city'),
(442397, '411100', '33.576278688483', '114.04606140023', '漯河市', 442217, 'city'),
(442398, '411200', '34.78331994105', '111.18126209327', '三门峡市', 442217, 'city'),
(442399, '411300', '33.011419569116', '112.54284190051', '南阳市', 442217, 'city'),
(442400, '411400', '34.438588640246', '115.64188568785', '商丘市', 442217, 'city'),
(442401, '411500', '32.128582307512', '114.08549099347', '信阳市', 442217, 'city'),
(442402, '411600', '33.623740818141', '114.6541019423', '周口市', 442217, 'city'),
(442403, '411700', '32.983158154093', '114.04915354746', '驻马店市', 442217, 'city'),
(442404, '419000', '34.157183767956', '113.48680405753', '省直辖县级行政区划', 442217, 'city'),
(442405, '420100', '30.581084126921', '114.31620010268', '武汉市', 442218, 'city'),
(442406, '420200', '30.216127127714', '115.05068316392', '黄石市', 442218, 'city'),
(442407, '420300', '32.636994339468', '110.80122891676', '十堰市', 442218, 'city'),
(442408, '420500', '30.732757818026', '111.31098109196', '宜昌市', 442218, 'city'),
(442409, '420600', '31.939712558944', '111.94954852739', '襄阳市', 442218, 'city'),
(442410, '420700', '30.384439322752', '114.89559404136', '鄂州市', 442218, 'city'),
(442411, '420800', '31.042611202949', '112.21733029897', '荆门市', 442218, 'city'),
(442412, '420900', '30.927954784201', '113.93573439207', '孝感市', 442218, 'city'),
(442413, '421000', '30.332590522986', '112.24186580719', '荆州市', 442218, 'city'),
(442414, '421100', '30.446108937901', '114.90661804658', '黄冈市', 442218, 'city'),
(442415, '421200', '29.880656757728', '114.30006059206', '咸宁市', 442218, 'city'),
(442416, '421300', '31.717857608189', '113.37935836429', '随州市', 442218, 'city'),
(442417, '422800', '30.285888316556', '109.49192330375', '恩施土家族苗族自治州', 442218, 'city'),
(442418, '429000', '31.20931625014', '112.41056219213', '省直辖县级行政区划', 442218, 'city'),
(442419, '430100', '28.213478230853', '112.97935278765', '长沙市', 442219, 'city'),
(442420, '430200', '27.827432927663', '113.13169534107', '株洲市', 442219, 'city'),
(442421, '430300', '27.835095052979', '112.93555563303', '湘潭市', 442219, 'city'),
(442422, '430400', '26.898164415358', '112.58381881072', '衡阳市', 442219, 'city'),
(442423, '430500', '27.236811244922', '111.46152540355', '邵阳市', 442219, 'city'),
(442424, '430600', '29.378007075474', '113.14619551912', '岳阳市', 442219, 'city'),
(442425, '430700', '29.012148855181', '111.65371813684', '常德市', 442219, 'city'),
(442426, '430800', '29.12488935322', '110.48162015697', '张家界市', 442219, 'city'),
(442427, '430900', '28.588087779887', '112.36654664523', '益阳市', 442219, 'city'),
(442428, '431000', '25.782263975739', '113.0377044678', '郴州市', 442219, 'city'),
(442429, '431100', '26.435971646759', '111.61464768616', '永州市', 442219, 'city'),
(442430, '431200', '27.557482901173', '109.98695879585', '怀化市', 442219, 'city'),
(442431, '431300', '27.741073302349', '111.99639635657', '娄底市', 442219, 'city'),
(442432, '433100', '28.317950793674', '109.74574580039', '湘西土家族苗族自治州', 442219, 'city'),
(442433, '440100', '23.120049102076', '113.30764967515', '广州市', 442220, 'city'),
(442434, '440200', '24.802960311892', '113.59446110744', '韶关市', 442220, 'city'),
(442435, '440300', '22.546053546205', '114.02597365732', '深圳市', 442220, 'city'),
(442436, '440400', '22.256914646126', '113.56244702619', '珠海市', 442220, 'city'),
(442437, '440500', '23.383908453269', '116.72865028834', '汕头市', 442220, 'city'),
(442438, '440600', '23.035094840514', '113.13402563539', '佛山市', 442220, 'city'),
(442439, '440700', '22.575116783451', '113.07812534115', '江门市', 442220, 'city'),
(442440, '440800', '21.257463103764', '110.36506726285', '湛江市', 442220, 'city'),
(442441, '440900', '21.668225718822', '110.93124533068', '茂名市', 442220, 'city'),
(442442, '441200', '23.078663282929', '112.47965336992', '肇庆市', 442220, 'city'),
(442443, '441300', '23.113539852408', '114.41065807997', '惠州市', 442220, 'city'),
(442444, '441400', '24.304570606031', '116.12640309837', '梅州市', 442220, 'city'),
(442445, '441500', '22.778730500164', '115.3729242894', '汕尾市', 442220, 'city'),
(442446, '441600', '23.757250850469', '114.71372147587', '河源市', 442220, 'city'),
(442447, '441700', '21.871517304519', '111.97700975587', '阳江市', 442220, 'city'),
(442448, '441800', '23.698468550422', '113.04077334891', '清远市', 442220, 'city'),
(442449, '445100', '23.661811676517', '116.63007599086', '潮州市', 442220, 'city'),
(442450, '445200', '23.547999466926', '116.37950085538', '揭阳市', 442220, 'city'),
(442451, '445300', '22.937975685537', '112.05094595865', '云浮市', 442220, 'city'),
(442452, '450100', '22.806492935603', '108.29723355587', '南宁市', 442221, 'city'),
(442453, '450200', '24.329053352467', '109.42240181015', '柳州市', 442221, 'city'),
(442454, '450300', '25.262901245955', '110.26092014748', '桂林市', 442221, 'city'),
(442455, '450400', '23.485394636734', '111.30547195007', '梧州市', 442221, 'city'),
(442456, '450500', '21.47271823501', '109.12262791919', '北海市', 442221, 'city'),
(442457, '450600', '21.617398470472', '108.35179115286', '防城港市', 442221, 'city'),
(442458, '450700', '21.973350465313', '108.63879805642', '钦州市', 442221, 'city'),
(442459, '450800', '23.103373164409', '109.61370755658', '贵港市', 442221, 'city'),
(442460, '450900', '22.643973608377', '110.15167631614', '玉林市', 442221, 'city'),
(442461, '451000', '23.90151236791', '106.63182140365', '百色市', 442221, 'city'),
(442462, '451100', '24.411053547113', '111.55259417884', '贺州市', 442221, 'city'),
(442463, '451200', '24.699520782873', '108.06994770937', '河池市', 442221, 'city'),
(442464, '451300', '23.741165926515', '109.23181650474', '来宾市', 442221, 'city'),
(442465, '451400', '22.415455296546', '107.35732203837', '崇左市', 442221, 'city'),
(442466, '460100', '20.022071276952', '110.33080184834', '海口市', 442222, 'city'),
(442467, '460200', '18.257775914897', '109.52277128136', '三亚市', 442222, 'city'),
(442468, '460300', '12.464712920653', '113.75535610385', '三沙市', 442222, 'city'),
(442469, '469000', '19.180500801261', '109.73375548794', '省直辖县级行政区划', 442222, 'city'),
(442470, '500100', '29.544606108886', '106.53063501341', '市辖区', 442223, 'city'),
(442471, '500200', '29.544606108886', '106.53063501341', '县', 442223, 'city'),
(442472, '510100', '30.67994284542', '104.0679234633', '成都市', 442224, 'city'),
(442473, '510300', '29.359156889476', '104.77607133936', '自贡市', 442224, 'city'),
(442474, '510400', '26.587571257109', '101.72242315249', '攀枝花市', 442224, 'city'),
(442475, '510500', '28.89592980386', '105.44397028921', '泸州市', 442224, 'city'),
(442476, '510600', '31.131139652701', '104.40239781824', '德阳市', 442224, 'city'),
(442477, '510700', '31.504701258061', '104.70551897529', '绵阳市', 442224, 'city'),
(442478, '510800', '32.441040158428', '105.81968694', '广元市', 442224, 'city'),
(442479, '510900', '30.55749135038', '105.56488779226', '遂宁市', 442224, 'city'),
(442480, '511000', '29.599461534775', '105.07305599171', '内江市', 442224, 'city'),
(442481, '511100', '29.600957611095', '103.76082423877', '乐山市', 442224, 'city'),
(442482, '511300', '30.800965168237', '106.10555398379', '南充市', 442224, 'city'),
(442483, '511400', '30.061115079945', '103.84142956287', '眉山市', 442224, 'city'),
(442484, '511500', '28.769674796266', '104.63301906153', '宜宾市', 442224, 'city'),
(442485, '511600', '30.463983887888', '106.63572033137', '广安市', 442224, 'city'),
(442486, '511700', '31.214198858945', '107.49497344659', '达州市', 442224, 'city'),
(442487, '511800', '29.999716337066', '103.00935646635', '雅安市', 442224, 'city'),
(442488, '511900', '31.86918915916', '106.75791584175', '巴中市', 442224, 'city'),
(442489, '512000', '30.132191433952', '104.63593030167', '资阳市', 442224, 'city'),
(442490, '513200', '31.905762858339', '102.22856468921', '阿坝藏族羌族自治州', 442224, 'city'),
(442491, '513300', '30.055144114356', '101.96923206306', '甘孜藏族自治州', 442224, 'city'),
(442492, '513400', '27.892392903666', '102.2595908032', '凉山彝族自治州', 442224, 'city'),
(442493, '520100', '26.629906741441', '106.70917709618', '贵阳市', 442225, 'city'),
(442494, '520200', '26.591866060319', '104.85208676007', '六盘水市', 442225, 'city'),
(442495, '520300', '27.699961377076', '106.93126031648', '遵义市', 442225, 'city'),
(442496, '520400', '26.228594577737', '105.92826996576', '安顺市', 442225, 'city'),
(442497, '520500', '27.408562131331', '105.33332337117', '毕节市', 442225, 'city'),
(442498, '520600', '27.674902690624', '109.16855802826', '铜仁市', 442225, 'city'),
(442499, '522300', '25.095148055927', '104.90055779825', '黔西南布依族苗族自治州', 442225, 'city'),
(442500, '522600', '26.583991766542', '107.98535257274', '黔东南苗族侗族自治州', 442225, 'city'),
(442501, '522700', '26.264535997442', '107.52320511006', '黔南布依族苗族自治州', 442225, 'city'),
(442502, '530100', '25.049153100453', '102.71460113878', '昆明市', 442226, 'city'),
(442503, '530300', '25.520758142871', '103.78253888803', '曲靖市', 442226, 'city'),
(442504, '530400', '24.370447134438', '102.54506789248', '玉溪市', 442226, 'city'),
(442505, '530500', '25.12048919619', '99.177995613278', '保山市', 442226, 'city'),
(442506, '530600', '27.340632963635', '103.72502065573', '昭通市', 442226, 'city'),
(442507, '530700', '26.875351089481', '100.22962839888', '丽江市', 442226, 'city'),
(442508, '530800', '22.788777780149', '100.98005773013', '普洱市', 442226, 'city'),
(442509, '530900', '23.887806103773', '100.09261291373', '临沧市', 442226, 'city'),
(442510, '532300', '25.066355674186', '101.52938223914', '楚雄彝族自治州', 442226, 'city'),
(442511, '532500', '23.367717516499', '103.38406475716', '红河哈尼族彝族自治州', 442226, 'city'),
(442512, '532600', '23.37408685041', '104.24629431757', '文山壮族苗族自治州', 442226, 'city'),
(442513, '532800', '22.009433002236', '100.80303827521', '西双版纳傣族自治州', 442226, 'city'),
(442514, '532900', '25.596899639421', '100.22367478928', '大理白族自治州', 442226, 'city'),
(442515, '533100', '24.441239663008', '98.589434287407', '德宏傣族景颇族自治州', 442226, 'city'),
(442516, '533300', '25.860676978165', '98.859932042482', '怒江傈僳族自治州', 442226, 'city'),
(442517, '533400', '27.831029461167', '99.713681598883', '迪庆藏族自治州', 442226, 'city'),
(442518, '540100', '29.662557062057', '91.111890895984', '拉萨市', 442227, 'city'),
(442519, '540200', '29.268160032655', '88.956062773518', '日喀则市', 442227, 'city'),
(442520, '540300', '30.510924801158', '96.362440472918', '昌都市', 442227, 'city'),
(442521, '540400', '29.128080197802', '95.466234246683', '林芝市', 442227, 'city'),
(442522, '540500', '28.354982378107', '92.22087273151', '山南市', 442227, 'city'),
(442523, '542400', '31.48067983012', '92.067018368859', '那曲地区', 442227, 'city'),
(442524, '542500', '30.404556588325', '81.10766868949', '阿里地区', 442227, 'city'),
(442525, '610100', '34.277799897831', '108.9530982792', '西安市', 442228, 'city'),
(442526, '610200', '34.908367696384', '108.9680670134', '铜川市', 442228, 'city'),
(442527, '610300', '34.364080809748', '107.17064545238', '宝鸡市', 442228, 'city'),
(442528, '610400', '34.345372995999', '108.7075092782', '咸阳市', 442228, 'city'),
(442529, '610500', '34.502357975829', '109.48393269658', '渭南市', 442228, 'city'),
(442530, '610600', '36.60332035226', '109.50050975697', '延安市', 442228, 'city'),
(442531, '610700', '33.081568978158', '107.04547762873', '汉中市', 442228, 'city'),
(442532, '610800', '38.279439240071', '109.74592574433', '榆林市', 442228, 'city'),
(442533, '610900', '32.704370449994', '109.03804456348', '安康市', 442228, 'city'),
(442534, '611000', '33.873907395085', '109.9342081538', '商洛市', 442228, 'city'),
(442535, '620100', '36.064225525043', '103.82330544073', '兰州市', 442229, 'city'),
(442536, '620300', '38.516071799532', '102.20812626259', '金昌市', 442229, 'city'),
(442537, '620400', '36.546681706163', '104.17124090374', '白银市', 442229, 'city'),
(442538, '620500', '34.584319418869', '105.73693162286', '天水市', 442229, 'city'),
(442539, '620600', '37.933172142906', '102.64014734337', '武威市', 442229, 'city'),
(442540, '620700', '38.939320296982', '100.45989186892', '张掖市', 442229, 'city'),
(442541, '620800', '35.550110190017', '106.68891115655', '平凉市', 442229, 'city'),
(442542, '620900', '39.741473768159', '98.508414506167', '酒泉市', 442229, 'city'),
(442543, '621000', '35.72680075453', '107.64422708673', '庆阳市', 442229, 'city'),
(442544, '621100', '35.586056241828', '104.62663760066', '定西市', 442229, 'city'),
(442545, '621200', '33.394479972938', '104.93457340575', '陇南市', 442229, 'city'),
(442546, '622900', '35.598514348802', '103.21524917832', '临夏回族自治州', 442229, 'city'),
(442547, '623000', '34.992211178379', '102.9174424865', '甘南藏族自治州', 442229, 'city'),
(442548, '630100', '36.640738611958', '101.7679209898', '西宁市', 442230, 'city'),
(442549, '630200', '36.312743354178', '102.37668874252', '海东市', 442230, 'city'),
(442550, '632200', '36.960654101084', '100.87980217448', '海北藏族自治州', 442230, 'city'),
(442551, '632300', '35.522851551728', '102.00760030834', '黄南藏族自治州', 442230, 'city'),
(442552, '632500', '36.284363803805', '100.6240660941', '海南藏族自治州', 442230, 'city'),
(442553, '632600', '34.48048458461', '100.22372276899', '果洛藏族自治州', 442230, 'city'),
(442554, '632700', '33.006239909722', '97.013316137414', '玉树藏族自治州', 442230, 'city'),
(442555, '632800', '37.37379907059', '97.342625415333', '海西蒙古族藏族自治州', 442230, 'city'),
(442556, '640100', '38.502621011876', '106.20647860784', '银川市', 442231, 'city'),
(442557, '640200', '39.020223283603', '106.37933720153', '石嘴山市', 442231, 'city'),
(442558, '640300', '37.993561002936', '106.20825419851', '吴忠市', 442231, 'city'),
(442559, '640400', '36.021523480709', '106.28526799598', '固原市', 442231, 'city'),
(442560, '640500', '37.521124191595', '105.19675419936', '中卫市', 442231, 'city'),
(442561, '650100', '43.840380347218', '87.564987741116', '乌鲁木齐市', 442232, 'city'),
(442562, '650200', '45.594331066706', '84.881180186144', '克拉玛依市', 442232, 'city'),
(442563, '650400', '42.678924820794', '89.266025488642', '吐鲁番市', 442232, 'city'),
(442564, '650500', '42.344467104552', '93.529373012389', '哈密市', 442232, 'city'),
(442565, '652300', '44.007057898533', '87.296038125667', '昌吉回族自治州', 442232, 'city'),
(442566, '652700', '44.913651374298', '82.052436267224', '博尔塔拉蒙古自治州', 442232, 'city'),
(442567, '652800', '41.771362202569', '86.121688362984', '巴音郭楞蒙古自治州', 442232, 'city'),
(442568, '652900', '41.171730901452', '80.269846179329', '阿克苏地区', 442232, 'city'),
(442569, '653000', '39.750345577845', '76.137564477462', '克孜勒苏柯尔克孜自治州', 442232, 'city'),
(442570, '653100', '39.470627188746', '75.992973267492', '喀什地区', 442232, 'city'),
(442571, '653200', '37.116774492678', '79.930238637213', '和田地区', 442232, 'city'),
(442572, '654000', '43.922248096341', '81.297853530366', '伊犁哈萨克自治州', 442232, 'city'),
(442573, '654200', '46.75868362968', '82.974880583744', '塔城地区', 442232, 'city'),
(442574, '654300', '47.839744486198', '88.137915487132', '阿勒泰地区', 442232, 'city'),
(442575, '659000', '42.127000957642', '85.614899338339', '自治区直辖县级行政区划', 442232, 'city'),
(442576, '110101', '39.938574012986', '116.42188470126', '东城区', 442236, 'county'),
(442577, '110102', '39.934280143709', '116.37319010402', '西城区', 442236, 'county'),
(442578, '110105', '39.958953166407', '116.52169489108', '朝阳区', 442236, 'county'),
(442579, '110106', '39.841937852205', '116.25837033547', '丰台区', 442236, 'county'),
(442580, '110107', '39.938866544646', '116.18455581037', '石景山区', 442236, 'county'),
(442581, '110108', '40.033162045078', '116.23967780102', '海淀区', 442236, 'county'),
(442582, '110109', '40.000893031476', '115.79579538125', '门头沟区', 442236, 'county'),
(442583, '110111', '39.726752620796', '115.8628363129', '房山区', 442236, 'county'),
(442584, '110112', '39.809814883851', '116.74007918068', '通州区', 442236, 'county'),
(442585, '110113', '40.154951470441', '116.72822904528', '顺义区', 442236, 'county'),
(442586, '110114', '40.221723549832', '116.21645635689', '昌平区', 442236, 'county'),
(442587, '110115', '39.652790118364', '116.42519459738', '大兴区', 442236, 'county'),
(442588, '110116', '40.638139340311', '116.59340835643', '怀柔区', 442236, 'county'),
(442589, '110117', '40.215925453896', '117.15043344819', '平谷区', 442236, 'county'),
(442590, '110118', '40.517334853846', '117.09666568438', '密云区', 442236, 'county'),
(442591, '110119', '40.535475747111', '116.1618831398', '延庆区', 442236, 'county'),
(442592, '120101', '39.124808844703', '117.20281365403', '和平区', 442237, 'county'),
(442593, '120102', '39.126625684666', '117.26169316527', '河东区', 442237, 'county'),
(442594, '120103', '39.084493739615', '117.23616545062', '河西区', 442237, 'county'),
(442595, '120104', '39.116987285522', '117.16272794945', '南开区', 442237, 'county'),
(442596, '120105', '39.173148933924', '117.22029676508', '河北区', 442237, 'county'),
(442597, '120106', '39.170621331225', '117.16221680792', '红桥区', 442237, 'county'),
(442598, '120110', '39.139604642775', '117.41478234325', '东丽区', 442237, 'county'),
(442599, '120111', '39.035064611485', '117.12620134665', '西青区', 442237, 'county'),
(442600, '120112', '38.969790532725', '117.39290995972', '津南区', 442237, 'county'),
(442601, '120113', '39.259130625979', '117.18060609828', '北辰区', 442237, 'county'),
(442602, '120114', '39.457042575494', '117.03457791373', '武清区', 442237, 'county'),
(442603, '120115', '39.615544004133', '117.41142059078', '宝坻区', 442237, 'county'),
(442604, '120116', '39.059176638035', '117.64628627057', '滨海新区', 442237, 'county'),
(442605, '120117', '39.390421570053', '117.6312358292', '宁河区', 442237, 'county'),
(442606, '120118', '38.837510804607', '116.98682530718', '静海区', 442237, 'county'),
(442607, '120119', '40.009456311951', '117.47034191571', '蓟州区', 442237, 'county'),
(442608, '130102', '38.076874795787', '114.59262155387', '长安区', 442238, 'county'),
(442609, '130104', '38.033364550068', '114.43813995532', '桥西区', 442238, 'county'),
(442610, '130105', '38.117218640478', '114.45350142869', '新华区', 442238, 'county'),
(442611, '130107', '38.08109756116', '114.05074376291', '井陉矿区', 442238, 'county'),
(442612, '130108', '38.014621045712', '114.58638255261', '裕华区', 442238, 'county'),
(442613, '130109', '38.089490113945', '114.82809608578', '藁城区', 442238, 'county'),
(442614, '130110', '38.089969323509', '114.35731900345', '鹿泉区', 442238, 'county'),
(442615, '130111', '37.91328595181', '114.64775310253', '栾城区', 442238, 'county'),
(442616, '130121', '38.000890815811', '114.07795206335', '井陉县', 442238, 'county'),
(442617, '130123', '38.227072535479', '114.57020132348', '正定县', 442238, 'county'),
(442618, '130125', '38.546695301387', '114.45743612437', '行唐县', 442238, 'county'),
(442619, '130126', '38.510935985414', '114.18781898137', '灵寿县', 442238, 'county'),
(442620, '130127', '37.622650870757', '114.6073846934', '高邑县', 442238, 'county'),
(442621, '130128', '38.194680827355', '115.23310242793', '深泽县', 442238, 'county'),
(442622, '130129', '37.628132452966', '114.28955340433', '赞皇县', 442238, 'county'),
(442623, '130130', '38.1832860202', '114.95113960113', '无极县', 442238, 'county'),
(442624, '130131', '38.408762191725', '113.87242852701', '平山县', 442238, 'county'),
(442625, '130132', '37.807352641009', '114.42836015628', '元氏县', 442238, 'county'),
(442626, '130133', '37.769612448365', '114.83493823756', '赵县', 442238, 'county'),
(442627, '130183', '37.991145102246', '115.09173828064', '晋州市', 442238, 'county'),
(442628, '130184', '38.377578025839', '114.76227076683', '新乐市', 442238, 'county'),
(442629, '130202', '39.612986996735', '118.20604028639', '路南区', 442239, 'county'),
(442630, '130203', '39.657845680029', '118.18506997308', '路北区', 442239, 'county'),
(442631, '130204', '39.723044780378', '118.46223153818', '古冶区', 442239, 'county'),
(442632, '130205', '39.692123420846', '118.25784790075', '开平区', 442239, 'county'),
(442633, '130207', '39.384662748593', '118.08584709899', '丰南区', 442239, 'county'),
(442634, '130208', '39.789909410339', '118.05949036617', '丰润区', 442239, 'county'),
(442635, '130209', '39.266037841072', '118.41596118319', '曹妃甸区', 442239, 'county'),
(442636, '130223', '39.785508848229', '118.5837772519', '滦县', 442239, 'county'),
(442637, '130224', '39.360738899901', '118.54938466456', '滦南县', 442239, 'county'),
(442638, '130225', '39.357228891896', '118.93994305703', '乐亭县', 442239, 'county'),
(442639, '130227', '40.238507660812', '118.37138905434', '迁西县', 442239, 'county'),
(442640, '130229', '39.818843355788', '117.7347526449', '玉田县', 442239, 'county'),
(442641, '130281', '40.137901064021', '117.95763912762', '遵化市', 442239, 'county'),
(442642, '130283', '40.04044251326', '118.68695461732', '迁安市', 442239, 'county'),
(442643, '130302', '39.988779577117', '119.57761724583', '海港区', 442240, 'county'),
(442644, '130303', '40.032899628101', '119.7136155797', '山海关区', 442240, 'county'),
(442645, '130304', '39.854292584187', '119.47932079421', '北戴河区', 442240, 'county'),
(442646, '130306', '39.910857115367', '119.34003537992', '抚宁区', 442240, 'county'),
(442647, '130321', '40.353650308648', '119.13758245072', '青龙满族自治县', 442240, 'county'),
(442648, '130322', '39.638021164728', '119.09462149738', '昌黎县', 442240, 'county'),
(442649, '130324', '39.920978455186', '118.98556414609', '卢龙县', 442240, 'county'),
(442650, '130402', '36.536153078937', '114.46928986668', '邯山区', 442241, 'county'),
(442651, '130403', '36.637214815152', '114.51106763052', '丛台区', 442241, 'county'),
(442652, '130404', '36.610368592227', '114.44809470749', '复兴区', 442241, 'county'),
(442653, '130406', '36.474684997423', '114.19042164993', '峰峰矿区', 442241, 'county'),
(442654, '130421', '36.620347221062', '114.49448604232', '邯郸县', 442241, 'county'),
(442655, '130423', '36.266141946474', '114.58694416944', '临漳县', 442241, 'county'),
(442656, '130424', '36.428150647186', '114.70477468285', '成安县', 442241, 'county'),
(442657, '130425', '36.309543770756', '115.24863464404', '大名县', 442241, 'county'),
(442658, '130426', '36.598104535573', '113.74291352234', '涉县', 442241, 'county'),
(442659, '130427', '36.406730602547', '114.25510074085', '磁县', 442241, 'county'),
(442660, '130428', '36.577260887621', '114.83690510574', '肥乡县', 442241, 'county'),
(442661, '130429', '36.770200181653', '114.64160198718', '永年县', 442241, 'county'),
(442662, '130430', '36.797269787143', '115.20670231619', '邱县', 442241, 'county'),
(442663, '130431', '36.873677489817', '114.86956581384', '鸡泽县', 442241, 'county'),
(442664, '130432', '36.51192631393', '115.02087402114', '广平县', 442241, 'county'),
(442665, '130433', '36.618537005781', '115.29915662582', '馆陶县', 442241, 'county'),
(442666, '130434', '36.250567761095', '114.93600011898', '魏县', 442241, 'county'),
(442667, '130435', '36.752651265719', '115.03853247193', '曲周县', 442241, 'county'),
(442668, '130481', '36.748995476597', '114.05833396936', '武安市', 442241, 'county'),
(442669, '130502', '37.059046252073', '114.52129744384', '桥东区', 442242, 'county'),
(442670, '130503', '37.053579664221', '114.46840126286', '桥西区', 442242, 'county'),
(442671, '130521', '37.152421699275', '114.16774440241', '邢台县', 442242, 'county'),
(442672, '130522', '37.463137591617', '114.38466503755', '临城县', 442242, 'county'),
(442673, '130523', '37.314224311167', '114.30459575437', '内丘县', 442242, 'county'),
(442674, '130524', '37.517418414338', '114.70742434434', '柏乡县', 442242, 'county'),
(442675, '130525', '37.36468808358', '114.79291584707', '隆尧县', 442242, 'county'),
(442676, '130526', '37.174630101755', '114.7699671597', '任县', 442242, 'county'),
(442677, '130527', '37.016963874379', '114.75308935883', '南和县', 442242, 'county'),
(442678, '130528', '37.612086758173', '115.02167843721', '宁晋县', 442242, 'county'),
(442679, '130529', '37.278679297084', '115.05888578855', '巨鹿县', 442242, 'county'),
(442680, '130530', '37.499362567334', '115.25720361984', '新河县', 442242, 'county'),
(442681, '130531', '37.083548692406', '115.19817308929', '广宗县', 442242, 'county'),
(442682, '130532', '37.056110207564', '115.00481854709', '平乡县', 442242, 'county'),
(442683, '130533', '37.078394650565', '115.38772530687', '威县', 442242, 'county'),
(442684, '130534', '37.040529913617', '115.69158951605', '清河县', 442242, 'county'),
(442685, '130535', '36.858027353556', '115.52844117588', '临西县', 442242, 'county'),
(442686, '130581', '37.286427413275', '115.47940958601', '南宫市', 442242, 'county'),
(442687, '130582', '36.938635459346', '114.28309250179', '沙河市', 442242, 'county'),
(442688, '130602', '38.896799171923', '115.4337718341', '竞秀区', 442243, 'county'),
(442689, '130606', '38.878869183082', '115.52517138526', '莲池区', 442243, 'county'),
(442690, '130607', '38.936509575446', '115.22854614305', '满城区', 442243, 'county'),
(442691, '130608', '38.746793898598', '115.50474549359', '清苑区', 442243, 'county'),
(442692, '130609', '39.030072064834', '115.56341421452', '徐水区', 442243, 'county'),
(442693, '130623', '39.616117563205', '115.44462792481', '涞水县', 442243, 'county'),
(442694, '130624', '38.894806411217', '114.16421062387', '阜平县', 442243, 'county'),
(442695, '130626', '39.211518314259', '115.75504588838', '定兴县', 442243, 'county'),
(442696, '130627', '38.904521131249', '114.80609127315', '唐县', 442243, 'county'),
(442697, '130628', '38.673020900262', '115.83844188387', '高阳县', 442243, 'county'),
(442698, '130629', '39.057813549536', '115.90877891487', '容城县', 442243, 'county'),
(442699, '130630', '39.366936787031', '114.73045121001', '涞源县', 442243, 'county'),
(442700, '130631', '38.679014979104', '115.17834559654', '望都县', 442243, 'county'),
(442701, '130632', '38.8782552166', '115.88673101005', '安新县', 442243, 'county'),
(442702, '130633', '39.317566051144', '115.25402170203', '易县', 442243, 'county'),
(442703, '130634', '38.706612214921', '114.66066397519', '曲阳县', 442243, 'county'),
(442704, '130635', '38.528232136022', '115.66928195753', '蠡县', 442243, 'county'),
(442705, '130636', '38.927951375985', '115.07398905469', '顺平县', 442243, 'county'),
(442706, '130637', '38.459123140672', '115.48778569396', '博野县', 442243, 'county'),
(442707, '130638', '39.042786858077', '116.18329894846', '雄县', 442243, 'county'),
(442708, '130681', '39.482481810572', '115.99905364071', '涿州市', 442243, 'county'),
(442709, '130683', '38.393739990352', '115.33482671534', '安国市', 442243, 'county'),
(442710, '130684', '39.265087764832', '116.04093362477', '高碑店市', 442243, 'county'),
(442711, '130702', '40.782910350247', '114.91516641164', '桥东区', 442244, 'county'),
(442712, '130703', '40.83764647974', '114.8616234507', '桥西区', 442244, 'county'),
(442713, '130705', '40.632394360149', '115.25847218771', '宣化区', 442244, 'county'),
(442714, '130706', '40.568836928653', '115.35049833098', '下花园区', 442244, 'county'),
(442715, '130708', '40.854322579125', '114.60159442219', '万全区', 442244, 'county'),
(442716, '130709', '41.041738952718', '115.18918281511', '崇礼区', 442244, 'county'),
(442717, '130722', '41.293640752346', '114.77289736584', '张北县', 442244, 'county'),
(442718, '130723', '41.784595269099', '114.60653573475', '康保县', 442244, 'county'),
(442719, '130724', '41.580403842568', '115.63609164922', '沽源县', 442244, 'county'),
(442720, '130725', '41.132634994489', '114.15252831523', '尚义县', 442244, 'county'),
(442721, '130726', '39.879353147831', '114.71253718704', '蔚县', 442244, 'county'),
(442722, '130727', '40.138642120211', '114.39439590667', '阳原县', 442244, 'county'),
(442723, '130728', '40.559533575131', '114.50260736695', '怀安县', 442244, 'county'),
(442724, '130730', '40.34798364385', '115.63406061974', '怀来县', 442244, 'county'),
(442725, '130731', '40.101875913481', '115.22392517513', '涿鹿县', 442244, 'county'),
(442726, '130732', '40.956026259537', '115.89222267195', '赤城县', 442244, 'county'),
(442727, '130802', '40.971406352197', '117.94835524238', '双桥区', 442245, 'county'),
(442728, '130803', '41.051453160703', '117.80933581725', '双滦区', 442245, 'county'),
(442729, '130804', '40.531760281234', '117.67942626427', '鹰手营子矿区', 442245, 'county'),
(442730, '130821', '40.9732421823', '118.12571829805', '承德县', 442245, 'county'),
(442731, '130822', '40.458141686295', '117.72613599005', '兴隆县', 442245, 'county'),
(442732, '130823', '41.075303768703', '118.73932350858', '平泉县', 442245, 'county'),
(442733, '130824', '40.924820741761', '117.36956340989', '滦平县', 442245, 'county'),
(442734, '130825', '41.517994972231', '117.56992967905', '隆化县', 442245, 'county'),
(442735, '130826', '41.425684335184', '116.62379481268', '丰宁满族自治县', 442245, 'county'),
(442736, '130827', '40.578090378096', '118.63588822017', '宽城满族自治县', 442245, 'county'),
(442737, '130828', '42.108024565862', '117.54702150524', '围场满族蒙古族自治县', 442245, 'county'),
(442738, '130902', '38.308375333084', '116.89305880724', '新华区', 442246, 'county'),
(442739, '130903', '38.314446124596', '116.84485357764', '运河区', 442246, 'county'),
(442740, '130921', '38.302138696207', '116.86271383128', '沧县', 442246, 'county'),
(442741, '130922', '38.565778328556', '116.85123400252', '青县', 442246, 'county'),
(442742, '130923', '37.887451603688', '116.67783233648', '东光县', 442246, 'county'),
(442743, '130924', '38.142470216907', '117.56726425888', '海兴县', 442246, 'county'),
(442744, '130925', '37.960369724102', '117.26693989632', '盐山县', 442246, 'county'),
(442745, '130926', '38.422207122829', '115.88581610558', '肃宁县', 442246, 'county'),
(442746, '130927', '38.023185702825', '116.8658497774', '南皮县', 442246, 'county'),
(442747, '130928', '37.661863472094', '116.5080334073', '吴桥县', 442246, 'county'),
(442748, '130929', '38.242725840471', '116.17550530237', '献县', 442246, 'county'),
(442749, '130930', '38.091264713342', '117.15953838544', '孟村回族自治县', 442246, 'county'),
(442750, '130981', '38.090278710884', '116.38923597745', '泊头市', 442246, 'county'),
(442751, '130982', '38.74110464111', '116.16321405193', '任丘市', 442246, 'county'),
(442752, '130983', '38.401521845487', '117.40021701974', '黄骅市', 442246, 'county'),
(442753, '130984', '38.483721432479', '116.27159283893', '河间市', 442246, 'county'),
(442754, '131002', '39.345312180639', '116.79612310881', '安次区', 442247, 'county'),
(442755, '131003', '39.533685537455', '116.69423648939', '广阳区', 442247, 'county'),
(442756, '131022', '39.351105940994', '116.28967015726', '固安县', 442247, 'county'),
(442757, '131023', '39.302836430211', '116.5605569701', '永清县', 442247, 'county'),
(442758, '131024', '39.743100032865', '117.05130555355', '香河县', 442247, 'county'),
(442759, '131025', '38.668802703656', '116.58863867606', '大城县', 442247, 'county'),
(442760, '131026', '38.911390482572', '116.49481687118', '文安县', 442247, 'county'),
(442761, '131028', '39.89531635509', '116.95507644864', '大厂回族自治县', 442247, 'county'),
(442762, '131081', '39.109320079055', '116.57430598976', '霸州市', 442247, 'county'),
(442763, '131082', '39.96742764877', '117.02128418409', '三河市', 442247, 'county'),
(442764, '131102', '37.72421788608', '115.66665700012', '桃城区', 442248, 'county'),
(442765, '131103', '37.53643502058', '115.44750567041', '冀州区', 442248, 'county'),
(442766, '131121', '37.461024106054', '115.75767748261', '枣强县', 442248, 'county'),
(442767, '131122', '37.827678592246', '115.94450660708', '武邑县', 442248, 'county'),
(442768, '131123', '38.050513034027', '115.920118282', '武强县', 442248, 'county'),
(442769, '131124', '38.223059241042', '115.74000746168', '饶阳县', 442248, 'county'),
(442770, '131125', '38.243195869487', '115.49041582246', '安平县', 442248, 'county'),
(442771, '131126', '37.356997906367', '115.97805666289', '故城县', 442248, 'county'),
(442772, '131127', '37.668477471141', '116.20013356506', '景县', 442248, 'county'),
(442773, '131128', '37.912309213617', '116.32842518537', '阜城县', 442248, 'county'),
(442774, '131182', '37.957012862702', '115.58669880842', '深州市', 442248, 'county'),
(442775, '139001', '38.465839158048', '115.05740695232', '定州市', 442249, 'county'),
(442776, '139002', '37.924121876409', '115.29874950521', '辛集市', 442249, 'county'),
(442777, '140105', '37.753527970896', '112.57740860671', '小店区', 442250, 'county'),
(442778, '140106', '37.865737302061', '112.66320298122', '迎泽区', 442250, 'county'),
(442779, '140107', '37.915556056965', '112.62983632135', '杏花岭区', 442250, 'county'),
(442780, '140108', '37.972757839535', '112.48843997984', '尖草坪区', 442250, 'county'),
(442781, '140109', '37.894693447581', '112.40285697662', '万柏林区', 442250, 'county'),
(442782, '140110', '37.748674917003', '112.48158725626', '晋源区', 442250, 'county'),
(442783, '140121', '37.59324244737', '112.38708511797', '清徐县', 442250, 'county'),
(442784, '140122', '38.158246373698', '112.67265853687', '阳曲县', 442250, 'county'),
(442785, '140123', '38.034584043133', '111.797820928', '娄烦县', 442250, 'county'),
(442786, '140181', '37.90517928255', '112.1077390237', '古交市', 442250, 'county'),
(442787, '140202', '40.102542866559', '113.29696587275', '城区', 442251, 'county'),
(442788, '140203', '40.036495634995', '113.0470017583', '矿区', 442251, 'county'),
(442789, '140211', '40.051891387407', '113.22645661564', '南郊区', 442251, 'county'),
(442790, '140212', '40.267127127574', '113.23689411719', '新荣区', 442251, 'county'),
(442791, '140221', '40.222311526135', '113.82318140606', '阳高县', 442251, 'county'),
(442792, '140222', '40.403528534338', '114.16812988719', '天镇县', 442251, 'county'),
(442793, '140223', '39.76899447952', '114.16170176527', '广灵县', 442251, 'county'),
(442794, '140224', '39.377267777348', '114.21309517425', '灵丘县', 442251, 'county'),
(442795, '140225', '39.634162361299', '113.71075899599', '浑源县', 442251, 'county'),
(442796, '140226', '40.000737963069', '112.77785639076', '左云县', 442251, 'county'),
(442797, '140227', '40.001627488893', '113.58386582098', '大同县', 442251, 'county'),
(442798, '140302', '37.85786536147', '113.61283811719', '城区', 442252, 'county'),
(442799, '140303', '37.890804244519', '113.54077065934', '矿区', 442252, 'county'),
(442800, '140311', '37.911503911114', '113.56808615363', '郊区', 442252, 'county'),
(442801, '140321', '37.8492714173', '113.76897794042', '平定县', 442252, 'county'),
(442802, '140322', '38.229385734147', '113.36096660369', '盂县', 442252, 'county'),
(442803, '140402', '36.184511192113', '113.12316935827', '城区', 442253, 'county'),
(442804, '140411', '36.270339558413', '113.11069620661', '郊区', 442253, 'county'),
(442805, '140421', '36.024679976201', '113.08619419794', '长治县', 442253, 'county'),
(442806, '140423', '36.580200785754', '112.98897348398', '襄垣县', 442253, 'county'),
(442807, '140424', '36.342609751076', '112.75036278967', '屯留县', 442253, 'county'),
(442808, '140425', '36.221794153091', '113.53368897635', '平顺县', 442253, 'county'),
(442809, '140426', '36.619367610278', '113.39685159379', '黎城县', 442253, 'county'),
(442810, '140427', '35.99265193372', '113.37199790438', '壶关县', 442253, 'county'),
(442811, '140428', '36.110999402019', '112.80225403294', '长子县', 442253, 'county'),
(442812, '140429', '36.888322821209', '112.96751985958', '武乡县', 442253, 'county'),
(442813, '140430', '36.70738347605', '112.65221013617', '沁县', 442253, 'county'),
(442814, '140431', '36.701566639488', '112.29009399197', '沁源县', 442253, 'county'),
(442815, '140481', '36.374406273238', '113.25438708828', '潞城市', 442253, 'county'),
(442816, '140502', '35.513593270468', '112.84269710529', '城区', 442254, 'county'),
(442817, '140521', '35.751489118151', '112.37742990987', '沁水县', 442254, 'county'),
(442818, '140522', '35.426540841161', '112.36152699508', '阳城县', 442254, 'county'),
(442819, '140524', '35.690743897919', '113.34338659863', '陵川县', 442254, 'county'),
(442820, '140525', '35.475851325496', '112.87098535033', '泽州县', 442254, 'county'),
(442821, '140581', '35.809742457991', '112.93511535362', '高平市', 442254, 'county'),
(442822, '140602', '39.243272437238', '112.5562001526', '朔城区', 442255, 'county'),
(442823, '140603', '39.640007394398', '112.30434987236', '平鲁区', 442255, 'county');
INSERT INTO `region` (`id`, `code`, `lat`, `lng`, `name`, `parent_id`, `by_type`) VALUES
(442824, '140621', '39.521049673137', '112.78680490549', '山阴县', 442255, 'county'),
(442825, '140622', '39.509316043686', '113.26059286958', '应县', 442255, 'county'),
(442826, '140623', '40.008135706467', '112.42167745341', '右玉县', 442255, 'county'),
(442827, '140624', '39.793570836032', '113.11230462343', '怀仁县', 442255, 'county'),
(442828, '140702', '37.650824689054', '112.84373652716', '榆次区', 442256, 'county'),
(442829, '140721', '37.140049981591', '112.95418082333', '榆社县', 442256, 'county'),
(442830, '140722', '37.03279458538', '113.47453786444', '左权县', 442256, 'county'),
(442831, '140723', '37.348373699108', '113.47493445138', '和顺县', 442256, 'county'),
(442832, '140724', '37.563418385072', '113.76210572071', '昔阳县', 442256, 'county'),
(442833, '140725', '37.825118914407', '113.14161086395', '寿阳县', 442256, 'county'),
(442834, '140726', '37.407696414459', '112.73643253249', '太谷县', 442256, 'county'),
(442835, '140727', '37.292198086629', '112.46906595172', '祁县', 442256, 'county'),
(442836, '140728', '37.148089778462', '112.26549326017', '平遥县', 442256, 'county'),
(442837, '140729', '36.834487193362', '111.73550355035', '灵石县', 442256, 'county'),
(442838, '140781', '37.02547627594', '111.99518801957', '介休市', 442256, 'county'),
(442839, '140802', '35.063676878932', '110.96193094165', '盐湖区', 442257, 'county'),
(442840, '140821', '35.149379501121', '110.62589540589', '临猗县', 442257, 'county'),
(442841, '140822', '35.388134013652', '110.71553950499', '万荣县', 442257, 'county'),
(442842, '140823', '35.373753157', '111.31928675078', '闻喜县', 442257, 'county'),
(442843, '140824', '35.59826692411', '110.96512183757', '稷山县', 442257, 'county'),
(442844, '140825', '35.631582540507', '111.17287529232', '新绛县', 442257, 'county'),
(442845, '140826', '35.498578532808', '111.64482039841', '绛县', 442257, 'county'),
(442846, '140827', '35.221584155393', '111.82478688142', '垣曲县', 442257, 'county'),
(442847, '140828', '35.126679510784', '111.35838628885', '夏县', 442257, 'county'),
(442848, '140829', '34.888645774448', '111.25110932094', '平陆县', 442257, 'county'),
(442849, '140830', '34.709534760447', '110.61649553806', '芮城县', 442257, 'county'),
(442850, '140881', '34.894671510755', '110.48894872131', '永济市', 442257, 'county'),
(442851, '140882', '35.631891049091', '110.70853926353', '河津市', 442257, 'county'),
(442852, '140902', '38.437831964453', '112.60520013418', '忻府区', 442258, 'county'),
(442853, '140921', '38.516749763878', '113.03558876456', '定襄县', 442258, 'county'),
(442854, '140922', '38.778174001492', '113.44210404535', '五台县', 442258, 'county'),
(442855, '140923', '39.093197224067', '113.05058168825', '代县', 442258, 'county'),
(442856, '140924', '39.204756994709', '113.596213567', '繁峙县', 442258, 'county'),
(442857, '140925', '38.821889666345', '112.19389583349', '宁武县', 442258, 'county'),
(442858, '140926', '38.400067154102', '112.06499425223', '静乐县', 442258, 'county'),
(442859, '140927', '39.173053445654', '112.00991986754', '神池县', 442258, 'county'),
(442860, '140928', '39.008730857984', '111.74475714392', '五寨县', 442258, 'county'),
(442861, '140929', '38.73795692606', '111.54356839151', '岢岚县', 442258, 'county'),
(442862, '140930', '39.206439528631', '111.3598292601', '河曲县', 442258, 'county'),
(442863, '140931', '38.887135247044', '111.14283481361', '保德县', 442258, 'county'),
(442864, '140932', '39.464649232881', '111.67190327635', '偏关县', 442258, 'county'),
(442865, '140981', '38.838876172747', '112.68212831914', '原平市', 442258, 'county'),
(442866, '141002', '36.125936912419', '111.47466486211', '尧都区', 442259, 'county'),
(442867, '141021', '35.704200978944', '111.52704105623', '曲沃县', 442259, 'county'),
(442868, '141022', '35.695397582179', '111.83920947478', '翼城县', 442259, 'county'),
(442869, '141023', '35.874204029953', '111.38595309536', '襄汾县', 442259, 'county'),
(442870, '141024', '36.325514180326', '111.65937789135', '洪洞县', 442259, 'county'),
(442871, '141025', '36.303822531038', '112.01124254119', '古县', 442259, 'county'),
(442872, '141026', '36.164295531066', '112.30790366347', '安泽县', 442259, 'county'),
(442873, '141027', '35.945830766006', '111.92840887398', '浮山县', 442259, 'county'),
(442874, '141028', '36.158677317484', '110.7281619704', '吉县', 442259, 'county'),
(442875, '141029', '35.925119179378', '110.94412841404', '乡宁县', 442259, 'county'),
(442876, '141030', '36.432636300532', '110.71080544253', '大宁县', 442259, 'county'),
(442877, '141031', '36.711950508392', '111.00996531617', '隰县', 442259, 'county'),
(442878, '141032', '36.737137367462', '110.61789818355', '永和县', 442259, 'county'),
(442879, '141033', '36.424599698879', '111.16235867375', '蒲县', 442259, 'county'),
(442880, '141034', '36.642781389504', '111.48314834543', '汾西县', 442259, 'county'),
(442881, '141081', '35.62178548788', '111.37150924676', '侯马市', 442259, 'county'),
(442882, '141082', '36.599677829115', '111.8308356262', '霍州市', 442259, 'county'),
(442883, '141102', '37.552339254903', '111.31314199347', '离石区', 442260, 'county'),
(442884, '141121', '37.459705909704', '111.96499464978', '文水县', 442260, 'county'),
(442885, '141122', '37.68704558482', '111.81992715422', '交城县', 442260, 'county'),
(442886, '141123', '38.392262583946', '111.0693110824', '兴县', 442260, 'county'),
(442887, '141124', '37.962867394138', '110.90114744504', '临县', 442260, 'county'),
(442888, '141125', '37.403754146298', '110.87692675415', '柳林县', 442260, 'county'),
(442889, '141126', '37.035145108626', '110.75347868183', '石楼县', 442260, 'county'),
(442890, '141127', '38.343813372012', '111.60664055968', '岚县', 442260, 'county'),
(442891, '141128', '37.886687741974', '111.33797892211', '方山县', 442260, 'county'),
(442892, '141129', '37.266317510804', '111.18590350772', '中阳县', 442260, 'county'),
(442893, '141130', '36.957718536996', '111.31592365888', '交口县', 442260, 'county'),
(442894, '141181', '37.118132778217', '111.63764576631', '孝义市', 442260, 'county'),
(442895, '141182', '37.316764309106', '111.74599591288', '汾阳市', 442260, 'county'),
(442896, '150102', '40.929360778776', '111.79132678714', '新城区', 442261, 'county'),
(442897, '150103', '40.838894763788', '111.5968855951', '回民区', 442261, 'county'),
(442898, '150104', '40.747386672042', '111.65855345988', '玉泉区', 442261, 'county'),
(442899, '150105', '40.788864152356', '111.87633478501', '赛罕区', 442261, 'county'),
(442900, '150121', '40.689987016139', '111.23470409261', '土默特左旗', 442261, 'county'),
(442901, '150122', '40.361083978494', '111.31970020313', '托克托县', 442261, 'county'),
(442902, '150123', '40.333868442059', '111.90169267122', '和林格尔县', 442261, 'county'),
(442903, '150124', '39.889117744685', '111.70623630691', '清水河县', 442261, 'county'),
(442904, '150125', '41.1162043874', '111.17957193814', '武川县', 442261, 'county'),
(442905, '150202', '40.589124487996', '110.07014136051', '东河区', 442262, 'county'),
(442906, '150203', '40.658057498224', '109.80683355282', '昆都仑区', 442262, 'county'),
(442907, '150204', '40.658777959476', '109.90367483934', '青山区', 442262, 'county'),
(442908, '150205', '40.716464297272', '110.29921474522', '石拐区', 442262, 'county'),
(442909, '150206', '41.789992502326', '109.98916893149', '白云鄂博矿区', 442262, 'county'),
(442910, '150207', '40.627202278275', '109.9491974592', '九原区', 442262, 'county'),
(442911, '150221', '40.527995764374', '110.69325794788', '土默特右旗', 442262, 'county'),
(442912, '150222', '41.104725412274', '110.16759209358', '固阳县', 442262, 'county'),
(442913, '150223', '41.943507148267', '110.28618869999', '达尔罕茂明安联合旗', 442262, 'county'),
(442914, '150302', '39.734833651275', '106.86148184332', '海勃湾区', 442263, 'county'),
(442915, '150303', '39.296209479392', '106.92539717866', '海南区', 442263, 'county'),
(442916, '150304', '39.535877701433', '106.72585891133', '乌达区', 442263, 'county'),
(442917, '150402', '42.286232134079', '118.99810293421', '红山区', 442264, 'county'),
(442918, '150403', '42.184130648802', '119.2681694129', '元宝山区', 442264, 'county'),
(442919, '150404', '42.268753015289', '118.75710571166', '松山区', 442264, 'county'),
(442920, '150421', '44.195956597411', '120.05324069384', '阿鲁科尔沁旗', 442264, 'county'),
(442921, '150422', '44.203430813088', '119.28076636509', '巴林左旗', 442264, 'county'),
(442922, '150423', '43.684786631454', '118.9460897431', '巴林右旗', 442264, 'county'),
(442923, '150424', '43.771462211479', '118.1102161479', '林西县', 442264, 'county'),
(442924, '150425', '43.218237176681', '117.35857031121', '克什克腾旗', 442264, 'county'),
(442925, '150426', '42.973979919258', '119.25464294075', '翁牛特旗', 442264, 'county'),
(442926, '150428', '41.908351449935', '118.66705601357', '喀喇沁旗', 442264, 'county'),
(442927, '150429', '41.571040867139', '118.90549936909', '宁城县', 442264, 'county'),
(442928, '150430', '42.430592238203', '120.15771329609', '敖汉旗', 442264, 'county'),
(442929, '150502', '43.658290149837', '122.29129415356', '科尔沁区', 442265, 'county'),
(442930, '150521', '44.0575792852', '122.49918004442', '科尔沁左翼中旗', 442265, 'county'),
(442931, '150522', '43.196082751665', '122.69734535162', '科尔沁左翼后旗', 442265, 'county'),
(442932, '150523', '43.734941954391', '121.32409399005', '开鲁县', 442265, 'county'),
(442933, '150524', '42.810038215314', '121.5730378859', '库伦旗', 442265, 'county'),
(442934, '150525', '42.972383010739', '120.94078899637', '奈曼旗', 442265, 'county'),
(442935, '150526', '44.82245130193', '120.59602806799', '扎鲁特旗', 442265, 'county'),
(442936, '150581', '45.52810605633', '119.57974844022', '霍林郭勒市', 442265, 'county'),
(442937, '150602', '39.805585913146', '109.76441928582', '东胜区', 442266, 'county'),
(442938, '150603', '39.640791926893', '109.84087569351', '康巴什区', 442266, 'county'),
(442939, '150621', '40.220264473893', '109.86619090676', '达拉特旗', 442266, 'county'),
(442940, '150622', '39.79472489563', '110.88623942079', '准格尔旗', 442266, 'county'),
(442941, '150623', '38.275938287288', '107.59700999652', '鄂托克前旗', 442266, 'county'),
(442942, '150624', '39.286296593278', '107.75202023325', '鄂托克旗', 442266, 'county'),
(442943, '150625', '40.212873152738', '108.21282820432', '杭锦旗', 442266, 'county'),
(442944, '150626', '38.640475147234', '108.88966323666', '乌审旗', 442266, 'county'),
(442945, '150627', '39.420695918404', '109.70418618841', '伊金霍洛旗', 442266, 'county'),
(442946, '150702', '49.279245456202', '120.04288208342', '海拉尔区', 442267, 'county'),
(442947, '150703', '49.461481568108', '117.72318498536', '扎赉诺尔区', 442267, 'county'),
(442948, '150721', '48.639988741071', '123.17195423134', '阿荣旗', 442267, 'county'),
(442949, '150722', '49.104886651718', '124.47443404901', '莫力达瓦达斡尔族自治旗', 442267, 'county'),
(442950, '150723', '50.348754571528', '123.81727783782', '鄂伦春自治旗', 442267, 'county'),
(442951, '150724', '48.499136514599', '120.06748322167', '鄂温克族自治旗', 442267, 'county'),
(442952, '150725', '49.605281276761', '119.53520765754', '陈巴尔虎旗', 442267, 'county'),
(442953, '150726', '48.43639187877', '118.62152477909', '新巴尔虎左旗', 442267, 'county'),
(442954, '150727', '48.644978915379', '116.8021843422', '新巴尔虎右旗', 442267, 'county'),
(442955, '150781', '49.500031717154', '117.60368677619', '满洲里市', 442267, 'county'),
(442956, '150782', '49.329995939597', '121.51266780552', '牙克石市', 442267, 'county'),
(442957, '150783', '47.7434033831', '121.92920216562', '扎兰屯市', 442267, 'county'),
(442958, '150784', '51.660818880977', '120.65276364824', '额尔古纳市', 442267, 'county'),
(442959, '150785', '51.37592516127', '121.79771324217', '根河市', 442267, 'county'),
(442960, '150802', '40.932018223224', '107.44183964667', '临河区', 442268, 'county'),
(442961, '150821', '41.045426664817', '108.07228406065', '五原县', 442268, 'county'),
(442962, '150822', '40.55518106886', '106.7000000567', '磴口县', 442268, 'county'),
(442963, '150823', '40.905993260887', '109.10529705882', '乌拉特前旗', 442268, 'county'),
(442964, '150824', '41.831044527428', '108.46454180074', '乌拉特中旗', 442268, 'county'),
(442965, '150825', '41.53194458396', '106.41380804671', '乌拉特后旗', 442268, 'county'),
(442966, '150826', '40.890870780779', '107.03345374933', '杭锦后旗', 442268, 'county'),
(442967, '150902', '41.027765971469', '113.11283222874', '集宁区', 442269, 'county'),
(442968, '150921', '40.958869485808', '112.44337671416', '卓资县', 442269, 'county'),
(442969, '150922', '41.979126377538', '114.16573790656', '化德县', 442269, 'county'),
(442970, '150923', '41.726516851564', '113.62215528367', '商都县', 442269, 'county'),
(442971, '150924', '40.952666521778', '113.77372051762', '兴和县', 442269, 'county'),
(442972, '150925', '40.502780210604', '112.55043247172', '凉城县', 442269, 'county'),
(442973, '150926', '40.981709597107', '113.24109639163', '察哈尔右翼前旗', 442269, 'county'),
(442974, '150927', '41.428255371505', '112.47074335399', '察哈尔右翼中旗', 442269, 'county'),
(442975, '150928', '41.529483090789', '113.06969288248', '察哈尔右翼后旗', 442269, 'county'),
(442976, '150929', '42.30714575607', '111.58903652853', '四子王旗', 442269, 'county'),
(442977, '150981', '40.558336025296', '113.30867650253', '丰镇市', 442269, 'county'),
(442978, '152201', '46.116943570165', '122.0815338095', '乌兰浩特市', 442270, 'county'),
(442979, '152202', '47.163696335727', '120.35753387505', '阿尔山市', 442270, 'county'),
(442980, '152221', '46.334025380898', '121.22152365342', '科尔沁右翼前旗', 442270, 'county'),
(442981, '152222', '45.242068815668', '121.19851019319', '科尔沁右翼中旗', 442270, 'county'),
(442982, '152223', '46.790807786397', '122.38814625782', '扎赉特旗', 442270, 'county'),
(442983, '152224', '45.632866219095', '121.51921179351', '突泉县', 442270, 'county'),
(442984, '152501', '43.417780458226', '111.96617841378', '二连浩特市', 442271, 'county'),
(442985, '152502', '44.078961129099', '116.13694826431', '锡林浩特市', 442271, 'county'),
(442986, '152522', '44.276507422523', '114.89347121165', '阿巴嘎旗', 442271, 'county'),
(442987, '152523', '44.039238043252', '113.14030742275', '苏尼特左旗', 442271, 'county'),
(442988, '152524', '42.900963777858', '112.91159981029', '苏尼特右旗', 442271, 'county'),
(442989, '152525', '45.826664793338', '117.8104504134', '东乌珠穆沁旗', 442271, 'county'),
(442990, '152526', '44.715902995292', '117.81696314273', '西乌珠穆沁旗', 442271, 'county'),
(442991, '152527', '41.906215635041', '115.30455821777', '太仆寺旗', 442271, 'county'),
(442992, '152528', '42.368275700926', '114.12058911409', '镶黄旗', 442271, 'county'),
(442993, '152529', '42.554842970033', '115.02434728451', '正镶白旗', 442271, 'county'),
(442994, '152530', '42.674413879311', '115.94010983058', '正蓝旗', 442271, 'county'),
(442995, '152531', '42.196600874379', '116.4986386762', '多伦县', 442271, 'county'),
(442996, '152921', '39.547806401013', '105.03824684198', '阿拉善左旗', 442272, 'county'),
(442997, '152922', '40.186228955604', '102.44385599727', '阿拉善右旗', 442272, 'county'),
(442998, '152923', '41.693799843161', '100.09951238471', '额济纳旗', 442272, 'county'),
(442999, '210102', '41.786474395792', '123.41433166046', '和平区', 442273, 'county'),
(443000, '210103', '41.798304641933', '123.45355228301', '沈河区', 442273, 'county'),
(443001, '210104', '41.835279080775', '123.49892677691', '大东区', 442273, 'county'),
(443002, '210105', '41.848913204573', '123.41537632672', '皇姑区', 442273, 'county'),
(443003, '210106', '41.805724167622', '123.35862982907', '铁西区', 442273, 'county'),
(443004, '210111', '41.589345157565', '123.42628905169', '苏家屯区', 442273, 'county'),
(443005, '210112', '41.794157738255', '123.5714290915', '浑南区', 442273, 'county'),
(443006, '210113', '42.043849976101', '123.5186904027', '沈北新区', 442273, 'county'),
(443007, '210114', '41.843551023712', '123.2428469731', '于洪区', 442273, 'county'),
(443008, '210115', '41.500330370098', '122.79857550059', '辽中区', 442273, 'county'),
(443009, '210123', '42.765540738313', '123.27359808776', '康平县', 442273, 'county'),
(443010, '210124', '42.415297839562', '123.24889709003', '法库县', 442273, 'county'),
(443011, '210181', '42.016776193846', '122.86641820399', '新民市', 442273, 'county'),
(443012, '210202', '38.900436431992', '121.67796628923', '中山区', 442274, 'county'),
(443013, '210203', '38.913369529939', '121.6258229781', '西岗区', 442274, 'county'),
(443014, '210204', '38.921778341674', '121.5826178068', '沙河口区', 442274, 'county'),
(443015, '210211', '38.955461760661', '121.52850037949', '甘井子区', 442274, 'county'),
(443016, '210212', '38.908290673003', '121.29593564059', '旅顺口区', 442274, 'county'),
(443017, '210213', '39.29861907186', '121.95658248044', '金州区', 442274, 'county'),
(443018, '210214', '39.651792833684', '122.21603953088', '普兰店区', 442274, 'county'),
(443019, '210224', '39.26010853029', '122.74826454271', '长海县', 442274, 'county'),
(443020, '210281', '39.70895639619', '121.79069878874', '瓦房店市', 442274, 'county'),
(443021, '210283', '39.858909784172', '122.934145267', '庄河市', 442274, 'county'),
(443022, '210302', '41.118235115557', '123.02070584518', '铁东区', 442275, 'county'),
(443023, '210303', '41.127872476833', '122.98578619475', '铁西区', 442275, 'county'),
(443024, '210304', '41.164172891853', '123.04047350708', '立山区', 442275, 'county'),
(443025, '210311', '41.061328521987', '123.01400529455', '千山区', 442275, 'county'),
(443026, '210321', '41.347099748004', '122.4436825276', '台安县', 442275, 'county'),
(443027, '210323', '40.4031809953', '123.34606899826', '岫岩满族自治县', 442275, 'county'),
(443028, '210381', '40.840354247523', '122.79120058219', '海城市', 442275, 'county'),
(443029, '210402', '41.869789660664', '123.91136857188', '新抚区', 442276, 'county'),
(443030, '210403', '41.833588275171', '124.02924934124', '东洲区', 442276, 'county'),
(443031, '210404', '41.860403588778', '123.78599647355', '望花区', 442276, 'county'),
(443032, '210411', '41.916014133796', '123.90172355545', '顺城区', 442276, 'county'),
(443033, '210421', '41.750076669591', '124.1365888338', '抚顺县', 442276, 'county'),
(443034, '210422', '41.635119411', '124.82786556411', '新宾满族自治县', 442276, 'county'),
(443035, '210423', '42.118882344791', '124.92431743309', '清原满族自治县', 442276, 'county'),
(443036, '210502', '41.240400691895', '123.69257521577', '平山区', 442277, 'county'),
(443037, '210503', '41.45615399333', '123.71186569778', '溪湖区', 442277, 'county'),
(443038, '210504', '41.347752110456', '123.90173685037', '明山区', 442277, 'county'),
(443039, '210505', '41.122716220999', '123.82788014761', '南芬区', 442277, 'county'),
(443040, '210521', '41.195670233912', '124.15856431847', '本溪满族自治县', 442277, 'county'),
(443041, '210522', '41.261815877129', '125.29002787', '桓仁满族自治县', 442277, 'county'),
(443042, '210602', '40.173197015058', '124.35032097797', '元宝区', 442278, 'county'),
(443043, '210603', '40.067035318752', '124.35556286011', '振兴区', 442278, 'county'),
(443044, '210604', '40.211546606919', '124.29219665893', '振安区', 442278, 'county'),
(443045, '210624', '40.766142006754', '124.93410611424', '宽甸满族自治县', 442278, 'county'),
(443046, '210681', '39.981217334184', '123.876870274', '东港市', 442278, 'county'),
(443047, '210682', '40.579570306659', '124.07296025051', '凤城市', 442278, 'county'),
(443048, '210702', '41.14138819307', '121.1264337451', '古塔区', 442279, 'county'),
(443049, '210703', '41.13438040426', '121.18266452595', '凌河区', 442279, 'county'),
(443050, '210711', '41.136830132753', '121.11864471768', '太和区', 442279, 'county'),
(443051, '210726', '41.799697885598', '122.26073588726', '黑山县', 442279, 'county'),
(443052, '210727', '41.534928118312', '121.30187737888', '义县', 442279, 'county'),
(443053, '210781', '41.152566155094', '121.28557458803', '凌海市', 442279, 'county'),
(443054, '210782', '41.547118023827', '121.86454971392', '北镇市', 442279, 'county'),
(443055, '210802', '40.703009826765', '122.2655920301', '站前区', 442280, 'county'),
(443056, '210803', '40.66694904618', '122.21012624622', '西市区', 442280, 'county'),
(443057, '210804', '40.25258448446', '122.17689658108', '鲅鱼圈区', 442280, 'county'),
(443058, '210811', '40.672565437571', '122.33090270339', '老边区', 442280, 'county'),
(443059, '210881', '40.235441470469', '122.47732679351', '盖州市', 442280, 'county'),
(443060, '210882', '40.646915451877', '122.57155106236', '大石桥市', 442280, 'county'),
(443061, '210902', '41.99090249247', '121.65270512981', '海州区', 442281, 'county'),
(443062, '210903', '42.074627619468', '121.82432100766', '新邱区', 442281, 'county'),
(443063, '210904', '42.00945236252', '121.73775310227', '太平区', 442281, 'county'),
(443064, '210905', '41.754998439335', '121.44683854847', '清河门区', 442281, 'county'),
(443065, '210911', '42.043253678758', '121.6275568874', '细河区', 442281, 'county'),
(443066, '210921', '42.157500408157', '121.69557778355', '阜新蒙古族自治县', 442281, 'county'),
(443067, '210922', '42.523754435526', '122.47417316389', '彰武县', 442281, 'county'),
(443068, '211002', '41.279285816853', '123.17516309965', '白塔区', 442282, 'county'),
(443069, '211003', '41.271122206557', '123.20121638487', '文圣区', 442282, 'county'),
(443070, '211004', '41.220763801748', '123.22051827536', '宏伟区', 442282, 'county'),
(443071, '211005', '41.145969646405', '123.42628014056', '弓长岭区', 442282, 'county'),
(443072, '211011', '41.274593139071', '123.17837427236', '太子河区', 442282, 'county'),
(443073, '211021', '41.077281158776', '123.21982126206', '辽阳县', 442282, 'county'),
(443074, '211081', '41.420098857086', '123.31257357315', '灯塔市', 442282, 'county'),
(443075, '211102', '41.193224510116', '122.03203843649', '双台子区', 442283, 'county'),
(443076, '211103', '41.155830887559', '121.96962911034', '兴隆台区', 442283, 'county'),
(443077, '211104', '40.905899458766', '122.08839097548', '大洼区', 442283, 'county'),
(443078, '211122', '41.193475065521', '121.95216562366', '盘山县', 442283, 'county'),
(443082, '211202', '42.248294823185', '123.85851586889', '银州区', 442284, 'county'),
(443084, '211204', '42.508557048192', '124.27578016446', '清河区', 442284, 'county'),
(443085, '211221', '42.222764650024', '123.91452868265', '铁岭县', 442284, 'county'),
(443086, '211223', '42.712739429005', '124.73850222789', '西丰县', 442284, 'county'),
(443087, '211224', '43.000462116167', '123.94640914451', '昌图县', 442284, 'county'),
(443088, '211281', '42.442929890534', '123.58434789559', '调兵山市', 442284, 'county'),
(443089, '211282', '42.471223289128', '124.28377598099', '开原市', 442284, 'county'),
(443090, '211302', '41.605740189556', '120.48407290204', '双塔区', 442285, 'county'),
(443091, '211303', '41.606226996662', '120.40133294592', '龙城区', 442285, 'county'),
(443092, '211321', '41.372795903547', '120.30506072918', '朝阳县', 442285, 'county'),
(443093, '211322', '41.842222586595', '119.63252714815', '建平县', 442285, 'county'),
(443094, '211324', '41.143623845035', '119.77553367022', '喀喇沁左翼蒙古族自治县', 442285, 'county'),
(443095, '211381', '41.865071031498', '120.81188458747', '北票市', 442285, 'county'),
(443096, '211382', '40.981801128352', '119.27154312683', '凌源市', 442285, 'county'),
(443097, '211402', '40.888537340117', '120.6883607801', '连山区', 442286, 'county'),
(443098, '211403', '40.750992710489', '120.90458597059', '龙港区', 442286, 'county'),
(443099, '211404', '41.137035783771', '120.66464506548', '南票区', 442286, 'county'),
(443100, '211421', '40.305129005823', '120.02630174192', '绥中县', 442286, 'county'),
(443101, '211422', '40.716827705586', '119.83489152944', '建昌县', 442286, 'county'),
(443102, '211481', '40.596284243832', '120.47552727234', '兴城市', 442286, 'county'),
(443103, '220102', '43.732190540843', '125.41964874071', '南关区', 442287, 'county'),
(443104, '220103', '43.998252407951', '125.34489933527', '宽城区', 442287, 'county'),
(443105, '220104', '43.689108619451', '125.27822648218', '朝阳区', 442287, 'county'),
(443106, '220105', '43.872222715497', '125.61148484631', '二道区', 442287, 'county'),
(443107, '220106', '43.912164564835', '125.19133076327', '绿园区', 442287, 'county'),
(443108, '220112', '43.531747024963', '125.71282235937', '双阳区', 442287, 'county'),
(443109, '220113', '44.194372106981', '125.96882675838', '九台区', 442287, 'county'),
(443110, '220122', '44.461506089801', '125.09432707273', '农安县', 442287, 'county'),
(443111, '220182', '44.879422926679', '126.60250076501', '榆树市', 442287, 'county'),
(443112, '220183', '44.510507146916', '125.76904438895', '德惠市', 442287, 'county'),
(443113, '220202', '44.023897560596', '126.3265130609', '昌邑区', 442288, 'county'),
(443114, '220203', '44.100874364702', '126.69508484724', '龙潭区', 442288, 'county'),
(443115, '220204', '43.882171941455', '126.38908947188', '船营区', 442288, 'county'),
(443116, '220211', '43.654515333155', '126.69820214702', '丰满区', 442288, 'county'),
(443117, '220221', '43.601481147552', '126.22756009767', '永吉县', 442288, 'county'),
(443118, '220281', '43.716756082246', '127.35174186542', '蛟河市', 442288, 'county'),
(443119, '220282', '43.056631099131', '127.04139243957', '桦甸市', 442288, 'county'),
(443120, '220283', '44.335465144158', '127.11677230895', '舒兰市', 442288, 'county'),
(443121, '220284', '43.05745611333', '126.17462779101', '磐石市', 442288, 'county'),
(443122, '220302', '43.214159722508', '124.35539155325', '铁西区', 442289, 'county'),
(443123, '220303', '43.101528833564', '124.45989915866', '铁东区', 442289, 'county'),
(443124, '220322', '43.414437629602', '124.38049140672', '梨树县', 442289, 'county'),
(443125, '220323', '43.346321828789', '125.27114939123', '伊通满族自治县', 442289, 'county'),
(443126, '220381', '43.791826067578', '124.6858822207', '公主岭市', 442289, 'county'),
(443127, '220382', '43.767694883217', '123.70852021747', '双辽市', 442289, 'county'),
(443128, '220402', '42.913196595909', '125.2109975481', '龙山区', 442290, 'county'),
(443129, '220403', '42.986364946378', '125.15014857862', '西安区', 442290, 'county'),
(443130, '220421', '42.683933895982', '125.45480890408', '东丰县', 442290, 'county'),
(443131, '220422', '42.94792512736', '125.18493119325', '东辽县', 442290, 'county'),
(443132, '220502', '41.677262396551', '125.9601237078', '东昌区', 442291, 'county'),
(443133, '220503', '41.772625959427', '126.15628012439', '二道江区', 442291, 'county'),
(443134, '220521', '41.729156130979', '125.85733217991', '通化县', 442291, 'county'),
(443135, '220523', '42.557948885604', '126.34272419975', '辉南县', 442291, 'county'),
(443136, '220524', '42.185665412078', '125.91727588294', '柳河县', 442291, 'county'),
(443137, '220581', '42.542649892656', '125.72351563218', '梅河口市', 442291, 'county'),
(443138, '220582', '41.251410585346', '125.99899197532', '集安市', 442291, 'county'),
(443139, '220602', '41.791642228255', '126.39664287376', '浑江区', 442292, 'county'),
(443140, '220605', '42.078958587922', '126.82530168684', '江源区', 442292, 'county'),
(443141, '220621', '42.277909113144', '127.62393805705', '抚松县', 442292, 'county'),
(443142, '220622', '42.449966505533', '126.90246851455', '靖宇县', 442292, 'county'),
(443143, '220623', '41.584709161363', '127.86435839919', '长白朝鲜族自治县', 442292, 'county'),
(443144, '220681', '41.816565968987', '127.19171033688', '临江市', 442292, 'county'),
(443145, '220702', '45.292709616884', '124.86757114896', '宁江区', 442293, 'county'),
(443146, '220721', '44.86912678932', '124.48165037618', '前郭尔罗斯蒙古族自治县', 442293, 'county'),
(443147, '220722', '44.305644527778', '123.8665042888', '长岭县', 442293, 'county'),
(443148, '220723', '44.92691448746', '123.96912337789', '乾安县', 442293, 'county'),
(443149, '220781', '45.171384133354', '125.60981401543', '扶余市', 442293, 'county'),
(443150, '220802', '45.623300921069', '122.78907446427', '洮北区', 442294, 'county'),
(443151, '220821', '45.956171923796', '123.45227210722', '镇赉县', 442294, 'county'),
(443152, '220822', '44.785716778696', '122.74529133311', '通榆县', 442294, 'county'),
(443153, '220881', '45.475604304499', '122.45367732552', '洮南市', 442294, 'county'),
(443154, '220882', '45.432438158186', '123.72371415195', '大安市', 442294, 'county'),
(443155, '222401', '43.05966660114', '129.47130153101', '延吉市', 442295, 'county'),
(443156, '222402', '43.03054892373', '129.83431076023', '图们市', 442295, 'county'),
(443157, '222403', '43.560201838077', '128.23949928011', '敦化市', 442295, 'county'),
(443158, '222404', '43.074719340737', '130.70236659184', '珲春市', 442295, 'county'),
(443159, '222405', '42.844249320769', '129.38381622469', '龙井市', 442295, 'county'),
(443160, '222406', '42.466442285556', '128.91121076889', '和龙市', 442295, 'county'),
(443161, '222424', '43.540143921506', '129.95399441696', '汪清县', 442295, 'county'),
(443162, '222426', '42.70103301919', '128.43765169208', '安图县', 442295, 'county'),
(443163, '230102', '45.686139243933', '126.36841846875', '道里区', 442296, 'county'),
(443164, '230103', '45.66612348458', '126.59025453924', '南岗区', 442296, 'county'),
(443165, '230104', '45.799105971955', '126.79557490271', '道外区', 442296, 'county'),
(443166, '230108', '45.773224633239', '126.65771685545', '平房区', 442296, 'county'),
(443167, '230109', '45.941458151669', '126.45227113075', '松北区', 442296, 'county'),
(443168, '230110', '45.710449322359', '126.79204413625', '香坊区', 442296, 'county'),
(443169, '230111', '46.079315096502', '126.78775713041', '呼兰区', 442296, 'county'),
(443170, '230112', '45.557335189202', '127.12462182332', '阿城区', 442296, 'county'),
(443171, '230113', '45.429694282772', '126.20893033512', '双城区', 442296, 'county'),
(443172, '230123', '46.275637068421', '129.72150310519', '依兰县', 442296, 'county'),
(443173, '230124', '45.819769362966', '128.94941872931', '方正县', 442296, 'county'),
(443174, '230125', '45.783825431221', '127.66161209688', '宾县', 442296, 'county'),
(443175, '230126', '46.340415542078', '127.32428735381', '巴彦县', 442296, 'county'),
(443176, '230127', '46.248171654041', '127.92983800734', '木兰县', 442296, 'county'),
(443177, '230128', '46.247857247283', '128.7622323166', '通河县', 442296, 'county'),
(443178, '230129', '45.489520215745', '128.4639428653', '延寿县', 442296, 'county'),
(443179, '230183', '45.083893011118', '128.31617023054', '尚志市', 442296, 'county'),
(443180, '230184', '44.772543560859', '127.49111263245', '五常市', 442296, 'county'),
(443181, '230202', '47.301073163863', '123.94483825767', '龙沙区', 442297, 'county'),
(443182, '230203', '47.404865706359', '124.02127875657', '建华区', 442297, 'county'),
(443183, '230204', '47.303488569291', '124.26293093367', '铁锋区', 442297, 'county'),
(443184, '230205', '47.104048383337', '123.97293464894', '昂昂溪区', 442297, 'county'),
(443185, '230206', '47.228951853753', '123.57199835236', '富拉尔基区', 442297, 'county'),
(443186, '230207', '47.585869259054', '122.93233528482', '碾子山区', 442297, 'county'),
(443187, '230208', '47.583080065198', '124.00548681519', '梅里斯达斡尔族区', 442297, 'county'),
(443188, '230221', '47.258895031048', '123.08910277315', '龙江县', 442297, 'county'),
(443189, '230223', '47.70687276451', '125.29463341876', '依安县', 442297, 'county'),
(443190, '230224', '46.603290111422', '123.55804791893', '泰来县', 442297, 'county'),
(443191, '230225', '48.011583079958', '123.84689963764', '甘南县', 442297, 'county'),
(443192, '230227', '47.66582009392', '124.57174679759', '富裕县', 442297, 'county'),
(443193, '230229', '48.16709075607', '125.70647087609', '克山县', 442297, 'county'),
(443194, '230230', '48.009015428979', '126.35213605416', '克东县', 442297, 'county'),
(443195, '230231', '47.59225565379', '126.02178604309', '拜泉县', 442297, 'county'),
(443196, '230281', '48.481453388811', '125.07655310394', '讷河市', 442297, 'county'),
(443197, '230302', '45.307610212685', '130.95993684965', '鸡冠区', 442298, 'county'),
(443198, '230303', '45.138570833129', '130.91626680525', '恒山区', 442298, 'county'),
(443199, '230304', '45.354342346984', '130.73483586173', '滴道区', 442298, 'county'),
(443200, '230305', '45.097064304174', '130.76523847274', '梨树区', 442298, 'county'),
(443201, '230306', '45.379689760283', '131.02770429868', '城子河区', 442298, 'county'),
(443202, '230307', '45.205825834254', '130.56688686698', '麻山区', 442298, 'county'),
(443203, '230321', '45.273228207889', '131.22565372007', '鸡东县', 442298, 'county'),
(443204, '230381', '45.997276203515', '133.12110607261', '虎林市', 442298, 'county'),
(443205, '230382', '45.469765426971', '132.17656238974', '密山市', 442298, 'county'),
(443206, '230402', '47.350919505165', '130.30123313444', '向阳区', 442299, 'county'),
(443207, '230403', '47.327770216306', '130.27719618578', '工农区', 442299, 'county'),
(443208, '230404', '47.298820938262', '130.28176460828', '南山区', 442299, 'county'),
(443209, '230405', '47.23371006572', '130.24437533634', '兴安区', 442299, 'county'),
(443210, '230406', '47.483737355287', '130.24750143952', '东山区', 442299, 'county'),
(443211, '230407', '47.393964799831', '130.32664592783', '兴山区', 442299, 'county'),
(443212, '230421', '47.74693489479', '130.76133324012', '萝北县', 442299, 'county'),
(443213, '230422', '47.483007019685', '131.85659492327', '绥滨县', 442299, 'county'),
(443214, '230502', '46.658524603822', '131.17851398363', '尖山区', 442300, 'county'),
(443215, '230503', '46.459521565337', '131.24602424779', '岭东区', 442300, 'county'),
(443216, '230505', '46.669775046181', '131.30870692831', '四方台区', 442300, 'county'),
(443217, '230506', '46.5292279819', '131.56483592752', '宝山区', 442300, 'county'),
(443218, '230521', '46.818437079003', '131.15055588277', '集贤县', 442300, 'county'),
(443219, '230522', '46.788592814562', '131.8549989164', '友谊县', 442300, 'county'),
(443220, '230523', '46.409383212717', '132.40927864827', '宝清县', 442300, 'county'),
(443221, '230524', '47.072628542857', '133.7292586825', '饶河县', 442300, 'county'),
(443222, '230602', '46.663311354817', '125.0424515298', '萨尔图区', 442301, 'county'),
(443223, '230603', '46.53556824178', '125.14176665986', '龙凤区', 442301, 'county'),
(443224, '230604', '46.729160383306', '124.83842676542', '让胡路区', 442301, 'county'),
(443225, '230605', '46.420778588396', '124.91428498269', '红岗区', 442301, 'county'),
(443226, '230606', '46.070051001663', '124.69907739268', '大同区', 442301, 'county'),
(443227, '230621', '45.837071583611', '125.3089692416', '肇州县', 442301, 'county'),
(443228, '230622', '45.647200471445', '124.76904364094', '肇源县', 442301, 'county'),
(443229, '230623', '47.159692937417', '124.8967829092', '林甸县', 442301, 'county'),
(443230, '230624', '46.561613536188', '124.24651264677', '杜尔伯特蒙古族自治县', 442301, 'county'),
(443231, '230702', '47.741959238189', '128.90057964259', '伊春区', 442302, 'county'),
(443232, '230703', '46.964156236684', '129.5388741261', '南岔区', 442302, 'county'),
(443233, '230704', '48.128001664241', '128.46596407584', '友好区', 442302, 'county'),
(443234, '230705', '47.500962038143', '129.22725517859', '西林区', 442302, 'county'),
(443235, '230706', '47.589933517239', '128.3654114401', '翠峦区', 442302, 'county'),
(443236, '230707', '48.216126405552', '129.78735692847', '新青区', 442302, 'county'),
(443237, '230708', '47.768892089215', '129.40940395803', '美溪区', 442302, 'county'),
(443238, '230709', '47.498543610736', '129.77190301946', '金山屯区', 442302, 'county'),
(443239, '230710', '48.229327781105', '129.061485473', '五营区', 442302, 'county'),
(443240, '230711', '47.549368172364', '128.79469008399', '乌马河区', 442302, 'county'),
(443241, '230712', '48.563262601637', '129.53875384299', '汤旺河区', 442302, 'county'),
(443242, '230713', '47.090162166708', '128.86147460713', '带岭区', 442302, 'county'),
(443243, '230714', '48.836655251992', '129.49893645126', '乌伊岭区', 442302, 'county'),
(443244, '230715', '48.298020306125', '129.25191896484', '红星区', 442302, 'county'),
(443245, '230716', '48.036509272978', '129.02239948161', '上甘岭区', 442302, 'county'),
(443246, '230722', '48.769519787363', '130.00824972425', '嘉荫县', 442302, 'county'),
(443247, '230781', '46.866328682376', '128.55251746527', '铁力市', 442302, 'county'),
(443248, '230803', '46.826706255713', '130.36295545541', '向阳区', 442303, 'county'),
(443249, '230804', '46.809721977545', '130.39791016311', '前进区', 442303, 'county'),
(443250, '230805', '46.894910414945', '130.51740321928', '东风区', 442303, 'county'),
(443251, '230811', '46.775887398703', '130.26396912133', '郊区', 442303, 'county'),
(443252, '230822', '46.306671717134', '130.63701542096', '桦南县', 442303, 'county'),
(443253, '230826', '46.989258424239', '130.9630176143', '桦川县', 442303, 'county'),
(443254, '230828', '46.988318509463', '130.07240618628', '汤原县', 442303, 'county'),
(443255, '230881', '47.833684686564', '133.27332836382', '同江市', 442303, 'county'),
(443256, '230882', '47.170672548244', '132.53900135629', '富锦市', 442303, 'county'),
(443257, '230883', '47.955162063941', '134.39306261929', '抚远市', 442303, 'county'),
(443258, '230902', '45.8134935903', '130.89318834856', '新兴区', 442304, 'county'),
(443259, '230903', '45.770092507257', '130.9925031193', '桃山区', 442304, 'county'),
(443260, '230904', '45.883167710316', '131.47522375459', '茄子河区', 442304, 'county'),
(443261, '230921', '45.930545419106', '130.81816940292', '勃利县', 442304, 'county'),
(443262, '231002', '44.408404499377', '129.86044675749', '东安区', 442305, 'county'),
(443263, '231003', '44.58797510378', '129.78391508059', '阳明区', 442305, 'county'),
(443264, '231004', '44.685920648737', '129.54456588932', '爱民区', 442305, 'county'),
(443265, '231005', '44.491714149262', '129.58492424063', '西安区', 442305, 'county'),
(443266, '231025', '45.396101732571', '130.02318050895', '林口县', 442305, 'county'),
(443267, '231081', '44.408005174587', '131.10245653286', '绥芬河市', 442305, 'county'),
(443268, '231083', '44.903617439366', '129.2214141346', '海林市', 442305, 'county'),
(443269, '231084', '44.058017259883', '129.21531714201', '宁安市', 442305, 'county'),
(443270, '231085', '44.576869855321', '130.39552588753', '穆棱市', 442305, 'county'),
(443271, '231086', '44.085228695883', '130.82976155466', '东宁市', 442305, 'county'),
(443272, '231102', '50.21824505447', '126.76426227527', '爱辉区', 442306, 'county'),
(443273, '231121', '49.621866015064', '125.77127508963', '嫩江县', 442306, 'county'),
(443274, '231123', '48.886739946849', '128.37087710653', '逊克县', 442306, 'county'),
(443275, '231124', '49.370655539474', '127.31667232079', '孙吴县', 442306, 'county'),
(443276, '231181', '48.115945723953', '127.11154600578', '北安市', 442306, 'county'),
(443277, '231182', '48.749166077372', '126.63450133401', '五大连池市', 442306, 'county'),
(443278, '231202', '46.747536778515', '126.95786274455', '北林区', 442307, 'county'),
(443279, '231221', '46.869481261175', '126.59302313008', '望奎县', 442307, 'county'),
(443280, '231222', '46.358350137762', '126.21354291791', '兰西县', 442307, 'county'),
(443281, '231223', '46.846560509098', '125.96052417701', '青冈县', 442307, 'county'),
(443282, '231224', '47.070365971064', '127.84448982607', '庆安县', 442307, 'county'),
(443283, '231225', '47.201247327838', '125.84126811337', '明水县', 442307, 'county'),
(443284, '231226', '47.584142706971', '127.71941343154', '绥棱县', 442307, 'county'),
(443285, '231281', '46.535467128182', '125.38455235789', '安达市', 442307, 'county'),
(443286, '231282', '46.009305917541', '125.84973124624', '肇东市', 442307, 'county'),
(443287, '231283', '47.447269604837', '126.89712924928', '海伦市', 442307, 'county'),
(443288, '232721', '51.813130087054', '124.91200234302', '呼玛县', 442308, 'county'),
(443289, '232722', '52.716506252523', '124.64020335752', '塔河县', 442308, 'county'),
(443290, '232723', '52.945658619469', '122.71572081474', '漠河县', 442308, 'county'),
(443291, '310101', '31.227203440769', '121.49607206403', '黄浦区', 442309, 'county'),
(443292, '310104', '31.169152089592', '121.44623500473', '徐汇区', 442309, 'county'),
(443293, '310105', '31.213301496814', '121.38761610866', '长宁区', 442309, 'county'),
(443294, '310106', '31.235380803488', '121.454755557', '静安区', 442309, 'county'),
(443295, '310107', '31.263742929076', '121.39844294375', '普陀区', 442309, 'county'),
(443296, '310109', '31.282497228987', '121.49191854079', '虹口区', 442309, 'county'),
(443297, '310110', '31.304510479542', '121.53571659963', '杨浦区', 442309, 'county'),
(443298, '310112', '31.093537540382', '121.42502428093', '闵行区', 442309, 'county'),
(443299, '310113', '31.398622694467', '121.40904121845', '宝山区', 442309, 'county'),
(443300, '310114', '31.364338055434', '121.25101353756', '嘉定区', 442309, 'county'),
(443301, '310115', '31.230895349134', '121.63848131409', '浦东新区', 442309, 'county'),
(443302, '310116', '30.835080777082', '121.24840817975', '金山区', 442309, 'county'),
(443303, '310117', '31.021244628099', '121.22679050142', '松江区', 442309, 'county'),
(443304, '310118', '31.130862397997', '121.09142524282', '青浦区', 442309, 'county'),
(443305, '310120', '30.915122452606', '121.56064167963', '奉贤区', 442309, 'county'),
(443306, '310151', '31.52860136251', '121.56909950183', '崇明区', 442309, 'county'),
(443307, '320102', '32.07176566029', '118.84893734485', '玄武区', 442310, 'county'),
(443308, '320104', '32.007969136143', '118.81722069709', '秦淮区', 442310, 'county'),
(443309, '320105', '32.012518207527', '118.71334176065', '建邺区', 442310, 'county'),
(443310, '320106', '32.068604458801', '118.76505691316', '鼓楼区', 442310, 'county'),
(443311, '320111', '32.05906230054', '118.56912478518', '浦口区', 442310, 'county'),
(443312, '320113', '32.16942425653', '118.96372475912', '栖霞区', 442310, 'county'),
(443313, '320114', '31.954552108797', '118.72197857905', '雨花台区', 442310, 'county'),
(443314, '320115', '31.863971430281', '118.83541822485', '江宁区', 442310, 'county'),
(443315, '320116', '32.400640243232', '118.84816604456', '六合区', 442310, 'county'),
(443316, '320117', '31.59098879063', '119.03955092741', '溧水区', 442310, 'county'),
(443317, '320118', '31.363673442531', '118.9648579166', '高淳区', 442310, 'county'),
(443318, '320205', '31.615587416408', '120.49100821099', '锡山区', 442311, 'county'),
(443319, '320206', '31.656376333546', '120.21529447552', '惠山区', 442311, 'county'),
(443320, '320211', '31.466578565031', '120.24850182101', '滨湖区', 442311, 'county'),
(443321, '320213', '31.57842412658', '120.30311934862', '梁溪区', 442311, 'county'),
(443322, '320214', '31.519399416228', '120.43882764569', '新吴区', 442311, 'county'),
(443323, '320281', '31.837425422051', '120.31067896716', '江阴市', 442311, 'county'),
(443324, '320282', '31.362244911879', '119.79026529658', '宜兴市', 442311, 'county'),
(443325, '320302', '34.301409800357', '117.29612858533', '鼓楼区', 442312, 'county'),
(443326, '320303', '34.22248667954', '117.27617608552', '云龙区', 442312, 'county'),
(443327, '320305', '34.410527773608', '117.49824588411', '贾汪区', 442312, 'county'),
(443328, '320311', '34.241946575704', '117.1755840183', '泉山区', 442312, 'county'),
(443329, '320312', '34.348981539618', '117.22940160979', '铜山区', 442312, 'county'),
(443330, '320321', '34.695773328628', '116.61573315373', '丰县', 442312, 'county'),
(443331, '320322', '34.700648164694', '116.91146840815', '沛县', 442312, 'county'),
(443332, '320324', '33.946570640866', '117.89036426969', '睢宁县', 442312, 'county'),
(443333, '320381', '34.284442736534', '118.34412147229', '新沂市', 442312, 'county'),
(443334, '320382', '34.402946394877', '117.90306004276', '邳州市', 442312, 'county'),
(443335, '320402', '31.777803256373', '120.00176576036', '天宁区', 442313, 'county'),
(443336, '320404', '31.79851137455', '119.91243874189', '钟楼区', 442313, 'county'),
(443337, '320411', '31.939946043961', '119.90315390841', '新北区', 442313, 'county'),
(443338, '320412', '31.672903473648', '119.94343167667', '武进区', 442313, 'county'),
(443339, '320413', '31.728356462124', '119.53415121469', '金坛区', 442313, 'county'),
(443340, '320481', '31.425241931012', '119.38283894831', '溧阳市', 442313, 'county'),
(443341, '320505', '31.351869327642', '120.47842441781', '虎丘区', 442314, 'county'),
(443342, '320506', '31.179869740166', '120.36577637267', '吴中区', 442314, 'county'),
(443343, '320507', '31.450775031111', '120.64685298258', '相城区', 442314, 'county'),
(443344, '320508', '31.326429631222', '120.61427934735', '姑苏区', 442314, 'county'),
(443345, '320509', '31.000093080624', '120.65734994979', '吴江区', 442314, 'county'),
(443346, '320581', '31.669446047798', '120.83148596516', '常熟市', 442314, 'county'),
(443347, '320582', '31.907812337769', '120.62727852834', '张家港市', 442314, 'county'),
(443348, '320583', '31.328936795497', '120.96580778411', '昆山市', 442314, 'county'),
(443349, '320585', '31.571904296415', '121.15897767248', '太仓市', 442314, 'county'),
(443350, '320602', '31.962660695271', '120.88759857738', '崇川区', 442315, 'county'),
(443351, '320611', '32.071256422788', '120.82387483505', '港闸区', 442315, 'county'),
(443352, '320612', '32.067098964254', '121.07249442751', '通州区', 442315, 'county'),
(443353, '320621', '32.553985066143', '120.47392692165', '海安县', 442315, 'county'),
(443354, '320623', '32.387662145338', '121.05924442185', '如东县', 442315, 'county'),
(443355, '320681', '31.871301838383', '121.67882229665', '启东市', 442315, 'county'),
(443356, '320682', '32.273616272606', '120.580143985', '如皋市', 442315, 'county'),
(443357, '320684', '31.956038868177', '121.31247014367', '海门市', 442315, 'county'),
(443358, '320703', '34.638921829102', '119.46701669742', '连云区', 442316, 'county'),
(443359, '320706', '34.514160144549', '119.16219625272', '海州区', 442316, 'county'),
(443360, '320707', '34.921103960847', '119.07859315245', '赣榆区', 442316, 'county'),
(443361, '320722', '34.556383225488', '118.79230964695', '东海县', 442316, 'county'),
(443362, '320723', '34.406832167104', '119.39277519918', '灌云县', 442316, 'county'),
(443363, '320724', '34.175194871764', '119.44639688138', '灌南县', 442316, 'county'),
(443364, '320803', '33.528348966942', '119.31329513264', '淮安区', 442317, 'county'),
(443365, '320804', '33.664059258402', '118.93566378046', '淮阴区', 442317, 'county'),
(443366, '320812', '33.494331166176', '119.04477992516', '清江浦区', 442317, 'county'),
(443367, '320813', '33.230193969134', '118.83000637571', '洪泽区', 442317, 'county'),
(443368, '320826', '33.884155184174', '119.32495655858', '涟水县', 442317, 'county'),
(443369, '320830', '32.971613125783', '118.53823246743', '盱眙县', 442317, 'county'),
(443370, '320831', '33.02583443776', '119.14563113528', '金湖县', 442317, 'county'),
(443371, '320902', '33.378948242447', '120.20635135183', '亭湖区', 442318, 'county'),
(443372, '320903', '33.265898266894', '119.96850073907', '盐都区', 442318, 'county'),
(443373, '320904', '33.265908526078', '120.58506449027', '大丰区', 442318, 'county'),
(443374, '320921', '34.232797426966', '119.79760156833', '响水县', 442318, 'county'),
(443375, '320922', '34.092317176392', '120.02660867811', '滨海县', 442318, 'county'),
(443376, '320923', '33.71197604815', '119.70499024879', '阜宁县', 442318, 'county'),
(443377, '320924', '33.745462250481', '120.27950474858', '射阳县', 442318, 'county'),
(443378, '320925', '33.488907986634', '119.83649673997', '建湖县', 442318, 'county'),
(443379, '320981', '32.791442548289', '120.56376947144', '东台市', 442318, 'county'),
(443380, '321002', '32.395670095608', '119.48667775758', '广陵区', 442319, 'county'),
(443381, '321003', '32.425830218252', '119.45826385876', '邗江区', 442319, 'county'),
(443382, '321012', '32.549160271061', '119.71731808779', '江都区', 442319, 'county'),
(443383, '321023', '33.225833658364', '119.45565078384', '宝应县', 442319, 'county'),
(443384, '321081', '32.392636465119', '119.20095502034', '仪征市', 442319, 'county'),
(443385, '321084', '32.835943695939', '119.50340701788', '高邮市', 442319, 'county'),
(443386, '321102', '32.201996095087', '119.5848217021', '京口区', 442320, 'county'),
(443387, '321111', '32.19664652864', '119.43092031591', '润州区', 442320, 'county'),
(443388, '321112', '32.114041364762', '119.4989723505', '丹徒区', 442320, 'county'),
(443389, '321181', '31.960263455083', '119.64430350829', '丹阳市', 442320, 'county'),
(443390, '321182', '32.189469410323', '119.84513751029', '扬中市', 442320, 'county'),
(443391, '321183', '31.932634957798', '119.20707980344', '句容市', 442320, 'county'),
(443392, '321202', '32.488257837661', '119.92117442715', '海陵区', 442321, 'county'),
(443393, '321203', '32.330075314459', '119.92574377278', '高港区', 442321, 'county'),
(443394, '321204', '32.532466165694', '120.06704535319', '姜堰区', 442321, 'county'),
(443395, '321281', '32.961954308808', '119.99641814069', '兴化市', 442321, 'county'),
(443396, '321282', '32.039442789049', '120.27689862725', '靖江市', 442321, 'county'),
(443397, '321283', '32.213678940627', '120.135346292', '泰兴市', 442321, 'county'),
(443398, '321302', '33.862829055956', '118.27463983758', '宿城区', 442322, 'county'),
(443399, '321311', '34.009529591744', '118.34369284322', '宿豫区', 442322, 'county'),
(443400, '321322', '34.154013659597', '118.85774971753', '沭阳县', 442322, 'county'),
(443401, '321323', '33.708800542074', '118.65694128685', '泗阳县', 442322, 'county'),
(443402, '321324', '33.425955266134', '118.3125512525', '泗洪县', 442322, 'county'),
(443403, '330102', '30.232357639233', '120.18012613889', '上城区', 442323, 'county'),
(443404, '330103', '30.310287874904', '120.18653502974', '下城区', 442323, 'county'),
(443405, '330104', '30.315832099954', '120.30382324371', '江干区', 442323, 'county'),
(443406, '330105', '30.344732010358', '120.15884493257', '拱墅区', 442323, 'county'),
(443407, '330106', '30.207036169515', '120.08899292561', '西湖区', 442323, 'county'),
(443408, '330108', '30.187587607727', '120.19237042946', '滨江区', 442323, 'county'),
(443409, '330109', '30.172893839066', '120.38908074858', '萧山区', 442323, 'county'),
(443410, '330110', '30.388119980754', '119.99808906005', '余杭区', 442323, 'county'),
(443411, '330111', '29.977808419757', '119.81096609176', '富阳区', 442323, 'county'),
(443412, '330122', '29.836582478934', '119.5604618667', '桐庐县', 442323, 'county'),
(443413, '330127', '29.614714225509', '118.89576489835', '淳安县', 442323, 'county'),
(443414, '330182', '29.487115319259', '119.37953322636', '建德市', 442323, 'county'),
(443415, '330185', '30.207683765784', '119.35029466684', '临安市', 442323, 'county'),
(443416, '330203', '29.876800511994', '121.5353945773', '海曙区', 442324, 'county'),
(443417, '330204', '29.87539247212', '121.5980008523', '江东区', 442324, 'county'),
(443418, '330205', '29.96639219001', '121.49329902932', '江北区', 442324, 'county'),
(443419, '330206', '29.868332319465', '121.88941885595', '北仑区', 442324, 'county'),
(443420, '330211', '29.995449382446', '121.61663045279', '镇海区', 442324, 'county'),
(443421, '330212', '29.78545893326', '121.53783481355', '鄞州区', 442324, 'county'),
(443422, '330225', '29.378771009449', '121.85866557564', '象山县', 442324, 'county'),
(443423, '330226', '29.314474088639', '121.46362436946', '宁海县', 442324, 'county'),
(443424, '330281', '29.996456719011', '121.15277918829', '余姚市', 442324, 'county'),
(443425, '330282', '30.189257122714', '121.33840825932', '慈溪市', 442324, 'county'),
(443426, '330283', '29.617073470394', '121.37718563878', '奉化市', 442324, 'county'),
(443427, '330302', '28.067865050513', '120.56579853224', '鹿城区', 442325, 'county'),
(443428, '330303', '27.913340713281', '120.81107773683', '龙湾区', 442325, 'county'),
(443429, '330304', '27.972177190591', '120.55840358596', '瓯海区', 442325, 'county'),
(443430, '330305', '27.884883705563', '121.15231818926', '洞头区', 442325, 'county'),
(443431, '330324', '28.336390468031', '120.66880872172', '永嘉县', 442325, 'county'),
(443432, '330326', '27.637700763436', '120.38938725481', '平阳县', 442325, 'county'),
(443433, '330327', '27.434436382653', '120.44554278341', '苍南县', 442325, 'county'),
(443434, '330328', '27.81271343668', '120.02842209847', '文成县', 442325, 'county'),
(443435, '330329', '27.536406837073', '119.88486761051', '泰顺县', 442325, 'county'),
(443436, '330381', '27.82923052833', '120.46834036335', '瑞安市', 442325, 'county'),
(443437, '330382', '28.26183898877', '121.01617490318', '乐清市', 442325, 'county'),
(443438, '330402', '30.716357921235', '120.84453542647', '南湖区', 442326, 'county'),
(443439, '330411', '30.777678969089', '120.69190746888', '秀洲区', 442326, 'county'),
(443440, '330421', '30.905748069187', '120.90887281597', '嘉善县', 442326, 'county'),
(443441, '330424', '30.526042585394', '120.88557558868', '海盐县', 442326, 'county'),
(443442, '330481', '30.442176799317', '120.61872710778', '海宁市', 442326, 'county');
INSERT INTO `region` (`id`, `code`, `lat`, `lng`, `name`, `parent_id`, `by_type`) VALUES
(443443, '330482', '30.716528587208', '121.10583903762', '平湖市', 442326, 'county'),
(443444, '330483', '30.612341030328', '120.49041120216', '桐乡市', 442326, 'county'),
(443445, '330502', '30.808545234564', '120.08891886954', '吴兴区', 442327, 'county'),
(443446, '330503', '30.766830865515', '120.30914675944', '南浔区', 442327, 'county'),
(443447, '330521', '30.567582881042', '120.04983138985', '德清县', 442327, 'county'),
(443448, '330522', '30.983352787535', '119.81941984715', '长兴县', 442327, 'county'),
(443449, '330523', '30.626370494334', '119.58315792627', '安吉县', 442327, 'county'),
(443450, '330602', '30.015792939952', '120.61832665179', '越城区', 442328, 'county'),
(443451, '330603', '29.999366392659', '120.54020524674', '柯桥区', 442328, 'county'),
(443452, '330604', '30.00645910703', '120.87986642651', '上虞区', 442328, 'county'),
(443453, '330624', '29.414313976622', '120.97570154218', '新昌县', 442328, 'county'),
(443454, '330681', '29.699399516981', '120.28143440994', '诸暨市', 442328, 'county'),
(443455, '330683', '29.591008031468', '120.76143097735', '嵊州市', 442328, 'county'),
(443456, '330702', '28.984539673649', '119.51757234284', '婺城区', 442329, 'county'),
(443457, '330703', '29.155526265081', '119.80922749595', '金东区', 442329, 'county'),
(443458, '330723', '28.774055561598', '119.72083317224', '武义县', 442329, 'county'),
(443459, '330726', '29.526266410155', '119.91048752626', '浦江县', 442329, 'county'),
(443460, '330727', '29.04420249188', '120.56744721648', '磐安县', 442329, 'county'),
(443461, '330781', '29.284102536325', '119.53333759742', '兰溪市', 442329, 'county'),
(443462, '330782', '29.306443911839', '120.06729564867', '义乌市', 442329, 'county'),
(443463, '330783', '29.237426947341', '120.38081772668', '东阳市', 442329, 'county'),
(443464, '330784', '28.940176566983', '120.10868352215', '永康市', 442329, 'county'),
(443465, '330802', '28.998535292058', '118.8130029548', '柯城区', 442330, 'county'),
(443466, '330803', '28.941983087299', '118.93904421103', '衢江区', 442330, 'county'),
(443467, '330822', '28.973666155532', '118.54767046745', '常山县', 442330, 'county'),
(443468, '330824', '29.18993794143', '118.33165006627', '开化县', 442330, 'county'),
(443469, '330825', '28.997079389242', '119.19866420604', '龙游县', 442330, 'county'),
(443470, '330881', '28.581969944141', '118.60708619901', '江山市', 442330, 'county'),
(443471, '330902', '30.06484716159', '122.07302446869', '定海区', 442331, 'county'),
(443472, '330903', '29.871101375771', '122.27876474766', '普陀区', 442331, 'county'),
(443473, '330921', '30.319415586505', '122.26035914727', '岱山县', 442331, 'county'),
(443474, '330922', '30.705003931261', '122.48168649477', '嵊泗县', 442331, 'county'),
(443475, '331002', '28.657015656331', '121.46737635254', '椒江区', 442332, 'county'),
(443476, '331003', '28.604655275769', '121.08831775253', '黄岩区', 442332, 'county'),
(443477, '331004', '28.548659438247', '121.45024245576', '路桥区', 442332, 'county'),
(443478, '331021', '28.179738010609', '121.28442605522', '玉环县', 442332, 'county'),
(443479, '331022', '29.017744246024', '121.48822880178', '三门县', 442332, 'county'),
(443480, '331023', '29.151778640761', '120.98556322305', '天台县', 442332, 'county'),
(443481, '331024', '28.738741988629', '120.64060572539', '仙居县', 442332, 'county'),
(443482, '331081', '28.400553817107', '121.42104597878', '温岭市', 442332, 'county'),
(443483, '331082', '28.857388590573', '121.22191927302', '临海市', 442332, 'county'),
(443484, '331102', '28.447361330679', '119.84995169272', '莲都区', 442333, 'county'),
(443485, '331121', '28.208428623515', '120.14673815822', '青田县', 442333, 'county'),
(443486, '331122', '28.666326291231', '120.19188183536', '缙云县', 442333, 'county'),
(443487, '331123', '28.525410332354', '119.08934238361', '遂昌县', 442333, 'county'),
(443488, '331124', '28.41158038279', '119.44101320226', '松阳县', 442333, 'county'),
(443489, '331125', '28.131320418187', '119.54173007925', '云和县', 442333, 'county'),
(443490, '331126', '27.62804612399', '119.15761923529', '庆元县', 442333, 'county'),
(443491, '331127', '27.896052631241', '119.61928969769', '景宁畲族自治县', 442333, 'county'),
(443492, '331181', '28.050639306133', '119.08229725532', '龙泉市', 442333, 'county'),
(443493, '340102', '31.905375399342', '117.33122366889', '瑶海区', 442334, 'county'),
(443494, '340103', '31.912901051134', '117.24783468704', '庐阳区', 442334, 'county'),
(443495, '340104', '31.838184928803', '117.23128044361', '蜀山区', 442334, 'county'),
(443496, '340111', '31.790724288122', '117.35391279997', '包河区', 442334, 'county'),
(443497, '340121', '32.286111151904', '117.17443835982', '长丰县', 442334, 'county'),
(443498, '340122', '32.003189086973', '117.57585687571', '肥东县', 442334, 'county'),
(443499, '340123', '31.732638067993', '117.03626088173', '肥西县', 442334, 'county'),
(443500, '340124', '31.228413825483', '117.33587636592', '庐江县', 442334, 'county'),
(443501, '340181', '31.676058567558', '117.7717833762', '巢湖市', 442334, 'county'),
(443502, '340202', '31.351965582559', '118.38724548573', '镜湖区', 442335, 'county'),
(443503, '340203', '31.216676779902', '118.33596966824', '弋江区', 442335, 'county'),
(443504, '340207', '31.375481957255', '118.49397424134', '鸠江区', 442335, 'county'),
(443505, '340208', '31.212824987426', '118.3117984229', '三山区', 442335, 'county'),
(443506, '340221', '31.191698969307', '118.53246218925', '芜湖县', 442335, 'county'),
(443507, '340222', '31.12832958697', '118.2001179722', '繁昌县', 442335, 'county'),
(443508, '340223', '30.8959818627', '118.28821596372', '南陵县', 442335, 'county'),
(443509, '340225', '31.22249365658', '117.82005160307', '无为县', 442335, 'county'),
(443510, '340302', '32.926342521363', '117.47832568768', '龙子湖区', 442336, 'county'),
(443511, '340303', '32.881522954878', '117.35635619096', '蚌山区', 442336, 'county'),
(443512, '340304', '32.889696360476', '117.3055150635', '禹会区', 442336, 'county'),
(443513, '340311', '33.023815185908', '117.38818423314', '淮上区', 442336, 'county'),
(443514, '340321', '33.037130745984', '117.04208647136', '怀远县', 442336, 'county'),
(443515, '340322', '33.138465310137', '117.764210401', '五河县', 442336, 'county'),
(443516, '340323', '33.272840934373', '117.35403405942', '固镇县', 442336, 'county'),
(443517, '340402', '32.643535866152', '117.11713761331', '大通区', 442337, 'county'),
(443518, '340403', '32.564363767687', '117.01468721736', '田家庵区', 442337, 'county'),
(443519, '340404', '32.544400181652', '116.90877214688', '谢家集区', 442337, 'county'),
(443520, '340405', '32.652390199515', '116.82552132442', '八公山区', 442337, 'county'),
(443521, '340406', '32.800694621968', '116.86619300469', '潘集区', 442337, 'county'),
(443522, '340421', '32.791416300893', '116.58490534783', '凤台县', 442337, 'county'),
(443523, '340422', '32.287816164667', '116.77854729708', '寿县', 442337, 'county'),
(443524, '340503', '31.711627118315', '118.57834785585', '花山区', 442338, 'county'),
(443525, '340504', '31.659719310829', '118.55455812086', '雨山区', 442338, 'county'),
(443526, '340506', '31.56550080289', '118.85133588367', '博望区', 442338, 'county'),
(443527, '340521', '31.503024380618', '118.64667323993', '当涂县', 442338, 'county'),
(443528, '340522', '31.68852815888', '118.51588184662', '含山县', 442338, 'county'),
(443529, '340523', '31.757568623793', '118.29986391138', '和县', 442338, 'county'),
(443530, '340602', '34.113251414374', '116.95496714841', '杜集区', 442339, 'county'),
(443531, '340603', '33.988334722309', '116.72896156685', '相山区', 442339, 'county'),
(443532, '340604', '33.84405351094', '116.9081817805', '烈山区', 442339, 'county'),
(443533, '340621', '33.693204649044', '116.73689934705', '濉溪县', 442339, 'county'),
(443534, '340705', '30.943050294456', '117.83324857069', '铜官区', 442340, 'county'),
(443535, '340706', '30.944585477816', '117.95780890267', '义安区', 442340, 'county'),
(443536, '340711', '30.754631362428', '117.64155067342', '郊区', 442340, 'county'),
(443537, '340722', '30.863982478208', '117.41703591878', '枞阳县', 442340, 'county'),
(443538, '340802', '30.541457598958', '117.15254234871', '迎江区', 442341, 'county'),
(443539, '340803', '30.532487247564', '116.9809683319', '大观区', 442341, 'county'),
(443540, '340811', '30.614339999814', '117.05612964375', '宜秀区', 442341, 'county'),
(443541, '340822', '30.579024527459', '116.80352690196', '怀宁县', 442341, 'county'),
(443542, '340824', '30.758639275993', '116.55281551688', '潜山县', 442341, 'county'),
(443543, '340825', '30.50109966504', '116.18253924827', '太湖县', 442341, 'county'),
(443544, '340826', '30.108216635083', '116.25351835628', '宿松县', 442341, 'county'),
(443545, '340827', '30.242568216534', '116.68809225224', '望江县', 442341, 'county'),
(443546, '340828', '30.901821144678', '116.22007036688', '岳西县', 442341, 'county'),
(443547, '340881', '30.972567972107', '116.95355904596', '桐城市', 442341, 'county'),
(443548, '341002', '29.716534699341', '118.30963663452', '屯溪区', 442342, 'county'),
(443549, '341003', '30.27774589512', '118.07754612726', '黄山区', 442342, 'county'),
(443550, '341004', '29.902140398578', '118.27859128593', '徽州区', 442342, 'county'),
(443551, '341021', '29.871177014075', '118.57515564084', '歙县', 442342, 'county'),
(443552, '341022', '29.669120361013', '118.09308178818', '休宁县', 442342, 'county'),
(443553, '341023', '30.014778480875', '117.91075047481', '黟县', 442342, 'county'),
(443554, '341024', '29.873705688292', '117.60052812882', '祁门县', 442342, 'county'),
(443555, '341102', '32.338458080903', '118.33756892154', '琅琊区', 442343, 'county'),
(443556, '341103', '32.310209092866', '118.27082841537', '南谯区', 442343, 'county'),
(443557, '341122', '32.473711637442', '118.53562960741', '来安县', 442343, 'county'),
(443558, '341124', '32.069932749958', '118.10577829394', '全椒县', 442343, 'county'),
(443559, '341125', '32.473258599425', '117.66596452497', '定远县', 442343, 'county'),
(443560, '341126', '32.792214955967', '117.61147230278', '凤阳县', 442343, 'county'),
(443561, '341181', '32.721213784185', '118.9729126449', '天长市', 442343, 'county'),
(443562, '341182', '32.81183581812', '118.14072656734', '明光市', 442343, 'county'),
(443563, '341202', '32.867688563381', '115.72772731323', '颍州区', 442344, 'county'),
(443564, '341203', '32.941585109575', '116.03998540511', '颍东区', 442344, 'county'),
(443565, '341204', '33.073509996971', '115.73402623147', '颍泉区', 442344, 'county'),
(443566, '341221', '32.909769412643', '115.24846137013', '临泉县', 442344, 'county'),
(443567, '341222', '33.33774827164', '115.64875595615', '太和县', 442344, 'county'),
(443568, '341225', '32.655881179954', '115.65409851632', '阜南县', 442344, 'county'),
(443569, '341226', '32.662460220803', '116.26531418265', '颍上县', 442344, 'county'),
(443570, '341282', '33.226192689105', '115.39864296673', '界首市', 442344, 'county'),
(443571, '341302', '33.726032251705', '117.15907588963', '埇桥区', 442345, 'county'),
(443572, '341321', '34.454057242308', '116.42028227207', '砀山县', 442345, 'county'),
(443573, '341322', '34.208529641052', '116.81242175884', '萧县', 442345, 'county'),
(443574, '341323', '33.690737031018', '117.54312668944', '灵璧县', 442345, 'county'),
(443575, '341324', '33.544346537362', '117.89035897388', '泗县', 442345, 'county'),
(443576, '341502', '31.631258470539', '116.66194105885', '金安区', 442346, 'county'),
(443577, '341503', '31.753038540484', '116.30257286162', '裕安区', 442346, 'county'),
(443578, '341504', '31.755558355198', '116.50525268298', '叶集区', 442346, 'county'),
(443579, '341522', '32.201507325967', '116.17352091075', '霍邱县', 442346, 'county'),
(443580, '341523', '31.310003081421', '116.82855911938', '舒城县', 442346, 'county'),
(443581, '341524', '31.47909281966', '115.77931490356', '金寨县', 442346, 'county'),
(443582, '341525', '31.287055799576', '116.24667502387', '霍山县', 442346, 'county'),
(443583, '341602', '33.782924407833', '115.81281423257', '谯城区', 442347, 'county'),
(443584, '341621', '33.557949046136', '116.22355045352', '涡阳县', 442347, 'county'),
(443585, '341622', '33.22304396133', '116.5915120873', '蒙城县', 442347, 'county'),
(443586, '341623', '33.157375760354', '116.16627183049', '利辛县', 442347, 'county'),
(443587, '341702', '30.514085692989', '117.50847770852', '贵池区', 442348, 'county'),
(443588, '341721', '30.034069906871', '117.00682739944', '东至县', 442348, 'county'),
(443589, '341722', '30.199160540051', '117.53828189034', '石台县', 442348, 'county'),
(443590, '341723', '30.602013463857', '117.90815913595', '青阳县', 442348, 'county'),
(443591, '341802', '30.943631043255', '118.7978027295', '宣州区', 442349, 'county'),
(443592, '341821', '31.100123797933', '119.16790406676', '郎溪县', 442349, 'county'),
(443593, '341822', '30.893949749016', '119.36471289716', '广德县', 442349, 'county'),
(443594, '341823', '30.599286819492', '118.37604020629', '泾县', 442349, 'county'),
(443595, '341824', '30.162401081144', '118.6637768779', '绩溪县', 442349, 'county'),
(443596, '341825', '30.321833135921', '118.48289793271', '旌德县', 442349, 'county'),
(443597, '341881', '30.502936034943', '118.99702452598', '宁国市', 442349, 'county'),
(443598, '350102', '26.097871106548', '119.29063293961', '鼓楼区', 442350, 'county'),
(443599, '350103', '26.062153767548', '119.32406268487', '台江区', 442350, 'county'),
(443600, '350104', '26.019664381274', '119.33493643794', '仓山区', 442350, 'county'),
(443601, '350105', '26.082650321112', '119.51080249492', '马尾区', 442350, 'county'),
(443602, '350111', '26.221752079694', '119.31492287341', '晋安区', 442350, 'county'),
(443603, '350121', '26.182432187564', '119.12238323588', '闽侯县', 442350, 'county'),
(443604, '350122', '26.301591411273', '119.5683393031', '连江县', 442350, 'county'),
(443605, '350123', '26.506325719276', '119.46523419293', '罗源县', 442350, 'county'),
(443606, '350124', '26.212273389994', '118.77880310691', '闽清县', 442350, 'county'),
(443607, '350125', '25.857384057085', '118.79474057257', '永泰县', 442350, 'county'),
(443608, '350128', '25.537737674887', '119.76645322176', '平潭县', 442350, 'county'),
(443609, '350181', '25.638120577122', '119.37754701319', '福清市', 442350, 'county'),
(443610, '350182', '25.915538436925', '119.56271983507', '长乐市', 442350, 'county'),
(443611, '350203', '24.468728076403', '118.13453488213', '思明区', 442351, 'county'),
(443612, '350205', '24.53619033141', '117.98395590267', '海沧区', 442351, 'county'),
(443613, '350206', '24.521973931072', '118.14467575095', '湖里区', 442351, 'county'),
(443614, '350211', '24.640972798479', '118.02941167016', '集美区', 442351, 'county'),
(443615, '350212', '24.781704734783', '118.11468496447', '同安区', 442351, 'county'),
(443616, '350213', '24.675484915197', '118.28080317925', '翔安区', 442351, 'county'),
(443617, '350302', '25.433374872116', '118.95444257513', '城厢区', 442352, 'county'),
(443618, '350303', '25.604741724857', '119.07903889678', '涵江区', 442352, 'county'),
(443619, '350304', '25.427591842484', '119.07410333682', '荔城区', 442352, 'county'),
(443620, '350305', '25.276364535891', '119.13146589277', '秀屿区', 442352, 'county'),
(443621, '350322', '25.468258336396', '118.70462563885', '仙游县', 442352, 'county'),
(443622, '350402', '26.307448553348', '117.63050069122', '梅列区', 442353, 'county'),
(443623, '350403', '26.173967139255', '117.51689648494', '三元区', 442353, 'county'),
(443624, '350421', '26.418484134559', '117.21859881719', '明溪县', 442353, 'county'),
(443625, '350423', '26.099297668335', '116.9211934366', '清流县', 442353, 'county'),
(443626, '350424', '26.310073098848', '116.67811816013', '宁化县', 442353, 'county'),
(443627, '350425', '25.797449314745', '117.81799668394', '大田县', 442353, 'county'),
(443628, '350426', '26.150593850717', '118.25386835128', '尤溪县', 442353, 'county'),
(443629, '350427', '26.446505905088', '117.81884600477', '沙县', 442353, 'county'),
(443630, '350428', '26.732328679548', '117.40083967575', '将乐县', 442353, 'county'),
(443631, '350429', '26.865476881883', '117.12565958885', '泰宁县', 442353, 'county'),
(443632, '350430', '26.817741252365', '116.79307136804', '建宁县', 442353, 'county'),
(443633, '350481', '25.919433151382', '117.32853545664', '永安市', 442353, 'county'),
(443634, '350502', '24.905744690408', '118.56845525017', '鲤城区', 442354, 'county'),
(443635, '350503', '24.936275095413', '118.6074317381', '丰泽区', 442354, 'county'),
(443636, '350504', '25.133414113301', '118.64345333988', '洛江区', 442354, 'county'),
(443637, '350505', '25.173479375703', '118.81901718056', '泉港区', 442354, 'county'),
(443638, '350521', '24.991871443315', '118.80947288339', '惠安县', 442354, 'county'),
(443639, '350524', '25.125684138245', '117.91163244343', '安溪县', 442354, 'county'),
(443640, '350525', '25.395598523493', '118.14097079846', '永春县', 442354, 'county'),
(443641, '350526', '25.674049363102', '118.2580388856', '德化县', 442354, 'county'),
(443642, '350527', '24.453685081793', '118.3797724059', '金门县', 442354, 'county'),
(443643, '350581', '24.744894247253', '118.69248092208', '石狮市', 442354, 'county'),
(443644, '350582', '24.729638297698', '118.55865054225', '晋江市', 442354, 'county'),
(443645, '350583', '25.017972545094', '118.38898065958', '南安市', 442354, 'county'),
(443646, '350602', '24.575089413411', '117.63336610614', '芗城区', 442355, 'county'),
(443647, '350603', '24.537177249549', '117.70403687855', '龙文区', 442355, 'county'),
(443648, '350622', '23.984924590871', '117.3381105564', '云霄县', 442355, 'county'),
(443649, '350623', '24.134610348852', '117.69145555574', '漳浦县', 442355, 'county'),
(443650, '350624', '23.87404072539', '117.13294195697', '诏安县', 442355, 'county'),
(443651, '350625', '24.744593711082', '117.81298738987', '长泰县', 442355, 'county'),
(443652, '350626', '23.691110309815', '117.42541646767', '东山县', 442355, 'county'),
(443653, '350627', '24.668805586956', '117.29305472699', '南靖县', 442355, 'county'),
(443654, '350628', '24.324490604831', '117.20072092242', '平和县', 442355, 'county'),
(443655, '350629', '24.918688094608', '117.54380454982', '华安县', 442355, 'county'),
(443656, '350681', '24.398816824823', '117.80759027663', '龙海市', 442355, 'county'),
(443657, '350702', '26.590155096909', '118.25473662436', '延平区', 442356, 'county'),
(443658, '350703', '27.42298490861', '118.09503169529', '建阳区', 442356, 'county'),
(443659, '350721', '26.908712277835', '117.87368081029', '顺昌县', 442356, 'county'),
(443660, '350722', '27.945164125785', '118.52429759856', '浦城县', 442356, 'county'),
(443661, '350723', '27.655597572386', '117.3555359708', '光泽县', 442356, 'county'),
(443662, '350724', '27.610704490355', '118.76568912522', '松溪县', 442356, 'county'),
(443663, '350725', '27.324781791328', '118.97167122766', '政和县', 442356, 'county'),
(443664, '350781', '27.235197069611', '117.48057222854', '邵武市', 442356, 'county'),
(443665, '350782', '27.748135171112', '118.01154264632', '武夷山市', 442356, 'county'),
(443666, '350783', '27.044913662799', '118.48514716959', '建瓯市', 442356, 'county'),
(443667, '350802', '25.22220637939', '117.08632241393', '新罗区', 442357, 'county'),
(443668, '350803', '24.733216812374', '116.75552020871', '永定区', 442357, 'county'),
(443669, '350821', '25.696958495476', '116.37188399052', '长汀县', 442357, 'county'),
(443670, '350823', '25.126526144211', '116.56866906668', '上杭县', 442357, 'county'),
(443671, '350824', '25.139021186901', '116.13591657582', '武平县', 442357, 'county'),
(443672, '350825', '25.60417681052', '116.82144796403', '连城县', 442357, 'county'),
(443673, '350881', '25.379998346458', '117.45172162006', '漳平市', 442357, 'county'),
(443674, '350902', '26.763865425402', '119.45455949068', '蕉城区', 442358, 'county'),
(443675, '350921', '26.868876533651', '119.99055111407', '霞浦县', 442358, 'county'),
(443676, '350922', '26.618899035408', '118.87954004139', '古田县', 442358, 'county'),
(443677, '350923', '26.921561586231', '118.98929146416', '屏南县', 442358, 'county'),
(443678, '350924', '27.426229516207', '119.5055198374', '寿宁县', 442358, 'county'),
(443679, '350925', '27.094312780593', '119.31332559174', '周宁县', 442358, 'county'),
(443680, '350926', '27.207067709716', '119.88752229998', '柘荣县', 442358, 'county'),
(443681, '350981', '27.055896714799', '119.65627713286', '福安市', 442358, 'county'),
(443682, '350982', '27.224828701234', '120.19830746412', '福鼎市', 442358, 'county'),
(443683, '360102', '28.692375145425', '115.91014826387', '东湖区', 442359, 'county'),
(443684, '360103', '28.657325885604', '115.89894765179', '西湖区', 442359, 'county'),
(443685, '360104', '28.636601455215', '115.9219541542', '青云谱区', 442359, 'county'),
(443686, '360105', '28.800556903151', '115.75048047817', '湾里区', 442359, 'county'),
(443687, '360111', '28.700848503487', '115.93090639742', '青山湖区', 442359, 'county'),
(443688, '360112', '28.762510375641', '115.97766347318', '新建区', 442359, 'county'),
(443689, '360121', '28.620772037399', '116.07126087416', '南昌县', 442359, 'county'),
(443690, '360123', '28.836411855907', '115.59520240179', '安义县', 442359, 'county'),
(443691, '360124', '28.441758032984', '116.3174577813', '进贤县', 442359, 'county'),
(443692, '360202', '29.272154837188', '117.1861998641', '昌江区', 442360, 'county'),
(443693, '360203', '29.303230556708', '117.23411943253', '珠山区', 442360, 'county'),
(443694, '360222', '29.556555537931', '117.30897851315', '浮梁县', 442360, 'county'),
(443695, '360281', '28.969928213838', '117.27327879748', '乐平市', 442360, 'county'),
(443696, '360302', '27.645394991779', '113.87886885384', '安源区', 442361, 'county'),
(443697, '360313', '27.53370963141', '113.73981816772', '湘东区', 442361, 'county'),
(443698, '360321', '27.223445481289', '113.95977686775', '莲花县', 442361, 'county'),
(443699, '360322', '27.832260223294', '113.86780638258', '上栗县', 442361, 'county'),
(443700, '360323', '27.578022564845', '114.07000665468', '芦溪县', 442361, 'county'),
(443701, '360402', '29.719639526122', '115.99984802155', '濂溪区', 442362, 'county'),
(443702, '360403', '29.717848894949', '116.00276787378', '浔阳区', 442362, 'county'),
(443703, '360421', '29.640229926977', '115.84203547109', '九江县', 442362, 'county'),
(443704, '360423', '29.263844028424', '115.02315949078', '武宁县', 442362, 'county'),
(443705, '360424', '29.000021311275', '114.4461918569', '修水县', 442362, 'county'),
(443706, '360425', '29.141310623242', '115.74247538366', '永修县', 442362, 'county'),
(443707, '360426', '29.401728483728', '115.63408426446', '德安县', 442362, 'county'),
(443708, '360428', '29.356214912479', '116.34204769578', '都昌县', 442362, 'county'),
(443709, '360429', '29.66806050769', '116.29256118736', '湖口县', 442362, 'county'),
(443710, '360430', '29.834597412665', '116.62933206226', '彭泽县', 442362, 'county'),
(443711, '360481', '29.628544625483', '115.45968602847', '瑞昌市', 442362, 'county'),
(443712, '360482', '29.236083846739', '115.820204477', '共青城市', 442362, 'county'),
(443713, '360483', '29.347769476561', '115.98274999338', '庐山市', 442362, 'county'),
(443714, '360502', '27.850578117027', '115.00785062298', '渝水区', 442363, 'county'),
(443715, '360521', '27.844993335525', '114.67816306735', '分宜县', 442363, 'county'),
(443716, '360602', '28.2472053807', '117.05770601694', '月湖区', 442364, 'county'),
(443717, '360622', '28.321070494465', '116.92157395441', '余江县', 442364, 'county'),
(443718, '360681', '28.190604458955', '117.19787036817', '贵溪市', 442364, 'county'),
(443719, '360702', '25.838710922212', '114.93736527747', '章贡区', 442365, 'county'),
(443720, '360703', '25.857651685208', '114.70979725341', '南康区', 442365, 'county'),
(443721, '360721', '25.90202543961', '115.07258602937', '赣县', 442365, 'county'),
(443722, '360722', '25.286018342725', '114.98179982408', '信丰县', 442365, 'county'),
(443723, '360723', '25.44847167429', '114.36649000169', '大余县', 442365, 'county'),
(443724, '360724', '25.939253373895', '114.402605282', '上犹县', 442365, 'county'),
(443725, '360725', '25.679632268061', '114.19933700414', '崇义县', 442365, 'county'),
(443726, '360726', '25.238854104103', '115.39661257033', '安远县', 442365, 'county'),
(443727, '360727', '24.772706198589', '114.73182493834', '龙南县', 442365, 'county'),
(443728, '360728', '24.824160396247', '115.09388033666', '定南县', 442365, 'county'),
(443729, '360729', '24.853232801668', '114.52234265684', '全南县', 442365, 'county'),
(443730, '360730', '26.590232461651', '116.01211627388', '宁都县', 442365, 'county'),
(443731, '360731', '25.936771660723', '115.50889266701', '于都县', 442365, 'county'),
(443732, '360732', '26.425200601326', '115.44650725812', '兴国县', 442365, 'county'),
(443733, '360733', '25.505756516053', '115.76515116427', '会昌县', 442365, 'county'),
(443734, '360734', '24.905101066212', '115.66514812504', '寻乌县', 442365, 'county'),
(443735, '360735', '26.305565307867', '116.37232152813', '石城县', 442365, 'county'),
(443736, '360781', '25.92183136176', '115.98586699622', '瑞金市', 442365, 'county'),
(443737, '360802', '27.160925346239', '114.96043668241', '吉州区', 442366, 'county'),
(443738, '360803', '26.859217102443', '115.2661672372', '青原区', 442366, 'county'),
(443739, '360821', '27.144039043235', '114.75127781381', '吉安县', 442366, 'county'),
(443740, '360822', '27.19746539341', '115.2546383758', '吉水县', 442366, 'county'),
(443741, '360823', '27.589281248366', '115.2144367887', '峡江县', 442366, 'county'),
(443742, '360824', '27.73857991921', '115.50683935381', '新干县', 442366, 'county'),
(443743, '360825', '27.097544566001', '115.59283108743', '永丰县', 442366, 'county'),
(443744, '360826', '26.744020657469', '114.90935609718', '泰和县', 442366, 'county'),
(443745, '360827', '26.344268552348', '114.37058927285', '遂川县', 442366, 'county'),
(443746, '360828', '26.444632606143', '114.82501601739', '万安县', 442366, 'county'),
(443747, '360829', '27.361338381942', '114.45559104078', '安福县', 442366, 'county'),
(443748, '360830', '26.973089209998', '114.18844710958', '永新县', 442366, 'county'),
(443749, '360881', '26.633149538063', '114.12543918071', '井冈山市', 442366, 'county'),
(443750, '360902', '27.839383216183', '114.29035792969', '袁州区', 442367, 'county'),
(443751, '360921', '28.714689488559', '115.18007761153', '奉新县', 442367, 'county'),
(443752, '360922', '28.209464188761', '114.33614285625', '万载县', 442367, 'county'),
(443753, '360923', '28.199053404375', '114.86095818884', '上高县', 442367, 'county'),
(443754, '360924', '28.454955048263', '114.77436594756', '宜丰县', 442367, 'county'),
(443755, '360925', '28.946083546903', '115.23770855063', '靖安县', 442367, 'county'),
(443756, '360926', '28.615515536873', '114.37013409066', '铜鼓县', 442367, 'county'),
(443757, '360981', '28.11151587847', '115.8234035155', '丰城市', 442367, 'county'),
(443758, '360982', '28.002513613496', '115.42134592031', '樟树市', 442367, 'county'),
(443759, '360983', '28.365231807956', '115.30448193078', '高安市', 442367, 'county'),
(443760, '361002', '27.924731514346', '116.36297445174', '临川区', 442368, 'county'),
(443761, '361021', '27.518966176458', '116.68173230038', '南城县', 442368, 'county'),
(443762, '361022', '27.261522440114', '116.93171710122', '黎川县', 442368, 'county'),
(443763, '361023', '27.114896155721', '116.50036153348', '南丰县', 442368, 'county'),
(443764, '361024', '27.714537232977', '116.0663364594', '崇仁县', 442368, 'county'),
(443765, '361025', '27.372428601937', '115.8433235072', '乐安县', 442368, 'county'),
(443766, '361026', '27.393066587894', '116.25124162127', '宜黄县', 442368, 'county'),
(443767, '361027', '27.931491559982', '116.7577119166', '金溪县', 442368, 'county'),
(443768, '361028', '27.745228535574', '117.03576739532', '资溪县', 442368, 'county'),
(443769, '361029', '28.221297983216', '116.6196227029', '东乡县', 442368, 'county'),
(443770, '361030', '26.761885358703', '116.36311652024', '广昌县', 442368, 'county'),
(443771, '361102', '28.497223477761', '118.05057821628', '信州区', 442369, 'county'),
(443772, '361103', '28.344342110797', '118.2644206827', '广丰区', 442369, 'county'),
(443773, '361121', '28.405679688374', '117.94436679858', '上饶县', 442369, 'county'),
(443774, '361123', '28.759339988568', '118.16891675165', '玉山县', 442369, 'county'),
(443775, '361124', '28.109822141358', '117.71346110788', '铅山县', 442369, 'county'),
(443776, '361125', '28.513847494908', '117.64519741314', '横峰县', 442369, 'county'),
(443777, '361126', '28.452236031909', '117.41664984164', '弋阳县', 442369, 'county'),
(443778, '361127', '28.682775556852', '116.62132713524', '余干县', 442369, 'county'),
(443779, '361128', '29.243055725231', '116.78769263036', '鄱阳县', 442369, 'county'),
(443780, '361129', '28.703236407929', '117.01441274225', '万年县', 442369, 'county'),
(443781, '361130', '29.327231721148', '117.78748504184', '婺源县', 442369, 'county'),
(443782, '361181', '28.940751536947', '117.75325925179', '德兴市', 442369, 'county'),
(443783, '370102', '36.659338577102', '117.10158579685', '历下区', 442370, 'county'),
(443784, '370103', '36.584025608593', '116.97943490154', '市中区', 442370, 'county'),
(443785, '370104', '36.682531368156', '116.89119924147', '槐荫区', 442370, 'county'),
(443786, '370105', '36.778077694991', '116.98315714712', '天桥区', 442370, 'county'),
(443787, '370112', '36.612688160201', '117.1908183999', '历城区', 442370, 'county'),
(443788, '370113', '36.428570220761', '116.8035523233', '长清区', 442370, 'county'),
(443789, '370124', '36.203933440502', '116.42250204992', '平阴县', 442370, 'county'),
(443790, '370125', '37.032805727164', '117.15002119645', '济阳县', 442370, 'county'),
(443791, '370126', '37.32594723869', '117.20871442383', '商河县', 442370, 'county'),
(443792, '370181', '36.744883031601', '117.47934537885', '章丘市', 442370, 'county'),
(443793, '370202', '36.072517005321', '120.37618412944', '市南区', 442371, 'county'),
(443794, '370203', '36.100057507009', '120.37849501736', '市北区', 442371, 'county'),
(443795, '370211', '36.005019406172', '120.16954109898', '黄岛区', 442371, 'county'),
(443796, '370212', '36.195587169547', '120.58490643507', '崂山区', 442371, 'county'),
(443797, '370213', '36.192897252321', '120.43114552866', '李沧区', 442371, 'county'),
(443798, '370214', '36.284246909785', '120.34632618533', '城阳区', 442371, 'county'),
(443799, '370281', '36.248031458483', '119.95942122689', '胶州市', 442371, 'county'),
(443800, '370282', '36.487908601599', '120.52110561991', '即墨市', 442371, 'county'),
(443801, '370283', '36.788550047135', '119.95106201677', '平度市', 442371, 'county'),
(443802, '370285', '36.863636936232', '120.44283105064', '莱西市', 442371, 'county'),
(443803, '370302', '36.58546320707', '118.02018132413', '淄川区', 442372, 'county'),
(443804, '370303', '36.816096523468', '118.07715128035', '张店区', 442372, 'county'),
(443805, '370304', '36.425426903116', '117.96555278725', '博山区', 442372, 'county'),
(443806, '370305', '36.854244021624', '118.30069695082', '临淄区', 442372, 'county'),
(443807, '370306', '36.771218733055', '117.87510840602', '周村区', 442372, 'county'),
(443808, '370321', '36.996290174374', '118.0343674972', '桓台县', 442372, 'county'),
(443809, '370322', '37.171377591723', '117.82824157972', '高青县', 442372, 'county'),
(443810, '370323', '36.135641879756', '118.20397204172', '沂源县', 442372, 'county'),
(443811, '370402', '34.870585491515', '117.60608168197', '市中区', 442373, 'county'),
(443812, '370403', '34.796330499958', '117.35850706735', '薛城区', 442373, 'county'),
(443813, '370404', '34.716097133125', '117.60355623943', '峄城区', 442373, 'county'),
(443814, '370405', '34.587964202085', '117.63824339976', '台儿庄区', 442373, 'county'),
(443815, '370406', '35.093150446743', '117.48403628836', '山亭区', 442373, 'county'),
(443816, '370481', '35.065790871862', '117.1476161953', '滕州市', 442373, 'county'),
(443817, '370502', '37.408666288041', '118.61264305188', '东营区', 442374, 'county'),
(443818, '370503', '37.969499930854', '118.62001162992', '河口区', 442374, 'county'),
(443819, '370505', '37.708139143783', '118.80543474393', '垦利区', 442374, 'county'),
(443820, '370522', '37.655326413663', '118.40033677253', '利津县', 442374, 'county'),
(443821, '370523', '37.162071119184', '118.53856931196', '广饶县', 442374, 'county'),
(443822, '370602', '37.520933396965', '121.36415635471', '芝罘区', 442375, 'county'),
(443823, '370611', '37.4810742238', '121.20346125307', '福山区', 442375, 'county'),
(443824, '370612', '37.272445856085', '121.56924005155', '牟平区', 442375, 'county'),
(443825, '370613', '37.407476077054', '121.45153473051', '莱山区', 442375, 'county'),
(443826, '370634', '38.07745626718', '120.75599624581', '长岛县', 442375, 'county'),
(443827, '370681', '37.610401281677', '120.5227995064', '龙口市', 442375, 'county'),
(443828, '370682', '36.905533169255', '120.75134338103', '莱阳市', 442375, 'county'),
(443829, '370683', '37.190401374398', '120.00134352944', '莱州市', 442375, 'county'),
(443830, '370684', '37.661160410834', '120.86269428184', '蓬莱市', 442375, 'county'),
(443831, '370685', '37.344145870524', '120.40051706225', '招远市', 442375, 'county'),
(443832, '370686', '37.311748207049', '120.9015556194', '栖霞市', 442375, 'county'),
(443833, '370687', '36.861587988875', '121.11361421128', '海阳市', 442375, 'county'),
(443834, '370702', '36.70198215587', '119.03430547775', '潍城区', 442376, 'county'),
(443835, '370703', '36.908365760671', '119.17913537725', '寒亭区', 442376, 'county'),
(443836, '370704', '36.625674339008', '119.25846542234', '坊子区', 442376, 'county'),
(443837, '370705', '36.691227364273', '119.19697218249', '奎文区', 442376, 'county'),
(443838, '370724', '36.365388839543', '118.55825637254', '临朐县', 442376, 'county'),
(443839, '370725', '36.535532052698', '118.91391393621', '昌乐县', 442376, 'county'),
(443840, '370781', '36.680584425831', '118.47018708791', '青州市', 442376, 'county'),
(443841, '370782', '36.016657533378', '119.41616966208', '诸城市', 442376, 'county'),
(443842, '370783', '37.029891849506', '118.85253352124', '寿光市', 442376, 'county'),
(443843, '370784', '36.335046466579', '119.15599227984', '安丘市', 442376, 'county'),
(443844, '370785', '36.387317992315', '119.70251223974', '高密市', 442376, 'county'),
(443845, '370786', '36.834234247985', '119.44991748822', '昌邑市', 442376, 'county'),
(443846, '370811', '35.380134737043', '116.57219935158', '任城区', 442377, 'county'),
(443847, '370812', '35.564429514451', '116.75256014243', '兖州区', 442377, 'county'),
(443848, '370826', '34.892715408071', '116.99240869227', '微山县', 442377, 'county'),
(443849, '370827', '35.01985835793', '116.57843727094', '鱼台县', 442377, 'county'),
(443850, '370828', '35.051246098924', '116.31512496802', '金乡县', 442377, 'county'),
(443851, '370829', '35.434199027209', '116.30729105899', '嘉祥县', 442377, 'county'),
(443852, '370830', '35.715701266031', '116.50644367868', '汶上县', 442377, 'county'),
(443853, '370831', '35.640740666213', '117.34526415391', '泗水县', 442377, 'county'),
(443854, '370832', '35.801606249716', '116.12480570072', '梁山县', 442377, 'county'),
(443855, '370881', '35.615760566258', '117.03178985128', '曲阜市', 442377, 'county'),
(443856, '370883', '35.354042540878', '117.08958175113', '邹城市', 442377, 'county'),
(443857, '370902', '36.215457241311', '117.18390217966', '泰山区', 442378, 'county'),
(443858, '370911', '36.148101133087', '117.19048736581', '岱岳区', 442378, 'county'),
(443859, '370921', '35.833600319907', '116.93293882598', '宁阳县', 442378, 'county'),
(443860, '370923', '35.97516090244', '116.34295320558', '东平县', 442378, 'county'),
(443861, '370982', '35.89581023511', '117.61301622046', '新泰市', 442378, 'county'),
(443862, '370983', '36.112514344701', '116.74476246765', '肥城市', 442378, 'county'),
(443863, '371002', '37.399343698592', '122.15207450216', '环翠区', 442379, 'county'),
(443864, '371003', '37.16608344728', '121.96829072766', '文登区', 442379, 'county'),
(443865, '371082', '37.128686091876', '122.40692581532', '荣成市', 442379, 'county'),
(443866, '371083', '36.976575050291', '121.52978797795', '乳山市', 442379, 'county'),
(443867, '371102', '35.469377334235', '119.37785169728', '东港区', 442380, 'county'),
(443868, '371103', '35.292714155339', '119.25182522442', '岚山区', 442380, 'county'),
(443869, '371121', '35.744382733588', '119.2494328324', '五莲县', 442380, 'county'),
(443870, '371122', '35.655874955573', '118.8935850849', '莒县', 442380, 'county'),
(443871, '371202', '36.313394584932', '117.6459130158', '莱城区', 442381, 'county'),
(443872, '371203', '36.092835887233', '117.8275371813', '钢城区', 442381, 'county'),
(443873, '371302', '35.174844704086', '118.31224292902', '兰山区', 442382, 'county'),
(443874, '371311', '34.964343085469', '118.29727935276', '罗庄区', 442382, 'county'),
(443875, '371312', '35.127030975379', '118.51731091285', '河东区', 442382, 'county'),
(443876, '371321', '35.536723374853', '118.41758556843', '沂南县', 442382, 'county'),
(443877, '371322', '34.649855053512', '118.32443065841', '郯城县', 442382, 'county'),
(443878, '371323', '35.914368629366', '118.60935780958', '沂水县', 442382, 'county'),
(443879, '371324', '34.862619866599', '118.00750944174', '兰陵县', 442382, 'county'),
(443880, '371325', '35.254970793112', '117.98583765075', '费县', 442382, 'county'),
(443881, '371326', '35.434249996001', '117.68244768554', '平邑县', 442382, 'county'),
(443882, '371327', '35.213123220035', '118.89007890268', '莒南县', 442382, 'county'),
(443883, '371328', '35.747440083102', '118.03674237099', '蒙阴县', 442382, 'county'),
(443884, '371329', '34.885484018739', '118.65944529359', '临沭县', 442382, 'county'),
(443885, '371402', '37.45743710416', '116.33291247583', '德城区', 442383, 'county'),
(443886, '371403', '37.418030354096', '116.67557519942', '陵城区', 442383, 'county'),
(443887, '371422', '37.68562160185', '116.81455550432', '宁津县', 442383, 'county'),
(443888, '371423', '37.801823529258', '117.46273738393', '庆云县', 442383, 'county'),
(443889, '371424', '37.235892912121', '116.89959541702', '临邑县', 442383, 'county'),
(443890, '371425', '36.723454326503', '116.67825351242', '齐河县', 442383, 'county'),
(443891, '371426', '37.156617614138', '116.43007889018', '平原县', 442383, 'county'),
(443892, '371427', '37.016688548156', '116.03732249608', '夏津县', 442383, 'county'),
(443893, '371428', '37.243982507441', '116.09122537249', '武城县', 442383, 'county'),
(443894, '371481', '37.674416911054', '117.14555333466', '乐陵市', 442383, 'county'),
(443895, '371482', '36.919142889593', '116.58133068117', '禹城市', 442383, 'county'),
(443896, '371502', '36.455829587246', '115.90770556753', '东昌府区', 442384, 'county'),
(443897, '371521', '36.146774001697', '115.87350298472', '阳谷县', 442384, 'county'),
(443898, '371522', '36.139121538984', '115.55267289485', '莘县', 442384, 'county'),
(443899, '371523', '36.588519734091', '116.18017382824', '茌平县', 442384, 'county'),
(443900, '371524', '36.331642489915', '116.2831984139', '东阿县', 442384, 'county'),
(443901, '371525', '36.53635700997', '115.54083712129', '冠县', 442384, 'county'),
(443902, '371526', '36.839764457085', '116.25743002174', '高唐县', 442384, 'county'),
(443903, '371581', '36.782069473113', '115.78260175173', '临清市', 442384, 'county'),
(443904, '371602', '37.424890835984', '117.98121111677', '滨城区', 442385, 'county'),
(443905, '371603', '37.868312497909', '118.05636772417', '沾化区', 442385, 'county'),
(443906, '371621', '37.375971318454', '117.57898363784', '惠民县', 442385, 'county'),
(443907, '371622', '37.605500456412', '117.57342951076', '阳信县', 442385, 'county'),
(443908, '371623', '37.942568300077', '117.79778189995', '无棣县', 442385, 'county'),
(443909, '371625', '37.19135384581', '118.22571531705', '博兴县', 442385, 'county'),
(443910, '371626', '36.956593309429', '117.67080618616', '邹平县', 442385, 'county'),
(443911, '371702', '35.283536562407', '115.47002526505', '牡丹区', 442386, 'county'),
(443912, '371703', '35.111855206745', '115.57403571958', '定陶区', 442386, 'county'),
(443913, '371721', '34.827952767182', '115.55360067628', '曹县', 442386, 'county'),
(443914, '371722', '34.738238141223', '116.122984618', '单县', 442386, 'county'),
(443915, '371723', '34.989110950657', '115.94498857738', '成武县', 442386, 'county'),
(443916, '371724', '35.279400360462', '116.04113122185', '巨野县', 442386, 'county'),
(443917, '371725', '35.612979519727', '115.89463235246', '郓城县', 442386, 'county'),
(443918, '371726', '35.555043149647', '115.55287125102', '鄄城县', 442386, 'county'),
(443919, '371728', '35.182435455789', '115.07411464426', '东明县', 442386, 'county'),
(443920, '410102', '34.779474293205', '113.55728142479', '中原区', 442387, 'county'),
(443921, '410103', '34.75661006414', '113.64964384986', '二七区', 442387, 'county'),
(443922, '410104', '34.70900380778', '113.72186105524', '管城回族区', 442387, 'county'),
(443923, '410105', '34.797406405145', '113.70801125038', '金水区', 442387, 'county'),
(443924, '410106', '34.822088918243', '113.29818225705', '上街区', 442387, 'county'),
(443925, '410108', '34.869446814666', '113.62834116351', '惠济区', 442387, 'county'),
(443926, '410122', '34.720319012422', '114.01122240275', '中牟县', 442387, 'county'),
(443927, '410181', '34.703798883243', '113.03959002892', '巩义市', 442387, 'county'),
(443928, '410182', '34.806179937519', '113.35180180957', '荥阳市', 442387, 'county'),
(443929, '410183', '34.514074899467', '113.43985443365', '新密市', 442387, 'county'),
(443930, '410184', '34.459442752589', '113.73611501497', '新郑市', 442387, 'county'),
(443931, '410185', '34.418362166819', '113.04174933248', '登封市', 442387, 'county'),
(443932, '410202', '34.860572766851', '114.34098849918', '龙亭区', 442388, 'county'),
(443933, '410203', '34.81777146999', '114.42852744048', '顺河回族区', 442388, 'county'),
(443934, '410204', '34.797982546084', '114.34190563407', '鼓楼区', 442388, 'county'),
(443935, '410205', '34.75102886185', '114.38560958232', '禹王台区', 442388, 'county'),
(443936, '410211', '34.860572766851', '114.34098849918', '金明区', 442388, 'county'),
(443937, '410212', '34.725946945916', '114.43805957404', '祥符区', 442388, 'county'),
(443938, '410221', '34.505963464038', '114.76878210877', '杞县', 442388, 'county'),
(443939, '410222', '34.441630948349', '114.50219933719', '通许县', 442388, 'county'),
(443940, '410223', '34.388437240132', '114.16103722407', '尉氏县', 442388, 'county'),
(443941, '410225', '34.879764140336', '114.98029307097', '兰考县', 442388, 'county'),
(443942, '410302', '34.704033141562', '112.45917255752', '老城区', 442389, 'county'),
(443943, '410303', '34.689693743302', '112.4071257244', '西工区', 442389, 'county'),
(443944, '410304', '34.702931706602', '112.50509438434', '瀍河回族区', 442389, 'county'),
(443945, '410305', '34.671667591915', '112.39075320818', '涧西区', 442389, 'county'),
(443946, '410306', '34.905378745091', '112.58976455586', '吉利区', 442389, 'county'),
(443947, '410311', '34.638792103903', '112.46709264771', '洛龙区', 442389, 'county'),
(443948, '410322', '34.831148181123', '112.47699634585', '孟津县', 442389, 'county'),
(443949, '410323', '34.837606946675', '112.12774350044', '新安县', 442389, 'county'),
(443950, '410324', '33.912392483904', '111.61701356274', '栾川县', 442389, 'county'),
(443951, '410325', '34.010600110067', '112.04951135131', '嵩县', 442389, 'county'),
(443952, '410326', '34.06296675028', '112.4355439591', '汝阳县', 442389, 'county'),
(443953, '410327', '34.486036200799', '112.04046789874', '宜阳县', 442389, 'county'),
(443954, '410328', '34.345208388992', '111.50679130206', '洛宁县', 442389, 'county'),
(443955, '410329', '34.407088177948', '112.46887702474', '伊川县', 442389, 'county'),
(443956, '410381', '34.630801858346', '112.73482167429', '偃师市', 442389, 'county'),
(443957, '410402', '33.771546437308', '113.20808222559', '新华区', 442390, 'county'),
(443958, '410403', '33.769107814671', '113.36538845598', '卫东区', 442390, 'county'),
(443959, '410404', '33.892093587751', '112.89469073779', '石龙区', 442390, 'county'),
(443960, '410411', '33.71234144492', '113.27818922149', '湛河区', 442390, 'county'),
(443961, '410421', '33.915497347446', '113.03577147499', '宝丰县', 442390, 'county'),
(443962, '410422', '33.551013481912', '113.3506762416', '叶县', 442390, 'county'),
(443963, '410423', '33.748697388191', '112.74030934124', '鲁山县', 442390, 'county'),
(443964, '410425', '34.005498968871', '113.23328182561', '郏县', 442390, 'county'),
(443965, '410481', '33.289605497055', '113.52599604654', '舞钢市', 442390, 'county'),
(443966, '410482', '34.162777545453', '112.8127174803', '汝州市', 442390, 'county'),
(443967, '410502', '36.034147665845', '114.41852222061', '文峰区', 442391, 'county'),
(443968, '410503', '36.141695896219', '114.39143588406', '北关区', 442391, 'county'),
(443969, '410505', '36.135573231517', '114.29712997501', '殷都区', 442391, 'county'),
(443970, '410506', '36.056024537571', '114.25660364057', '龙安区', 442391, 'county'),
(443971, '410522', '36.125134517065', '114.31712430011', '安阳县', 442391, 'county'),
(443972, '410523', '35.907982338855', '114.46206281377', '汤阴县', 442391, 'county'),
(443973, '410526', '35.471733779112', '114.67364721954', '滑县', 442391, 'county'),
(443974, '410527', '35.906569063676', '114.82334356443', '内黄县', 442391, 'county'),
(443975, '410581', '36.016561032268', '113.86108354948', '林州市', 442391, 'county'),
(443976, '410602', '35.973345969386', '114.09845417079', '鹤山区', 442392, 'county'),
(443977, '410603', '35.927453768113', '114.25302901346', '山城区', 442392, 'county'),
(443978, '410611', '35.812418921012', '114.19951434914', '淇滨区', 442392, 'county'),
(443979, '410621', '35.686206113273', '114.46718581475', '浚县', 442392, 'county'),
(443980, '410622', '35.667571747251', '114.16903374394', '淇县', 442392, 'county'),
(443981, '410702', '35.286150085139', '113.91461891258', '红旗区', 442393, 'county'),
(443982, '410703', '35.294831576876', '113.86463773299', '卫滨区', 442393, 'county'),
(443983, '410704', '35.399318437608', '113.86418902939', '凤泉区', 442393, 'county'),
(443984, '410711', '35.338890167673', '113.89672215157', '牧野区', 442393, 'county'),
(443985, '410721', '35.220522070112', '113.84824573704', '新乡县', 442393, 'county'),
(443986, '410724', '35.203480558843', '113.65196887023', '获嘉县', 442393, 'county'),
(443987, '410725', '35.029035610429', '113.95316420007', '原阳县', 442393, 'county'),
(443988, '410726', '35.279607523872', '114.23135664543', '延津县', 442393, 'county'),
(443989, '410727', '35.040384096253', '114.48767812651', '封丘县', 442393, 'county'),
(443990, '410728', '35.218127613796', '114.76690326799', '长垣县', 442393, 'county'),
(443991, '410781', '35.499572079266', '114.07811240389', '卫辉市', 442393, 'county'),
(443992, '410782', '35.543594465927', '113.68789198968', '辉县市', 442393, 'county'),
(443993, '410802', '35.241712363011', '113.23080396516', '解放区', 442394, 'county'),
(443994, '410803', '35.257023702543', '113.16153562728', '中站区', 442394, 'county'),
(443995, '410804', '35.304171016133', '113.36732116029', '马村区', 442394, 'county'),
(443996, '410811', '35.241160124283', '113.27635056977', '山阳区', 442394, 'county'),
(443997, '410821', '35.309677964198', '113.36352820836', '修武县', 442394, 'county'),
(443998, '410822', '35.186007002113', '113.07507819608', '博爱县', 442394, 'county'),
(443999, '410823', '35.057332269363', '113.39993452059', '武陟县', 442394, 'county'),
(444000, '410825', '34.950259905072', '113.05529612566', '温县', 442394, 'county'),
(444001, '410882', '35.133826023222', '112.8883049259', '沁阳市', 442394, 'county'),
(444002, '410883', '34.925884390003', '112.76969911916', '孟州市', 442394, 'county'),
(444003, '410902', '35.77193370823', '115.04809659609', '华龙区', 442395, 'county'),
(444004, '410922', '35.924381925846', '115.1612013425', '清丰县', 442395, 'county'),
(444005, '410923', '36.097697402715', '115.24982310475', '南乐县', 442395, 'county'),
(444006, '410926', '35.801404731968', '115.53840075342', '范县', 442395, 'county'),
(444007, '410927', '35.966389126572', '115.88573804863', '台前县', 442395, 'county'),
(444008, '410928', '35.59228702617', '115.15660204659', '濮阳县', 442395, 'county'),
(444009, '411002', '34.043477065508', '113.82531644192', '魏都区', 442396, 'county'),
(444010, '411023', '34.048516339751', '113.83526207686', '许昌县', 442396, 'county'),
(444011, '411024', '34.01192963976', '114.20240879522', '鄢陵县', 442396, 'county'),
(444012, '411025', '33.86190518971', '113.56898289597', '襄城县', 442396, 'county'),
(444013, '411081', '34.200307558026', '113.39269360872', '禹州市', 442396, 'county'),
(444014, '411082', '34.236601226737', '113.85556820652', '长葛市', 442396, 'county'),
(444015, '411102', '33.53475547247', '113.92360106938', '源汇区', 442397, 'county'),
(444016, '411103', '33.670703809923', '113.94136182694', '郾城区', 442397, 'county'),
(444017, '411104', '33.57798961468', '114.18514173047', '召陵区', 442397, 'county'),
(444018, '411121', '33.549301454759', '113.68005525753', '舞阳县', 442397, 'county'),
(444019, '411122', '33.844425731931', '113.96389863435', '临颍县', 442397, 'county'),
(444020, '411202', '34.771777672947', '111.28129514586', '湖滨区', 442398, 'county'),
(444021, '411203', '34.642257128616', '111.38347360282', '陕州区', 442398, 'county'),
(444022, '411221', '34.839691429224', '111.80253536998', '渑池县', 442398, 'county'),
(444023, '411224', '33.973393506457', '110.99472361928', '卢氏县', 442398, 'county'),
(444024, '411281', '34.749524796841', '111.90609266704', '义马市', 442398, 'county'),
(444025, '411282', '34.437104010525', '110.77973742779', '灵宝市', 442398, 'county'),
(444026, '411302', '32.934703186447', '112.61390774771', '宛城区', 442399, 'county'),
(444027, '411303', '33.009838704626', '112.48426735075', '卧龙区', 442399, 'county'),
(444028, '411321', '33.472841576965', '112.39366620514', '南召县', 442399, 'county'),
(444029, '411322', '33.29995432977', '113.01682220295', '方城县', 442399, 'county'),
(444030, '411323', '33.48692481474', '111.43898978054', '西峡县', 442399, 'county'),
(444031, '411324', '33.070817074221', '112.19328453943', '镇平县', 442399, 'county'),
(444032, '411325', '33.224377176241', '111.8474050987', '内乡县', 442399, 'county'),
(444033, '411326', '32.989722669393', '111.44539628251', '淅川县', 442399, 'county'),
(444034, '411327', '32.982431382713', '112.99852720955', '社旗县', 442399, 'county'),
(444035, '411328', '32.619993292449', '112.85911827542', '唐河县', 442399, 'county'),
(444036, '411329', '32.553440600793', '112.41599071451', '新野县', 442399, 'county'),
(444037, '411330', '32.495650299965', '113.43416900109', '桐柏县', 442399, 'county'),
(444038, '411381', '32.684649552173', '112.0568605764', '邓州市', 442399, 'county'),
(444039, '411402', '34.5030395946', '115.63773066554', '梁园区', 442400, 'county'),
(444040, '411403', '34.286754693787', '115.58978387844', '睢阳区', 442400, 'county'),
(444041, '411421', '34.696116524323', '115.17841405829', '民权县', 442400, 'county'),
(444042, '411422', '34.39975982873', '115.04300110058', '睢县', 442400, 'county'),
(444043, '411423', '34.454601801008', '115.29840053317', '宁陵县', 442400, 'county'),
(444044, '411424', '34.111651823091', '115.30904185846', '柘城县', 442400, 'county'),
(444045, '411425', '34.36907192525', '115.9142248589', '虞城县', 442400, 'county'),
(444046, '411426', '34.223680706067', '116.15745373108', '夏邑县', 442400, 'county'),
(444047, '411481', '33.972013062908', '116.33077515791', '永城市', 442400, 'county'),
(444048, '411502', '32.031339669892', '113.96277662182', '浉河区', 442401, 'county'),
(444049, '411503', '32.307840062297', '114.1390859663', '平桥区', 442401, 'county'),
(444050, '411521', '32.031230299697', '114.44356295731', '罗山县', 442401, 'county'),
(444051, '411522', '31.941431722351', '114.84316193505', '光山县', 442401, 'county'),
(444052, '411523', '31.646279005794', '114.85908905243', '新县', 442401, 'county'),
(444053, '411524', '31.766261672209', '115.37524581828', '商城县', 442401, 'county'),
(444054, '411525', '32.13694390485', '115.70974321125', '固始县', 442401, 'county'),
(444055, '411526', '32.132798426222', '115.16440991805', '潢川县', 442401, 'county'),
(444056, '411527', '32.44657354908', '115.32456065214', '淮滨县', 442401, 'county'),
(444057, '411528', '32.410808174844', '114.87168181062', '息县', 442401, 'county'),
(444058, '411602', '33.630875553438', '114.65795015653', '川汇区', 442402, 'county'),
(444059, '411621', '34.100655472765', '114.43732658093', '扶沟县', 442402, 'county');
INSERT INTO `region` (`id`, `code`, `lat`, `lng`, `name`, `parent_id`, `by_type`) VALUES
(444060, '411622', '33.793632327288', '114.47808721231', '西华县', 442402, 'county'),
(444061, '411623', '33.52093272236', '114.5595768694', '商水县', 442402, 'county'),
(444062, '411624', '33.295149932293', '115.17871821127', '沈丘县', 442402, 'county'),
(444063, '411625', '33.641500072188', '115.30129735228', '郸城县', 442402, 'county'),
(444064, '411626', '33.709946652498', '114.90201820622', '淮阳县', 442402, 'county'),
(444065, '411627', '34.097096248874', '114.85570075514', '太康县', 442402, 'county'),
(444066, '411628', '33.894050509383', '115.38398333433', '鹿邑县', 442402, 'county'),
(444067, '411681', '33.274470322798', '114.89338047633', '项城市', 442402, 'county'),
(444068, '411702', '32.968356527361', '114.00828960502', '驿城区', 442403, 'county'),
(444069, '411721', '33.37154892996', '113.92283839684', '西平县', 442403, 'county'),
(444070, '411722', '33.301221213377', '114.40923857452', '上蔡县', 442403, 'county'),
(444071, '411723', '32.992143963522', '114.64744862019', '平舆县', 442403, 'county'),
(444072, '411724', '32.546931233463', '114.49796073761', '正阳县', 442403, 'county'),
(444073, '411725', '32.711951228899', '113.96358973796', '确山县', 442403, 'county'),
(444074, '411726', '32.883863636522', '113.44717429859', '泌阳县', 442403, 'county'),
(444075, '411727', '32.921968466052', '114.3257758188', '汝南县', 442403, 'county'),
(444076, '411728', '33.167855168478', '113.90248496569', '遂平县', 442403, 'county'),
(444077, '411729', '32.783574270118', '114.94939334965', '新蔡县', 442403, 'county'),
(444078, '419001', '35.093893094508', '112.40383005708', '济源市', 442404, 'county'),
(444079, '420102', '30.656090889378', '114.33286813952', '江岸区', 442405, 'county'),
(444080, '420103', '30.610951375707', '114.26638369307', '江汉区', 442405, 'county'),
(444081, '420104', '30.603890608484', '114.21975676824', '硚口区', 442405, 'county'),
(444082, '420105', '30.547265210116', '114.21759191464', '汉阳区', 442405, 'county'),
(444083, '420106', '30.564860292785', '114.35362228468', '武昌区', 442405, 'county'),
(444084, '420107', '30.633205056354', '114.44449542245', '青山区', 442405, 'county'),
(444085, '420111', '30.54362328175', '114.43389643664', '洪山区', 442405, 'county'),
(444086, '420112', '30.69815326481', '114.08715512184', '东西湖区', 442405, 'county'),
(444087, '420113', '30.287139798861', '113.96273175623', '汉南区', 442405, 'county'),
(444088, '420114', '30.456183515878', '113.97206459286', '蔡甸区', 442405, 'county'),
(444089, '420115', '30.252484112134', '114.36708160048', '江夏区', 442405, 'county'),
(444090, '420116', '30.985285897674', '114.36464422879', '黄陂区', 442405, 'county'),
(444091, '420117', '30.803887901859', '114.76208468205', '新洲区', 442405, 'county'),
(444092, '420202', '30.233764966969', '115.0731593966', '黄石港区', 442406, 'county'),
(444093, '420203', '30.184485507434', '115.1322665517', '西塞山区', 442406, 'county'),
(444094, '420204', '30.195818128952', '114.99298679763', '下陆区', 442406, 'county'),
(444095, '420205', '30.218698027629', '114.90300946351', '铁山区', 442406, 'county'),
(444096, '420222', '29.828087088129', '115.14049262648', '阳新县', 442406, 'county'),
(444097, '420281', '30.072895848258', '114.84614160381', '大冶市', 442406, 'county'),
(444098, '420302', '32.605601870191', '110.78595269258', '茅箭区', 442407, 'county'),
(444099, '420303', '32.663839857981', '110.7174012025', '张湾区', 442407, 'county'),
(444100, '420304', '32.848666872', '110.70709242813', '郧阳区', 442407, 'county'),
(444101, '420322', '33.04842762997', '110.15015123042', '郧西县', 442407, 'county'),
(444102, '420323', '32.240141680909', '110.07273955599', '竹山县', 442407, 'county'),
(444103, '420324', '32.0377375614', '109.7912365606', '竹溪县', 442407, 'county'),
(444104, '420325', '31.896989987694', '110.71456120963', '房县', 442407, 'county'),
(444105, '420381', '32.567476506858', '111.19322791899', '丹江口市', 442407, 'county'),
(444106, '420502', '30.740828168194', '111.31370556274', '西陵区', 442408, 'county'),
(444107, '420503', '30.678659340635', '111.380922081', '伍家岗区', 442408, 'county'),
(444108, '420504', '30.625384685781', '111.21627903018', '点军区', 442408, 'county'),
(444109, '420505', '30.551849254685', '111.45521482125', '猇亭区', 442408, 'county'),
(444110, '420506', '30.979970536584', '111.31064943757', '夷陵区', 442408, 'county'),
(444111, '420525', '31.176854341724', '111.58511301877', '远安县', 442408, 'county'),
(444112, '420526', '31.319349537746', '110.82440565254', '兴山县', 442408, 'county'),
(444113, '420527', '30.903334635073', '110.68599344932', '秭归县', 442408, 'county'),
(444114, '420528', '30.482854820654', '110.85396847661', '长阳土家族自治县', 442408, 'county'),
(444115, '420529', '30.173164959818', '110.70999872376', '五峰土家族自治县', 442408, 'county'),
(444116, '420581', '30.294919731409', '111.37553355505', '宜都市', 442408, 'county'),
(444117, '420582', '30.825538036113', '111.84271236769', '当阳市', 442408, 'county'),
(444118, '420583', '30.451766635038', '111.72856708021', '枝江市', 442408, 'county'),
(444119, '420602', '31.935360283633', '112.01708254994', '襄城区', 442409, 'county'),
(444120, '420606', '32.153953344009', '111.92852759276', '樊城区', 442409, 'county'),
(444121, '420607', '32.161267821333', '112.1615782359', '襄州区', 442409, 'county'),
(444122, '420624', '31.643279800381', '111.76462860893', '南漳县', 442409, 'county'),
(444123, '420625', '32.173451559392', '111.49595776173', '谷城县', 442409, 'county'),
(444124, '420626', '31.719672647836', '111.20990495958', '保康县', 442409, 'county'),
(444125, '420682', '32.434165591299', '111.76583021988', '老河口市', 442409, 'county'),
(444126, '420683', '32.092510578007', '112.77260678733', '枣阳市', 442409, 'county'),
(444127, '420684', '31.673335169944', '112.37274539501', '宜城市', 442409, 'county'),
(444128, '420702', '30.172732100474', '114.65002920477', '梁子湖区', 442410, 'county'),
(444129, '420703', '30.473067617235', '114.7014718376', '华容区', 442410, 'county'),
(444130, '420704', '30.320603111112', '114.90101603375', '鄂城区', 442410, 'county'),
(444131, '420802', '31.129834655672', '112.08731072725', '东宝区', 442411, 'county'),
(444132, '420804', '30.932878257728', '112.19392270314', '掇刀区', 442411, 'county'),
(444133, '420821', '31.085751895572', '113.1122609366', '京山县', 442411, 'county'),
(444134, '420822', '30.664549510743', '112.39598267744', '沙洋县', 442411, 'county'),
(444135, '420881', '31.244981073964', '112.58482623119', '钟祥市', 442411, 'county'),
(444136, '420902', '30.9446167023', '114.01614199013', '孝南区', 442412, 'county'),
(444137, '420921', '31.239758867241', '114.03487209446', '孝昌县', 442412, 'county'),
(444138, '420922', '31.57825524841', '114.31029950549', '大悟县', 442412, 'county'),
(444139, '420923', '31.004978516713', '113.77818589474', '云梦县', 442412, 'county'),
(444140, '420981', '30.925709286687', '113.55644020385', '应城市', 442412, 'county'),
(444141, '420982', '31.304354863067', '113.63338728419', '安陆市', 442412, 'county'),
(444142, '420984', '30.622039213976', '113.68167835943', '汉川市', 442412, 'county'),
(444143, '421002', '30.325722718965', '112.42410926804', '沙市区', 442413, 'county'),
(444144, '421003', '30.396103360853', '112.09985718065', '荆州区', 442413, 'county'),
(444145, '421022', '29.957130184896', '112.15361758468', '公安县', 442413, 'county'),
(444146, '421023', '29.848933249111', '113.0019564425', '监利县', 442413, 'county'),
(444147, '421024', '30.101502949806', '112.47370114506', '江陵县', 442413, 'county'),
(444148, '421081', '29.742222414324', '112.51435972656', '石首市', 442413, 'county'),
(444149, '421083', '29.996772000415', '113.53891465228', '洪湖市', 442413, 'county'),
(444150, '421087', '30.105224314496', '111.69620454012', '松滋市', 442413, 'county'),
(444151, '421102', '30.518802478736', '114.94956939748', '黄州区', 442414, 'county'),
(444152, '421121', '30.723706101243', '115.01408720557', '团风县', 442414, 'county'),
(444153, '421122', '31.29012275323', '114.62811879353', '红安县', 442414, 'county'),
(444154, '421123', '30.932372750757', '115.48102224121', '罗田县', 442414, 'county'),
(444155, '421124', '30.872992046545', '115.77430241642', '英山县', 442414, 'county'),
(444156, '421125', '30.507400278808', '115.27625105135', '浠水县', 442414, 'county'),
(444157, '421126', '30.328717011744', '115.60077083531', '蕲春县', 442414, 'county'),
(444158, '421127', '29.998875662753', '115.94188335896', '黄梅县', 442414, 'county'),
(444159, '421181', '31.217943121813', '115.08971464087', '麻城市', 442414, 'county'),
(444160, '421182', '30.01561431062', '115.62583375392', '武穴市', 442414, 'county'),
(444161, '421202', '29.854650359958', '114.39186727646', '咸安区', 442415, 'county'),
(444162, '421221', '30.013807145954', '113.9671389967', '嘉鱼县', 442415, 'county'),
(444163, '421222', '29.229496067967', '113.85326552547', '通城县', 442415, 'county'),
(444164, '421223', '29.46178869538', '114.06793496135', '崇阳县', 442415, 'county'),
(444165, '421224', '29.557670344417', '114.61524564759', '通山县', 442415, 'county'),
(444166, '421281', '29.742560741036', '113.88916760653', '赤壁市', 442415, 'county'),
(444167, '421303', '31.607981069768', '113.46768060015', '曾都区', 442416, 'county'),
(444168, '421321', '31.89292220952', '113.26226604576', '随县', 442416, 'county'),
(444169, '421381', '31.68232502305', '113.81261910549', '广水市', 442416, 'county'),
(444170, '422801', '30.463309797502', '109.15843052724', '恩施市', 442417, 'county'),
(444171, '422802', '30.42403337354', '108.75827737341', '利川市', 442417, 'county'),
(444172, '422822', '30.578575985623', '109.93959920981', '建始县', 442417, 'county'),
(444173, '422823', '30.827452858588', '110.30061735767', '巴东县', 442417, 'county'),
(444174, '422825', '30.044021286424', '109.45211696118', '宣恩县', 442417, 'county'),
(444175, '422826', '29.64880608709', '109.11475831378', '咸丰县', 442417, 'county'),
(444176, '422827', '29.425663227736', '109.2467141194', '来凤县', 442417, 'county'),
(444177, '422828', '29.959848783933', '110.2232960585', '鹤峰县', 442417, 'county'),
(444178, '429004', '30.293966004922', '113.38744819358', '仙桃市', 442418, 'county'),
(444179, '429005', '30.343115792601', '112.76876801686', '潜江市', 442418, 'county'),
(444180, '429006', '30.649047356422', '113.12623048765', '天门市', 442418, 'county'),
(444181, '429021', '31.595767599083', '110.48723070015', '神农架林区', 442418, 'county'),
(444182, '430102', '28.203810552355', '113.02096885649', '芙蓉区', 442419, 'county'),
(444183, '430103', '28.144470861087', '112.99619520748', '天心区', 442419, 'county'),
(444184, '430104', '28.202706634928', '112.90869935253', '岳麓区', 442419, 'county'),
(444185, '430105', '28.260219056422', '113.02472997183', '开福区', 442419, 'county'),
(444186, '430111', '28.146444362118', '113.02020071545', '雨花区', 442419, 'county'),
(444187, '430112', '28.277901873199', '112.84853518023', '望城区', 442419, 'county'),
(444188, '430121', '28.322758625178', '113.22494603976', '长沙县', 442419, 'county'),
(444189, '430124', '28.131212630242', '112.36046547366', '宁乡县', 442419, 'county'),
(444190, '430181', '28.234472053802', '113.72198528266', '浏阳市', 442419, 'county'),
(444191, '430202', '27.907228809861', '113.2125259488', '荷塘区', 442420, 'county'),
(444192, '430203', '27.822072525123', '113.16975977942', '芦淞区', 442420, 'county'),
(444193, '430204', '27.941584145955', '113.16351107646', '石峰区', 442420, 'county'),
(444194, '430211', '27.77777212283', '113.06800898383', '天元区', 442420, 'county'),
(444195, '430221', '27.535936240494', '113.15334777322', '株洲县', 442420, 'county'),
(444196, '430223', '27.172267738735', '113.48783136261', '攸县', 442420, 'county'),
(444197, '430224', '26.806729309467', '113.6524812712', '茶陵县', 442420, 'county'),
(444198, '430225', '26.382712485446', '113.85053602814', '炎陵县', 442420, 'county'),
(444199, '430281', '27.662278573878', '113.47062497305', '醴陵市', 442420, 'county'),
(444200, '430302', '27.871843464684', '112.89447989496', '雨湖区', 442421, 'county'),
(444201, '430304', '27.927747363022', '113.02348797463', '岳塘区', 442421, 'county'),
(444202, '430321', '27.66922281069', '112.78880535021', '湘潭县', 442421, 'county'),
(444203, '430381', '27.77667974388', '112.35516854771', '湘乡市', 442421, 'county'),
(444204, '430382', '27.927332779842', '112.53309503972', '韶山市', 442421, 'county'),
(444205, '430405', '26.882224641246', '112.68848999752', '珠晖区', 442422, 'county'),
(444206, '430406', '26.852862113311', '112.60790741194', '雁峰区', 442422, 'county'),
(444207, '430407', '26.958880199218', '112.60248766531', '石鼓区', 442422, 'county'),
(444208, '430408', '26.886508776556', '112.5550474327', '蒸湘区', 442422, 'county'),
(444209, '430412', '27.259358565856', '112.70876706188', '南岳区', 442422, 'county'),
(444210, '430421', '27.109626113862', '112.35157940823', '衡阳县', 442422, 'county'),
(444211, '430422', '26.759844895044', '112.64851378595', '衡南县', 442422, 'county'),
(444212, '430423', '27.281912376828', '112.71963002036', '衡山县', 442422, 'county'),
(444213, '430424', '27.085080215257', '113.02900158518', '衡东县', 442422, 'county'),
(444214, '430426', '26.806848291159', '111.96160590404', '祁东县', 442422, 'county'),
(444215, '430481', '26.423992793417', '112.9215515181', '耒阳市', 442422, 'county'),
(444216, '430482', '26.365629347663', '112.43550437188', '常宁市', 442422, 'county'),
(444217, '430502', '27.248222019138', '111.54534736863', '双清区', 442423, 'county'),
(444218, '430503', '27.15673687542', '111.48663933069', '大祥区', 442423, 'county'),
(444219, '430511', '27.250338344113', '111.42227870533', '北塔区', 442423, 'county'),
(444220, '430521', '27.193653689477', '111.85672034136', '邵东县', 442423, 'county'),
(444221, '430522', '27.431198790186', '111.47127474176', '新邵县', 442423, 'county'),
(444222, '430523', '26.984976684914', '111.33237232124', '邵阳县', 442423, 'county'),
(444223, '430524', '27.351830793846', '110.97332605607', '隆回县', 442423, 'county'),
(444224, '430525', '27.103195627285', '110.5997390171', '洞口县', 442423, 'county'),
(444225, '430527', '26.714433355354', '110.20598518573', '绥宁县', 442423, 'county'),
(444226, '430528', '26.548580699981', '110.92469767578', '新宁县', 442423, 'county'),
(444227, '430529', '26.325514573582', '110.32530265472', '城步苗族自治县', 442423, 'county'),
(444228, '430581', '26.786578072622', '110.74581533919', '武冈市', 442423, 'county'),
(444229, '430602', '29.367743455935', '113.15536982346', '岳阳楼区', 442424, 'county'),
(444230, '430603', '29.526210726593', '113.35377424951', '云溪区', 442424, 'county'),
(444231, '430611', '29.461963175999', '112.82353001902', '君山区', 442424, 'county'),
(444232, '430621', '29.178498531192', '113.23752715256', '岳阳县', 442424, 'county'),
(444233, '430623', '29.493395834151', '112.65100948964', '华容县', 442424, 'county'),
(444234, '430624', '28.713089704815', '112.8053736108', '湘阴县', 442424, 'county'),
(444235, '430626', '28.762202955269', '113.72084646866', '平江县', 442424, 'county'),
(444236, '430681', '28.801958087001', '113.12502676793', '汨罗市', 442424, 'county'),
(444237, '430682', '29.496146011064', '113.51974938156', '临湘市', 442424, 'county'),
(444238, '430702', '28.996871241883', '111.69744989482', '武陵区', 442425, 'county'),
(444239, '430703', '28.99524298628', '111.74779560677', '鼎城区', 442425, 'county'),
(444240, '430721', '29.448996008449', '112.16243681004', '安乡县', 442425, 'county'),
(444241, '430722', '28.864800229907', '112.04431060341', '汉寿县', 442425, 'county'),
(444242, '430723', '29.750168137633', '111.70770306732', '澧县', 442425, 'county'),
(444243, '430724', '29.486256878123', '111.62542246954', '临澧县', 442425, 'county'),
(444244, '430725', '28.917817681602', '111.27070654871', '桃源县', 442425, 'county'),
(444245, '430726', '29.801742760215', '111.04428685665', '石门县', 442425, 'county'),
(444246, '430781', '29.474442427089', '111.90685042221', '津市市', 442425, 'county'),
(444247, '430802', '29.08853881247', '110.50100729665', '永定区', 442426, 'county'),
(444248, '430811', '29.35720050871', '110.48849578734', '武陵源区', 442426, 'county'),
(444249, '430821', '29.397692771035', '110.9362003537', '慈利县', 442426, 'county'),
(444250, '430822', '29.567691591611', '110.18733600686', '桑植县', 442426, 'county'),
(444251, '430902', '28.694069428897', '112.34312135279', '资阳区', 442427, 'county'),
(444252, '430903', '28.456919373898', '112.46132362565', '赫山区', 442427, 'county'),
(444253, '430921', '29.242714345729', '112.4444992186', '南县', 442427, 'county'),
(444254, '430922', '28.464142378681', '111.99046415183', '桃江县', 442427, 'county'),
(444255, '430923', '28.286580101198', '111.39078157302', '安化县', 442427, 'county'),
(444256, '430981', '28.977186044013', '112.56494222194', '沅江市', 442427, 'county'),
(444257, '431002', '25.679158376796', '112.88447564616', '北湖区', 442428, 'county'),
(444258, '431003', '25.773515156215', '113.05100154527', '苏仙区', 442428, 'county'),
(444259, '431021', '25.893490018268', '112.60810756507', '桂阳县', 442428, 'county'),
(444260, '431022', '25.275886554538', '112.93344735219', '宜章县', 442428, 'county'),
(444261, '431023', '26.216491688814', '113.19839325538', '永兴县', 442428, 'county'),
(444262, '431024', '25.637287293573', '112.41435261569', '嘉禾县', 442428, 'county'),
(444263, '431025', '25.34399717934', '112.56804110466', '临武县', 442428, 'county'),
(444264, '431026', '25.555136753643', '113.67767672321', '汝城县', 442428, 'county'),
(444265, '431027', '25.98664473819', '113.90640126356', '桂东县', 442428, 'county'),
(444266, '431028', '26.580785897827', '113.3656988724', '安仁县', 442428, 'county'),
(444267, '431081', '25.937184405929', '113.4685220784', '资兴市', 442428, 'county'),
(444268, '431102', '26.102311299933', '111.56391866724', '零陵区', 442429, 'county'),
(444269, '431103', '26.560381677834', '111.6215855691', '冷水滩区', 442429, 'county'),
(444270, '431121', '26.460846002508', '111.97259355447', '祁阳县', 442429, 'county'),
(444271, '431122', '26.495587621014', '111.3428094117', '东安县', 442429, 'county'),
(444272, '431123', '25.914932997744', '111.71629417694', '双牌县', 442429, 'county'),
(444273, '431124', '25.499396959983', '111.60204209765', '道县', 442429, 'county'),
(444274, '431125', '25.199988241379', '111.25388667817', '江永县', 442429, 'county'),
(444275, '431126', '25.653839564231', '111.98806316398', '宁远县', 442429, 'county'),
(444276, '431127', '25.319502616064', '112.1963927883', '蓝山县', 442429, 'county'),
(444277, '431128', '25.890527389354', '112.23480727989', '新田县', 442429, 'county'),
(444278, '431129', '24.977642122796', '111.75249569192', '江华瑶族自治县', 442429, 'county'),
(444279, '431202', '27.612024135064', '109.94553900894', '鹤城区', 442430, 'county'),
(444280, '431221', '27.52093513528', '110.16536245669', '中方县', 442430, 'county'),
(444281, '431222', '28.576604506247', '110.60117801132', '沅陵县', 442430, 'county'),
(444282, '431223', '27.895902086692', '110.27300890779', '辰溪县', 442430, 'county'),
(444283, '431224', '27.83590994386', '110.65858111747', '溆浦县', 442430, 'county'),
(444284, '431225', '26.914136373938', '109.8099454141', '会同县', 442430, 'county'),
(444285, '431226', '27.791375726707', '109.72917909558', '麻阳苗族自治县', 442430, 'county'),
(444286, '431227', '27.234509109112', '109.1687410593', '新晃侗族自治县', 442430, 'county'),
(444287, '431228', '27.402510416382', '109.61110485123', '芷江侗族自治县', 442430, 'county'),
(444288, '431229', '26.550430723333', '109.59083349436', '靖州苗族侗族自治县', 442430, 'county'),
(444289, '431230', '26.215115332486', '109.7446605455', '通道侗族自治县', 442430, 'county'),
(444290, '431281', '27.239105321481', '110.08719342097', '洪江市', 442430, 'county'),
(444291, '431302', '27.766945342839', '112.00461910688', '娄星区', 442431, 'county'),
(444292, '431321', '27.465564445594', '112.18792282367', '双峰县', 442431, 'county'),
(444293, '431322', '27.873272599439', '111.24684472009', '新化县', 442431, 'county'),
(444294, '431381', '27.684914712556', '111.49394197482', '冷水江市', 442431, 'county'),
(444295, '431382', '27.743727453351', '111.79458146238', '涟源市', 442431, 'county'),
(444296, '433101', '28.297553747059', '109.90596604398', '吉首市', 442432, 'county'),
(444297, '433122', '28.004620053587', '109.83368299284', '泸溪县', 442432, 'county'),
(444298, '433123', '28.128806804716', '109.627609014', '凤凰县', 442432, 'county'),
(444299, '433124', '28.573833156579', '109.45712787573', '花垣县', 442432, 'county'),
(444300, '433125', '28.653191600514', '109.69701784684', '保靖县', 442432, 'county'),
(444301, '433126', '28.603594321825', '110.00814905055', '古丈县', 442432, 'county'),
(444302, '433127', '28.753308819921', '109.95878299439', '永顺县', 442432, 'county'),
(444303, '433130', '29.458093683151', '109.44489996147', '龙山县', 442432, 'county'),
(444304, '440103', '23.093666203644', '113.23442278391', '荔湾区', 442433, 'county'),
(444305, '440104', '23.139277859339', '113.28783302666', '越秀区', 442433, 'county'),
(444306, '440105', '23.087629228789', '113.33384126613', '海珠区', 442433, 'county'),
(444307, '440106', '23.166129265425', '113.38564289133', '天河区', 442433, 'county'),
(444308, '440111', '23.294514083014', '113.33130628641', '白云区', 442433, 'county'),
(444309, '440112', '23.108711814239', '113.49288457425', '黄埔区', 442433, 'county'),
(444310, '440113', '22.934590795798', '113.41679952965', '番禺区', 442433, 'county'),
(444311, '440114', '23.446660997141', '113.22017551212', '花都区', 442433, 'county'),
(444312, '440115', '22.729893804121', '113.58022392527', '南沙区', 442433, 'county'),
(444313, '440117', '23.705203224537', '113.69870948609', '从化区', 442433, 'county'),
(444314, '440118', '23.332025887963', '113.77002334194', '增城区', 442433, 'county'),
(444315, '440203', '24.708193228698', '113.37960618165', '武江区', 442434, 'county'),
(444316, '440204', '24.919162254549', '113.57745027759', '浈江区', 442434, 'county'),
(444317, '440205', '24.651897914445', '113.64217762719', '曲江区', 442434, 'county'),
(444318, '440222', '24.852706291962', '114.11540446493', '始兴县', 442434, 'county'),
(444319, '440224', '25.148465646013', '113.78547373726', '仁化县', 442434, 'county'),
(444320, '440229', '24.426734740638', '114.03042755919', '翁源县', 442434, 'county'),
(444321, '440232', '24.812051773842', '113.17577755468', '乳源瑶族自治县', 442434, 'county'),
(444322, '440233', '24.070091776392', '114.14177489194', '新丰县', 442434, 'county'),
(444323, '440281', '25.244441914003', '113.24695611826', '乐昌市', 442434, 'county'),
(444324, '440282', '25.189905400508', '114.38658277052', '南雄市', 442434, 'county'),
(444325, '440303', '22.581934478848', '114.15639529324', '罗湖区', 442435, 'county'),
(444326, '440304', '22.551730572433', '114.05559275391', '福田区', 442435, 'county'),
(444327, '440305', '22.558887751083', '113.95072266574', '南山区', 442435, 'county'),
(444328, '440306', '22.707432793082', '113.93001313569', '宝安区', 442435, 'county'),
(444329, '440307', '22.657462286882', '114.34769572771', '龙岗区', 442435, 'county'),
(444330, '440308', '22.606981337589', '114.27848287567', '盐田区', 442435, 'county'),
(444331, '440402', '22.26559983535', '113.53373098039', '香洲区', 442436, 'county'),
(444332, '440403', '22.216636753124', '113.24798167517', '斗门区', 442436, 'county'),
(444333, '440404', '22.04721492726', '113.41758987066', '金湾区', 442436, 'county'),
(444334, '440507', '23.408849226222', '116.75934746239', '龙湖区', 442437, 'county'),
(444335, '440511', '23.399887892781', '116.65179359137', '金平区', 442437, 'county'),
(444336, '440512', '23.282442837577', '116.71136293853', '濠江区', 442437, 'county'),
(444337, '440513', '23.347253898106', '116.48544753544', '潮阳区', 442437, 'county'),
(444338, '440514', '23.181395091106', '116.41405584392', '潮南区', 442437, 'county'),
(444339, '440515', '23.532996549632', '116.8148077949', '澄海区', 442437, 'county'),
(444340, '440523', '23.439131822072', '117.0704048247', '南澳县', 442437, 'county'),
(444341, '440604', '23.004210165991', '113.07042319497', '禅城区', 442438, 'county'),
(444342, '440605', '23.07826538747', '113.04138132585', '南海区', 442438, 'county'),
(444343, '440606', '22.848510084787', '113.18702987688', '顺德区', 442438, 'county'),
(444344, '440607', '23.294580845555', '112.90467719327', '三水区', 442438, 'county'),
(444345, '440608', '22.824522683444', '112.68325830314', '高明区', 442438, 'county'),
(444346, '440703', '22.660132832793', '113.06077007598', '蓬江区', 442439, 'county'),
(444347, '440704', '22.554846678035', '113.13537054201', '江海区', 442439, 'county'),
(444348, '440705', '22.38821506964', '113.0347511329', '新会区', 442439, 'county'),
(444349, '440781', '22.034638545952', '112.7159079377', '台山市', 442439, 'county'),
(444350, '440783', '22.374200664984', '112.54804114164', '开平市', 442439, 'county'),
(444351, '440784', '22.675317373533', '112.80161841196', '鹤山市', 442439, 'county'),
(444352, '440785', '22.240985208711', '112.28646122263', '恩平市', 442439, 'county'),
(444353, '440802', '21.287667885107', '110.37972297262', '赤坎区', 442440, 'county'),
(444354, '440803', '21.2048473973', '110.38519600028', '霞山区', 442440, 'county'),
(444355, '440804', '21.283819774873', '110.51272613161', '坡头区', 442440, 'county'),
(444356, '440811', '21.094100364979', '110.33802177707', '麻章区', 442440, 'county'),
(444357, '440823', '21.270307383787', '110.0398954377', '遂溪县', 442440, 'county'),
(444358, '440825', '20.429967572815', '110.25784725094', '徐闻县', 442440, 'county'),
(444359, '440881', '21.645265225554', '110.14171137206', '廉江市', 442440, 'county'),
(444360, '440882', '20.796584309564', '110.01263612715', '雷州市', 442440, 'county'),
(444361, '440883', '21.441681041112', '110.70818705195', '吴川市', 442440, 'county'),
(444362, '440902', '21.676115917529', '110.86860979348', '茂南区', 442441, 'county'),
(444363, '440904', '21.66821689615', '111.15968915137', '电白区', 442441, 'county'),
(444364, '440981', '22.035521645119', '110.97560541086', '高州市', 442441, 'county'),
(444365, '440982', '21.845482259109', '110.53959146838', '化州市', 442441, 'county'),
(444366, '440983', '22.431974274304', '111.12542886235', '信宜市', 442441, 'county'),
(444367, '441202', '23.103323258382', '112.47779387429', '端州区', 442442, 'county'),
(444368, '441203', '23.208968105809', '112.62524912783', '鼎湖区', 442442, 'county'),
(444369, '441204', '23.110684686218', '112.51216619847', '高要区', 442442, 'county'),
(444370, '441223', '23.677207015329', '112.44331648004', '广宁县', 442442, 'county'),
(444371, '441224', '23.974272952942', '112.18024001499', '怀集县', 442442, 'county'),
(444372, '441225', '23.561267405148', '111.72348651223', '封开县', 442442, 'county'),
(444373, '441226', '23.276366860198', '111.98726848872', '德庆县', 442442, 'county'),
(444374, '441284', '23.431443755334', '112.68755812366', '四会市', 442442, 'county'),
(444375, '441302', '23.278292790243', '114.7325947848', '惠城区', 442443, 'county'),
(444376, '441303', '22.788789691764', '114.47977020249', '惠阳区', 442443, 'county'),
(444377, '441322', '23.352582051478', '114.28847482844', '博罗县', 442443, 'county'),
(444378, '441323', '23.049117499162', '114.95551769006', '惠东县', 442443, 'county'),
(444379, '441324', '23.666408023307', '114.13724281901', '龙门县', 442443, 'county'),
(444380, '441402', '24.290750354901', '116.11595202018', '梅江区', 442444, 'county'),
(444381, '441403', '24.3647824353', '116.171027251', '梅县区', 442444, 'county'),
(444382, '441422', '24.347933570693', '116.66412418408', '大埔县', 442444, 'county'),
(444383, '441423', '23.916084592091', '116.29139470791', '丰顺县', 442444, 'county'),
(444384, '441424', '23.802833236552', '115.64131969769', '五华县', 442444, 'county'),
(444385, '441426', '24.695653660804', '115.93265634975', '平远县', 442444, 'county'),
(444386, '441427', '24.683283405987', '116.19614150108', '蕉岭县', 442444, 'county'),
(444387, '441481', '24.267311238028', '115.75329965584', '兴宁市', 442444, 'county'),
(444388, '441502', '22.768710049741', '115.42435769122', '城区', 442445, 'county'),
(444389, '441521', '22.969599520286', '115.2863223299', '海丰县', 442445, 'county'),
(444390, '441523', '23.284406924899', '115.62919633367', '陆河县', 442445, 'county'),
(444391, '441581', '22.967876723873', '115.78802975191', '陆丰市', 442445, 'county'),
(444392, '441602', '23.693604112347', '114.65448360226', '源城区', 442446, 'county'),
(444393, '441621', '23.525442374357', '115.06447099781', '紫金县', 442446, 'county'),
(444394, '441622', '24.334679775761', '115.36229172074', '龙川县', 442446, 'county'),
(444395, '441623', '24.340566290031', '114.54297659273', '连平县', 442446, 'county'),
(444396, '441624', '24.45211039106', '115.01181507521', '和平县', 442446, 'county'),
(444397, '441625', '23.933052556598', '114.82694608538', '东源县', 442446, 'county'),
(444398, '441702', '21.762803637074', '111.93003574135', '江城区', 442447, 'county'),
(444399, '441704', '21.90761038558', '112.04622577462', '阳东区', 442447, 'county'),
(444400, '441721', '21.720609599412', '111.60050919755', '阳西县', 442447, 'county'),
(444401, '441781', '22.223897927949', '111.69444876956', '阳春市', 442447, 'county'),
(444402, '441802', '23.62585596526', '113.11458528252', '清城区', 442448, 'county'),
(444403, '441803', '23.932290452567', '112.94889933526', '清新区', 442448, 'county'),
(444404, '441821', '23.881077228129', '113.56668917499', '佛冈县', 442448, 'county'),
(444405, '441823', '24.509485552315', '112.68133014518', '阳山县', 442448, 'county'),
(444406, '441825', '24.515164969495', '112.10080575295', '连山壮族瑶族自治县', 442448, 'county'),
(444407, '441826', '24.574155992653', '112.26364236788', '连南瑶族自治县', 442448, 'county'),
(444408, '441881', '24.225680391225', '113.32316898492', '英德市', 442448, 'county'),
(444409, '441882', '24.937020846031', '112.45918890578', '连州市', 442448, 'county'),
(444410, '441900', '23.043023815368', '113.76343399076', '东莞市', 442220, 'county'),
(444411, '442000', '22.545177514513', '113.4220600208', '中山市', 442220, 'county'),
(444412, '445102', '23.700043577114', '116.67789952964', '湘桥区', 442449, 'county'),
(444413, '445103', '23.717386141778', '116.60876927831', '潮安区', 442449, 'county'),
(444414, '445122', '23.865029718048', '116.90612266997', '饶平县', 442449, 'county'),
(444415, '445202', '23.529452754199', '116.3692235802', '榕城区', 442450, 'county'),
(444416, '445203', '23.585024810833', '116.37807073325', '揭东区', 442450, 'county'),
(444417, '445222', '23.494712399671', '115.91682503049', '揭西县', 442450, 'county'),
(444418, '445224', '23.034046544147', '116.2247989034', '惠来县', 442450, 'county'),
(444419, '445281', '23.288953583142', '116.07816590835', '普宁市', 442450, 'county'),
(444420, '445302', '22.973002378136', '112.17160356227', '云城区', 442451, 'county'),
(444421, '445303', '22.856466364893', '111.96143088808', '云安区', 442451, 'county'),
(444422, '445321', '22.626992446128', '112.21754109744', '新兴县', 442451, 'county'),
(444423, '445322', '23.043633197681', '111.61993760725', '郁南县', 442451, 'county'),
(444424, '445381', '22.690983986437', '111.49324209266', '罗定市', 442451, 'county'),
(444425, '450102', '22.924530825243', '108.41762068739', '兴宁区', 442452, 'county'),
(444426, '450103', '22.829217973591', '108.54167973252', '青秀区', 442452, 'county'),
(444427, '450105', '22.663806639444', '108.13559066584', '江南区', 442452, 'county'),
(444428, '450107', '22.912937296114', '108.21544203073', '西乡塘区', 442452, 'county'),
(444429, '450108', '22.498910081219', '108.37044913796', '良庆区', 442452, 'county'),
(444430, '450109', '22.595811549706', '108.62620569962', '邕宁区', 442452, 'county'),
(444431, '450110', '23.233267218289', '108.23369489981', '武鸣区', 442452, 'county'),
(444432, '450123', '23.110227709531', '107.69066557406', '隆安县', 442452, 'county'),
(444433, '450124', '23.664942974082', '108.1696043635', '马山县', 442452, 'county'),
(444434, '450125', '23.521730154673', '108.64581538209', '上林县', 442452, 'county'),
(444435, '450126', '23.168344342302', '108.94049469657', '宾阳县', 442452, 'county'),
(444436, '450127', '22.774919317685', '109.16892656267', '横县', 442452, 'county'),
(444437, '450202', '24.371128485733', '109.48318080161', '城中区', 442453, 'county'),
(444438, '450203', '24.275815550781', '109.45632703637', '鱼峰区', 442453, 'county'),
(444439, '450204', '24.306183897363', '109.34346581857', '柳南区', 442453, 'county'),
(444440, '450205', '24.471742756535', '109.41391452987', '柳北区', 442453, 'county'),
(444441, '450206', '24.21578019619', '109.33837797157', '柳江区', 442453, 'county'),
(444442, '450222', '24.62988200842', '109.23019655363', '柳城县', 442453, 'county'),
(444443, '450223', '24.532198388889', '109.80281600679', '鹿寨县', 442453, 'county'),
(444444, '450224', '25.139782632024', '109.51401020724', '融安县', 442453, 'county'),
(444445, '450225', '25.343698638547', '109.05786347353', '融水苗族自治县', 442453, 'county'),
(444446, '450226', '25.74756560612', '109.5100810614', '三江侗族自治县', 442453, 'county'),
(444447, '450302', '25.287138490985', '110.27454852003', '秀峰区', 442454, 'county'),
(444448, '450303', '25.318874237568', '110.336225817', '叠彩区', 442454, 'county'),
(444449, '450304', '25.215755465426', '110.28460774513', '象山区', 442454, 'county'),
(444450, '450305', '25.264669861823', '110.35658833681', '七星区', 442454, 'county'),
(444451, '450311', '25.112805740761', '110.37148547606', '雁山区', 442454, 'county'),
(444452, '450312', '25.266798702759', '110.05831249425', '临桂区', 442454, 'county'),
(444453, '450321', '24.857282289724', '110.48292929478', '阳朔县', 442454, 'county'),
(444454, '450323', '25.381008804927', '110.41812911351', '灵川县', 442454, 'county'),
(444455, '450324', '25.936464773168', '111.02643476452', '全州县', 442454, 'county'),
(444456, '450325', '25.6070310342', '110.60102057414', '兴安县', 442454, 'county'),
(444457, '450326', '24.997329894857', '109.91693042182', '永福县', 442454, 'county'),
(444458, '450327', '25.458880833514', '111.0777084233', '灌阳县', 442454, 'county'),
(444459, '450328', '25.868327982022', '110.0102504878', '龙胜各族自治县', 442454, 'county'),
(444460, '450329', '26.067857197159', '110.59842700994', '资源县', 442454, 'county'),
(444461, '450330', '24.558919773879', '110.79768988938', '平乐县', 442454, 'county'),
(444462, '450331', '24.525342885432', '110.36832789757', '荔浦县', 442454, 'county'),
(444463, '450332', '24.949325584117', '110.90944732333', '恭城瑶族自治县', 442454, 'county'),
(444464, '450403', '23.563455294046', '111.42162608637', '万秀区', 442455, 'county'),
(444465, '450405', '23.560200006306', '111.1899141988', '长洲区', 442455, 'county'),
(444466, '450406', '23.205423202289', '111.32167060016', '龙圩区', 442455, 'county'),
(444467, '450421', '23.626737954219', '111.29835212828', '苍梧县', 442455, 'county'),
(444468, '450422', '23.510902782468', '110.77883787789', '藤县', 442455, 'county'),
(444469, '450423', '24.133850543878', '110.56122298515', '蒙山县', 442455, 'county'),
(444470, '450481', '22.925290987321', '111.02872021172', '岑溪市', 442455, 'county'),
(444471, '450502', '21.518620780285', '109.16534360381', '海城区', 442456, 'county'),
(444472, '450503', '21.48972262057', '109.2515908141', '银海区', 442456, 'county'),
(444473, '450512', '21.574915371765', '109.42248930511', '铁山港区', 442456, 'county'),
(444474, '450521', '21.740444343774', '109.33539345631', '合浦县', 442456, 'county'),
(444475, '450602', '21.662035674238', '108.44916612265', '港口区', 442457, 'county'),
(444476, '450603', '21.764841822261', '108.02974018357', '防城区', 442457, 'county'),
(444477, '450621', '22.053625294887', '107.90234352919', '上思县', 442457, 'county'),
(444478, '450681', '21.627169839712', '108.0610807332', '东兴市', 442457, 'county'),
(444479, '450702', '21.89668072285', '108.8165239388', '钦南区', 442458, 'county'),
(444480, '450703', '22.171133309191', '108.52867631111', '钦北区', 442458, 'county'),
(444481, '450721', '22.315715686267', '109.14774755818', '灵山县', 442458, 'county'),
(444482, '450722', '22.271304072712', '109.54236668008', '浦北县', 442458, 'county'),
(444483, '450802', '23.244654866397', '109.68955750941', '港北区', 442459, 'county'),
(444484, '450803', '22.87475110083', '109.7098514885', '港南区', 442459, 'county'),
(444485, '450804', '23.147899675106', '109.4013360968', '覃塘区', 442459, 'county'),
(444486, '450821', '23.538682883685', '110.41260119285', '平南县', 442459, 'county'),
(444487, '450881', '23.3332806173', '110.08711890997', '桂平市', 442459, 'county'),
(444488, '450902', '22.557212692568', '110.0645342655', '玉州区', 442460, 'county'),
(444489, '450903', '22.485121229661', '109.99939646175', '福绵区', 442460, 'county'),
(444490, '450921', '22.831614121088', '110.61027737584', '容县', 442460, 'county'),
(444491, '450922', '22.251747037814', '110.27211293424', '陆川县', 442460, 'county'),
(444492, '450923', '22.066766171087', '109.87890451509', '博白县', 442460, 'county'),
(444493, '450924', '22.798461756462', '109.92861094693', '兴业县', 442460, 'county'),
(444494, '450981', '22.528890370522', '110.46705456426', '北流市', 442460, 'county'),
(444495, '451002', '23.941865593712', '106.50559640624', '右江区', 442461, 'county'),
(444496, '451021', '23.729759302774', '106.81127009414', '田阳县', 442461, 'county'),
(444497, '451022', '23.614585367817', '107.19163711741', '田东县', 442461, 'county'),
(444498, '451023', '23.540954424157', '107.57751209903', '平果县', 442461, 'county'),
(444499, '451024', '23.382214509826', '106.59428466728', '德保县', 442461, 'county'),
(444500, '451026', '23.247545560208', '105.8347049622', '那坡县', 442461, 'county'),
(444501, '451027', '24.363726145418', '106.64837922239', '凌云县', 442461, 'county'),
(444502, '451028', '24.829664240385', '106.5178987429', '乐业县', 442461, 'county'),
(444503, '451029', '24.392538479127', '105.99982724993', '田林县', 442461, 'county'),
(444504, '451030', '24.391377583083', '105.09732745516', '西林县', 442461, 'county'),
(444505, '451031', '24.680432837057', '105.30321343556', '隆林各族自治县', 442461, 'county'),
(444506, '451081', '23.221036271428', '106.38310874744', '靖西市', 442461, 'county'),
(444507, '451102', '24.309335821524', '111.68835191834', '八步区', 442462, 'county'),
(444508, '451103', '24.272024331072', '111.4583206726', '平桂区', 442462, 'county'),
(444509, '451121', '24.108072861819', '110.97690768696', '昭平县', 442462, 'county'),
(444510, '451122', '24.513864981375', '111.24883282187', '钟山县', 442462, 'county'),
(444511, '451123', '24.891613643714', '111.31324328294', '富川瑶族自治县', 442462, 'county'),
(444512, '451202', '24.660762069433', '107.87344356952', '金城江区', 442463, 'county'),
(444513, '451221', '25.11943883054', '107.46800068753', '南丹县', 442463, 'county'),
(444514, '451222', '25.01883375149', '106.99659443884', '天峨县', 442463, 'county'),
(444515, '451223', '24.560064974996', '107.01971572195', '凤山县', 442463, 'county'),
(444516, '451224', '24.511600489222', '107.41353376084', '东兰县', 442463, 'county'),
(444517, '451225', '24.904567511665', '108.82719124199', '罗城仫佬族自治县', 442463, 'county'),
(444518, '451226', '25.104531056442', '108.29198518646', '环江毛南族自治县', 442463, 'county'),
(444519, '451227', '24.157595548736', '107.20766596976', '巴马瑶族自治县', 442463, 'county'),
(444520, '451228', '24.169778074597', '108.11806068056', '都安瑶族自治县', 442463, 'county'),
(444521, '451229', '23.970744702117', '107.71195932144', '大化瑶族自治县', 442463, 'county'),
(444522, '451281', '24.481176748089', '108.5465522796', '宜州市', 442463, 'county'),
(444523, '451302', '23.664270771977', '109.19320522678', '兴宾区', 442464, 'county'),
(444524, '451321', '24.018747261796', '108.75231859732', '忻城县', 442464, 'county'),
(444525, '451322', '24.019170132851', '109.77196784577', '象州县', 442464, 'county'),
(444526, '451323', '23.61072110732', '109.68768015891', '武宣县', 442464, 'county'),
(444527, '451324', '24.089876611192', '110.13777637174', '金秀瑶族自治县', 442464, 'county'),
(444528, '451381', '23.802816135427', '108.94253993058', '合山市', 442464, 'county'),
(444529, '451402', '22.529826577387', '107.46135714079', '江州区', 442465, 'county'),
(444530, '451421', '22.524058231311', '107.82912504554', '扶绥县', 442465, 'county'),
(444531, '451422', '22.005062342039', '107.29465888577', '宁明县', 442465, 'county'),
(444532, '451423', '22.431578425316', '106.85853904762', '龙州县', 442465, 'county'),
(444533, '451424', '22.813462764842', '107.13710947577', '大新县', 442465, 'county'),
(444534, '451425', '23.117161246424', '107.08133912276', '天等县', 442465, 'county'),
(444535, '451481', '22.093647276973', '106.83705317757', '凭祥市', 442465, 'county'),
(444536, '460105', '19.884344360797', '110.26320040619', '秀英区', 442466, 'county'),
(444537, '460106', '19.905350664019', '110.33522411653', '龙华区', 442466, 'county'),
(444538, '460107', '19.741333613805', '110.48011046473', '琼山区', 442466, 'county'),
(444539, '460108', '19.942908977934', '110.50726929452', '美兰区', 442466, 'county'),
(444540, '460202', '18.38141790489', '109.73605457423', '海棠区', 442467, 'county'),
(444541, '460203', '18.266590591978', '109.57378482237', '吉阳区', 442467, 'county'),
(444542, '460204', '18.395908258064', '109.38879057584', '天涯区', 442467, 'county'),
(444543, '460205', '18.448774794407', '109.18636245975', '崖州区', 442467, 'county'),
(444544, '460321', '16.497085431044', '111.67308686126', '西沙群岛', 442468, 'county'),
(444545, '460322', '4.9743661921368', '112.66030170907', '南沙群岛', 442468, 'county'),
(444546, '460323', '12.464712920653', '113.75535610385', '中沙群岛的岛礁及其海域', 442468, 'county'),
(444547, '460400', '19.574787798597', '109.33458619886', '儋州市', 442222, 'county'),
(444548, '469001', '18.831305749013', '109.51775006369', '五指山市', 442469, 'county'),
(444549, '469002', '19.214830368617', '110.41435935151', '琼海市', 442469, 'county'),
(444550, '469005', '19.750947380145', '110.78090944499', '文昌市', 442469, 'county'),
(444551, '469006', '18.839885909177', '110.29250485724', '万宁市', 442469, 'county'),
(444552, '469007', '18.998160861218', '108.85100963157', '东方市', 442469, 'county'),
(444553, '469021', '20.050057124473', '110.20642407813', '定安县', 442469, 'county'),
(444554, '469022', '19.347749127852', '110.06336404474', '屯昌县', 442469, 'county'),
(444555, '469023', '19.693135069577', '109.99673620157', '澄迈县', 442469, 'county'),
(444556, '469024', '19.805922012409', '109.72410152868', '临高县', 442469, 'county'),
(444557, '469025', '19.216056142062', '109.35858558291', '白沙黎族自治县', 442469, 'county'),
(444558, '469026', '19.222482900957', '109.01129968163', '昌江黎族自治县', 442469, 'county'),
(444559, '469027', '18.658613560734', '109.0626980127', '乐东黎族自治县', 442469, 'county'),
(444560, '469028', '18.575984851566', '109.94866071004', '陵水黎族自治县', 442469, 'county'),
(444561, '469029', '18.597592346267', '109.65611337969', '保亭黎族苗族自治县', 442469, 'county'),
(444562, '469030', '19.039771066968', '109.86184857077', '琼中黎族苗族自治县', 442469, 'county'),
(444563, '500101', '30.710054184366', '108.4134386367', '万州区', 442470, 'county'),
(444564, '500102', '29.66467054056', '107.34079973803', '涪陵区', 442470, 'county'),
(444565, '500103', '29.555236194395', '106.54696678483', '渝中区', 442470, 'county'),
(444566, '500104', '29.424139786946', '106.46532181465', '大渡口区', 442470, 'county'),
(444567, '500105', '29.619317744064', '106.71361473094', '江北区', 442470, 'county'),
(444568, '500106', '29.630548136629', '106.37480489265', '沙坪坝区', 442470, 'county'),
(444569, '500107', '29.434566154958', '106.37059488439', '九龙坡区', 442470, 'county'),
(444570, '500108', '29.541514618903', '106.66717849904', '南岸区', 442470, 'county'),
(444571, '500109', '29.866596066865', '106.52034245432', '北碚区', 442470, 'county'),
(444572, '500110', '28.825949323551', '106.73584657225', '綦江区', 442470, 'county'),
(444573, '500111', '29.622204718555', '105.76093297492', '大足区', 442470, 'county'),
(444574, '500112', '29.816264082426', '106.7537985312', '渝北区', 442470, 'county'),
(444575, '500113', '29.378027968889', '106.7582741592', '巴南区', 442470, 'county'),
(444576, '500114', '29.440981033584', '108.71480796402', '黔江区', 442470, 'county'),
(444577, '500115', '29.96049135503', '107.14661537132', '长寿区', 442470, 'county'),
(444578, '500116', '29.035351190668', '106.26928185639', '江津区', 442470, 'county'),
(444579, '500117', '30.118708260134', '106.31802875449', '合川区', 442470, 'county'),
(444580, '500118', '29.296487646991', '105.88035760368', '永川区', 442470, 'county'),
(444581, '500119', '29.141685769527', '107.17788827954', '南川区', 442470, 'county'),
(444582, '500120', '29.588328631909', '106.21326949786', '璧山区', 442470, 'county'),
(444583, '500151', '29.813265758673', '106.03488288304', '铜梁区', 442470, 'county'),
(444584, '500152', '30.116632232545', '105.78466162818', '潼南区', 442470, 'county'),
(444585, '500153', '29.472620663129', '105.52149235061', '荣昌区', 442470, 'county'),
(444586, '500154', '31.262995406524', '108.42256829126', '开州区', 442470, 'county'),
(444587, '500228', '30.66436343529', '107.72542817193', '梁平县', 442471, 'county'),
(444588, '500229', '31.888131392209', '108.74185516517', '城口县', 442471, 'county'),
(444589, '500230', '29.890595717682', '107.8375173643', '丰都县', 442471, 'county'),
(444590, '500231', '30.259498445887', '107.44444454166', '垫江县', 442471, 'county'),
(444591, '500232', '29.379270963599', '107.71610570339', '武隆县', 442471, 'county'),
(444592, '500233', '29.544606108886', '106.53063501341', '忠县', 442471, 'county'),
(444593, '500235', '31.042409267237', '108.86318575675', '云阳县', 442471, 'county'),
(444594, '500236', '30.958552797156', '109.35566670168', '奉节县', 442471, 'county'),
(444595, '500237', '31.121151720268', '109.90861122268', '巫山县', 442471, 'county'),
(444596, '500238', '31.509161376321', '109.36053147066', '巫溪县', 442471, 'county'),
(444597, '500240', '30.099636944155', '108.30489042793', '石柱土家族自治县', 442471, 'county'),
(444598, '500241', '28.498315398405', '109.02532125368', '秀山土家族苗族自治县', 442471, 'county'),
(444599, '500242', '28.905277662391', '108.80680823733', '酉阳土家族苗族自治县', 442471, 'county'),
(444600, '500243', '29.359628264894', '108.27286773419', '彭水苗族土家族自治县', 442471, 'county'),
(444601, '510104', '30.606301824621', '104.12426938462', '锦江区', 442472, 'county'),
(444602, '510105', '30.685101946314', '103.98842870094', '青羊区', 442472, 'county'),
(444603, '510106', '30.735622100763', '104.06137695451', '金牛区', 442472, 'county'),
(444604, '510107', '30.612881788753', '104.04124020837', '武侯区', 442472, 'county'),
(444605, '510108', '30.695040111899', '104.15003204704', '成华区', 442472, 'county'),
(444606, '510112', '30.603368382019', '104.30118080707', '龙泉驿区', 442472, 'county'),
(444607, '510113', '30.796353967983', '104.34642982356', '青白江区', 442472, 'county'),
(444608, '510114', '30.839503886637', '104.11658349961', '新都区', 442472, 'county'),
(444609, '510115', '30.730254927008', '103.81646839534', '温江区', 442472, 'county'),
(444610, '510116', '30.450175430612', '104.0328303402', '双流区', 442472, 'county'),
(444611, '510121', '30.728612610912', '104.61537139695', '金堂县', 442472, 'county'),
(444612, '510124', '30.839641883011', '103.88462503305', '郫县', 442472, 'county'),
(444613, '510129', '30.614941412606', '103.38845160801', '大邑县', 442472, 'county'),
(444614, '510131', '30.239938504594', '103.49773846901', '蒲江县', 442472, 'county'),
(444615, '510132', '30.42786608997', '103.83217681027', '新津县', 442472, 'county'),
(444616, '510181', '31.039123659728', '103.63734201321', '都江堰市', 442472, 'county'),
(444617, '510182', '31.148577255886', '103.88986635887', '彭州市', 442472, 'county'),
(444618, '510183', '30.388736018151', '103.37651244321', '邛崃市', 442472, 'county'),
(444619, '510184', '30.71964092397', '103.52946689588', '崇州市', 442472, 'county'),
(444620, '510185', '30.37250750046', '104.55059629796', '简阳市', 442472, 'county'),
(444621, '510302', '29.28261396923', '104.70785437828', '自流井区', 442473, 'county'),
(444622, '510303', '29.314590727756', '104.6027348472', '贡井区', 442473, 'county'),
(444623, '510304', '29.411547695333', '104.87756638738', '大安区', 442473, 'county'),
(444624, '510311', '29.242640479342', '104.854763441', '沿滩区', 442473, 'county'),
(444625, '510321', '29.398978496698', '104.372407917', '荣县', 442473, 'county'),
(444626, '510322', '29.152297063892', '105.02222048778', '富顺县', 442473, 'county'),
(444627, '510402', '26.587571257109', '101.72242315249', '东区', 442474, 'county'),
(444628, '510403', '26.587571257109', '101.72242315249', '西区', 442474, 'county'),
(444629, '510411', '26.56790741922', '101.66970205128', '仁和区', 442474, 'county'),
(444630, '510421', '26.932749356485', '102.00072626456', '米易县', 442474, 'county'),
(444631, '510422', '26.940087094351', '101.58605027726', '盐边县', 442474, 'county'),
(444632, '510502', '28.87690067554', '105.37171257028', '江阳区', 442475, 'county'),
(444633, '510503', '28.614041373614', '105.3906055521', '纳溪区', 442475, 'county'),
(444634, '510504', '28.987460236388', '105.4378416897', '龙马潭区', 442475, 'county'),
(444635, '510521', '29.124919969133', '105.50826734902', '泸县', 442475, 'county'),
(444636, '510522', '28.751865254096', '105.93160013109', '合江县', 442475, 'county'),
(444637, '510524', '28.099206628496', '105.46859233328', '叙永县', 442475, 'county'),
(444638, '510525', '27.983319448381', '105.93629331276', '古蔺县', 442475, 'county'),
(444639, '510603', '31.179805144786', '104.41525849556', '旌阳区', 442476, 'county'),
(444640, '510623', '30.887114236708', '104.80495180574', '中江县', 442476, 'county'),
(444641, '510626', '31.320265186662', '104.53541026815', '罗江县', 442476, 'county'),
(444642, '510681', '31.006480881164', '104.29847583599', '广汉市', 442476, 'county'),
(444643, '510682', '31.29369418585', '104.01987074915', '什邡市', 442476, 'county'),
(444644, '510683', '31.436657312108', '104.12929386201', '绵竹市', 442476, 'county'),
(444645, '510703', '31.435734812547', '104.67051389601', '涪城区', 442477, 'county'),
(444646, '510704', '31.518816009605', '104.98157984665', '游仙区', 442477, 'county'),
(444647, '510705', '31.589559671673', '104.37720699346', '安州区', 442477, 'county'),
(444648, '510722', '31.118872490873', '105.04258112078', '三台县', 442477, 'county'),
(444649, '510723', '31.247942979309', '105.4790711008', '盐亭县', 442477, 'county'),
(444650, '510725', '31.653620996937', '105.19383418448', '梓潼县', 442477, 'county'),
(444651, '510726', '31.962527312739', '104.25834135739', '北川羌族自治县', 442477, 'county'),
(444652, '510727', '32.446911722865', '104.40430826693', '平武县', 442477, 'county'),
(444653, '510781', '31.952426668806', '104.93314929993', '江油市', 442477, 'county'),
(444654, '510802', '32.478529639449', '105.7853172322', '利州区', 442478, 'county'),
(444655, '510811', '32.141760307902', '105.82174977463', '昭化区', 442478, 'county'),
(444656, '510812', '32.708417209014', '106.02216392398', '朝天区', 442478, 'county'),
(444657, '510821', '32.372139642174', '106.40182287359', '旺苍县', 442478, 'county'),
(444658, '510822', '32.515859827572', '105.19044673467', '青川县', 442478, 'county'),
(444659, '510823', '31.921947731961', '105.50302096949', '剑阁县', 442478, 'county'),
(444660, '510824', '31.918551658673', '106.11328295036', '苍溪县', 442478, 'county'),
(444661, '510903', '30.523499649283', '105.62152802077', '船山区', 442479, 'county'),
(444662, '510904', '30.363522338679', '105.41441146849', '安居区', 442479, 'county'),
(444663, '510921', '30.657491289748', '105.71608763636', '蓬溪县', 442479, 'county'),
(444664, '510922', '30.908078631387', '105.38824463742', '射洪县', 442479, 'county'),
(444665, '510923', '30.580190633917', '105.25637201729', '大英县', 442479, 'county'),
(444666, '511002', '29.55164493068', '104.95397876928', '市中区', 442480, 'county'),
(444667, '511011', '29.628088552472', '105.20216881381', '东兴区', 442480, 'county'),
(444668, '511024', '29.599588801619', '104.59397578195', '威远县', 442480, 'county'),
(444669, '511025', '29.813836235002', '104.80746566888', '资中县', 442480, 'county'),
(444670, '511028', '29.367868749158', '105.25295771714', '隆昌县', 442480, 'county'),
(444671, '511102', '29.61984411709', '103.80478219007', '市中区', 442481, 'county'),
(444672, '511111', '29.316409760812', '103.60454818519', '沙湾区', 442481, 'county'),
(444673, '511112', '29.395443506532', '103.84663334546', '五通桥区', 442481, 'county'),
(444674, '511113', '29.293819974164', '103.07336628607', '金口河区', 442481, 'county');
INSERT INTO `region` (`id`, `code`, `lat`, `lng`, `name`, `parent_id`, `by_type`) VALUES
(444675, '511123', '29.231190495122', '103.98019853458', '犍为县', 442481, 'county'),
(444676, '511124', '29.644500661501', '104.05532967168', '井研县', 442481, 'county'),
(444677, '511126', '29.7761069203', '103.55926293457', '夹江县', 442481, 'county'),
(444678, '511129', '29.006905081763', '103.82650268685', '沐川县', 442481, 'county'),
(444679, '511132', '29.050415556838', '103.2167397181', '峨边彝族自治县', 442481, 'county'),
(444680, '511133', '28.776739333365', '103.48138754926', '马边彝族自治县', 442481, 'county'),
(444681, '511181', '29.50700404085', '103.40091230856', '峨眉山市', 442481, 'county'),
(444682, '511302', '30.949624560849', '106.11579825983', '顺庆区', 442482, 'county'),
(444683, '511303', '30.75468404314', '106.25975917374', '高坪区', 442482, 'county'),
(444684, '511304', '30.665451829003', '105.93870307769', '嘉陵区', 442482, 'county'),
(444685, '511321', '31.349802866479', '105.92351381942', '南部县', 442482, 'county'),
(444686, '511322', '31.162322799073', '106.7185269662', '营山县', 442482, 'county'),
(444687, '511323', '31.007075500318', '106.42891733521', '蓬安县', 442482, 'county'),
(444688, '511324', '31.443593221681', '106.53472488695', '仪陇县', 442482, 'county'),
(444689, '511325', '31.063877074354', '105.85733163521', '西充县', 442482, 'county'),
(444690, '511381', '31.602117348886', '106.07809314825', '阆中市', 442482, 'county'),
(444691, '511402', '30.057372008382', '103.74833257305', '东坡区', 442483, 'county'),
(444692, '511403', '30.24443648473', '103.84644479804', '彭山区', 442483, 'county'),
(444693, '511421', '29.985868914405', '104.22551880085', '仁寿县', 442483, 'county'),
(444694, '511423', '29.694316499556', '103.18015949893', '洪雅县', 442483, 'county'),
(444695, '511424', '30.014802935586', '103.43451305805', '丹棱县', 442483, 'county'),
(444696, '511425', '29.82275999862', '103.83750799161', '青神县', 442483, 'county'),
(444697, '511502', '28.81581998264', '104.69325460374', '翠屏区', 442484, 'county'),
(444698, '511503', '28.891857434171', '104.92244522579', '南溪区', 442484, 'county'),
(444699, '511521', '28.906871431718', '104.38270989151', '宜宾县', 442484, 'county'),
(444700, '511523', '28.663532600917', '105.12877827717', '江安县', 442484, 'county'),
(444701, '511524', '28.515433786755', '104.93114856989', '长宁县', 442484, 'county'),
(444702, '511525', '28.463200492005', '104.5930664103', '高县', 442484, 'county'),
(444703, '511526', '28.196990137689', '104.8066179908', '珙县', 442484, 'county'),
(444704, '511527', '28.042098884244', '104.58843340511', '筠连县', 442484, 'county'),
(444705, '511528', '28.255538437302', '105.14122589128', '兴文县', 442484, 'county'),
(444706, '511529', '28.702428662485', '103.99911803649', '屏山县', 442484, 'county'),
(444707, '511602', '30.599249987199', '106.75891196362', '广安区', 442485, 'county'),
(444708, '511603', '30.543834838815', '106.86565774045', '前锋区', 442485, 'county'),
(444709, '511621', '30.540768629653', '106.4208329851', '岳池县', 442485, 'county'),
(444710, '511622', '30.373904543993', '106.23136624407', '武胜县', 442485, 'county'),
(444711, '511623', '30.263283994028', '107.00333361946', '邻水县', 442485, 'county'),
(444712, '511681', '30.321832376319', '106.75941195402', '华蓥市', 442485, 'county'),
(444713, '511702', '31.238764440346', '107.51920394973', '通川区', 442486, 'county'),
(444714, '511703', '31.187291385014', '107.42129730953', '达川区', 442486, 'county'),
(444715, '511722', '31.51979762495', '107.93603281988', '宣汉县', 442486, 'county'),
(444716, '511723', '31.05158729925', '107.89101188441', '开江县', 442486, 'county'),
(444717, '511724', '30.690772377766', '107.27987739412', '大竹县', 442486, 'county'),
(444718, '511725', '30.94881416065', '106.98760176612', '渠县', 442486, 'county'),
(444719, '511781', '31.986241088206', '107.99381097493', '万源市', 442486, 'county'),
(444720, '511802', '29.928506655301', '103.03840450831', '雨城区', 442487, 'county'),
(444721, '511803', '30.117458953474', '103.23102630214', '名山区', 442487, 'county'),
(444722, '511822', '29.740877769322', '102.69194616494', '荥经县', 442487, 'county'),
(444723, '511823', '29.431575643201', '102.62513643454', '汉源县', 442487, 'county'),
(444724, '511824', '29.235484876512', '102.2939695159', '石棉县', 442487, 'county'),
(444725, '511825', '30.078874542047', '102.57830462584', '天全县', 442487, 'county'),
(444726, '511826', '30.440281571631', '103.01809878481', '芦山县', 442487, 'county'),
(444727, '511827', '30.567649711279', '102.71689365787', '宝兴县', 442487, 'county'),
(444728, '511902', '31.785302790667', '106.739266453', '巴州区', 442488, 'county'),
(444729, '511903', '31.86918915916', '106.75791584175', '恩阳区', 442488, 'county'),
(444730, '511921', '32.13640689395', '107.35277526385', '通江县', 442488, 'county'),
(444731, '511922', '32.337239209081', '106.83618103409', '南江县', 442488, 'county'),
(444732, '511923', '31.59771477028', '107.16735749976', '平昌县', 442488, 'county'),
(444733, '512002', '30.091647255037', '104.75541652784', '雁江区', 442489, 'county'),
(444734, '512021', '29.999677270422', '105.4008757725', '安岳县', 442489, 'county'),
(444735, '512022', '30.313944636249', '105.02831576248', '乐至县', 442489, 'county'),
(444736, '513201', '32.007871202647', '101.9836278113', '马尔康市', 442490, 'county'),
(444737, '513221', '31.168774069592', '103.29431691527', '汶川县', 442490, 'county'),
(444738, '513222', '31.566906370691', '103.42033582964', '理县', 442490, 'county'),
(444739, '513223', '30.367480937958', '102.8991597236', '茂县', 442490, 'county'),
(444740, '513224', '32.625458557695', '103.532712222', '松潘县', 442490, 'county'),
(444741, '513225', '33.317446497617', '103.9340437688', '九寨沟县', 442490, 'county'),
(444742, '513226', '31.52757038818', '101.80476934386', '金川县', 442490, 'county'),
(444743, '513227', '30.969288643982', '102.41921664895', '小金县', 442490, 'county'),
(444744, '513228', '32.052158211237', '103.01249848065', '黑水县', 442490, 'county'),
(444745, '513230', '32.148226253207', '101.05971696123', '壤塘县', 442490, 'county'),
(444746, '513231', '32.916574446999', '101.70212990273', '阿坝县', 442490, 'county'),
(444747, '513232', '33.584805758741', '102.97487609843', '若尔盖县', 442490, 'county'),
(444748, '513233', '32.736132092126', '102.64115041582', '红原县', 442490, 'county'),
(444749, '513301', '29.963390007018', '101.75312764174', '康定市', 442491, 'county'),
(444750, '513322', '29.747744290103', '102.12006613226', '泸定县', 442491, 'county'),
(444751, '513323', '30.9670743425', '101.75239771652', '丹巴县', 442491, 'county'),
(444752, '513324', '28.917804185231', '101.63507969271', '九龙县', 442491, 'county'),
(444753, '513325', '29.922924021131', '100.96923972469', '雅江县', 442491, 'county'),
(444754, '513326', '30.870125609599', '101.19484158408', '道孚县', 442491, 'county'),
(444755, '513327', '31.492154542768', '100.67587127372', '炉霍县', 442491, 'county'),
(444756, '513328', '32.029329338001', '99.762676935333', '甘孜县', 442491, 'county'),
(444757, '513329', '30.945762801068', '100.28751803177', '新龙县', 442491, 'county'),
(444758, '513330', '32.059408545008', '98.967480948584', '德格县', 442491, 'county'),
(444759, '513331', '31.052585679081', '99.291922023716', '白玉县', 442491, 'county'),
(444760, '513332', '33.187627237402', '98.204993247891', '石渠县', 442491, 'county'),
(444761, '513333', '32.356620371592', '100.21388463777', '色达县', 442491, 'county'),
(444762, '513334', '29.895282575765', '100.18511019101', '理塘县', 442491, 'county'),
(444763, '513335', '29.916287662541', '99.300290950528', '巴塘县', 442491, 'county'),
(444764, '513336', '29.11737591967', '99.738451912881', '乡城县', 442491, 'county'),
(444765, '513337', '28.766496640591', '100.26589077053', '稻城县', 442491, 'county'),
(444766, '513338', '28.736358436486', '99.324235091425', '得荣县', 442491, 'county'),
(444767, '513401', '27.86337739584', '102.11788786038', '西昌市', 442492, 'county'),
(444768, '513422', '28.360344107012', '100.95305714268', '木里藏族自治县', 442492, 'county'),
(444769, '513423', '27.603027908142', '101.46762448738', '盐源县', 442492, 'county'),
(444770, '513424', '27.331194175431', '102.19173440069', '德昌县', 442492, 'county'),
(444771, '513425', '26.591300796186', '102.2639270515', '会理县', 442492, 'county'),
(444772, '513426', '26.573608339176', '102.74296743101', '会东县', 442492, 'county'),
(444773, '513427', '27.091250349517', '102.71663446894', '宁南县', 442492, 'county'),
(444774, '513428', '27.54828572105', '102.56809148368', '普格县', 442492, 'county'),
(444775, '513429', '27.599974195145', '102.88192797888', '布拖县', 442492, 'county'),
(444776, '513430', '27.706169003934', '103.20105938361', '金阳县', 442492, 'county'),
(444777, '513431', '28.013719135153', '102.83281789781', '昭觉县', 442492, 'county'),
(444778, '513432', '28.196488558773', '102.44996824625', '喜德县', 442492, 'county'),
(444779, '513433', '28.514858657537', '102.06891407501', '冕宁县', 442492, 'county'),
(444780, '513434', '28.592190495109', '102.6286813356', '越西县', 442492, 'county'),
(444781, '513435', '28.974853435967', '102.76740070722', '甘洛县', 442492, 'county'),
(444782, '513436', '28.443545061087', '103.10172987962', '美姑县', 442492, 'county'),
(444783, '513437', '28.279340727749', '103.5125046952', '雷波县', 442492, 'county'),
(444784, '520102', '26.541413272278', '106.72417349818', '南明区', 442493, 'county'),
(444785, '520103', '26.6035246503', '106.71791401556', '云岩区', 442493, 'county'),
(444786, '520111', '26.39791693477', '106.66527322287', '花溪区', 442493, 'county'),
(444787, '520112', '26.688326312941', '106.73344967037', '乌当区', 442493, 'county'),
(444788, '520113', '26.71973739529', '106.68674281438', '白云区', 442493, 'county'),
(444789, '520115', '26.650328732081', '106.59533224014', '观山湖区', 442493, 'county'),
(444790, '520121', '27.075427307857', '107.04688065585', '开阳县', 442493, 'county'),
(444791, '520122', '27.150807565921', '106.68198036543', '息烽县', 442493, 'county'),
(444792, '520123', '26.931538038107', '106.590592735', '修文县', 442493, 'county'),
(444793, '520181', '26.688621110571', '106.35381440326', '清镇市', 442493, 'county'),
(444794, '520201', '26.731157002932', '104.76254690076', '钟山区', 442494, 'county'),
(444795, '520203', '26.235865065306', '105.3830336754', '六枝特区', 442494, 'county'),
(444796, '520221', '26.430546898236', '104.93035685319', '水城县', 442494, 'county'),
(444797, '520222', '25.772838336514', '104.66691307994', '盘县', 442494, 'county'),
(444798, '520302', '27.670445028837', '106.92265113614', '红花岗区', 442495, 'county'),
(444799, '520303', '27.887590184121', '107.00310975556', '汇川区', 442495, 'county'),
(444800, '520304', '27.634108765721', '106.87453374646', '播州区', 442495, 'county'),
(444801, '520322', '28.414479762728', '106.88633093203', '桐梓县', 442495, 'county'),
(444802, '520323', '28.146365347962', '107.20354220552', '绥阳县', 442495, 'county'),
(444803, '520324', '28.506639188144', '107.412773457', '正安县', 442495, 'county'),
(444804, '520325', '28.934154342524', '107.61686480802', '道真仡佬族苗族自治县', 442495, 'county'),
(444805, '520326', '28.661403914202', '107.91993460165', '务川仡佬族苗族自治县', 442495, 'county'),
(444806, '520327', '27.928826877455', '107.77157370195', '凤冈县', 442495, 'county'),
(444807, '520328', '27.764873370783', '107.49167985383', '湄潭县', 442495, 'county'),
(444808, '520329', '27.394794444136', '107.70936003115', '余庆县', 442495, 'county'),
(444809, '520330', '28.357319781328', '106.35892584857', '习水县', 442495, 'county'),
(444810, '520381', '28.493333651299', '105.92051307621', '赤水市', 442495, 'county'),
(444811, '520382', '27.839203217967', '106.34790800681', '仁怀市', 442495, 'county'),
(444812, '520402', '26.197376772867', '106.0600169739', '西秀区', 442496, 'county'),
(444813, '520403', '26.443751196397', '106.28653433135', '平坝区', 442496, 'county'),
(444814, '520422', '26.345747551067', '105.74269311886', '普定县', 442496, 'county'),
(444815, '520423', '25.844353005861', '105.83355264869', '镇宁布依族苗族自治县', 442496, 'county'),
(444816, '520424', '25.862190437976', '105.56872665252', '关岭布依族苗族自治县', 442496, 'county'),
(444817, '520425', '25.700614716072', '106.18836244554', '紫云苗族布依族自治县', 442496, 'county'),
(444818, '520502', '27.464053316005', '105.42355760084', '七星关区', 442497, 'county'),
(444819, '520521', '27.253059626031', '105.73720221998', '大方县', 442497, 'county'),
(444820, '520522', '27.086384569459', '106.14050275681', '黔西县', 442497, 'county'),
(444821, '520523', '27.471542677862', '106.22685215924', '金沙县', 442497, 'county'),
(444822, '520524', '26.615333373403', '105.73204873021', '织金县', 442497, 'county'),
(444823, '520525', '26.795102083008', '105.26868668571', '纳雍县', 442497, 'county'),
(444824, '520526', '26.921382002531', '104.22408619137', '威宁彝族回族苗族自治县', 442497, 'county'),
(444825, '520527', '27.15332246261', '104.5980318783', '赫章县', 442497, 'county'),
(444826, '520602', '27.716136520691', '109.19370501854', '碧江区', 442498, 'county'),
(444827, '520603', '27.546566730898', '109.11781910744', '万山区', 442498, 'county'),
(444828, '520621', '27.674902690624', '109.16855802826', '江口县', 442498, 'county'),
(444829, '520622', '27.337802674507', '109.00175995258', '玉屏侗族自治县', 442498, 'county'),
(444830, '520623', '27.496152657798', '108.14106368738', '石阡县', 442498, 'county'),
(444831, '520624', '27.856658927235', '108.19797894198', '思南县', 442498, 'county'),
(444832, '520625', '27.986045252865', '108.52830153805', '印江土家族苗族自治县', 442498, 'county'),
(444833, '520626', '28.30284362728', '108.06756938624', '德江县', 442498, 'county'),
(444834, '520627', '28.642296722444', '108.33962765519', '沿河土家族自治县', 442498, 'county'),
(444835, '520628', '27.674902690624', '109.16855802826', '松桃苗族自治县', 442498, 'county'),
(444836, '522301', '25.236664590554', '105.07190822677', '兴义市', 442499, 'county'),
(444837, '522322', '25.436104684385', '105.21234670506', '兴仁县', 442499, 'county'),
(444838, '522323', '25.72781408682', '105.00016731249', '普安县', 442499, 'county'),
(444839, '522324', '25.697662011039', '105.19013699964', '晴隆县', 442499, 'county'),
(444840, '522325', '25.438979713387', '105.63574297484', '贞丰县', 442499, 'county'),
(444841, '522326', '24.936694569809', '106.13757227494', '望谟县', 442499, 'county'),
(444842, '522327', '24.940047609962', '105.79746392761', '册亨县', 442499, 'county'),
(444843, '522328', '24.950885976914', '105.34855137966', '安龙县', 442499, 'county'),
(444844, '522601', '26.670643028177', '108.03104164859', '凯里市', 442500, 'county'),
(444845, '522622', '26.802372712542', '108.08892037089', '黄平县', 442500, 'county'),
(444846, '522623', '27.147665744201', '108.01532489539', '施秉县', 442500, 'county'),
(444847, '522624', '26.940229768581', '108.76221698279', '三穗县', 442500, 'county'),
(444848, '522625', '26.912627316255', '108.48422420195', '镇远县', 442500, 'county'),
(444849, '522626', '27.440109779329', '108.95781984971', '岑巩县', 442500, 'county'),
(444850, '522627', '27.027180472033', '109.47993207974', '天柱县', 442500, 'county'),
(444851, '522628', '26.497609137737', '109.15647562165', '锦屏县', 442500, 'county'),
(444852, '522629', '26.902825927797', '106.7349961033', '剑河县', 442500, 'county'),
(444853, '522630', '26.676404735086', '108.17122222338', '台江县', 442500, 'county'),
(444854, '522631', '25.938276335425', '109.33697304601', '黎平县', 442500, 'county'),
(444855, '522632', '26.250544367069', '108.43782402746', '榕江县', 442500, 'county'),
(444856, '522633', '25.758440923722', '108.78396090306', '从江县', 442500, 'county'),
(444857, '522634', '26.346498520621', '108.09819178205', '雷山县', 442500, 'county'),
(444858, '522635', '26.512050397344', '107.79954768993', '麻江县', 442500, 'county'),
(444859, '522636', '26.126097716769', '108.11538894273', '丹寨县', 442500, 'county'),
(444860, '522701', '26.902825927797', '106.7349961033', '都匀市', 442501, 'county'),
(444861, '522702', '26.902825927797', '106.7349961033', '福泉市', 442501, 'county'),
(444862, '522722', '25.597752027123', '107.79056706026', '荔波县', 442501, 'county'),
(444863, '522723', '26.262176502508', '107.16183225437', '贵定县', 442501, 'county'),
(444864, '522725', '27.189412906689', '107.5629905221', '瓮安县', 442501, 'county'),
(444865, '522726', '25.636840589583', '107.56375230978', '独山县', 442501, 'county'),
(444866, '522727', '25.850446129607', '107.37217138777', '平塘县', 442501, 'county'),
(444867, '522728', '26.902825927797', '106.7349961033', '罗甸县', 442501, 'county'),
(444868, '522729', '26.000476134172', '106.40419817264', '长顺县', 442501, 'county'),
(444869, '522730', '26.51764181381', '107.00653768938', '龙里县', 442501, 'county'),
(444870, '522731', '25.98299665897', '106.72222309186', '惠水县', 442501, 'county'),
(444871, '522732', '25.852864250197', '107.95650555321', '三都水族自治县', 442501, 'county'),
(444872, '530102', '25.261305956605', '102.64937733166', '五华区', 442502, 'county'),
(444873, '530103', '25.274019484219', '102.76755633841', '盘龙区', 442502, 'county'),
(444874, '530111', '25.031310976713', '102.82881924705', '官渡区', 442502, 'county'),
(444875, '530112', '24.983630124462', '102.60347769299', '西山区', 442502, 'county'),
(444876, '530113', '26.139328854726', '103.07856150869', '东川区', 442502, 'county'),
(444877, '530114', '24.855409037478', '102.88428310764', '呈贡区', 442502, 'county'),
(444878, '530122', '24.605041073447', '102.5796139323', '晋宁县', 442502, 'county'),
(444879, '530124', '25.363439290284', '102.58410264122', '富民县', 442502, 'county'),
(444880, '530125', '24.944907933143', '103.1928154872', '宜良县', 442502, 'county'),
(444881, '530126', '24.754309493364', '103.42733563256', '石林彝族自治县', 442502, 'county'),
(444882, '530127', '25.317900180495', '103.00652503386', '嵩明县', 442502, 'county'),
(444883, '530128', '25.943771040548', '102.59302748518', '禄劝彝族苗族自治县', 442502, 'county'),
(444884, '530129', '25.666609835146', '103.12781347645', '寻甸回族彝族自治县', 442502, 'county'),
(444885, '530181', '24.852355456268', '102.39112679952', '安宁市', 442502, 'county'),
(444886, '530302', '25.360057471573', '103.91332638626', '麒麟区', 442503, 'county'),
(444887, '530303', '25.79421035848', '103.86810959342', '沾益区', 442503, 'county'),
(444888, '530321', '25.368839999918', '103.51309543424', '马龙县', 442503, 'county'),
(444889, '530322', '25.037570538056', '103.70738599876', '陆良县', 442503, 'county'),
(444890, '530323', '24.680198025566', '104.12947917517', '师宗县', 442503, 'county'),
(444891, '530324', '24.983157127291', '104.34927875646', '罗平县', 442503, 'county'),
(444892, '530325', '25.467214378875', '104.36745192446', '富源县', 442503, 'county'),
(444893, '530326', '26.46221827304', '103.46854362948', '会泽县', 442503, 'county'),
(444894, '530381', '26.276828622628', '104.15257073219', '宣威市', 442503, 'county'),
(444895, '530402', '24.369853985289', '102.49989459797', '红塔区', 442504, 'county'),
(444896, '530403', '24.367487731796', '102.75575323372', '江川区', 442504, 'county'),
(444897, '530422', '24.678380080254', '102.94685033975', '澄江县', 442504, 'county'),
(444898, '530423', '24.117558495247', '102.71141640598', '通海县', 442504, 'county'),
(444899, '530424', '24.284812305871', '102.99906774243', '华宁县', 442504, 'county'),
(444900, '530425', '24.6964042729', '102.12219715311', '易门县', 442504, 'county'),
(444901, '530426', '24.246114547538', '102.21924987866', '峨山彝族自治县', 442504, 'county'),
(444902, '530427', '24.029740767019', '101.73913066729', '新平彝族傣族自治县', 442504, 'county'),
(444903, '530428', '23.605002999101', '102.01115013144', '元江哈尼族彝族傣族自治县', 442504, 'county'),
(444904, '530502', '25.205265354944', '99.069046057861', '隆阳区', 442505, 'county'),
(444905, '530521', '24.657220496518', '99.157489563481', '施甸县', 442505, 'county'),
(444906, '530523', '24.499046233076', '98.842541709908', '龙陵县', 442505, 'county'),
(444907, '530524', '24.758162812306', '99.591112178323', '昌宁县', 442505, 'county'),
(444908, '530581', '25.248177969272', '98.43366397623', '腾冲市', 442505, 'county'),
(444909, '530602', '27.427583042152', '103.60727718737', '昭阳区', 442506, 'county'),
(444910, '530621', '27.205702890521', '103.42585557677', '鲁甸县', 442506, 'county'),
(444911, '530622', '27.008327725094', '103.13002031079', '巧家县', 442506, 'county'),
(444912, '530623', '28.130706512198', '104.23053478313', '盐津县', 442506, 'county'),
(444913, '530624', '27.905095827254', '103.91217766939', '大关县', 442506, 'county'),
(444914, '530625', '27.953163331681', '103.65282254144', '永善县', 442506, 'county'),
(444915, '530626', '28.538865566801', '104.01558782767', '绥江县', 442506, 'county'),
(444916, '530627', '27.568915967438', '104.83385203039', '镇雄县', 442506, 'county'),
(444917, '530628', '27.630986376229', '104.24144905945', '彝良县', 442506, 'county'),
(444918, '530629', '27.891462851573', '105.05028255746', '威信县', 442506, 'county'),
(444919, '530630', '28.510929836359', '104.22883253661', '水富县', 442506, 'county'),
(444920, '530702', '26.859300417703', '100.32859641682', '古城区', 442507, 'county'),
(444921, '530721', '27.104463367195', '99.951633936724', '玉龙纳西族自治县', 442507, 'county'),
(444922, '530722', '26.491706080711', '100.70492052637', '永胜县', 442507, 'county'),
(444923, '530723', '26.645807144841', '101.25172921237', '华坪县', 442507, 'county'),
(444924, '530724', '27.265588579997', '100.7783019296', '宁蒗彝族自治县', 442507, 'county'),
(444925, '530802', '22.739133092283', '100.85525310555', '思茅区', 442508, 'county'),
(444926, '530821', '23.097350457989', '101.19686023959', '宁洱哈尼族彝族自治县', 442508, 'county'),
(444927, '530822', '23.363251135433', '101.55548645512', '墨江哈尼族自治县', 442508, 'county'),
(444928, '530823', '24.39672894394', '100.79520569746', '景东彝族自治县', 442508, 'county'),
(444929, '530824', '23.368117190796', '100.56429126294', '景谷傣族彝族自治县', 442508, 'county'),
(444930, '530825', '24.011540716931', '101.11331766944', '镇沅彝族哈尼族拉祜族自治县', 442508, 'county'),
(444931, '530826', '22.625657650356', '101.79692922563', '江城哈尼族彝族自治县', 442508, 'county'),
(444932, '530827', '22.334366217371', '99.580342878181', '孟连傣族拉祜族佤族自治县', 442508, 'county'),
(444933, '530828', '22.665993561569', '99.98453733381', '澜沧拉祜族自治县', 442508, 'county'),
(444934, '530829', '22.708423462771', '99.522119995137', '西盟佤族自治县', 442508, 'county'),
(444935, '530902', '23.849570452879', '100.13990768016', '临翔区', 442509, 'county'),
(444936, '530921', '24.610505511987', '99.92091022745', '凤庆县', 442509, 'county'),
(444937, '530922', '24.32740647676', '100.23368014335', '云县', 442509, 'county'),
(444938, '530923', '24.089579787148', '99.427631734164', '永德县', 442509, 'county'),
(444939, '530924', '23.901062771813', '99.005735747996', '镇康县', 442509, 'county'),
(444940, '530925', '23.476856812064', '99.840913908222', '双江拉祜族佤族布朗族傣族自治县', 442509, 'county'),
(444941, '530926', '23.641730399889', '99.434265495794', '耿马傣族佤族自治县', 442509, 'county'),
(444942, '530927', '23.274581274109', '99.270497931724', '沧源佤族自治县', 442509, 'county'),
(444943, '532301', '24.880252472651', '101.32863799918', '楚雄市', 442510, 'county'),
(444944, '532322', '24.535545259465', '101.64032208579', '双柏县', 442510, 'county'),
(444945, '532323', '25.407356738979', '101.59675770511', '牟定县', 442510, 'county'),
(444946, '532324', '25.103522672439', '101.0380121979', '南华县', 442510, 'county'),
(444947, '532325', '25.516954435787', '101.2112377333', '姚安县', 442510, 'county'),
(444948, '532326', '25.947669905565', '101.24291307892', '大姚县', 442510, 'county'),
(444949, '532327', '26.143679315458', '101.56019002604', '永仁县', 442510, 'county'),
(444950, '532328', '25.783195511954', '101.87051082301', '元谋县', 442510, 'county'),
(444951, '532329', '25.731109547237', '102.20117587964', '武定县', 442510, 'county'),
(444952, '532331', '25.185818987516', '102.02612983069', '禄丰县', 442510, 'county'),
(444953, '532501', '24.864212795483', '101.59295163701', '个旧市', 442511, 'county'),
(444954, '532502', '24.864212795483', '101.59295163701', '开远市', 442511, 'county'),
(444955, '532503', '23.338656934664', '103.51669152583', '蒙自市', 442511, 'county'),
(444956, '532504', '24.251508766722', '103.445318018', '弥勒市', 442511, 'county'),
(444957, '532523', '23.21267768326', '103.89743584577', '屏边苗族自治县', 442511, 'county'),
(444958, '532524', '23.987913437671', '102.79065788154', '建水县', 442511, 'county'),
(444959, '532525', '23.789535774797', '102.40773898854', '石屏县', 442511, 'county'),
(444960, '532527', '24.539744740964', '103.75100845447', '泸西县', 442511, 'county'),
(444961, '532528', '23.141055739179', '102.73114873474', '元阳县', 442511, 'county'),
(444962, '532529', '23.211095049214', '102.51563446331', '红河县', 442511, 'county'),
(444963, '532530', '22.996373389905', '103.25176348949', '金平苗族瑶族傣族自治县', 442511, 'county'),
(444964, '532531', '23.091544011399', '102.35379214946', '绿春县', 442511, 'county'),
(444965, '532532', '22.862620371198', '103.67125958623', '河口瑶族自治县', 442511, 'county'),
(444966, '532601', '23.416009535072', '104.03093981246', '文山市', 442512, 'county'),
(444967, '532622', '23.89947126948', '104.5218246496', '砚山县', 442512, 'county'),
(444968, '532623', '23.49211237168', '104.8239553349', '西畴县', 442512, 'county'),
(444969, '532624', '23.433721461415', '105.06044414168', '麻栗坡县', 442512, 'county'),
(444970, '532625', '24.864212795483', '101.59295163701', '马关县', 442512, 'county'),
(444971, '532626', '24.08610830304', '104.34003905514', '丘北县', 442512, 'county'),
(444972, '532627', '24.222835784674', '104.83870730798', '广南县', 442512, 'county'),
(444973, '532628', '23.396160831269', '105.60430973793', '富宁县', 442512, 'county'),
(444974, '532801', '24.864212795483', '101.59295163701', '景洪市', 442513, 'county'),
(444975, '532822', '21.960731038528', '100.33738150932', '勐海县', 442513, 'county'),
(444976, '532823', '21.736659717735', '101.46195902318', '勐腊县', 442513, 'county'),
(444977, '532901', '25.57616489493', '100.15242712507', '大理市', 442514, 'county'),
(444978, '532922', '25.605571782134', '99.898375043674', '漾濞彝族自治县', 442514, 'county'),
(444979, '532923', '25.501610700196', '100.57035927192', '祥云县', 442514, 'county'),
(444980, '532924', '25.875307433534', '100.62753770311', '宾川县', 442514, 'county'),
(444981, '532925', '25.1911077724', '100.58186613328', '弥渡县', 442514, 'county'),
(444982, '532926', '24.903013805535', '100.42490043802', '南涧彝族自治县', 442514, 'county'),
(444983, '532927', '25.343492434694', '100.26759078972', '巍山彝族回族自治县', 442514, 'county'),
(444984, '532928', '25.374646522171', '99.600792211043', '永平县', 442514, 'county'),
(444985, '532929', '25.894118475707', '99.310077976062', '云龙县', 442514, 'county'),
(444986, '532930', '26.294924841349', '100.03831537618', '洱源县', 442514, 'county'),
(444987, '532931', '26.439596125001', '99.750307802477', '剑川县', 442514, 'county'),
(444988, '532932', '26.3354536106', '100.27717450154', '鹤庆县', 442514, 'county'),
(444989, '533102', '24.864212795483', '101.59295163701', '瑞丽市', 442515, 'county'),
(444990, '533103', '24.441239663008', '98.589434287407', '芒市', 442515, 'county'),
(444991, '533122', '24.743716502863', '98.322123152856', '梁河县', 442515, 'county'),
(444992, '533123', '24.706749398739', '97.950762066645', '盈江县', 442515, 'county'),
(444993, '533124', '24.381370607265', '97.965384779773', '陇川县', 442515, 'county'),
(444994, '533301', '26.042265332796', '98.86274058298', '泸水市', 442516, 'county'),
(444995, '533323', '26.996507466856', '98.86865857308', '福贡县', 442516, 'county'),
(444996, '533324', '24.864212795483', '101.59295163701', '贡山独龙族怒族自治县', 442516, 'county'),
(444997, '533325', '26.443506114149', '99.117417482927', '兰坪白族普米族自治县', 442516, 'county'),
(444998, '533401', '27.866680825387', '99.85507644287', '香格里拉市', 442517, 'county'),
(444999, '533422', '28.351417174855', '99.037553971725', '德钦县', 442517, 'county'),
(445000, '533423', '27.45295793965', '99.152722827441', '维西傈僳族自治县', 442517, 'county'),
(445001, '540102', '29.666400338845', '91.168729990815', '城关区', 442518, 'county'),
(445002, '540103', '29.796237760398', '90.8294509947', '堆龙德庆区', 442518, 'county'),
(445003, '540121', '30.116477915324', '91.347042959548', '林周县', 442518, 'county'),
(445004, '540122', '30.424299479353', '90.894814857309', '当雄县', 442518, 'county'),
(445005, '540123', '29.603193843519', '90.095471065982', '尼木县', 442518, 'county'),
(445006, '540124', '29.445004244787', '90.714553495792', '曲水县', 442518, 'county'),
(445007, '540126', '29.747665564603', '91.473900848722', '达孜县', 442518, 'county'),
(445008, '540127', '29.916717538581', '92.031891526243', '墨竹工卡县', 442518, 'county'),
(445009, '540202', '29.268160032655', '88.956062773518', '桑珠孜区', 442519, 'county'),
(445010, '540221', '29.268160032655', '88.956062773518', '南木林县', 442519, 'county'),
(445011, '540222', '29.268160032655', '88.956062773518', '江孜县', 442519, 'county'),
(445012, '540223', '29.268160032655', '88.956062773518', '定日县', 442519, 'county'),
(445013, '540224', '29.268160032655', '88.956062773518', '萨迦县', 442519, 'county'),
(445014, '540225', '29.268160032655', '88.956062773518', '拉孜县', 442519, 'county'),
(445015, '540226', '29.268160032655', '88.956062773518', '昂仁县', 442519, 'county'),
(445016, '540227', '29.268160032655', '88.956062773518', '谢通门县', 442519, 'county'),
(445017, '540228', '28.795794414747', '89.113585077496', '白朗县', 442519, 'county'),
(445018, '540229', '29.247386928273', '90.003352252057', '仁布县', 442519, 'county'),
(445019, '540230', '29.268160032655', '88.956062773518', '康马县', 442519, 'county'),
(445020, '540231', '29.268160032655', '88.956062773518', '定结县', 442519, 'county'),
(445021, '540232', '29.268160032655', '88.956062773518', '仲巴县', 442519, 'county'),
(445022, '540233', '29.268160032655', '88.956062773518', '亚东县', 442519, 'county'),
(445023, '540234', '29.268160032655', '88.956062773518', '吉隆县', 442519, 'county'),
(445024, '540235', '29.268160032655', '88.956062773518', '聂拉木县', 442519, 'county'),
(445025, '540236', '29.268160032655', '88.956062773518', '萨嘎县', 442519, 'county'),
(445026, '540237', '29.268160032655', '88.956062773518', '岗巴县', 442519, 'county'),
(445027, '540302', '31.529862428285', '97.334535331605', '卡若区', 442520, 'county'),
(445028, '540321', '31.780569180094', '98.118022293299', '江达县', 442520, 'county'),
(445029, '540322', '30.736504388643', '98.426429044036', '贡觉县', 442520, 'county'),
(445030, '540323', '31.449065979774', '96.391967073454', '类乌齐县', 442520, 'county'),
(445031, '540324', '31.685241377535', '95.522471824657', '丁青县', 442520, 'county'),
(445032, '540325', '30.618372794064', '97.836858866086', '察雅县', 442520, 'county'),
(445033, '540326', '30.074689806895', '96.879160712332', '八宿县', 442520, 'county'),
(445034, '540327', '29.444978973304', '97.887595370154', '左贡县', 442520, 'county'),
(445035, '540328', '29.51433481024', '98.554769564416', '芒康县', 442520, 'county'),
(445036, '540329', '30.698457783096', '95.916491128377', '洛隆县', 442520, 'county'),
(445037, '540330', '30.96906728495', '94.483413447372', '边坝县', 442520, 'county'),
(445038, '540402', '29.813114097649', '94.375930017941', '巴宜区', 442521, 'county'),
(445039, '540421', '30.032418475925', '93.262111473599', '工布江达县', 442521, 'county'),
(445040, '540422', '29.25246445514', '94.174542402829', '米林县', 442521, 'county'),
(445041, '540423', '28.754711260372', '94.931464872257', '墨脱县', 442521, 'county'),
(445042, '540424', '30.019416163945', '95.345896846749', '波密县', 442521, 'county'),
(445043, '540425', '28.653566547177', '97.229506149139', '察隅县', 442521, 'county'),
(445044, '540426', '29.087717194008', '93.126863813454', '朗县', 442521, 'county'),
(445045, '540502', '29.167743326287', '91.797519071958', '乃东区', 442522, 'county'),
(445046, '540521', '29.266820605393', '91.407298324182', '扎囊县', 442522, 'county'),
(445047, '540522', '29.240130838536', '90.860869080783', '贡嘎县', 442522, 'county'),
(445048, '540523', '29.42065032555', '92.231280965366', '桑日县', 442522, 'county'),
(445049, '540524', '29.013065914396', '91.584038524668', '琼结县', 442522, 'county'),
(445050, '540525', '28.954175694618', '92.237345606421', '曲松县', 442522, 'county'),
(445051, '540526', '28.585924343435', '91.551753322243', '措美县', 442522, 'county'),
(445052, '540527', '28.210518915815', '90.897039902791', '洛扎县', 442522, 'county'),
(445053, '540528', '29.275255680226', '92.727658842783', '加查县', 442522, 'county'),
(445054, '540529', '28.486722025563', '93.019223010095', '隆子县', 442522, 'county'),
(445055, '540530', '27.66585127009', '92.888732384978', '错那县', 442522, 'county'),
(445056, '540531', '28.732790957089', '90.702914725708', '浪卡子县', 442522, 'county'),
(445057, '542421', '31.252314725152', '92.034626453644', '那曲县', 442523, 'county'),
(445058, '542422', '30.668911708265', '92.961316438644', '嘉黎县', 442523, 'county'),
(445059, '542423', '31.44713553851', '93.493424136652', '比如县', 442523, 'county'),
(445060, '542424', '32.249649761022', '92.642153446415', '聂荣县', 442523, 'county'),
(445061, '542425', '33.321681895077', '90.569314254249', '安多县', 442523, 'county'),
(445062, '542426', '31.035234259381', '88.735362127732', '申扎县', 442523, 'county'),
(445063, '542427', '31.592787589013', '94.312549818243', '索县', 442523, 'county'),
(445064, '542428', '31.2181120282', '90.12340113956', '班戈县', 442523, 'county'),
(445065, '542429', '32.198838522007', '94.018948797784', '巴青县', 442523, 'county'),
(445066, '542430', '33.536965980784', '87.654846646508', '尼玛县', 442523, 'county'),
(445067, '542431', '34.102579150651', '88.221417061569', '双湖县', 442523, 'county'),
(445068, '542521', '30.637119738777', '81.530582849369', '普兰县', 442524, 'county'),
(445069, '542522', '31.553648618356', '79.552757074516', '札达县', 442524, 'county'),
(445070, '542523', '32.005501431945', '80.315974443536', '噶尔县', 442524, 'county'),
(445071, '542524', '33.984683055318', '80.719742169545', '日土县', 442524, 'county'),
(445072, '542525', '32.057883395434', '82.03379760961', '革吉县', 442524, 'county'),
(445073, '542526', '33.841204623772', '84.285002167349', '改则县', 442524, 'county'),
(445074, '542527', '30.749850801541', '85.210285811396', '措勤县', 442524, 'county'),
(445075, '610102', '34.271473780191', '108.99153865841', '新城区', 442525, 'county'),
(445076, '610103', '34.255484557671', '108.96625890407', '碑林区', 442525, 'county'),
(445077, '610104', '34.273192373169', '108.91554659362', '莲湖区', 442525, 'county'),
(445078, '610111', '34.303915149746', '109.10875495118', '灞桥区', 442525, 'county'),
(445079, '610112', '34.331331489423', '108.92646199371', '未央区', 442525, 'county'),
(445080, '610113', '34.221414918471', '108.93879042836', '雁塔区', 442525, 'county'),
(445081, '610114', '34.686373084486', '109.31341715315', '阎良区', 442525, 'county'),
(445082, '610115', '34.456277329548', '109.3104528348', '临潼区', 442525, 'county'),
(445083, '610116', '34.066898727937', '108.87425634018', '长安区', 442525, 'county'),
(445084, '610117', '34.513346424398', '109.07152291236', '高陵区', 442525, 'county'),
(445085, '610122', '34.100786931955', '109.42339003093', '蓝田县', 442525, 'county'),
(445086, '610124', '33.953602363476', '108.11354147874', '周至县', 442525, 'county'),
(445087, '610125', '34.00383365133', '108.59248134192', '户县', 442525, 'county'),
(445088, '610202', '35.070041017281', '109.06850448637', '王益区', 442526, 'county'),
(445089, '610203', '35.160933883455', '109.18538598068', '印台区', 442526, 'county'),
(445090, '610204', '35.032000358937', '108.8354996676', '耀州区', 442526, 'county'),
(445091, '610222', '35.383901876425', '109.20440179145', '宜君县', 442526, 'county'),
(445092, '610302', '34.311027035867', '107.10824439064', '渭滨区', 442527, 'county'),
(445093, '610303', '34.40317453128', '107.11761362728', '金台区', 442527, 'county'),
(445094, '610304', '34.482540082479', '106.92358089475', '陈仓区', 442527, 'county'),
(445095, '610322', '34.577025723794', '107.43678881989', '凤翔县', 442527, 'county'),
(445096, '610323', '34.410705264779', '107.68898501373', '岐山县', 442527, 'county'),
(445097, '610324', '34.41197448025', '107.92510162193', '扶风县', 442527, 'county'),
(445098, '610326', '34.150539928532', '107.83384402175', '眉县', 442527, 'county'),
(445099, '610327', '34.876941335239', '106.7730643229', '陇县', 442527, 'county'),
(445100, '610328', '34.766951491427', '107.17797400373', '千阳县', 442527, 'county'),
(445101, '610329', '34.785692691421', '107.71077450759', '麟游县', 442527, 'county'),
(445102, '610330', '33.993251781372', '106.76610395886', '凤县', 442527, 'county'),
(445103, '610331', '33.94297244023', '107.4168652793', '太白县', 442527, 'county'),
(445104, '610402', '34.354285427987', '108.68341537696', '秦都区', 442528, 'county'),
(445105, '610403', '34.290198720106', '108.05873803758', '杨陵区', 442528, 'county'),
(445106, '610404', '34.423852572977', '108.81731239458', '渭城区', 442528, 'county'),
(445107, '610422', '34.703211629709', '108.98069993821', '三原县', 442528, 'county'),
(445108, '610423', '34.608867001852', '108.78075311312', '泾阳县', 442528, 'county'),
(445109, '610424', '34.527672579796', '108.22948289516', '乾县', 442528, 'county'),
(445110, '610425', '34.597853791406', '108.48256879405', '礼泉县', 442528, 'county'),
(445111, '610426', '34.777655607134', '108.13671381859', '永寿县', 442528, 'county'),
(445112, '610427', '35.051834974906', '108.06798630717', '彬县', 442528, 'county'),
(445113, '610428', '35.170581688184', '107.83479969951', '长武县', 442528, 'county'),
(445114, '610429', '35.216832056498', '108.49412543869', '旬邑县', 442528, 'county'),
(445115, '610430', '34.869115751197', '108.57021884883', '淳化县', 442528, 'county'),
(445116, '610431', '34.316553316648', '108.19099325441', '武功县', 442528, 'county'),
(445117, '610481', '34.307609399651', '108.47576040598', '兴平市', 442528, 'county'),
(445118, '610502', '34.553520116268', '109.56474625615', '临渭区', 442529, 'county'),
(445119, '610503', '34.420454032973', '109.82852431434', '华州区', 442529, 'county'),
(445120, '610522', '34.507137056057', '110.29554551613', '潼关县', 442529, 'county'),
(445121, '610523', '34.796840374649', '110.01194954265', '大荔县', 442529, 'county'),
(445122, '610524', '35.208388187296', '110.19110357566', '合阳县', 442529, 'county'),
(445123, '610525', '35.222564490705', '109.90160517601', '澄城县', 442529, 'county'),
(445124, '610526', '34.967696650545', '109.62824611949', '蒲城县', 442529, 'county'),
(445125, '610527', '35.271645917395', '109.5701661435', '白水县', 442529, 'county'),
(445126, '610528', '34.879423511794', '109.23593971498', '富平县', 442529, 'county'),
(445127, '610581', '35.582782138309', '110.39377368099', '韩城市', 442529, 'county'),
(445128, '610582', '34.532717876993', '110.05818818766', '华阴市', 442529, 'county'),
(445129, '610602', '36.575992490922', '109.64860224516', '宝塔区', 442530, 'county'),
(445130, '610603', '36.926615808304', '109.15556502968', '安塞区', 442530, 'county'),
(445131, '610621', '36.543668537707', '110.13820395785', '延长县', 442530, 'county'),
(445132, '610622', '36.88242672637', '110.08409714122', '延川县', 442530, 'county'),
(445133, '610623', '37.231001638593', '109.62229012795', '子长县', 442530, 'county'),
(445134, '610625', '36.753503067474', '108.66244666802', '志丹县', 442530, 'county'),
(445135, '610626', '36.985223505156', '108.12948505986', '吴起县', 442530, 'county'),
(445136, '610627', '36.353544169394', '109.18223909796', '甘泉县', 442530, 'county'),
(445137, '610628', '36.017427088571', '109.04960350294', '富县', 442530, 'county'),
(445138, '610629', '35.744158257757', '109.56098175038', '洛川县', 442530, 'county'),
(445139, '610630', '36.071139382475', '110.19112656349', '宜川县', 442530, 'county'),
(445140, '610631', '35.702635706301', '109.94510069451', '黄龙县', 442530, 'county'),
(445141, '610632', '35.62841424749', '108.95305831904', '黄陵县', 442530, 'county'),
(445142, '610702', '33.187204162513', '107.04616716185', '汉台区', 442531, 'county'),
(445143, '610721', '32.812036143125', '106.96974070111', '南郑县', 442531, 'county'),
(445144, '610722', '33.223582827388', '107.26083703889', '城固县', 442531, 'county'),
(445145, '610723', '33.371586660725', '107.61609308741', '洋县', 442531, 'county'),
(445146, '610724', '32.894902801657', '107.75371241439', '西乡县', 442531, 'county'),
(445147, '610725', '33.243885511916', '106.66457828015', '勉县', 442531, 'county'),
(445148, '610726', '32.914183257269', '106.14087102725', '宁强县', 442531, 'county'),
(445149, '610727', '33.385373533804', '106.16283351204', '略阳县', 442531, 'county'),
(445150, '610728', '32.517415574628', '107.88277388136', '镇巴县', 442531, 'county'),
(445151, '610729', '33.612960467486', '106.95962831346', '留坝县', 442531, 'county'),
(445152, '610730', '33.549939112272', '107.92883622761', '佛坪县', 442531, 'county'),
(445153, '610802', '38.386406641165', '109.64269245717', '榆阳区', 442532, 'county'),
(445154, '610803', '37.80809785663', '109.50067321533', '横山区', 442532, 'county'),
(445155, '610821', '38.829035865956', '110.33126976909', '神木县', 442532, 'county'),
(445156, '610822', '39.187272466272', '110.86693418362', '府谷县', 442532, 'county'),
(445157, '610824', '37.484215805492', '108.81325925462', '靖边县', 442532, 'county'),
(445158, '610825', '37.388791134452', '107.7542930803', '定边县', 442532, 'county'),
(445159, '610826', '37.520861333414', '110.39614377168', '绥德县', 442532, 'county'),
(445160, '610827', '37.8285293893', '110.18690099232', '米脂县', 442532, 'county'),
(445161, '610828', '38.078380449363', '110.37373997088', '佳县', 442532, 'county'),
(445162, '610829', '37.594879166095', '110.69187682122', '吴堡县', 442532, 'county'),
(445163, '610830', '37.187443767355', '110.28929412737', '清涧县', 442532, 'county'),
(445164, '610831', '37.533672486299', '109.8772926757', '子洲县', 442532, 'county'),
(445165, '610902', '32.814464034575', '108.89624328129', '汉滨区', 442533, 'county'),
(445166, '610921', '32.902520654164', '108.49695491326', '汉阴县', 442533, 'county'),
(445167, '610922', '33.065316023116', '108.25051841167', '石泉县', 442533, 'county'),
(445168, '610923', '33.536923574813', '108.45179634269', '宁陕县', 442533, 'county'),
(445169, '610924', '32.448942187958', '108.44482568913', '紫阳县', 442533, 'county'),
(445170, '610925', '32.242470474479', '108.88718069832', '岚皋县', 442533, 'county'),
(445171, '610926', '32.291256567392', '109.2703969757', '平利县', 442533, 'county'),
(445172, '610927', '31.939261564672', '109.45671118087', '镇坪县', 442533, 'county'),
(445173, '610928', '32.902076990198', '109.42357994353', '旬阳县', 442533, 'county'),
(445174, '610929', '32.729864671398', '109.91837503137', '白河县', 442533, 'county'),
(445175, '611002', '33.895484903711', '109.87327053686', '商州区', 442534, 'county'),
(445176, '611021', '34.16568436231', '110.27264280953', '洛南县', 442534, 'county'),
(445177, '611022', '33.684553642613', '110.44379951579', '丹凤县', 442534, 'county'),
(445178, '611023', '33.411702755168', '110.76653283748', '商南县', 442534, 'county'),
(445179, '611024', '33.427684397681', '109.98131923576', '山阳县', 442534, 'county'),
(445180, '611025', '33.380938764863', '109.07737051732', '镇安县', 442534, 'county'),
(445181, '611026', '33.695399655075', '109.28054880136', '柞水县', 442534, 'county'),
(445182, '620102', '36.054008131567', '103.85157116258', '城关区', 442535, 'county'),
(445183, '620103', '35.992495346876', '103.77199449949', '七里河区', 442535, 'county'),
(445184, '620104', '36.106471763711', '103.56267979363', '西固区', 442535, 'county'),
(445185, '620105', '36.11552303805', '103.7191558999', '安宁区', 442535, 'county'),
(445186, '620111', '36.303488391492', '103.06027548801', '红古区', 442535, 'county'),
(445187, '620121', '36.616923609035', '103.25279353462', '永登县', 442535, 'county'),
(445188, '620122', '36.394687882673', '103.89046691011', '皋兰县', 442535, 'county'),
(445189, '620123', '35.999785042711', '104.244289521', '榆中县', 442535, 'county'),
(445190, '620201', '39.802397326734', '98.281634585257', '嘉峪关市', 442229, 'county'),
(445191, '620302', '38.492171668259', '102.32867993808', '金川区', 442536, 'county'),
(445192, '620321', '38.433409665467', '102.03431627622', '永昌县', 442536, 'county'),
(445193, '620402', '36.50182182871', '104.2056493285', '白银区', 442537, 'county'),
(445194, '620403', '36.690350490926', '104.94560896536', '平川区', 442537, 'county'),
(445195, '620421', '36.749103432427', '104.73232686762', '靖远县', 442537, 'county'),
(445196, '620422', '35.963481743844', '105.10186095322', '会宁县', 442537, 'county'),
(445197, '620423', '37.14607896393', '104.06166772084', '景泰县', 442537, 'county'),
(445198, '620502', '34.344448280622', '105.58117092709', '秦州区', 442538, 'county'),
(445199, '620503', '34.520218471455', '106.05204030761', '麦积区', 442538, 'county'),
(445200, '620521', '34.74252730496', '106.14008000664', '清水县', 442538, 'county'),
(445201, '620522', '34.953499919918', '105.69804091114', '秦安县', 442538, 'county'),
(445202, '620523', '34.809420550799', '105.27453175252', '甘谷县', 442538, 'county'),
(445203, '620524', '34.680181826047', '104.88672977677', '武山县', 442538, 'county'),
(445204, '620525', '34.995955449082', '106.28213682392', '张家川回族自治县', 442538, 'county'),
(445205, '620602', '37.916272406996', '102.75947740159', '凉州区', 442539, 'county'),
(445206, '620621', '38.827727985281', '103.20247261178', '民勤县', 442539, 'county'),
(445207, '620622', '37.531271711364', '103.34292346491', '古浪县', 442539, 'county'),
(445208, '620623', '37.280912201076', '102.76116389471', '天祝藏族自治县', 442539, 'county'),
(445209, '620702', '39.010620607403', '100.52207864992', '甘州区', 442540, 'county'),
(445210, '620721', '38.92057106606', '99.32677151937', '肃南裕固族自治县', 442540, 'county'),
(445211, '620722', '38.473163420728', '100.7984292987', '民乐县', 442540, 'county'),
(445212, '620723', '39.347031674446', '100.19122429388', '临泽县', 442540, 'county'),
(445213, '620724', '39.54167477275', '99.607521373805', '高台县', 442540, 'county'),
(445214, '620725', '38.530221367211', '101.23164701727', '山丹县', 442540, 'county'),
(445215, '620802', '35.515774315921', '106.74888681637', '崆峒区', 442541, 'county'),
(445216, '620821', '35.354114511504', '107.44140503868', '泾川县', 442541, 'county'),
(445217, '620822', '35.074478179591', '107.40960562376', '灵台县', 442541, 'county'),
(445218, '620823', '35.249102891785', '107.0037763949', '崇信县', 442541, 'county'),
(445219, '620824', '35.205549578778', '106.60867034279', '华亭县', 442541, 'county'),
(445220, '620825', '35.255968489859', '106.06568568013', '庄浪县', 442541, 'county'),
(445221, '620826', '35.434011745999', '105.67756247251', '静宁县', 442541, 'county'),
(445222, '620902', '39.598374259485', '98.802616462619', '肃州区', 442542, 'county'),
(445223, '620921', '40.382579195507', '99.186587021952', '金塔县', 442542, 'county'),
(445224, '620922', '40.734286870761', '95.804712825239', '瓜州县', 442542, 'county'),
(445225, '620923', '40.67651966541', '96.532550627515', '肃北蒙古族自治县', 442542, 'county'),
(445226, '620924', '39.025889605786', '94.452300569161', '阿克塞哈萨克族自治县', 442542, 'county'),
(445227, '620981', '40.225551802072', '97.461208851694', '玉门市', 442542, 'county'),
(445228, '620982', '40.388771499344', '94.158041766451', '敦煌市', 442542, 'county'),
(445229, '621002', '35.677201418546', '107.67367365978', '西峰区', 442543, 'county'),
(445230, '621021', '36.046137555053', '107.68254775923', '庆城县', 442543, 'county'),
(445231, '621022', '36.616788638949', '107.07217218514', '环县', 442543, 'county'),
(445232, '621023', '36.444471972715', '108.03431226296', '华池县', 442543, 'county'),
(445233, '621024', '36.014259860237', '108.31734058051', '合水县', 442543, 'county'),
(445234, '621025', '35.414731657592', '108.37808719083', '正宁县', 442543, 'county'),
(445235, '621026', '35.571366266826', '108.11417335803', '宁县', 442543, 'county'),
(445236, '621027', '35.787953892327', '107.17722652653', '镇原县', 442543, 'county'),
(445237, '621102', '35.644415174266', '104.63762366893', '安定区', 442544, 'county'),
(445238, '621121', '35.213473762851', '105.19397766216', '通渭县', 442544, 'county'),
(445239, '621122', '35.111801691091', '104.63291319296', '陇西县', 442544, 'county'),
(445240, '621123', '35.139480681839', '104.14632784195', '渭源县', 442544, 'county'),
(445241, '621124', '35.531078701642', '103.91201515484', '临洮县', 442544, 'county'),
(445242, '621125', '34.726750534701', '104.36540253683', '漳县', 442544, 'county'),
(445243, '621126', '34.429644403444', '104.24672610097', '岷县', 442544, 'county'),
(445244, '621202', '33.293917195649', '105.13455295643', '武都区', 442545, 'county'),
(445245, '621221', '33.747296636905', '105.68828896242', '成县', 442545, 'county'),
(445246, '621222', '32.947265418467', '104.78420570271', '文县', 442545, 'county'),
(445247, '621223', '34.013488842529', '104.45282709018', '宕昌县', 442545, 'county'),
(445248, '621224', '33.284990408681', '105.63797390347', '康县', 442545, 'county'),
(445249, '621225', '33.919624520579', '105.33853139264', '西和县', 442545, 'county'),
(445250, '621226', '34.111636708139', '105.06409130212', '礼县', 442545, 'county'),
(445251, '621227', '33.892851204433', '106.03331703965', '徽县', 442545, 'county'),
(445252, '621228', '33.911378923592', '106.40388517582', '两当县', 442545, 'county'),
(445253, '622901', '35.585834814564', '103.2005757611', '临夏市', 442546, 'county'),
(445254, '622921', '35.51871940176', '103.05079063073', '临夏县', 442546, 'county'),
(445255, '622922', '35.258018266344', '103.62902014045', '康乐县', 442546, 'county'),
(445256, '622923', '36.007874959311', '103.22504409432', '永靖县', 442546, 'county'),
(445257, '622924', '35.478027097747', '103.63113999251', '广河县', 442546, 'county'),
(445258, '622925', '35.345732331975', '103.29856767173', '和政县', 442546, 'county'),
(445259, '622926', '35.698472340993', '103.45214513327', '东乡族自治县', 442546, 'county'),
(445260, '622927', '35.710873026896', '102.86781858816', '积石山保安族东乡族撒拉族自治县', 442546, 'county'),
(445261, '623001', '34.997259505739', '103.08564921659', '合作市', 442547, 'county'),
(445262, '623021', '34.742615145611', '103.63190648409', '临潭县', 442547, 'county'),
(445263, '623022', '34.614457775996', '103.39362024363', '卓尼县', 442547, 'county'),
(445264, '623023', '33.634810419739', '104.32632271288', '舟曲县', 442547, 'county'),
(445265, '623024', '34.005620769228', '103.57044621531', '迭部县', 442547, 'county'),
(445266, '623025', '33.850721989423', '101.66897741851', '玛曲县', 442547, 'county'),
(445267, '623026', '34.392608970483', '102.4775472855', '碌曲县', 442547, 'county'),
(445268, '623027', '35.023030857767', '102.50657841215', '夏河县', 442547, 'county'),
(445269, '630102', '36.602116754388', '101.8318647116', '城东区', 442548, 'county'),
(445270, '630103', '36.606648708407', '101.77736110275', '城中区', 442548, 'county'),
(445271, '630104', '36.631635686769', '101.72760342157', '城西区', 442548, 'county'),
(445272, '630105', '36.686367847542', '101.7126636128', '城北区', 442548, 'county'),
(445273, '630121', '37.120688447453', '101.49047766775', '大通回族土族自治县', 442548, 'county'),
(445274, '630122', '36.579759412822', '101.54449443066', '湟中县', 442548, 'county'),
(445275, '630123', '36.636354795068', '101.16317752228', '湟源县', 442548, 'county'),
(445276, '630202', '36.535266451079', '102.45288779666', '乐都区', 442549, 'county'),
(445277, '630203', '36.410605515699', '102.00299964296', '平安区', 442549, 'county'),
(445278, '630222', '36.312743354178', '102.37668874252', '民和回族土族自治县', 442549, 'county'),
(445279, '630223', '36.83096012588', '102.25718820705', '互助土族自治县', 442549, 'county'),
(445280, '630224', '36.063668678141', '102.19192348838', '化隆回族自治县', 442549, 'county'),
(445281, '630225', '35.70370031381', '102.41213008567', '循化撒拉族自治县', 442549, 'county'),
(445282, '632221', '37.45838446475', '101.73134392349', '门源回族自治县', 442550, 'county'),
(445283, '632222', '38.327948935969', '99.711262922683', '祁连县', 442550, 'county'),
(445284, '632223', '37.114748322372', '100.84335509134', '海晏县', 442550, 'county'),
(445285, '632224', '37.556876866897', '99.988382638435', '刚察县', 442550, 'county'),
(445286, '632321', '35.426828765429', '102.07844901848', '同仁县', 442551, 'county'),
(445287, '632322', '35.918696822502', '101.8497538518', '尖扎县', 442551, 'county'),
(445288, '632323', '35.139216924404', '101.43544631681', '泽库县', 442551, 'county');
INSERT INTO `region` (`id`, `code`, `lat`, `lng`, `name`, `parent_id`, `by_type`) VALUES
(445289, '632324', '34.511389737869', '101.55630729533', '河南蒙古族自治县', 442551, 'county'),
(445290, '632521', '36.538342364813', '100.06487566684', '共和县', 442552, 'county'),
(445291, '632522', '35.068401149266', '100.60173869992', '同德县', 442552, 'county'),
(445292, '632523', '36.010503374887', '101.41576189108', '贵德县', 442552, 'county'),
(445293, '632524', '35.54029982537', '99.733309029', '兴海县', 442552, 'county'),
(445294, '632525', '35.698086207737', '100.8846104318', '贵南县', 442552, 'county'),
(445295, '632621', '34.504017087053', '99.794261606919', '玛沁县', 442553, 'county'),
(445296, '632622', '32.909735756429', '100.55042865772', '班玛县', 442553, 'county'),
(445297, '632623', '34.021807573602', '100.1478423084', '甘德县', 442553, 'county'),
(445298, '632624', '33.482696864248', '99.410809497102', '达日县', 442553, 'county'),
(445299, '632625', '33.473902951608', '101.00550828784', '久治县', 442553, 'county'),
(445300, '632626', '34.79757019551', '98.244476788626', '玛多县', 442553, 'county'),
(445301, '632701', '32.906574629922', '96.712350119487', '玉树市', 442554, 'county'),
(445302, '632722', '33.065763568805', '94.30131455019', '杂多县', 442554, 'county'),
(445303, '632723', '33.935171842212', '97.001973841494', '称多县', 442554, 'county'),
(445304, '632724', '34.884438571607', '92.608641864013', '治多县', 442554, 'county'),
(445305, '632725', '32.178288570852', '96.137026010488', '囊谦县', 442554, 'county'),
(445306, '632726', '34.876865391833', '95.140845875343', '曲麻莱县', 442554, 'county'),
(445307, '632801', '35.499761004275', '96.202543672261', '格尔木市', 442555, 'county'),
(445308, '632802', '35.499761004275', '96.202543672261', '德令哈市', 442555, 'county'),
(445309, '632821', '36.902366896364', '98.672630599729', '乌兰县', 442555, 'county'),
(445310, '632822', '36.160067040805', '97.154434686537', '都兰县', 442555, 'county'),
(445311, '632823', '38.051753388375', '98.496512304144', '天峻县', 442555, 'county'),
(445312, '640104', '38.464266316255', '106.38212078081', '兴庆区', 442556, 'county'),
(445313, '640105', '38.55328059311', '106.05555591606', '西夏区', 442556, 'county'),
(445314, '640106', '38.47859072607', '106.24264950801', '金凤区', 442556, 'county'),
(445315, '640121', '38.295049444356', '106.10904802497', '永宁县', 442556, 'county'),
(445316, '640122', '38.687106885054', '106.26651804243', '贺兰县', 442556, 'county'),
(445317, '640181', '37.935174812169', '106.53199999229', '灵武市', 442556, 'county'),
(445318, '640202', '38.967534270672', '106.38721561034', '大武口区', 442557, 'county'),
(445319, '640205', '39.108073765064', '106.61377347013', '惠农区', 442557, 'county'),
(445320, '640221', '38.891511355897', '106.54437947509', '平罗县', 442557, 'county'),
(445321, '640302', '37.76788189318', '106.21901163377', '利通区', 442558, 'county'),
(445322, '640303', '37.374136412893', '106.16687896986', '红寺堡区', 442558, 'county'),
(445323, '640323', '37.625336523188', '107.04976116152', '盐池县', 442558, 'county'),
(445324, '640324', '37.098456634364', '106.24738743176', '同心县', 442558, 'county'),
(445325, '640381', '37.942124742884', '105.96146159918', '青铜峡市', 442558, 'county'),
(445326, '640402', '36.206829483476', '106.25401126905', '原州区', 442559, 'county'),
(445327, '640422', '35.939934380868', '105.72674858688', '西吉县', 442559, 'county'),
(445328, '640423', '35.589137720123', '106.07361128455', '隆德县', 442559, 'county'),
(445329, '640424', '35.529746376118', '106.35402263843', '泾源县', 442559, 'county'),
(445330, '640425', '35.972546262958', '106.66247325572', '彭阳县', 442559, 'county'),
(445331, '640502', '37.360638517868', '105.11127776143', '沙坡头区', 442560, 'county'),
(445332, '640521', '37.360097375108', '105.69186958245', '中宁县', 442560, 'county'),
(445333, '640522', '36.603124838712', '105.67964899633', '海原县', 442560, 'county'),
(445334, '650102', '43.783860225571', '87.632902512248', '天山区', 442561, 'county'),
(445335, '650103', '43.807885738392', '87.545631053987', '沙依巴克区', 442561, 'county'),
(445336, '650104', '43.898324290635', '87.549218796363', '新市区', 442561, 'county'),
(445337, '650105', '43.843907230143', '87.668013771241', '水磨沟区', 442561, 'county'),
(445338, '650106', '43.925789450498', '87.425048810466', '头屯河区', 442561, 'county'),
(445339, '650107', '42.840608943765', '87.895407243798', '达坂城区', 442561, 'county'),
(445340, '650109', '44.070554173621', '87.691186460177', '米东区', 442561, 'county'),
(445341, '650121', '43.419107804291', '87.360244284205', '乌鲁木齐县', 442561, 'county'),
(445342, '650202', '44.302338209135', '84.899916988861', '独山子区', 442562, 'county'),
(445343, '650203', '45.203919246039', '84.926989634948', '克拉玛依区', 442562, 'county'),
(445344, '650204', '45.633602431504', '85.177828513011', '白碱滩区', 442562, 'county'),
(445345, '650205', '46.006575616849', '85.511149264018', '乌尔禾区', 442562, 'county'),
(445346, '650402', '42.508199556726', '89.227738842032', '高昌区', 442563, 'county'),
(445347, '650421', '42.678924820794', '89.266025488642', '鄯善县', 442563, 'county'),
(445348, '650422', '42.678924820794', '89.266025488642', '托克逊县', 442563, 'county'),
(445349, '650502', '42.344467104552', '93.529373012389', '伊州区', 442564, 'county'),
(445350, '650521', '42.127000957642', '85.614899338339', '巴里坤哈萨克自治县', 442564, 'county'),
(445351, '650522', '42.127000957642', '85.614899338339', '伊吾县', 442564, 'county'),
(445352, '652301', '44.175083447891', '87.073618053225', '昌吉市', 442565, 'county'),
(445353, '652302', '44.424103693512', '88.305949271281', '阜康市', 442565, 'county'),
(445354, '652323', '44.380955717336', '86.693166111969', '呼图壁县', 442565, 'county'),
(445355, '652324', '44.503551752404', '86.137668859258', '玛纳斯县', 442565, 'county'),
(445356, '652325', '44.527652368056', '90.11026866784', '奇台县', 442565, 'county'),
(445357, '652327', '44.352913670744', '89.053073195064', '吉木萨尔县', 442565, 'county'),
(445358, '652328', '44.106618870761', '90.823487793346', '木垒哈萨克自治县', 442565, 'county'),
(445359, '652701', '44.844209020588', '81.874284679178', '博乐市', 442566, 'county'),
(445360, '652702', '45.061386641726', '82.895221509025', '阿拉山口市', 442566, 'county'),
(445361, '652722', '44.557568454509', '82.922361700992', '精河县', 442566, 'county'),
(445362, '652723', '44.9688196179', '80.952155808353', '温泉县', 442566, 'county'),
(445363, '652801', '41.705499905674', '85.709417601735', '库尔勒市', 442567, 'county'),
(445364, '652822', '41.819287515207', '84.57895946698', '轮台县', 442567, 'county'),
(445365, '652823', '40.858795810656', '86.866990811172', '尉犁县', 442567, 'county'),
(445366, '652824', '38.973844089966', '89.762772308375', '若羌县', 442567, 'county'),
(445367, '652825', '38.100709422823', '85.506365638195', '且末县', 442567, 'county'),
(445368, '652826', '42.096103707937', '86.07606847595', '焉耆回族自治县', 442567, 'county'),
(445369, '652827', '42.828681293853', '85.200093433149', '和静县', 442567, 'county'),
(445370, '652828', '42.141076067327', '87.588716477325', '和硕县', 442567, 'county'),
(445371, '652829', '41.857897990299', '86.88537877372', '博湖县', 442567, 'county'),
(445372, '652901', '40.349444301113', '81.156013147807', '阿克苏市', 442568, 'county'),
(445373, '652922', '41.582084613402', '80.461878185727', '温宿县', 442568, 'county'),
(445374, '652923', '41.781932892776', '83.459806782673', '库车县', 442568, 'county'),
(445375, '652924', '40.406065186743', '82.925515505452', '沙雅县', 442568, 'county'),
(445376, '652925', '41.365699703636', '81.985216276674', '新和县', 442568, 'county'),
(445377, '652926', '42.04528513577', '81.90123535088', '拜城县', 442568, 'county'),
(445378, '652927', '41.26184731177', '79.281638850531', '乌什县', 442568, 'county'),
(445379, '652928', '40.060787890713', '80.439931783004', '阿瓦提县', 442568, 'county'),
(445380, '652929', '40.456665812896', '78.994696137796', '柯坪县', 442568, 'county'),
(445381, '653001', '42.127000957642', '85.614899338339', '阿图什市', 442569, 'county'),
(445382, '653022', '39.12880375818', '75.814939311182', '阿克陶县', 442569, 'county'),
(445383, '653023', '42.127000957642', '85.614899338339', '阿合奇县', 442569, 'county'),
(445384, '653024', '39.971830894544', '75.146818569489', '乌恰县', 442569, 'county'),
(445385, '653101', '39.513110585312', '76.014342798943', '喀什市', 442570, 'county'),
(445386, '653121', '39.409740997776', '75.754898212901', '疏附县', 442570, 'county'),
(445387, '653122', '39.187644733788', '76.369990308095', '疏勒县', 442570, 'county'),
(445388, '653123', '38.800015263145', '76.368708511974', '英吉沙县', 442570, 'county'),
(445389, '653124', '38.122552699106', '77.226408238901', '泽普县', 442570, 'county'),
(445390, '653125', '38.322587836687', '77.014833164072', '莎车县', 442570, 'county'),
(445391, '653126', '36.993013961904', '77.223630915245', '叶城县', 442570, 'county'),
(445392, '653127', '38.848362710463', '78.242310158759', '麦盖提县', 442570, 'county'),
(445393, '653128', '39.116644959661', '76.989631103308', '岳普湖县', 442570, 'county'),
(445394, '653129', '39.599103145624', '77.231563046663', '伽师县', 442570, 'county'),
(445395, '653130', '39.618107499846', '78.907138995454', '巴楚县', 442570, 'county'),
(445396, '653131', '37.019583155993', '75.843222371735', '塔什库尔干塔吉克自治县', 442570, 'county'),
(445397, '653201', '37.153349739681', '79.915813731039', '和田市', 442571, 'county'),
(445398, '653221', '35.68343240637', '79.354993072983', '和田县', 442571, 'county'),
(445399, '653222', '38.384224145853', '80.047148111072', '墨玉县', 442571, 'county'),
(445400, '653223', '37.228318546135', '78.521850388972', '皮山县', 442571, 'county'),
(445401, '653224', '38.02421985339', '80.741311117244', '洛浦县', 442571, 'county'),
(445402, '653225', '37.084313855547', '81.097995717517', '策勒县', 442571, 'county'),
(445403, '653226', '37.169130186737', '81.995462903271', '于田县', 442571, 'county'),
(445404, '653227', '37.173146693576', '83.352763187', '民丰县', 442571, 'county'),
(445405, '654002', '44.020355819309', '81.289048071493', '伊宁市', 442572, 'county'),
(445406, '654003', '44.559556778997', '85.013934401512', '奎屯市', 442572, 'county'),
(445407, '654004', '44.452519773233', '80.472151391129', '霍尔果斯市', 442572, 'county'),
(445408, '654021', '44.008116880627', '81.756940142999', '伊宁县', 442572, 'county'),
(445409, '654022', '43.63837704253', '81.098298342118', '察布查尔锡伯自治县', 442572, 'county'),
(445410, '654023', '44.309120433611', '80.781158528097', '霍城县', 442572, 'county'),
(445411, '654024', '43.302460015973', '82.445700944329', '巩留县', 442572, 'county'),
(445412, '654025', '43.376951418093', '83.558150188258', '新源县', 442572, 'county'),
(445413, '654026', '42.776878220953', '80.984257123681', '昭苏县', 442572, 'county'),
(445414, '654027', '42.925563515093', '82.006852355503', '特克斯县', 442572, 'county'),
(445415, '654028', '43.816730949065', '83.23110039646', '尼勒克县', 442572, 'county'),
(445416, '654201', '46.75868362968', '82.974880583744', '塔城市', 442573, 'county'),
(445417, '654202', '44.40768749824', '84.277878264967', '乌苏市', 442573, 'county'),
(445418, '654221', '46.590663664844', '84.20931964579', '额敏县', 442573, 'county'),
(445419, '654223', '44.353744632126', '85.474874072005', '沙湾县', 442573, 'county'),
(445420, '654224', '45.656986383852', '83.895484795593', '托里县', 442573, 'county'),
(445421, '654225', '46.004456478157', '82.814799479048', '裕民县', 442573, 'county'),
(445422, '654226', '46.256702546595', '86.217435804622', '和布克赛尔蒙古自治县', 442573, 'county'),
(445423, '654301', '47.890135725749', '87.926214360189', '阿勒泰市', 442574, 'county'),
(445424, '654321', '48.31600661463', '87.235518096505', '布尔津县', 442574, 'county'),
(445425, '654322', '46.536156506123', '89.393483612342', '富蕴县', 442574, 'county'),
(445426, '654323', '46.391693535493', '88.050870553487', '福海县', 442574, 'county'),
(445427, '654324', '48.316559027363', '86.409672960245', '哈巴河县', 442574, 'county'),
(445428, '654325', '46.26815068272', '90.403028447838', '青河县', 442574, 'county'),
(445429, '654326', '47.40631111494', '86.208104287811', '吉木乃县', 442574, 'county'),
(445430, '659001', '42.127000957642', '85.614899338339', '石河子市', 442575, 'county'),
(445431, '659002', '40.615680005185', '81.291736550158', '阿拉尔市', 442575, 'county'),
(445432, '659003', '39.889222881804', '79.198155107904', '图木舒克市', 442575, 'county'),
(445433, '659004', '44.368899479018', '87.565448980181', '五家渠市', 442575, 'county'),
(445434, '659006', '41.806667022365', '85.726306886394', '铁门关市', 442575, 'county'),
(445435, '441900003', '23.034187558639', '113.78983123714', '东城街道办事处', 444410, 'town'),
(445436, '441900004', '23.043023815368', '113.76343399076', '南城街道办事处', 444410, 'town'),
(445437, '441900005', '23.053216729046', '113.74487765252', '万江街道办事处', 444410, 'town'),
(445438, '441900006', '23.044807443255', '113.75560020347', '莞城街道办事处', 444410, 'town'),
(445439, '441900101', '23.105009814264', '113.81981570219', '石碣镇', 444410, 'town'),
(445440, '441900102', '23.11161544389', '113.88072838846', '石龙镇', 444410, 'town'),
(445441, '441900103', '23.082481820485', '113.87562020652', '茶山镇', 444410, 'town'),
(445442, '441900104', '23.094860319218', '113.94654976997', '石排镇', 444410, 'town'),
(445443, '441900105', '23.079042407351', '114.02853135589', '企石镇', 444410, 'town'),
(445444, '441900106', '23.024814163604', '113.97299506133', '横沥镇', 444410, 'town'),
(445445, '441900107', '23.020458678747', '114.10677408198', '桥头镇', 444410, 'town'),
(445446, '441900108', '22.967217838002', '114.15514140002', '谢岗镇', 444410, 'town'),
(445447, '441900109', '23.001562568215', '113.94045100498', '东坑镇', 444410, 'town'),
(445448, '441900110', '22.981050796988', '113.9995109047', '常平镇', 444410, 'town'),
(445449, '441900111', '23.00371616099', '113.88126801675', '寮步镇', 444410, 'town'),
(445450, '441900112', '22.920830687532', '114.08977863491', '樟木头镇', 444410, 'town'),
(445451, '441900113', '22.945659062868', '113.95058428998', '大朗镇', 444410, 'town'),
(445452, '441900114', '22.921615129208', '114.00998764783', '黄江镇', 444410, 'town'),
(445453, '441900115', '22.85030931077', '114.17089092789', '清溪镇', 444410, 'town'),
(445454, '441900116', '22.812790577997', '114.07912123626', '塘厦镇', 444410, 'town'),
(445455, '441900117', '22.752715714135', '114.14334020333', '凤岗镇', 444410, 'town'),
(445456, '441900118', '22.905900630606', '113.84869038983', '大岭山镇', 444410, 'town'),
(445457, '441900119', '22.82104526943', '113.80903565699', '长安镇', 444410, 'town'),
(445458, '441900121', '22.820652927195', '113.67932364446', '虎门镇', 444410, 'town'),
(445459, '441900122', '22.941327853433', '113.67679510848', '厚街镇', 444410, 'town'),
(445460, '441900123', '22.925272079534', '113.62408243967', '沙田镇', 444410, 'town'),
(445461, '441900124', '23.010254161879', '113.6817198771', '道滘镇', 444410, 'town'),
(445462, '441900125', '23.000648523575', '113.61544017114', '洪梅镇', 444410, 'town'),
(445463, '441900126', '23.057083015858', '113.58837988097', '麻涌镇', 444410, 'town'),
(445464, '441900127', '23.061598763352', '113.66263830413', '望牛墩镇', 444410, 'town'),
(445465, '441900128', '23.098649541505', '113.66393529738', '中堂镇', 444410, 'town'),
(445466, '441900129', '23.097244208669', '113.75235767092', '高埗镇', 444410, 'town'),
(445467, '441900401', '22.929023833476', '113.90498400265', '松山湖管委会', 444410, 'town'),
(445468, '441900402', '22.87760055556', '113.5974063853', '虎门港管委会', 444410, 'town'),
(445469, '441900403', '23.06893955376', '113.93401286467', '东莞生态园', 444410, 'town'),
(445470, '442000001', '22.543405990677', '113.39476330111', '石岐区街道办事处', 444411, 'town'),
(445471, '442000002', '22.545177514513', '113.4220600208', '东区街道办事处', 444411, 'town'),
(445472, '442000003', '22.54191612433', '113.47638423802', '火炬开发区街道办事处', 444411, 'town'),
(445473, '442000004', '22.524075818178', '113.36301785094', '西区街道办事处', 444411, 'town'),
(445474, '442000005', '22.503167561223', '113.37678191523', '南区街道办事处', 444411, 'town'),
(445475, '442000006', '22.451434375841', '113.40930659782', '五桂山街道办事处', 444411, 'town'),
(445476, '442000100', '22.668653892986', '113.25710031734', '小榄镇', 444411, 'town'),
(445477, '442000101', '22.716774199156', '113.34579765142', '黄圃镇', 444411, 'town'),
(445478, '442000102', '22.627523677586', '113.50009575036', '民众镇', 444411, 'town'),
(445479, '442000103', '22.708252148345', '113.26390219773', '东凤镇', 444411, 'town'),
(445480, '442000104', '22.628979044198', '113.29799140513', '东升镇', 444411, 'town'),
(445481, '442000105', '22.61867275523', '113.19699907493', '古镇镇', 444411, 'town'),
(445482, '442000106', '22.514758626025', '113.32782880229', '沙溪镇', 444411, 'town'),
(445483, '442000107', '22.260588179755', '113.47430653463', '坦洲镇', 444411, 'town'),
(445484, '442000108', '22.591755080708', '113.39153101373', '港口镇', 444411, 'town'),
(445485, '442000109', '22.682488953575', '113.42447628565', '三角镇', 444411, 'town'),
(445486, '442000110', '22.574461443704', '113.24834255727', '横栏镇', 444411, 'town'),
(445487, '442000111', '22.723520491884', '113.29828270584', '南头镇', 444411, 'town'),
(445488, '442000112', '22.672921927334', '113.35641695563', '阜沙镇', 444411, 'town'),
(445489, '442000113', '22.504952068383', '113.53783552043', '南朗镇', 444411, 'town'),
(445490, '442000114', '22.363791945407', '113.44797569368', '三乡镇', 444411, 'town'),
(445491, '442000115', '22.422651529649', '113.3288721509', '板芙镇', 444411, 'town'),
(445492, '442000116', '22.471242810046', '113.30718743409', '大涌镇', 444411, 'town'),
(445493, '442000117', '22.308297939473', '113.3702758154', '神湾镇', 444411, 'town'),
(445494, '460400100', '19.52127763772', '109.552961497', '那大镇', 444547, 'town'),
(445495, '460400101', '19.531275426074', '109.64738779153', '和庆镇', 444547, 'town'),
(445496, '460400102', '19.415717747276', '109.56235340507', '南丰镇', 444547, 'town'),
(445497, '460400103', '19.513907807686', '109.40607236409', '大成镇', 444547, 'town'),
(445498, '460400104', '19.449779604127', '109.27567597122', '雅星镇', 444547, 'town'),
(445499, '460400105', '19.466430878427', '109.67367138789', '兰洋镇', 444547, 'town'),
(445500, '460400106', '19.823919412162', '109.48786919371', '光村镇', 444547, 'town'),
(445501, '460400107', '19.809926770596', '109.35673473687', '木棠镇', 444547, 'town'),
(445502, '460400108', '19.509695855917', '108.95966166166', '海头镇', 444547, 'town'),
(445503, '460400109', '19.860383433642', '109.27331128257', '峨蔓镇', 444547, 'town'),
(445504, '460400110', '19.793150845121', '109.22582623889', '三都镇', 444547, 'town'),
(445505, '460400111', '19.659803263052', '109.30218328967', '王五镇', 444547, 'town'),
(445506, '460400112', '19.574787798597', '109.33458619886', '白马井镇', 444547, 'town'),
(445507, '460400113', '19.749465537088', '109.35613533713', '中和镇', 444547, 'town'),
(445508, '460400114', '19.644673698003', '109.16975400269', '排浦镇', 444547, 'town'),
(445509, '460400115', '19.709924104042', '109.4680333127', '东成镇', 444547, 'town'),
(445510, '460400116', '19.720234743232', '109.32260133284', '新州镇', 444547, 'town'),
(445511, '460400400', '19.483502718426', '109.4599938961', '国营西培农场', 444547, 'town'),
(445512, '460400404', '19.574664060327', '109.57014071402', '国营西联农场', 444547, 'town'),
(445513, '460400405', '19.463516627749', '109.68307796619', '国营蓝洋农场', 444547, 'town'),
(445514, '460400407', '19.460913902693', '109.31617355538', '国营八一农场', 444547, 'town'),
(445515, '460400499', '19.775218244804', '109.19229656623', '洋浦经济开发区', 444547, 'town'),
(445516, '460400500', '19.574787798597', '109.33458619886', '华南热作学院', 444547, 'town'),
(445517, '620201100', '39.880279868432', '98.458266361225', '新城镇', 445190, 'town'),
(445518, '620201101', '39.813928611238', '98.23795042668', '峪泉镇', 445190, 'town'),
(445519, '620201102', '39.700128295254', '98.384821213543', '文殊镇', 445190, 'town'),
(445520, '620201401', '39.802397326734', '98.281634585257', '雄关区', 445190, 'town'),
(445521, '620201402', '39.802397326734', '98.281634585257', '镜铁区', 445190, 'town'),
(445522, '620201403', '39.914711003026', '98.404752795538', '长城区', 445190, 'town');

-- --------------------------------------------------------

--
-- 表的结构 `robot`
--

CREATE TABLE `robot` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `rid` varchar(255) NOT NULL,
  `robot_name` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL,
  `descriptions` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `role`
--

INSERT INTO `role` (`id`, `name`, `value`, `by_type`, `descriptions`) VALUES
(15, '免费会员', 'ROLE_FREE', 'platform', '平台角色，客服默认角色'),
(14, '注册管理员', 'ROLE_ADMIN', 'platform', '平台角色，注册用户默认角色'),
(16, '付费会员', 'ROLE_VIP', 'platform', '平台角色'),
(17, '客服组长', 'ROLE_WORKGROUP_ADMIN', 'workgroup', '管理客服组，具有组内最高权限'),
(19, '访客', 'ROLE_VISITOR', 'platform', '访客'),
(20, '超级管理员', 'ROLE_SUPER', 'platform', '超级管理员，平台最高权限'),
(21, '客服账号', 'ROLE_WORKGROUP_AGENT', 'workgroup', '工作组内客服角色'),
(22, '试用会员', 'ROLE_TRY', 'platform', '试用会员'),
(23, '第三方代理', 'ROLE_PROXY', 'platform', '第三方代理'),
(24, '质检人员', 'ROLE_WORKGROUP_CHECKER', 'workgroup', '质检人员'),
(25, '普通员工', 'ROLE_MEMBER', 'platform', '普通员工：非客服人员'),
(26, '智能客服', 'ROLE_ROBOT', 'workgroup', '智能客服机器人'),
(27, 'IM注册用户', 'ROLE_USER', 'platform', 'IM注册用户');

-- --------------------------------------------------------

--
-- 表的结构 `role_authority`
--

CREATE TABLE `role_authority` (
  `role_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `role_authority`
--

INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES
(17, 1),
(17, 2),
(17, 3),
(17, 4),
(21, 1);

-- --------------------------------------------------------

--
-- 表的结构 `setting`
--

CREATE TABLE `setting` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `is_web_notification` bit(1) DEFAULT NULL,
  `score` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `statistic`
--

CREATE TABLE `statistic` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `on_date` datetime DEFAULT NULL,
  `users_id` bigint(20) DEFAULT NULL,
  `accept_queue_rate` double DEFAULT NULL,
  `current_thread_count` int(11) DEFAULT NULL,
  `leave_queue_count` int(11) DEFAULT NULL,
  `on_hour` int(11) DEFAULT NULL,
  `online_agent_count` int(11) DEFAULT NULL,
  `queuing_count` int(11) DEFAULT NULL,
  `rate_rate` double DEFAULT NULL,
  `satisfy_rate` double DEFAULT NULL,
  `total_thread_count` int(11) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL,
  `average_leave_queue_length` double DEFAULT NULL,
  `average_queue_time_length` double DEFAULT NULL,
  `max_queue_time_length` bigint(20) DEFAULT NULL,
  `rate_count` int(11) DEFAULT NULL,
  `satisfy_count` int(11) DEFAULT NULL,
  `total_queue_count` int(11) DEFAULT NULL,
  `accept_count_gt5m` double DEFAULT NULL,
  `accept_count_lt1m` double DEFAULT NULL,
  `accept_count_lt3m` double DEFAULT NULL,
  `accept_count_lt5m` double DEFAULT NULL,
  `accept_queue_count` double DEFAULT NULL,
  `accept_rate_gt5m` double DEFAULT NULL,
  `accept_rate_lt1m` double DEFAULT NULL,
  `accept_rate_lt5m` double DEFAULT NULL,
  `accept_rate_lt3m` double DEFAULT NULL,
  `leave_queue_rate` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `statistic`
--

INSERT INTO `statistic` (`id`, `created_at`, `updated_at`, `on_date`, `users_id`, `accept_queue_rate`, `current_thread_count`, `leave_queue_count`, `on_hour`, `online_agent_count`, `queuing_count`, `rate_rate`, `satisfy_rate`, `total_thread_count`, `by_type`, `average_leave_queue_length`, `average_queue_time_length`, `max_queue_time_length`, `rate_count`, `satisfy_count`, `total_queue_count`, `accept_count_gt5m`, `accept_count_lt1m`, `accept_count_lt3m`, `accept_count_lt5m`, `accept_queue_count`, `accept_rate_gt5m`, `accept_rate_lt1m`, `accept_rate_lt5m`, `accept_rate_lt3m`, `leave_queue_rate`) VALUES
(855922, '2019-03-09 04:00:00', '2019-03-09 04:00:00', '2019-03-08 00:00:00', 15, 0, 1, 0, 18, 1, 0, 0, 0, 0, 'hour', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `statistic_detail`
--

CREATE TABLE `statistic_detail` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `accept_rate_gt5m` double DEFAULT NULL,
  `accept_rate_lt1m` double DEFAULT NULL,
  `accept_rate_lt3m` double DEFAULT NULL,
  `accept_rate_lt5m` double DEFAULT NULL,
  `agent_message_count` int(11) DEFAULT NULL,
  `agent_word_count` int(11) DEFAULT NULL,
  `answer_question_rate` double DEFAULT NULL,
  `average_init_response_length` int(11) DEFAULT NULL,
  `average_response_time_length` int(11) DEFAULT NULL,
  `average_time_length` int(11) DEFAULT NULL,
  `on_date` datetime DEFAULT NULL,
  `dimension_type` varchar(255) DEFAULT NULL,
  `on_hour` int(11) DEFAULT NULL,
  `long_thread_count` int(11) DEFAULT NULL,
  `max_current_count` int(11) DEFAULT NULL,
  `response_rate_lt30s` double DEFAULT NULL,
  `slow_response_count` int(11) DEFAULT NULL,
  `solve_once_rate` double DEFAULT NULL,
  `time_type` varchar(255) DEFAULT NULL,
  `total_message_count` int(11) DEFAULT NULL,
  `visitor_message_count` int(11) DEFAULT NULL,
  `users_id` bigint(20) DEFAULT NULL,
  `accept_count_gt5m` int(11) DEFAULT NULL,
  `accept_count_lt1m` int(11) DEFAULT NULL,
  `accept_count_lt3m` int(11) DEFAULT NULL,
  `accept_count_lt5m` int(11) DEFAULT NULL,
  `response_count_lt30s` int(11) DEFAULT NULL,
  `agent_thread_count` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `status`
--

CREATE TABLE `status` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `client` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `status`
--

INSERT INTO `status` (`id`, `created_at`, `updated_at`, `client`, `status`, `users_id`, `session_id`, `by_type`) VALUES
(855917, '2019-03-09 03:28:22', '2019-03-09 03:28:22', NULL, 'disconnected', 15, '0va0onvm', NULL),
(855918, '2019-03-09 03:28:24', '2019-03-09 03:28:24', 'web', 'login', 15, NULL, NULL),
(855919, '2019-03-09 03:28:25', '2019-03-09 03:28:25', NULL, 'connected', 15, 'ruz1f04d', NULL),
(855920, '2019-03-09 03:59:02', '2019-03-09 03:59:02', 'web', 'login', 15, NULL, NULL),
(855921, '2019-03-09 03:59:03', '2019-03-09 03:59:03', NULL, 'connected', 15, 'pp12ffsi', NULL),
(855923, '2019-03-09 04:00:27', '2019-03-09 04:00:27', NULL, 'disconnected', 15, 'pp12ffsi', NULL),
(855924, '2019-03-09 04:00:28', '2019-03-09 04:00:28', 'web', 'login', 15, NULL, NULL),
(855925, '2019-03-09 04:00:33', '2019-03-09 04:00:33', NULL, 'connected', 15, 'dqfyuu3q', NULL),
(855926, '2019-03-09 04:09:00', '2019-03-09 04:09:00', NULL, 'disconnected', 15, 'dqfyuu3q', NULL),
(855927, '2019-03-09 04:09:01', '2019-03-09 04:09:01', 'web', 'login', 15, NULL, NULL),
(855928, '2019-03-09 04:09:02', '2019-03-09 04:09:02', NULL, 'connected', 15, 'gozvoxvf', NULL),
(855929, '2019-03-09 04:55:20', '2019-03-09 04:55:20', NULL, 'disconnected', 15, 'gozvoxvf', NULL);

-- --------------------------------------------------------

--
-- 表的结构 `subscribe`
--

CREATE TABLE `subscribe` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `leaved` tinyint(1) DEFAULT '0',
  `thread_id` bigint(20) DEFAULT NULL,
  `users_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `synonyms`
--

CREATE TABLE `synonyms` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `standard` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `is_synonym` bit(1) DEFAULT NULL,
  `sid` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `synonyms_related`
--

CREATE TABLE `synonyms_related` (
  `standard_id` bigint(20) NOT NULL,
  `synonym_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `taboo`
--

CREATE TABLE `taboo` (
  `id` bigint(20) NOT NULL COMMENT '主键，自动生成',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `standard` varchar(255) DEFAULT NULL COMMENT '标准词',
  `is_synonym` tinyint(1) DEFAULT NULL COMMENT '是否同义词',
  `tid` varchar(100) NOT NULL COMMENT '唯一标识',
  `users_id` bigint(20) NOT NULL COMMENT '创建人'
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `taboo_related`
--

CREATE TABLE `taboo_related` (
  `standard_id` bigint(20) NOT NULL COMMENT '主键，自动生成',
  `taboo_id` bigint(20) NOT NULL COMMENT '主键，自动生成'
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `tag`
--

CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `is_robot` bit(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `template`
--

CREATE TABLE `template` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `thread`
--

CREATE TABLE `thread` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `closed_at` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `started_at` datetime DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `unread_count` int(11) DEFAULT NULL,
  `queue_id` bigint(20) DEFAULT NULL,
  `workgroup_id` bigint(20) DEFAULT NULL,
  `is_closed` bit(1) DEFAULT NULL,
  `visitor_id` bigint(20) DEFAULT NULL,
  `session_id` varchar(255) DEFAULT NULL,
  `agent_id` bigint(20) DEFAULT NULL,
  `is_auto_close` bit(1) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL,
  `is_current` tinyint(1) DEFAULT '0',
  `token` varchar(255) DEFAULT NULL,
  `contact_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `tid` varchar(255) NOT NULL,
  `is_appointed` bit(1) DEFAULT NULL,
  `is_rated` bit(1) DEFAULT NULL,
  `close_type` varchar(255) DEFAULT NULL,
  `client` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `thread_agent`
--

CREATE TABLE `thread_agent` (
  `thread_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `thread_deleted`
--

CREATE TABLE `thread_deleted` (
  `thread_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `thread_disturb`
--

CREATE TABLE `thread_disturb` (
  `thread_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `thread_top`
--

CREATE TABLE `thread_top` (
  `thread_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `thread_unread`
--

CREATE TABLE `thread_unread` (
  `thread_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- 表的结构 `transfer`
--

CREATE TABLE `transfer` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_accepted` tinyint(1) DEFAULT '0',
  `actioned_at` datetime DEFAULT NULL,
  `from_client` varchar(255) DEFAULT NULL,
  `t_tid` varchar(255) NOT NULL,
  `to_client` varchar(255) DEFAULT NULL,
  `from_user_id` bigint(20) DEFAULT NULL,
  `thread_id` bigint(20) DEFAULT NULL,
  `to_user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `url`
--

CREATE TABLE `url` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `description` longtext,
  `keywords` longtext
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_enabled` bit(1) NOT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `passwords` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `sub_domain` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `client` varchar(255) DEFAULT NULL,
  `max_thread_count` int(11) DEFAULT '10',
  `users_id` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  `is_robot` tinyint(1) DEFAULT '0',
  `welcome_tip` varchar(255) DEFAULT NULL,
  `is_auto_reply` tinyint(1) DEFAULT '0',
  `auto_reply_content` varchar(255) DEFAULT NULL,
  `accept_status` varchar(255) DEFAULT NULL,
  `connection_status` varchar(255) DEFAULT NULL,
  `im_status` varchar(255) DEFAULT NULL,
  `device_token` varchar(255) DEFAULT NULL,
  `num` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `users`
--

INSERT INTO `users` (`id`, `created_at`, `updated_at`, `avatar`, `email`, `is_enabled`, `mobile`, `nickname`, `passwords`, `username`, `real_name`, `sub_domain`, `company`, `client`, `max_thread_count`, `users_id`, `description`, `uuid`, `is_deleted`, `is_robot`, `welcome_tip`, `is_auto_reply`, `auto_reply_content`, `accept_status`, `connection_status`, `im_status`, `device_token`, `num`) VALUES
(15, '2018-07-13 12:04:38', '2019-03-09 04:55:20', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/admin_default_avatar.png', '270580156@qq.com', b'1', '13311156272', '客服001', '$2a$10$E3v2k1L.32GQQXo18M5SSudEXzX349qcxkuQ6vT2aqPoKl8noTAJu', '270580156@qq.com', '超级管理员', 'vip', NULL, NULL, 1, NULL, '个性签名1', '201808221551193', 0, 0, '您好，有什么可以帮您的？', 0, '无自动回复', 'online', 'disconnected', NULL, NULL, NULL),
(19, '2018-07-18 00:00:00', '2018-07-18 00:00:00', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/admin_default_avatar.png', 'bytedesk_notification@bytedesk.com', b'1', '13311158888', '系统通知', '0', 'bytedesk_notification', '系统通知', 'vip', 'bytedesk', '', 10, NULL, NULL, '201808221551195', 0, 0, NULL, 0, '无自动回复', NULL, NULL, NULL, NULL, NULL),
(36242, '2018-07-31 16:41:21', '2019-01-20 10:11:18', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/chrome_default_avatar.png', '201807311641201@bytedesk.com', b'1', NULL, '201807311641201', '$2a$10$NYSoWk3AWfqbN75MHXOGTOvbCb1SNqUAenT5Igi5UJ7ivsfeR7NxG', '201807311641201', NULL, 'vip', NULL, 'web', 10, NULL, NULL, '201808221551196', 0, 0, NULL, 0, '无自动回复', NULL, 'disconnected', NULL, NULL, NULL),
(169128, '2018-08-15 16:55:57', '2019-03-08 05:38:33', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/admin_default_avatar.png', '123456@qq.com', b'1', NULL, '小王', '$2a$10$lJ/qT.Hxzwt5HnDmP1DAzOxzdfTH77cSnASRGRjPIgghOrySt0ZRu', 'test2@vip', '王晓晓', 'vip', NULL, NULL, 1, 15, '个性签名', '201808221551197', 0, 0, '您好，有什么可以帮您的？', 0, '无自动回复', 'online', 'disconnected', NULL, NULL, NULL),
(161435, '2018-08-10 15:41:20', '2019-03-09 02:53:05', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/admin_default_avatar.png', '123456@aa.com', b'1', NULL, '小例子2', '$2a$10$tcqye4afuSwIk8TDSR8Qxe7TrIks1E5/KEJzlaKi8v8zqzs5ZYzty', 'test1@vip', '李大大', 'vip', NULL, NULL, 9, 15, '个性签名', '201808221551198', 0, 0, '您好，有什么可以帮您的？', 0, '无自动回复', 'online', 'disconnected', NULL, NULL, NULL),
(169157, '2018-08-15 17:37:33', '2019-03-04 17:39:52', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/admin_default_avatar.png', '123456@qq.com', b'1', NULL, '小张', '$2a$10$QDmZJadaTaOHKV7N6YVnruaUQgEnFAWJ3hv07C81OWKnSIiDuZAIK', 'test3@vip', '张小小', 'vip', NULL, NULL, 9, 15, '个性签名', '201808221551199', 0, 0, '您好，有什么可以帮您的？', 0, '无自动回复', 'logout', 'disconnected', NULL, NULL, NULL),
(194669, '2018-09-19 23:35:03', '2018-09-19 23:35:03', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/agent_default_avatar.png', '201809192335036@bytedesk.com', b'1', NULL, '智能客服', '201809192335036', '201809192335036', '智能客服', '335031', NULL, NULL, 10, 194665, '智能客服描述', '201809192335035', 0, 1, '您好，我是智能助理，请问有什么可以帮您的？', 0, '无自动回复', NULL, NULL, NULL, NULL, NULL),
(194670, '2018-09-19 23:35:03', '2018-09-20 07:16:49', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/agent_default_avatar.png', '201809192335037@bytedesk.com', b'1', NULL, '小薇', '201809192335037', '201809192335037@vip', '智能客服', 'vip', NULL, NULL, 10, 15, '智能客服描述', '201809192335038', 0, 1, 'Hello，欢迎来到大学长，我是智能学长小助手，请输入您的问题哦。', 0, '无自动回复', NULL, NULL, NULL, NULL, NULL),
(402937, '2018-10-20 17:58:13', '2019-01-13 22:09:28', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/admin_default_avatar.png', NULL, b'1', NULL, '白玉红', '$2a$10$NAPYxhZTzW0zAi4FVGbcRuVc21M7Oks8xnBrLXqtv/X9gRUjgkR0S', '10984@vip', '白玉红', 'vip', NULL, NULL, 10, 15, '个性签名', '201810201758122', 0, 0, '您好，有什么可以帮您的？', 0, '无自动回复', 'logout', 'disconnected', NULL, NULL, NULL),
(402938, '2018-10-20 17:58:13', '2018-12-07 12:34:58', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/admin_default_avatar.png', NULL, b'1', NULL, '马萧瑶', '$2a$10$Jf9Yq7zwv.Q7d5EVBzh/P.HTHySy44HD5D32r5UhPbrkA8X/s2Af2', '210303@vip', '马萧瑶', 'vip', NULL, NULL, 10, 15, '个性签名', '201810201758123', 0, 0, '您好，有什么可以帮您的？', 0, '无自动回复', 'online', 'disconnected', NULL, NULL, NULL),
(402939, '2018-10-20 17:58:13', '2018-10-20 17:58:13', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/admin_default_avatar.png', NULL, b'1', NULL, '申辰', '$2a$10$izqYxiZ1PhXE.mnQhT1Za.z1LY4UfLV3YjtGhxzU2wagY72w1wO3i', '10375@vip', '申辰', 'vip', NULL, NULL, 10, 15, '个性签名', '201810201758124', 0, 0, '您好，有什么可以帮您的？', 0, '无自动回复', NULL, NULL, NULL, NULL, NULL),
(402940, '2018-10-20 17:58:14', '2019-01-05 17:22:06', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/admin_default_avatar.png', NULL, b'1', NULL, '李妍', '$2a$10$FaCtcYv1rVnJsRUBdXBDjOZKSdLVrS/JStC0h4W3D3u6Cbpto7HT.', '10910@vip', '李妍', 'vip', NULL, NULL, 10, 15, '个性签名', '201810201758125', 0, 0, '您好，有什么可以帮您的？', 0, '无自动回复', 'logout', 'disconnected', NULL, NULL, NULL),
(402941, '2018-10-20 17:58:14', '2019-02-22 19:53:17', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/admin_default_avatar.png', NULL, b'1', NULL, '李益', '$2a$10$5HWR7TkLRSO6MW8YEoCZs.DeYUUmsTa3xH/FxnWTYvynca1Uxj3DS', '11301@vip', '李益', 'vip', NULL, NULL, 10, 15, '个性签名', '201810201758126', 0, 0, '您好，有什么可以帮您的？', 0, '无自动回复', 'logout', 'disconnected', NULL, NULL, NULL),
(402942, '2018-10-20 17:58:14', '2019-01-03 16:06:31', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/admin_default_avatar.png', NULL, b'1', NULL, '李靓竹', '$2a$10$Oh7H6SU/l9yMpby6v0LwCO9tv6CzsbzZBsfngJkqZdVmVNpRbXTI2', '11815@vip', '李靓竹', 'vip', NULL, NULL, 1, 15, '个性签名', '201810201758131', 0, 0, '您好，有什么可以帮您的？', 0, '无自动回复', 'logout', 'disconnected', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `users_authority`
--

CREATE TABLE `users_authority` (
  `users_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `users_follow`
--

CREATE TABLE `users_follow` (
  `users_id` bigint(20) NOT NULL,
  `follow_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `users_follow`
--

INSERT INTO `users_follow` (`users_id`, `follow_id`) VALUES
(402937, 161435),
(161435, 402937);

-- --------------------------------------------------------

--
-- 表的结构 `users_robot`
--

CREATE TABLE `users_robot` (
  `users_id` bigint(20) NOT NULL,
  `robot_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `users_robot`
--

INSERT INTO `users_robot` (`users_id`, `robot_id`) VALUES
(15, 194670),
(194395, 194399),
(194630, 194634),
(194642, 194646),
(194665, 194669),
(362757, 362761),
(826098, 826102),
(826116, 826120),
(826122, 826126),
(834086, 834090),
(839320, 839324),
(839334, 839338),
(854807, 854811);

-- --------------------------------------------------------

--
-- 表的结构 `users_role`
--

CREATE TABLE `users_role` (
  `users_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `users_role`
--

INSERT INTO `users_role` (`users_id`, `role_id`) VALUES
(6, 15),
(15, 14),
(15, 20),
(161413, 15),
(161413, 21),
(161435, 17),
(169128, 21),
(169157, 17),
(194353, 14),
(194353, 17),
(194364, 14),
(194364, 17),
(194370, 14),
(194370, 17),
(194377, 14),
(194377, 17),
(194389, 14),
(194389, 17),
(194665, 14),
(194665, 22),
(194669, 26),
(194670, 26),
(402937, 21),
(402938, 21),
(402939, 21),
(402940, 21),
(402941, 21),
(402942, 21);

-- --------------------------------------------------------

--
-- 表的结构 `users_tag`
--

CREATE TABLE `users_tag` (
  `users_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `validations`
--

CREATE TABLE `validations` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `by_type` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `is_valid` bit(1) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `validations`
--

INSERT INTO `validations` (`id`, `created_at`, `updated_at`, `status`, `by_type`, `uuid`, `is_valid`, `message`, `note`, `end_date`, `start_date`) VALUES
(1, '2018-10-18 00:00:00', '2018-10-18 00:00:00', 'normal', 'server', 'daxuezhang', b'1', '验证失败，请联系管理员', '私有部署用户', '2018-10-18 00:00:00', '2018-10-18 00:00:00');

-- --------------------------------------------------------

--
-- 表的结构 `wechat`
--

CREATE TABLE `wechat` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `aes_key` varchar(255) DEFAULT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  `app_secret` varchar(255) DEFAULT NULL,
  `authorization_code` varchar(255) DEFAULT NULL,
  `authorization_code_expired` varchar(255) DEFAULT NULL,
  `authorize` bit(1) DEFAULT NULL,
  `authorizer_access_token` varchar(255) DEFAULT NULL,
  `authorizer_app_id` varchar(255) DEFAULT NULL,
  `authorizer_refresh_token` varchar(255) DEFAULT NULL,
  `business_info_open_card` int(11) DEFAULT NULL,
  `business_info_open_pay` int(11) DEFAULT NULL,
  `business_info_open_scan` int(11) DEFAULT NULL,
  `business_info_open_shake` int(11) DEFAULT NULL,
  `business_info_open_store` int(11) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `encode_type` varchar(255) DEFAULT NULL,
  `expires_in` int(11) DEFAULT NULL,
  `head_img` varchar(255) DEFAULT NULL,
  `is_mini_program` bit(1) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `original_id` varchar(255) DEFAULT NULL,
  `principal_name` varchar(255) DEFAULT NULL,
  `qrcode_url` varchar(255) DEFAULT NULL,
  `service_type_info` int(11) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `verify_type_info` int(11) DEFAULT NULL,
  `wid` varchar(255) NOT NULL,
  `mini_program_info_id` bigint(20) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `workgroup_id` bigint(20) DEFAULT NULL,
  `wx_number` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `wechat`
--

INSERT INTO `wechat` (`id`, `created_at`, `updated_at`, `aes_key`, `alias`, `app_id`, `app_secret`, `authorization_code`, `authorization_code_expired`, `authorize`, `authorizer_access_token`, `authorizer_app_id`, `authorizer_refresh_token`, `business_info_open_card`, `business_info_open_pay`, `business_info_open_scan`, `business_info_open_shake`, `business_info_open_store`, `category`, `company`, `data_type`, `description`, `encode_type`, `expires_in`, `head_img`, `is_mini_program`, `nick_name`, `original_id`, `principal_name`, `qrcode_url`, `service_type_info`, `signature`, `token`, `url`, `user_name`, `verify_type_info`, `wid`, `mini_program_info_id`, `users_id`, `workgroup_id`, `wx_number`) VALUES
(556334, '2018-10-29 16:32:22', '2018-12-16 17:14:43', 'OadnyZnUEPiscOA4DsjCSCW4TY4uJQ3zj3aSB2XKDVg', NULL, 'wx89711c5fd9910289', '834605c44811aed6c6878608f45c7dc2', NULL, NULL, b'0', '16_SdJPASUTbHYe2UuDmf_2KrCkv-hS0JlIGKjv7f_A4FQT9hi8UpbvBx7u16AogZYNG4PgrbovXdM9QsorKtav6l85pr8rAdmdhApQfU0-MCq2nE1fbtllPI53tHdaqHz5lZklXnpyFg8OepL6HBPbAIAPJX', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '北京微语天下科技有限公司', NULL, '移动终端在线客服，app在线客服，提供SDK和客户端', '3', 0, NULL, b'0', '吾协', NULL, NULL, NULL, NULL, NULL, '201810291632211', 'https://wechat.bytedesk.com/wechat/mp/push/201810291632211', 'gh_93a8091893ed', NULL, '201810291632211', NULL, 15, 17, 'appkefu'),
(562614, '2018-10-29 23:02:00', '2018-10-29 23:02:00', 'YLOfuQ2DfNTx58PqdJoO4cceZOeT8Dv1NYBoZ1ftSjJ', NULL, 'wxaa30599823640966', 'ccfe985bb0150527df87874ab0a34c70', NULL, NULL, b'0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '北京微语天下科技有限公司', '2', '全渠道智能云客服手机客服端', '3', 0, NULL, b'1', '吾协', NULL, NULL, NULL, NULL, NULL, '201810292301591', 'https://wechat.bytedesk.com/wechat/mini/push/201810292301591', 'gh_a2745083632e', NULL, '201810292301592', NULL, 15, 17, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `wechat_funcinfo`
--

CREATE TABLE `wechat_funcinfo` (
  `wechat_id` bigint(20) NOT NULL,
  `funcinfo_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `wechat_menu`
--

CREATE TABLE `wechat_menu` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `my_key` varchar(255) DEFAULT NULL,
  `media_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `page_path` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `by_type` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `wechat_userinfo`
--

CREATE TABLE `wechat_userinfo` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `groupid` varchar(255) DEFAULT NULL,
  `headimgurl` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `sex` bit(1) DEFAULT NULL,
  `subscribe` bit(1) DEFAULT NULL,
  `subscribe_time` time DEFAULT NULL,
  `unionid` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) NOT NULL,
  `wechat_id` bigint(20) NOT NULL,
  `group_id` int(11) DEFAULT NULL,
  `head_img_url` varchar(255) DEFAULT NULL,
  `union_id` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `wechat_usertag`
--

CREATE TABLE `wechat_usertag` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tag_id` int(11) DEFAULT NULL,
  `wechat_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `workgroup`
--

CREATE TABLE `workgroup` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `accept_tip` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `offline_tip` varchar(255) DEFAULT NULL,
  `route_type` varchar(255) DEFAULT NULL,
  `welcome_tip` varchar(255) DEFAULT NULL,
  `users_id` bigint(20) DEFAULT NULL,
  `is_default_robot` tinyint(1) DEFAULT '0',
  `is_force_rate` tinyint(1) DEFAULT '0',
  `is_offline_robot` tinyint(1) DEFAULT '0',
  `wid` varchar(255) NOT NULL,
  `non_working_time_tip` varchar(255) DEFAULT NULL,
  `is_default` tinyint(1) DEFAULT '0',
  `is_department` bit(1) DEFAULT NULL,
  `about` varchar(255) DEFAULT NULL,
  `slogan` longtext,
  `company_id` bigint(20) DEFAULT NULL,
  `on_duty_workgroup_id` bigint(20) DEFAULT NULL,
  `questionnaire_id` bigint(20) DEFAULT NULL,
  `is_auto_pop` tinyint(1) DEFAULT '0',
  `pop_after_time_length` int(11) DEFAULT '10',
  `auto_close_tip` varchar(255) DEFAULT NULL,
  `close_tip` varchar(255) DEFAULT NULL,
  `admin_id` bigint(20) DEFAULT NULL,
  `max_queue_count` int(11) DEFAULT NULL,
  `max_queue_count_exceed_tip` varchar(255) DEFAULT NULL,
  `max_queue_second` int(11) DEFAULT NULL,
  `max_queue_second_exceed_tip` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `workgroup`
--

INSERT INTO `workgroup` (`id`, `created_at`, `updated_at`, `accept_tip`, `avatar`, `description`, `nickname`, `offline_tip`, `route_type`, `welcome_tip`, `users_id`, `is_default_robot`, `is_force_rate`, `is_offline_robot`, `wid`, `non_working_time_tip`, `is_default`, `is_department`, `about`, `slogan`, `company_id`, `on_duty_workgroup_id`, `questionnaire_id`, `is_auto_pop`, `pop_after_time_length`, `auto_close_tip`, `close_tip`, `admin_id`, `max_queue_count`, `max_queue_count_exceed_tip`, `max_queue_second`, `max_queue_second_exceed_tip`) VALUES
(17, '2018-07-13 12:04:38', '2019-02-18 22:17:12', '您好，有什么可以帮您的？', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/workgroup_default_avatar.png', '这是一个默认工作组，系统自动生成', '萝卜丝', '当前无客服在线，请自助查询或留言', 'robin', '您好，请稍候，客服将很快为您服务', 15, 0, 0, 0, '201807171659201', '非工作时间提示语', 1, b'0', NULL, NULL, NULL, NULL, NULL, 0, 10, NULL, NULL, 15, 10, '人工繁忙，请稍后再试', 60, '很抱歉，目前人工服务全忙，小M尽快为您转接。如需自助服务请回复序号，退出排队后继续向小M提问：[1]继续等待 [2]退出排队'),
(161483, '2018-08-10 18:19:30', '2019-02-24 03:00:46', '您好有什么可以帮您的?', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/workgroup_default_avatar.png', '这是一个默认工作组，系统自动生成', '售前', '当前客服不在线，请留言', 'robin', '正在为您分配客服，请稍后', 15, 0, 0, 0, '201808101819291', '非工作时间提示语', 0, b'0', NULL, NULL, NULL, 17, 1, 0, 10, NULL, NULL, 169128, 10, '人工繁忙，请稍后再试', 60, '很抱歉，目前人工服务全忙，小M尽快为您转接。如需自助服务请回复序号，退出排队后继续向小M提问：[1]继续等待 [2]退出排队'),
(187513, '2018-09-06 17:16:23', '2019-01-01 11:30:56', '您好有什么可以帮您的?12', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/workgroup_default_avatar.png', '这是一个默认工作组，系统自动生成', '链雪', '当前客服不在线，请留言12', 'robin', '正在为您分配客服，请稍后12', 15, 0, 0, 0, '201809061716221', '当前非工作时间，请自助查询或留言', 0, b'0', NULL, NULL, NULL, 17, NULL, 0, 10, NULL, NULL, 161435, 10, '人工繁忙，请稍后再试', 60, '很抱歉，目前人工服务全忙，小M尽快为您转接。如需自助服务请回复序号，退出排队后继续向小M提问：[1]继续等待 [2]退出排队'),
(402935, '2018-10-20 17:58:13', '2019-02-24 01:46:19', '您好，有什么可以帮您的？', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/workgroup_default_avatar.png', '这是一个默认工作组，系统自动生成', '北京-澳大利亚', '当前无客服在线，请自助查询或留言', 'robin', '您好，请稍候，客服将很快为您服务', 15, 0, 0, 0, '201810201758121', '当前非工作时间，请自助查询或留言', 0, b'0', '关于我们', '全心全意为您服务', 216819, NULL, NULL, 0, 10, '长时间没有对话，系统自动关闭会话', '客服关闭会话', 402941, 10, '人工繁忙，请稍后再试', 60, '很抱歉，目前人工服务全忙，小M尽快为您转接。如需自助服务请回复序号，退出排队后继续向小M提问：[1]继续等待 [2]退出排队'),
(402953, '2018-10-20 17:58:15', '2019-02-24 01:46:14', '您好，有什么可以帮您的？', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/workgroup_default_avatar.png', '这是一个默认工作组，系统自动生成', '北京-新西兰', '当前无客服在线，请自助查询或留言', 'robin', '您好，请稍候，客服将很快为您服务', 15, 0, 0, 0, '201810201758144', '当前非工作时间，请自助查询或留言', 0, b'0', '关于我们', '全心全意为您服务', 216819, 17, NULL, 0, 10, '长时间没有对话，系统自动关闭会话', '客服关闭会话', 402939, 10, '人工繁忙，请稍后再试', 60, '很抱歉，目前人工服务全忙，小M尽快为您转接。如需自助服务请回复序号，退出排队后继续向小M提问：[1]继续等待 [2]退出排队'),
(402960, '2018-10-20 17:58:16', '2019-02-24 01:45:57', '您好，有什么可以帮您的？', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/workgroup_default_avatar.png', '这是一个默认工作组，系统自动生成', '北京-加拿大', '当前无客服在线，请自助查询或留言', 'robin', '您好，请稍候，客服将很快为您服务', 15, 0, 0, 0, '201810201758151', '当前非工作时间，请自助查询或留言', 0, b'0', '关于我们', '全心全意为您服务', 216819, 17, NULL, 0, 10, '长时间没有对话，系统自动关闭会话', '客服关闭会话', 402938, 10, '人工繁忙，请稍后再试', 60, '很抱歉，目前人工服务全忙，小M尽快为您转接。如需自助服务请回复序号，退出排队后继续向小M提问：[1]继续等待 [2]退出排队'),
(799261, '2018-12-20 00:05:36', '2019-01-01 11:26:46', '您好有什么可以帮您的?', 'https://chainsnow.oss-cn-shenzhen.aliyuncs.com/avatars/workgroup_default_avatar.png', '这是一个默认工作组，系统自动生成', '测试工作组', '当前客服不在线，请留言', 'robin', '正在为您分配客服，请稍后', 15, 0, 0, 0, '201812200005351', '当前非工作时间，请自助查询或留言', 0, b'0', '关于我们', '全心全意为您服务', NULL, 17, NULL, 0, 10, '长时间没有对话，系统自动关闭会话', '客服关闭会话', 169128, 10, '人工繁忙，请稍后再试', 60, '很抱歉，目前人工服务全忙，小M尽快为您转接。如需自助服务请回复序号，退出排队后继续向小M提问：[1]继续等待 [2]退出排队');

-- --------------------------------------------------------

--
-- 表的结构 `workgroup_app`
--

CREATE TABLE `workgroup_app` (
  `workgroup_id` bigint(20) NOT NULL,
  `app_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `workgroup_app`
--

INSERT INTO `workgroup_app` (`workgroup_id`, `app_id`) VALUES
(161483, 16),
(187513, 16),
(799261, 16);

-- --------------------------------------------------------

--
-- 表的结构 `workgroup_users`
--

CREATE TABLE `workgroup_users` (
  `users_id` bigint(20) NOT NULL,
  `workgroup_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `workgroup_users`
--

INSERT INTO `workgroup_users` (`users_id`, `workgroup_id`) VALUES
(15, 17),
(15, 161483),
(15, 187513),
(15, 402983),
(161435, 17),
(161435, 161483),
(161435, 187513),
(169128, 17),
(169128, 161483),
(169128, 187513),
(169128, 799261),
(169157, 17),
(169157, 187513),
(194670, 187513),
(402937, 402960),
(402938, 402960),
(402939, 402953),
(402939, 799261),
(402940, 402953),
(402941, 402935),
(402942, 402935);

-- --------------------------------------------------------

--
-- 表的结构 `workgroup_worktime`
--

CREATE TABLE `workgroup_worktime` (
  `workgroup_id` bigint(20) NOT NULL,
  `worktime_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `workgroup_worktime`
--

INSERT INTO `workgroup_worktime` (`workgroup_id`, `worktime_id`) VALUES
(17, 849861),
(161483, 851794),
(187513, 826661),
(194667, 194668),
(402935, 851778),
(402953, 851777),
(402960, 851776),
(799261, 826653),
(826100, 826101),
(826118, 826119),
(826124, 826125),
(834088, 834089),
(839322, 839323),
(839336, 839337),
(854809, 854810);

-- --------------------------------------------------------

--
-- 表的结构 `work_time`
--

CREATE TABLE `work_time` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `start_time` time DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `work_time`
--

INSERT INTO `work_time` (`id`, `created_at`, `updated_at`, `end_time`, `start_time`) VALUES
(1, '2018-07-27 00:00:00', '2018-07-27 00:00:00', '23:59:59', '00:00:00'),
(161485, '2018-08-10 18:19:30', '2018-08-10 18:19:30', '23:59:59', '00:00:00'),
(161484, '2018-08-10 18:19:30', '2018-08-10 18:19:30', '23:59:59', '00:00:00'),
(187514, '2018-09-06 17:16:23', '2018-09-06 17:16:23', '23:59:59', '00:00:00'),
(187550, '2018-09-06 17:23:27', '2018-09-06 17:23:27', '23:59:59', '00:00:00'),
(187551, '2018-09-06 17:23:27', '2018-09-06 17:23:27', '23:59:59', '00:00:00'),
(187630, '2018-09-06 19:38:51', '2018-09-06 19:38:51', '23:59:59', '00:00:00'),
(187631, '2018-09-06 19:39:07', '2018-09-06 19:39:07', '23:59:59', '00:00:00'),
(187632, '2018-09-06 19:40:23', '2018-09-06 19:40:23', '23:59:59', '00:00:00'),
(187633, '2018-09-06 19:40:23', '2018-09-06 19:40:23', '23:59:59', '00:00:00'),
(187634, '2018-09-06 19:41:34', '2018-09-06 19:41:34', '23:59:59', '00:00:00'),
(187635, '2018-09-06 19:41:34', '2018-09-06 19:41:34', '23:59:59', '00:00:00'),
(187636, '2018-09-06 19:41:45', '2018-09-06 19:41:45', '23:59:59', '00:00:00'),
(187637, '2018-09-06 19:41:45', '2018-09-06 19:41:45', '23:59:59', '00:00:00'),
(187638, '2018-09-06 19:42:19', '2018-09-06 19:42:19', '23:59:59', '00:00:00'),
(187639, '2018-09-06 19:42:19', '2018-09-06 19:42:19', '23:59:59', '00:00:00'),
(187640, '2018-09-06 19:42:19', '2018-09-06 19:42:19', '23:59:59', '00:00:00'),
(187641, '2018-09-06 19:42:33', '2018-09-06 19:42:33', '23:59:59', '00:00:00'),
(187642, '2018-09-06 19:42:33', '2018-09-06 19:42:33', '23:59:59', '00:00:00'),
(194356, '2018-09-19 17:00:11', '2018-09-19 17:00:11', '23:59:59', '00:00:00'),
(194367, '2018-09-19 17:38:05', '2018-09-19 17:38:05', '23:59:59', '00:00:00'),
(194373, '2018-09-19 17:43:59', '2018-09-19 17:43:59', '23:59:59', '00:00:00'),
(194380, '2018-09-19 17:53:01', '2018-09-19 17:53:01', '23:59:59', '00:00:00'),
(194392, '2018-09-19 18:07:01', '2018-09-19 18:07:01', '23:59:59', '00:00:00'),
(194398, '2018-09-19 18:15:26', '2018-09-19 18:15:26', '23:59:59', '00:00:00'),
(194633, '2018-09-19 23:17:32', '2018-09-19 23:17:32', '23:59:59', '00:00:00'),
(194645, '2018-09-19 23:23:02', '2018-09-19 23:23:02', '23:59:59', '00:00:00'),
(194668, '2018-09-19 23:35:03', '2018-09-19 23:35:03', '23:59:59', '00:00:00'),
(402961, '2018-10-20 17:58:16', '2018-10-20 17:58:16', '23:59:59', '00:00:00'),
(402954, '2018-10-20 17:58:15', '2018-10-20 17:58:15', '23:59:59', '00:00:00'),
(402936, '2018-10-20 17:58:13', '2018-10-20 17:58:13', '23:59:59', '00:00:00'),
(799262, '2018-12-20 00:05:36', '2018-12-20 00:05:36', '11:30:00', '08:30:00'),
(824705, '2018-12-26 16:38:01', '2018-12-26 16:38:01', '11:30:00', '08:30:00'),
(824706, '2018-12-26 16:38:11', '2018-12-26 16:38:11', '23:59:59', '00:00:00'),
(824707, '2018-12-26 16:38:18', '2018-12-26 16:38:18', '23:59:59', '00:00:00'),
(824708, '2018-12-26 16:38:32', '2018-12-26 16:38:32', '23:59:59', '00:00:00'),
(824709, '2018-12-26 16:38:38', '2018-12-26 16:38:38', '23:59:59', '00:00:00'),
(824710, '2018-12-26 16:38:41', '2018-12-26 16:38:41', '23:59:59', '00:00:00'),
(824711, '2018-12-26 16:38:45', '2018-12-26 16:38:45', '23:59:59', '00:00:00'),
(824763, '2018-12-26 17:28:00', '2018-12-26 17:28:00', '11:30:00', '08:30:00'),
(824775, '2018-12-26 17:31:27', '2018-12-26 17:31:27', '23:59:59', '00:00:00'),
(826101, '2018-12-30 19:54:17', '2018-12-30 19:54:17', '23:59:59', '00:54:17'),
(826119, '2018-12-30 20:00:01', '2018-12-30 20:00:01', '23:59:59', '00:00:01'),
(826125, '2018-12-30 20:04:00', '2018-12-30 20:04:00', '23:59:59', '00:03:59'),
(826629, '2019-01-01 10:56:28', '2019-01-01 10:56:28', '23:59:59', '00:00:00'),
(826646, '2019-01-01 11:08:56', '2019-01-01 11:08:56', '11:30:00', '08:30:00'),
(826647, '2019-01-01 11:14:27', '2019-01-01 11:14:27', '11:30:00', '08:30:00'),
(826648, '2019-01-01 11:23:06', '2019-01-01 11:23:06', '11:30:00', '08:30:00'),
(826649, '2019-01-01 11:23:15', '2019-01-01 11:23:15', '11:30:00', '08:30:00'),
(826650, '2019-01-01 11:23:28', '2019-01-01 11:23:28', '23:59:59', '00:00:00'),
(826651, '2019-01-01 11:24:23', '2019-01-01 11:24:23', '11:30:00', '08:30:00'),
(826653, '2019-01-01 11:26:46', '2019-01-01 11:26:46', '11:30:00', '08:30:00'),
(826654, '2019-01-01 11:27:25', '2019-01-01 11:27:25', '23:59:59', '00:00:00'),
(826655, '2019-01-01 11:27:35', '2019-01-01 11:27:35', '23:59:59', '00:00:00'),
(826656, '2019-01-01 11:27:40', '2019-01-01 11:27:40', '23:59:59', '00:00:00'),
(826658, '2019-01-01 11:28:10', '2019-01-01 11:28:10', '11:30:00', '08:30:00'),
(826660, '2019-01-01 11:30:51', '2019-01-01 11:30:51', '23:59:59', '00:00:00'),
(826661, '2019-01-01 11:30:56', '2019-01-01 11:30:56', '23:59:59', '00:00:00'),
(826662, '2019-01-01 11:31:00', '2019-01-01 11:31:00', '23:59:59', '00:00:00'),
(826663, '2019-01-01 11:31:05', '2019-01-01 11:31:05', '23:59:59', '00:00:00'),
(826664, '2019-01-01 11:31:09', '2019-01-01 11:31:09', '23:59:59', '00:00:00'),
(826666, '2019-01-01 11:31:54', '2019-01-01 11:31:54', '11:30:00', '08:30:00'),
(826749, '2019-01-01 14:39:52', '2019-01-01 14:39:52', '11:30:00', '08:30:00'),
(826761, '2019-01-01 14:55:21', '2019-01-01 14:55:21', '11:30:00', '08:30:00'),
(826760, '2019-01-01 14:55:12', '2019-01-01 14:55:12', '11:30:00', '08:30:00'),
(826793, '2019-01-01 15:42:45', '2019-01-01 15:42:45', '11:30:00', '08:30:00'),
(827245, '2019-01-03 15:44:50', '2019-01-03 15:44:50', '23:59:59', '00:00:00'),
(831685, '2019-01-03 21:45:21', '2019-01-03 21:45:21', '23:59:59', '00:00:00'),
(834089, '2019-01-09 22:41:40', '2019-01-09 22:41:40', '23:59:59', '00:41:40'),
(839323, '2019-01-24 03:12:27', '2019-01-24 03:12:27', '09:59:59', '10:12:26'),
(839337, '2019-01-24 03:23:50', '2019-01-24 03:23:50', '09:59:59', '10:23:50'),
(849861, '2019-02-18 22:17:12', '2019-02-18 22:17:12', '06:59:59', '19:00:00'),
(850560, '2019-02-20 00:13:58', '2019-02-20 00:13:58', '04:59:59', '23:59:59'),
(851463, '2019-02-22 08:49:49', '2019-02-22 08:49:49', '09:59:59', '17:59:59'),
(851776, '2019-02-24 01:45:57', '2019-02-24 01:45:57', '06:59:59', '18:00:00'),
(851777, '2019-02-24 01:46:14', '2019-02-24 01:46:14', '06:59:59', '18:00:00'),
(851778, '2019-02-24 01:46:19', '2019-02-24 01:46:19', '09:59:59', '17:59:59'),
(851793, '2019-02-24 03:00:34', '2019-02-24 03:00:34', '23:59:59', '00:00:00'),
(851794, '2019-02-24 03:00:46', '2019-02-24 03:00:46', '23:59:59', '00:00:00'),
(854810, '2019-03-04 18:20:19', '2019-03-04 18:20:19', '09:59:59', '10:20:19');

--
-- 转储表的索引
--

--
-- 表的索引 `answer`
--
ALTER TABLE `answer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKnmuoaicd084dw0peu780q2p9n` (`users_id`),
  ADD KEY `FKe6w78io8diu625m36dmdp81vh` (`workgroup_id`);

--
-- 表的索引 `answer_category`
--
ALTER TABLE `answer_category`
  ADD PRIMARY KEY (`answer_id`,`category_id`),
  ADD KEY `FKhs18hmc6o51jwwe9nj4fusxdn` (`category_id`);

--
-- 表的索引 `answer_query`
--
ALTER TABLE `answer_query`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK79ifnhjv36uc8dgtrm74t5hrg` (`answer_id`),
  ADD KEY `FKip913h5jl18ufns3bq7ufed8j` (`users_id`);

--
-- 表的索引 `answer_rate`
--
ALTER TABLE `answer_rate`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5t88masgcn3u99gd39rchl69j` (`answer_id`),
  ADD KEY `FK4e0slrnfa56eyd7eaaivcnjdc` (`message_id`),
  ADD KEY `FKmv0khpm6thy5hleulbxfcco8x` (`users_id`);

--
-- 表的索引 `answer_related`
--
ALTER TABLE `answer_related`
  ADD PRIMARY KEY (`answer_id`,`related_id`),
  ADD KEY `FKqc9b5ycrl8e7w3edab1a9md8o` (`related_id`);

--
-- 表的索引 `app`
--
ALTER TABLE `app`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpmajekr3d4un6x04ggbllfgol` (`users_id`);

--
-- 表的索引 `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6qwjxued33ulih2djfjep0hva` (`users_id`);

--
-- 表的索引 `article_category`
--
ALTER TABLE `article_category`
  ADD PRIMARY KEY (`article_id`,`category_id`),
  ADD KEY `FK855bhtvb75kxl2e0nmf2sd7la` (`category_id`);

--
-- 表的索引 `article_keyword`
--
ALTER TABLE `article_keyword`
  ADD PRIMARY KEY (`article_id`,`tag_id`),
  ADD KEY `FK67kpfpu491k0wxihn298i5g6o` (`tag_id`);

--
-- 表的索引 `article_rate`
--
ALTER TABLE `article_rate`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtnptrewjgcr6j7ichxuvci9c0` (`users_id`),
  ADD KEY `FKel9hff2fly9w96pevb258trh5` (`article_id`);

--
-- 表的索引 `article_read`
--
ALTER TABLE `article_read`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjyio3xyuvnperjigxyjynexgm` (`users_id`),
  ADD KEY `FKftik528br89od1cpbhxrai38p` (`article_id`);

--
-- 表的索引 `authority`
--
ALTER TABLE `authority`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `block`
--
ALTER TABLE `block`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_7mf3un935du6542wch08p42pr` (`bid`),
  ADD KEY `FKkwtg59orv2tbwct5cl83ia37c` (`blocked_user_id`),
  ADD KEY `FKoyl2k03ss2jw0kk6wnsunuhth` (`users_id`);

--
-- 表的索引 `browse`
--
ALTER TABLE `browse`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKegpmixvpoggfgqrh88fcxwbta` (`visitor_id`),
  ADD KEY `FK9mtm1ehh25qwdo4a5m5vnthvw` (`workgroup_id`),
  ADD KEY `FKe3xuj3m46pmlfeu9ywqx43o9s` (`referrer_id`),
  ADD KEY `FKr2w4n8sop4jkdhlig8kk46bja` (`url_id`);

--
-- 表的索引 `browse_invite`
--
ALTER TABLE `browse_invite`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_nudgiehal443c42gwet8r69ag` (`b_iid`),
  ADD KEY `FKpnnwnhbw9umsoo3lkoepvcn6m` (`workgroup_id`),
  ADD KEY `FK1vfmx54gmi5na68aj5v5em22f` (`browse_id`),
  ADD KEY `FK8w7ho6knxcg70h301bttldll8` (`from_user_id`),
  ADD KEY `FK23eio2ha3velpj0r2503cx64g` (`to_user_id`);

--
-- 表的索引 `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpidjslvlcnjcj75t9lg7rm3nf` (`users_id`),
  ADD KEY `FKap0cnk1255oj4bwam7in1hxxv` (`category_id`);

--
-- 表的索引 `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_5990k858oecspewp6xcbvv6xa` (`cid`),
  ADD KEY `FKt55y2infwbbw3o942o2mhm0v4` (`users_id`),
  ADD KEY `FKnwlc4xv9fm2swgesuas3uf3mo` (`article_id`),
  ADD KEY `FKbqnvawwwv4gtlctsi3o7vs131` (`post_id`);

--
-- 表的索引 `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_p2lqb3288a46snm17w40lale` (`cid`),
  ADD KEY `FKrkiogbcxxodv9h7r4yh50p674` (`users_id`);

--
-- 表的索引 `company_region`
--
ALTER TABLE `company_region`
  ADD PRIMARY KEY (`company_id`,`region_id`),
  ADD KEY `FK49ccp5h5mv4dp9svoeihwncng` (`region_id`);

--
-- 表的索引 `country`
--
ALTER TABLE `country`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_fijsnq7n1da00wfh3p85vf58s` (`cid`);

--
-- 表的索引 `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_1leedcwx9wcy3lk8rwo7n0pyh` (`cid`);

--
-- 表的索引 `cuw`
--
ALTER TABLE `cuw`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtdqh8y9xbve7aggpt6phsdlhu` (`users_id`),
  ADD KEY `FKd4e9btd6y5tc5o0fu00k1wji1` (`category_id`);

--
-- 表的索引 `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_36n1ji2jshj6nnyllhnda2dok` (`did`),
  ADD KEY `FK30uqp70migimv1t29d0io3x5c` (`users_id`),
  ADD KEY `FKh1m88q0f7sc0mk76kju4kcn6f` (`company_id`),
  ADD KEY `FKfocjcc3h9f6kip2xk3wid89ce` (`department_id`);

--
-- 表的索引 `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_i6lte3tlwxcvlsranr1qop6mo` (`fid`),
  ADD KEY `FK5kvmyu6eito8t2p9mk15xhmak` (`users_id`),
  ADD KEY `FKcpx660msvmy16ht6dnvrtudbh` (`visitor_id`);

--
-- 表的索引 `fingerprint`
--
ALTER TABLE `fingerprint`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1kmxgugco9h0ystff7mdk2big` (`users_id`);

--
-- 表的索引 `func_info`
--
ALTER TABLE `func_info`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_j0lyw3unt51fpx04lhsfq4jmx` (`gid`),
  ADD KEY `FKm2v0pc8an83n8g3d2513ejyn0` (`users_id`);

--
-- 表的索引 `groups_admin`
--
ALTER TABLE `groups_admin`
  ADD PRIMARY KEY (`groups_id`,`users_id`),
  ADD KEY `FK4fn94ooa51o9fwd3wrucw6wn` (`users_id`);

--
-- 表的索引 `groups_detail`
--
ALTER TABLE `groups_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqm12i7h3f81xaqo4rx9em8v7b` (`groups_id`),
  ADD KEY `FKt8qs4g9inlvpedt7e39r2tfhd` (`users_id`);

--
-- 表的索引 `groups_member`
--
ALTER TABLE `groups_member`
  ADD PRIMARY KEY (`groups_id`,`users_id`),
  ADD KEY `FKfqck0mq6ohpcw4pqiquptpx0u` (`users_id`);

--
-- 表的索引 `groups_muted`
--
ALTER TABLE `groups_muted`
  ADD PRIMARY KEY (`groups_id`,`users_id`);

--
-- 表的索引 `invite`
--
ALTER TABLE `invite`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_9wmo4jd73bui0bbhhr3cmxwlq` (`t_iid`),
  ADD KEY `FKp3wxvvtr3ofdxe1jm8x5ro83e` (`from_user_id`),
  ADD KEY `FKk1p9cx2f2ky7yph1hxqxp9y4i` (`group_id`),
  ADD KEY `FKp4mft3ydkinipit1bbn7vib0c` (`thread_id`),
  ADD KEY `FKiemup544a6brhk5w2tfi1cilh` (`to_user_id`);

--
-- 表的索引 `ip`
--
ALTER TABLE `ip`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `leave_message`
--
ALTER TABLE `leave_message`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_20xb1a6o9ewy2lmbdrxddivf4` (`lid`),
  ADD KEY `FKsibna01kcx1v3si8v5gfa792f` (`agent_id`),
  ADD KEY `FKni2mm092la467sdrlvqqohvi` (`users_id`),
  ADD KEY `FKnogt4o20nbb60n1bpnb3mgpdl` (`visitor_id`),
  ADD KEY `FKe2lukj5wh2u2llfkpfgfhnvil` (`work_group_id`);

--
-- 表的索引 `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_n9e3t0yc6t4i4bher7htdxjwi` (`mid`),
  ADD KEY `FKcwsjctg2caabo17bol1qv7iff` (`company_id`),
  ADD KEY `FKat33lwutdphmv4jpmg2b8a10r` (`questionnaire_id`),
  ADD KEY `FKg84ick4co3doh4bj85etwchy7` (`queue_id`),
  ADD KEY `FK8gpbwekbva6ty81i7o4ccf1en` (`users_id`),
  ADD KEY `FKng9f5tmplkvofk6x34knntjqs` (`thread_id`);

--
-- 表的索引 `message_answer`
--
ALTER TABLE `message_answer`
  ADD PRIMARY KEY (`message_id`,`answer_id`),
  ADD KEY `FKb3b5hqjisti91mv1lq8qqtco` (`answer_id`);

--
-- 表的索引 `message_deleted`
--
ALTER TABLE `message_deleted`
  ADD PRIMARY KEY (`message_id`,`users_id`);

--
-- 表的索引 `message_status`
--
ALTER TABLE `message_status`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqboxuo620r6qmh6ds85x5bt3l` (`message_id`),
  ADD KEY `FKi3ngkv4cwkyytelu5ydwqydg` (`users_id`);

--
-- 表的索引 `message_workgroup`
--
ALTER TABLE `message_workgroup`
  ADD PRIMARY KEY (`message_id`,`workgroup_id`),
  ADD KEY `FKlxkwawefnpvrs2e94cgp1g83w` (`workgroup_id`);

--
-- 表的索引 `mini_program_info`
--
ALTER TABLE `mini_program_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_sf1rw338c9v12jl31dggx2mol` (`wid`),
  ADD KEY `FK4pqoqeu5qal1tw054voip9rem` (`wechat_id`);

--
-- 表的索引 `monitor`
--
ALTER TABLE `monitor`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `notice`
--
ALTER TABLE `notice`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK30ik8roar52falaln0333i1wr` (`users_id`),
  ADD KEY `FKbp7suppae78bj405wshkmw6e2` (`group_id`);

--
-- 表的索引 `notice_reader`
--
ALTER TABLE `notice_reader`
  ADD PRIMARY KEY (`notice_id`,`users_id`),
  ADD KEY `FKj6ft7fvx7as82jm17vu5u7v1d` (`users_id`);

--
-- 表的索引 `notice_users`
--
ALTER TABLE `notice_users`
  ADD PRIMARY KEY (`notice_id`,`users_id`),
  ADD KEY `FK2fk34oph284hwrg6h4l131d9u` (`users_id`);

--
-- 表的索引 `oauth_client_details`
--
ALTER TABLE `oauth_client_details`
  ADD PRIMARY KEY (`client_id`);

--
-- 表的索引 `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpxjvbghn15fxqirhheghgoxd7` (`users_id`);

--
-- 表的索引 `pay_feature`
--
ALTER TABLE `pay_feature`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKg3jakdl9x1l0135noavbokl69` (`users_id`);

--
-- 表的索引 `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK654j7e05utx6icpfaoqxrdds2` (`users_id`);

--
-- 表的索引 `post_category`
--
ALTER TABLE `post_category`
  ADD PRIMARY KEY (`post_id`,`category_id`),
  ADD KEY `FKqly0d5oc4npxdig2fjfoshhxg` (`category_id`);

--
-- 表的索引 `post_keyword`
--
ALTER TABLE `post_keyword`
  ADD PRIMARY KEY (`post_id`,`tag_id`),
  ADD KEY `FK6cj15behpd08v49ec97kvbq2b` (`tag_id`);

--
-- 表的索引 `questionnaire`
--
ALTER TABLE `questionnaire`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_16kct2qahck34j9jild7xwl1t` (`qid`),
  ADD KEY `FKqi7qymksrf2il89xda147fv4s` (`users_id`);

--
-- 表的索引 `questionnaire_answer`
--
ALTER TABLE `questionnaire_answer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_b9sw7ht6kr27q03i079ierqvm` (`qid`),
  ADD KEY `FK4y5t6vy7mmh5cs7ax28m61agw` (`users_id`),
  ADD KEY `FK12792158wcx9ufw8w8831bdbv` (`questionnaire_item_id`),
  ADD KEY `FK4b8n7q6uyyi4pbqjfae4ta6wc` (`questionnaire_item_item_id`);

--
-- 表的索引 `questionnaire_answer_item`
--
ALTER TABLE `questionnaire_answer_item`
  ADD PRIMARY KEY (`questionnaire_answer_id`,`questionnaire_item_item_id`),
  ADD KEY `FKh4t3bpsutfnn1lgcwgqgkpqt6` (`questionnaire_item_item_id`);

--
-- 表的索引 `questionnaire_item`
--
ALTER TABLE `questionnaire_item`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_r9hdtxfldarvklqfy65mf3ll3` (`qid`),
  ADD KEY `FKnco08xc354qqmccyd5cuf2iko` (`users_id`),
  ADD KEY `FK2g19vhb8269p41wjuad3qk3rc` (`questionnaire_id`);

--
-- 表的索引 `questionnaire_item_item`
--
ALTER TABLE `questionnaire_item_item`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_e6c0hyjyn7axb6r53mpe3gk8n` (`qid`),
  ADD KEY `FKqd6t0dsl0ye59yduqwv3o63am` (`users_id`),
  ADD KEY `FKt9ai4xb30f93ot3htlq5wt7iw` (`questionnaire_item_id`);

--
-- 表的索引 `queue`
--
ALTER TABLE `queue`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_9t8jd4xkb5llex88uxgegmnrp` (`qid`),
  ADD KEY `FKlttgrci2dkmxifi41tipbkora` (`agent_id`),
  ADD KEY `FKfvuopclw92mffcpgschbl2ewv` (`thread_id`),
  ADD KEY `FK6qkcf52dcysbhksuojy9f8uim` (`visitor_id`),
  ADD KEY `FKqdd1mj21n1p2f0up8u1a6tlh7` (`workgroup_id`);

--
-- 表的索引 `rate`
--
ALTER TABLE `rate`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `region`
--
ALTER TABLE `region`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5cgfpq4u2digwkllynq14k7te` (`parent_id`);

--
-- 表的索引 `robot`
--
ALTER TABLE `robot`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_lgb5ms6lfy8ilujwycr6tirwq` (`rid`),
  ADD KEY `FKpijgh2dqn8omghdu1w1xan1bm` (`users_id`);

--
-- 表的索引 `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `role_authority`
--
ALTER TABLE `role_authority`
  ADD PRIMARY KEY (`role_id`,`authority_id`),
  ADD KEY `FKqbri833f7xop13bvdje3xxtnw` (`authority_id`);

--
-- 表的索引 `setting`
--
ALTER TABLE `setting`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj0uyxhec59fajg73gx64tchjx` (`users_id`);

--
-- 表的索引 `statistic`
--
ALTER TABLE `statistic`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgjvqgyarnkgeyqdi91a0b9rs3` (`users_id`);

--
-- 表的索引 `statistic_detail`
--
ALTER TABLE `statistic_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK2a9xmi0qrhhe1n3r1inmpotkm` (`users_id`);

--
-- 表的索引 `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKihet23wm5mpltxd785ao02ea6` (`users_id`);

--
-- 表的索引 `subscribe`
--
ALTER TABLE `subscribe`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3p60bl7sc74gk0a3vr2s1ylc` (`users_id`),
  ADD KEY `FKe1dorf2oobq3wv2o6ec53lhhe` (`thread_id`);

--
-- 表的索引 `synonyms`
--
ALTER TABLE `synonyms`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKi5dq76hv7y55mmklycswiroup` (`users_id`);

--
-- 表的索引 `synonyms_related`
--
ALTER TABLE `synonyms_related`
  ADD PRIMARY KEY (`standard_id`,`synonym_id`),
  ADD KEY `FKoe2l70185homf8kcoe0v2ym5d` (`synonym_id`);

--
-- 表的索引 `taboo`
--
ALTER TABLE `taboo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_78p6n2v7x520qr9n1ou8i2j3p` (`tid`),
  ADD KEY `FKogmvfrt04mitm694qkk929tnx` (`users_id`);

--
-- 表的索引 `taboo_related`
--
ALTER TABLE `taboo_related`
  ADD PRIMARY KEY (`standard_id`,`taboo_id`),
  ADD KEY `FKjpjaqm1pnoy0cvnkmu9jwkxdm` (`taboo_id`);

--
-- 表的索引 `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlhcrgyk7m8viqioyk5ak0ucs6` (`users_id`);

--
-- 表的索引 `template`
--
ALTER TABLE `template`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKh2ojvu7eyrutwafj2rysqqpa6` (`users_id`);

--
-- 表的索引 `thread`
--
ALTER TABLE `thread`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6ec6jeppmgt33eccso1tgofxf` (`tid`),
  ADD KEY `FKkooot5p7uhj9s7c2kx07dl8iw` (`agent_id`),
  ADD KEY `FKm1tngych6m09kaa0h3xt2nmri` (`contact_id`),
  ADD KEY `FKhmx3jcje168bhgn1vlkkgea3` (`group_id`),
  ADD KEY `FK74wwpd6ug7oe80j1wx9y1rbs4` (`queue_id`),
  ADD KEY `FKjb85m8ulwls88q6srtgp7irw5` (`workgroup_id`),
  ADD KEY `FKhp38ylxtev1m8xpxshlmnjr1w` (`visitor_id`);

--
-- 表的索引 `thread_agent`
--
ALTER TABLE `thread_agent`
  ADD PRIMARY KEY (`thread_id`,`users_id`),
  ADD KEY `FK19161d6sh2raqitrhblgcdx6m` (`users_id`);

--
-- 表的索引 `thread_deleted`
--
ALTER TABLE `thread_deleted`
  ADD PRIMARY KEY (`thread_id`,`users_id`);

--
-- 表的索引 `thread_disturb`
--
ALTER TABLE `thread_disturb`
  ADD PRIMARY KEY (`thread_id`,`users_id`);

--
-- 表的索引 `thread_top`
--
ALTER TABLE `thread_top`
  ADD PRIMARY KEY (`thread_id`,`users_id`);

--
-- 表的索引 `thread_unread`
--
ALTER TABLE `thread_unread`
  ADD PRIMARY KEY (`thread_id`,`users_id`);

--
-- 表的索引 `transfer`
--
ALTER TABLE `transfer`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_jgv1l0t1nyjsih65iaffxc3wn` (`t_tid`),
  ADD KEY `FKbchn0ky4cjs70br1w8x4dta08` (`to_user_id`),
  ADD KEY `FKkeq6gsav47bsyk9yc1xsjptaq` (`from_user_id`),
  ADD KEY `FKhvk5yjlp1sjuesq5gdjvsq37q` (`thread_id`);

--
-- 表的索引 `url`
--
ALTER TABLE `url`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_a7hlm8sj8kmijx6ucp7wfyt31` (`uuid`),
  ADD KEY `FK7hg5l7arqs535pitlsyqhccq0` (`users_id`);

--
-- 表的索引 `users_authority`
--
ALTER TABLE `users_authority`
  ADD PRIMARY KEY (`users_id`,`authority_id`),
  ADD KEY `FKjaiv95no9046qme8nr3us9srp` (`authority_id`);

--
-- 表的索引 `users_follow`
--
ALTER TABLE `users_follow`
  ADD PRIMARY KEY (`follow_id`,`users_id`),
  ADD KEY `FKfmp8cxycif97gf4fh8sdwwrwg` (`users_id`);

--
-- 表的索引 `users_robot`
--
ALTER TABLE `users_robot`
  ADD PRIMARY KEY (`users_id`,`robot_id`),
  ADD KEY `FK6f6tjessmrlu5ykspc5cgsx5r` (`robot_id`);

--
-- 表的索引 `users_role`
--
ALTER TABLE `users_role`
  ADD PRIMARY KEY (`users_id`,`role_id`),
  ADD KEY `FK3qjq7qsiigxa82jgk0i0wuq3g` (`role_id`);

--
-- 表的索引 `users_tag`
--
ALTER TABLE `users_tag`
  ADD PRIMARY KEY (`users_id`,`tag_id`),
  ADD KEY `FKteunswcqfgao3j07bss3b6hmf` (`tag_id`);

--
-- 表的索引 `validations`
--
ALTER TABLE `validations`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_kgh8tiessj70ivmavx21sslh3` (`uuid`);

--
-- 表的索引 `wechat`
--
ALTER TABLE `wechat`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_7op5ij2ch51ugt9w38g5697og` (`wid`),
  ADD KEY `FKm8v0myt7nsi8t6o6f3kfl0h6q` (`users_id`),
  ADD KEY `FKqv53nm3hey2n84rdr81vxfv3f` (`workgroup_id`),
  ADD KEY `FKn5l2ov807sej02qi4xrtbd22v` (`mini_program_info_id`);

--
-- 表的索引 `wechat_funcinfo`
--
ALTER TABLE `wechat_funcinfo`
  ADD PRIMARY KEY (`wechat_id`,`funcinfo_id`),
  ADD KEY `FKp9jmt3hex0i3ykf6hm9hfigt4` (`funcinfo_id`);

--
-- 表的索引 `wechat_menu`
--
ALTER TABLE `wechat_menu`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsm0t2nm8ny2xplv3ivwylc0io` (`users_id`);

--
-- 表的索引 `wechat_userinfo`
--
ALTER TABLE `wechat_userinfo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqtgdp6lx2xtpmaf1kj3r3iun5` (`users_id`),
  ADD KEY `FKcg7bmkmnpbdeu8d2jawfkwwt9` (`wechat_id`);

--
-- 表的索引 `wechat_usertag`
--
ALTER TABLE `wechat_usertag`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKa49vxeh6ygn3vowhm3sm8g92` (`wechat_id`);

--
-- 表的索引 `workgroup`
--
ALTER TABLE `workgroup`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_22bnbmdf1aopa84ruuofwkpt2` (`wid`),
  ADD KEY `FKt78yad8fgq9pydlateoj3ddu8` (`admin_id`),
  ADD KEY `FKdtgfhctg1p5tts8t9ptaa9k4b` (`users_id`),
  ADD KEY `FKmefksuh61714f5bvh8xksbll` (`company_id`),
  ADD KEY `FKhhdcswje5gbj53lrojshs4jfe` (`on_duty_workgroup_id`),
  ADD KEY `FKijli71u1xdj4jjatn07uli2o3` (`questionnaire_id`);

--
-- 表的索引 `workgroup_app`
--
ALTER TABLE `workgroup_app`
  ADD PRIMARY KEY (`workgroup_id`,`app_id`),
  ADD KEY `FK28p3187fuoiyg8uiehglrosdn` (`app_id`);

--
-- 表的索引 `workgroup_users`
--
ALTER TABLE `workgroup_users`
  ADD PRIMARY KEY (`users_id`,`workgroup_id`),
  ADD KEY `FKbqylj5djqf0oi2yli14bf6ucq` (`workgroup_id`);

--
-- 表的索引 `workgroup_worktime`
--
ALTER TABLE `workgroup_worktime`
  ADD PRIMARY KEY (`workgroup_id`,`worktime_id`),
  ADD KEY `FK2fc5krjuew7f4g9okerprdach` (`worktime_id`);

--
-- 表的索引 `work_time`
--
ALTER TABLE `work_time`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `app`
--
ALTER TABLE `app`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=854809;

--
-- 使用表AUTO_INCREMENT `authority`
--
ALTER TABLE `authority`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- 使用表AUTO_INCREMENT `category`
--
ALTER TABLE `category`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=843304;

--
-- 使用表AUTO_INCREMENT `message`
--
ALTER TABLE `message`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `pay_feature`
--
ALTER TABLE `pay_feature`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `queue`
--
ALTER TABLE `queue`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=833003;

--
-- 使用表AUTO_INCREMENT `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- 使用表AUTO_INCREMENT `thread`
--
ALTER TABLE `thread`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=854812;

--
-- 使用表AUTO_INCREMENT `workgroup`
--
ALTER TABLE `workgroup`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=854810;

--
-- 使用表AUTO_INCREMENT `work_time`
--
ALTER TABLE `work_time`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=854811;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
