package examples.staticfactory;

public class Actor {
    public String doAction(int aNumber) {
        MyService service = MyService.construct("doService");
        return service.doIt(1);
    }
}
