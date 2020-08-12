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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.jetbrains.annotations.NotNull;
import org.ots123it.jhlper.CommonHelper;
import org.ots123it.jhlper.JsonHelper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.console.MiraiConsole;
import net.mamoe.mirai.console.command.BlockingCommand;
import net.mamoe.mirai.console.command.CommandSender;

/**
 * JMirai的mirai-console控制台核心功能
 * @since 0.0.2
 * @author 御坂12456
 *
 */
public class JMiraiConProcessor extends BlockingCommand
{
	 public JMiraiConProcessor()
	 {
		  super(NAME, ALIAS, DESCRIPTION, USAGE);
	 }

	 protected static final String DESCRIPTION = "JMirai控制台实用工具";
	 
	 protected static final String USAGE = "/jmirai [getprop|update|ver|stat|help]";
	 
	 protected static final String[] HELP = {"/jmirai getprop - 获取JVM系统环境变量",
														   "/jmirai ver - 查看JMirai版本信息",
														   "/jmirai stat - 查看JMirai运行状态",
														   "/jmirai help - 获取本帮助"};
	 
	 protected static final String NAME = "jmirai";
	 
	 protected static final ArrayList<String> ALIAS = new ArrayList<String>();
	 @Override
	 public List<String> getAlias()
	 {
		  return ALIAS;
	 }

	 @Override
	 public String getDescription()
	 {
		 return DESCRIPTION;
	 }

	 @Override
	 public String getName()
	 {
		  return NAME;
	 }

	 @Override
	 public String getUsage()
	 {
		  return USAGE;
	 }

	 @Override
	 public boolean onCommandBlocking(@NotNull CommandSender sender,@NotNull List<String> list)
	 {
		  if (list.size() < 1) { //如果没有参数
				return false; //返回Usage
		  }
		  switch (list.get(0).toLowerCase()) //获得第一个参数
		  {
		  case "getprop": //获取JVM系统环境变量
				if (list.size() < 2) {
					 System.out.println(Mirai.getFormattedLog("INFO", "JMirai", "/jmirai getprop [环境变量名]"));
				} else {
					 String propName = list.get(1); //获取环境变量名
					 String propValue = System.getProperty(propName); //获取环境变量值
					 System.out.println(Mirai.getFormattedLog("INFO", "JMirai", propValue));
				}
				return true;
		  case "update": //检查新版本
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
				return true;
		  case "ver": //查看版本信息
				String miraiConsoleVer = MiraiConsole.version; //获取mirai-console当前版本号
				String jMiraiVer = JMirai.JMIRAI_VERSION; //获取JMirai当前版本号
				System.out.println(Mirai.getFormattedLog("INFO", "JMirai", "Current JMirai Version: " + jMiraiVer));
				System.out.println(Mirai.getFormattedLog("INFO", "JMirai", "Current Mirai-Console Version: " + miraiConsoleVer));
				return true;
		  case "stat": //查看运行状态
				TimeZone.setDefault(TimeZone.getTimeZone("GMT+0:00"));
				Calendar nowCalendar = Calendar.getInstance();
				String miraiConsoleVer2 = MiraiConsole.version; //获取mirai-console当前版本号
				String jMiraiVer2 = JMirai.JMIRAI_VERSION; //获取JMirai当前版本号
				String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath(); //获取JMirai库文件所在路径
				long runningTimeLong = System.currentTimeMillis() - MiraiAppAbstract.JMirai_Start; //获取JMirai已运行毫秒数
				nowCalendar.setTimeInMillis(runningTimeLong);
				String runningTimeStr = new SimpleDateFormat("HH:mm:ss").format(nowCalendar.getTime()); //获取JMirai已运行时间(格式化后字符串)
				try {
					 path = URLDecoder.decode(path, "UTF-8");
				} catch (UnsupportedEncodingException e) {
				}
				StringBuilder statBuilder = new StringBuilder("JMirai - A Powerful Java SDK for mirai-console plugin\n")
						  .append("Current JMirai Version: ").append(jMiraiVer2).append("\n")
						  .append("Current Mirai-Console Version: ").append(miraiConsoleVer2).append("\n")
						  .append("JMirai已运行: ").append(runningTimeStr).append("\n")
						  .append("JMirai is now running under ").append(path).append("\n")
						  .append("此程序以GNU GPL3.0协议发布，使用时请遵守协议\n")
						  .append("代码库：github.com/123-Open-Source-Organization/JMirai\n")
						  .append("Powered by 123 Open-Source Organization and jmirai contributors");
				System.out.println(Mirai.getFormattedLog("INFO", "JMirai", statBuilder.toString()));
				return true;
		  case "help": //查看帮助
				for (String eachLine : HELP) {
					 System.out.println(Mirai.getFormattedLog("INFO", "JMirai", eachLine));
				}
				return true;
		  default:
				return false; //返回Usage
		  }
	 }


}
