package Player;

import java.util.LinkedList;
import java.util.List;

/**
 * @author kory
 */
public class GUICommunicator extends Communicator{
    
    private String message = null;
    final List<String> holder = new LinkedList<>();
    
    @Override
    public String getMessage() {
        
        
        
        return message;
    }

    @Override
    public void sendMessage(String str) {
        this.message = str;
    }

    @Override
    public void sendMessageln(String str) {
    }

}
