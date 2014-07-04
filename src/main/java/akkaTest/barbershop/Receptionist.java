package akkaTest.barbershop;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.japi.Procedure;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by dmytro_veres on 30.06.14.
 */
public class Receptionist extends UntypedActor{

    private Queue<ActorRef> visitors;
    private ActorRef hairdresser;
    private int maxVisitors;

    public Receptionist(int maxVisitors, ActorRef hairdresser) {
        visitors = new LinkedList<ActorRef>();
        this.maxVisitors = maxVisitors;
        this.hairdresser = hairdresser;
    }

    Procedure<Object> alarmMode = new Procedure<Object>() {
        @Override
        public void apply(Object o) throws Exception {
            if (o instanceof Messages.Hello) {
                visitors.add(getSender());
                hairdresser.tell(new Messages.WakeUp(), getSelf());
                getContext().unbecome();
            }
        }
    };

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Messages.Hello) {
            if (visitors.size() == maxVisitors) {
                getSender().tell(new Messages.BarbershopFull(), getSelf());
            } else {
                System.out.println("Visitor has been added to queue.");
                visitors.add(getSender());
            }
        } else if (message instanceof Messages.NeedClients) {
            if (visitors.size() == 0) {
                getSender().tell(new Messages.NextClient(null), getSelf());
                getContext().become(alarmMode, false);
            } else {
                ActorRef nextVisitor = visitors.remove();
                getSender().tell(new Messages.NextClient(nextVisitor), getSelf());
            }
        }
    }
}
