package com.template.coe.demo.bootstrap;

import com.template.coe.demo.model.Student;
import com.template.coe.demo.repository.StudentRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final StudentRepository repository;

    public DataLoader(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Student student_a = new Student();
        student_a.setFirstName("Kido");
        student_a.setLastName("Bae");
        student_a.setDepartment("Data Science");
        repository.save(student_a);

        Student student_b = new Student();
        student_b.setFirstName("Josh");
        student_b.setLastName("Lang");
        student_b.setDepartment("Computer Science");
        repository.save(student_b);

    }
}
