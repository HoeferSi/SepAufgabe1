package simon.hoefer.Aufgabe1.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PreparedStatementParameters {
    private List<Object> values;
    private HashMap<Integer, String> strings;
    private HashMap<Integer, Integer> ints;
    private HashMap<Integer, Date> date;
    private int parameterCounter;
    // Add more hashmaps for types as needed

    public PreparedStatementParameters() {
        values = new ArrayList<>();
        strings = new HashMap<>();
        ints = new HashMap<>();
        date = new HashMap<>();
        parameterCounter = 0;
    }

    public <T> void add(T obj) {
        values.add(obj);
        //ints.put(parameterCounter++, nextInt);
    }

    public PreparedStatement setParameter(PreparedStatement preparedStatement) {
        try {
            for (int index = 0; index < values.size(); index++) {
                Object nextValue = values.get(index);
                switch (nextValue) {
                    case Integer i:
                        preparedStatement.setInt(index, i.intValue()); break;
                    case String s:
                        preparedStatement.setString(index, s.toString()); break;
                    default: throw new RuntimeException("Object cant be cast to integer, string or date,"+ nextValue.toString()); // TODO:log
                }
            }
        } catch (SQLException e) {
            // TODO: log
        }
        return preparedStatement;
    }

}
