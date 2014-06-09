package app.domain;


public class Token {

    private TokenType type;
    private String value;

    public void setType(TokenType type) {
        this.type = type;
    }

    public TokenType getType() {
        return this.type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
