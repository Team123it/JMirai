/*
 * Copyright (C) 2020 123 Open-Source Organization and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 * 
 * https://github.com/123-Open-Source-Organization/Jmirai/blob/master/LICENSE
 *
 */
package org.ots123it.JMirai;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.MessageReceipt;

/**
 * Jmirai的消息发送结果类。<br>
 * 该类通过封装{@link MessageReceipt}和消息是否发送成功的状态(status)来方便mirai-console插件开发者进行消息发送后的处理。<br>
 * 若发送成功(status值为0)则{@link #getMessageReceipt()}返回的{@link MessageReceipt}对象不为null,否则为null。<br>
 * status值具体说明:<br>
 * <table border="1">
 * <tr>
 * <td><b>status值</b></td><td><b>具体含义</b></td>
 * </tr>
 * <tr>
 * <td>0</td><td>发送成功</td>
 * </tr>
 * <tr>
 * <td>-1</td><td>未添加对应群/好友</td>
 * </tr>
 * <tr>
 * <td>-2</td><td>Bot在该群被禁言</td>
 * </tr>
 * <tr>
 * <td>-3</td><td>消息内容为空</td>
 * </tr>
 * <tr>
 * <td>-4</td><td>消息内容过长</td>
 * </tr>
 * <tr>
 * <td>-99</td><td>其它</td>
 * </tr>
 * </table>
 * @author 御坂12456
 *
 */
public class MsgResult
{
	 MessageReceipt<Contact> selfReceipt;
	 int selfStatus;
	 /**
	  * 以消息发送返回信息和消息发送状态初始化{@link MsgResult}类的新实例。
	  * @param msgReceipt 消息发送事件所返回的信息。
	  * @param status 消息是否发送成功的状态。
	  */
	 public MsgResult(MessageReceipt<Contact> msgReceipt,int status)
	 {
		  if (msgReceipt != null) { //如果msgReceipt不为空
				selfReceipt = msgReceipt; //设置selfReceipt
				selfStatus = status; //设置selfStatus
		  } else { //否则
				selfReceipt = msgReceipt; //设置selfReceipt
				if (status == 0) { //如果status等于0
					 selfStatus = -99; //设置selfStatus为-99(其它)
				} else { //否则
					 selfStatus = status; //设置selfStatus
				}
		  }
	 }
	 /**
	  * 以消息发送状态初始化{@link MsgResult}类的新实例。
	  * @param status 消息是否发送成功的状态。
	  * @throws NullPointerException 状态值等于0抛出此异常(若状态值等于0请使用{@link #MsgResult(MessageReceipt, boolean)}方法初始化实例)
	  */
	 public MsgResult(int status)
	 {
		  if (status < 0) {
				selfStatus = status;
				selfReceipt = null;
		  } else {
				throw new NullPointerException();
		  }
	 }
	 
	 
	 /**
	  * 以消息发送返回信息初始化{@link MsgResult}类的新实例。<br>
	  * 若返回信息为null则实例中的status值为-99(其它)。
	  * @param msgReceipt 消息发送事件所返回的信息。
	  * @param status 消息是否发送成功的状态。
	  */
	 public MsgResult(MessageReceipt<Contact> msgReceipt)
	 {
		  if (msgReceipt != null) {
				selfReceipt = msgReceipt;
				selfStatus = 0;
		  } else {
				selfReceipt = null;
				selfStatus = -99;
		  }
	 }
	 /**
	  * 获取消息发送后返回的信息对象。	  
	  * @return 消息发送后返回的信息对象
	  */
	 public MessageReceipt<Contact> getMessageReceipt()
	 {
		  return selfReceipt;
	 }
	 
	 /**
	  * 获取消息发送状态
	  * @return 消息是否发送成功的状态
	  */
	 public int getStatus()
	 {
		  return selfStatus;
	 }
}
