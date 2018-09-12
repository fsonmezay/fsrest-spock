package fsrest

import grails.gorm.transactions.Transactional

@Transactional
class PeopleService {

    List<Person> findPeopleByAgeGreaterThan(Integer age) {
        Person.findAllByAgeGreaterThan(age)
    }

    def createPerson(String name, Integer age) {
        new Person(name:name, age: age).save(flush:true, failOnError:true)
    }

    def updatePerson(Long id, String name, Integer age) {
        def person = Person.get(id)
        person.name = name
        person.age = age
        person.save(failOnError: true)
    }
}
