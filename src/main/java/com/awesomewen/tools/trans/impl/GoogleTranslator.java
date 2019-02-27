package com.awesomewen.tools.trans.impl;

import com.awesomewen.tools.constants.LANG;
import com.awesomewen.tools.trans.AbstractTranslator;
import com.awesomewen.tools.util.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static com.awesomewen.tools.constants.JSFileList.GOOGLE_TOKEN_JS;

public final class GoogleTranslator extends AbstractTranslator {
    private static final String url = "https://translate.google.cn/translate_a/single";

    public GoogleTranslator(){
        super(url);
    }


    @Override
    public void setLangSupport() {
        langMap.put(LANG.ZH, "zh-CN");
        langMap.put(LANG.EN, "en");
        langMap.put(LANG.JP, "ja");
        langMap.put(LANG.KOR, "ko");
        langMap.put(LANG.FRA, "fr");
        langMap.put(LANG.RU, "ru");
        langMap.put(LANG.DE, "de");
    }

    @Override
    public void setFormData(LANG from, LANG to, String text) {
        formData.put("client", "t");
        formData.put("sl", langMap.get(from));
        formData.put("tl", langMap.get(to));
        formData.put("hl", "zh-CN");
        formData.put("dt", "at");
        formData.put("dt", "bd");
        formData.put("dt", "ex");
        formData.put("dt", "ld");
        formData.put("dt", "md");
        formData.put("dt", "qca");
        formData.put("dt", "rw");
        formData.put("dt", "rm");
        formData.put("dt", "ss");
        formData.put("dt", "t");
        formData.put("ie", "UTF-8");
        formData.put("oe", "UTF-8");
        formData.put("source", "btn");
        formData.put("ssel", "0");
        formData.put("tsel", "0");
        formData.put("kc", "0");
        formData.put("tk", TokenUtils.generateTokenByJs(text,GOOGLE_TOKEN_JS));
        formData.put("q", text);
    }

    @Override
    public String query() throws Exception {
        URIBuilder uri = new URIBuilder(url);
        for (String key : formData.keySet()) {
            String value = formData.get(key);
            uri.addParameter(key, value);
        }
        HttpUriRequest request = new HttpGet(uri.toString());
        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity, "utf-8");

        EntityUtils.consume(entity);
        response.getEntity().getContent().close();
        response.close();

        return result;
    }

    @Override
    public String parses(String text) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(text).get(0).get(0).get(0).toString();
    }
}
