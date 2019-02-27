package com.awesomewen.tools.util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

public class TokenUtils {

    private static final String ENGINE_NAME = "js";
    private static final String FUNCTION_NAME = "token";

    public static String generateTokenByJs(String text, String jsFile) {
        String tk = "";
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(ENGINE_NAME);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(TokenUtils.class.getClassLoader().getResourceAsStream(jsFile)));
            engine.eval(reader);

            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                tk = String.valueOf(invoke.invokeFunction(FUNCTION_NAME, text));
            }
        } catch (Exception e) {
            // todo:
            e.printStackTrace();
        }
        return tk;
    }

    public static String generateTokenByJsWithGtk(String text, String jsFile, String gtk) {
        String result = "";
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(ENGINE_NAME);
        try {
            FileReader reader = new FileReader(jsFile);
            engine.eval(reader);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                result = String.valueOf(invoke.invokeFunction(FUNCTION_NAME, text, gtk));
            }
        } catch (ScriptException | NoSuchMethodException | FileNotFoundException e) {
            // todo:
            e.printStackTrace();
        }
        return result;
    }
}
