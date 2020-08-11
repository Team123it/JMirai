/*
 * Copyright (C) 2020 123 Open-Source Organization and jmirai contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 * 
 * https://github.com/123-Open-Source-Organization/Jmirai/blob/master/LICENSE
 *
 */
package org.ots123it.JMirai;

import net.mamoe.mirai.console.command.JCommandManager;
import net.mamoe.mirai.console.plugins.PluginBase;
import net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.BotJoinGroupEvent;
import net.mamoe.mirai.event.events.BotLeaveEvent;
import net.mamoe.mirai.event.events.BotMuteEvent;
import net.mamoe.mirai.event.events.BotUnmuteEvent;
import net.mamoe.mirai.event.events.GroupMuteAllEvent;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.MemberMuteEvent;
import net.mamoe.mirai.event.events.MemberPermissionChangeEvent;
import net.mamoe.mirai.event.events.MemberUnmuteEvent;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import org.ots123it.JMirai.JMsg;
import org.ots123it.JMirai.Mirai;
import org.ots123it.jhlper.CommonHelper;
import org.ots123it.jhlper.JsonHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * mirai-console插件的主类模板类。<br>
 * 请将您的主类继承本类并添加所有未实现的方法(<a href="https://github.com/123-Open-Source-Organization/JMirai/blob/master/demo">参考Demo</a>)
 * @since 0.0.1
 * @author 御坂12456
 *
 */
public abstract class MiraiAppAbstract extends PluginBase implements JMsg
{
	 public static PluginBase selfApp;
	 
	 public static long JMirai_Start = System.currentTimeMillis();

	 /**
	  * 是否启用Debug模式,若启用则:<br>
	  * <ul>
	  * <li>输出所有接收的消息内容(参考{@link JMsg.groupMsg(Mirai,GroupMessageEvent,Message,long,long,String,long,String,String)}方法的参数)</li>
	  * <li>输出使用{@link Mirai.logDebug(String,String)}方法输出的日志</li>
	  * <li>输出使用{@link Mirai.logError(String,Throwable)}方法输出的日志</li>
	  * </ul>
	  * @return 是否启动Debug模式的标志
	  */
	 public abstract boolean isDebug();
	 /**
	  * mirai-console启动 (Jmirai type=1001)<br>
	  * 本方法会在mirai-console【主线程】中被调用。<br>
	  * 请在这里执行插件初始化代码。<br>
	  * 请务必尽快返回本子程序，否则会卡住其他插件以及主程序的加载。
	  *
	  */
	 public void startup()
	 {
		  super.onLoad();
		  selfApp = this;
		  // [start] Check JMirai Update on every starting
		  try {
				 System.out.println(Mirai.getFormattedLog("INFO", "JMirai", "Fetching Newest JMirai Version..."));
				 String updateJsonStr = JsonHelper.loadJson("https://api.github.com/repos/123-Open-Source-Organization/JMirai/releases");
				 // 加载Github仓库的Releases信息
				 JSONArray releasesArray = JSONArray.parseArray(updateJsonStr); // 反序列化Release信息
				 JSONObject latestReleaseObject = releasesArray.getJSONObject(releasesArray.size() - 1); //获得最新的Release信息
				 String latestVersionStr = latestReleaseObject.getString("tag_name"); //获取最新版本的版本号字符串(格式:v{主要版本}.{次要版本}.{修订版本})
				 String plainLatestVersion = latestVersionStr.substring(1); //获取最新版本纯版本号(格式:{主要版本}.{次要版本}.{修订版本})
				 String plainCurrentVersion = JMirai.JMIRAI_VERSION.substring(1); //获取当前版本纯版本号
				 if (CommonHelper.compareVersion(plainLatestVersion, plainCurrentVersion) <= 0) { //如果当前版本大于等于最新版本
					  System.out.println(Mirai.getFormattedLog("INFO", "JMirai", "Stay on current version."));
				 } else {
					  String downloadURL = latestReleaseObject.getString("html_url");
					  System.out.println(Mirai.getFormattedLog("INFO", "JMirai", "Found new version of JMirai"));
					  System.out.println(Mirai.getFormattedLog("INFO", "JMirai", "Latest version: " + latestVersionStr));
					  System.out.println(Mirai.getFormattedLog("INFO", "JMirai", "Current Version: " + JMirai.JMIRAI_VERSION));
					  System.out.println(Mirai.getFormattedLog("INFO", "JMirai", "Please visit the following link to download the latest version:"));
					  System.out.println(Mirai.getFormattedLog("INFO", "JMirai", downloadURL));
				 }
			} catch (Exception e) {
				 System.err.println(Mirai.getFormattedLog("ERROR", "JMirai", "Check update failed: " + e.getMessage()));
			}
		  // [end]
	 }

	 /**
	  * 插件已被启用 (Jmirai type=1002)<br>
	  * 当应用被启用后，将收到此事件。<br>
	  * 如非必要，不建议在这里加载窗口。
	  */
	 public void onEnable()
	 {
		  // [start] 事件00:JMirai控制台指令事件
		  JCommandManager.getInstance().register(this, new JMiraiConProcessor());
		  // [end]
		  // [start] 事件系列0x:群常用事件
		  // [start] 事件01:群消息事件
		  this.getEventListener().subscribeAlways(GroupMessageEvent.class, (GroupMessageEvent event) -> {
				GroupMessageEvent rawEvent = event; // 消息原事件对象
				Mirai rawMirai = new Mirai(rawEvent); // Mirai对象(类似JCQ的CQ对象)
				rawMirai.isDebug = this.isDebug();
				Message rawMsg = rawEvent.getMessage(); // 消息原对象
				long sendTime = (long) rawEvent.getTime(); // 消息时间戳
				long groupId = rawEvent.getGroup().getId(); // 消息来源群号
				String groupName = rawEvent.getGroup().getName(); // 消息来源QQ号
				long qqId = rawEvent.getSender().getId(); // 消息来源QQ号
				String qqName = rawEvent.getSenderName(); // 消息来源QQ昵称(若存在群名片则使用群名片昵称)
				String msg = rawMsg.toString().split("]", 2)[1].replace("\n", "\\n"); // 消息内容
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: GroupMessageEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName()).append(", rawMsg=").append(rawMsg.toString())
								  .append(", sendTime=").append(sendTime).append(", groupId=").append(groupId).append(", groupName=").append(groupName)
								  .append(", qqId=").append(qqId).append(", qqName=").append(qqName).append(", msg=").append(msg).append(")").toString());
				}
				groupMsg(rawMirai, rawEvent, rawMsg, sendTime, groupId, groupName, qqId, qqName, msg);
		  });
		  // [end]
		  // [start] 事件02:私聊(好友)消息事件
		  this.getEventListener().subscribeAlways(FriendMessageEvent.class, (FriendMessageEvent event) -> {
				FriendMessageEvent rawEvent = event; // 消息原事件对象
				Mirai rawMirai = new Mirai(rawEvent); // Mirai对象(类似JCQ的CQ对象)
				Message rawMsg = rawEvent.getMessage(); // 消息原对象
				long sendTime = (long) rawEvent.getTime(); // 消息时间戳
				long qqId = rawEvent.getSender().getId(); // 消息来源QQ号
				String qqName = rawEvent.getSenderName(); // 消息来源QQ昵称
				String msg = rawMsg.toString().replace("\n", "\\n"); // 消息内容
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: FriendMessageEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName()).append(", rawMsg=").append(rawMsg.toString())
								  .append(", sendTime=").append(sendTime)
								  .append(", qqId=").append(qqId).append(", qqName=").append(qqName).append(", msg=").append(msg).append(")").toString());
				}
				privateMsg(rawMirai, rawEvent, rawMsg, sendTime, qqId, qqName, msg);
		  });
		  // [end]
		  // [start] 事件03:成员已入群(群成员增加)事件
		  this.getEventListener().subscribeAlways(MemberJoinEvent.class, (MemberJoinEvent event) -> {
				MemberJoinEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long groupId = rawEvent.getGroup().getId();
				String groupName = rawEvent.getGroup().getName();
				long qqId = rawEvent.getMember().getId();
				String qqName = rawEvent.getMember().getNick();
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: MemberJoinEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName())
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName)
								  .append(", fromQQ=").append(qqId).append(", qqName=").append(qqName).append(")").toString());
				}
				groupMemberIncrease(rawMirai, rawEvent, groupId, groupName, qqId, qqName);
		  });
		  // [end]
		  // [start] 事件04:成员已退群/被移出群(群成员减少)事件
		  this.getEventListener().subscribeAlways(MemberLeaveEvent.class, (MemberLeaveEvent event) -> 
		  {
				MemberLeaveEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long groupId = rawEvent.getGroup().getId();
				String groupName = rawEvent.getGroup().getName();
				long qqId = rawEvent.getMember().getId();
				String qqName = rawEvent.getMember().getNick();
				boolean isKick = ((rawEvent.getClass() == MemberLeaveEvent.Kick.class) ? true : false);
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: MemberLeaveEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName())
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName)
								  .append(", fromQQ=").append(qqId).append(", qqName=").append(qqName)
								  .append(", isKick=").append(isKick).append(")").toString());
				}
				groupMemberDecrease(rawMirai, rawEvent, groupId, groupName, qqId, qqName,isKick);
		  });
		  // [end]
		  // [start] 事件05:成员入群请求事件
		  this.getEventListener().subscribeAlways(MemberJoinRequestEvent.class, (MemberJoinRequestEvent event) -> {
				MemberJoinRequestEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long requestId = rawEvent.getEventId();
				long groupId = rawEvent.getGroupId();
				String groupName = rawEvent.getGroupName();
				long qqId = rawEvent.getFromId();
				String qqName = rawEvent.getFromNick();
				String requestMsg = rawEvent.getMessage().replace("\n", "\\n");
				if (rawMirai.isDebug) {
					 rawMirai.logDebug("JMirai", new StringBuilder("Event: MemberJoinRequestEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName())
								  .append(", requestId=").append(requestId)
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName)
								  .append(", fromQQ=").append(qqId).append(", qqName=").append(qqName)
								  .append(", requestMsg=").append(requestMsg).append(")").toString());
				}
				requestGroupAdd(rawMirai, rawEvent, requestId, groupId, groupName, qqId, qqName, requestMsg);
		  });
		  // [end]
		  // [start] 事件06:群成员被禁言事件
		  this.getEventListener().subscribeAlways(MemberMuteEvent.class, (MemberMuteEvent event) -> {
				MemberMuteEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long groupId = rawEvent.getGroup().getId();
				String groupName = rawEvent.getGroup().getName();
				long qqId = rawEvent.getOperator().getId();
				String qqName = rawEvent.getOperator().getNick();
				long targetQQId = rawEvent.getMember().getId();
				String targetQQName = rawEvent.getMember().getNick();
				int duration = rawEvent.getDurationSeconds();
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: MemberMuteEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName())
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName)
								  .append(", fromQQ=").append(qqId).append(", qqName=").append(qqName)
								  .append(", targetQQ=").append(targetQQId).append(", targetQQName=").append(targetQQName)
								  .append(", duration=").append(duration).append(")").toString());
				}
				groupMemberMute(rawMirai, rawEvent, groupId, groupName, qqId, qqName, targetQQId, targetQQName, duration);
		  });
		  // [end]
		  // [start] 事件07:群成员被解禁事件
		  this.getEventListener().subscribeAlways(MemberUnmuteEvent.class, (MemberUnmuteEvent event) -> {
				MemberUnmuteEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long groupId = rawEvent.getGroup().getId();
				String groupName = rawEvent.getGroup().getName();
				long qqId = rawEvent.getOperator().getId();
				String qqName = rawEvent.getOperator().getNick();
				long targetQQId = rawEvent.getMember().getId();
				String targetQQName = rawEvent.getMember().getNick();
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: MemberUnmuteEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName())
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName)
								  .append(", fromQQ=").append(qqId).append(", qqName=").append(qqName)
								  .append(", targetQQ=").append(targetQQId).append(", targetQQName=").append(targetQQName).append(")").toString());
				}
				groupMemberUnmute(rawMirai, rawEvent, groupId, groupName, qqId, qqName, targetQQId, targetQQName);
		  });
		  // [end]
		  // [start] 事件08:群全员禁言开关变动事件
		  this.getEventListener().subscribeAlways(GroupMuteAllEvent.class, (GroupMuteAllEvent event) -> {
				GroupMuteAllEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long groupId = rawEvent.getGroup().getId();
				String groupName = rawEvent.getGroup().getName();
				long qqId = rawEvent.getOperator().getId();
				String qqName = rawEvent.getOperator().getNick();
				boolean isMuteAll = rawMirai.getBot().getGroup(groupId).getSettings().isMuteAll();
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: GroupMuteAllEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName())
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName)
								  .append(", fromQQ=").append(qqId).append(", qqName=").append(qqName)
								  .append(", isMuteAll=").append((isMuteAll ? "true" : "false")).append(")").toString());
				}
				groupMuteAll(rawMirai, rawEvent, groupId, groupName, qqId, qqName, isMuteAll);
		  });
		  // [end]
		  // [start] 事件09:群成员权限变动事件
		  this.getEventListener().subscribeAlways(MemberPermissionChangeEvent.class, (MemberPermissionChangeEvent event) -> {
				MemberPermissionChangeEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long groupId = rawEvent.getGroup().getId();
				String groupName = rawEvent.getGroup().getName();
				long qqId = rawEvent.getMember().getId();
				String qqName = rawEvent.getMember().getNick();
				rawEvent.getMember().getPermission();
				int newPermission = 0;
				int oldPermission = 0;
				switch (rawEvent.getNew())
				{
				case OWNER:
					 newPermission = 2;
					 break;
				case ADMINISTRATOR:
					 newPermission = 1;
					 break;
				default:
					 break;
				}
				switch (rawEvent.getOrigin())
				{
				case OWNER:
					 oldPermission = 2;
					 break;
				case ADMINISTRATOR:
					 oldPermission = 1;
					 break;
				default:
					 break;
				}
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: MemberPermissionChangeEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName())
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName)
								  .append(", fromQQ=").append(qqId).append(", qqName=").append(qqName)
								  .append(", oldPermission=").append(oldPermission)
								  .append(", newPermission=").append(newPermission).append(")").toString());
				}
				groupMemberPermissionChange(rawMirai, rawEvent, groupId, groupName, qqId, qqName,oldPermission, newPermission);
		  });
		  // [end]
		  // [end]
		  // [start] 事件系列1x:群内Bot常用事件
		  // [start] 事件101:Bot已入群事件
		  this.getEventListener().subscribeAlways(BotJoinGroupEvent.class, (BotJoinGroupEvent event) -> {
				BotJoinGroupEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long groupId = rawEvent.getGroup().getId();
				String groupName = rawEvent.getGroup().getName();
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: BotJoinGroupEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName())
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName).append(")").toString());
				}
				botHasJoinGroup(rawMirai, rawEvent, groupId, groupName);
		  });
		  // [end]
		  // [start] 事件102:Bot被邀请入群请求事件
		  this.getEventListener().subscribeAlways(BotInvitedJoinGroupRequestEvent.class, (BotInvitedJoinGroupRequestEvent event) -> {
				BotInvitedJoinGroupRequestEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long requestId = rawEvent.getEventId();
				long groupId = rawEvent.getGroupId();
				String groupName = rawEvent.getGroupName();
				long invitorQQId = rawEvent.getInvitorId();
				String invitorQQName = rawEvent.getInvitorNick();
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: BotJoinGroupEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName()).append(", requestId=").append(requestId)
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName)
								  .append(", fromQQ=").append(invitorQQId).append(", qqId=").append(invitorQQName).append(")").toString());
				}
				requestBotInviteJoinGroup(rawMirai, rawEvent, requestId, groupId, groupName, invitorQQId, invitorQQName);
		  });
		  // [end]
		  // [start] 事件103:Bot已退群/被移出群事件
		  this.getEventListener().subscribeAlways(BotLeaveEvent.class, (BotLeaveEvent event) -> {
				BotLeaveEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long groupId = rawEvent.getGroup().getId();
				String groupName = rawEvent.getGroup().getName();
				boolean isKick = ((rawEvent.getClass() == BotLeaveEvent.Kick.class) ? true : false);
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: BotLeaveEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName())
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName)
								  .append(", isKick=").append(isKick).append(")").toString());
				}
				botHasExitGroup(rawMirai, rawEvent, groupId, groupName,isKick);
				
		  });
		  // [end]
		  // [start] 事件104:Bot权限变动事件
		  this.getEventListener().subscribeAlways(BotGroupPermissionChangeEvent.class, (BotGroupPermissionChangeEvent event) -> {
				BotGroupPermissionChangeEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long groupId = rawEvent.getGroup().getId();
				String groupName = rawEvent.getGroup().getName();
				int oldPermission = 0;
				int newPermission = 0;
				switch (rawEvent.getNew())
				{
				case OWNER:
					 newPermission = 2;
					 break;
				case ADMINISTRATOR:
					 newPermission = 1;
					 break;
				default:
					 break;
				}
				switch (rawEvent.getOrigin())
				{
				case OWNER:
					 oldPermission = 2;
					 break;
				case ADMINISTRATOR:
					 oldPermission = 1;
					 break;
				default:
					 break;
				}
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: BotGroupPermissionChangeEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName())
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName)
								  .append(", oldPermission=").append(oldPermission)
								  .append(", newPermission=").append(newPermission).append(")").toString());
				}
				botPermissionChange(rawMirai, rawEvent, groupId, groupName, oldPermission, newPermission);
		  });
		  // [end]
		  // [start] 事件105:Bot被禁言事件
		  this.getEventListener().subscribeAlways(BotMuteEvent.class, (BotMuteEvent event) -> {
				BotMuteEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long groupId = rawEvent.getGroup().getId();
				String groupName = rawEvent.getGroup().getName();
				long qqId = rawEvent.getOperator().getId();
				String qqName = rawEvent.getOperator().getNick();
				int duration = rawEvent.getDurationSeconds();
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: BotMuteEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName())
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName)
								  .append(", fromQQ=").append(qqId).append(", qqName=").append(qqName)
								  .append(", duration=").append(duration).append(")").toString());
				}
				botMute(rawMirai, rawEvent, groupId, groupName, qqId, qqName, duration);
		  });
		  // [end]
		  // [start] 事件106:Bot被解禁事件
		  this.getEventListener().subscribeAlways(BotUnmuteEvent.class, (BotUnmuteEvent event) -> {
				BotUnmuteEvent rawEvent = event;
				Mirai rawMirai = new Mirai(rawEvent);
				long groupId = rawEvent.getGroup().getId();
				String groupName = rawEvent.getGroup().getName();
				long qqId = rawEvent.getOperator().getId();
				String qqName = rawEvent.getOperator().getNick();
				if (rawMirai.isDebug) {
						rawMirai.logDebug("JMirai", new StringBuilder("Event: BotUnmuteEvent(Mirai=").append(rawMirai.toString()) 
								  .append(", rawEvent=").append(rawEvent.getClass().getName())
								  .append(", fromGroup=").append(groupId).append(", groupName=").append(groupName)
								  .append(", fromQQ=").append(qqId).append(", qqName=").append(qqName)
								  .append(")").toString());
				}
				botUnmute(rawMirai, rawEvent, groupId, groupName, qqId, qqName);
		  });
		  // [end]
		  // [end]
	 }

	 /**
	  * 插件已被停用 (Jmirai type=1003)<br>
	  * 当插件被停用前，将收到此事件。<br>
	  * 如果mirai-console载入前插件未启用，则本函数【不会】被调用。<br>
	  * 本插件若已被启用，则mirai-console关闭前本函数【会】被调用。
	  *
	  */
	 public void onDisable()
	 {
		  super.onDisable();
	 }
}
