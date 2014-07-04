package akkaTest.barbershop;

import akka.actor.UntypedActor;
import akka.japi.Procedure;


/**
 * Created by dmytro_veres on 30.06.14.
 */
public class Hairdresser extends UntypedActor{

    public static final int HAIRCUT_DURATION = 500;


    Procedure<Object> sleeping = new Procedure<Object>() {

        @Override
        public void apply(Object param) throws Exception {
            if (param instanceof Messages.WakeUp) {
                System.out.println("Hairdresser has woken up.");
                getSender().tell(new Messages.NeedClients(), getSelf());
                getContext().unbecome();
            }
        }
    };

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Messages.NextClient) {
            System.out.println("Hairdresser asks for next client.");
            Messages.NextClient castedMessage = (Messages.NextClient) message;
            if (castedMessage.getClient() == null) {
                System.out.println("No clients. Hairdresser gonna sleep.");
                getContext().become(sleeping, false);
            } else {
                System.out.println("Hairdresser cuts hair...");
                Thread.sleep(HAIRCUT_DURATION);
                castedMessage.getClient().tell(new Messages.HaircutDone(), getSelf());
                getSender().tell(new Messages.NeedClients(), getSelf());
            }
        }

    }
}
