-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2019-03-09 10:58:57
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

-- --------------------------------------------------------

--
-- 表的结构 `article_category`
--

CREATE TABLE `article_category` (
  `article_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

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

-- --------------------------------------------------------

--
-- 表的结构 `city`
--

CREATE TABLE `city` (
  `id` bigint(20) NOT NULL,
  `cid` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `province_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- 表的结构 `company_region`
--

CREATE TABLE `company_region` (
  `company_id` bigint(20) NOT NULL,
  `region_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- 表的结构 `groups_admin`
--

CREATE TABLE `groups_admin` (
  `groups_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

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

-- --------------------------------------------------------

--
-- 表的结构 `role_authority`
--

CREATE TABLE `role_authority` (
  `role_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- 表的结构 `users_robot`
--

CREATE TABLE `users_robot` (
  `users_id` bigint(20) NOT NULL,
  `robot_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `users_role`
--

CREATE TABLE `users_role` (
  `users_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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

-- --------------------------------------------------------

--
-- 表的结构 `workgroup_app`
--

CREATE TABLE `workgroup_app` (
  `workgroup_id` bigint(20) NOT NULL,
  `app_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `workgroup_users`
--

CREATE TABLE `workgroup_users` (
  `users_id` bigint(20) NOT NULL,
  `workgroup_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `workgroup_worktime`
--

CREATE TABLE `workgroup_worktime` (
  `workgroup_id` bigint(20) NOT NULL,
  `worktime_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
-- 表的索引 `city`
--
ALTER TABLE `city`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKll21eddgtrjc9f40ueeouyr8f` (`province_id`);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `authority`
--
ALTER TABLE `authority`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `category`
--
ALTER TABLE `category`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `role`
--
ALTER TABLE `role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `thread`
--
ALTER TABLE `thread`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `workgroup`
--
ALTER TABLE `workgroup`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `work_time`
--
ALTER TABLE `work_time`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
