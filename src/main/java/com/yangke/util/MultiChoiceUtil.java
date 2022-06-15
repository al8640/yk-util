package com.yangke.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ke.yang1
 * @description
 * @date 2022/5/6 10:47 下午
 */
public class MultiChoiceUtil {
    /**
     * @param args
     */
    public static void main(String[] args) {
        List<String> sure_answer = new ArrayList<>();
        sure_answer.add("A");
        sure_answer.add("B");
        sure_answer.add("C");
        List<String> user_answer = new ArrayList<>();
        user_answer.add("A");
        System.out.println(isAnswerRight(sure_answer,user_answer));
    }

    public static int isAnswerRight(List<String> trueAnswers, List<String> userAnswer){
//        trueAnswers.stream().map(s -> {s.})
        if(trueAnswers.size() == 1){
            if(userAnswer.size() > 1){
                return 2;
            }else if(!trueAnswers.get(0).equals(userAnswer.get(0))){
                return 2;
            }
            return 0;
        }
        if (userAnswer.size() <= trueAnswers.size()) {
            Collections.sort(trueAnswers);
            Collections.sort(userAnswer);
            String[] s1 = trueAnswers.toArray(new String[0]);
            String[] s2 = userAnswer.toArray(new String[0]);
            //jdk⾃带⽅法：判断两个数组是否完成相等（⼀⼀对应）
            if (Arrays.equals(s1, s2)) {
                return 0;
            } else if (MultiChoiceUtil.containArray(s1, s2)) {
                return 1;
            }else{
                return 2;
            }
        }
        return 2;
    }

    /**
     * 判断sure_answer数组是否全部包含user_answer数组并且相同的元素不少于两个
     * @param sure_answer
     * @param user_answer
     * @return
     */
    public static boolean containArray(String[] sure_answer,
                                       String[] user_answer) {
        //true表⽰多选题部分对，false表⽰不对
        boolean bool = true;
        // ⽤户输⼊的每⼀个答案都遍历正确答案数组，如果count等于正确答案数组的个数，则这个答案是错的。

        // 对题的个数：防⽌出现考⽣输⼊⼀个答案的情况
        int ques_count = 0;
        for (int i = 0; i < user_answer.length; i++) {
            int count = 0;
            for (int j = 0; j < sure_answer.length; j++) {
                if (!user_answer[i].equals(sure_answer[j])) {
                    count++;
                }
            }
            //看count声明时的注释
            if (count == sure_answer.length) {
                bool = false;
                break;
            } else {
            //表⽰这个打算对了
                ques_count++;
            }
            // 出现⼀个错误选项直接返回false
            if (!bool) {
                return bool;
            }
        }
        //相对元素的个数在两个以上才算对
//        if (ques_count <= 1) {
//            bool = false;
//        }
        return bool;
    }
}
