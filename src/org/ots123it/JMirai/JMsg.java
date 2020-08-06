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

import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;

/**
 * JMsg接口。<br>
 * <br>
 * 该接口定义了Mirai支持的所有事件处理方法。<br>
 * 若继承{@link MiraiAppAbstract}则必须实现本接口。
 * @since 0.0.1
 * @version 0.0.1
 * @see MiraiAppAbstract
 * @author 御坂12456
 *
 */
public interface JMsg{

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
	 public void groupMsg(Mirai Mirai, GroupMessageEvent rawEvent, Message rawMsg, long sendTime, long groupId,
				String groupName, long qqId, String qqName, String msg);

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
	 public void privateMsg(Mirai Mirai, FriendMessageEvent rawEvent, Message rawMsg, long sendTime, long qqId,
				String qqName, String msg);
	 
}

