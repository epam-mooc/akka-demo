package akkaTest.barbershop;

import akka.actor.ActorRef;

/**
 * Created by dmytro_veres on 30.06.14.
 */
public class Messages {
    private Messages() {

    }

    //Customers
    public static class Hello{}


    //Hairdresser
    public static class NeedClients{}
    public static class HaircutDone{}


    //Receptionist
    public static class BarbershopFull{}
    public static class WakeUp{}
    public static class NextClient{
        private ActorRef client;

        public NextClient(ActorRef client) {
            this.client = client;
        }

        public ActorRef getClient() {
            return client;
        }
    }
}
