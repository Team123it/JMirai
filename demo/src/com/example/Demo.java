/**
 * Copyright (C) 2020 [Your Name]
 */
package com.example;

import org.ots123it.JMirai.Mirai;
import org.ots123it.JMirai.MiraiAppAbstract;

import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;

public class Demo extends MiraiAppAbstract
{

	 static final long TEST_QQ = 12345678L;
	 
	 static final long TEST_GROUP = 123456789L;
	 
	 boolean isStarted = false;
	 
	 /**
	  * mirai-console启动 (Jmirai type=1001)<br>
	  * 本方法会在mirai-console【主线程】中被调用。<br>
	  * 请在这里执行插件初始化代码。<br>
	  * 请务必尽快返回本子程序，否则会卡住其他插件以及主程序的加载。
	  *
	  */
	 public void startup()
	 {
		  super.startup(); //加载必需的代码块(来自MiraiAppAbstract)
	 }
	 
	 /**
	  * 插件已被启用 (Jmirai type=1002)<br>
	  * 当应用被启用后，将收到此事件。<br>
	  * 如非必要，不建议在这里加载窗口。
	  */
	 public void onEnable()
	 {
	     super.onEnable(); //加载必需的代码块(来自MiraiAppAbstract)
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
		  super.onDisable(); //加载必需的代码块(来自MiraiAppAbstract)
	 }

	 /**
	  * 事件01:群消息事件<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai     {@link Mirai}对象
	  * @param rawEvent  消息原事件对象
	  * @param rawMsg    消息原对象
	  * @param sendTime  消息时间戳
	  * @param groupId   消息来源群号
	  * @param groupName 消息来源群名
	  * @param qqId      消息来源QQ号
	  * @param qqName    消息来源QQ昵称(若存在群名片则使用群名片昵称)
	  * @param msg       消息内容
	  */
	 @Override
	 public void groupMsg(Mirai Mirai, GroupMessageEvent rawEvent, Message rawMsg, long sendTime, long groupId,
				String groupName, long qqId, String qqName, String msg)
	 {
		  if (!isStarted) {
				Mirai.sendGroupMsg(TEST_GROUP, "测试插件启动完成,接收到了第一条群消息");
				isStarted = true;
		  }
		  rawEvent.getSubject().sendMessage("你发送了这样的消息:" + msg);
	 }

	 /**
	  * 事件02:私聊(好友)消息事件<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 消息原事件对象
	  * @param rawMsg   消息原对象
	  * @param sendTime 消息时间戳
	  * @param qqId     消息来源QQ号
	  * @param qqName   消息来源QQ昵称
	  * @param msg      消息内容
	  */
	 @Override
	 public void privateMsg(Mirai Mirai, FriendMessageEvent rawEvent, Message rawMsg, long sendTime, long qqId,
				String qqName, String msg)
	 {
		  rawEvent.getSubject().sendMessage("你发送了这样的消息:" + msg);
		  Mirai.sendPrivateMsg(TEST_QQ, "有人私聊机器人,请处理\n来源QQ:" + qqName + "(" + qqId + ")\n" + 
					 "消息内容:\n" + msg);
	 }
	 
}
