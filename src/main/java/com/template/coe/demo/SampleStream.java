package com.template.coe.demo;


import java.util.*;
import java.util.stream.Collectors;

public class SampleStream {

    public static void main(String[] args) {

        List<Person> people = getPeople();

//        Imperative approach
//        List<Person> females = new ArrayList<Person>();
//
//        for (Person person : people) {
//            if (person.getGender().equals(Gender.FEMALE)) {
//                females.add(person);
//            }
//        }
//
//        females.forEach(System.out::println);

//        Declarative approach

//        Filter
        List<Person> females = people.stream()
                .filter(person -> person.getGender().equals(Gender.FEMALE))
                .collect(Collectors.toList());
//        females.forEach(System.out::println);

//        Sort
        System.out.println("Sort: ");
        people.stream()
                .sorted(Comparator.comparing(Person::getAge))
                .collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("Sort reverse : ");
        people.stream()
                .sorted(Comparator.comparing(Person::getAge).reversed())
                .collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("Sort multiple comparing : ");
        people.stream()
                .sorted(Comparator.comparing(Person::getAge).thenComparing(Person::getGender).reversed())
                .collect(Collectors.toList()).forEach(System.out::println);

//        All Match
        boolean allMatch = people.stream()
                .allMatch(person -> person.getAge() > 7);

        System.out.println("All Match: " + allMatch);

//        Any Match
        boolean anyMatch = people.stream()
                .anyMatch(person -> person.getAge() > 5);

        System.out.println("Any Match: " + allMatch);

//        Nomne Match
        boolean noneMatch = people.stream()
                .noneMatch(person -> person.getName().equals("Antonio"));

        System.out.println("Mone Match: " + noneMatch);

//        Max
        people.stream()
                .max(Comparator.comparing(Person::getAge)).ifPresent(System.out::println);
//        Min
        people.stream()
                .min(Comparator.comparing(Person::getAge)).ifPresent(System.out::println);
//        Group
        Map<Gender, List<Person>> groupGender = people.stream()
                .collect(Collectors.groupingBy(Person::getGender));

        System.out.println("Group by");

        groupGender.forEach((gender, peopleGroup) -> {
            System.out.println(gender);
            peopleGroup.forEach(System.out::println);
            System.out.println();
        });

        Optional<String> oldestFemaleAge = people.stream()
                .filter(person -> person.getGender().equals(Gender.FEMALE))
                .max(Comparator.comparing(Person::getAge))
                .map(Person::getName);
        System.out.println("The Name of OldestFemaleAge: ");
        oldestFemaleAge.ifPresent(System.out::println);
    }

    private static List<Person> getPeople() {
        ArrayList<Person> person = new ArrayList<>();
        person.add(new Person("James Bond", 20, Gender.MALE));
        person.add(
            new Person("James Bond", 20, Gender.MALE));

        person.add(     new Person("Alina Smith", 33, Gender.FEMALE));
        person.add(     new Person("Helen White", 57, Gender.FEMALE));
        person.add(    new Person("Alex Boz", 14, Gender.MALE));
        person.add(     new Person("Jamie Goa", 99, Gender.MALE));
        person.add(     new Person("Anna Cook", 7, Gender.FEMALE));
        person.add(     new Person("Zelda Brown", 120, Gender.FEMALE));

        return person;
    }
}
