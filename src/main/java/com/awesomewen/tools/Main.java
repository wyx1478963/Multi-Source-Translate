package com.awesomewen.tools;

import com.awesomewen.tools.constants.LANG;
import com.awesomewen.tools.querier.Querier;
import com.awesomewen.tools.trans.AbstractTranslator;
import com.awesomewen.tools.trans.impl.GoogleTranslator;

import java.util.List;

public class Main {

    public static void main(String[] args) {
//        Querier<AbstractTTS> querierTTS = new Querier<>();                          // 获取查询器
//
//        querierTTS.setParams(LANG.EN, "我的好朋友");   // 设置参数
//
//        querierTTS.attach(new GoogleTTS());                                          // 向查询器中添加 Google 翻译器
//
//        List<String> result = querierTTS.execute();                                 // 执行查询并接收查询结果
//
//        for (String str : result) {
//            System.out.println(str);
//        }

        Querier<AbstractTranslator> querierTrans = new Querier<>();                   // 获取查询器
        long now = System.currentTimeMillis();
//        querierTrans.setParams(LANG.ZH, LANG.EN, "哈哈");    // 设置参数
        querierTrans.setParams(LANG.EN, LANG.ZH, "maven");    // 设置参数

        querierTrans.attach(new GoogleTranslator());                                  // 向查询器中添加 Google 翻译器

        List<String> result = querierTrans.execute();                                 // 执行查询并接收查询结果

        for (String str : result) {
            System.out.println(str);
        }

        System.out.println("time escape : " + String.valueOf(System.currentTimeMillis() - now));
    }
}
