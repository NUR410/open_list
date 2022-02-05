package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Restaurant;

public class RestaurantValidator {
    public static List<String> validate(Restaurant r) {
        List<String> errors = new ArrayList<String>();

        String name_error = _validateName(r.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        return errors;
    }

    private static String _validateName(String name) {
        if(name == null || name.equals("")) {
            return "店舗名を入力してください。";
            }

        return "";
    }

}