package fsrest

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class PersonSpec extends Specification implements DomainUnitTest<Person> {

    def setup() {
    }

    def cleanup() {
    }

    @Unroll("given age is #age validation is #isValid")
    void "person age should be between 0 and 120"() {

        expect: "validate age of Person"
        new Person(age: age).validate(['age']) == isValid
        where:
        age | isValid
        -2  | false
        0   | true
        120 | true
        121 | false
    }

    @Unroll("Name is #name and result is #isValid")
    void "name can not be null or empty"() {
        expect: "Validate name of person"
        new Person(name: name).validate(['name']) == isValid

        where:
        name | isValid
        ''   | false
        null | false
        "f"  | true
    }

    @Unroll("Name is : #name , Age is : #age, Result is : #isValid")
    void "person validation"() {
        expect:
        new Person(name: name, age: age).validate() == isValid

        where:
        name    | age  | isValid
        "ferdi" | 38   | true
        "ferd2" | -1   | false
        "ferd3" | 121  | false
        ""      | 40   | false
        null    | 40   | false
        "ferd4" | null | false
    }

    @Unroll("#name | #isValid | #errorCode")
    void "notNull different Validation"() {

        when:
        domain.name = name

        then:
        domain.validate(['name']) == isValid
        domain.errors['name']?.code == errorCode

        where:
        name | isValid | errorCode
        ''   | false   | 'blank'
        null | false   | 'nullable'
        'f'  | true    | null

    }
}
