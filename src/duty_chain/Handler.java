package duty_chain;


public abstract class Handler {
    private Handler nextHandler;

    //处理者对请求作出处理
    public final Response handleMessage(Request request) {
        Response response = null;
        //判断处理级别
        if (this.getHandlerLevel().equals(request.getRequestLevel())) {
            response = this.echo(request);
        } else {
            //下一个处理者进行操作
            if (this.nextHandler != null) {
                response = this.nextHandler.handleMessage(request);
            } else {
                //没有处理者了 业务自行处理
            }
        }
        return response;
    }

    //设置下一个处理者是谁
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    //处理者有自己的处理级别
    protected abstract Level getHandlerLevel();

    //每个处理者都必须实现处理任务
    protected abstract Response echo(Request request);
}
