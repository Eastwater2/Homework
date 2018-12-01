package designmodle;

public class DogFactory implements BigFactory{
    @Override
    public Animal createAnimal() {
        return new Dog();
    }
}
