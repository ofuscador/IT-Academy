package n2Exercise2;

public class GenericMethods {
    
    @SafeVarargs
    static <T> T[] genericMethod(T... args) {
        return args;
    }
}
