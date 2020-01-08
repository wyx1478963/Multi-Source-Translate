package com.awesomewen.tools;

import com.awesomewen.tools.constants.LANG;
import com.awesomewen.tools.querier.Querier;
import com.awesomewen.tools.trans.AbstractTranslator;
import com.awesomewen.tools.trans.impl.BaiduTranslator;
import com.awesomewen.tools.trans.impl.GoogleTranslator;
import com.awesomewen.tools.trans.impl.TencentTranslator;
import com.awesomewen.tools.trans.impl.YoudaoTranslator;
import com.awesomewen.tools.tts.AbstractTTS;
import com.awesomewen.tools.tts.impl.BaiduTTS;
import com.awesomewen.tools.tts.impl.SogouTTS;
import com.awesomewen.tools.tts.impl.TencentTTS;

import java.util.List;

public class Main {

    public static void main(String[] args) {


        Querier<AbstractTranslator> querierTrans = new Querier<>();                   // 获取查询器
        long now = System.currentTimeMillis();
//        querierTrans.setParams(LANG.ZH, LANG.EN, "哈哈");    // 设置参数
        querierTrans.setParams(LANG.EN, LANG.ZH, "hello");    // 设置参数

        querierTrans.attach(new BaiduTranslator());                                  // 向查询器中添加 Google 翻译器

        List<String> result = querierTrans.execute();                                 // 执行查询并接收查询结果

        for (String str : result) {
            System.out.println(str);
            Querier<AbstractTTS> querierTTS = new Querier<>();                          // 获取查询器

            querierTTS.setParams(LANG.CHT, str);   // 设置参数

            querierTTS.attach(new SogouTTS());                                          // 向查询器中添加 Google 翻译器

            List<String> ttlResult = querierTTS.execute();                                 // 执行查询并接收查询结果

            for (String filePaht : ttlResult) {
                System.out.println(filePaht);
            }
        }

        System.out.println("time escape : " + String.valueOf(System.currentTimeMillis() - now));
    }
}
