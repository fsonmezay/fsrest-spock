package fsrest

class Person {
    String name
    int age

    static constraints = {
        age range: 0..120
    }
}
