package akkaTest.barbershop;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * Created by dmytro_veres on 30.06.14.
 */
public class Visitor extends UntypedActor {

    private String visitorName;
    private ActorRef receptionist;

    public Visitor(String visitorName, ActorRef receptionist) {
        this.visitorName = visitorName;
        this.receptionist = receptionist;
        receptionist.tell(new Messages.Hello(), getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Messages.BarbershopFull) {
            System.out.println("Visitor " + visitorName + " leaves. Barbershop was full.");
        } else if (message instanceof Messages.HaircutDone){
            System.out.println("Visitor " + visitorName + " has his hair cut and leaves the barbershop.");
        }
    }
}
