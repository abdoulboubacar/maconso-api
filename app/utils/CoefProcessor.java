package utils;

import models.State;

import javax.inject.Singleton;

/**
 * Created by abdoulbou on 11/05/17.
 */
@Singleton
public class CoefProcessor {

    public void setCoef(State state) {
        if ("GAS".equals(state.getDeal().getResource().getKey())) {
            Integer postalCode = state.getDeal().getPostalCode();
            state.setCoef(10l); //recup du vrai coef de conversion depuis les api de GRDF
        } else {
            state.setCoef(1l);
        }
    }
}
