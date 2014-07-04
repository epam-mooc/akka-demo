package akkaTest.simpleApp;
import akka.actor.*;

/**
 * Hello world!
 *
 */
public class App {
    public static class SimpleActor extends UntypedActor {
        private int count = 0;

        @Override
        public void onReceive(Object o) throws Exception {
            if (o instanceof Integer) {
                count += (Integer) o;
                System.out.println("Count: " + count);
                getSender().tell(o, getSelf());
            }
        }
    }
    public static void main( String[] args ) {
        final ActorSystem system = ActorSystem.create("SimpleActorSystem");
        final ActorRef actor = system.actorOf(Props.create(SimpleActor.class), "ActorName");

        for (int i = 0; i < 10; i++) {
            System.out.println("Sending: " + i);
            actor.tell(new Integer(i), ActorRef.noSender());
        }

        system.shutdown();
        System.out.println( "Hello World!" );
    }
}
