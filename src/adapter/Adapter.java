package adapter;

//适配器角色
public class Adapter extends Role implements Target {
    @Override
    public void request() {
        super.method();
    }
}
