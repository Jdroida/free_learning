public class AsyncCallback {
    interface IClient {
        String receiveAnswer(String answer);
    }

    static class Client implements IClient, Runnable {
        Server server;

        public Client(Server server) {
            this.server = server;
        }

        void ask(String question) {
            server.answer(this, question);
        }

        @Override
        public String receiveAnswer(String answer) {
            System.out.println(answer);
            return answer;
        }

        @Override
        public void run() {

        }
    }

    static class Server {
        String answer(IClient client, String question) {
            return client.receiveAnswer("question is " + question + "\tand I don't know it");
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        Client client = new Client(server);
        client.ask("How are you");
    }
}
