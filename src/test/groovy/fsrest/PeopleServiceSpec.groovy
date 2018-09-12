package fsrest

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import org.springframework.test.annotation.Rollback
import spock.lang.Unroll

@Rollback
class PeopleServiceSpec extends HibernateSpec implements ServiceUnitTest<PeopleService>{ // extends HibernateSpec


    List<Class> getDomainClasses() {[Person]}
    def setup() {
    }

    def setupData() { //this will rollback
        new Person(id: 1, name:"aa", age:38).save(flush:true)
        new Person(id: 2, name:"bb", age:32).save(flush:true)
        new Person(id: 3, name:"cc", age:30).save(flush:true)
        new Person(id: 4, name:"dd", age:25).save(flush:true)
    }

    def cleanup() {
    }

    void "test person count"() {
        given:
        setupData()

        expect:"get count"
        Person.count == 4

    }

    def "test find people with age > 30"() {
        given:
        setupData()

        when: "service is called"
        List<Person> people = service.findPeopleByAgeGreaterThan(30)

        then:
        Person.count == 4
        people.size() == 2
        people[0].name == "aa"
        people[0].age == 38
    }

    @Unroll("Test find by age with params: #age, #size, #count")
    def "test find people with expect"() {
        given:
        setupData()
        when:
        List<Person> people = service.findPeopleByAgeGreaterThan(age)

        then:
        people.size == size
        Person.count == count

        where:
        age | size | count
        40  | 0    | 4
        39  | 0    | 4
        37  | 1    | 4
        20  | 4    | 4

    }

    def "test person save"() {

        when:
        service.createPerson("ferdi", 30)

        then:
        Person.count == old(Person.count) +1
    }

    def "test person update"() {
        given :
        setupData()

        when:
        def person = Person.get(1)
        service.updatePerson(person.id, "ferdi", 30)

        then:
        person.name == "ferdi"
        person.age == 30
    }
}
