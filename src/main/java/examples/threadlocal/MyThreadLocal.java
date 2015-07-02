package examples.threadlocal;

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
