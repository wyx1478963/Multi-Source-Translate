package com.awesomewen.tools.tts.impl;

import com.awesomewen.tools.constants.LANG;
import com.awesomewen.tools.tts.AbstractTTS;

public final class TencentTTS extends AbstractTTS {
    private final static String url = "http://audiodetect.browser.qq.com:8080/tts";

    public TencentTTS() {
        super(url);
        setLangSupport();
    }

    @Override
    public void setLangSupport() {
        langMap.put(LANG.ZH, "0");
        langMap.put(LANG.EN, "1");
        langMap.put(LANG.JP, "2");
        langMap.put(LANG.KOR, "3");
    }

    @Override
    public void setFormData(LANG source, String text) {
        formData.put("platform", "PC_Website");
        formData.put("language", langMap.get(source));
        formData.put("text", text);
        formData.put("guid", "d4480e20-1644-4a47-a98d-787cfa244fd2");
    }
}