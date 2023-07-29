package net.bxx2004.pandalib.convert;

public class ConvertOption {
    public enum Model{
        PRECISE,
        VAGUE
    }
    private Model model = Model.VAGUE;

    public ConvertOption setModel(Model model) {
        this.model = model;
        return this;
    }

    public Model getModel() {
        return model;
    }
}
