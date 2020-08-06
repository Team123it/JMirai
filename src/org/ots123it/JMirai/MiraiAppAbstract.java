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

import net.mamoe.mirai.console.plugins.PluginBase;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;

/**
 * mirai-console插件的主类模板类。<br>
 * 请将您的主类继承本类并添加所有未实现的方法(<a href="https://github.com/123-Open-Source-Organization/JMirai/blob/master/demo">参考Demo</a>)
 * @author 御坂12456
 *
 */
public abstract class MiraiAppAbstract extends PluginBase implements JMsg
{
	 public static PluginBase selfApp;

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
	 }

	 /**
	  * 插件已被启用 (Jmirai type=1002)<br>
	  * 当应用被启用后，将收到此事件。<br>
	  * 如非必要，不建议在这里加载窗口。
	  */
	 public void onEnable()
	 {

		  // 事件01:群消息事件
		  this.getEventListener().subscribeAlways(GroupMessageEvent.class, (GroupMessageEvent event) -> {
				GroupMessageEvent rawEvent = event; // 消息原事件对象
				Mirai rawMirai = new Mirai(rawEvent); // Mirai对象(类似JCQ的CQ对象)
				Message rawMsg = rawEvent.getMessage(); // 消息原对象
				long sendTime = (long) rawEvent.getTime(); // 消息时间戳
				long groupId = rawEvent.getGroup().getId(); // 消息来源群号
				String groupName = rawEvent.getGroup().getName(); // 消息来源QQ号
				long qqId = rawEvent.getSender().getId(); // 消息来源QQ号
				String qqName = rawEvent.getSenderName(); // 消息来源QQ昵称(若存在群名片则使用群名片昵称)
				String msg = rawMsg.toString().replaceAll("(\\[mirai){1}.*\\]{1}", ""); // 消息内容
				new Thread(new Runnable() // 异步方法(多线程)处理消息来弥补mirai单线程的弊端
				{
					 @Override
					 public void run()
					 {
						  groupMsg(rawMirai, rawEvent, rawMsg, sendTime, groupId, groupName, qqId, qqName, msg);
					 }
				}).run();
		  });
		  // 事件02:私聊(好友)消息事件
		  this.getEventListener().subscribeAlways(FriendMessageEvent.class, (FriendMessageEvent event) -> {
				FriendMessageEvent rawEvent = event; // 消息原事件对象
				Mirai rawMirai = new Mirai(rawEvent); // Mirai对象(类似JCQ的CQ对象)
				Message rawMsg = rawEvent.getMessage(); // 消息原对象
				long sendTime = (long) rawEvent.getTime(); // 消息时间戳
				long qqId = rawEvent.getSender().getId(); // 消息来源QQ号
				String qqName = rawEvent.getSenderName(); // 消息来源QQ昵称
				String msg = rawMsg.toString(); // 消息内容
				new Thread(new Runnable() // 异步方法(多线程)处理消息来弥补mirai单线程的弊端
				{
					 @Override
					 public void run()
					 {
						  privateMsg(rawMirai, rawEvent, rawMsg, sendTime, qqId, qqName, msg);
					 }
				}).run();
		  });
		  this.getLogger().info("Ping-Pong Enabled");
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
