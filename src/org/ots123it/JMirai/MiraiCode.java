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

import java.io.File;
import org.ots123it.jhlper.ExceptionHelper;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Face;
import net.mamoe.mirai.message.data.FriendFlashImage;
import net.mamoe.mirai.message.data.FriendImage;
import net.mamoe.mirai.message.data.GroupFlashImage;
import net.mamoe.mirai.message.data.GroupImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PokeMessage;
import net.mamoe.mirai.utils.OverFileSizeMaxException;

/**
 * Mirai码实现类。<br>
 * 该类可以帮助开发者序列化Mirai码。<br>
 * Mirai码规范请<a href="https://github.com/123-Open-Source-Organization/mirai/blob/master/docs/mirai-code-specification.md">单击这里</a>
 * @since 0.0.2
 * @author 御坂12456
 *
 */
public class MiraiCode
{
	 protected Mirai selfMirai = null;
	 protected long selfGroupId = 0L;
	 protected Friend selfFriend = null;
	 /**
	  * 当前{@link MiraiCode}实例的构造来源是否是一个群聊的标志。<br>
	  * true为群聊构造的实例,false为好友(私聊)构造的实例。
	  */
	 private boolean isGroup = false;
	 
	 /**
	  * 使用{@link Mirai}对象和群号初始化{@link MiraiCode}的新实例。<br>
	  * 使用该构造函数初始化{@link MiraiCode}对象后将标记为<b>群聊构造的实例</b>。
	  * @param mirai {@link Mirai}对象
	  * @param groupId 要将{@link MiraiCode}发送到的群聊群号
	  * @throws NullPointerException mirai参数值为null或groupId参数值等于0
	  */
	 public MiraiCode(Mirai mirai,long groupId)
	 {
		  if ((mirai != null) && (groupId != 0)) {
				selfMirai = mirai;
				selfGroupId = groupId;
				isGroup = true;
		  } else {
				throw new NullPointerException();
		  }
	 }
	 
	 /**
	  * 使用{@link Mirai}对象和群聊对象({@link Group}对象)初始化{@link MiraiCode}的新实例。<br>
	  * 使用该构造函数初始化{@link MiraiCode}对象后将标记为<b>群聊构造的实例</b>。
	  * @param mirai {@link Mirai}对象
	  * @param group 要将{@link MiraiCode}发送到的群聊对象({@link Group}对象)
	  * @throws NullPointerException mirai参数或friend参数值为null
	  */
	 public MiraiCode(Mirai mirai,Group group)
	 {
		  if ((mirai != null) && (group != null)) {
				selfMirai = mirai;
				selfGroupId = group.getId();
				isGroup = true;
		  } else {
				throw new NullPointerException();
		  }
	 }
	 
	 /**
	  * 使用{@link Mirai}对象和好友QQ对象({@link Friend}对象)初始化{@link MiraiCode}的新实例。<br>
	  * 使用该构造函数初始化{@link MiraiCode}对象后将标记为<b>好友(私聊)构造的实例</b>。
	  * @param mirai {@link Mirai}对象
	  * @param friend 要将{@link MiraiCode}发送到的好友QQ对象({@link Friend}对象)
	  * @throws NullPointerException mirai参数或friend参数值为null
	  */
	 public MiraiCode(Mirai mirai,Friend friend)
	 {
		  if ((mirai != null) && (friend != null)) {
				selfMirai = mirai;
				selfFriend = friend;
				isGroup = false;
		  } else {
				throw new NullPointerException();
		  }
	 }

	 /**
	  * 群At(@)消息(末尾带空格)<br>
	  * 末尾加一个空格可以使At更加美观,如不需要加空格,请使用{@linkplain #at(long, boolean) 群At}方法。
	  * @param qqId 要At到的目标成员,如果目标成员不在群内则返回null
	  * @return Mirai码
	  * @see #at(long, boolean) 群At
	  * @throws UnsupportedOperationException 尝试用来源为一个好友的{@link MiraiCode}对象调用本方法时抛出此异常
	  */
	 public String at(long qqId)
	 {
		  if (this.isGroup == true) { //如果本对象来源为群聊
				try {
					 	Member targetMember = selfMirai.getBot().getGroup(selfGroupId).getMembers().getOrNull(qqId); //获取目标成员对象
					 	if (targetMember == null) { //如果目标成员不在群内
					 		 return null;
					 	} else { //否则
					 		String atCode = new At(selfMirai.getBot().getGroup(selfGroupId).getMembers().get(qqId)).toMiraiCode(); //获取Mirai码
							atCode = atCode.trim() + " "; //加空格
							return atCode;
					 	}
				  } catch (Exception e) {
						selfMirai.logError("JMirai", ExceptionHelper.getStackTrace(e));
						return null;
				  }
		  } else { //否则
				throw new UnsupportedOperationException("Method 'at' is not supported in a MiraiCode object whose source is a friend.");
		  }
	 }
	 
	 /**
	  * 群At(@)消息<br>
	  * @param qqId 要At到的目标成员,如果目标成员不在群内则返回null
	  * @param isSpaceTail 是否在At消息末尾添加空格,如不需添加请置此项为true。
	  * @return Mirai码
	  * @see #at(long) 群At(末尾带空格)
	  * @throws UnsupportedOperationException 尝试用来源为一个好友的{@link MiraiCode}对象调用本方法时抛出此异常
	  */
	 public String at(long qqId,boolean isNotSpaceTail)
	 {
		  if (this.isGroup == true) { //如果本对象来源为群聊
				try {
					 	Member targetMember = selfMirai.getBot().getGroup(selfGroupId).getMembers().getOrNull(qqId); //获取目标成员对象
					 	if (targetMember == null) { //如果目标成员不在群内
					 		 return null;
					 	} else { //否则
					 		String atCode = new At(selfMirai.getBot().getGroup(selfGroupId).getMembers().get(qqId)).toMiraiCode(); //获取Mirai码
							atCode = atCode.trim();
							if (!isNotSpaceTail) { //如果需要在末尾加空格
								 atCode = atCode + " ";
							}
							return atCode;
					 	}
				  } catch (Exception e) {
						selfMirai.logError("JMirai", ExceptionHelper.getStackTrace(e));
						return null;
				  }
		  } else { //否则
				throw new UnsupportedOperationException("Method 'at' is not supported in a MiraiCode object whose source is a friend.");
		  }
	 }
	 
	 /**
	  * 群At(@)消息(全体成员)(末尾带空格)<br>
	  * <p>非会员每天只可发送10条"@全体成员",会员每天可发送20条"@全体成员"。<br>
	  * 次数不够或权限不足将自动转为文本。</p>
	  * 末尾加一个空格可以使At更加美观,如不需要加空格,请使用{@linkplain #atAll(boolean) 群At(全体成员)}方法。
	  * @return Mirai码
	  * @see #atAll(boolean) 群At(全体成员)
	  * @throws UnsupportedOperationException 尝试用来源为一个好友的{@link MiraiCode}对象调用本方法时抛出此异常
	  */
	 public String atAll()
	 {
		  if (this.isGroup == true) { //如果本对象来源为群聊
				return "[mirai:atall] ";
		  } else { //否则
				throw new UnsupportedOperationException("Method 'atAll' is not supported in a MiraiCode object whose source is a friend.");
		  }
	 }
	 
	 /**
	  * 群At(@)消息(全体成员)<br>
	  * <p>非会员每天只可发送10条"@全体成员",会员每天可发送20条"@全体成员"。<br>
	  * 次数不够或权限不足将自动转为文本。</p>
	  * @param isSpaceTail 是否在At消息末尾添加空格,如不需添加请置此项为true。
	  * @return Mirai码
	  * @see #atAll() 群At(全体成员)(末尾带空格)
	  * @throws UnsupportedOperationException 尝试用来源为一个好友的{@link MiraiCode}对象调用本方法时抛出此异常
	  */
	 public String atAll(boolean isNotSpaceTail)
	 {
		  if (this.isGroup == true) { //如果本对象来源为群聊
				if (!isNotSpaceTail) { //如果末尾带空格
					 return "[mirai:atall] ";
				} else { //否则
					 return "[mirai:atall]";
				}
		  } else { //否则
				throw new UnsupportedOperationException("Method 'atAll' is not supported in a MiraiCode object whose source is a friend.");
		  }
	 }
	 
	 /**
	  * QQ自带表情
	  * @param faceId 表情id(<a href="https://github.com/123-Open-Source-Organization/mirai/blob/master/mirai-core/src/commonMain/kotlin/net.mamoe.mirai/message/data/Face.kt#L47">查看表情id列表</a>)<br>可以直接使用{@link Face}类下的表情id常量。
	  * @return Mirai码
	  */
	 public String face(int faceId)
	 {
		  Face currentFace = new Face(faceId);
		  return currentFace.toMiraiCode(); //获得Mirai码
	 }
	 
	 /**
	  * 戳一戳消息
	  * @param pokeMsgObj 戳一戳消息类型(<a href="https://github.com/123-Open-Source-Organization/mirai/blob/master/mirai-core/src/commonMain/kotlin/net.mamoe.mirai/message/data/HummerMessage.kt#L63">查看戳一戳消息类型列表</a>)<br>可以直接使用{@link PokeMessage}下的戳一戳常量对象。
	  * @return Mirai码
	  */
	 public String pokeMsg(PokeMessage pokeMsgObj)
	 {
		  return pokeMsgObj.toMiraiCode(); //获得Mirai码
	 }
	 
	 /**
	  * 图片消息<br>
	  * <p>为获取到图片消息对应的Mirai码,调用本方法会<b>立即上传</b>图片以获取临时的图片id(不会立即发送到群聊/好友(私聊)中)。<br>
	  * 强烈建议在消息处理的最后使用该方法获取Mirai码然后直接发送。<br></p>
	  * <p>若需要发送图文消息(同一消息内包含图片及文字),请将获得的Mirai码附加在文字消息后再发送,否则会无法发送(抛出异常)。<br>
	  * <b>注意:图文消息不支持闪照图片,尝试发送会导致Mirai-Console抛出异常。</b></p>
	  * @param imagePath 图片文件的路径
	  * @param isFlashImage 是否为闪照图片,若发送闪照图片请置此项为true。
	  * @return Mirai码，若图片上传失败则为null
	  * @throws NullPointerException 路径参数为null
	  * @throws OverFileSizeMaxException 图片文件大小超过QQ允许的图片大小上限
	  * @see #image(File, boolean) 图片消息(图片文件对象为参数)
	  */
	 public String image(String imagePath,boolean isFlashImage) {
		  if (imagePath == null) { //如果图片路径为null
				throw new NullPointerException("imagePath cannot be null");
		  }
		  try {
				File imageFile = new File(imagePath); //定义图片文件对象
				if (imageFile.exists()) { //如果指定路径对应的图片文件存在
					 if (isGroup) {
						  Image currentImage = selfMirai.getBot().getGroup(selfGroupId).uploadImage(imageFile); //上传图片文件
						  GroupImage currentGImage = new GroupImage() //转换成群聊图片对象
						  {
						  	 @Override
						  	 public String getImageId()
						  	 {
						  		  return currentImage.getImageId();
						  	 }
						  };
						  if (isFlashImage) { //如果要发送的图片是闪照
								GroupFlashImage currentGFImage = new GroupFlashImage(currentGImage); //转换为闪照图片
								String gFImageCode = currentGFImage.toMiraiCode(); //获得Mirai码
								return gFImageCode;
						  } else { //否则
								String gImageCode = currentGImage.toMiraiCode(); //获得Mirai码
							   return gImageCode;
						  }
 					 } else {
 						 Image currentImage = selfFriend.uploadImage(imageFile); //上传图片文件
 						 FriendImage currentFImage = new FriendImage() //转换成好友(私聊)图片对象
 						 {
 							  @Override
 							  public String getImageId()
 							  {
 									return currentImage.getImageId();
 							  }
 						 };
 						 if (isFlashImage) {
 							  FriendFlashImage currentFFImage = new FriendFlashImage(currentFImage); //转换为闪照图片
 							  String fFImageCode = currentFFImage.toMiraiCode(); //获得Mirai码
 							  return fFImageCode;
 						 } else {
 							  String fImageCode = currentFImage.toMiraiCode(); //获得Mirai码
 							  return fImageCode;
 						 }
 					 }
					 
				} else { //否则
					 return null;
				}		
		  } catch (OverFileSizeMaxException e) { //图片大小超限
				throw e;
		  } catch (Exception e) {
				selfMirai.logError("JMirai", ExceptionHelper.getStackTrace(e));
				return null;
		  }
	 }
	 
	 /**
	  * 图片消息<br>
	  * <p>为获取到图片消息对应的Mirai码,调用本方法会<b>立即上传</b>图片以获取临时的图片id(不会立即发送到群聊/好友(私聊)中)。<br>
	  * 强烈建议在消息处理的最后使用该方法获取Mirai码然后直接发送。<br></p>
	  * <p>若需要发送图文消息(同一消息内包含图片及文字),请将获得的Mirai码附加在文字消息后再发送,否则会无法发送(抛出异常)。<br>
	  * <b>注意:图文消息不支持闪照图片,尝试发送会导致Mirai-Console抛出异常。</b></p>
	  * @param image 图片文件对象
	  * @param isFlashImage 是否为闪照图片,若发送闪照图片请置此项为true。
	  * @return Mirai码，若图片上传失败则为null
	  * @throws NullPointerException 文件参数为null
	  * @throws OverFileSizeMaxException 图片文件大小超过QQ允许的图片大小上限
	  * @see #image(String,boolean) 图片消息(图片路径为参数)
	  */
	 public String image(File image,boolean isFlashImage) {
		  if (image == null) { //如果图片路径为null
				throw new NullPointerException("image cannot be null");
		  }
		  try {
				if (image.exists()) { //如果指定路径对应的图片文件存在
					 if (isGroup) {
						  Image currentImage = selfMirai.getBot().getGroup(selfGroupId).uploadImage(image); //上传图片文件
						  GroupImage currentGImage = new GroupImage() //转换成群聊图片对象
						  {
						  	 @Override
						  	 public String getImageId()
						  	 {
						  		  return currentImage.getImageId();
						  	 }
						  };
						  if (isFlashImage) { //如果要发送的图片是闪照
								GroupFlashImage currentGFImage = new GroupFlashImage(currentGImage); //转换为闪照图片
								String gFImageCode = currentGFImage.toMiraiCode(); //获得Mirai码
								return gFImageCode;
						  } else { //否则
								String gImageCode = currentGImage.toMiraiCode(); //获得Mirai码
							   return gImageCode;
						  }
 					 } else {
 						 Image currentImage = selfFriend.uploadImage(image); //上传图片文件
 						 FriendImage currentFImage = new FriendImage() //转换成好友(私聊)图片对象
 						 {
 							  @Override
 							  public String getImageId()
 							  {
 									return currentImage.getImageId();
 							  }
 						 };
 						 if (isFlashImage) {
 							  FriendFlashImage currentFFImage = new FriendFlashImage(currentFImage); //转换为闪照图片
 							  String fFImageCode = currentFFImage.toMiraiCode(); //获得Mirai码
 							  return fFImageCode;
 						 } else {
 							  String fImageCode = currentFImage.toMiraiCode(); //获得Mirai码
 							  return fImageCode;
 						 }
 					 }
					 
				} else { //否则
					 return null;
				}		
		  } catch (OverFileSizeMaxException e) { //图片大小超限
				throw e;
		  } catch (Exception e) {
				selfMirai.logError("JMirai", ExceptionHelper.getStackTrace(e));
				return null;
		  }
	 }

}
