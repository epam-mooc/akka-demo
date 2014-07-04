package akkaTest.barbershop;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by dmytro_veres on 01.07.14.
 */
public class BarbershopRunner {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create();
        ActorRef hairdresser = system.actorOf(Props.create(Hairdresser.class));
        ActorRef receptionist = system.actorOf(Props.create(Receptionist.class, 4, hairdresser));
        receptionist.tell(new Messages.NeedClients(), hairdresser);
        ActorRef visitorOne = system.actorOf(Props.create(Visitor.class, "Johny", receptionist));
        ActorRef visitorTwo = system.actorOf(Props.create(Visitor.class, "Billy", receptionist));
        ActorRef visitorThree = system.actorOf(Props.create(Visitor.class, "Max", receptionist));
        ActorRef visitorFour = system.actorOf(Props.create(Visitor.class, "Robert", receptionist));
        ActorRef visitorFive = system.actorOf(Props.create(Visitor.class, "Fred", receptionist));
        ActorRef visitorSix = system.actorOf(Props.create(Visitor.class, "Chris", receptionist));
    }
}
