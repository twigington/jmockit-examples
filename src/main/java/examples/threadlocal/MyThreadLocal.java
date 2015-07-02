package examples.threadlocal;

/**
 * I don't think this is a proper pattern for ThreadLocal use. I am not sure of the value of sub-classing it
 * and it causes problems with mocking. (It wrecks havoc on jmockit if you aren't careful).
 * But I've seen it done and written tests to accommodate it.
 */
public class MyThreadLocal extends ThreadLocal {
    private static MyThreadLocal thLocalCtx = new MyThreadLocal();

    private String module = "Default Module";
    private OtherService otherService;

    public static MyThreadLocal getThreadLocalCtx() {
        MyThreadLocal tlc = (MyThreadLocal) thLocalCtx.get();
        if (tlc == null) {
            tlc = new MyThreadLocal();
            thLocalCtx.set(tlc);
        }
        return tlc;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void init(OtherService anOtherService) {
        this.otherService = anOtherService;
    }

    public OtherService getOtherService() {
        return otherService;
    }
}
