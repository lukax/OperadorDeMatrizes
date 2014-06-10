package app.domain;

import app.mat.base.Expressao;

public class Variable {

    private String name;
    private Expressao<?> expressao;

    public Variable() {

    }

    public Variable(String name, Expressao<?> expressao) {
        this.name = name;
        this.expressao = expressao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Expressao<?> getExpressao() {
        return expressao;
    }

    public void setExpressao(Expressao<?> expressao) {
        this.expressao = expressao;
    }
}
