package com.uillirt.projects.frameworks.luaj;

import com.uillirt.projects.frameworks.luaj.function.CustomFunctionsPackage;
import org.luaj.vm2.core.Globals;
import org.luaj.vm2.core.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kshekhovtsova on 11.05.2015.
 */
public class LuajMain {

    private static Globals luaGlobals = JsePlatform.standardGlobals();

    public static void main(String[] args) {
        Map<String, Object> source = new HashMap<String, Object>() {{
            put("amount", 115L);
            put("date", new Date());
        }};
        load(source);
        System.out.println("amount: " + evaluateLong("amount"));
        System.out.println("date: " + evaluateDate("date"));
    }

    private static void load(Map<String, Object> src) {
        luaGlobals.load(new CustomFunctionsPackage(src));
    }

    private static String evaluateLong(String parameterName) {
        final String script = "return string.format(\"%.2f\",customSrc('" + parameterName + "')/7)";
        LuaValue formatScript = luaGlobals.load(script);
        return formatScript.invoke().toString();
    }

    private static String evaluateDate(String parameterName) {
        final String script = "return os.date('%Y-%m-%d %H:%M:%S', customSrc('" + parameterName + "'))";
        LuaValue formatScript = luaGlobals.load(script);
        return formatScript.invoke().toString();
    }
}


