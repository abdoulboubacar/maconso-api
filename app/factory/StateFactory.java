package factory;

import form.StateForm;
import models.State;

/**
 * Created by abdoulbou on 10/01/17.
 */
public class StateFactory {

    public static State createState(StateForm form) {
        State state = new State();
        state.setValue(form.getValue());

        return state;
    }
}
