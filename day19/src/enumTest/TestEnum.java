package enumTest;

public class TestEnum {

    public static void main(String[] args) {

        System.out.println(Sex.MALE.ordinal());
        System.out.println(Sex.FEMALE.ordinal());
        System.out.println(Sex.MALE.name());

        System.out.println(Sex.valueOf("MALE") == Sex.MALE);

        for (Sex value : Sex.values()) {
            System.out.println(value);
        }

        System.out.println(Sex.MALE.cnName());
        System.out.println(Sex.FEMALE.cnName());
        System.out.println(Sex.MALE.test());
    }
}
