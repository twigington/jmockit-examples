package examples.threadlocal;

/**
 *
 */
public class MyService {
    public String getMyThreadLocalModule() {
        return MyThreadLocal.getThreadLocalCtx().getModule();
    }

    public void setMyThreadLocalModule(String newModule) {
        MyThreadLocal.getThreadLocalCtx().setModule(newModule);
    }

    public String getOtherServiceName() {
        return MyThreadLocal.getThreadLocalCtx().getOtherService().getName();
    }
}
