package form;

import play.data.format.Formats;
import play.data.validation.Constraints;

import java.util.Date;

/**
 * Created by abdoulbou on 10/01/17.
 */
public class StateForm {
    @Constraints.Required
    private Long value;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
