package designmodle;

public class Test {
    public static void main(String[] args) {
        //工厂设计模式
        //工厂方法模式中抽象工厂类负责定义创建对象的接口，具体对象的创建工作由继承抽象工厂的具体类实现。
        Animal cat = new CatFactory().createAnimal();
        cat.eat();
        Animal dog = new DogFactory().createAnimal();
        dog.eat();

    }
}
