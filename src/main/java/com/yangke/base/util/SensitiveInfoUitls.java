package com.yangke.base.util;

import com.yangke.base.utils.callback.AesUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 处理敏感字段
 * @Author: ke.yang1
 * @Date: 2021/11/4 5:29 下午
 * @Version 1.0
 */
public class SensitiveInfoUitls {


    /**
     * 日志脱敏开关
     */
    private static final String CONVERTER_CAN_RUN = "true";
    /**
     * 日志脱敏关键字
     */
    private static final String SENSITIVE_DATA_KEYS = "password,oldPassword,newPassword,phone,mobileTel,idNo,bankAccount,address,bankMobileNo,hold_id_no,beneficiary_id_no,mobile_tel,bank_open_id_no,bankOpeIdNo";
    private static final List<String> SENSITIVE_KEYS = new ArrayList<>(Arrays.asList("password", "oldPassword", "newPassword", "phone", "mobileTel", "idNo","mobile_tel","bankAccount", "address", "bankMobileNo", "hold_id_no", "beneficiary_id_no", "bank_open_id_no", "bankOpeIdNo"));

    /**
     * 返回参数需要掩码的字段名
     */
    public static final List<String> RESPONSE_PHONE = new ArrayList<>(Arrays.asList("phone", "mobileTel", "bankMobileNo"));
    public static final List<String> RESPONSE_ID_NO = new ArrayList<>(Arrays.asList("idNo", "bankOpeIdNo"));
    public static final List<String> RESPONSE_BANK_ACCOUNT = new ArrayList<>(Arrays.asList("bankAccount"));

    private static final List<String> FILTER_SENSITIVE_DATA_KEYS = new ArrayList<>(Arrays.asList("password", "oldPassword", "newPassword"));

    private static final Pattern PATTERN = Pattern.compile("[0-9a-zA-Z]");

    // 手机号正则匹配
    private static final String PHONE_REGEX = "1[3|4|5|6|7|8|9][0-9]\\d{8}";
    // 身份证号正则匹配
    private static final String ID_CARD_REGEX = "([1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx])|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3})";

    private static final String KEY = "axedfqLGpu";


    /**
     * 处理日志字符串，返回脱敏后的字符串
     *
     * @return
     * @param: msg
     */
    public static String invokeMsg(final String oriMsg) {
        String tempMsg = oriMsg;
        if (Boolean.TRUE.toString().equals(CONVERTER_CAN_RUN)) {
            // 处理字符串
            if (SENSITIVE_DATA_KEYS != null && SENSITIVE_DATA_KEYS.length() > 0) {
                String[] keysArray = SENSITIVE_DATA_KEYS.split(",");
                for (String key : keysArray) {
                    int index = -1;
                    do {
                        index = tempMsg.indexOf(key, index + 1);
                        if (index != -1) {
                            // 判断key是否为单词字符
                            if (isWordChar(tempMsg, key, index)) {
                                continue;
                            }
                            // 寻找值的开始位置
                            int valueStart = getValueStartIndex(tempMsg, index + key.length());

                            // 查找值的结束位置（逗号，分号）........................
                            int valueEnd = getValueEndEIndex(tempMsg, valueStart);

                            // 对获取的值进行脱敏
                            String subStr = tempMsg.substring(valueStart, valueEnd);
                            subStr = deSensitization(subStr, key);
                            ///
                            tempMsg = tempMsg.substring(0, valueStart) + subStr + tempMsg.substring(valueEnd);
                        }
                    } while (index != -1);
                }
            }
        }
        return tempMsg;
    }

    /**
     * 判断从字符串msg获取的key值是否为单词
     * index为key在msg中的索引值
     *
     * @return
     */
    private static boolean isWordChar(String msg, String key, int index) {
        // 必须确定key是一个单词
        // 判断key前面一个字符
        if (index != 0) {
            char preCh = msg.charAt(index - 1);
            Matcher match = PATTERN.matcher(preCh + "");
            if (match.matches()) {
                return true;
            }
        }
        // 判断key后面一个字符
        char nextCh = msg.charAt(index + key.length());
        Matcher match = PATTERN.matcher(nextCh + "");
        if (match.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 获取value值的开始位置
     *
     * @param msg        要查找的字符串
     * @param valueStart 查找的开始位置
     * @return
     */
    private static int getValueStartIndex(String msg, int valueStart) {
        // 寻找值的开始位置.................................
        do {
            char ch = msg.charAt(valueStart);
            // key与 value的分隔符
            if (ch == ':' || ch == '=') {
                valueStart++;
                ch = msg.charAt(valueStart);
                if (ch == '"') {
                    valueStart++;
                }
                break;
            } else {
                valueStart++;
            }
        } while (true);
        return valueStart;
    }

    /**
     * 获取value值的结束位置
     *
     * @return
     */
    private static int getValueEndEIndex(String msg, int valueEnd) {
        do {
            if (valueEnd == msg.length()) {
                break;
            }
            char ch = msg.charAt(valueEnd);
            // 引号时，判断下一个值是结束，分号还是逗号决定是否为值的结束
            if (ch == '"') {
                if (valueEnd + 1 == msg.length()) {
                    break;
                }
                char nextCh = msg.charAt(valueEnd + 1);
                if (nextCh == ';' || nextCh == ',') {
                    // 去掉前面的 \  处理这种形式的数据
                    while (valueEnd > 0) {
                        char preCh = msg.charAt(valueEnd - 1);
                        if (preCh != '\\') {
                            break;
                        }
                        valueEnd--;
                    }
                    break;
                } else {
                    valueEnd++;
                }
            } else if (ch == ';' || ch == ',' || ch == '}') {
                break;
            } else {
                valueEnd++;
            }

        } while (true);
        return valueEnd;
    }

    /**
     * 调用脱敏工具类进行字段日志处理
     *
     * @param subMsg
     * @param key
     * @return
     */
    private static String deSensitization(String subMsg, String key) {
        if (FILTER_SENSITIVE_DATA_KEYS.contains(key)) {
            return "******";
        }
        if (SENSITIVE_KEYS.contains(key)) {
            return AesUtil.encrypt(subMsg, AesEnum.AES_ACCOUNT);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 对敏感信息脱敏
     *
     * @param content
     * @return
     * @author ljh
     * @date 2019年12月17日
     */
    public static String filterSensitive(String content) {
        try {
            if (StringUtils.isBlank(content)) {
                return content;
            }
            return filterMobile(content);
        } catch (Exception e) {
            return content;
        }
    }

    /**
     * [身份证号] 指定展示几位，其他隐藏 。<例子：1101**********5762>
     *
     * @param num
     * @return
     * @author ljh
     * @date 2019年12月18日
     */
    private static String filterIdCard(String num) {
        Pattern pattern = Pattern.compile(ID_CARD_REGEX);
        Matcher matcher = pattern.matcher(num);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, baseSensitive(matcher.group(), 4, 4));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     *
     * @param num
     * @return
     * @author ljh
     * @date 2019年12月18日
     */
    private static String filterMobile(String num) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(num);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, baseSensitive(matcher.group(), 3, 4));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 基础脱敏处理 指定起止展示长度 剩余用"KEY"中字符替换
     *
     * @param str         待脱敏的字符串
     * @param startLength 开始展示长度
     * @param endLength   末尾展示长度
     * @return 脱敏后的字符串
     */
    private static String baseSensitive(String str, int startLength, int endLength) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        String replacement = str.substring(startLength, str.length() - endLength);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < replacement.length(); i++) {
            char ch;
            if (replacement.charAt(i) >= '0' && replacement.charAt(i) <= '9') {
                ch = KEY.charAt((int) (replacement.charAt(i) - '0'));
            } else {
                ch = replacement.charAt(i);
            }
            sb.append(ch);
        }
        return StringUtils.left(str, startLength).concat(StringUtils.leftPad(StringUtils.right(str, endLength), str.length() - startLength, sb.toString()));
    }

    /**
     * 按"KEY"中字符解密
     *
     * @param str
     * @param startLength
     * @param endLength
     * @return
     * @author ljh
     * @date 2019年12月18日
     */
    private static String decrypt(String str, int startLength, int endLength) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        String replacement = str.substring(startLength, str.length() - endLength);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < replacement.length(); i++) {
            int index = KEY.indexOf(replacement.charAt(i));
            if (index != -1) {
                sb.append(index);
            } else {
                sb.append(replacement.charAt(i));
            }
        }
        return StringUtils.left(str, startLength).concat(StringUtils.leftPad(StringUtils.right(str, endLength), str.length() - startLength, sb.toString()));
    }
}
