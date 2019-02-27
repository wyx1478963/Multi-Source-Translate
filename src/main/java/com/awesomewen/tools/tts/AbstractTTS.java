package com.awesomewen.tools.tts;

import com.awesomewen.tools.tts.impl.*;
import com.awesomewen.tools.http.AbstractHttpAttribute;
import com.awesomewen.tools.http.HttpParams;
import com.awesomewen.tools.constants.LANG;
import com.awesomewen.tools.util.TranslateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.*;

/**
 * AbstractTTS is an abstract base class for all TTS,
 * including several (abstract) functions. By setting
 * parameters, the request is sent to the target server,
 * and the return is converted to the result of the speech.
 *
 * @see BaiduTTS
 * @see GoogleTTS
 * @see SogouTTS
 * @see TencentTTS
 * @see YoudaoTTS
 */
public abstract class AbstractTTS extends AbstractHttpAttribute implements HttpParams {

    public AbstractTTS(String url) {
        super(url);
        setLangSupport();
    }

    @Override
    public String run(LANG from, LANG to, String text) {
        return null;
    }

    @Override
    public String run(LANG source, String text) {
        String saveFile = null;
        setFormData(source, text);
        try {
            saveFile = query();
            System.out.println(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        close();
        return saveFile;
    }

    /**
     * Initialize the supported language mapping.
     */
    public abstract void setLangSupport();

    @Override
    public String query() throws IOException {
        String uri = TranslateUtils.getUrlWithQueryString(url, formData);
        HttpGet request = new HttpGet(uri);

        HttpResponse response = httpClient.execute(request);
        InputStream is = response.getEntity().getContent();

        // 将 TTS 结果保存为 mp3 音频文件，以待转换文本的 md5 码作为部分文件名
        StringBuilder saveFile = new StringBuilder();
        saveFile.append(TranslateUtils.md5(uri))
                .append(".mp3");

//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
//        String content;
//        while ((content = bufferedReader.readLine()) !=null) {
//            System.out.println(content);
//        }
//
        File file = new File(saveFile.toString());
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[4096];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        is.close();
//        bufferedReader.close();
        return saveFile.toString();
    }

    @Override
    public abstract void setFormData(LANG source, String text);

    @Override
    public void setFormData(LANG from, LANG to, String text) {
    }
}
