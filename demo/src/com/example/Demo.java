/**
 * Copyright (C) 2020 [Your Name]
 */
package com.example;

import org.ots123it.JMirai.Mirai;
import org.ots123it.JMirai.MiraiAppAbstract;

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

	 /**
	  * 是否启用Debug模式,若启用则:<br>
	  * <ul>
	  * <li>输出所有接收的消息内容(参考{@link JMsg.groupMsg(Mirai,GroupMessageEvent,Message,long,long,String,long,String,String)}方法的参数)</li>
	  * <li>输出使用{@link Mirai.logDebug(String,String)}方法输出的日志</li>
	  * <li>输出使用{@link Mirai.logError(String,Throwable)}方法输出的日志</li>
	  * </ul>
	  * @return 是否启动Debug模式的标志
	  */
	 public boolean isDebug()
	 {
		  // TODO: 若启用Debug模式请返回true,否则返回false。
		  return false;
	 }
	 
	 /**
	  * 事件03:成员已入群(群成员增加)事件<br>
	  * 注意:Bot帐号已入群【不会】触发本事件,请参考{@linkplain #botHasJoinGroup(Mirai, BotJoinGroupEvent, long, String) 事件101:Bot已入群事件}。<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param fromGroup 事件来源群号
	  * @param groupName 事件来源群名
	  * @param fromQQ 新成员QQ号
	  * @param qqName 新成员QQ昵称
	  * @see #botHasJoinGroup(Mirai, BotJoinGroupEvent, long, String) 事件101:Bot已入群事件
	  */
	 @Override
	 public void groupMemberIncrease(Mirai Mirai, MemberJoinEvent rawEvent, long fromGroup, String groupName,
				long fromQQ, String qqName)
	 {
		  // TODO 自动生成的方法存根
		  
	 }

	 /**
	  * 事件04:成员已退群/被移除群(群成员减少)事件<br>
	  * 注意:Bot帐号退群/被移出群【不会】触发本事件,请参考{@linkplain #botHasExitGroup(Mirai, BotLeaveEvent, long, String) 事件103:Bot已退群/被移出群事件}。<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param fromGroup 事件来源群号
	  * @param groupName 事件来源群名
	  * @param fromQQ 离开的成员的QQ号
	  * @param qqName 离开的成员的QQ昵称
	  * @param isKick 是否该成员是被移出的(成员被移出群为true,否则为false)
	  * @see #botHasExitGroup(Mirai, BotLeaveEvent, long, String) 事件103:Bot已退群/被移出群事件
	  */
	 @Override
	 public void groupMemberDecrease(Mirai Mirai, MemberLeaveEvent rawEvent, long fromGroup, String groupName,
				long fromQQ, String qqName, boolean isKick)
	 {
		  // TODO 自动生成的方法存根
		  
	 }

	 /**
	  * 事件05:成员入群请求事件<br>
	  * 注意:Bot帐号请求被邀请入群【不会】触发本事件,请参考{@linkplain #requestBotInviteJoinGroup(Mirai, BotInvitedJoinGroupRequestEvent, long, long, String, long, String) 事件102:Bot被邀请入群请求事件}。<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param requestId 入群请求id
	  * @param fromGroup 事件来源群号
	  * @param groupName 事件来源群名
	  * @param fromQQ 新成员QQ号
	  * @param qqName 新成员QQ昵称
	  * @param requestMsg 所填写的验证信息/答案内容
	  * @see #requestBotInviteJoinGroup(Mirai, BotInvitedJoinGroupRequestEvent, long, long, String, long, String) 事件102:Bot被邀请入群请求事件
	  */
	 @Override
	 public void requestGroupAdd(Mirai Mirai, MemberJoinRequestEvent rawEvent, long requestId, long fromGroup,
				String groupName, long fromQQ, String qqName, String requestMsg)
	 {
		  // TODO 自动生成的方法存根
		  
	 }

	 /**
	  * 事件06:群成员被禁言事件<br>
	  * 注意:Bot帐号被禁言【不会】触发本事件,请参考{@linkplain #botMute(Mirai, BotMuteEvent, long, String, long, String, int) 事件105:Bot被禁言事件}。<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param fromGroup 事件来源群号
	  * @param groupName 事件来源群名
	  * @param fromQQ 操作者成员QQ号
	  * @param qqName 操作者成员QQ昵称
	  * @param targetQQ 被禁言目标成员QQ号
	  * @param targetQQName 被禁言目标成员QQ昵称
	  * @param duration 禁言时长(单位为秒)
	  * @see #botMute(Mirai, BotMuteEvent, long, String, long, String, int) 事件105:Bot被禁言事件
	  */
	 @Override
	 public void groupMemberMute(Mirai Mirai, MemberMuteEvent rawEvent, long fromGroup, String groupName, long fromQQ,
				String qqName, long targetQQ, String targetQQName, int duration)
	 {
		  // TODO 自动生成的方法存根
		  
	 }

	 /**
	  * 事件07:群成员被解禁事件<br>
	  * 注意:Bot帐号被解禁【不会】触发本事件,请参考{@linkplain #botUnmute(Mirai, BotUnmuteEvent, long, String, long, String) 事件106:Bot被解禁事件}。<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param fromGroup 事件来源群号
	  * @param groupName 事件来源群名
	  * @param fromQQ 操作者成员QQ号
	  * @param qqName 操作者成员QQ昵称
	  * @param targetQQ 被解禁目标成员QQ号
	  * @param targetQQName 被解禁目标成员QQ昵称
	  * @see #botUnmute(Mirai, BotUnmuteEvent, long, String, long, String) 事件106:Bot被解禁事件
	  */
	 @Override
	 public void groupMemberUnmute(Mirai Mirai, MemberUnmuteEvent rawEvent, long fromGroup, String groupName,
				long fromQQ, String qqName, long targetQQ, String targetQQName)
	 {
		  // TODO 自动生成的方法存根
		  
	 }

	 /**
	  * 事件08:群全员禁言开关变动事件<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param fromGroup 事件来源群号
	  * @param groupName 事件来源群名
	  * @param fromQQ 操作者成员的QQ号
	  * @param qqName 操作者成员的QQ昵称
	  * @param isMuteAll 该群是否开启了全员禁言,若为开启全员禁言事件则为true,否则为false
	  */
	 @Override
	 public void groupMuteAll(Mirai Mirai, GroupMuteAllEvent rawEvent, long fromGroup, String groupName, long fromQQ,
				String qqName, boolean isMuteAll)
	 {
		  // TODO 自动生成的方法存根
		  
	 }

	 /**
	  * 事件09:群成员权限变动事件<br>
	  * 注意:Bot帐号权限变动【不会】触发本事件,请参考{@linkplain #botPermissionChange(Mirai, BotGroupPermissionChangeEvent, long, String, int, int) 事件104:Bot权限变动事件}。<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param fromGroup 事件来源群号
	  * @param groupName 事件来源群名
	  * @param fromQQ 权限被变动的成员的QQ号
	  * @param qqName 权限被变动的成员的QQ昵称
	  * @param oldPermission 变动前对应成员的原权限(0=普通成员,1=管理员,2=群主)
	  * @param newPermission 变动后的对应成员的当前权限(0=普通成员,1=管理员,2=群主)
	  * @see #botPermissionChange(Mirai, BotGroupPermissionChangeEvent, long, String, int, int) 事件104:Bot权限变动事件
	  */
	 @Override
	 public void groupMemberPermissionChange(Mirai Mirai, MemberPermissionChangeEvent rawEvent, long fromGroup,
				String groupName, long fromQQ, String qqName, int oldPermission, int newPermission)
	 {
		  // TODO 自动生成的方法存根
		  
	 }

	 /**
	  * 事件101:Bot已入群事件<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param fromGroup 加入的群号
	  * @param groupName 加入的群名
	  * @see #groupMemberIncrease(Mirai, MemberJoinEvent, long, String, long, String) 事件03:成员已入群(群成员增加)事件
	  */
	 @Override
	 public void botHasJoinGroup(Mirai Mirai, BotJoinGroupEvent rawEvent, long fromGroup, String groupName)
	 {
		  // TODO 自动生成的方法存根
		  
	 }

	 /**
	  * 事件102:Bot被邀请入群请求事件<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param requestId 请求id
	  * @param fromGroup 被邀请入群的群号
	  * @param groupName 被邀请入群的群名
	  * @param fromQQ 邀请人QQ号
	  * @param qqName 邀请人QQ昵称
	  * @see #requestGroupAdd(Mirai, MemberJoinRequestEvent, long, long, String, long, String, String) 事件05:成员入群请求事件
	  */
	 @Override
	 public void requestBotInviteJoinGroup(Mirai Mirai, BotInvitedJoinGroupRequestEvent rawEvent, long requestId,
				long fromGroup, String groupName, long fromQQ, String qqName)
	 {
		  // TODO 自动生成的方法存根
		  
	 }

	 /**
	  * 事件103:Bot已退群/被移出群事件<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param requestId 请求id
	  * @param fromGroup 离开的群的群号
	  * @param groupName 离开的群的群名
	  * @param isKick 是否Bot是被移出的(Bot被移出群为true,否则为false)
	  * @see #groupMemberDecrease(Mirai, MemberLeaveEvent, long, String, long, String, boolean) 事件04:成员已退群/被移除群(群成员减少)事件
	  */
	 @Override
	 public void botHasExitGroup(Mirai Mirai, BotLeaveEvent rawEvent, long fromGroup, String groupName, boolean isKick)
	 {
		  // TODO 自动生成的方法存根
		  
	 }

	 /**
	  * 事件104:Bot权限变动事件<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param fromGroup 事件来源群号
	  * @param groupName 事件来源群名
	  * @param oldPermission 变动前Bot的原权限(0=普通成员,1=管理员,2=群主)
	  * @param newPermission 变动后Bot的当前权限(0=普通成员,1=管理员,2=群主)
	  * @see #groupMemberPermissionChange(Mirai, MemberPermissionChangeEvent, long, String, long, String, int, int) 事件09:群成员权限变动事件
	  */
	 @Override
	 public void botPermissionChange(Mirai Mirai, BotGroupPermissionChangeEvent rawEvent, long fromGroup,
				String groupName, int oldPermission, int newPermission)
	 {
		  // TODO 自动生成的方法存根
		  
	 }

	 /**
	  * 事件105:Bot被禁言事件<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param fromGroup 事件来源群号
	  * @param groupName 事件来源群名
	  * @param fromQQ 操作者成员QQ号
	  * @param qqName 操作者成员QQ昵称
	  * @param duration 禁言时长(单位为秒)
	  * @see #groupMemberMute(Mirai, MemberMuteEvent, long, String, long, String, long, String, int) 事件06:群成员被禁言事件
	  */
	 @Override
	 public void botMute(Mirai Mirai, BotMuteEvent rawEvent, long fromGroup, String groupName, long fromQQ,
				String qqName, int duration)
	 {
		  // TODO 自动生成的方法存根
		  
	 }

	 /**
	  * 事件106:Bot被解禁事件<br>
	  * 该方法会在mirai-console【主线程】中被调用。
	  * 
	  * @param Mirai    {@link Mirai}对象
	  * @param rawEvent 原事件对象
	  * @param fromGroup 事件来源群号
	  * @param groupName 事件来源群名
	  * @param fromQQ 操作者成员QQ号
	  * @param qqName 操作者成员QQ昵称
	  * @see #groupMemberUnmute(Mirai, MemberUnmuteEvent, long, String, long, String, long, String) 事件07:群成员被解禁事件
	  */
	 @Override
	 public void botUnmute(Mirai Mirai, BotUnmuteEvent rawEvent, long fromGroup, String groupName, long fromQQ,
				String qqName)
	 {
		  // TODO 自动生成的方法存根
		  
	 }
	 
}
