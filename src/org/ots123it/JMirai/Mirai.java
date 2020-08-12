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

import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;

import java.io.File;
import java.util.Calendar;

import javax.naming.OperationNotSupportedException;

import org.jetbrains.annotations.NotNull;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.BotIsBeingMutedException;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.MessageTooLargeException;
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

/**
 * Mirai对象实现类.<br>
 * <p>对使用过JCQ或其他SDK开发过酷Q应用的开发者:<br>
 * 本类使用方法与"CQ"对象(或"酷Q"对象)类似。</p>
 * <p>对使用匿名类开发而未使用过类似该类开发Mirai-console插件的开发者:<br>
 * 通过Mirai类可以很容易实现在程序的任何部分(只要传递了{@link Mirai}对象)都可以发送消息、获取群列表等。<br>
 * 以下是一个示例:<p>
 * <code>public void groupMsg(Mirai Mirai,GroupMessageEvent rawEvent, Message rawMsg, long sendTime, long groupId,
 *				String groupName, long qqId, String qqName, String msg)<br>
 *				{<br>
 *				 &nbsp;&nbsp;&nbsp;&nbsp;{@link Mirai}.{@link #sendGroupMsg}(607182105,"Hello world!"); //向群号为607812805的群聊发送信息,内容为"Hello world!"<br>
 *				 &nbsp;&nbsp;&nbsp;&nbsp;{@link Mirai}.{@link #sendPrivateMsg}(770296414,"近来可好?"); //向QQ号为770296414的好友发送消息,内容为"近来可好?"<br>
 *				}<br>
 *	</code>
 * 使用{@link Mirai}类和{@link MiraiAppAbstract}类将可以抛弃掉匿名类的开发方法。<br>
 * </p></p>
 * 如果需要开发插件主类,请使用{@link MiraiAppAbstract}(用法与JCQ中的JCQAppAbstract类似),注意查看相关注释。
 * @since 0.0.1
 * @see MiraiAppAbstract
 * @author 御坂12456
 *
 */
public class Mirai
{
	 Bot selfBot = null;
	 public boolean isDebug = false;
	 /**
	  * 使用群消息原事件对象初始化{@link Mirai}类的新实例。
	  * @param rawGroupEvent 群消息原事件对象
	  * @throws NullPointerException 原事件对象为null
	  */
	 public Mirai(GroupMessageEvent rawGroupEvent)
	 {
			if (rawGroupEvent != null) { //如果参数不为null
				 selfBot = rawGroupEvent.getBot(); //获得Bot对象
			} else { //如果参数为null
				 throw new NullPointerException("rawGroupEvent is null");
			}
	 }
	 
	 /**
	  * 使用私聊消息(好友消息)原事件对象初始化{@link Mirai}类的新实例。
	  * @param rawPrivateEvent 私聊消息(好友消息)原事件对象
	  * @throws NullPointerException 原事件对象为null
	  */
	 public Mirai(FriendMessageEvent rawPrivateEvent)
	 {
		  if (rawPrivateEvent != null) { //如果参数不为null
				selfBot = rawPrivateEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("rawPrivateEvent is null");
		  }
	 }

	 public Mirai(MemberJoinEvent rawJoinEvent)
	 {
		  if (rawJoinEvent  != null) { //如果参数不为null
				selfBot = rawJoinEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("rawJoinEvent is null");
		  }
	 }
	 
	 public Mirai(MemberLeaveEvent rawLeaveEvent)
	 {
		  if (rawLeaveEvent  != null) { //如果参数不为null
				selfBot = rawLeaveEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("rawLeaveEvent is null");
		  }
	 }
	 
	 public Mirai(MemberJoinRequestEvent rawJoinRequestEvent)
	 {
		  if (rawJoinRequestEvent  != null) { //如果参数不为null
				selfBot = rawJoinRequestEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("rawJoinRequestEvent is null");
		  }
	 }
	 
	 public Mirai(MemberMuteEvent rawMuteEvent)
	 {
		  if (rawMuteEvent  != null) { //如果参数不为null
				selfBot = rawMuteEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("rawMuteEvent is null");
		  }
	 }
	 
	 public Mirai(MemberUnmuteEvent rawUnmuteEvent)
	 {
		  if (rawUnmuteEvent  != null) { //如果参数不为null
				selfBot = rawUnmuteEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("rawUnmuteEvent is null");
		  }
	 }
	 
	 public Mirai(GroupMuteAllEvent rawMuteAllEvent)
	 {
		  if (rawMuteAllEvent  != null) { //如果参数不为null
				selfBot = rawMuteAllEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("rawMuteAllEvent is null");
		  }
	 }

	 public Mirai(MemberPermissionChangeEvent rawPermissionChangeEvent)
	 {
		  if (rawPermissionChangeEvent  != null) { //如果参数不为null
				selfBot = rawPermissionChangeEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("rawPermissionChangeEvent is null");
		  }
	 }

	 public Mirai(BotJoinGroupEvent bRawJoinGroupEvent)
	 {
		  if (bRawJoinGroupEvent  != null) { //如果参数不为null
				selfBot = bRawJoinGroupEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("bRawJoinGroupEvent is null");
		  }
	 }

	 public Mirai(BotInvitedJoinGroupRequestEvent bRawJoinGroupRequestEvent)
	 {
		  if (bRawJoinGroupRequestEvent  != null) { //如果参数不为null
				selfBot = bRawJoinGroupRequestEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("bRawJoinGroupRequestEvent is null");
		  }
	 }
	 
	 public Mirai(BotLeaveEvent bRawLeaveEvent)
	 {
		  if (bRawLeaveEvent  != null) { //如果参数不为null
				selfBot = bRawLeaveEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("bRawLeaveEvent is null");
		  }
	 }
	 
	 public Mirai(BotGroupPermissionChangeEvent bPermissionChangeEvent)
	 {
		  if (bPermissionChangeEvent  != null) { //如果参数不为null
				selfBot = bPermissionChangeEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("bPermissionChangeEvent is null");
		  }
	 }

	 public Mirai(BotMuteEvent bMuteEvent)
	 {
		  if (bMuteEvent  != null) { //如果参数不为null
				selfBot = bMuteEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("bMuteEvent is null");
		  }
	 }

	 public Mirai(BotUnmuteEvent bUnmuteEvent)
	 {
		  if (bUnmuteEvent  != null) { //如果参数不为null
				selfBot = bUnmuteEvent.getBot(); //获得Bot对象
		  } else { //如果参数为null
				throw new NullPointerException("bUnmuteEvent is null");
		  }
	 }
	 
	 /**
	  * 获得当前{@link Mirai}对象对应的{@link Bot}对象。
	  * @throws NullPointerException {@link Bot}对象为null
	  * @return {@link Bot}对象
	  */
	 public Bot getBot()
	 {
		  if (selfBot != null) { //如果selfBot不为null
				return selfBot; //返回selfBot
		  } else { //否则
				throw new NullPointerException();
		  }
	 }
	 
	 /**
	  * 发送私聊消息(好友消息)。
	  * @param qqId 对应好友的QQ号
	  * @param msg 消息内容
	  * @throws NullPointerException {@link Mirai}对象为null
	  * @return 消息发送后的返回结果
	  */
	 public MsgResult sendPrivateMsg(long qqId,String msg)
	 {
		  if (selfBot != null) { //如果selfBot不为null
				boolean hasThisFriend = false; //定义是否存在要把信息发送到的好友
				ContactList<Friend> friendsList = selfBot.getFriends(); //获取好友列表
				for (Friend friend : friendsList) { //遍历好友列表
					 if (friend.getId() == qqId) { //如果存在该好友
						  hasThisFriend = true;
						  break; //跳出循环
					 }
				}
				if (hasThisFriend) { //如果存在该好友
					 MessageReceipt<Contact> receipt = selfBot.getFriend(qqId).sendMessage(msg); //发送消息并获得返回信息
					 if (receipt != null) { //如果返回信息不为null
						  MsgResult result = new MsgResult(receipt); //发送成功
						  return result;
					 } else { //否则
						  MsgResult result = new MsgResult(receipt,-99); //其它
						  return result;
					 }
				} else { //否则
					 MsgResult result = new MsgResult(-1); //未添加该好友
					 return result;
				}
		  } else { //否则
				throw new NullPointerException();
		  }
	 }

	 /**
	  * 发送私聊消息(好友消息)。
	  * @param qqId 对应好友的QQ号
	  * @param msg 消息对象
	  * @throws NullPointerException {@link Mirai}对象为null
	  * @return 消息发送后的返回结果
	  */
	 public MsgResult sendPrivateMsg(long qqId,Message msg)
	 {
		  if (selfBot != null) { //如果selfBot不为null
				boolean hasThisFriend = false; //定义是否存在要把信息发送到的好友
				ContactList<Friend> friendsList = selfBot.getFriends(); //获取好友列表
				for (Friend friend : friendsList) { //遍历好友列表
					 if (friend.getId() == qqId) { //如果存在该好友
						  hasThisFriend = true;
						  break; //跳出循环
					 }
				}
				if (hasThisFriend) { //如果存在该好友
					 MessageReceipt<Contact> receipt = selfBot.getFriend(qqId).sendMessage(msg); //发送消息并获得返回信息
					 if (receipt != null) { //如果返回信息不为null
						  MsgResult result = new MsgResult(receipt); //发送成功
						  return result;
					 } else { //否则
						  MsgResult result = new MsgResult(receipt,-99); //其它
						  return result;
					 }
				} else { //否则
					 MsgResult result = new MsgResult(-1); //未添加该好友
					 return result;
				}
		  } else { //否则
				throw new NullPointerException();
		  }
	 }

	 /**
	  * 发送群消息。
	  * 
	  * @param groupId 对应群聊的群号
	  * @param msg  消息内容
	  * @throws NullPointerException {@link Mirai}对象为null
	  * @return 消息发送后的返回结果
	  */
	 public MsgResult sendGroupMsg(long groupId, String msg)
	 {
		  try {
				if (selfBot != null) { // 如果selfBot不为null
					 boolean hasJoinThisGroup = false; // 定义是否存在要把信息发送到的群聊
					 ContactList<Group> groupsList = selfBot.getGroups(); // 获取群聊列表
					 for (Group group : groupsList) { // 遍历群聊列表
						  if (group.getId() == groupId) { // 如果存在该群聊
								hasJoinThisGroup = true;
								break; // 跳出循环
						  }
					 }
					 if (hasJoinThisGroup) { // 如果存在该群聊
						  MessageReceipt<Contact> receipt = selfBot.getGroup(groupId).sendMessage(msg); // 发送消息并获得返回信息
						  if (receipt != null) { // 如果返回信息不为null
								MsgResult result = new MsgResult(receipt); // 发送成功
								return result;
						  } else { // 否则
								MsgResult result = new MsgResult(receipt, -99); // 其它
								return result;
						  }
					 } else { // 否则
						  MsgResult result = new MsgResult(-1); // 未添加该群聊
						  return result;
					 }
				} else { // 否则
					 throw new NullPointerException();
				}
		  } catch (BotIsBeingMutedException e) { // Bot被禁言
				MsgResult result = new MsgResult(-2); // Bot被禁言
				return result;
		  } catch (IllegalArgumentException e) { // 消息为空
				MsgResult result = new MsgResult(-3); // 消息为空
				return result;
		  } catch (MessageTooLargeException e) { // 消息内容过长
				MsgResult result = new MsgResult(-4); // 消息内容过长
				return result;
		  } catch (NullPointerException e) {
				throw e;
		  }
	 }

	 /**
	  * 发送群消息。
	  * @param groupId 对应群聊的群号
	  * @param msg 消息对象
	  * @throws NullPointerException {@link Mirai}对象为null
	  * @return 消息发送后的返回结果
	  */
	 public MsgResult sendGroupMsg(long groupId,Message msg)
	 {
		  try {
				if (selfBot != null) { //如果selfBot不为null
						boolean hasJoinThisGroup = false; //定义是否存在要把信息发送到的群聊
						ContactList<Group> groupsList = selfBot.getGroups(); //获取群聊列表
						for (Group group : groupsList) { //遍历群聊列表
							 if (group.getId() == groupId) { //如果存在该群聊
								  hasJoinThisGroup = true;
								  break; //跳出循环
							 }
						}
						if (hasJoinThisGroup) { //如果存在该群聊
							 MessageReceipt<Contact> receipt = selfBot.getGroup(groupId).sendMessage(msg); //发送消息并获得返回信息
							 if (receipt != null) { //如果返回信息不为null
								  MsgResult result = new MsgResult(receipt); //发送成功
								  return result;
							 } else { //否则
								  MsgResult result = new MsgResult(receipt,-99); //其它
								  return result;
							 }
						} else { //否则
							 MsgResult result = new MsgResult(-1); //未添加该群聊
							 return result;
						}
				  } else { //否则
						throw new NullPointerException();
				  }
		  } catch (BotIsBeingMutedException e) { //Bot被禁言
				MsgResult result = new MsgResult(-2); //Bot被禁言
				return result;
		  } catch (IllegalArgumentException e) { //消息为空
				MsgResult result = new MsgResult(-3); //消息为空
				return result;
		  } catch (MessageTooLargeException e) { //消息内容过长
				MsgResult result = new MsgResult(-4); //消息内容过长
				return result;
		  } catch (NullPointerException e) {
				throw e;
		  }
	 }

	 /**
	  * 获取登录的QQ账号的昵称
	  * @return 昵称
	  */
	 public String getLoginNick()
	 {
		  if (selfBot != null) { //如果selfBot不为null
				return selfBot.getSelfQQ().getNick(); //返回登陆帐号的昵称
		  } else { //否则
				throw new NullPointerException();
		  }
	 }
	 /**
	  * 获取登录的QQ账号的QQ号
	  * @return QQ号
	  */
	 public long getLoginQQ()
	 {
		  if (selfBot != null) { //如果selfBot不为null
				return selfBot.getSelfQQ().getId(); //返回登陆帐号的QQ号
		  } else { //否则
				throw new NullPointerException();
		  }
	 }
	 
	 /**
	  * 获取插件的数据路径
	  * @return 数据路径
	  */
	 public String getDataPath()
	 {
		  if (selfBot != null) { //如果selfBot不为null
				return MiraiAppAbstract.selfApp.getDataFolder().toString(); //返回插件的数据目录路径
		  } else { //否则
				throw new NullPointerException();
		  }
	 }
	 
	 /**
	  * 获取插件的数据文件夹对象
	  * @return 数据文件夹的{@link File}对象
	  */
	 public File getDataFile()
	 {
		  if (selfBot != null) { //如果selfBot不为null
				return MiraiAppAbstract.selfApp.getDataFolder(); //返回插件的数据目录路径
		  } else { //否则
				throw new NullPointerException();
		  }
	 }
	 
	 /**
	  * 向控制台写入调试(Debug)类日志。<br>
	  * 在继承{@link MiraiAppAbstract}的主类中存在一个方法{@link MiraiAppAbstract.isDebug()}<br>
	  * 若该方法返回true则输出调试日志,否则不输出调试日志。
	  * @param source 日志来源名,为null则使用插件名
	  * @param msg 日志内容
	  */
	 public void logDebug(String source,String msg)
	 {
		  if (isDebug) {
				if (selfBot != null) { //如果selfBot不为null
						if (msg != null) { //如果msg不为null
							 if (source == null) { //如果source为null
								  source = MiraiAppAbstract.selfApp.getPluginName$mirai_console(); //设置source为插件名
							 }
							 String logContent = getFormattedLog("DEBUG", source, msg);
							 System.out.println(logContent);
						} else { //否则
							throw new NullPointerException();
						}
				  } else { //否则
						throw new NullPointerException();
				  }
		  }
	 }
	 
	 /**
	  * 向控制台写入信息(Info)类日志。
	  * @param source 日志来源名,为null则使用插件名
	  * @param msg 日志内容
	  */
	 public void logInfo(String source,String msg)
	 {
		  if (selfBot != null) { //如果selfBot不为null
				if (msg != null) { //如果msg不为null
					 if (source == null) { //如果source为null
						  source = MiraiAppAbstract.selfApp.getPluginName$mirai_console(); //设置source为插件名
					 }
					 String logContent = getFormattedLog("INFO", source, msg);
					 System.out.println(logContent);
				} else { //否则
					throw new NullPointerException();
				}
		  } else { //否则
				throw new NullPointerException();
		  }
	 }
	 
	 /**
	  * 向控制台写入警告(Warning)类日志。
	  * @param source 日志来源名,为null则使用插件名
	  * @param msg 日志内容
	  */
	 public void logWarn(String source,String msg)
	 {
		  if (selfBot != null) { //如果selfBot不为null
				if (msg != null) { //如果msg不为null
					 if (source == null) { //如果source为null
						  source = MiraiAppAbstract.selfApp.getPluginName$mirai_console(); //设置source为插件名
					 }
					 String logContent = getFormattedLog("WARNING", source, msg);
					 System.out.println(logContent);
				} else { //否则
					throw new NullPointerException();
				}
		  } else { //否则
				throw new NullPointerException();
		  }
	 }
	 
	 /**
	  * 向控制台输出错误(Error)类日志。
	  * @param source 日志来源名,为null则使用插件名
	  * @param msg 日志内容
	  */
	 public void logError(String source,String msg)
	 {
		  if (selfBot != null) { //如果selfBot不为null
				if (msg != null) { //如果msg不为null
					 if (source == null) { //如果source为null
						  source = MiraiAppAbstract.selfApp.getPluginName$mirai_console(); //设置source为插件名
					 }
					 String logContent = getFormattedLog("ERROR", source, msg);
					 System.err.println(logContent);
				} else { //否则
					throw new NullPointerException();
				}
		  } else { //否则
				throw new NullPointerException();
		  }
	 }
	 
	 /**
	  * 获取实时的格式化的日志内容
	  * @param type 日志类型
	  * @param source 日志来源
	  * @param msg 日志内容
	  * @return 格式化后的日志内容
	  */
	 public static String getFormattedLog(@NotNull String type,@NotNull String source,@NotNull String msg)
	 {
		  	int rawHour,rawMinute,rawSecond;
		  	String hour,min,sec;
			Calendar logCalendar = Calendar.getInstance();
			rawHour = logCalendar.get(Calendar.HOUR_OF_DAY); //获取当前时间小时数
			rawMinute = logCalendar.get(Calendar.MINUTE); //获取当前时间分钟数
			rawSecond = logCalendar.get(Calendar.SECOND); //获取当前时间秒数
			if (rawHour <= 9) { //小时数补0
				 hour = "0" + Long.toString(rawHour);
			} else {
				 hour = Long.toString(rawHour);
			}
			if (rawMinute <= 9) { //分钟数补0
				 min = "0" + Long.toString(rawMinute);
			} else {
				 min = Long.toString(rawMinute);
			}
			if (rawSecond <= 9) { //秒数补0
				 sec = "0" + Long.toString(rawSecond);
			} else {
				 sec = Long.toString(rawSecond);
			}
			StringBuilder logBuilder = new StringBuilder(" ").append(hour).append(":").append(min).append(":").append(sec)
					  .append(" [").append(type.toUpperCase()).append("] [").append(source).append("] ").append(msg);
			return logBuilder.toString();
	 }
	 
	 /**
	  * 处理加群请求(常用参数)(Bot必须是管理员或群主)。<br>
	  * 本方法默认在拒绝和忽略时不会将请求发起人(申请加群的人员)加入黑名单(不再接收该人员的加群请求),如需加入黑名单请使用{@linkplain #setRequestGroupAdd(MemberJoinRequestEvent, int, String, boolean) 处理加群请求(完整参数)}。
	  * @param event 原请求事件
	  * @param processType 处理类型(0=通过请求,1=拒绝请求,2=忽略请求)
	  * @param rejectMsg 处理类型为拒绝请求时的拒绝信息(请求发起人可见),仅在处理类型为拒绝请求时可用(无需拒绝信息或为其它处理类型请置此项为null)
	  * @see #setRequestGroupAdd(MemberJoinRequestEvent, int, String, boolean) 处理加群请求(完整参数)
	  * @throws OperationNotSupportedException 无效的处理类型
	  */
	 public void setRequestGroupAdd(MemberJoinRequestEvent event,int processType,String rejectMsg)
	 {
		  try {
				switch (processType)
				{
				case 0: //允许
					 event.accept();
					 break;
				case 1: //拒绝
					 if (rejectMsg == null) {
						  event.reject();
					 } else {
						  event.reject(false, rejectMsg);
					 }
					 break;
				case 2: //忽略
					 event.ignore();
				default:
					 throw new OperationNotSupportedException("processType is invalid");
				}
		  } catch (Exception e) {
				logError(MiraiAppAbstract.selfApp.getPluginName$mirai_console(), e.getMessage());
		  }
	 }
	 
	 /**
	  * 处理加群请求(完整参数)(Bot必须是管理员或群主)。
	  * @param event 原请求事件
	  * @param processType 处理类型(0=通过请求,1=拒绝请求,2=忽略请求)
	  * @param rejectMsg 处理类型为拒绝请求时的拒绝信息(请求发起人可见),仅在处理类型为拒绝请求时可用(无需拒绝信息或为其它处理类型请置此项为null)
	  * @param isBlackList 是否将对应成员加入黑名单(不再接收该人员的加群请求),仅在处理类型为拒绝请求和忽略请求时可用(其它处理类型请置此项为false)
	  * @see #setRequestGroupAdd(MemberJoinRequestEvent, int, String) 处理加群请求(常用参数)
	  * @throws OperationNotSupportedException 无效的处理类型
	  */
	 public void setRequestGroupAdd(MemberJoinRequestEvent event,int processType,String rejectMsg,boolean isBlackList)
	 {
		  try {
				switch (processType)
				{
				case 0: //允许
					 event.accept();
					 break;
				case 1: //拒绝
					 if (rejectMsg == null) {
						  event.reject(isBlackList);
					 } else {
						  event.reject(isBlackList, rejectMsg);
					 }
					 break;
				case 2: //忽略
					 event.ignore(isBlackList);
				default:
					 throw new OperationNotSupportedException("processType is invalid");
				}
		  } catch (Exception e) {
				logError(MiraiAppAbstract.selfApp.getPluginName$mirai_console(), e.getMessage());
		  }
	 }
	 
	 /**
	  * 处理Bot被邀请入群请求。
	  * @param event 原请求事件
	  * @param processType 处理类型(0=通过请求,1=忽略请求)
	  * @throws OperationNotSupportedException 无效的处理类型
	  */
	 public void setRequestBotInviteJoinGroup(BotInvitedJoinGroupRequestEvent event,int processType)
	 {
		  try {
				switch (processType)
				{
				case 0: //允许
					 event.accept();
					 break;
				case 1: //忽略
					 event.ignore();
				default:
					 throw new OperationNotSupportedException("processType is invalid");
				}
		  } catch (Exception e) {
				logError(MiraiAppAbstract.selfApp.getPluginName$mirai_console(), e.getMessage());
		  }
	 }
	 
	 @Override
	 public String toString()
	 {
		  return "Mirai(BotQQ=" + selfBot.getSelfQQ().getId() + ", " + "BotId=" + selfBot.getId() + ")";
	 }
}
