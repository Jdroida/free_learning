package duty_chain;

public class ConcreteHandler1 extends Handler {
    @Override
    protected Level getHandlerLevel() {//定义自己处理级别
        return null;
    }

    @Override
    protected Response echo(Request request) {//完成自己处理逻辑
        return null;
    }
}
