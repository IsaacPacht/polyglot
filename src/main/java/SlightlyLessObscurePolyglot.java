import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

/**
 * Calculate 1+1+1+1+1+1+1+1+1+1, with each addition operation performed in a different language
 */
public class SlightlyLessObscurePolyglot {
  static { // These properties should be specified on the command line, but are embedded here for convenience
    System.setProperty("python.console.encoding", "UTF-8");
    System.setProperty("python.import.site", "false");
  }
  
  public static void main(String[] args) throws ScriptException {
    Object total = 1;
    total = eval("jython", "a+1", total);
    total = eval("Oracle Nashorn", "a+1", total);
    total = eval("Groovy Scripting Engine", "a+1", total);
    total = eval("juel", "${a+1}", total);
    total = eval("BeanShell Engine", "a+1", total);
    total = eval("Clojure Scripting Engine", "(+ a 1)", total);
    total = eval("JEXL Engine", "a+1", total);
    total = eval("Luaj", "return a+1", total);
    total = eval("JSR 223 JRuby Engine", "a+1", total);
    System.out.println(total);
  }

  private static Object eval(String language, String script, Object parameter) throws ScriptException {
    HashMap<String, Object> map = new HashMap<>();
    map.put("a", parameter);
    SimpleBindings bindings = new SimpleBindings(map);
    ScriptEngine engine = findEngine(language);
    return engine.eval(script, bindings);
  }
  
  private static ScriptEngine findEngine(String language) {
    return new ScriptEngineManager().getEngineFactories().stream()
            .filter(f -> f.getEngineName().equals(language))
            .findAny()
            .get()
            .getScriptEngine();
  }

}
