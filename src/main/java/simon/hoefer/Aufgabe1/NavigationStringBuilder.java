package simon.hoefer.Aufgabe1;

import java.util.HashMap;

public class NavigationStringBuilder {

    private String baseString;

    private HashMap<String,String> params;

    private NavigationStringBuilder(String baseString){
        this.baseString = baseString;
        this.params = new HashMap<>();
    }

    public static NavigationStringBuilder getLogin() {
        return new NavigationStringBuilder("login.xhtml");
    }

    public static NavigationStringBuilder getProfil() {
        return new NavigationStringBuilder("profil.xhtml");
    }

    public NavigationStringBuilder addParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    public NavigationStringBuilder withRedirect() {
        params.put("faces-redirect","true");
        return this;
    }

    public String build() {
        String paramString = params
                .entrySet()
                .stream()
                .reduce("?", // TODO: find nicer way to reduce
                        (param, value) -> param + "=" + value + "&",
                        (soFar, next) -> soFar + next
                );
        return baseString
                + paramString.substring(0,paramString.length()-1); // remove last '&'
    }
}
