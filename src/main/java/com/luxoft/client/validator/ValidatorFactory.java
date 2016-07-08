package com.luxoft.client.validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;
import com.luxoft.shared.EventModel;

import javax.validation.Validator;
import javax.validation.groups.Default;

public class ValidatorFactory extends AbstractGwtValidatorFactory {

    @GwtValidation(value = EventModel.class, groups = Default.class)
    public interface EventValidator extends Validator{
    }

    @Override
    public AbstractGwtValidator createValidator() {
        return GWT.create(EventValidator.class);
    }
}
