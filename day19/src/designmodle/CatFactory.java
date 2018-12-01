package designmodle;

public class CatFactory implements BigFactory{
    @Override
    public Animal createAnimal() {
        return new Cat();
    }
}
