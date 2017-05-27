package com.honglei.toolbox.utils;

import java.util.regex.Pattern;


/**
 * 正则验证
 * 
 * @author Cary.Liu
 * @create 2014-09-24
 *
 */
public class RegexValidate {
	
	
	/**
	 * 是否为uuid字符串
	 * @param str
	 * @return
	 */
	public static boolean isUuid(String str) {
		String reg= "^[0-9a-zA-Z]{8}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{4}-[0-9a-zA-Z]{12}$";
		
		return Pattern.matches(reg,str);
	}
	
	/**
	 * 判断身份证
	 * @param str
	 * @return
	 */
	public static boolean isIdCard(String str) {
		String reg= "([0-9]{17}([0-9]|X|x))|([0-9]{15})";
		
		return Pattern.matches(reg,str);
	}
	
	/**
	 * 验证数字号码
	 * @param str
	 * @return
	 */
	public static boolean isCard(String str) {
		 return str.matches("^[0-9]*$");
	}
	
	/**
	 * 验证用户名(客户用户)
	 * @param str
	 * @return
	 */
	public static boolean isUsername(String str) {
		
		 return str.matches("^([0-9.]|[A-Za-z.]|[0-9A-Za-z_.]){6,20}$");
	}
	
	/**
	 * 验证用户名(后台用户)
	 * @param str
	 * @return
	 */
	public static boolean isApproveUsername(String str) {
		
		 return str.matches("^([0-9.]|[A-Za-z.]|[0-9A-Za-z_.]){2,20}$");
	}
	
	/**
	 * 验证手机号码
	 * 格式：以1开头的11为数字 
	 * @param str
	 * @return
	 */
	public static boolean isPhoneNum(String str) {
		
		return str.matches("^1\\d{10}$");
	}
	
	
	
	/**
	 * 固定号码验证
	 * @param area 区号(格式0731-7561872)
	 * @param num 号码
	 * @return
	 */
	public static boolean isTel(String area , String num){
		String regex = "(^(\\d{3,4}-)?\\d{7,8})$|(13[0-9]{9})";
		
		return Pattern.matches(regex, area+"-"+num);
	}
	
	/**
	 * 验证邮箱
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		
		 return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
	}
	
	/**
	 * 验证学号
	 * 格式：字母、数字
	 * @param str
	 * @return
	 */
	public static boolean isStuId(String str) {
		
		 return str.matches("^[a-zA-Z0-9]*$");
	}
	
	/**
	 * 验证纯数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		
		 return str.matches("^[0-9]*$");
	}
	
	/**
	 * 是否为正浮点数
	 * @param str
	 * @return
	 */
	public static boolean isDigitFloatPos(String str) {
		
		String reg="^\\d+(\\.\\d+)?$";
		
		return Pattern.matches(reg,str);

	}
	
	/**
	 * 判断下拉列表-年
	 * 格式：例 2014
	 * @param str
	 * @return
	 */
	public static boolean isYear(String str){
		
		return str.matches("^[0-9]{4}");
	}
	
	/**
	 * 判断下拉列表-月
	 * 格式：例 09
	 * @param str
	 * @return
	 */
	public static boolean isMonth(String str){
		
		return str.matches("^[0-9]{2}");
	}
	
	/**
	 * 判断下拉列表-日
	 * 格式：例 29
	 * @param str
	 * @return
	 */
	public static boolean isDay(String str){
		
		return str.matches("^[0-9]{2}");
	}
	
	/**
	 * 判断字符串只能是数字或者字母
	 * @param str
	 * @return
	 */
	public static boolean isNumOrLetter(String str){
		String reg = "^[0-9a-zA-Z]*";
		
		return Pattern.matches(reg, str);
	}
	
	/**
	 * 验证特殊字符
	 * 可以包含中文,字母，数字
	 * @param str
	 * @return
	 */
	public static boolean isStrFilter(String str){
		
		String regex = "^[\\w\u4e00-\u9fa5]+$";
		return Pattern.matches(regex, str);
	}
	
	/**
	 * 验证下拉列表值范围
	 * 格式：0到5位数字或者字母
	 * @param str
	 * @return
	 */
	public static boolean isSelect(String str){
		
		String regex = "^[a-zA-Z0-9]{0,5}$";
		return Pattern.matches(regex, str);
		
	}
	
	/**
	 * 验证地址下拉列表值范围(如：省、市、区)
	 * 格式：6位数字代码
	 * @param str
	 * @return
	 */
	public static boolean isSelectToAddress(String str){
		
		String regex = "^[0-9]{6}$";
		
		return Pattern.matches(regex, str);
	}
	

	/**
	 * 是否为非负整数
	 * @param Str
	 * @return
	 */
	public static boolean isDigitNotNeg(String str) {
		String reg="^\\d+$";
		
	    return Pattern.matches(reg,str);

	}
	
	/**
	 * 是否为整数
	 * @param Str
	 * @return
	 */
	public static boolean isDigit(String str) {
		String reg="^-?\\d+$";
		
	    return Pattern.matches(reg,str);

	}
	
	/**
	 * 是否为正整数
	 * @param Str
	 * @return
	 */
	public static boolean isDigitPos(String str) {
		String reg="^[0-9]*[1-9][0-9]*$";
		
	    return Pattern.matches(reg,str);

	}
	
	/**
	 * 真实姓名验证
	 * 格式：只能输入中文或者'.'，'·'
	 * @param str
	 * @return
	 */
	public static boolean isRealName(String str){
		String regex = "^[\u4e00-\u9fa5|.|·]+$";
		
		return Pattern.matches(regex, str);
	}
	
	
	/**
	 * 是否为全中文字符 
	 * @param str
	 */
	public static boolean isOnlyChinese(String str){
		String regex = "^[\u4e00-\u9fa5]+$";
		
		return Pattern.matches(regex, str);
	}
	
	/**
	 * 申请件流水号格式是否正确
	 * @param str
	 * @return
	 */
	public static boolean isAppNo(String str){
		
		String regex = "^[0-9]{14}$";
		
		return Pattern.matches(regex, str);
	}
	
	
	
	public static void main(String[] args) {
//		System.out.println(isDigit("15"));
//		System.out.println(isUsername("robiguo"));
//		System.out.println(isAppNo("31411111900291"));
//		System.out.println(isNumber("1"));
		System.out.println(isDigitFloatPos("123"));
	}
	
}
